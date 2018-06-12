package com.easy.cooking.learneat.models;

public class Ingredient {
    private String nameIngredient;
    private String quantityIngredient;

    public Ingredient() {
    }

    public Ingredient(String nameIngredient, String quantityIngredient) {
        this.nameIngredient = nameIngredient;
        this.quantityIngredient = quantityIngredient;
    }

    public String getNameIngredient() {
        return nameIngredient;
    }

    public void setNameIngredient(String nameIngredient) {
        this.nameIngredient = nameIngredient;
    }

    public String getQuantityIngredient() {
        return quantityIngredient;
    }

    public void setQuantityIngredient(String quantityIngredient) {
        this.quantityIngredient = quantityIngredient;
    }
}
