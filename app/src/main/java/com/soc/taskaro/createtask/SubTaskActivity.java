package com.soc.taskaro.createtask;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class SubTaskActivity extends AppCompatActivity {

    RecyclerView recyclerSubTask;
    ArrayList<SubTask> subTaskArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);

        recyclerSubTask = findViewById(R.id.recycler_subtask);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerSubTask.setLayoutManager(layoutManager);

        subTaskArrayList = (ArrayList<SubTask>) getIntent().getExtras().getSerializable("list");
        recyclerSubTask.setAdapter(new SubTaskAdapter(subTaskArrayList));
    }
}