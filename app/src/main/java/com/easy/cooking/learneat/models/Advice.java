package com.easy.cooking.learneat.models;

public class Advice {
    private int idAdvice;
    private String nameChef;
    private String descriptionAdvice;
    private String categoryAdvice;
    private boolean favoriteAdvice;

    public Advice() {
    }

    public Advice(int idAdvice, String nameChef, String descriptionAdvice, String categoryAdvice, boolean favoriteAdvice) {
        this.idAdvice = idAdvice;
        this.nameChef = nameChef;
        this.descriptionAdvice = descriptionAdvice;
        this.categoryAdvice = categoryAdvice;
        this.favoriteAdvice = favoriteAdvice;
    }

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
}
