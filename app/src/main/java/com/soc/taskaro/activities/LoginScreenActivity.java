package com.soc.taskaro.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class LoginScreenActivity extends AppCompatActivity {

    TextView goToForgotTextView, loginTagLine;
    EditText emailEditText, passwordEditText;
    boolean passwordVisible;
    boolean isEmailValid, isPasswordValid;
    LinearLayout goToSignupLL;
    ProgressDialog progressDialog;
    Button loginBtn;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("ClickableViewAccessibility")
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
        loginTagLine = findViewById(R.id.loginTagLine);

        // This fills the textView with gradient
        TextPaint paint = loginTagLine.getPaint();
        float width = paint.measureText(getString(R.string.login_screen_tag_line));
        Shader textShader = new LinearGradient(0, 0, width, loginTagLine.getTextSize(),
                new int[]{
                        getResources().getColor(R.color.md_theme_light_shadow),
                        getResources().getColor(R.color.seed)
                }, null, Shader.TileMode.CLAMP);
        loginTagLine.getPaint().setShader(textShader);

        // Show or hide password
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
                    progressDialog = new Extras().showProgressBar(LoginScreenActivity.this);
                    SetValidation();
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToSignupLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreenActivity.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        goToForgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreenActivity.this, ForgetPasswordActivity.class);
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
                            new FirestoreClass().getUsersDetails(LoginScreenActivity.this);

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
        Intent doneActivity = new Intent(LoginScreenActivity.this, MainActivity.class);
        LoginScreenActivity.this.startActivity(doneActivity);
        finish();

    }
}