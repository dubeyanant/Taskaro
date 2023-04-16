package com.soc.taskaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.soc.taskaro.MainActivity;
import com.soc.taskaro.R;
import com.soc.taskaro.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    SharedPreferences sharedPreferences = SplashActivity.this.getSharedPreferences(Constants.TASKARO_PREFERENCES, Context.MODE_PRIVATE);
                    if (sharedPreferences.getBoolean(Constants.FIRST_ENTRY_OF_USER, true) == true) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Constants.FIRST_ENTRY_OF_USER, false);
                        editor.apply();
                        Intent i = new Intent(SplashActivity.this, WelcomeScreenActivity.class);
                        startActivity(i);
                        finish();
                    } else if (sharedPreferences.getBoolean(Constants.FIRST_ENTRY_OF_USER, true) == false) {
                        Intent i = new Intent(SplashActivity.this, LoginScreenActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        }, SPLASH_SCREEN_TIME_OUT);


    }
}