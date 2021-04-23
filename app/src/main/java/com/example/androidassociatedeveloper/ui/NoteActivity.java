package com.example.androidassociatedeveloper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidassociatedeveloper.DataManager;
import com.example.androidassociatedeveloper.NoteActivityViewModel;
import com.example.androidassociatedeveloper.R;
import com.example.androidassociatedeveloper.models.CourseInfo;
import com.example.androidassociatedeveloper.models.NoteInfo;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION = "NOTE_POSITION.com.example.androidassociatedeveloper";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo noteInfo;
    private EditText note_text, note_title;
    boolean isNewNote;
    private Spinner spinnerCourses;
    private int notePosition;
    private boolean isCancelling;
    private NoteActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        viewModel = viewModelProvider.get(NoteActivityViewModel.class);

        if (viewModel.isNewlyCreated && savedInstanceState != null) {
            viewModel.restoreState(savedInstanceState);
        }

        viewModel.isNewlyCreated = false;


        note_text = findViewById(R.id.text_note_text);
        note_title = findViewById(R.id.text_note_title);

        List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();

        spinnerCourses = findViewById(R.id.spinner_courses);
        ArrayAdapter<CourseInfo> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, courseInfoList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(arrayAdapter);

        readNoteContent();
        saveOriginalData();

        if (!isNewNote)
            displayNote(spinnerCourses, note_title, note_text);
    }

    private void saveOriginalData() {
        if (isNewNote)
            return;

        viewModel.originalCourseId = noteInfo.getCourse().getCourseId();
        viewModel.originalCourseTitle = noteInfo.getTitle();
        viewModel.originalCourseText = noteInfo.getText();


    }

    private void readNoteContent() {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        isNewNote = position == POSITION_NOT_SET;
        if (isNewNote) {
            createNewNote();
        } else {
            noteInfo = DataManager.getInstance().getNotes().get(position);
        }
    }

    private void createNewNote() {
        DataManager dataManager = DataManager.getInstance();
        notePosition = dataManager.createNewNote();
        noteInfo = dataManager.getNotes().get(notePosition);
    }

    private void displayNote(Spinner spinner, EditText title, EditText text) {
        List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();
        int courseIndex = courseInfoList.indexOf(noteInfo.getCourse());
        spinner.setSelection(courseIndex);

        title.setText(noteInfo.getTitle());
        text.setText(noteInfo.getText());
    }

    private void sendEmail() {
        CourseInfo courseInfo = (CourseInfo) spinnerCourses.getSelectedItem();
        String subject = note_title.getText().toString();
        String text = "Check out what I learnt \"" + courseInfo.getTitle() + "\"\n" + note_text.getText();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);

    }

    public void saveNote() {
        noteInfo.setCourse((CourseInfo) spinnerCourses.getSelectedItem());
        noteInfo.setTitle(note_title.getText().toString());
        noteInfo.setText(note_text.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send_email) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {
            isCancelling = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isCancelling) {
            if (isNewNote) {
                DataManager.getInstance().removeNote(notePosition);
            } else {
                restoreOriginalDataValues();
            }
        } else {
            saveNote();
        }
    }

    private void restoreOriginalDataValues() {
        CourseInfo courseInfo = DataManager.getInstance().getCourse(viewModel.originalCourseId);
        noteInfo.setCourse(courseInfo);
        noteInfo.setTitle(viewModel.originalCourseTitle);
        noteInfo.setText(viewModel.originalCourseText);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.saveState(outState);
    }
}