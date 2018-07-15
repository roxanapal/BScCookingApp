package com.easy.cooking.learneat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.cooking.learneat.R;
import com.easy.cooking.learneat.models.CompletedRecipe;
import com.easy.cooking.learneat.models.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedRecipeAdapter extends RecyclerView.Adapter<CompletedRecipeAdapter.CompletedRecipeViewHolder> {

    private List<CompletedRecipe> completedRecipeList;
    private Context context;

    public CompletedRecipeAdapter(List<CompletedRecipe> completedRecipeList, Context context) {
        this.completedRecipeList = completedRecipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public CompletedRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.item_completed_recipe, parent, false);
        return new CompletedRecipeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedRecipeViewHolder holder, int position) {
        CompletedRecipe completedRecipe = completedRecipeList.get(position);
        Picasso.get()
                .load(completedRecipe.getImageUri())
                .into(holder.ivItemCompleted);
        holder.tvItemCompletedTitle.setText(completedRecipe.getTitleRecipe());
        holder.tvItemCompletedDate.setText(completedRecipe.getDateCompleted());
        holder.tvItemCompletedPoints.setText(String.valueOf(completedRecipe.getNumberPoints()));
    }

    @Override
    public int getItemCount() {
        return completedRecipeList.size();
    }

    class CompletedRecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_completed_recipe)
        ImageView ivItemCompleted;

        @BindView(R.id.tv_item_completed_recipe_title)
        TextView tvItemCompletedTitle;

        @BindView(R.id.tv_item_completed_recipe_date)
        TextView tvItemCompletedDate;

        @BindView(R.id.tv_item_completed_recipe_points)
        TextView tvItemCompletedPoints;

        public CompletedRecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
