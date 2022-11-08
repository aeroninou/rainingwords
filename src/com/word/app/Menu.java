package com.word.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.word.Difficulty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Static utility class for prompting user for input.
 */
class Menu {
    private static final String BANNER_PATH = "welcomebanner.txt";

    enum Option {
        PLAY,
        VIEW_HISTORY,
        QUIT
    }
    private static final Prompter prompter = new Prompter(new Scanner(System.in));

    // TODO: Remove once com.word.Difficult is renamed to com.word.Difficulty and once it is public.
    // TODO: ask Jay how to test, or if it's even necessary to test.


    private Menu() {
    }

    public static String promptForName() {
        return prompter.prompt("Player Name: ", "[A-Za-z]{2,16}", "must be between 2 and 16 letters\n"); //regex upper and lower [A-Za-z] and take 2 - 16 characters {2,16}
    }

    public static boolean promptToContinue() {
        // Case-insensitive.
        String answer = prompter.prompt("Continue? Y/N: ", "(?i)(Y|N)", "");
        return answer.equalsIgnoreCase("Y");
    }

    public static Difficulty promptForDifficulty() {
        StringBuilder text = new StringBuilder("Choose your Difficulty:\n");
        // Case-insensitive.
//        StringBuilder regex = new StringBuilder("(?i)(");

        for (Difficulty difficulty : Difficulty.values()) {
            text.append(String.format("[%s] %s\n", difficulty.getAlias(), difficulty));
//            regex.append(difficulty.getAlias()).append("|");
        }
        text.append("> ");
//        regex.append(")");
//        System.out.println(regex); //(?i)(E|M|H|)
        String answer = prompter.prompt(text.toString(),  "(?i)(E|M|H)","invalid difficulty please try again\n");
        return Difficulty.fromAlias(answer);
    }

    public static void welcome() {
        Console.clear(); // to clear the console to display the welcome banner

        try {
            String welcomeBanner = Files.readString(Path.of(Difficulty.CONF_PATH,BANNER_PATH));
            System.out.println(welcomeBanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created by Aeron, Sergio, and Vlad");
        System.out.println("© Copyright");
        Console.pause(3000); // pause welcome message for 10 seconds
        Console.clear(); // clear welcome message after 10 seconds
    }

    public static Option promptForOption() {
        StringBuilder text = new StringBuilder("Please choose one of the followings:\n");
        //case-insensitive:
//        StringBuilder regex = new StringBuilder("(?i)(");

        for (Option option : Option.values()) {
            text.append(String.format("[%s] %s\n", option.toString().charAt(0), option));
//            regex.append(option).append("|");
        }
        text.append("> ");
//        regex.append(")");
        String answer = prompter.prompt(text.toString(), "(?i)(P|V|Q)", "Error... ").toUpperCase(); //(?i)(PLAY|VIEW_HISTORY|QUIT)
        Option option = null;
        if("P".equals(answer)){
            option = Option.PLAY;
        }
        else if("V".equals(answer)){
            option = Option.VIEW_HISTORY;
        }
        else if ("Q".equals(answer)){
            option = Option.QUIT;
        }
        return option;

    }

}
