package com.example.noteapp;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NoteFragment extends Fragment {
    public static final int MY_DURATION =1000;

    private CardSource data;
    private NoteAdapter adapter;
    private RecyclerView recyclerView;

    public  static NoteFragment newInstance() {
        return  new NoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container,false);
        data = new CardSourceImpl().init();
        initView(view);
        setHasOptionsMenu(true);
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                data.addCardData(new CardData("name " +(data.size()),
                        "description"+(data.size())));
                adapter.notifyItemInserted(data.size()-1);
                recyclerView.smoothScrollToPosition(data.size()-1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        data = new CardSourceImpl().init();
        initRecyclerView();

    }

    private void initRecyclerView(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(data, this);
        recyclerView.setAdapter(adapter);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DURATION);
        animator.setRemoveDuration(MY_DURATION);
        recyclerView.setItemAnimator(animator);
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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_popup, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()){
            case R.id.update_note:
                showNoteDescription(Note.getNotes().get(position));
            return true;

            case R.id.delete_note:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
