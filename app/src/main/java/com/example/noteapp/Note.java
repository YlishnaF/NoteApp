package com.example.noteapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Note implements Parcelable {
    private int id;
    private String name;
    private String description;
    private static int count = 0;
    private static List<Note> notes=new ArrayList<>();

    static {
        for (int i = 0; i < 5; i++) {
            notes.add(new Note("name"+" "+ count, "description" + count));
        }

    }

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
        count++;
        id=count;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public static List<Note> getNotes() {
        return notes;
    }
    public static List<Note> deleteNotes(int i ) {
        notes.remove(i);
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private void setId(int id) {
        this.id = id;
    }

    public static void addNote(Note note){
        notes.add(note);
    }

}