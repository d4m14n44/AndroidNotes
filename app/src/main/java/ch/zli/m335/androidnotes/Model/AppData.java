package ch.zli.m335.androidnotes.Model;

import java.util.ArrayList;

public class AppData {

    private ArrayList<Note> notes = new ArrayList<>();
    private static AppData instance;

    private AppData() {}

    public ArrayList<Note> getList() {return this.notes;}

//    public void setList(ArrayList<Note> notes) {
//        this.notes = notes;
//    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

}
