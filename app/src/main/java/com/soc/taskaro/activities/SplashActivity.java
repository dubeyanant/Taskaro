package com.soc.taskaro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.soc.taskaro.MainActivity;
import com.soc.taskaro.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent i=new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Intent i=new Intent(SplashActivity.this, WelcomeScreen.class);
            startActivity(i);
            finish();
        }
    }
}