package com.soc.taskaro.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.soc.taskaro.R;

public class OnboardingAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    int Images[] = {
            R.drawable.onboarding_screen_1_image,
            R.drawable.onboarding_screen_2_image,
            R.drawable.onboarding_screen_3_image,
            R.drawable.onboarding_screen_4_image
    };

    int headings[] = {
            R.string.do_it,
            R.string.schedule_it,
            R.string.delegate_it,
            R.string.delete_it,
    };

    int description[] = {
            R.string.do_it_description,
            R.string.schedule_it_description,
            R.string.delegate_it_description,
            R.string.delete_it_description,
    };

    public OnboardingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboard_resource, container, false);

        ImageView slideTitleImage = (ImageView) view.findViewById(R.id.onBoard_RImageView);
        TextView sliderHeading = (TextView) view.findViewById(R.id.onBoard_RTextView2);
        TextView sliderdescription = (TextView) view.findViewById(R.id.onBoard_RTextView1);

        slideTitleImage.setImageResource(Images[position]);
        sliderHeading.setText(headings[position]);
        sliderdescription.setText(description[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}