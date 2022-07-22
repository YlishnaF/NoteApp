package com.example.noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;



public class NotesListFragment extends Fragment {
    public static final String CURRENT_NOTE = "Note";
    private Note note = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    public void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        List<String> noteTitles=Note.getNotes().stream().map(Note::getName).collect(Collectors.toList());

        for (int i = 0; i < noteTitles.size(); i++) {
            final String title = noteTitles.get(i);
            TextView textView = new TextView(getContext());
            textView.setText(title);
            textView.setTextSize(30);
            linearLayout.addView(textView);
            final int position = i;
            textView.setOnClickListener(v -> {
                Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                note=Note.getNotes().get(position);
                showNoteDescription(note);
            });
        }
    }

    private void showNoteDescription(Note note) {
        FragmentTransaction fragmentTransaction = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .addToBackStack("")
                .replace(R.id.fragment_container, NoteDescriptionFragment.newInstance(note))
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, note);
        super.onSaveInstanceState(outState);
    }
}