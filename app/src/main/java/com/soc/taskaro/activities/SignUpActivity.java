package com.soc.taskaro.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.User;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    ImageView img_go_to_login;
    TextView txt_go_to_login;

    EditText name, email, phone, password, cpassword;
    Button btn_signup;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    ProgressBar progressBar;
    CheckBox showPassword;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_go_to_login = (TextView) findViewById(R.id.txt_back);
        img_go_to_login = (ImageView) findViewById(R.id.img_back);

        name = (EditText) findViewById(R.id.txt_name);
        email = (EditText) findViewById(R.id.txt_email);
        phone = (EditText) findViewById(R.id.txt_mobile);
        password = (EditText) findViewById(R.id.txt_password);
        cpassword = (EditText) findViewById(R.id.txt_cpassword);
        btn_signup = (Button) findViewById(R.id.btn_register);
        showPassword = (CheckBox) findViewById(R.id.showPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.md_theme_light_secondary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
        }


        img_go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        txt_go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        //To display text from the fields of password
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showPassword.isChecked()){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //Signup Button logic
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean have_WIFI = false;
                boolean have_MobileData = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                for(NetworkInfo info:networkInfos){
                    if(info.getTypeName().equalsIgnoreCase("WIFI"))
                        if(info.isConnected())
                            have_WIFI = true;
                    if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                        if(info.isConnected())
                            have_MobileData = true;
                }
                if(have_MobileData || have_WIFI){
                    setValidation();
                    progressBar.setVisibility(VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Authentication Failed! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setValidation(){
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digitCase = Pattern.compile("[0-9]");
        if (name.getText().toString().trim().isEmpty()) {
            name.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        }
        if(!name.getText().toString().trim().isEmpty()) {
            isNameValid = true;
        }
        if (email.getText().toString().trim().isEmpty()) {
            email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }
        if (phone.getText().toString().trim().isEmpty()) {
            phone.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else if (phone.getText().toString().trim().length() != 10) {
            phone.setError(getResources().getString(R.string.error_invalid_phone));
            isPhoneValid = false;
        } else {
            isPhoneValid = true;
        }
        if (password.getText().toString().trim().isEmpty()) {
            password.setError(getResources().getString(R.string.error_Empty_Password));
            isPasswordValid = false;
        } else {
            if (!lowerCase.matcher(password.getText().toString().trim()).find()) {
                password.setError(getResources().getString(R.string.error_LowerCase_Password));
                isPasswordValid = false;
            } else if (!digitCase.matcher(password.getText().toString().trim()).find()) {
                password.setError(getResources().getString(R.string.error_digitCase_Password));
                isPasswordValid = false;
            } else if (password.getText().toString().trim().length() < 8) {
                password.setError(getResources().getString(R.string.length_error));
                isPasswordValid = false;
            }
        }
        if (cpassword.getText().toString().trim().isEmpty()) {
            cpassword.setError(getResources().getString(R.string.error_Empty_CPassword));
            isPasswordValid = false;
        } else if (!password.getText().toString().trim().equals(cpassword.getText().toString())) {
            cpassword.setError(getResources().getString(R.string.error_Cpassword_Match));
            isPasswordValid = false;
        } else if (password.getText().toString().trim().equals(cpassword.getText().toString())) {
            isPasswordValid = true;
        }
        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            String email_txt = email.getText().toString().trim();
            String password_txt = password.getText().toString().trim();
            final Handler handler = new Handler();
        }
        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            String email_txt = email.getText().toString().trim();
            String password_txt = password.getText().toString().trim();
            final Handler handler = new Handler();

            firebaseAuth.createUserWithEmailAndPassword(email_txt, password_txt)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(GONE);

                            if (task.isSuccessful()) {
                                User userInfo = new User(firebaseAuth.getUid(), name.getText().toString().trim(), email_txt, phone.getText().toString().trim());
                                new FirestoreClass().registerUser(SignUpActivity.this, userInfo);
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            firebaseAuth.signOut();
                                            Intent signinActivity = new Intent(SignUpActivity.this, LoginScreen.class);
                                            SignUpActivity.this.startActivity(signinActivity);
                                            finish();
                                            //for (int i=0; i < 3; i++)
                                            //{
                                                Toast.makeText(getApplicationContext(), "Email Send! Please check your Email Address to get Verified.", Toast.LENGTH_SHORT).show();
                                            //}
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Failed! Unable to send Email.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}