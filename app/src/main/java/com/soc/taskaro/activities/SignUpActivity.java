package com.soc.taskaro.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.User;
import com.soc.taskaro.utils.Extras;

import java.util.Random;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    LinearLayout goToLoginLL;
    EditText nameEditText, emailEditText, mobileEditText, passwordEditText, confirmPasswordEditText;
    boolean passwordVisible;
    Button registerButton;
    TextView signUpTagline;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    ProgressDialog progressDialog;
    ImageView faceImageView;
    int[] face;
    Handler handler = new Handler();
    Random randomNumber = new Random();
    private FirebaseAuth firebaseAuth;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            int randNum = randomNumber.nextInt(face.length - 1);
            faceImageView.setImageDrawable(getResources().getDrawable(face[randNum]));
            handler.postDelayed(this, 1200);
        }
    };

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        goToLoginLL = findViewById(R.id.goToLoginLL);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
        firebaseAuth = FirebaseAuth.getInstance();
        faceImageView = findViewById(R.id.faceImageView);
        signUpTagline = findViewById(R.id.signUpTagline);
        face = new int[]{
                R.drawable.vct_face_1,
                R.drawable.vct_face_2,
                R.drawable.vct_face_3,
                R.drawable.vct_face_4,
                R.drawable.vct_face_5,
                R.drawable.vct_face_6,
                R.drawable.vct_face_7,
                R.drawable.vct_face_8,
                R.drawable.vct_face_9,
                R.drawable.vct_face_10,
                R.drawable.vct_face_11,
                R.drawable.vct_face_12,
                R.drawable.vct_face_13,
                R.drawable.vct_face_14,
                R.drawable.vct_face_15,
                R.drawable.vct_face_16,
                R.drawable.vct_face_17,
                R.drawable.vct_face_18,
                R.drawable.vct_face_19,
                R.drawable.vct_face_20,
                R.drawable.vct_face_21,
                R.drawable.vct_face_22,
                R.drawable.vct_face_23,
                R.drawable.vct_face_24,
                R.drawable.vct_face_25,
                R.drawable.vct_face_26
        };

        // This methods change the face in the activity
        startCounting();

        // This fills the textView with gradient
        TextPaint paint = signUpTagline.getPaint();
        float width = paint.measureText(getString(R.string.register_tagline));
        Shader textShader = new LinearGradient(0, 0, width, signUpTagline.getTextSize(),
                new int[]{
                        getResources().getColor(R.color.md_theme_light_shadow),
                        getResources().getColor(R.color.seed)
                }, null, Shader.TileMode.CLAMP);
        signUpTagline.getPaint().setShader(textShader);

        goToLoginLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Show or hide password for password
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int Right = 2;
                if (event.getAction() >= MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = passwordEditText.getSelectionEnd();

                        if (passwordVisible) {
                            // Set drawable image
                            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_outline, 0, R.drawable.ic_visibility_off_outline, 0);
                            // To hide password
                            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            // Set drawable image
                            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_outline, 0, R.drawable.ic_visibility_outline, 0);
                            // To show password
                            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }

                        passwordEditText.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        // Show or hide password for confirm password
        confirmPasswordEditText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int Right = 2;
                if (event.getAction() >= MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= confirmPasswordEditText.getRight() - confirmPasswordEditText.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = confirmPasswordEditText.getSelectionEnd();

                        if (passwordVisible) {
                            // Set drawable image
                            confirmPasswordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_outline, 0, R.drawable.ic_visibility_off_outline, 0);
                            // To hide password
                            confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            // Set drawable image
                            confirmPasswordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_outline, 0, R.drawable.ic_visibility_outline, 0);
                            // To show password
                            confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }

                        confirmPasswordEditText.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        //Signup Button logic
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean have_WIFI = false;
                boolean have_MobileData = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                for (NetworkInfo info : networkInfos) {
                    if (info.getTypeName().equalsIgnoreCase("WIFI"))
                        if (info.isConnected())
                            have_WIFI = true;
                    if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (info.isConnected())
                            have_MobileData = true;
                }
                if (have_MobileData || have_WIFI) {
                    setValidation();
                    progressDialog = new Extras().showProgressBar(SignUpActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), "Authentication Failed! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Below methods are used to change face ImageView
    private void startCounting() {
        handler.post(run);
    }

    private void setValidation() {
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digitCase = Pattern.compile("[0-9]");
        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        }
        if (!nameEditText.getText().toString().trim().isEmpty()) {
            isNameValid = true;
        }
        if (emailEditText.getText().toString().trim().isEmpty()) {
            emailEditText.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()) {
            emailEditText.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }
        if (mobileEditText.getText().toString().trim().isEmpty()) {
            mobileEditText.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else if (mobileEditText.getText().toString().trim().length() != 10) {
            mobileEditText.setError(getResources().getString(R.string.error_invalid_phone));
            isPhoneValid = false;
        } else {
            isPhoneValid = true;
        }
        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordEditText.setError(getResources().getString(R.string.error_Empty_Password));
            isPasswordValid = false;
        } else {
            if (!lowerCase.matcher(passwordEditText.getText().toString().trim()).find()) {
                passwordEditText.setError(getResources().getString(R.string.error_LowerCase_Password));
                isPasswordValid = false;
            } else if (!digitCase.matcher(passwordEditText.getText().toString().trim()).find()) {
                passwordEditText.setError(getResources().getString(R.string.error_digitCase_Password));
                isPasswordValid = false;
            } else if (passwordEditText.getText().toString().trim().length() < 8) {
                passwordEditText.setError(getResources().getString(R.string.length_error));
                isPasswordValid = false;
            }
        }
        if (confirmPasswordEditText.getText().toString().trim().isEmpty()) {
            confirmPasswordEditText.setError(getResources().getString(R.string.error_Empty_CPassword));
            isPasswordValid = false;
        } else if (!passwordEditText.getText().toString().trim().equals(confirmPasswordEditText.getText().toString())) {
            confirmPasswordEditText.setError(getResources().getString(R.string.error_Cpassword_Match));
            isPasswordValid = false;
        } else if (passwordEditText.getText().toString().trim().equals(confirmPasswordEditText.getText().toString())) {
            isPasswordValid = true;
        }
        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            String email_txt = emailEditText.getText().toString().trim();
            String password_txt = passwordEditText.getText().toString().trim();
            final Handler handler = new Handler();
        }
        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            String email_txt = emailEditText.getText().toString().trim();
            String password_txt = passwordEditText.getText().toString().trim();
            final Handler handler = new Handler();

            firebaseAuth.createUserWithEmailAndPassword(email_txt, password_txt)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                User userInfo = new User(firebaseAuth.getUid(), nameEditText.getText().toString().trim(), email_txt, mobileEditText.getText().toString().trim());
                                new FirestoreClass().registerUser(SignUpActivity.this, userInfo);
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            firebaseAuth.signOut();
                                            Intent signinActivity = new Intent(SignUpActivity.this, LoginScreen.class);
                                            SignUpActivity.this.startActivity(signinActivity);
                                            finish();
                                            //for (int i=0; i < 3; i++)
                                            //{
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Email Send! Please check your Email Address to get Verified.", Toast.LENGTH_SHORT).show();
                                            //}
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Failed! Unable to send Email.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }
}