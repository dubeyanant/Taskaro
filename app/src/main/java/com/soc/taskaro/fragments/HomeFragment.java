package com.soc.taskaro.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.Task;
import com.soc.taskaro.utils.Constants;
import com.soc.taskaro.utils.Extras;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    DoAdapter doAdapter;
    ScheduleAdapter scheduleAdapter;
    DelegateAdapter delegateAdapter;
    DeleteAdapter deleteAdapter;
    LinearLayout doItLL, scheduleItLL, delegateItLL, deleteItLL;
    private ArrayList<Task> doArrayList;
    private ArrayList<Task> scheduleArrayList;
    private ArrayList<Task> delegateArrayList;
    private ArrayList<Task> deleteArrayList;
    private String[] homeDescription;
    private String[] homeHeading;
    private RecyclerView doRecyclerView, scheduleRecyclerView, delegateRecyclerView, deleteRecyclerView;

    public ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        doRecyclerView = view.findViewById(R.id.doRecyclerView);
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView);
        delegateRecyclerView = view.findViewById(R.id.delegateRecyclerView);
        deleteRecyclerView = view.findViewById(R.id.deleteRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        doItLL = view.findViewById(R.id.doItLL);
        scheduleItLL = view.findViewById(R.id.scheduleItLL);
        delegateItLL = view.findViewById(R.id.delegateItLL);
        deleteItLL = view.findViewById(R.id.deleteItLL);

        LinearLayoutManager doLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager scheduleLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager delegateLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager deleteLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        doRecyclerView.setLayoutManager(doLinearLayoutManager);
        scheduleRecyclerView.setLayoutManager(scheduleLinearLayoutManager);
        delegateRecyclerView.setLayoutManager(delegateLinearLayoutManager);
        deleteRecyclerView.setLayoutManager(deleteLinearLayoutManager);
    }

    private void dataInitialize(ArrayList<Task> tasksList) {
        doArrayList = new ArrayList<>();
        scheduleArrayList = new ArrayList<>();
        delegateArrayList = new ArrayList<>();
        deleteArrayList = new ArrayList<>();
        for (int i = 0; i < tasksList.size(); i++){
            System.out.println(tasksList.get(i).getTitle()+"??????????????????");
            if(tasksList.get(i).isImportant() == true && tasksList.get(i).isUrgent() == true){
                doArrayList.add(tasksList.get(i));
            }else if (tasksList.get(i).isImportant() == true && tasksList.get(i).isUrgent() == false) {
                scheduleArrayList.add(tasksList.get(i));
            }else if (tasksList.get(i).isImportant() == false && tasksList.get(i).isUrgent() == true) {
                delegateArrayList.add(tasksList.get(i));
            }else if (tasksList.get(i).isImportant() == false && tasksList.get(i).isUrgent() == false) {
                deleteArrayList.add(tasksList.get(i));
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Extras.networkCheck(getContext())) {
            progressDialog = new Extras().showProgressBar(this);
            new FirestoreClass().getTasksList(this);
        } else {
            Toast.makeText(getContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onTasksListSuccess(ArrayList<Task> tasksList) {
        progressDialog.dismiss();

        dataInitialize(tasksList);

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
    }
}