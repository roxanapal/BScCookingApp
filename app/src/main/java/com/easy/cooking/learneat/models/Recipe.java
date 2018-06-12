package com.easy.cooking.learneat.models;

import java.util.List;

public class Recipe {
    private int idRecipe;
    private int titleRecipe;
    private int descriptionRecipe;
    private String urlImageRecipe;
    private int timeRecipe;
    private int pointsRecipe;
    private int numberIngredients;
    private List<Ingredient> ingredientList;
    private int numberSteps;
    private List<Step> stepList;

    public Recipe() {
    }

    public Recipe(int idRecipe, int titleRecipe, int descriptionRecipe, String urlImageRecipe, int timeRecipe, int pointsRecipe, int numberIngredients, List<Ingredient> ingredientList, int numberSteps, List<Step> stepList) {
        this.idRecipe = idRecipe;
        this.titleRecipe = titleRecipe;
        this.descriptionRecipe = descriptionRecipe;
        this.urlImageRecipe = urlImageRecipe;
        this.timeRecipe = timeRecipe;
        this.pointsRecipe = pointsRecipe;
        this.numberIngredients = numberIngredients;
        this.ingredientList = ingredientList;
        this.numberSteps = numberSteps;
        this.stepList = stepList;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getTitleRecipe() {
        return titleRecipe;
    }

    public void setTitleRecipe(int titleRecipe) {
        this.titleRecipe = titleRecipe;
    }

    public int getDescriptionRecipe() {
        return descriptionRecipe;
    }

    public void setDescriptionRecipe(int descriptionRecipe) {
        this.descriptionRecipe = descriptionRecipe;
    }

    public String getUrlImageRecipe() {
        return urlImageRecipe;
    }

    public void setUrlImageRecipe(String urlImageRecipe) {
        this.urlImageRecipe = urlImageRecipe;
    }

    public int getTimeRecipe() {
        return timeRecipe;
    }

    public void setTimeRecipe(int timeRecipe) {
        this.timeRecipe = timeRecipe;
    }

    public int getPointsRecipe() {
        return pointsRecipe;
    }

    public void setPointsRecipe(int pointsRecipe) {
        this.pointsRecipe = pointsRecipe;
    }

    public int getNumberIngredients() {
        return numberIngredients;
    }

    public void setNumberIngredients(int numberIngredients) {
        this.numberIngredients = numberIngredients;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getNumberSteps() {
        return numberSteps;
    }

    public void setNumberSteps(int numberSteps) {
        this.numberSteps = numberSteps;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "idRecipe=" + idRecipe +
                ", titleRecipe=" + titleRecipe +
                ", descriptionRecipe=" + descriptionRecipe +
                ", urlImageRecipe='" + urlImageRecipe + '\'' +
                ", timeRecipe=" + timeRecipe +
                ", pointsRecipe=" + pointsRecipe +
                ", numberIngredients=" + numberIngredients +
                ", ingredientList=" + ingredientList +
                ", numberSteps=" + numberSteps +
                ", stepList=" + stepList +
                '}';
    }
}
