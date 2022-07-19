package com.example.noteapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import static com.example.noteapp.NoteDescriptionFragment.ARG_INDEX;

public class NotePortActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_port);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.description_fragment_container,NoteDescriptionFragment.newInstance(Objects.requireNonNull(getIntent().getExtras())
                            .getInt(ARG_INDEX)))
                    .commit();
        }
    }
}