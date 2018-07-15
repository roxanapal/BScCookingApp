package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CompletedRecipe implements Parcelable, Serializable {
    private int idRecipe;
    private String titleRecipe;
    private String imageUri;
    private String dateCompleted;
    private int numberPoints;

    public CompletedRecipe() {
    }

    public CompletedRecipe(int idRecipe, String titleRecipe, String imageUri, String dateCompleted, int numberPoints) {
        this.idRecipe = idRecipe;
        this.titleRecipe = titleRecipe;
        this.imageUri = imageUri;
        this.dateCompleted = dateCompleted;
        this.numberPoints = numberPoints;
    }

    protected CompletedRecipe(Parcel in) {
        idRecipe = in.readInt();
        titleRecipe = in.readString();
        imageUri = in.readString();
        dateCompleted = in.readString();
        numberPoints = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRecipe);
        dest.writeString(titleRecipe);
        dest.writeString(imageUri);
        dest.writeString(dateCompleted);
        dest.writeInt(numberPoints);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompletedRecipe> CREATOR = new Creator<CompletedRecipe>() {
        @Override
        public CompletedRecipe createFromParcel(Parcel in) {
            return new CompletedRecipe(in);
        }

        @Override
        public CompletedRecipe[] newArray(int size) {
            return new CompletedRecipe[size];
        }
    };

    public int getNumberPoints() {
        return numberPoints;
    }

    public void setNumberPoints(int numberPoints) {
        this.numberPoints = numberPoints;
    }

    public String getTitleRecipe() {
        return titleRecipe;
    }

    public void setTitleRecipe(String titleRecipe) {
        this.titleRecipe = titleRecipe;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "CompletedRecipe{" +
                "idRecipe=" + idRecipe +
                ", titleRecipe='" + titleRecipe + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", dateCompleted='" + dateCompleted + '\'' +
                ", numberPoints=" + numberPoints +
                '}';
    }
}
