package com.soc.taskaro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;
import com.soc.taskaro.utils.Constants;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    HomeFragmentAdapter homeFragmentAdapter;
    private ArrayList<TasksPojo> doArrayList;
    private ArrayList<TasksPojo> scheduleArrayList;
    private ArrayList<TasksPojo> delegateArrayList;
    private ArrayList<TasksPojo> deleteArrayList;

    private String[] homeDescription;
    private String[] homeHeading;
    private RecyclerView homeRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        homeRecyclerView = view.findViewById(R.id.Home_RecyclerView);
        homeRecyclerView.setHasFixedSize(true);
        homeFragmentAdapter = new HomeFragmentAdapter(getContext(), doArrayList, homeRecyclerView);
        homeRecyclerView.setAdapter(homeFragmentAdapter);
        homeFragmentAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        homeRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void dataInitialize() {
        doArrayList = new ArrayList<>();
        scheduleArrayList = new ArrayList<>();
        delegateArrayList = new ArrayList<>();
        deleteArrayList = new ArrayList<>();

        homeHeading = new String[]{
                "Task 1",
                "Task 2",
                "Task 3",
                "Task 4",
                "Task 5"
        };

        homeDescription = new String[]{
                "Task 1 Description",
                "Task 2 Description",
                "Task 3 Description",
                "Task 4 Description",
                "Task 5 Description"
        };

        int[] taskLevel = new int[]{
                Constants.SCHEDULE,
                Constants.SCHEDULE,
                Constants.DELEGATE,
                Constants.DELETE,
                Constants.DO
        };

        for (int i = 0; i < homeHeading.length; i++) {
            switch (taskLevel[i]) {
                case Constants.DO:
                    TasksPojo doTaskPojo = new TasksPojo(homeHeading[i], homeDescription[i]);
                    doArrayList.add(doTaskPojo);
                    break;

                case Constants.SCHEDULE:
                    TasksPojo scheduleTaskPojo = new TasksPojo(homeHeading[i], homeDescription[i]);
                    scheduleArrayList.add(scheduleTaskPojo);
                    break;

                case Constants.DELEGATE:
                    TasksPojo delegateTaskPojo = new TasksPojo(homeHeading[i], homeDescription[i]);
                    delegateArrayList.add(delegateTaskPojo);
                    break;

                case Constants.DELETE:
                    TasksPojo deleteTaskPojo = new TasksPojo(homeHeading[i], homeDescription[i]);
                    deleteArrayList.add(deleteTaskPojo);
                    break;
            }
        }
    }
}