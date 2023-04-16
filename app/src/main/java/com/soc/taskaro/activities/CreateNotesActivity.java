package com.soc.taskaro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.R;
import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.models.Note;
import com.soc.taskaro.utils.Constants;
import com.soc.taskaro.utils.Extras;

import java.util.HashMap;

public class CreateNotesActivity extends AppCompatActivity {

    public TextView doneSaveNotesTextView;
    EditText writeNotesTitleEditText, writeNotesDescriptionEditText;
    ProgressDialog progressDialog;
    boolean isTitleValid = false;

    String noteID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        writeNotesTitleEditText = findViewById(R.id.writeNotesTitleEditText);
        writeNotesDescriptionEditText = findViewById(R.id.writeNotesDescriptionEditText);
        doneSaveNotesTextView = findViewById(R.id.doneSaveNotesTextView);

        // Executed when called by Main Notes Screen
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.Extra_NOTE_ID)) {
            noteID = intent.getStringExtra(Constants.Extra_NOTE_ID);
            getNoteDetails(noteID);
        }


        doneSaveNotesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extras.networkCheck(getApplicationContext())) {
                    if (noteID == null) {
                        progressDialog = new Extras().showProgressBar(CreateNotesActivity.this);
                        setValidation();
                    } else {
                        doneSaveNotesTextView.setEnabled(false);
                        progressDialog = new Extras().showProgressBar(CreateNotesActivity.this);
                        setValidation();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getNoteDetails(String noteID) {
        if (Extras.networkCheck(getApplicationContext())) {
            progressDialog = new Extras().showProgressBar(this);
            new FirestoreClass().getNoteDetails(this, noteID);
        } else {
            Toast.makeText(getApplicationContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setValidation() {
        if (writeNotesTitleEditText.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            doneSaveNotesTextView.setEnabled(true);
            writeNotesTitleEditText.setError(getResources().getString(R.string.empty_field_error));
            isTitleValid = false;
        } else {
            isTitleValid = true;
        }

        if (isTitleValid && noteID == null) {
            HashMap<String, Object> noteHashMap = new HashMap<>();
            noteHashMap.put(Constants.NOTE_HEADING, writeNotesTitleEditText.getText().toString().trim());
            noteHashMap.put(Constants.NOTE_DESCRIPTION, writeNotesDescriptionEditText.getText().toString().trim());
            new FirestoreClass().uploadNotesDetails(CreateNotesActivity.this, noteHashMap);
        } else if (isTitleValid && noteID != null) {
            HashMap<String, Object> noteHashMap = new HashMap<>();
            noteHashMap.put(Constants.Extra_NOTE_ID, noteID);
            noteHashMap.put(Constants.NOTE_HEADING, writeNotesTitleEditText.getText().toString().trim());
            noteHashMap.put(Constants.NOTE_DESCRIPTION, writeNotesDescriptionEditText.getText().toString().trim());
            new FirestoreClass().updateNotesDetails(this, noteHashMap);
        }
    }

    public void uploadDataSuccess() {
        doneSaveNotesTextView.setEnabled(true);
        progressDialog.dismiss();
        Toast.makeText(CreateNotesActivity.this, "Data uploaded successfully...", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }

    public void noteGetDetailsSuccess(Note note) {
        progressDialog.dismiss();
        writeNotesTitleEditText.setText(note.getHeading());
        writeNotesDescriptionEditText.setText(note.getDescription());
    }

    public void userDataUpdateSuccess() {
        progressDialog.dismiss();
        Toast.makeText(CreateNotesActivity.this, "Data updated successfully...", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }
}