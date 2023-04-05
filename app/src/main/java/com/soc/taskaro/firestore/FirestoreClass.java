package com.soc.taskaro.firestore;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soc.taskaro.MainActivity;
import com.soc.taskaro.activities.LoginScreen;
import com.soc.taskaro.activities.SignUpActivity;
import com.soc.taskaro.fragments.SettingsFragment;
import com.soc.taskaro.models.CreateTask;
import com.soc.taskaro.utils.Constants;
import com.soc.taskaro.models.User;

import java.util.HashMap;

public class FirestoreClass {
    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

    public void registerUser(SignUpActivity activity, User UsersInfo) {
        System.out.println(UsersInfo.id + UsersInfo.name + UsersInfo.email + UsersInfo.mobile);
        dbroot.collection(Constants.USERS).document(UsersInfo.id).set(UsersInfo, SetOptions.merge())
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

    public String getCurrentUserID() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = "";
        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }
        return currentUserID;
    }

    public void getUsersDetails(Activity activity) {
        dbroot.collection(Constants.USERS).document(getCurrentUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.TASKARO_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.NAME, user.name);
                editor.apply();

                if (activity instanceof LoginScreen) {
                    ((LoginScreen) activity).userLoggedInSuccess(user);
                }

            }
        });

    }

    public void getUsersDetails(Fragment fragment) {
        dbroot.collection(Constants.USERS).document(getCurrentUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                SharedPreferences sharedPreferences = fragment.getActivity().getSharedPreferences(Constants.TASKARO_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.NAME, user.getName());
                editor.apply();

                if (fragment instanceof SettingsFragment) {
                    ((SettingsFragment) fragment).userGetDataSuccess(user);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(fragment.getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

    /*public void uploadTaskDetails(CreateTaskActivity createTaskActivity, String txt_title, String txt_description) {
        DocumentReference documentReference = dbroot.collection(Constants.TASKS).document();

        CreateTask task = new CreateTask(getCurrentUserID(), txt_title, txt_description, documentReference.getId());
        documentReference.set(task, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                createTaskActivity.uploadDataSuccess();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(createTaskActivity, "Error! Unable to add product.", Toast.LENGTH_SHORT).show();
            createTaskActivity.btn_saveCreateTask.setEnabled(true);
        });
    }*/
    }

    public void updateUserDetails(HashMap<String, Object> userHashMap, Fragment fragment) {

        DocumentReference documentReference = dbroot.collection(Constants.USERS).document(getCurrentUserID());
        documentReference.update(userHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (fragment instanceof SettingsFragment) {
                    ((SettingsFragment) fragment).userDataUpdateSuccess();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(fragment.getContext(), "Something went wrong. Try Again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteUsersDetails(Fragment fragment) {
        dbroot.collection(Constants.USERS).document(getCurrentUserID()).delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });
    }

    public void uploadImageToCloudStorage(Fragment fragment, Uri mSelectedImageFileUri, String imageType) {
        StorageReference sRef = FirebaseStorage.getInstance().getReference().child(imageType + System.currentTimeMillis() + "." + Constants.getFileExtension(fragment.getActivity(), mSelectedImageFileUri));
        sRef.putFile(mSelectedImageFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ((SettingsFragment) fragment).imageUploadSuccess(mSelectedImageFileUri);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(fragment.getContext(), "Error! Unable to add product.", Toast.LENGTH_LONG).show();
        });
    }
}