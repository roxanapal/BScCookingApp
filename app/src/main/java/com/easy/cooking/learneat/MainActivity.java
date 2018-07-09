package com.easy.cooking.learneat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.adapters.RecipeAdapter;
import com.easy.cooking.learneat.data.UserProfile;
import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.Recipe;
import com.easy.cooking.learneat.models.User;
import com.easy.cooking.learneat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;

    @BindView(R.id.main_navigation_view)
    NavigationView mainNavigationView;

    @BindView(R.id.main_rv_recipe_list)
    RecyclerView recyclerViewRecipe;

    @BindView(R.id.main_progressbar)
    ProgressBar mainProgressBar;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FirebaseAuth mAuth;

    private List<Recipe> recipes = new ArrayList<>();
    private RecipeAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        initToolbar();
        initFirebaseController();
        setNavigationView();
    }

    public void initToolbar() {
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void setNavigationView() {
        setupDrawerContent(mainNavigationView);
        View headerView = mainNavigationView.inflateHeaderView(R.layout.header_drawer_layout);

        ImageView ivProfile = headerView.findViewById(R.id.menu_avatar);
        Picasso.get().load(user.getUrlProfilePhoto())
                .into(ivProfile);

        TextView tvUsername = headerView.findViewById(R.id.menu_name);
        tvUsername.setText(user.getUsername());

        TextView tvNumberPoints = headerView.findViewById(R.id.menu_number_points);
        tvNumberPoints.setText(user.getNumberPoints());

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, 0, 0);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    public void initFirebaseController() {
        FirebaseController firebaseController = FirebaseController.getInstance();
        firebaseController.getAllRecipes(getRecipesFromFirebase());
        firebaseController.getUser(getUserFromFirebase());
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.menu_profile_label:
                        Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        intentProfile.putExtra(Constants.EXTRA_PROFILE, user);
                        startActivity(intentProfile);
                        break;
                    case R.id.menu_advice_famous_chefs:
                        Toast.makeText(MainActivity.this, "Sfaturi Bucatari", Toast.LENGTH_SHORT).show();
                        Intent intentAdvice = new Intent(getApplicationContext(), AdviceActivity.class);
                        startActivity(intentAdvice);
                        break;
                    case R.id.menu_feedback:
                        Intent intentFeedback = new Intent(getApplicationContext(), FeedbackActivity.class);
                        startActivity(intentFeedback);
                        break;
                    case R.id.menu_help:
                        Toast.makeText(MainActivity.this, "Ajutor", Toast.LENGTH_SHORT).show();
                        Intent intentHelp = new Intent(getApplicationContext(), HelpActivity.class);
                        startActivity(intentHelp);
                        break;
                    case R.id.menu_log_out:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Te-ai delogat cu succes", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                mainDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private ValueEventListener getRecipesFromFirebase() {
        mainProgressBar.setVisibility(View.VISIBLE);
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipes = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Recipe recipe = data.getValue(Recipe.class);
                    if (recipe != null) {
                        recipes.add(recipe);
                        Log.i(TAG, "Selected recipe: " + recipe.toString());
                    } else {
                        Log.i(TAG, "Selected recipe is null");
                    }
                }
                initRvRecipes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Data is not available.");
            }
        };
    }

    private ValueEventListener getUserFromFirebase() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    user = data.getValue(User.class);
                    if (user != null && user.getUid().equals(firebaseUser.getUid())) {
                        Log.i(TAG, "Selected user: " + user.toString());
                        return;
                    } else {
                        Log.i(TAG, "Selected user is null");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Data is not available.");
            }
        };
    }

    private void initRvRecipes() {
        adapter = new RecipeAdapter(this, recipes);
        recyclerViewRecipe.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecipe.setAdapter(adapter);
        mainProgressBar.setVisibility(View.GONE);
    }
}
