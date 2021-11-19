package ch.zli.m335.androidnotes.Activities;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.zli.m335.androidnotes.MainActivity;
import ch.zli.m335.androidnotes.R;

public class DeleteNoteActivity extends AppCompatActivity {

    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);

        this.yes = (Button) findViewById(R.id.yesButton);
        this.no = (Button) findViewById(R.id.noButton);


    }

    private OnClickListener deleteNote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DeleteNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private OnClickListener cancelDelete = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DeleteNoteActivity.this, EditNoteActivity.class);
            startActivity(intent);
        }
    };


}
