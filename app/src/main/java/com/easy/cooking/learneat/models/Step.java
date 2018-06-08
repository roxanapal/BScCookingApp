package com.easy.cooking.learneat.models;

public class Step {
    private int idStep;
    private int titleStep;
    private int descriptionStep;
    private String urlImageStep;
    private int timeStep;
    private int pointsStep;

    public Step() {
    }

    public Step(int idStep, int titleStep, int descriptionStep, String urlImageStep, int timeStep, int pointsStep) {
        this.idStep = idStep;
        this.titleStep = titleStep;
        this.descriptionStep = descriptionStep;
        this.urlImageStep = urlImageStep;
        this.timeStep = timeStep;
        this.pointsStep = pointsStep;
    }

    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    public int getTitleStep() {
        return titleStep;
    }

    public void setTitleStep(int titleStep) {
        this.titleStep = titleStep;
    }

    public int getDescriptionStep() {
        return descriptionStep;
    }

    public void setDescriptionStep(int descriptionStep) {
        this.descriptionStep = descriptionStep;
    }

    public String getUrlImageStep() {
        return urlImageStep;
    }

    public void setUrlImageStep(String urlImageStep) {
        this.urlImageStep = urlImageStep;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public int getPointsStep() {
        return pointsStep;
    }

    public void setPointsStep(int pointsStep) {
        this.pointsStep = pointsStep;
    }
}
