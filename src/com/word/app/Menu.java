package com.word.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.word.Color;
import com.word.Difficulty;
import com.word.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Static utility class for prompting user for input.
 */
class Menu {
    private static final String BANNER_PATH = "welcomebanner.txt";
    private static final String EXIT_BANNER_PATH = "exitbanner.txt";
    private static String banner;



    private static Prompter prompter = new Prompter(new Scanner(System.in));


    // TODO: Remove once com.word.Difficult is renamed to com.word.Difficulty and once it is public.
    // TODO: ask Jay how to test, or if it's even necessary to test.


    private Menu() {
    }

    static void setScannerSource(String filename){
        try {
            prompter = new Prompter(new Scanner(new File(filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String promptForName() {
        Console.clear();
        System.out.println(banner);
        String answer = prompt("\nPlayer Name: ", "[A-Za-z]{2,16}", "must be between 2 and 16 letters\n");
        return answer;
    }

    public static boolean promptToContinue() {
        Console.clear();
        System.out.println(banner);
        String continueText = String.format("\nContinue? %s: ", Color.YELLOW.setFontColor("Y/N"));
        // Case-insensitive.
        String answer = prompt(continueText, "(?i)(Y|N)", "");
        // if user wants to continue playing print a message asking the user find Game Window
        boolean findGameWindow = answer.equalsIgnoreCase("Y");
        if (findGameWindow) {
            System.out.println(Color.GREEN.setFontColor("Please find the game window on the taskbar....."));

        }
        return findGameWindow;
    }

    public static Difficulty promptForDifficulty() {
        Console.clear();
        System.out.println(banner);
        StringBuilder text = new StringBuilder("\nChoose your Difficulty:\n");


        for (Difficulty difficulty : Difficulty.values()) {
            String difficultyAlias = difficulty.getAlias();
            text.append(String.format("[%s] %s\n", Color.YELLOW.setFontColor(difficultyAlias), difficulty));

        }
        text.append("> ");

        String answer = prompt(text.toString(), "(?i)(E|M|H)", "invalid difficulty please try again\n");

        System.out.println(Color.GREEN.setFontColor("Please find the game window on the taskbar....."));


        return Difficulty.fromAlias(answer);
    }

    public static void welcome() {
        Console.clear(); // to clear the console to display the welcome banner

        try {
            banner = Files.readString(Path.of(Difficulty.CONF_PATH, BANNER_PATH));
            System.out.println(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created by Aeron, Sergio, and Vlad");
        Console.pause(3000); // pause welcome message for 10 seconds
        Console.clear(); // clear welcome message after 10 seconds
    }

    public static void displayQuitMessage() {
        Console.clear(); // to clear the console to display the exit banner

        try {
            String exitBanner = Files.readString(Path.of(Difficulty.CONF_PATH, EXIT_BANNER_PATH));
            System.out.println(exitBanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.pause(4000);
        Console.clear();
    }


    public static Option promptForOption() {
        Console.clear();
        System.out.println(banner);
        StringBuilder text = new StringBuilder("\nPlease choose one of the followings:\n");

        for (Option option : Option.values()) {
            String optionLetter = String.valueOf(option.toString().charAt(0)); //convert letter to string
            text.append(String.format("[%s] %s\n", Color.YELLOW.setFontColor(optionLetter), option));
        }
        text.append("> ");

        String answer = prompt(text.toString(), "(?i)(P|Q)", "Error... ").toUpperCase(); //(?i)(PLAY|VIEW_HISTORY|QUIT)

        Option option = null;
        if ("P".equals(answer)) {
            option = Option.PLAY;
        } else if ("Q".equals(answer)) {
            option = Option.QUIT;
        }
        return option;
    }

    private static String prompt(String promptMessage, String regex, String helpMessage) {

        try {
            String answer = prompter.prompt(promptMessage, regex, helpMessage).toUpperCase();
            return answer;
        } catch (NoSuchElementException e) {
            displayQuitMessage();
            System.exit(0);
        }
        return null;
    }
}
