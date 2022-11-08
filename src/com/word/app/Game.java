package com.word.app;

import com.word.Difficulty;

import java.util.List;

public class Game {

    public static final String TITLE = "Raining Words";

    enum Option {
        PLAY, VIEW_HISTORY, QUIT
    }

    public void run() {
        // Menu.welcome();
        Option option = Option.QUIT;
        // executes 1 or more times
        do {
            // option = Menu.promptForOption();
            if (option == Option.PLAY)
                startGame();
            else if (option == Option.VIEW_HISTORY)
                startViewHistory();
        } while(option != Option.QUIT);
        // Menu.displayQuitMessage();
   }
    public void startGame() {
        // Request player name
        String playerName = Menu.promptForName();
        Player player = new Player(playerName);
        // Request difficulty
        Difficulty difficulty = Menu.promptForDifficulty();
        List<String> remainingWords = difficulty.getWords();
        // Get some words from the difficulty list (not all of them probably.. maybe 20 out of the 200 at a time)
        // Create game window with the player name, and the words that we made above.
        GameWindow window = new GameWindow(player, remainingWords);
        boolean isPlaying = true;
        while (isPlaying) {
            showGameWindow(window); // this also initializes the threads
            while (!remainingWords.isEmpty())
                ; // keeep running game
            // set the player score
            hideGameWindow(window);
            displayStatistics();
            isPlaying = Menu.promptToContinue();
        }
    }

    private void showGameWindow(GameWindow window) {
        // setVisible true
        window.setVisible(true);
        window.showWindow();
    }

    private void hideGameWindow(GameWindow window) {
        // Stop showing the window, but keep it running.
        window.setVisible(false);

        // Resets all UI components, such as clearing label text and score.
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