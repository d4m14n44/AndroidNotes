package ch.zli.m335.androidnotes.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import ch.zli.m335.androidnotes.Model.Note;

public class ServiceNote extends Service {

    public static ArrayList<Note> allNotes = new ArrayList<>();
    private final IBinder binder = new LocalBinder();

    // Innerclass
    public class LocalBinder extends Binder {
        public ServiceNote getService() {
            return ServiceNote.this;
        }
    }

    public ServiceNote() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }
}
