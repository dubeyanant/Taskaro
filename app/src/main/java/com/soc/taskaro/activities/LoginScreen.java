package com.soc.taskaro.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.soc.taskaro.MainActivity;
import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.User;

public class LoginScreen extends AppCompatActivity {
    TextView txt_go_to_signup, txt_go_to_forget;
    ImageView img_go_to_signup;

    EditText txt_email, txt_password;
    boolean isEmailValid, isPasswordValid;
    CheckBox showPassword;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_go_to_signup = (TextView) findViewById(R.id.txt_go_to_signup);
        img_go_to_signup = (ImageView) findViewById(R.id.img_go_to_signup);
        txt_go_to_forget = (TextView) findViewById(R.id.txt_go_to_forget);
        txt_go_to_signup = (TextView) findViewById(R.id.txt_go_to_signup);
        btn_login = (Button) findViewById(R.id.btn_login);
        showPassword = (CheckBox) findViewById(R.id.chk_showPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showPassword.isChecked()){
                    txt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    txt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    SetValidation();
                    progressBar.setVisibility(VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Failed! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_go_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        img_go_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        txt_go_to_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, ForgetPassword.class);
                startActivity(i);
            }
        });
    }

    public void SetValidation() {
        if (txt_email.getText().toString().isEmpty()) {
            txt_email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email.getText().toString()).matches()) {
            txt_email.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }

        // Check for a valid password.
        if (txt_password.getText().toString().isEmpty()) {
            txt_password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }


        if (isEmailValid && isPasswordValid) {
            String email_txt = txt_email.getText().toString().trim();
            String password_txt = txt_password.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(email_txt, password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(GONE);
                    if(task.isSuccessful()){
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            try {
                                Thread.sleep(2000);
                            }
                            catch (Exception e){
                                System.out.println(e);
                            }
                            new FirestoreClass().getUsersDetails(LoginScreen.this);

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Login Failed! Your Email ID is not Verified, Check your Email to get verified.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Login Failed! Check your Email ID or Password.", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e);
                }
            });

        }
    }
    public void userLoggedInSuccess(User user){
            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent doneActivity = new Intent(LoginScreen.this, MainActivity.class);
            LoginScreen.this.startActivity(doneActivity);
            finish();

    }
}