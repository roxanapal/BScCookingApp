package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String nameIngredient;
    private String quantityIngredient;

    public Ingredient() {
    }

    public Ingredient(String nameIngredient, String quantityIngredient) {
        this.nameIngredient = nameIngredient;
        this.quantityIngredient = quantityIngredient;
    }

    protected Ingredient(Parcel in) {
        nameIngredient = in.readString();
        quantityIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameIngredient);
        dest.writeString(quantityIngredient);
    }
}
