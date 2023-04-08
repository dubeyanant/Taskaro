package com.soc.taskaro.createtask;

import android.os.Bundle;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerSubTask.setLayoutManager(layoutManager);

        subTaskArrayList = (ArrayList<SubTask>) getIntent().getExtras().getSerializable("list");
        recyclerSubTask.setAdapter(new SubTaskAdapter(subTaskArrayList));
    }
}