package com.soc.taskaro.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;
import com.soc.taskaro.createtask.ExpandedTaskDialogFragment;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.Task;
import com.soc.taskaro.utils.Extras;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.HomeViewHolder> {

    HomeFragment fragment;
    ArrayList<Task> homeArrayList;
    View emptyView;
    HomeViewHolder holder;
    public ScheduleAdapter(HomeFragment fragment, ArrayList<Task> homeArrayList, View emptyView) {
        this.fragment = fragment;
        this.homeArrayList = homeArrayList;
        this.emptyView = emptyView;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(fragment.getContext()).inflate(R.layout.home_tasks_schedule, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        this.holder = holder;
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
                    if (Extras.networkCheck(fragment.getContext())) {
                        fragment.progressDialog = new Extras().showProgressBar(fragment);
                        new FirestoreClass().deleteTask(ScheduleAdapter.this, fragment, task, homeArrayList, temp);
                    } else {
                        Toast.makeText(fragment.getContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    holder.taskHeading.setPaintFlags(holder.taskHeading.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        // Used to click on items in recyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putSerializable("task",  task);


                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                ExpandedTaskDialogFragment expandedTaskDialogFragment = new ExpandedTaskDialogFragment();
                expandedTaskDialogFragment.setArguments(bundle);
                expandedTaskDialogFragment.show(fragmentManager, "ExpandedTaskDialogFragment");
            }
        });
    }
    public void onNoteDeleteSuccess(int temp) {
        ((HomeFragment) fragment).progressDialog.dismiss();
        homeArrayList.remove(temp);
        notifyItemRemoved(temp);
        notifyItemRangeChanged(temp, homeArrayList.size());
        updateEmptyViewVisibility();
        holder.taskHeading.setPaintFlags(holder.taskHeading.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Toast.makeText(fragment.getContext(), "Note deleted successfully...", Toast.LENGTH_SHORT).show();
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