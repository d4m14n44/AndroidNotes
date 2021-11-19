package ch.zli.m335.androidnotes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;

import ch.zli.m335.androidnotes.Model.AppData;
import ch.zli.m335.androidnotes.Model.Note;
import ch.zli.m335.androidnotes.R;

public class CreateNoteActivity extends AppCompatActivity {

    private Button add;
    private Button cancel;
    private EditText title;
    private MultiAutoCompleteTextView noteContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_activity);

        this.add = (Button) findViewById(R.id.saveNoteButton);
        this.cancel = (Button) findViewById(R.id.cancelEditButton);
        this.title = (EditText) findViewById(R.id.EditNoteTitle);
        this.noteContent = (MultiAutoCompleteTextView) findViewById(R.id.editNoteText);

        this.cancel.setOnClickListener(cancelCreateNote);
        this.add.setOnClickListener(addNewNote);


    }

    /**
     * Wenn nötig dann in allen Klassen noch onResume und onpause machen
     */
    private OnClickListener cancelCreateNote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private OnClickListener addNewNote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String noteTitle = title.getText().toString();
            String noteText = noteContent.getText().toString();
            Note no = new Note(noteTitle, noteText);
            AppData.getInstance().getList().add(no);
            Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };


}
