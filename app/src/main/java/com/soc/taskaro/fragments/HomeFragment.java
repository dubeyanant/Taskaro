package com.soc.taskaro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    HomeFragmentAdapter homeFragmentAdapter;
    private ArrayList<TasksPojo> HomeArrayList;
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
        homeFragmentAdapter = new HomeFragmentAdapter(getContext(), HomeArrayList);
        homeRecyclerView.setAdapter(homeFragmentAdapter);
        homeFragmentAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        HomeArrayList = new ArrayList<>();
        homeHeading = new String[]{
                "Task 1",
                "Task 2",
                "Task 3",
                "Task 4",
                "Task 5",
                "Task 6",
                "Task 7",
                "Task 8"

        };

        homeDescription = new String[]{
                "Task 1 Description",
                "Task 2 Description",
                "Task 3 Description",
                "Task 4 Description",
                "Task 5 Description",
                "Task 6 Description",
                "Task 7 Description",
                "Task 8 Description"
        };
        for (int i = 0; i < homeHeading.length; i++) {
            TasksPojo tasksPojo = new TasksPojo(homeHeading[i], homeDescription[i]);
            HomeArrayList.add(tasksPojo);
        }
}
}