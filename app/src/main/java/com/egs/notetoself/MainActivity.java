package com.egs.notetoself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btnShowNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogShowNote dialogShowNote = new DialogShowNote();
                dialogShowNote.sendNoteSelected(mTempNote);
                dialogShowNote.show(getSupportFragmentManager(), "123");
            }
        });

        ImageButton btnAdd = (ImageButton) findViewById(R.id.imageButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNewNote dialogNewNote = new DialogNewNote();
                dialogNewNote.show(getSupportFragmentManager(), "123");
            }
        });
    }
    Note mTempNote = new Note();

    public void createNewNote(Note note) {
        mTempNote = note;
    }
}
