package com.soc.taskaro.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.HomeViewHolder> {

    Context context;
    ArrayList<TasksPojo> homeArrayList;
    View emptyView;

    public HomeFragmentAdapter(Context context, ArrayList<TasksPojo> homeArrayList, View emptyView) {
        this.context = context;
        this.homeArrayList = homeArrayList;
        this.emptyView = emptyView;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_tasks, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        TasksPojo tasksPojo = homeArrayList.get(position);
        holder.taskHeading.setText(tasksPojo.taskHeading);
        if (tasksPojo.taskDescription.equals("")) {
            holder.taskDescription.setVisibility(View.GONE);
        } else {
            holder.taskDescription.setText(tasksPojo.taskDescription);
        }
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