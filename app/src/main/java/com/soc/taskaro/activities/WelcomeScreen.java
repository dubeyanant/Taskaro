package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;

public class WelcomeScreen extends AppCompatActivity {

    Button welcomeScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeScreenButton = (Button) findViewById(R.id.welcomeScreenButton);

        welcomeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OnboardingScreen.class);
                startActivity(intent);
            }
        });
    }
}