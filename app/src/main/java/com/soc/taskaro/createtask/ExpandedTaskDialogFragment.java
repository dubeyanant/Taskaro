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
    TextView taskTitleTextView, taskDescriptionTextView, taskNotificationDescriptionTextView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_expanded_task, null);

        recyclerSubTask = view.findViewById(R.id.recycler_subtask);
        taskTitleTextView = view.findViewById(R.id.taskTitleTextView);
        taskDescriptionTextView = view.findViewById(R.id.taskDescriptionTextView);
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

        if (task.time == null) {
            taskNotificationDescriptionTextView.setVisibility(View.GONE);
        } else {
            if (!(task.daysArrayList == null)) {

                String[] dateDivided = task.date.split("/", 0);

                switch (dateDivided[1]) {
                    case "01":
                        dateDivided[1] = "January";
                        break;
                    case "02":
                        dateDivided[1] = "February";
                        break;
                    case "03":
                        dateDivided[1] = "March";
                        break;
                    case "04":
                        dateDivided[1] = "April";
                        break;
                    case "05":
                        dateDivided[1] = "May";
                        break;
                    case "06":
                        dateDivided[1] = "June";
                        break;
                    case "07":
                        dateDivided[1] = "July";
                        break;
                    case "08":
                        dateDivided[1] = "August";
                        break;
                    case "09":
                        dateDivided[1] = "September";
                        break;
                    case "10":
                        dateDivided[1] = "October";
                        break;
                    case "11":
                        dateDivided[1] = "November";
                        break;
                    case "12":
                        dateDivided[1] = "December";
                        break;
                }
                taskNotificationDescriptionTextView.setText(String.format("%s %s %s %s %s %s", getString(R.string.notification_set_at), task.time, getString(R.string.on_date), dateDivided[0], dateDivided[1], dateDivided[2]));
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }
}