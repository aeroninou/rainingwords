package com.word.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.word.Difficulty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

/**
 * Static utility class for prompting user for input.
 */
class Menu {
    private static final Prompter prompter = new Prompter(new Scanner(System.in));

    // TODO: Remove once com.word.Difficult is renamed to com.word.Difficulty and once it is public.
    // TODO: ask Jay how to test, or if it's even necessary to test.


    private Menu() {
    }

    public static String promptForName() {
        return prompter.prompt("Player Name: ");
    }

    public static boolean promptToContinue() {
        // Case-insensitive.
        String answer = prompter.prompt("Continue? Y/N: ", "(?i)(Y|N)", "");
        return answer.equalsIgnoreCase("Y");
    }

    public static Difficulty promptForDifficulty() {
        StringBuilder text = new StringBuilder("Choose your Difficulty:\n");
        // Case-insensitive.
        StringBuilder regex = new StringBuilder("(?i)(");

        for (Difficulty difficulty : Difficulty.values()) {
            text.append(String.format("[%s] %s\n", difficulty.getAlias(), difficulty));
            regex.append(difficulty.getAlias()).append("|");
        }
        text.append("> ");
        regex.append(")");
        String answer = prompter.prompt(text.toString(), regex.toString(), "");
        return Difficulty.fromAlias(answer);
    }

    public static void welcome(){
        Console.clear(); // to clear the console to display the welcome banner

        try {
            String welcomeBanner = Files.readString(Path.of("welcomebanner.txt"));
            System.out.println(welcomeBanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.pause(1000); // pause welcome message for 10 seconds
        Console.clear(); // clear welcome message after 10 seconds
    }

    public static Game.Option promptForOption(){
        StringBuilder text = new StringBuilder("Please choose one of the followings:\n");
        //case-insensitive:
        StringBuilder regex = new StringBuilder("(?i)(");

        for(Game.Option option : Game.Option.values()){
            text.append(String.format("[%s] %s\n", option, option));
            regex.append(option).append("|");
        }
        text.append("> ");
        regex.append(")");
        String answer = prompter.prompt(text.toString(), regex.toString(), "Error... "); //(?i)(PLAY|VIEW_HISTORY|QUIT)

        return Game.Option.valueOf(answer.toUpperCase()); // to convert what user enter into an Enum

    }

    public static void main(String[] args) {

//        Game.Option option = Menu.promptForOption();
//        System.out.println(option);
       Difficulty difficulty = promptForDifficulty();
        System.out.println(difficulty);




    }


}
