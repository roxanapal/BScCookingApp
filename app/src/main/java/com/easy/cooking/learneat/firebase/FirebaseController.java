package com.easy.cooking.learneat.firebase;

import android.util.Log;

import com.easy.cooking.learneat.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by roxan on 3/13/2018.
 */

public class FirebaseController {
    public static final String TAG = "FirebaseController";
    private final String TABLE_NAME_USER = "users";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private boolean insertResponse;
    private static FirebaseController firebaseController;

    // Constructor
    private FirebaseController() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    // Get instance method
    public static FirebaseController getInstance() {
        synchronized (FirebaseController.class) {
            if (firebaseController == null) {
                firebaseController = new FirebaseController();
            }
        }
        return firebaseController;
    }

    public boolean addUser(User user) {
        insertResponse = false;

        if (user == null) {
            return insertResponse;
        }

        databaseReference = firebaseDatabase.getReference(TABLE_NAME_USER);
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            user.setId(databaseReference.push().getKey());
        }
        databaseReference.child(user.getId()).setValue(user);
        updateUser(user);

        return insertResponse;
    }

    private void updateUser(User user) {
        databaseReference.child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null) {
                    insertResponse = true;
                    Log.i(TAG, "Updated user " + user.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Insert is not working");
            }
        });
    }

}
