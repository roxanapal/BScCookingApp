package com.easy.cooking.learneat.models;

import java.util.List;

/**
 * Created by roxan on 3/13/2018.
 */

public class User {
    private String uid;
    private String username;
    private String password;
    private String email;
    private int numberPoints;
    private List<Advice> favoriteAdviceList;
    private List<Recipe> completedRecipes;
    private String urlImageCompletedRecipe;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String uid, String username, String password, String email) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String uid, String username, String password, String email, int numberPoints, List<Advice> favoriteAdviceList, List<Recipe> completedRecipes, String urlImageCompletedRecipe) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.numberPoints = numberPoints;
        this.favoriteAdviceList = favoriteAdviceList;
        this.completedRecipes = completedRecipes;
        this.urlImageCompletedRecipe = urlImageCompletedRecipe;
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

    public List<Advice> getFavoriteAdviceList() {
        return favoriteAdviceList;
    }

    public void setFavoriteAdviceList(List<Advice> favoriteAdviceList) {
        this.favoriteAdviceList = favoriteAdviceList;
    }

    public List<Recipe> getCompletedRecipes() {
        return completedRecipes;
    }

    public void setCompletedRecipes(List<Recipe> completedRecipes) {
        this.completedRecipes = completedRecipes;
    }

    public String getUrlImageCompletedRecipe() {
        return urlImageCompletedRecipe;
    }

    public void setUrlImageCompletedRecipe(String urlImageCompletedRecipe) {
        this.urlImageCompletedRecipe = urlImageCompletedRecipe;
    }
}
