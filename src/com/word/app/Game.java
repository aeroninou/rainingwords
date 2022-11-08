package com.word.app;

import com.word.Difficulty;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public class Game {
    public static final String TITLE = "Raining Words";
    // At most 3 words can "fall" or "rain" at the same time.
    private static final int WORD_FALLING_COUNT = 3;

    public void run() {
        Menu.welcome();
        Menu.Option option;
        do {
            option = Menu.promptForOption();
            if (option == Menu.Option.PLAY)
                startGame();
            else if (option == Menu.Option.VIEW_HISTORY)
                startViewHistory();
        } while (option != Menu.Option.QUIT);
        // Menu.displayQuitMessage();
    }

    public void startGame() {
        String playerName = Menu.promptForName();
        Player player = new Player(playerName);
        Difficulty startingDifficulty = Menu.promptForDifficulty();
        // Change GameWindow so that it only takes a player (not remainingWords anymore)
        GameWindow window = new GameWindow(player, WORD_FALLING_COUNT);
        boolean isPlaying = true;
        while (isPlaying) {
            // Pick words according to difficulty.
            List<String> remainingWords = pickRandomWords(startingDifficulty);

            // Get the labels whose position we will be updating.
            java.util.Collection<JLabel> fallingLabels = window.getFallingLabels();

            // Ensure player can see the JFrame window.
            showGameWindow(window);

            // Wait for player to click the Start button on the JFrame
            while (!window.isStartClicked()) {
                try {
                    Thread.sleep(50);   // Wait a bit before checking again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Now that player has pressed start, we can start making words fall.
            while(!remainingWords.isEmpty() || someLabelHasText(fallingLabels)) {
                // See if there is an empty label to update.
                for (JLabel label: fallingLabels) {
                    if (label.getText().equals("") && !remainingWords.isEmpty()) {
                        // Player matched a label, get another word
                        label.setText(remainingWords.remove(0));
                    }
                     FallingWordsUpdater.updateLabelPosition(label);
                }
                pause(300); // Wait a bit allowing labels to fall again
            }
            hideGameWindow(window);
            displayStatistics();
            isPlaying = Menu.promptToContinue();
        }
    }

    private void pause(long pauseDuration) {
        try {
            Thread.sleep(pauseDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean someLabelHasText(Collection<JLabel> fallingLabels) {
        // TODO: Aeron, try to implement this so that it's true if at least one label has text
        // otherwise, have it return false.
        return false;
    }

    private List<String> pickRandomWords(Difficulty difficulty) {
        // TODO: Aeron, try to implement this method.
        // Get the words from the difficulty
        // Shuffle the list of words
        // After shuffling, return a list with only, say, 10 words
        // Recommend: a class constant with the number 10, and use that here.
        return null;
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