package com.example.androidassociatedeveloper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidassociatedeveloper.DataManager;
import com.example.androidassociatedeveloper.R;
import com.example.androidassociatedeveloper.models.NoteInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private ArrayAdapter<NoteInfo> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(NoteListActivity.this, NoteActivity.class)));

        populateListView();
    }

    private void populateListView() {
        final ListView listView = findViewById(R.id.list_notes);
        List<NoteInfo> noteInfoList = DataManager.getInstance().getNotes();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, noteInfoList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
            intent.putExtra(NoteActivity.NOTE_POSITION, position);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapter.notifyDataSetChanged();
    }
}