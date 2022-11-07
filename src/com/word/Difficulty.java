package com.word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public enum Difficulty {

    EASY("easy.txt"),
    MEDIUM("medium.txt"),
    HARD("hard.txt");

    //advance enum use constructor and getter:

    //Fields
    private List<String> words;

    //Constructor
    Difficulty(String fileName) {

        Path path = Paths.get(fileName);

        try {
            words = Files.lines(path, StandardCharsets.UTF_8)
                    .map(String::toLowerCase) //.map(word -> word.toLowerCase())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ACCESSOR:
    public List<String> getWords() {
        return words;
    }

    public static void main(String[] args) {
        System.out.println(Difficulty.EASY.getWords());
    }

}



