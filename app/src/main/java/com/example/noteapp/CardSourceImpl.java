package com.example.noteapp;

import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource {
    private static List<CardData> dataSource  = new ArrayList<>();;
    static {
        for (int i = 0; i < 5; i++) {

            dataSource.add(new CardData("name"+" "+ i, "description" + i));
        }
    }

    public CardSourceImpl() {

    }

    public static  List<CardData> getDataSource() {
        return dataSource;
    }

    public CardSourceImpl init() {
//        List<String> noteTitles = Note.getNotes().stream().map(Note::getName).collect(Collectors.toList());
//        List<String> noteSDescriptions = Note.getNotes().stream().map(Note::getDescription).collect(Collectors.toList());
//        for (int i = 0; i < noteTitles.size(); i++) {
////            dataSource.add(new CardData(noteTitles.get(i), noteSDescriptions.get(i)));
////        }
//        for (int i = 0; i < 5; i++) {
//            dataSource.add(new CardData("name"+" "+ i, "description" + i));
//        }
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        dataSource.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}
