package ch.zli.m335.androidnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ch.zli.m335.androidnotes.Activities.CreateNoteActivity;
import ch.zli.m335.androidnotes.Activities.EditNoteActivity;
import ch.zli.m335.androidnotes.Model.AppData;
import ch.zli.m335.androidnotes.Model.Note;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Button add;
    private Button scrollUp;
    private Button scrollDown;
    private TextView textView;
    private TextView test;
    private SensorManager sensorManager;
    private Sensor tmpSensor;
    private Boolean isSensorAvailable;
    private ConstraintLayout constrainLayout;
    private LinearLayout linear;
    private Button noteButtons;
    boolean bound = false;
    public ServiceNote serviceNote;
    private ScrollView scrollView;
    SharedPreferences preferences;
    private String buttonTitle;
    private String noteText;
    ArrayList<String> array = new ArrayList<>();
    ArrayList<String> array2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.array.add("Notiz 1");
        this.array.add("Notiz 2");
        this.array.add("Notiz 3");
        this.array.add("Notiz 4");
        this.array.add("Notiz 5");

        this.array2.add("Inhalt von Notiz 1");
        this.array2.add("Inhalt von Notiz 2");
        this.array2.add("Inhalt von Notiz 3");
        this.array2.add("Inhalt von Notiz 4");
        this.array2.add("Inhalt von Notiz 5");


        preferences = getSharedPreferences("NoteTitle", 0);
        buttonTitle = preferences.getString("NoteTitles", "Notiz 1");

        preferences = getSharedPreferences("Content", 0);
        noteText = preferences.getString("Contents", "Inhalt von Notiz 1");


        this.add = (Button) findViewById(R.id.addButton);
        this.scrollUp = (Button) findViewById(R.id.upButton);
        this.scrollDown = (Button) findViewById(R.id.downButton);
        this.textView = (TextView) findViewById(R.id.test);
        this.constrainLayout = (ConstraintLayout) findViewById(R.id.constrainLayout);
        this.test = (TextView) findViewById(R.id.test);
        this.linear = (LinearLayout) findViewById(R.id.linearLayout);
        this.scrollView = (ScrollView) findViewById(R.id.scrollview);

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.add.setOnClickListener(openAddActivity);
        this.scrollUp.setOnClickListener(scrollingUp);
        this.scrollDown.setOnClickListener(scrollingDown);

        if (this.sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            this.tmpSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            this.isSensorAvailable = true;
        } else {
            this.isSensorAvailable = false;
        }

        for (String i : array) {
            buttonTitle = i;
            this.noteButtons = new Button(this);
            this.noteButtons.setText(buttonTitle + "");
            this.noteButtons.setPadding(150, 0, 150, 0);
            this.noteButtons.setMaxLines(100);
            // Farben setzen
            this.noteButtons.setTextColor(getApplication().getResources().getColor(R.color.black));
            this.noteButtons.setBackgroundColor(getApplication().getResources().getColor(R.color.yellow));
            // Damit erste Notiz auf gleicher höhe wie add Button startet
            linear.setPadding(0, 78, 0, 0); // Grösse der Buttons
            ConstraintLayout.LayoutParams l = new ConstraintLayout.LayoutParams(
                    600,
                    300
            );
            // Abstand zwischen den Buttons
            l.setMargins(0, 0, 0, 100);
            noteButtons.setLayoutParams(l);

            linear.addView(noteButtons);
            this.noteButtons.setOnClickListener(openNote);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        Intent intent = new Intent(this, ServiceNote.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        bound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceNote.LocalBinder binder = (ServiceNote.LocalBinder) service;
            serviceNote = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    private OnClickListener scrollingUp = new OnClickListener() {
        @Override
        public void onClick(View v) {
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    };

    private OnClickListener scrollingDown = new OnClickListener() {
        @Override
        public void onClick(View v) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };


    private OnClickListener openNote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            saveNotes();
            System.out.println(noteButtons.getText());
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            intent.putExtra("NoteTitles", array.get(0));
            intent.putExtra("Contents", array2.get(1));
            startActivity(intent);
        }
    };

    private OnClickListener openAddActivity = new OnClickListener() {
        @Override
        public void onClick(View v) {
            saveNotes();
            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        double tmp = event.values[0];
        this.test.setText(tmp + "");
        if (tmp < 0.0) {
            this.constrainLayout.setBackgroundColor(Color.BLUE);
        } else if (tmp > 0.0 && tmp < 25.0) {
            this.constrainLayout.setBackgroundColor(Color.GRAY);
        } else {
            this.constrainLayout.setBackgroundColor(Color.RED);
        }
    }

    @Override
    protected void onResume() {
        /*
            Hier muss der foreach kommen für alle buttons
         */
        super.onResume();
        if (isSensorAvailable) {
            this.sensorManager.registerListener(this, tmpSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorAvailable) {
            this.sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void saveNotes()
    {
        SharedPreferences.Editor edit = preferences.edit();
        edit.apply();
    }

    public ArrayList<String> getArray() {
        return array;
    }
}