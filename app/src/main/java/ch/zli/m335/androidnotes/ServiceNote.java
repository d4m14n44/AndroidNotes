package ch.zli.m335.androidnotes;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

import ch.zli.m335.androidnotes.Model.Note;

public class ServiceNote extends Service{

    public static ArrayList<Note> allNotes = new ArrayList<>();
    private final IBinder binder = new LocalBinder();

    // Innerclass
    public class LocalBinder extends Binder {
        public ServiceNote getService() {
            return ServiceNote.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }



    public void addNoteInArrayList(String noteTitle, String noteText) {
        Note newNote = new Note(noteTitle, noteText);
        allNotes.add(newNote);
        System.out.println("www");
        for (Note n: allNotes) {
            System.out.println(n.getTitle());
        }
    }


}
