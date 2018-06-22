package com.easy.cooking.learneat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    private int stepNumber;
    private String titleStep;
    private String descriptionStep;
    private String urlImageStep;
    private String timeStep;
    private int pointsStep;

    public Step() {
    }

    protected Step(Parcel in) {
        stepNumber = in.readInt();
        titleStep = in.readString();
        descriptionStep = in.readString();
        urlImageStep = in.readString();
        timeStep = in.readString();
        pointsStep = in.readInt();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepNumber);
        dest.writeString(titleStep);
        dest.writeString(descriptionStep);
        dest.writeString(urlImageStep);
        dest.writeString(timeStep);
        dest.writeInt(pointsStep);
    }
}
