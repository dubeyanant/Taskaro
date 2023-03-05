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

public class OnboardAdapter extends PagerAdapter {

    Context context;

    int[] Images = {
            R.drawable.doit,
            R.drawable.schedule,
            R.drawable.delegate,
            R.drawable.delete
    };

    int[] headings = {
            R.string.do_it,
            R.string.schedule_it,
            R.string.delegate_it,
            R.string.delete_it,
    };

    int[] description = {
            R.string.do_it_description,
            R.string.schedule_it_description,
            R.string.delegate_it_description,
            R.string.delete_it_description,
    };

    public OnboardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboard_resource, container, false);

        ImageView slideTitleImage = view.findViewById(R.id.onBoard_RImageView);
        TextView sliderHeading = view.findViewById(R.id.onBoard_RTextView2);
        TextView sliderDescription = view.findViewById(R.id.onBoard_RTextView1);

        slideTitleImage.setImageResource(Images[position]);
        sliderHeading.setText(headings[position]);
        sliderDescription.setText(description[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}