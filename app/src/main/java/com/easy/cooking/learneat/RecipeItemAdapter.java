package com.easy.cooking.learneat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.cooking.learneat.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roxana on 6/9/2018.
 */

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeItemAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        Picasso.get()
                .load(recipe.getUrlImageRecipe())
                .into(holder.ivRecipe);
        holder.tvTitleRecipe.setText(recipe.getTitleRecipe());

        String timeRecipe = recipe.getTimeRecipe() + context.getString(R.string.item_recipe_minutes_label);
        holder.tvTimeRecipe.setText(timeRecipe);

        String pointsRecipe = recipe.getPointsRecipe() + context.getString(R.string.item_recipe_points_label);
        holder.tvPointsRecipe.setText(pointsRecipe);

        holder.ivRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(RecipeActivity.EXTRA_RECIPE, recipe);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_recipe_image)
        ImageView ivRecipe;

        @BindView(R.id.item_recipe_title)
        TextView tvTitleRecipe;

        @BindView(R.id.item_recipe_time)
        TextView tvTimeRecipe;

        @BindView(R.id.item_recipe_points)
        TextView tvPointsRecipe;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
