package com.soc.taskaro.firestore;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.soc.taskaro.activities.LoginScreen;
import com.soc.taskaro.activities.SignUpActivity;
import com.soc.taskaro.utils.Constants;
import com.soc.taskaro.models.User;

public class FirestoreClass {
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

    public void registerUser(SignUpActivity activity , User UsersInfo){
        System.out.println(UsersInfo.id + UsersInfo.name + UsersInfo.email + UsersInfo.mobile );
        dbroot.collection(Constants.USERS).document(UsersInfo.id).set(UsersInfo,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Done");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.print(e);
                    }
                });
    }

    public String getCurrentUserID(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = "";
        if(currentUser != null){
            currentUserID = currentUser.getUid();
        }
        return currentUserID;
    }
    public void getUsersDetails(Activity activity){
        dbroot.collection(Constants.USERS).document(getCurrentUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.TASKARO_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.LOGGED_IN_USERNAME, user.name);
                editor.apply();

                if(activity instanceof LoginScreen){
                    ((LoginScreen) activity).userLoggedInSuccess(user);
                }

            }
        });
    }

}
