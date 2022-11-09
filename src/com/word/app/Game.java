package com.word.app;

import com.word.Difficulty;
import com.word.Option;
import com.word.Player;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game {
    public static final String TITLE = "Raining Words";
    private static final int WORD_FALLING_COUNT = 3;
    private static final int RANDOM_WORD_COUNT = 10;
    private static final long START_BUTTON_CHECK_PAUSE = 50;

    public void run() {
        Menu.welcome();
        Option option;
        do {
            option = Menu.promptForOption();
            if (option == Option.PLAY)
                startGame();
        } while (option != Option.QUIT);
        Menu.displayQuitMessage();
    }

    public void startGame() {
        String playerName = Menu.promptForName();
        Player player = new Player(playerName);
        Difficulty startingDifficulty = Menu.promptForDifficulty();
        // Creates the window, but does not yet display it.
        GameWindow window = new GameWindow(player, WORD_FALLING_COUNT);
        boolean isPlaying = true;
        while (isPlaying) {
            int scoreAtStartOfRound = player.getScore();
            List<String> remainingWords = pickRandomWords(startingDifficulty);
            showGameWindow(window);
            // Wait for player to click the Start button on the JFrame
            while (!window.isStartClicked()) {
                pause(START_BUTTON_CHECK_PAUSE);
            }

            // Player clicked start, so we start making labels "rain"!
            java.util.Collection<JLabel> fallingLabels = window.getFallingLabels();
            while(!remainingWords.isEmpty() || someLabelHasText(fallingLabels)) {
                for (JLabel label: fallingLabels) {
                    // Update the text on labels that player matched.
                    if (label.getText().equals("") && !remainingWords.isEmpty()) {
                        label.setText(remainingWords.remove(0));
                    }
                    // Update label text, position, and colo.
                    FallingWordsUpdater.updateLabel(label, window.getWordFallingBounds());
                }
                pause(300); // Wait a bit allowing labels to fall again
            }
            // Round has finished, redirect them to console.
            hideGameWindow(window);
            displayStatistics(player, scoreAtStartOfRound);
            isPlaying = Menu.promptToContinue();
        }
        Menu.displayQuitMessage();
        window.close();
    }

    private void pause(long pauseDuration) {
        try {
            Thread.sleep(pauseDuration);
        } catch (InterruptedException ignored) {}
    }

    private boolean someLabelHasText(Collection<JLabel> fallingLabels) {
        for (JLabel label : fallingLabels){
            if (!label.getText().isEmpty()){
                return true;
            }
        }
        return false;
    }

    private List<String> pickRandomWords(Difficulty difficulty) {
        List<String> randomWords = difficulty.getWords();
        Collections.shuffle(randomWords);
        List<String> words;
        words = randomWords.subList(0, RANDOM_WORD_COUNT);
        return words;
    }

    private void showGameWindow(GameWindow window) {
        window.setVisible(true);
        window.showWindow();
    }

    private void hideGameWindow(GameWindow window) {
        window.setVisible(false);
        window.reset();
    }

    private void displayStatistics(Player player, int scoreAtStartOfRound) {
        // Menu.displayScore();
        int currentRoundWordCount = player.getScore() - scoreAtStartOfRound;
        System.out.printf("You got %s words this round.\n", currentRoundWordCount);
        System.out.printf("Your total score is now: %s\n", player.getScore());
    }

}