package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;

public class SignUpActivity extends AppCompatActivity {
    ImageView img_go_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        img_go_to_login = findViewById(R.id.img_go_to_login);

        img_go_to_login.setOnClickListener(view -> {
            Intent i = new Intent(SignUpActivity.this, LoginScreen.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}