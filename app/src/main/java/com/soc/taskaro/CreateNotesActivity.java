package com.soc.taskaro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.fragments.NotesFragment;

public class CreateNotesActivity extends AppCompatActivity {

    EditText writeNotesTitleEditText, writeNotesDescriptionEditText;
    TextView doneSaveNotesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        writeNotesTitleEditText = findViewById(R.id.writeNotesTitleEditText);
        writeNotesDescriptionEditText = findViewById(R.id.writeNotesDescriptionEditText);
        doneSaveNotesTextView = findViewById(R.id.doneSaveNotesTextView);

        doneSaveNotesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateNotesActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            }
        });
    }
}