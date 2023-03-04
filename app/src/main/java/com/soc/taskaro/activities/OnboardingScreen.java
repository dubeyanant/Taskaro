package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.soc.taskaro.R;

public class OnboardingScreen extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    Button prev, next, skip;

    TextView[] dots;
    OnboardingAdapter onboardingAdapter;
    ViewPager.OnPageChangeListener viewLisner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position > 0)
                prev.setVisibility(View.VISIBLE);
            else if (position == 0)
                prev.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        prev = findViewById(R.id.onBoard_Prev_Button);
        next = findViewById(R.id.onBoard_Next_Button);
        skip = findViewById(R.id.onBoard_Skip_Button);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0) > 0) {
                    viewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0) < onboardingAdapter.getCount() - 1) {
                    viewPager.setCurrentItem(getItem(1), true);
                } else {
                    Intent i = new Intent(OnboardingScreen.this, LoginScreen.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnboardingScreen.this, LoginScreen.class);
                startActivity(i);
                finish();
            }
        });

        viewPager = findViewById(R.id.onBoarding_viewPager);
        dotsLayout = findViewById(R.id.onBoard_linearLayout1);

        onboardingAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(onboardingAdapter);
        setUpIndicator(0);
        viewPager.addOnPageChangeListener(viewLisner);
    }

    public void setUpIndicator(int position) {
        int count = onboardingAdapter.getCount();
        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.md_theme_light_inverseOnSurface));
            dotsLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.md_theme_dark_shadow));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}