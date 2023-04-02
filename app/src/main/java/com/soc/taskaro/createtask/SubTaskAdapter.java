package com.soc.taskaro.createtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskView> {

    ArrayList<SubTask> subTasksArrayList;

    public SubTaskAdapter(ArrayList<SubTask> subTasksArrayList) {
        this.subTasksArrayList = subTasksArrayList;
    }

    @NonNull
    @Override
    public SubTaskView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_task, parent, false);
        return new SubTaskView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskView holder, int position) {
        SubTask subTask = subTasksArrayList.get(position);
        holder.subTaskTestTextView.setText(subTask.getSubTask());
    }

    @Override
    public int getItemCount() {
        return subTasksArrayList.size();
    }

    public static class SubTaskView extends RecyclerView.ViewHolder {
        TextView subTaskTestTextView;

        public SubTaskView(@NonNull View itemView) {
            super(itemView);
            subTaskTestTextView = itemView.findViewById(R.id.subTaskTestTextView);
        }
    }
}