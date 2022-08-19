package com.example.noteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class NoteFragment extends Fragment {
    public static final int MY_DURATION = 1000;
    SharedPreferences sharedPreferences = null;
    public final static String KEY = "key";
    private NoteAdapter adapter;
    private RecyclerView recyclerView;
    static ArrayList<CardData> cards = new ArrayList<>();


    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        for (int i = 0; i < 3; i++) {
            cards.add(new CardData("note" + i, "description" + i));
        }
        initView(view);

        String savedNotes = sharedPreferences.getString(KEY, null);
        if (savedNotes == null || savedNotes.isEmpty()) {
            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Type type = new TypeToken<ArrayList<CardData>>() {
                }.getType();
                adapter.setNewData(new GsonBuilder().create().fromJson(savedNotes, type));
                cards = new GsonBuilder().create().fromJson(savedNotes, type);
            } catch (JsonSyntaxException e) {
                Toast.makeText(getContext(), "Ошибка трансформации", Toast.LENGTH_SHORT).show();
            }
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                cards.add(new CardData("Note title", "Note description"));
                adapter.setNewData(cards);
                String jsonNote = new GsonBuilder().create().toJson(cards);
                sharedPreferences.edit().putString(KEY, jsonNote).apply();
                adapter.notifyItemInserted(cards.size() - 1);
                recyclerView.smoothScrollToPosition(cards.size() - 1);
                return true;
            case R.id.action_clear:
                cards.removeAll(cards);
                jsonNote = new GsonBuilder().create().toJson(cards);
                sharedPreferences.edit().putString(KEY, jsonNote).apply();
                adapter.setNewData(cards);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(cards, this);
        recyclerView.setAdapter(adapter);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DURATION);
        animator.setRemoveDuration(MY_DURATION);
        recyclerView.setItemAnimator(animator);
        adapter.setOnItemClickListener((view, position) -> {
            showNoteDescription(cards.get(position));
        });
    }

    private void showNoteDescription(CardData cardData) {
        FragmentTransaction fragmentTransaction = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .addToBackStack("")
                .replace(R.id.fragment_container, NoteDescriptionFragment.newInstance(cardData))
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
        switch (item.getItemId()) {
            case R.id.update_note:
                showNoteDescription(cards.get(position));
                return true;

            case R.id.delete_note:
                cards.remove(position);
                adapter.setNewData(cards);
                String jsonNote = new GsonBuilder().create().toJson(cards);
                sharedPreferences.edit().putString(KEY, jsonNote).apply();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
