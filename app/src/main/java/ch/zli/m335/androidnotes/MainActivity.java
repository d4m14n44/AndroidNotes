package ch.zli.m335.androidnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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

import ch.zli.m335.androidnotes.Activities.CreateNoteActivity;
import ch.zli.m335.androidnotes.Activities.EditNoteActivity;
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


    /**
     *
     *
     *
     *
     * SERVICE FÜR NOTES EVTL BEGRABEN UND DURCH PERSISTENZ (PROTOTYP COUNTER)
     * ERSETZEN (EVTL IN EXTERNEM FILE (im Projekt) ABSPEICHERN)
     *
     * SERVICE FÜR DIE WECHSLUNG DER HINTERGRUNDFARBEN MACHEN
     *
     * https://stackoverflow.com/questions/1944656/android-global-variable (wie ich auf Service zugreien kann)
     *
     *
     *
     *
     *
     *
     *
     *
     *

     */










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] array = new int[]{1,2,3,4, 5};

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
        }else {
            this.isSensorAvailable = false;
        }

        for (int i : array) {
            this.noteButtons = new Button(this);
            this.noteButtons.setText(i + "");
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
            System.out.println("TESTTESTTESTTEST");
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
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            startActivity(intent);
        }
    };

    private OnClickListener openAddActivity = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        double tmp = event.values[0];
        this.test.setText(tmp + "");
        if (tmp < 0.0)
        {
            this.constrainLayout.setBackgroundColor(Color.BLUE);
        } else if (tmp > 0.0 && tmp < 25.0) {
            this.constrainLayout.setBackgroundColor(Color.GRAY);
        } else {
            this.constrainLayout.setBackgroundColor(Color.RED);
        }
    }

    @Override
    protected void onResume()
    {
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

}