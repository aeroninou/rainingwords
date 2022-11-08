package com.word.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.word.Difficulty;
import com.word.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Static utility class for prompting user for input.
 */
class Menu {
    private static final String BANNER_PATH = "welcomebanner.txt";
    private static final String EXIT_BANNER_PATH = "exitbanner.txt";
    private static String banner;


    private static final Prompter prompter = new Prompter(new Scanner(System.in));

    // TODO: Remove once com.word.Difficult is renamed to com.word.Difficulty and once it is public.
    // TODO: ask Jay how to test, or if it's even necessary to test.


    private Menu() {
    }

    public static String promptForName() {
        Console.clear();
        System.out.println(banner);
        String answer = prompter.prompt("\nPlayer Name: ", "[A-Za-z]{2,16}", "must be between 2 and 16 letters\n");
        return answer;
    }

    public static boolean promptToContinue() {
        Console.clear();
        System.out.println(banner);
        // Case-insensitive.
        String answer = prompter.prompt("\nContinue? Y/N: ", "(?i)(Y|N)", "");
        return answer.equalsIgnoreCase("Y");
    }

    public static Difficulty promptForDifficulty() {
        Console.clear();
        System.out.println(banner);
        StringBuilder text = new StringBuilder("\nChoose your Difficulty:\n");


        for (Difficulty difficulty : Difficulty.values()) {
            text.append(String.format("[%s] %s\n", difficulty.getAlias(), difficulty));

        }
        text.append("> ");

        String answer = prompter.prompt(text.toString(),  "(?i)(E|M|H)","invalid difficulty please try again\n");

        return Difficulty.fromAlias(answer);
    }

    public static void welcome() {
        Console.clear(); // to clear the console to display the welcome banner

        try {
            banner = Files.readString(Path.of(Difficulty.CONF_PATH,BANNER_PATH));
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
            String exitBanner = Files.readString(Path.of(Difficulty.CONF_PATH,EXIT_BANNER_PATH));
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
        //case-insensitive:
//        StringBuilder regex = new StringBuilder("(?i)(");

        for (Option option : Option.values()) {
            text.append(String.format("[%s] %s\n", option.toString().charAt(0), option));
//            regex.append(option).append("|");
        }
        text.append("> ");
//        regex.append(")");
        String answer = prompter.prompt(text.toString(), "(?i)(P|Q)", "Error... ").toUpperCase(); //(?i)(PLAY|VIEW_HISTORY|QUIT)
        Option option = null;
        if("P".equals(answer)){
            option = Option.PLAY;
        }
//        else if("V".equals(answer)){
//            option = Option.VIEW_HISTORY;
//        }
        else if ("Q".equals(answer)){
            option = Option.QUIT;
        }
        return option;
    }
}
