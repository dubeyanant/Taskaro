package com.soc.taskaro.createtask;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.fragments.DelegateAdapter;
import com.soc.taskaro.fragments.HomeFragment;
import com.soc.taskaro.models.SubTask;
import com.soc.taskaro.models.Task;
import com.soc.taskaro.utils.Constants;
import com.soc.taskaro.utils.Extras;

import java.util.ArrayList;
import java.util.HashMap;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskView> {

    ArrayList<SubTask> subTasksArrayList;
    boolean isCheckedCircleCheckBox = false;
    Fragment fragment;
    Task task;

    public SubTaskAdapter(ExpandedTaskDialogFragment fragment, ArrayList<SubTask> subTasksArrayList, Task task) {
        this.fragment = fragment;
        this.subTasksArrayList = subTasksArrayList;
        this.task = task;
    }

    @NonNull
    @Override
    public SubTaskView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_task, parent, false);
        return new SubTaskView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskView holder, @SuppressLint("RecyclerView") int position) {
        SubTask subTask = subTasksArrayList.get(position);

        if (task.getSubTaskStateList().get(position) == 1) {
            System.out.println(task.getSubTaskStateList().get(position)+"!!!!!!!");
            holder.subTaskTestTextView.setText(subTask.getSubTask());
            holder.subTaskTestTextView.setPaintFlags(holder.subTaskTestTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.subTaskCheckBox.setImageResource(R.drawable.ic_check_circle);
            isCheckedCircleCheckBox = true;
        } else {
            System.out.println(task.getSubTaskStateList().get(position)+"@@@@@@@");
            holder.subTaskTestTextView.setText(subTask.getSubTask());
            holder.subTaskTestTextView.setPaintFlags(holder.subTaskTestTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.subTaskCheckBox.setImageResource(R.drawable.ic_check_circle_outline);
            isCheckedCircleCheckBox = false;
        }

        // Sub-task check
        int temp = holder.getAdapterPosition();
        holder.subTaskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheckedCircleCheckBox) {
                    task.getSubTaskStateList().set(position, 1);
                    HashMap<String, Object> taskHashMap = new HashMap<>();
                    taskHashMap.put(Constants.Extra_TASK_ID, task.getTask_id());
                    taskHashMap.put(Constants.Extra_SUBTASK_STATE, task.getSubTaskStateList());
                    ((ExpandedTaskDialogFragment) fragment).progressDialog = new Extras().showProgressBar(fragment);
                    new FirestoreClass().updateTaskDetails(SubTaskAdapter.this, fragment, taskHashMap, holder);
                } else {
                    task.getSubTaskStateList().set(position, 0);
                    HashMap<String, Object> taskHashMap = new HashMap<>();
                    taskHashMap.put(Constants.Extra_TASK_ID, task.getTask_id());
                    taskHashMap.put(Constants.Extra_SUBTASK_STATE, task.getSubTaskStateList());
                    ((ExpandedTaskDialogFragment) fragment).progressDialog = new Extras().showProgressBar(fragment);
                    new FirestoreClass().updateTaskDetails(SubTaskAdapter.this, fragment, taskHashMap, holder);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return subTasksArrayList.size();
    }

    public void onTaskUpdateSuccess(SubTaskView holder) {
        ((ExpandedTaskDialogFragment) fragment).progressDialog.dismiss();
        if (!isCheckedCircleCheckBox) {
            holder.subTaskTestTextView.setPaintFlags(holder.subTaskTestTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            isCheckedCircleCheckBox = true;
            holder.subTaskCheckBox.setImageResource(R.drawable.ic_check_circle);
        } else {
            ((ExpandedTaskDialogFragment) fragment).progressDialog.dismiss();
            holder.subTaskTestTextView.setPaintFlags(holder.subTaskTestTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            isCheckedCircleCheckBox = false;
            holder.subTaskCheckBox.setImageResource(R.drawable.ic_check_circle_outline);
        }
    }
    public static class SubTaskView extends RecyclerView.ViewHolder {
        TextView subTaskTestTextView;
        ImageButton subTaskCheckBox;

        public SubTaskView(@NonNull View itemView) {
            super(itemView);
            subTaskTestTextView = itemView.findViewById(R.id.subTaskTestTextView);
            subTaskCheckBox = itemView.findViewById(R.id.subTaskCheckBox);
        }
    }
}