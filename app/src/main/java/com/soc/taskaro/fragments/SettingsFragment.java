package com.soc.taskaro.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.soc.taskaro.R;
import com.soc.taskaro.activities.LoginScreenActivity;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.User;
import com.soc.taskaro.utils.Constants;
import com.soc.taskaro.utils.Extras;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class SettingsFragment extends Fragment {
    public ProgressDialog progressDialog;
    ImageButton nameTextViewEditBtn, nameTextViewDoneBtn, editProfilePhotoBtn, deleteProfilePhotoBtn;
    LinearLayout nameTextViewLL, nameEditTextLL;
    TextView nameTextView, emailTextView, mobileTextView;
    ImageView profileImageView;
    ActivityResultLauncher<Intent> launcher;
    Button saveSettingBtn, logoutBtn, deleteAccountBtn;
    AlertDialog.Builder builder;
    Boolean isNameValid = false;
    EditText nameEditText;
    private Uri uri = null;
    private String userProfileImageURL = "";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameTextViewEditBtn = view.findViewById(R.id.nameTextViewEditBtn);
        nameTextViewDoneBtn = view.findViewById(R.id.nameTextViewDoneBtn);
        editProfilePhotoBtn = view.findViewById(R.id.editProfilePhotoBtn);
        deleteProfilePhotoBtn = view.findViewById(R.id.deleteProfilePhotoBtn);
        nameEditText = view.findViewById(R.id.nameEditText);
        nameTextViewLL = view.findViewById(R.id.nameTextViewLL);
        nameEditTextLL = view.findViewById(R.id.nameEditTextLL);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        deleteAccountBtn = view.findViewById(R.id.deleteAccountBtn);
        profileImageView = view.findViewById(R.id.profileImageView);
        saveSettingBtn = view.findViewById(R.id.saveSettingBtn);
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        mobileTextView = view.findViewById(R.id.mobileTextView);

        builder = new AlertDialog.Builder(getContext());


        progressDialog = new Extras().showProgressBar(SettingsFragment.this);
        new FirestoreClass().getUsersDetails(SettingsFragment.this);

        // Replacing textView with editText
        nameTextViewEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameTextViewLL.setVisibility(View.GONE);
                nameEditTextLL.setVisibility(View.VISIBLE);
            }
        });

        nameTextViewDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameTextView.setText(nameEditText.getText().toString().trim());
                nameTextViewLL.setVisibility(View.VISIBLE);
                nameEditTextLL.setVisibility(View.GONE);
                saveSettingBtn.setEnabled(true);
            }
        });

        // Logout the app
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logout Successfully...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SettingsFragment.this.getActivity(), LoginScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                getActivity().finish();
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to delete your account?").setTitle("Alert!")
                        .setCancelable(false)
                        .setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                new FirestoreClass().deleteUsersDetails(SettingsFragment.this);
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext(), "Account Deleted Successfully...", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SettingsFragment.this.getActivity(), LoginScreenActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        getActivity().finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        // Choose Image for Profile
        editProfilePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(requireActivity())
                        .crop(16f, 9f)
                        .maxResultSize(1080, 1080, true)
                        .provider(ImageProvider.BOTH)
                        .createIntentFromDialog(new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcher.launch(it);
                            }
                        });
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                uri = result.getData().getData();
                profileImageView.setImageURI(uri);
                userProfileImageURL = uri.toString();
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), "Image not selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Remove profile photo
        deleteProfilePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileImageView.getDrawable().getConstantState() != Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.pic_abstract)).getConstantState()) {
                    profileImageView.setImageResource(R.drawable.pic_abstract);
                    saveSettingBtn.setEnabled(true);
                    deleteProfilePhotoBtn.setVisibility(View.GONE);
                    userProfileImageURL = "";
                    uri = null;
                }
            }
        });

        // Disable save button
        saveSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extras.networkCheck(getContext())) {
                    saveSettingBtn.setEnabled(false);
                    progressDialog = new Extras().showProgressBar(SettingsFragment.this);
                    setValidation();
                } else {
                    Toast.makeText(getContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void setValidation() {
        if (nameEditText.getText().toString().trim().isEmpty()) {
            isNameValid = false;
            Toast.makeText(getActivity(), "Please enter Name", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            isNameValid = true;
        }

        if (isNameValid) {
            if (userProfileImageURL.equals("")) {
                HashMap<String, Object> userHashMap = new HashMap<>();
                userHashMap.put(Constants.IMAGE, userProfileImageURL);
                userHashMap.put(Constants.NAME, nameEditText.getText().toString().trim());
                new FirestoreClass().updateUserDetails(userHashMap, SettingsFragment.this);
            } else {
                new FirestoreClass().uploadImageToCloudStorage(SettingsFragment.this, uri, Constants.USER_IMAGE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (profileImageView.getDrawable().getConstantState() != Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.pic_abstract)).getConstantState()) {
            saveSettingBtn.setEnabled(true);
        } else saveSettingBtn.setEnabled(false);

        if (profileImageView.getDrawable().getConstantState() != Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.pic_abstract)).getConstantState()) {
            deleteProfilePhotoBtn.setVisibility(View.VISIBLE);
        } else deleteProfilePhotoBtn.setVisibility(View.GONE);
    }

    public void userGetDataSuccess(User user) {
        if (user.image.equals("")) {
            nameEditText.setText(user.getName());
            nameTextView.setText(user.getName());
            emailTextView.setText(user.getEmail());
            mobileTextView.setText(String.valueOf(user.getMobile()));
            progressDialog.dismiss();
        } else {
            deleteProfilePhotoBtn.setVisibility(View.VISIBLE);
            userProfileImageURL = user.getImage();
            Picasso.get().load(user.getImage()).into(profileImageView);
            nameEditText.setText(user.getName());
            nameTextView.setText(user.getName());
            emailTextView.setText(user.getEmail());
            mobileTextView.setText(String.valueOf(user.getMobile()));
            progressDialog.dismiss();
        }
    }

    public void userDataUpdateSuccess() {
        progressDialog.dismiss();
    }

    public void imageUploadSuccess(Uri imageUri) {
        HashMap<String, Object> userHashMap = new HashMap<>();
        userHashMap.put(Constants.IMAGE, imageUri);
        userHashMap.put(Constants.NAME, nameEditText.getText().toString().trim());
        new FirestoreClass().updateUserDetails(userHashMap, SettingsFragment.this);
    }
}