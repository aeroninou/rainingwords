package com.word.app;

import com.apps.util.Prompter;
import com.word.Difficulty;

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

    public static void main(String[] args) {

        String name = Menu.promptForName();
        System.out.println(name);
        boolean continuePrompt = Menu.promptToContinue();
        System.out.println(continuePrompt);
        Difficulty promptDiff = Menu.promptForDifficulty();
        System.out.println(promptDiff);

    }
}
