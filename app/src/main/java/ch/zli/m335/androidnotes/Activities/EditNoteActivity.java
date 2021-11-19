package ch.zli.m335.androidnotes.Activities;

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

public class EditNoteActivity extends AppCompatActivity {

    private Button save;
    private Button cancel;
    private Button delete;
    private EditText editTitle;
    private MultiAutoCompleteTextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);

        this.save = (Button) findViewById(R.id.saveNoteButton);
        this.cancel = (Button) findViewById(R.id.cancelEditButton);
        this.delete = (Button) findViewById(R.id.deleteNoteButton);
        this.editTitle = (EditText) findViewById(R.id.EditNoteTitle);
        this.editText = (MultiAutoCompleteTextView) findViewById(R.id.editNoteText);

        this.save.setOnClickListener(saveNote);
        this.cancel.setOnClickListener(cancelEdit);
        this.delete.setOnClickListener(deleteNote);

    }

    private OnClickListener saveNote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private OnClickListener cancelEdit = new OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private OnClickListener deleteNote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EditNoteActivity.this, DeleteNoteActivity.class);
            startActivity(intent);
        }
    };


}
