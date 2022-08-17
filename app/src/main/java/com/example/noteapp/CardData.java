package com.example.noteapp;


import android.os.Parcel;
import android.os.Parcelable;

public class CardData implements Parcelable {
    private String title;
    private String description;

    public CardData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
