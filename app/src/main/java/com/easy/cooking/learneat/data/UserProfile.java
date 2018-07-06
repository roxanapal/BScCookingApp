package com.easy.cooking.learneat.data;

import com.easy.cooking.learneat.models.User;

public class UserProfile {
    private User userProfile;

    private static UserProfile instance;

    private UserProfile(){

    }

    public static UserProfile getInstance() {
        if (instance == null){
            instance = new UserProfile();
        }

        return instance;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    public User getUserProfile() {
        return userProfile;
    }
}
