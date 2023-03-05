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

public class OnboardScreen extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    Button prev, next, skip;
    TextView[] dots;
    OnboardAdapter onboardAdapter;

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageSelected(int position) {
            prev.setVisibility(View.INVISIBLE);
            if (position != 0) prev.setVisibility(View.VISIBLE);

            if (position == (onboardAdapter.getCount() - 1)) skip.setVisibility(View.INVISIBLE);
            else skip.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        prev = findViewById(R.id.onBoard_Prev_Button);
        next = findViewById(R.id.onBoard_Next_Button);
        skip = findViewById(R.id.onBoard_Skip_Button);

        prev.setOnClickListener(view -> {
            if (getItem(0) > 0) {
                viewPager.setCurrentItem(getItem(-1), true);
            }
        });

        next.setOnClickListener(view -> {
            if (getItem(0) < onboardAdapter.getCount() - 1) {
                viewPager.setCurrentItem(getItem(1), true);
            } else {
                Intent i = new Intent(OnboardScreen.this, LoginScreen.class);
                startActivity(i);
                finish();
            }
        });

        skip.setOnClickListener(view -> {
            Intent i = new Intent(OnboardScreen.this, LoginScreen.class);
            startActivity(i);
            finish();
        });

        viewPager = findViewById(R.id.onBoarding_viewPager);
        dotsLayout = findViewById(R.id.onBoard_linearLayout1);

        onboardAdapter = new OnboardAdapter(this);
        viewPager.setAdapter(onboardAdapter);
        setUpIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpIndicator(int position) {
        int count = onboardAdapter.getCount();
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