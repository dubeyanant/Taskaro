package com.soc.taskaro.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        if (notes.description.equals("")) {
            holder.notesDescription.setVisibility(View.GONE);
        } else {
            holder.notesDescription.setText(notes.description);
        }


        // Removes views if clicked on delete button
        int temp = holder.getAdapterPosition();
        holder.deleteNoteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesArrayList.remove(temp);
                notifyItemRemoved(temp);
                notifyItemRangeChanged(temp, notesArrayList.size());
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