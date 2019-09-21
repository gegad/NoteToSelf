package com.egs.notetoself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public boolean mSound;
    final public static int FAST = 1;
    final public static int SLOW = 2;
    final public static int NONE = 3;
    private int mAnimOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mEditor = mPrefs.edit();

        mSound = mPrefs.getBoolean("sound", true);
        CheckBox checkBox = findViewById(R.id.checkBoxSound);
        checkBox.setChecked(mSound);

        mAnimOptions = mPrefs.getInt("anim option", FAST);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rb_group);
        radioGroup.clearCheck();

        switch (mAnimOptions) {
            case FAST:
                radioGroup.check(R.id.radioButtonFast);
                break;
            case SLOW:
                radioGroup.check(R.id.radioButtonSlow);
                break;
            case NONE:
                radioGroup.check(R.id.radioButtonNone);
                break;
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.i("sound = ", "" + mSound);
                Log.i("isChecked = ", "" + isChecked);

                mSound = !mSound;
                mEditor.putBoolean("sound", mSound);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radioButtonFast:
                        mAnimOptions = FAST;
                        break;
                    case R.id.radioButtonSlow:
                        mAnimOptions = SLOW;
                        break;
                    case R.id.radioButtonNone:
                        mAnimOptions = NONE;
                        break;
                }
                mEditor.putInt("anim option", mAnimOptions);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        mEditor.commit();
    }
}
