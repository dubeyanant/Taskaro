package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;

public class WelcomeScreen extends AppCompatActivity {

    Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        skipButton = (Button) findViewById(R.id.skipButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}