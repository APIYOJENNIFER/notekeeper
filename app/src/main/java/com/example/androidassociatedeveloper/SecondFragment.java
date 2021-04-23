package com.example.androidassociatedeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public class SecondFragment extends Fragment {
    public static final String NOTE_INFO = "NOTE_INFO.com.example.androidassociatedeveloper";
    private NoteInfo noteInfo;
    private EditText note_text, note_title;
    boolean isNewNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();
        Log.i("hdhdhd", String.valueOf(courseInfoList));
        note_text = view.findViewById(R.id.text_note_text);
        note_title = view.findViewById(R.id.text_note_title);
        Spinner spinnerCourses = view.findViewById(R.id.spinner_courses);
        ArrayAdapter<CourseInfo> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseInfoList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(arrayAdapter);

        readNoteContent();

        if (!isNewNote)
        displayNote(spinnerCourses, note_title, note_text);
    }

    private void readNoteContent(){
        Intent intent = getActivity().getIntent();
        noteInfo = intent.getParcelableExtra(NOTE_INFO);
        isNewNote = noteInfo == null;
    }

    private void displayNote(Spinner spinner, EditText title, EditText text){
        List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();
        int courseIndex = courseInfoList.indexOf(noteInfo.getCourse());
        spinner.setSelection(courseIndex);

        title.setText(noteInfo.getTitle());
        text.setText(noteInfo.getText());
    }
}