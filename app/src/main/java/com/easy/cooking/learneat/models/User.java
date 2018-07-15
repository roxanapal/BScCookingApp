package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roxan on 3/13/2018.
 */

public class User implements Parcelable, Serializable{
    private String uid;
    private String username;
    private String password;
    private String email;
    private int numberPoints;
    private HashMap<String, Advice> favoriteAdviceList;
    private String urlProfilePhoto;
    private HashMap<String, CompletedRecipe> completedRecipesGallery;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String uid,
                String username,
                String password,
                String email,
                int numberPoints,
                HashMap<String, Advice> favoriteAdviceList,
                String urlProfilePhoto,
                HashMap<String, CompletedRecipe> completedRecipesGallery) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.numberPoints = numberPoints;
        this.favoriteAdviceList = favoriteAdviceList;
        this.urlProfilePhoto = urlProfilePhoto;
        this.completedRecipesGallery = completedRecipesGallery;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberPoints() {
        return numberPoints;
    }

    public void setNumberPoints(int numberPoints) {
        this.numberPoints = numberPoints;
    }

    public String getUrlProfilePhoto() {
        return urlProfilePhoto;
    }

    public void setUrlProfilePhoto(String urlProfilePhoto) {
        this.urlProfilePhoto = urlProfilePhoto;
    }

    public HashMap<String, Advice> getFavoriteAdviceList() {
        return favoriteAdviceList;
    }

    public void setFavoriteAdviceList(HashMap<String, Advice> favoriteAdviceList) {
        this.favoriteAdviceList = favoriteAdviceList;
    }

    public HashMap<String, CompletedRecipe> getCompletedRecipesGallery() {
        return completedRecipesGallery;
    }

    public void setCompletedRecipesGallery(HashMap<String, CompletedRecipe> completedRecipesGallery) {
        this.completedRecipesGallery = completedRecipesGallery;
    }

    @Override
    public String toString() {
        return "User{}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeInt(this.numberPoints);
        dest.writeSerializable(this.favoriteAdviceList);
        dest.writeString(this.urlProfilePhoto);
        dest.writeSerializable(this.completedRecipesGallery);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.numberPoints = in.readInt();
        this.favoriteAdviceList = (HashMap<String, Advice>) in.readSerializable();
        this.urlProfilePhoto = in.readString();
        this.completedRecipesGallery = (HashMap<String, CompletedRecipe>) in.readSerializable();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
