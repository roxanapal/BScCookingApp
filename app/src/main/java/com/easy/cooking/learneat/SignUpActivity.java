package com.easy.cooking.learneat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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
    private String username;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        setToolbar(toolbar);
    }

    public void addUserToDatabase() {
        FirebaseController firebaseController = FirebaseController.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String profilePhoto = "https://firebasestorage.googleapis.com/v0/b/learn-eat-app.appspot.com/o/profilephotos%2Fuserprofile.png?alt=media&token=549a29d5-4edb-4ba6-bf6d-c541ada25aa6";

        User userAuth = new User(firebaseUser.getUid(), username, password, email,0,null, profilePhoto, null);
        firebaseController.addUserToRealtimeDatabase(userAuth);

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
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();

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
                            addUserToDatabase();

                            Toast.makeText(getApplicationContext(), R.string.sign_up_user_created_toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            //clear all the open activities from the stack, the user cant go back to sign up activity
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), R.string.sign_up_user_already_registered, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
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
