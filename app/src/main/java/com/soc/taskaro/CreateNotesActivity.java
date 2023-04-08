package com.soc.taskaro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soc.taskaro.firestore.FirestoreClass;
import com.soc.taskaro.utils.Extras;

public class CreateNotesActivity extends AppCompatActivity {

    public TextView doneSaveNotesTextView;
    EditText writeNotesTitleEditText, writeNotesDescriptionEditText;
    ProgressDialog progressDialog;
    boolean isTitleValid = false, isDescriptionValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        writeNotesTitleEditText = findViewById(R.id.writeNotesTitleEditText);
        writeNotesDescriptionEditText = findViewById(R.id.writeNotesDescriptionEditText);
        doneSaveNotesTextView = findViewById(R.id.doneSaveNotesTextView);

        // Executed when called by Main Notes Screen
        Intent intent = getIntent();
        String Title = intent.getStringExtra("title");
        String Description = intent.getStringExtra("description");

        writeNotesTitleEditText.setText(Title);
        writeNotesDescriptionEditText.setText(Description);

        doneSaveNotesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Extras.networkCheck(getApplicationContext())) {
                    doneSaveNotesTextView.setEnabled(false);
                    progressDialog = new Extras().showProgressBar(CreateNotesActivity.this);
                    setValidation();
                } else {
                    Toast.makeText(getApplicationContext(), "Error! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        if (writeNotesDescriptionEditText.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            doneSaveNotesTextView.setEnabled(true);
            writeNotesDescriptionEditText.setError(getResources().getString(R.string.empty_field_error));
            isDescriptionValid = false;
        } else {
            isDescriptionValid = true;
        }

        if (isTitleValid && isDescriptionValid) {
            new FirestoreClass().uploadNotesDetails(CreateNotesActivity.this, writeNotesTitleEditText.getText().toString().trim(), writeNotesDescriptionEditText.getText().toString().trim());
        }
    }

    public void uploadDataSuccess() {
        doneSaveNotesTextView.setEnabled(true);
        progressDialog.dismiss();
        Toast.makeText(CreateNotesActivity.this, "Data uploaded successfully...", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }
}