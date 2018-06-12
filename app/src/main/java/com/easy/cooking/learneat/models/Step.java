package com.easy.cooking.learneat.models;

public class Step {
    private String titleStep;
    private String descriptionStep;
    private String urlImageStep;
    private String timeStep;
    private int pointsStep;

    public Step() {
    }

    public Step(String titleStep, String descriptionStep, String urlImageStep, String timeStep, int pointsStep) {
        this.titleStep = titleStep;
        this.descriptionStep = descriptionStep;
        this.urlImageStep = urlImageStep;
        this.timeStep = timeStep;
        this.pointsStep = pointsStep;
    }

    public String getTitleStep() {
        return titleStep;
    }

    public void setTitleStep(String titleStep) {
        this.titleStep = titleStep;
    }

    public String getDescriptionStep() {
        return descriptionStep;
    }

    public void setDescriptionStep(String descriptionStep) {
        this.descriptionStep = descriptionStep;
    }

    public String getUrlImageStep() {
        return urlImageStep;
    }

    public void setUrlImageStep(String urlImageStep) {
        this.urlImageStep = urlImageStep;
    }

    public String getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(String timeStep) {
        this.timeStep = timeStep;
    }

    public int getPointsStep() {
        return pointsStep;
    }

    public void setPointsStep(int pointsStep) {
        this.pointsStep = pointsStep;
    }
}
