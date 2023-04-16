package com.soc.taskaro.fragments;

import android.app.ProgressDialog;
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
import com.soc.taskaro.adapters.NotesFragmentAdapter;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.Note;
import com.soc.taskaro.utils.Extras;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    public ProgressDialog progressDialog;
    SearchView searchView;
    NotesFragmentAdapter notesFragmentAdapter;
    boolean state;
    private ArrayList<Note> notesArrayList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = root.findViewById(R.id.notesRecyclerView);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        ArrayList<Note> filteredList = new ArrayList<>();
        for (Note note : notesArrayList) {
            if (note.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(note);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No notes found", Toast.LENGTH_SHORT).show();
        } else {
            notesFragmentAdapter.setFilteredList(filteredList);
        }
    }

    /*public void dataInitialize() {
        System.out.println("Yes2"+"///////////");
        notesArrayList = new ArrayList<Notes>();

        notesHeading = new String[]{
                "Hello World",
                "Anant Dubey",
                getString(R.string.do_it),
                getString(R.string.delete_it)
        };

        notesDescription = new String[]{
                "",
                "Hello",
                getString(R.string.do_it_description),
                getString(R.string.delete_it_description)
        };
        for (int i = 0; i < notesHeading.length; i++) {
            Notes note = new Notes(notesHeading[i], notesDescription[i]);
            notesArrayList.add(note);
        }
    }*/

    public void onNotesListSuccess(ArrayList<Note> notesList) {
        progressDialog.dismiss();
        notesArrayList = notesList;
        recyclerView.setHasFixedSize(true);
        notesFragmentAdapter = new NotesFragmentAdapter(this, notesArrayList);
        recyclerView.setAdapter(notesFragmentAdapter);
        notesFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Extras.networkCheck(getContext())) {
            progressDialog = new Extras().showProgressBar(this);
            new FirestoreClass().getNotesList(this);
        } else {
            Toast.makeText(getContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNoteDeleteSuccess() {
        progressDialog.dismiss();
    }
}
