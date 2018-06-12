package com.easy.cooking.learneat.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseController {
    private final String TABLE_NAME_RECIPE = "RECIPE";

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private static FirebaseController firebaseController;

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
            databaseReference = firebaseDatabase.getReference(TABLE_NAME_RECIPE);
        }
        databaseReference.addValueEventListener(eventListener);
    }
}
