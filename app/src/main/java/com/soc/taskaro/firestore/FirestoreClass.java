package com.soc.taskaro.firestore;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.soc.taskaro.activities.SignUpActivity;
import com.soc.taskaro.activities.utils.Constants;
import com.soc.taskaro.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class FirestoreClass {
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

    public void registerUser(SignUpActivity activity , User UsersInfo){
        System.out.println(UsersInfo.id + UsersInfo.name + UsersInfo.email + UsersInfo.mobile );
        dbroot.collection(Constants.USERS).document(UsersInfo.id).set(UsersInfo,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Done///////////");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.print(e+"!!!!!!!!!!!!");
                    }
                });
    }


}
