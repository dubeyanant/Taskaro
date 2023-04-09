package com.soc.taskaro.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.CreateNotesActivity;
import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.Note;
import com.soc.taskaro.utils.Constants;

import java.util.ArrayList;

public class NotesFragmentAdapter extends RecyclerView.Adapter<NotesFragmentAdapter.NotesViewHolder> {

    Fragment fragment;
    ArrayList<Note> notesArrayList;
    int temp;
    public NotesFragmentAdapter(Fragment fragment, ArrayList<Note> notesArrayList) {
        this.fragment = fragment;
        this.notesArrayList = notesArrayList;
    }

    public void setFilteredList(ArrayList<Note> filteredList) {
        this.notesArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(fragment.getContext()).inflate(R.layout.notes_list, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notesArrayList.get(position);
        holder.notesHeading.setText(note.getHeading());
        if (note.getDescription().equals("")) {
            holder.notesDescription.setVisibility(View.GONE);
        } else {
            holder.notesDescription.setText(note.getDescription());
        }

        // Removes views if clicked on delete button
        temp = holder.getAdapterPosition();
        holder.deleteNoteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirestoreClass().deleteNote(fragment, note, notesArrayList);
                notesArrayList.remove(temp);
                notifyItemRemoved(temp);
                notifyItemRangeChanged(temp, notesArrayList.size());

            }
        });

        // Used to click on items in recyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateNotesActivity.class);
                intent.putExtra(Constants.Extra_NOTE_ID, note.getNote_id());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView notesHeading, notesDescription;
        ImageButton deleteNoteImageButton;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesHeading = itemView.findViewById(R.id.notes_list_text1);
            notesDescription = itemView.findViewById(R.id.notes_list_text2);
            deleteNoteImageButton = itemView.findViewById(R.id.deleteNoteImageButton);
        }
    }
}