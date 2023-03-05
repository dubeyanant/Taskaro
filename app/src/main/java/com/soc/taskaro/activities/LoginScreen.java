package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;

import org.w3c.dom.Text;

public class LoginScreen extends AppCompatActivity {
    TextView txt_go_to_signup, txt_go_to_forget;
    ImageView img_go_to_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        txt_go_to_signup = (TextView)  findViewById(R.id.txt_go_to_signup);
        img_go_to_signup = (ImageView) findViewById(R.id.img_go_to_signup);

        txt_go_to_forget = (TextView)  findViewById(R.id.txt_go_to_forget);

        txt_go_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        img_go_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, SignUpActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        txt_go_to_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, ForgetPassword.class);
                startActivity(i);
            }
        });

    }
}