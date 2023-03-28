package com.soc.taskaro;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // Date Picker
        TextView dateTextView = (TextView) findViewById(R.id.date);
        dateTextView.setText(MessageFormat.format("{0}", new SimpleDateFormat("dd/MM/yyy", Locale.getDefault()).format(new Date())));
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault()).format(new Date(selection));
                        dateTextView.setText(MessageFormat.format("{0}", date));
                    }
                });

                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        // Time Picker
        TextView timeTextView = (TextView) findViewById(R.id.time);
        timeTextView.setText(MessageFormat.format("{0}", new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date())));
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(12).setMinute(10).setTitleText("Select time").build();

                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = materialTimePicker.getHour();
                        int minute = materialTimePicker.getMinute();
                        String amPm = (hour < 12) ? "am" : "pm";
                        hour = (hour > 12) ? hour - 12 : hour;
                        hour = (hour == 0) ? 12 : hour;
                        String time = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, amPm);
                        timeTextView.setText(time);
                    }
                });

                materialTimePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        // Days Picker
        TextView[] days = new TextView[]{
                findViewById(R.id.sun),
                findViewById(R.id.mon),
                findViewById(R.id.tue),
                findViewById(R.id.wed),
                findViewById(R.id.thu),
                findViewById(R.id.fri),
                findViewById(R.id.sat)
        };
        final int[] dayClickCount = {0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < days.length; i++) {
            final int index = i;
            days[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dayClickCount[index] == 0) {
                        days[index].setBackground(getResources().getDrawable(R.drawable.style_create_task_circle_dark));
                        dayClickCount[index] = 1;
                    } else {
                        days[index].setBackground(getResources().getDrawable(R.drawable.style_create_task_circle));
                        dayClickCount[index] = 0;
                    }
                }
            });
        }

        // Add sub-task
        LinearLayout addTaskButtonLL = (LinearLayout) findViewById(R.id.addTaskButtonLL);
        LinearLayout subTaskLL = (LinearLayout) findViewById(R.id.subTaskLL);

        addTaskButtonLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View subTaskView = getLayoutInflater().inflate(R.layout.sub_task, null, false);
                EditText editText = (EditText) findViewById(R.id.editTextSubTask);
                ImageView imageView = (ImageView) subTaskView.findViewById(R.id.imageViewRemove);

                subTaskLL.addView(subTaskView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subTaskLL.removeView(subTaskView);
                    }
                });
            }
        });
    }
}