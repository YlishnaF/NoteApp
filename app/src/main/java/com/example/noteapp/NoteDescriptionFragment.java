package com.example.noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class NoteDescriptionFragment extends Fragment {
    static final String ARG_INDEX = "index";
    private Note notes = new Note();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arg = getArguments();
        if(arg!=null){
            TextView tv = view.findViewById(R.id.note_description);
            int index = arg.getInt(ARG_INDEX);
            List<String> descriptions = notes.getDescriptions();
            tv.setText(descriptions.get(index));
        }
    }

    public  static NoteDescriptionFragment newInstance(int index){
        NoteDescriptionFragment fragment = new NoteDescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return  fragment;
    }
}