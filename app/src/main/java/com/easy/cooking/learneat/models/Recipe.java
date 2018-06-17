package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
    private int idRecipe;
    private String titleRecipe;
    private String descriptionRecipe;
    private String urlImageRecipe;
    private String timeRecipe;
    private int pointsRecipe;
    private int numberIngredients;
    private List<Ingredient> ingredientList;
    private int numberSteps;
    private List<Step> stepList;

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        idRecipe = in.readInt();
        titleRecipe = in.readString();
        descriptionRecipe = in.readString();
        urlImageRecipe = in.readString();
        timeRecipe = in.readString();
        pointsRecipe = in.readInt();
        numberIngredients = in.readInt();

        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);

        numberSteps = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRecipe);
        dest.writeString(titleRecipe);
        dest.writeString(descriptionRecipe);
        dest.writeString(urlImageRecipe);
        dest.writeString(timeRecipe);
        dest.writeInt(pointsRecipe);
        dest.writeInt(numberIngredients);

        dest.writeTypedList(ingredientList);

        dest.writeInt(numberSteps);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getTitleRecipe() {
        return titleRecipe;
    }

    public void setTitleRecipe(String titleRecipe) {
        this.titleRecipe = titleRecipe;
    }

    public String getDescriptionRecipe() {
        return descriptionRecipe;
    }

    public void setDescriptionRecipe(String descriptionRecipe) {
        this.descriptionRecipe = descriptionRecipe;
    }

    public String getUrlImageRecipe() {
        return urlImageRecipe;
    }

    public void setUrlImageRecipe(String urlImageRecipe) {
        this.urlImageRecipe = urlImageRecipe;
    }

    public String getTimeRecipe() {
        return timeRecipe;
    }

    public void setTimeRecipe(String timeRecipe) {
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

    @Override
    public int describeContents() {
        return 0;
    }
}
