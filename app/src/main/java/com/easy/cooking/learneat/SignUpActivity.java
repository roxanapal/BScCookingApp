package com.easy.cooking.learneat;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

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

    private FirebaseController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        firebaseController = FirebaseController.getInstance();

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
        if (etUsername == null || etUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.sign_up_username_error, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etPassword == null || etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.sign_up_password_error, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etEmail == null || etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.sign_up_email_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onClickToSignUp(View view) {
        if (validate()) {
            User user = createUserFromView();
            firebaseController.addUser(user);
            Toast.makeText(this, R.string.sign_up_user_created_toast, Toast.LENGTH_SHORT).show();

            if(user != null){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private User createUserFromView() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        return new User(username, password, email);
    }
}
