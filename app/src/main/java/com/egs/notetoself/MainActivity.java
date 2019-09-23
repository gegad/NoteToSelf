package com.egs.notetoself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter mNoteAdapter;
    private boolean mSound;
    private int mAnimOption;
    private SharedPreferences mPrefs;

    Animation mAnimFadeIn;
    Animation mAnimFlash;

    @Override
    protected void onResume() {
        super.onResume();
        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("sound", true);
        mAnimOption = mPrefs.getInt("anim option", SettingsActivity.FAST);

        mAnimFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        mAnimFlash = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flash);

        if (mAnimOption == SettingsActivity.FAST) {
            mAnimFlash.setDuration(100);
        } else if (mAnimOption == SettingsActivity.SLOW) {
            mAnimFlash.setDuration(1000);
        }
        mNoteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNoteAdapter.saveNotes();
    }

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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNoteAdapter = new NoteAdapter();

        ListView listNote = findViewById(R.id.listView);
        listNote.setAdapter(mNoteAdapter);

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int whichItem, long id) {
                Note tempNote = mNoteAdapter.getItem(whichItem);
                DialogShowNote dialogShowNote = new DialogShowNote();
                dialogShowNote.sendNoteSelected(tempNote);
                dialogShowNote.show(getSupportFragmentManager(), "");
            }

        });
    }

    public void createNewNote(Note note){
        mNoteAdapter.addNote(note);
    }

    public class NoteAdapter extends BaseAdapter {
        private  JSONSerializer mJSONSerializer;
        List<Note> mNoteList = new ArrayList<Note>();

        public NoteAdapter() {
            mJSONSerializer = new JSONSerializer("NoteToSelf.json",
                    MainActivity.this.getApplicationContext());

            try {
                mNoteList = mJSONSerializer.load();
            } catch (Exception e) {
                mNoteList = new ArrayList<Note>();
            }
        }

        public void saveNotes() {
            try {
                mJSONSerializer.save(mNoteList);
            } catch (Exception e) {
                Log.i("Error saving", "NotesToSelf.json");
                Log.i("Error saving","" + e);
            }
        }

        @Override
        public int getCount() {
            return mNoteList.size();
        }

        @Override
        public Note getItem(int whichItem) {
            return mNoteList.get(whichItem);
        }

        @Override
        public long getItemId(int whichItem) {
            return whichItem;
        }

        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, viewGroup, false);
            }
            TextView txtTitle = (TextView) view.findViewById(R.id.textTitleItem);
            TextView txtDescription = (TextView) view.findViewById(R.id.textDescriptionItem);
            ImageView ivImportant = (ImageView) view.findViewById(R.id.imageViewImportantItem);
            ImageView ivTodo = (ImageView) view.findViewById(R.id.imageViewTodoItem);
            ImageView ivIdea = (ImageView) view.findViewById(R.id.imageViewIdeaItem);
            Note tempNote = mNoteList.get(whichItem);

            if (tempNote.isImportant() && mAnimOption != SettingsActivity.NONE) {
                view.setAnimation(mAnimFlash);
            } else {
                view.setAnimation(mAnimFadeIn);
            }

            if (!tempNote.isImportant()) {
                ivImportant.setVisibility(View.GONE);
            }
            if (!tempNote.isTodo()) {
                ivTodo.setVisibility(View.GONE);
            }
            if (!tempNote.isIdea()) {
                ivIdea.setVisibility(View.GONE);
            }
            txtTitle.setText(tempNote.getTitle());
            txtDescription.setText(tempNote.getDescription());
            return view;
        }

        public void addNote(Note note){
            mNoteList.add(note);
            notifyDataSetChanged();
        }
    }
}
