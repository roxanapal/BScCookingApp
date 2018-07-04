package com.easy.cooking.learneat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.models.User;
import com.easy.cooking.learneat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_toolbar)
    Toolbar profileToolbar;

    @BindView(R.id.iv_profile_picture)
    ImageView ivProfilePicture;

    @BindView(R.id.tv_profile_username)
    TextView tvProfileUsername;

    FirebaseAuth mAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        initToolbar();

        Intent intent = getIntent();
        if (intent == null) {
            showErrorMessage();
            return;
        }

        user = intent.getParcelableExtra(Constants.EXTRA_PROFILE);

        setUserLayout();

    }

    private void setUserLayout() {
        tvProfileUsername.setText(user.getUsername());
        Picasso.get().load(user.getUrlProfilePhoto()).into(ivProfilePicture);
    }

    public void initToolbar() {
        profileToolbar.setTitle(R.string.menu_profile);
        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            Toast.makeText(this, "Trebuie să vă logați.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,LoginActivity.class));
        } else {
            tvProfileUsername.setText(mAuth.getCurrentUser().getEmail());
        }
    }

    private void showErrorMessage() {
        Toast.makeText(this, R.string.recipe_data_error, Toast.LENGTH_SHORT).show();
    }
}
