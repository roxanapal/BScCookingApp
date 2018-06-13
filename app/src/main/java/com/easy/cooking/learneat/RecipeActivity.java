package com.easy.cooking.learneat;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.models.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "recipe";
    private static final int DEFAULT_ID = -1;

    @BindView(R.id.recipe_toolbar)
    Toolbar recipeToolbar;

    @BindView(R.id.recipe_title)
    TextView tvTitle;

    @BindView(R.id.recipe_image)
    ImageView ivRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setSupportActionBar(recipeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            errorMessage();
            return;
        }

        Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
        if (recipe.getIdRecipe() == DEFAULT_ID) {
            errorMessage();
            return;
        }

        setRecipeInfo(recipe);
    }

    private void errorMessage() {
        Toast.makeText(this, R.string.recipe_data_error, Toast.LENGTH_SHORT).show();
    }

    private void setRecipeInfo(Recipe recipe){
        tvTitle.setText(recipe.getTitleRecipe());
        Picasso.get()
                .load(recipe.getUrlImageRecipe())
                .into(ivRecipe);
    }
}
