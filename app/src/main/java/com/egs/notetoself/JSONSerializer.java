package com.egs.notetoself;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class JSONSerializer {
    private String mFilename;
    private Context mContext;

    public JSONSerializer(String file, Context context) {
        mFilename = file;
        mContext = context;
    }

    public void save(List<Note> notes) throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        for(Note n: notes) {
            try {
                jsonArray.put(n.convertToJSON());
            } catch (JSONException e) {
                Log.i("Exeption " + e, n.getTitle());
            }
        }

        Writer writer = null;
        try {
            OutputStream outputStream = mContext.openFileOutput(mFilename, mContext.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }

        }
    }

    public ArrayList<Note> load() throws IOException, JSONException {
        ArrayList<Note> noteArrayList = new ArrayList<Note>();
        BufferedReader reader = null;
        try {
            InputStream  inputStream = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < jsonArray.length(); ++i) {
                noteArrayList.add(new Note(jsonArray.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            Log.i("File Not Found: ", mFilename);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return noteArrayList;
    }
}
