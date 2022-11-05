package com.word.app;

import com.apps.util.Prompter;

import java.util.Scanner;

/**
 * Static utility class for prompting user for input.
 */
class Menu {
    private static final Prompter prompter = new Prompter(new Scanner(System.in));

    // TODO: Remove once com.word.Difficult is renamed to com.word.Difficulty and once it is public.
    // TODO: ask Jay how to test, or if it's even necessary to test.
    private enum Difficulty {
        EASY("E"),
        MEDIUM("M"),
        HARD("H");

        private final String alias;

        Difficulty(String alias) {
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
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
    }

    private Menu() {}

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

        for(Difficulty difficulty: Difficulty.values()) {
            text.append(String.format("[%s] %s\n", difficulty.getAlias(), difficulty));
            regex.append(difficulty.alias).append("|");
        }
        text.append("> ");
        regex.append(")");
        String answer = prompter.prompt(text.toString(), regex.toString(), "");
        return Difficulty.fromAlias(answer);
    }
}
