package com.soc.taskaro.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.soc.taskaro.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class SettingsFragment extends Fragment {
    ImageButton nameTextViewEditBtn, nameTextViewDoneBtn, editProfilePhotoBtn, deleteProfilePhotoBtn;
    LinearLayout nameTextViewLL, nameEditTextLL;
    TextView logoutBtn;
    ImageView profileImageView;
    ActivityResultLauncher<Intent> launcher;
    Button saveSettingBtn;

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
        nameTextViewLL = view.findViewById(R.id.nameTextViewLL);
        nameEditTextLL = view.findViewById(R.id.nameEditTextLL);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        profileImageView = view.findViewById(R.id.profileImageView);
        saveSettingBtn = view.findViewById(R.id.saveSettingBtn);

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
                nameTextViewLL.setVisibility(View.VISIBLE);
                nameEditTextLL.setVisibility(View.GONE);
                enableDisableSave(true);
            }
        });

        // Logout the app
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write logout code here
            }
        });

        // Choose Image for Profile
        editProfilePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableSave(true);

                ImagePicker.Companion.with(requireActivity())
                        .setDismissListener(new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                enableDisableSave(false);
                                return Unit.INSTANCE;
                            }
                        })
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
                Uri uri = result.getData().getData();
                profileImageView.setImageURI(uri);
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
                    enableDisableSave(true);
                }
            }
        });

        // Disable save button
        saveSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableSave(false);
            }
        });

        return view;
    }

    // Enable or Disable save button
    void enableDisableSave(boolean enable) {
        saveSettingBtn.setEnabled(enable);
    }
}