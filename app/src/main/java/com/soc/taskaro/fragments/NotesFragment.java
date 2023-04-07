package com.soc.taskaro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    SearchView searchView;
    NotesFragmentAdapter notesFragmentAdapter;
    private ArrayList<Notes> notesArrayList;
    private String[] notesDescription;
    private String[] notesHeading;
    private RecyclerView recyclerView;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();
        recyclerView = view.findViewById(R.id.notesRecyclerView);
        recyclerView.setHasFixedSize(true);
        notesFragmentAdapter = new NotesFragmentAdapter(getContext(), notesArrayList);
        recyclerView.setAdapter(notesFragmentAdapter);
        notesFragmentAdapter.notifyDataSetChanged();

        searchView = view.findViewById(R.id.notesSearchBar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<Notes> filteredList = new ArrayList<>();
        for (Notes notes : notesArrayList) {
            if (notes.heading.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(notes);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No notes found", Toast.LENGTH_SHORT).show();
        } else {
            notesFragmentAdapter.setFilteredList(filteredList);
        }
    }

    private void dataInitialize() {
        notesArrayList = new ArrayList<>();
        notesHeading = new String[]{
                getString(R.string.delete_it),
                getString(R.string.delegate_it),
                getString(R.string.do_it),
                getString(R.string.delete_it)
        };

        notesDescription = new String[]{
                getString(R.string.delete_it_description),
                getString(R.string.delegate_it_description),
                getString(R.string.do_it_description),
                getString(R.string.delete_it_description)
        };
        for (int i = 0; i < notesHeading.length; i++) {
            Notes notes = new Notes(notesHeading[i], notesDescription[i]);
            notesArrayList.add(notes);
        }
    }
}