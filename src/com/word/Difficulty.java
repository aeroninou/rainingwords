package com.word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public enum Difficulty {

    EASY("easy.txt", "E"),
    MEDIUM("medium.txt", "M"),
    HARD("hard.txt", "H");

    public static final String CONF_PATH = "conf";

    //advance enum use constructor and getter:

    //Fields
    private List<String> words;
    private final String alias;

    //Constructor
    Difficulty(String fileName, String alias) {
        this.alias = alias;

        Path path = Paths.get(CONF_PATH, fileName);

        try {
            words = Files.lines(path, StandardCharsets.UTF_8)
                    .map(String::toLowerCase) //.map(word -> word.toLowerCase())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Difficulty fromAlias(String s) {
            if (EASY.alias.equalsIgnoreCase(s))
                return EASY;
            else if ((MEDIUM.alias.equalsIgnoreCase(s)))
                return MEDIUM;
            else if (HARD.alias.equalsIgnoreCase(s))
                return HARD;
            return null;
        }

    //ACCESSOR:
    public List<String> getWords() {
        return words;
    }

    public String getAlias() {
        return alias;
    }

    public static void main(String[] args) {
        System.out.println(Difficulty.EASY.getWords());
    }

}



