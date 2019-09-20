package com.egs.notetoself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.http.X509TrustManagerExtensions;
import android.os.Bundle;
import android.os.Trace;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            DialogNewNote dialogNewNote = new DialogNewNote();
            dialogNewNote.show(getSupportFragmentManager(), "123");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    }
    Note mTempNote = new Note();

    public void createNewNote(Note note) {
        mTempNote = note;
    }
}
