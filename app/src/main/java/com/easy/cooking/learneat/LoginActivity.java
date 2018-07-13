package com.easy.cooking.learneat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tiet_login_email)
    TextInputEditText etEmail;

    @BindView(R.id.tiet_login_password)
    TextInputEditText etPassword;

    @BindView(R.id.tv_login_register)
    TextView tvRegister;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.iv_login_information)
    ImageView ivInformation;

    @BindView(R.id.login_progressbar)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private String mEmail;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
        else {
            ButterKnife.bind(this);
            initPreferences();

            ivInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentInformation = new Intent(getApplicationContext(), HelpActivity.class);
                    startActivity(intentInformation);
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        userLogin();
                    }
                }
            });

            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void initPreferences() {
        preferences = getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEmail = preferences.getString(Constants.EMAIL_NAME_LOGIN, "");
        mPassword = preferences.getString(Constants.PASSWORD_NAME_LOGIN, "");

        etEmail.setText(mEmail);
        etPassword.setText(mPassword);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.EMAIL_NAME_LOGIN, etEmail.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.PASSWORD_NAME_LOGIN, etPassword.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void userLogin() {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            //clear all the open activities from the stack, the user cant go back to the login activity
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), R.string.login_welcome_message, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validate() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

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

        return true;
    }
}
