package com.example.noteapp;

public interface CardSource {
    CardData getCardData(int position);
    int size();
}
