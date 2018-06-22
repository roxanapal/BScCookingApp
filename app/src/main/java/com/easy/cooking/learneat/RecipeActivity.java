package com.easy.cooking.learneat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.adapters.IngredientAdapter;
import com.easy.cooking.learneat.models.Ingredient;
import com.easy.cooking.learneat.models.Recipe;
import com.easy.cooking.learneat.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    @BindView(R.id.recipe_toolbar)
    Toolbar recipeToolbar;

    @BindView(R.id.recipe_title)
    TextView tvTitle;

    @BindView(R.id.recipe_image)
    ImageView ivRecipe;

    @BindView(R.id.recipe_description)
    TextView tvDescription;

    @BindView(R.id.recipe_rv_ingredients)
    RecyclerView recyclerViewIngredients;

    @BindView(R.id.recipe_btn_start)
    Button btnStartRecipe;

    private Context context = RecipeActivity.this;

    private IngredientAdapter ingredientAdapter;

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
            showErrorMessage();
            return;
        }

        final Recipe recipe = intent.getParcelableExtra(Constants.EXTRA_RECIPE);
        if (recipe.getIdRecipe() == Constants.DEFAULT_ID) {
            showErrorMessage();
            return;
        }

        setRecipeInfo(recipe);
        initRecyclerViewIngredients(recipe.getIngredientList());

        btnStartRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stepIntent = new Intent(RecipeActivity.this, StepActivity.class);
                stepIntent.putExtra(Constants.EXTRA_RECIPE, recipe);
                startActivity(stepIntent);
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, R.string.recipe_data_error, Toast.LENGTH_SHORT).show();
    }

    private void setRecipeInfo(Recipe recipe){
        tvTitle.setText(recipe.getTitleRecipe());
        Picasso.get()
                .load(recipe.getUrlImageRecipe())
                .into(ivRecipe);
        tvDescription.setText(recipe.getDescriptionRecipe());
    }

    private void initRecyclerViewIngredients(List<Ingredient> ingredientList) {
        ingredientAdapter = new IngredientAdapter(ingredientList, this);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIngredients.setNestedScrollingEnabled(false);
        recyclerViewIngredients.setAdapter(ingredientAdapter);
    }
}
