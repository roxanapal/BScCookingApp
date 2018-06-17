package com.easy.cooking.learneat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easy.cooking.learneat.R;
import com.easy.cooking.learneat.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.tvQuantity.setText(ingredient.getQuantityIngredient());
        holder.tvName.setText(ingredient.getNameIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_ingredient_quantity)
        TextView tvQuantity;

        @BindView(R.id.item_ingredient_name)
        TextView tvName;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
