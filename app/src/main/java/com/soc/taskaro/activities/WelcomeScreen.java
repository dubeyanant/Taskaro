package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;

public class WelcomeScreen extends AppCompatActivity {

    Button welcomeScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeScreenButton = findViewById(R.id.welcomeScreenButton);

        welcomeScreenButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), OnboardScreen.class);
            startActivity(intent);
        });
    }
}