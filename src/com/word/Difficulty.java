package com.word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * TODO: vlad
 *  - smart enum
 *  create the 3 files
 * */
public enum Difficulty {

    EASY("easy.txt"),
    MEDIUM("medium"),
    HARD("hard");

    //advance enum use constructor and getter:
    //Fields
    private final String fileName;

    //Constructor
    Difficulty(String fileName){
        this.fileName = fileName;
    }

    /**
     * TODO:
     * read the file File.readAllLines
     * returns string read in into Word objects
     *  populate list of words
     **/

    //Method


    //ACCESSOR:
    public String getFileName() {
        return fileName;
    }

    public static void main(String[] args)
    {
//        Path path = Paths.get("easy.txt");
//        Path path = Paths.get("medium.txt");
        Path path = Paths.get("hard.txt");

        String content = null;
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            content = String.join(System.lineSeparator(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(content);
    }

}



