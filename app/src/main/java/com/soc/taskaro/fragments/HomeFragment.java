package com.soc.taskaro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;
import com.soc.taskaro.utils.Constants;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    DoAdapter doAdapter;
    ScheduleAdapter scheduleAdapter;
    DelegateAdapter delegateAdapter;
    DeleteAdapter deleteAdapter;
    LinearLayout doItLL, scheduleItLL, delegateItLL, deleteItLL;
    private ArrayList<TasksPojo> doArrayList;
    private ArrayList<TasksPojo> scheduleArrayList;
    private ArrayList<TasksPojo> delegateArrayList;
    private ArrayList<TasksPojo> deleteArrayList;
    private String[] homeDescription;
    private String[] homeHeading;
    private RecyclerView doRecyclerView, scheduleRecyclerView, delegateRecyclerView, deleteRecyclerView;

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

        doRecyclerView = view.findViewById(R.id.doRecyclerView);
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView);
        delegateRecyclerView = view.findViewById(R.id.delegateRecyclerView);
        deleteRecyclerView = view.findViewById(R.id.deleteRecyclerView);

        doItLL = view.findViewById(R.id.doItLL);
        scheduleItLL = view.findViewById(R.id.scheduleItLL);
        delegateItLL = view.findViewById(R.id.delegateItLL);
        deleteItLL = view.findViewById(R.id.deleteItLL);

        doRecyclerView.setHasFixedSize(true);
        scheduleRecyclerView.setHasFixedSize(true);
        delegateRecyclerView.setHasFixedSize(true);
        deleteRecyclerView.setHasFixedSize(true);

        doAdapter = new DoAdapter(getContext(), doArrayList, doItLL);
        scheduleAdapter = new ScheduleAdapter(getContext(), scheduleArrayList, scheduleItLL);
        delegateAdapter = new DelegateAdapter(getContext(), delegateArrayList, delegateItLL);
        deleteAdapter = new DeleteAdapter(getContext(), deleteArrayList, deleteItLL);

        doRecyclerView.setAdapter(doAdapter);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
        delegateRecyclerView.setAdapter(delegateAdapter);
        deleteRecyclerView.setAdapter(deleteAdapter);

        doAdapter.notifyDataSetChanged();
        scheduleAdapter.notifyDataSetChanged();
        delegateAdapter.notifyDataSetChanged();
        deleteAdapter.notifyDataSetChanged();

        LinearLayoutManager doLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager scheduleLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager delegateLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager deleteLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        doRecyclerView.setLayoutManager(doLinearLayoutManager);
        scheduleRecyclerView.setLayoutManager(scheduleLinearLayoutManager);
        delegateRecyclerView.setLayoutManager(delegateLinearLayoutManager);
        deleteRecyclerView.setLayoutManager(deleteLinearLayoutManager);
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
                "Task 4"
        };

        homeDescription = new String[]{
                "Task 1 Description",
                "Task 2 Description",
                "Task 3 Description",
                "Task 4 Description"
        };

        int[] taskLevel = new int[]{
                Constants.DO,
                Constants.SCHEDULE,
                Constants.DELEGATE,
                Constants.DELETE
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