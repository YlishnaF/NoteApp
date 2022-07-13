package com.example.noteapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.noteapp.NoteDescriptionFragment.ARG_INDEX;

public class NotesListFragment extends Fragment {
    private static final String CURRENT_DESCRIPTION = "Description";
    private int currentPosition = 0;
    private Note notes = new Note();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null){
            currentPosition = savedInstanceState.getInt(CURRENT_DESCRIPTION, 0);
        }
        initList(view);
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            showLandDescription(currentPosition);
        }
    }

    public void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        List<String> noteTitles =  notes.getNoteTitles();
        for (int i = 0; i < noteTitles.size(); i++) {
            final String title = noteTitles.get(i);
            TextView textView = new TextView(getContext());
            textView.setText(title);
            textView.setTextSize(30);
            linearLayout.addView(textView);
            final int position = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                    currentPosition = position;
                    showNoteDescription(position);
                }
            });
        }
    }

    private void showNoteDescription(int index) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showLandDescription(index);
        } else {
            showPortDescription(index);
        }
    }

    private void showPortDescription(int index) {
        Activity activity = requireActivity();
        final Intent intent = new Intent(activity, NotePortActivity.class);
        intent.putExtra(ARG_INDEX, index);
        activity.startActivity(intent);
    }

    private void showLandDescription(int index) {
        NoteDescriptionFragment noteDescriptionFragment = NoteDescriptionFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.note_description_container, noteDescriptionFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_DESCRIPTION, currentPosition);
        super.onSaveInstanceState(outState);
    }
}