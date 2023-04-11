package com.soc.taskaro.createtask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import com.soc.taskaro.models.Task;


import java.util.ArrayList;

public class ExpandedTaskDialogFragment extends DialogFragment {

    RecyclerView recyclerSubTask;
    ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
    TextView taskTitleTextView, taskDescriptionTextView, taskNotificationTextView, taskNotificationDescriptionTextView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_expanded_task, null);

        recyclerSubTask = view.findViewById(R.id.recycler_subtask);
        taskTitleTextView = view.findViewById(R.id.taskTitleTextView);
        taskDescriptionTextView = view.findViewById(R.id.taskDescriptionTextView);
        taskNotificationTextView = view.findViewById(R.id.taskNotificationTextView);
        taskNotificationDescriptionTextView = view.findViewById(R.id.taskNotificationDescriptionTextView);

        Bundle bundle = getArguments();

        Task task = (Task) bundle.getSerializable("task");
        String Title = task.getTitle();
        String Description = task.getDescription();
        subTaskArrayList = (ArrayList<SubTask>) task.getSubTasks();


        if (Description.equals("")) {
            taskDescriptionTextView.setVisibility(View.GONE);
        }
        taskTitleTextView.setText(Title);
        taskDescriptionTextView.setText(Description);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerSubTask.setLayoutManager(layoutManager);

        recyclerSubTask.setAdapter(new SubTaskAdapter(subTaskArrayList));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }
}