package com.soc.taskaro.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.soc.taskaro.R;

public class ForgetPassword extends AppCompatActivity {

    Button btn_send;
    TextView goToLoginTextView;
    ProgressBar progressBar;
    EditText txt_email;
    boolean isEmailValid;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_send = findViewById(R.id.btn_send);
        goToLoginTextView = findViewById(R.id.goToLoginTextView);
        progressBar = findViewById(R.id.progressBar);
        txt_email = findViewById(R.id.txt_email);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_send.setOnClickListener(new View.OnClickListener() {
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
                    progressBar.setVisibility(VISIBLE);
                    SetValidation();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
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
        } else {
            isEmailValid = true;
        }

        if (isEmailValid) {
            String email_txt = txt_email.getText().toString().trim();
            firebaseAuth.sendPasswordResetEmail(email_txt).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressBar.setVisibility(GONE);
                    if (task.isSuccessful()) {
                        TextView txt_goToLogin;
                        Dialog dialog = new Dialog(ForgetPassword.this);
                        dialog.setContentView(R.layout.check_email_pop);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                        txt_goToLogin = dialog.findViewById(R.id.txt_goToLogin);
                        txt_goToLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(ForgetPassword.this, LoginScreen.class);
                                startActivity(i);
                            }
                        });
                        dialog.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed! You are not registered with this Email ID", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}