package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Advice implements Parcelable, Serializable {
    private int idAdvice;
    private String nameChef;
    private String descriptionAdvice;
    private String categoryAdvice;
    private String urlImageChef;
    private boolean favoriteAdvice;

    public Advice() {
    }

    public Advice(int idAdvice, String nameChef, String descriptionAdvice, String categoryAdvice, String urlImageChef, boolean favoriteAdvice) {
        this.idAdvice = idAdvice;
        this.nameChef = nameChef;
        this.descriptionAdvice = descriptionAdvice;
        this.categoryAdvice = categoryAdvice;
        this.urlImageChef = urlImageChef;
        this.favoriteAdvice = favoriteAdvice;
    }

    protected Advice(Parcel in) {
        idAdvice = in.readInt();
        nameChef = in.readString();
        descriptionAdvice = in.readString();
        categoryAdvice = in.readString();
        urlImageChef = in.readString();
        favoriteAdvice = in.readByte() != 0;
    }

    public static final Creator<Advice> CREATOR = new Creator<Advice>() {
        @Override
        public Advice createFromParcel(Parcel in) {
            return new Advice(in);
        }

        @Override
        public Advice[] newArray(int size) {
            return new Advice[size];
        }
    };

    public int getIdAdvice() {
        return idAdvice;
    }

    public void setIdAdvice(int idAdvice) {
        this.idAdvice = idAdvice;
    }

    public String getNameChef() {
        return nameChef;
    }

    public void setNameChef(String nameChef) {
        this.nameChef = nameChef;
    }

    public String getDescriptionAdvice() {
        return descriptionAdvice;
    }

    public void setDescriptionAdvice(String descriptionAdvice) {
        this.descriptionAdvice = descriptionAdvice;
    }

    public String getCategoryAdvice() {
        return categoryAdvice;
    }

    public void setCategoryAdvice(String categoryAdvice) {
        this.categoryAdvice = categoryAdvice;
    }

    public boolean isFavoriteAdvice() {
        return favoriteAdvice;
    }

    public void setFavoriteAdvice(boolean favoriteAdvice) {
        this.favoriteAdvice = favoriteAdvice;
    }

    public String getUrlImageChef() {
        return urlImageChef;
    }

    public void setUrlImageChef(String urlImageChef) {
        this.urlImageChef = urlImageChef;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAdvice);
        dest.writeString(nameChef);
        dest.writeString(descriptionAdvice);
        dest.writeString(categoryAdvice);
        dest.writeString(urlImageChef);
        dest.writeByte((byte) (favoriteAdvice ? 1 : 0));
    }
}
