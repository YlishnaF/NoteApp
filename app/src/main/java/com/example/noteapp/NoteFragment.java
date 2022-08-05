package com.example.noteapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

public class NoteFragment extends Fragment {

    public  static NoteFragment newInstance() {
        return  new NoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        CardSource data = new CardSourceImpl().init();
        initRecyclerView(recyclerView, data);
        return  view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NoteAdapter adapter = new NoteAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Note note = Note.getNotes().get(position);
                showNoteDescription(note);
            }
        });
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
}
