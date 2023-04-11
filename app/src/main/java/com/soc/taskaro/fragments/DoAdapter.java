package com.soc.taskaro.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;
import com.soc.taskaro.createtask.ExpandedTaskDialogFragment;
import com.soc.taskaro.models.Task;

import java.util.ArrayList;

public class DoAdapter extends RecyclerView.Adapter<DoAdapter.HomeViewHolder> {

    Context context;
    ArrayList<Task> homeArrayList;
    View emptyView;
//    LayoutInflater inflater = LayoutInflater.from(emptyView.getContext());

    public DoAdapter(Context context, ArrayList<Task> homeArrayList, View emptyView) {
        this.context = context;
        this.homeArrayList = homeArrayList;
        this.emptyView = emptyView;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_tasks_do, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        Task task = homeArrayList.get(position);
        holder.taskHeading.setText(task.getTitle());
        if (task.getDescription().equals("")) {
            holder.taskDescription.setVisibility(View.GONE);
        } else {
            holder.taskDescription.setText(task.getDescription());
        }

        // Removes views if clicked on delete button
        int temp = holder.getAdapterPosition();
        holder.taskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.taskCheckBox.isChecked()) {
                    holder.taskHeading.setPaintFlags(holder.taskHeading.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.taskHeading.setPaintFlags(holder.taskHeading.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }

                homeArrayList.remove(temp);
                notifyItemRemoved(temp);
                notifyItemRangeChanged(temp, homeArrayList.size());
                updateEmptyViewVisibility();
            }
        });

        // Used to click on items in recyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FIXME: Open as Intent -- Option 1
//                Intent intent = new Intent(v.getContext(), ExpandedTaskActivity.class);
//                intent.putExtra("title", task.getTitle());
//                intent.putExtra("description", task.getDescription());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("list", task.getSubTasks());
//                intent.putExtras(bundle);
//                v.getContext().startActivity(intent);
//
//                FIXME: Open as DialogFragment -- Option 2
                Bundle bundle = new Bundle();
                bundle.putString("title", task.getTitle());
                bundle.putString("description", task.getDescription());
                bundle.putSerializable("list", task.getSubTasks());

                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                ExpandedTaskDialogFragment expandedTaskDialogFragment = new ExpandedTaskDialogFragment();
                expandedTaskDialogFragment.setArguments(bundle);
                expandedTaskDialogFragment.show(fragmentManager, "ExpandedTaskDialogFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeArrayList.size();
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        updateEmptyViewVisibility();
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        updateEmptyViewVisibility();
    }

    private void updateEmptyViewVisibility() {
        if (emptyView != null) {
            emptyView.setVisibility(getItemCount() == 0 ? View.GONE : View.VISIBLE);
        }
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView taskHeading, taskDescription;
        CheckBox taskCheckBox;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            taskHeading = itemView.findViewById(R.id.task_title);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBok);
        }
    }
}