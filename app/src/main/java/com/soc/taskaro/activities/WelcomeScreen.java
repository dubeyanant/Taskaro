package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;

public class WelcomeScreen extends AppCompatActivity {

    Button skipButton, welcomeScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        skipButton = (Button) findViewById(R.id.skipButton);
        welcomeScreenButton = (Button) findViewById(R.id.welcomeScreenButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginScreen.class);
                startActivity(intent);
            }
        });

        welcomeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OnboardingScreen.class);
                startActivity(intent);
            }
        });
    }
}