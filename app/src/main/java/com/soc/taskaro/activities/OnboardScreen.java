package com.soc.taskaro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
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
    Button next, skip;
    TextView[] dots;
    OnboardAdapter onboardAdapter;
    View tempView;

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageSelected(int position) {

<<<<<<< HEAD
            if (position != 0)
                prev.setVisibility(View.VISIBLE);
            else
                prev.setVisibility(View.INVISIBLE);

            if (position == (onboardAdapter.getCount() - 1)) skip.setVisibility(View.INVISIBLE);
            else skip.setVisibility(View.VISIBLE);
=======
            if (position == (onboardAdapter.getCount() - 1)) {
                skip.setVisibility(View.GONE);
                next.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                tempView.setVisibility(View.GONE);
                next.setText(R.string.lets_login);
            } else {
                skip.setVisibility(View.VISIBLE);
                next.setLayoutParams(new LinearLayout.LayoutParams(inDP(170), LinearLayout.LayoutParams.WRAP_CONTENT));
                tempView.setVisibility(View.VISIBLE);
                next.setText(R.string.next);
            }
>>>>>>> bc5fee814885286d2a98975f12222123b6d5ff9e

            setUpIndicator(getItem(0));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        next = findViewById(R.id.onBoard_Next_Button);
        skip = findViewById(R.id.onBoard_Skip_Button);
        tempView = findViewById(R.id.tempView);

        next.setOnClickListener(view -> {
            if (getItem(0) < onboardAdapter.getCount() - 1) {
                viewPager.setCurrentItem(getItem(1), true);
            } else {
                Intent i = new Intent(OnboardScreen.this, LoginScreen.class);
                startActivity(i);
                finish();
            }
            setUpIndicator(getItem(0));
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
        dots = new TextView[onboardAdapter.getCount()];
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

    /**
     * @param pixels takes size in pixels
     * @return size in DP
     */
    private int inDP(int pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, getResources().getDisplayMetrics());
    }
}