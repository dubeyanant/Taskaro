package com.soc.taskaro.createtask;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskView> {

    ArrayList<SubTask> subTasksArrayList;
    boolean isCheckedCircleCheckBox = false;

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

        // Sub-task check
        int temp = holder.getAdapterPosition();
        holder.subTaskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheckedCircleCheckBox) {
                    holder.subTaskTestTextView.setPaintFlags(holder.subTaskTestTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    isCheckedCircleCheckBox = true;
                    holder.subTaskCheckBox.setImageResource(R.drawable.ic_check_circle);
                } else {
                    holder.subTaskTestTextView.setPaintFlags(holder.subTaskTestTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    isCheckedCircleCheckBox = false;
                    holder.subTaskCheckBox.setImageResource(R.drawable.ic_check_circle_outline);
                }

//                subTasksArrayList.remove(temp);
//                notifyItemRemoved(temp);
//                notifyItemRangeChanged(temp, subTasksArrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return subTasksArrayList.size();
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