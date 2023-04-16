package com.soc.taskaro.createtask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.SubTask;
import com.soc.taskaro.utils.Extras;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity {

    public Button btn_saveCreateTask;
    Boolean[] dayClickCountArray = {false, false, false, false, false, false, false};
    TextView dateTextView, timeTextView;
    TextView[] days;
    LinearLayout addTaskButtonLL, subTaskLL, addNotificationButtonLL, notificationLL;
    EditText titleEditText, descriptionEditText;
    ImageButton closeNotification;
    boolean isNotificationEnabled = false;

    boolean isTitleValid;
    ProgressDialog progressDialog;
    com.google.android.material.materialswitch.MaterialSwitch importantSwitch, urgentSwitch;

    ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
    ArrayList<Integer> subTaskArrayStateList = new ArrayList<>();
    ViewGroup.MarginLayoutParams addTaskButtonLLParams; // Helps in changing margin of the particular layout viewGroup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        dateTextView = findViewById(R.id.time);
        timeTextView = findViewById(R.id.date);
        days = new TextView[]{
                findViewById(R.id.sun),
                findViewById(R.id.mon),
                findViewById(R.id.tue),
                findViewById(R.id.wed),
                findViewById(R.id.thu),
                findViewById(R.id.fri),
                findViewById(R.id.sat)
        };
        addTaskButtonLL = findViewById(R.id.addTaskButtonLL);
        subTaskLL = findViewById(R.id.subTaskLL);
        btn_saveCreateTask = findViewById(R.id.saveCreateTaskButton);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addNotificationButtonLL = findViewById(R.id.addNotificationButtonLL);
        notificationLL = findViewById(R.id.notificationLL);
        closeNotification = findViewById(R.id.closeNotification);
        importantSwitch = findViewById(R.id.importantSwitch);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addTaskButtonLLParams = (ViewGroup.MarginLayoutParams) addTaskButtonLL.getLayoutParams();


        // Date Picker
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
        for (int i = 0; i < days.length; i++) {
            final int index = i;
            days[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dayClickCountArray[index] == false) {
                        days[index].setBackground(getResources().getDrawable(R.drawable.style_create_task_circle_dark));
                        dayClickCountArray[index] = true;
                    } else {
                        days[index].setBackground(getResources().getDrawable(R.drawable.style_create_task_circle));
                        dayClickCountArray[index] = false;
                    }
                }
            });
        }

        // Add sub-task
        addTaskButtonLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View subTaskView = getLayoutInflater().inflate(R.layout.sub_task, null, false);
                ImageView imageView = subTaskView.findViewById(R.id.imageViewRemove);

                subTaskLL.addView(subTaskView);

                Animation slideInLeft = AnimationUtils.loadAnimation(CreateTaskActivity.this, R.anim.slide_in_left);
                subTaskView.startAnimation(slideInLeft);

                addTaskButtonLLParams.setMargins(0, inDP(12), 0, inDP(7));

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subTaskLL.removeView(subTaskView);
                    }
                });
            }
        });

        // Return to MainActivity
        btn_saveCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new Extras().showProgressBar(CreateTaskActivity.this);
                checkIfValidAndRead();
                setValidation();
            }
        });

        // Add notification
        addNotificationButtonLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotificationButtonLL.setVisibility(View.GONE);
                notificationLL.setVisibility(View.VISIBLE);
                isNotificationEnabled = true;
            }
        });

        // Remove notification
        closeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotificationButtonLL.setVisibility(View.VISIBLE);
                notificationLL.setVisibility(View.GONE);
                isNotificationEnabled = false;
            }
        });
    }


    /**
     * @param pixels takes size in pixels
     * @return size in DP
     */
    private int inDP(int pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, getResources().getDisplayMetrics());
    }

    // This method reads the dynamic sub-task editText
    private boolean checkIfValidAndRead() {
        subTaskArrayList.clear();
        boolean result = true;

        for (int i = 0; i < subTaskLL.getChildCount(); i++) {
            View subTaskView = subTaskLL.getChildAt(i);

            EditText editText = subTaskView.findViewById(R.id.editTextSubTask);

            SubTask subTask = new SubTask();

            if (!editText.getText().toString().equals("")) {
                subTask.setSubTask(editText.getText().toString());
            } else {
                result = false;
                break;
            }

            subTaskArrayList.add(subTask);
            subTaskArrayStateList.add(0);
        }

        if (subTaskArrayList.size() == 0) {
            result = false;
            Toast.makeText(this, "Enter sub-task", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void setValidation() {
        if (titleEditText.getText().toString().trim().isEmpty()) {
            progressDialog.dismiss();
            titleEditText.setError(getResources().getString(R.string.empty_field_error));
        } else {
            isTitleValid = true;
        }

        if (isTitleValid) {
            if (isNotificationEnabled == false) {
                new FirestoreClass().uploadTaskDetails(this, titleEditText.getText().toString().trim(), descriptionEditText.getText().toString().trim(), importantSwitch.isChecked(), urgentSwitch.isChecked(), subTaskArrayList, subTaskArrayStateList, isNotificationEnabled);
            } else {

                new FirestoreClass().uploadTaskDetails(this, titleEditText.getText().toString().trim(), descriptionEditText.getText().toString().trim(), importantSwitch.isChecked(), urgentSwitch.isChecked(), subTaskArrayList, subTaskArrayStateList, isNotificationEnabled, timeTextView.getText().toString().trim(), dateTextView.getText().toString().trim(), dayClickCountArray);
            }
        }
    }

    public void uploadDataSuccess() {
        progressDialog.dismiss();
        Toast.makeText(this, "Data uploaded successfully...", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }
}