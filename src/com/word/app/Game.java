package com.word.app;

import com.word.Difficulty;

import java.util.List;

public class Game {
    public static final String TITLE = "Raining Words";

    enum Option {
        PLAY, VIEW_HISTORY, QUIT
    }

    public void run() {
        Menu.welcome();
        Option option;
        do {
             option = Menu.promptForOption();
            if (option == Option.PLAY)
                startGame();
            else if (option == Option.VIEW_HISTORY)
                startViewHistory();
        } while(option != Option.QUIT);
        // Menu.displayQuitMessage();
   }
    public void startGame() {
        String playerName = Menu.promptForName();
        Player player = new Player(playerName);
        Difficulty difficulty = Menu.promptForDifficulty();
        List<String> remainingWords = difficulty.getWords();
        GameWindow window = new GameWindow(player, remainingWords);
        boolean isPlaying = true;
        while (isPlaying) {
            showGameWindow(window);
            while (window.hasWordsRemaining()) {
                ; // keeep running game
            }
            hideGameWindow(window);
            displayStatistics();
            isPlaying = Menu.promptToContinue();
        }
    }

    private void showGameWindow(GameWindow window) {
        window.setVisible(true);
        window.showWindow();
    }

    private void hideGameWindow(GameWindow window) {
        window.setVisible(false);
        window.reset();
    }

    private void displayStatistics() {
        // Display score
        // Display statistics
    }

    private void startViewHistory() {
        System.out.println("Game match history");
    }

}