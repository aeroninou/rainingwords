package com.word.client;

import com.word.app.GameWindow;
import com.word.app.Player;

import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        java.util.List<String> words = new ArrayList<>(java.util.List.of("Aeron", "Sergio", "Vlad", "Jay")) ;
        new GameWindow(new Player("Sergio"), words);
    }
}