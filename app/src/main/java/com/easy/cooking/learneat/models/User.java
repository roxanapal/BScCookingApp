package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by roxan on 3/13/2018.
 */

public class User implements Parcelable {
    private String uid;
    private String username;
    private String password;
    private String email;
    private int numberPoints;
    private List<Advice> favoriteAdviceList;
    private String urlProfilePhoto;
    private List<String> completedRecipesGallery;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String uid, String username, String password, String email, int numberPoints, List<Advice> favoriteAdviceList, String urlProfilePhoto, List<String> completedRecipesGallery) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.numberPoints = numberPoints;
        this.favoriteAdviceList = favoriteAdviceList;
        this.urlProfilePhoto = urlProfilePhoto;
        this.completedRecipesGallery = completedRecipesGallery;
    }

    protected User(Parcel in) {
        uid = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        numberPoints = in.readInt();
        urlProfilePhoto = in.readString();
        completedRecipesGallery = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public List<Advice> getFavoriteAdviceList() {
        return favoriteAdviceList;
    }

    public void setFavoriteAdviceList(List<Advice> favoriteAdviceList) {
        this.favoriteAdviceList = favoriteAdviceList;
    }

    public List<String> getCompletedRecipesGallery() {
        return completedRecipesGallery;
    }

    public void setCompletedRecipesGallery(List<String> completedRecipesGallery) {
        this.completedRecipesGallery = completedRecipesGallery;
    }

    public String getUrlProfilePhoto() {
        return urlProfilePhoto;
    }

    public void setUrlProfilePhoto(String urlProfilePhoto) {
        this.urlProfilePhoto = urlProfilePhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeInt(numberPoints);
        dest.writeString(urlProfilePhoto);
        dest.writeStringList(completedRecipesGallery);
    }

    @Override
    public String toString() {
        return "User{}";
    }
}
