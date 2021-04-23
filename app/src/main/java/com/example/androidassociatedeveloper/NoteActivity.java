package com.example.androidassociatedeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_INFO = "NOTE_INFO.com.example.androidassociatedeveloper";
    private NoteInfo noteInfo;
    private EditText note_text, note_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        note_text = findViewById(R.id.text_note_text);
        note_title = findViewById(R.id.text_note_title);

        List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();
        Log.i("hdhdhd", String.valueOf(courseInfoList));

//        Spinner spinnerCourses = findViewById(R.id.spinner_courses);
//        ArrayAdapter<CourseInfo> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, courseInfoList);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCourses.setAdapter(arrayAdapter);
//
//        readNoteContent();
//
//        displayNote(spinnerCourses, note_title, note_text);
    }

    private void readNoteContent(){
        Intent intent = getIntent();
        noteInfo = intent.getParcelableExtra(NOTE_INFO);
    }

    private void displayNote(Spinner spinner, EditText title, EditText text){
       List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();
       int courseIndex = courseInfoList.indexOf(noteInfo.getCourse());
       spinner.setSelection(courseIndex);

        title.setText(noteInfo.getTitle());
        text.setText(noteInfo.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}