package ch.zli.m335.androidnotes.Model;

import android.app.ListActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class AppData extends ListActivity {

    private ArrayList<Note> notes = new ArrayList<Note>();
    ArrayAdapter<Note> adapter;
    private static AppData instance;

    private AppData(){}

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

}
