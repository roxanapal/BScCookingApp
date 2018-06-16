package com.easy.cooking.learneat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.Recipe;
import com.easy.cooking.learneat.ui.menu.MenuFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseController firebaseController;
    private List<Recipe> recipes = new ArrayList<>();

    private RecipeItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        setNavigationView();
        initFirebaseController();
    }

    public void initToolbar() {
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void setNavigationView() {
        setupDrawerContent(mainNavigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, 0, 0);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.menu_advice_famous_chefs:
                        Toast.makeText(MainActivity.this, "Sfaturi Bucatari", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_help:
                        Toast.makeText(MainActivity.this, "Ajutor", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_log_out:
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Te-ai delogat cu succes", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
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

    private ValueEventListener uploadRecipesFromFirebase() {
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

    private void initRvRecipes() {
        adapter = new RecipeItemAdapter(this, recipes);
        recyclerViewRecipe.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecipe.setAdapter(adapter);
    }

    public void initFirebaseController() {
        firebaseController = FirebaseController.getInstance();
        firebaseController.getAllRecipes(uploadRecipesFromFirebase());
    }
}
