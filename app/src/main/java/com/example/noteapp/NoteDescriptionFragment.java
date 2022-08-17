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
import android.widget.EditText;


public class NoteDescriptionFragment extends Fragment {
    static final String ARG_INDEX = "index";
    public static final String CURRENT_CARD = "Card";
    EditText tv;
    EditText nameTv;
    CardData card;

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

            card = (CardData)arg.getParcelable(CURRENT_CARD);

            tv = view.findViewById(R.id.note_description);
            tv.setText(card.getDescription());

            nameTv = view.findViewById(R.id.title);
            nameTv.setText(card.getTitle());


            nameTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                    card.setTitle(nameTv.getText().toString());
                }
                @Override
                public void afterTextChanged(Editable editable) { }
            });

            tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

                    card.setDescription(tv.getText().toString());
                }
                @Override
                public void afterTextChanged(Editable editable) { }
            });

        }
        getChildFragmentManager().beginTransaction()
                .replace(R.id.checkbox_container, CheckboxFragment.newInstance()).commit();
    }

    public static NoteDescriptionFragment newInstance(CardData card){
        NoteDescriptionFragment fragment = new NoteDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_CARD, card);
        fragment.setArguments(args);
        return  fragment;
    }
}