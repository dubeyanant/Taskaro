package com.soc.taskaro.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soc.taskaro.R;

import java.util.ArrayList;

public class NotesFragmentAdapter extends RecyclerView.Adapter<NotesFragmentAdapter.NotesViewHolder> {

    Context context;
    ArrayList<Notes> notesArrayList;

    public NotesFragmentAdapter(Context context, ArrayList<Notes> notesArrayList) {
        this.context = context;
        this.notesArrayList = notesArrayList;
    }

    public void setFilteredList(ArrayList<Notes> filteredList) {
        this.notesArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes notes = notesArrayList.get(position);
        holder.notesHeading.setText(notes.heading);
        holder.notesDescription.setText(notes.description);
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView notesHeading, notesDescription;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesHeading = itemView.findViewById(R.id.notes_list_text1);
            notesDescription = itemView.findViewById(R.id.notes_list_text2);
        }
    }
}