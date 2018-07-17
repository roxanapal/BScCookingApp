package com.easy.cooking.learneat.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.easy.cooking.learneat.models.Advice;
import com.easy.cooking.learneat.models.CompletedRecipe;
import com.easy.cooking.learneat.models.User;
import com.easy.cooking.learneat.utils.Constants;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseController {

    public static final String TAG = FirebaseController.class.getSimpleName();

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private static FirebaseController firebaseController;

    private boolean responseInsert;

    private FirebaseController() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static FirebaseController getInstance() {
        synchronized (FirebaseController.class) {
            if(firebaseController == null) {
                firebaseController = new FirebaseController();
            }
        }
        return firebaseController;
    }

    public void getAllRecipes(ValueEventListener eventListener) {
        if(eventListener != null) {
            databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_RECIPE);
        }
        databaseReference.addValueEventListener(eventListener);
    }

    public void getAdviceList(ValueEventListener eventListener) {
        if(eventListener != null) {
            databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_ADVICE);
        }
        databaseReference.addValueEventListener(eventListener);
    }

    public boolean addUserToRealtimeDatabase(User user) {

        responseInsert = false;
        if (user == null)
            return responseInsert;

        databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_USER);
        // generate an id for your registration
        if (user.getUid() == null || user.getUid().trim().isEmpty()) {
            user.setUid(databaseReference.push().getKey());
        }
        databaseReference.child(user.getUid()).setValue(user);

        addChangeEventListenerForEachUser(user);

        return responseInsert;
    }

    private void addChangeEventListenerForEachUser(User user) {
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User tempUser = dataSnapshot.getValue(User.class);
                if(tempUser != null){
                    responseInsert = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Insert is not working.");
            }
        });
    }

    public void getUser(ValueEventListener eventListener, FirebaseUser firebaseUser) {
        databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_USER);
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(eventListener);
    }

    public void updateProfilePhoto(FirebaseUser firebaseUser, String photoUrl){
        databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_USER);
        databaseReference.child(firebaseUser.getUid()).child("urlProfilePhoto").setValue(photoUrl);
    }

    public void addCompletedRecipe(FirebaseUser firebaseUser, CompletedRecipe completedRecipe){
        databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_USER);
        databaseReference.child(firebaseUser.getUid()).child("completedRecipesGallery").push().setValue(completedRecipe);
    }

    public void updateFavoriteAdvice(Advice advice){
        databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_ADVICE);
        databaseReference.child(String.valueOf(advice.getIdAdvice())).child("favoriteAdvice").setValue(!advice.isFavoriteAdvice());
    }

    public void addNumberPoints(FirebaseUser firebaseUser, int numberPoints){
        databaseReference = firebaseDatabase.getReference(Constants.TABLE_NAME_USER);
        databaseReference.child(firebaseUser.getUid()).child("numberPoints").setValue(numberPoints);
    }
}
