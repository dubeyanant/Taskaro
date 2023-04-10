package com.soc.taskaro.createtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class ExpandedTaskActivity extends AppCompatActivity {

    RecyclerView recyclerSubTask;
    ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
    TextView taskTitleTextView, taskDescriptionTextView, taskNotificationTextView, taskNotificationDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_task);

        recyclerSubTask = findViewById(R.id.recycler_subtask);
        taskTitleTextView = findViewById(R.id.taskTitleTextView);
        taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView);
        taskNotificationTextView = findViewById(R.id.taskNotificationTextView);
        taskNotificationDescriptionTextView = findViewById(R.id.taskNotificationDescriptionTextView);

        // Executed when called by Main Notes Screen
        Intent intent = getIntent();
        String Title = intent.getStringExtra("title");
        String Description = intent.getStringExtra("description");
        if (Description.equals("")) {
            taskDescriptionTextView.setVisibility(View.GONE);
        }
        taskTitleTextView.setText(Title);
        taskDescriptionTextView.setText(Description);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerSubTask.setLayoutManager(layoutManager);

        subTaskArrayList = (ArrayList<SubTask>) getIntent().getExtras().getSerializable("list");
        recyclerSubTask.setAdapter(new SubTaskAdapter(subTaskArrayList));
    }
}