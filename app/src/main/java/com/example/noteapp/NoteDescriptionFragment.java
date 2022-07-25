package com.example.noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.noteapp.NotesListFragment.CURRENT_NOTE;


public class NoteDescriptionFragment extends Fragment {
    static final String ARG_INDEX = "index";
    EditText tv;
    EditText nameTv;
    Note note;

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
            note = (Note) arg.getParcelable(CURRENT_NOTE);
            tv = view.findViewById(R.id.note_description);
            tv.setText(note.getDescription());
            nameTv = view.findViewById(R.id.title);
            nameTv.setText(note.getName());
            nameTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                    note.setName(nameTv
                            .getText().toString());
                }
                @Override
                public void afterTextChanged(Editable editable) { }
            });

            tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                    note.setDescription(tv
                            .getText().toString());
                }
                @Override
                public void afterTextChanged(Editable editable) { }
            });

        }
        getChildFragmentManager().beginTransaction()
                .replace(R.id.checkbox_container, CheckboxFragment.newInstance()).commit();
    }

    public  static NoteDescriptionFragment newInstance(Note note){
        NoteDescriptionFragment fragment = new NoteDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        fragment.setArguments(args);
        return  fragment;
    }
}