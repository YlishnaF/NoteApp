package com.example.noteapp;

import java.util.Arrays;
import java.util.List;

public class Note {

    List<String> noteTitles = Arrays.asList("Прибраться дома", "Выучить урок", "Сходить на тренировку");
    List<String> descriptions = Arrays.asList("Вынести мусор, пропылесосить, вымыть пол, протереть пыль", "Прочитать теорию, сделать практику", "Встать с дивана, начать двигаться");


    public List<String> getNoteTitles() {
        return noteTitles;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }
}