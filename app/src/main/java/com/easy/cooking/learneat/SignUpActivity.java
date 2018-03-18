package com.easy.cooking.learneat;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easy.cooking.learneat.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private final static String TAG = "SignUpActivity";

    @BindView(R.id.sign_up_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tiet_sign_up_username)
    TextInputEditText etUsername;

    @BindView(R.id.tiet_sign_up_password)
    TextInputEditText etPassword;

    @BindView(R.id.tiet_sign_up_email)
    TextInputEditText etEmail;

    @BindView(R.id.sign_up_toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        setToolbar(toolbar);
    }

    /**
     * Method for setting toolbar
     */
    private void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private boolean validate() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, R.string.sign_up_username_error, Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, R.string.sign_up_password_error, Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        if (password.length() < 5) {
            etPassword.setError(getString(R.string.sign_up_password_error_validation));
            etPassword.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, R.string.sign_up_email_error, Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.sign_up_email_error_validation));
            etEmail.requestFocus();
            return false;
        }

        return true;
    }

    public void onClickToSignUp(View view) {
        if (validate()) {
            registerUser();
        }
    }

    private void registerUser() {
        User user = createUserFromView();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.sign_up_user_created_toast, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail: SUCCESS");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.some_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private User createUserFromView() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        return new User(username, password, email);
    }
}
