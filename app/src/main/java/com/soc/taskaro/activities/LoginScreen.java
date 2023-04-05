package com.soc.taskaro.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.soc.taskaro.utils.Extras;

public class LoginScreen extends AppCompatActivity {

    TextView goToForgotTextView;
    EditText emailEditText, passwordEditText;
    boolean isEmailValid, isPasswordValid;
    LinearLayout goToSignupLL;
    ProgressDialog progressDialog;
    Button loginBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        goToForgotTextView = (TextView) findViewById(R.id.goToForgotTextView);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        goToSignupLL = findViewById(R.id.goToSignupLL);

//        showPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (showPassword.isChecked()) {
//                    txt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    txt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    progressDialog = new Extras().showProgressBar(LoginScreen.this);
                    SetValidation();
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToSignupLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        goToForgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, ForgetPassword.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void SetValidation() {
        if (emailEditText.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            emailEditText.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            progressDialog.dismiss();
            emailEditText.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }

        // Check for a valid password.
        if (passwordEditText.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            passwordEditText.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }


        if (isEmailValid && isPasswordValid) {
            String email_txt = emailEditText.getText().toString().trim();
            String password_txt = passwordEditText.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(email_txt, password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                            try {
                                Thread.sleep(2000);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            new FirestoreClass().getUsersDetails(LoginScreen.this);

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login Failed! Your Email ID is not Verified, Check your Email to get verified.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
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

    public void userLoggedInSuccess(User user) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
        Intent doneActivity = new Intent(LoginScreen.this, MainActivity.class);
        LoginScreen.this.startActivity(doneActivity);
        finish();

    }
}