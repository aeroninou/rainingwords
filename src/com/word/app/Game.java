package com.word.app;

import com.word.Difficulty;
import com.word.Option;
import com.word.Player;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game {
    public static final String TITLE = "Raining Words";
    // At most 3 words can "fall" or "rain" at the same time.
    private static final int WORD_FALLING_COUNT = 3;
    private static final int RANDOM_WORD_COUNT = 10;
    private static final long START_BUTTON_CHECK_PAUSE_DURATION = 50;
    public int wordsLeftCounter;

    GameWindow window;
    Player player;

    /**
     * Starts the application. Allows user to play or quit.
     */
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

    /**
     * Runs if the player asked to PLAY when prompted during run().
     *
     * Prompts player for their name and desired difficulty.
     * Creates a JFrame window, and does not start round until clicks start.
     * Once round is over, player can choose to continue or not.
     */
    private void startGame() {
        String playerName = Menu.promptForName();
        player = new Player(playerName);
        Difficulty startingDifficulty = Menu.promptForDifficulty();
        // Creates the window, but does not yet display it.
        window = new GameWindow(player, WORD_FALLING_COUNT);
        window.getPlayerInput().addActionListener(new WordInputFieldListener());
        boolean isPlaying = true;
        while (isPlaying) {
            int scoreAtStartOfRound = player.getScore();
            showGameWindow(window);
            // Wait for player to click the Start button on the JFrame.
            while (!window.isStartClicked()) {
                pause(START_BUTTON_CHECK_PAUSE_DURATION);
            }
            beginRound(startingDifficulty);
            hideGameWindow(window);
            displayStatistics(player, scoreAtStartOfRound);
            isPlaying = Menu.promptToContinue();
        }
        Menu.displayQuitMessage();
        window.close();
    }

    /**
     * Starts updating the game sequence, making words fall until none are left.
     *
     * @param difficulty Decides how fast words fall, and how difficult words are.
     */
    private void beginRound(Difficulty difficulty) {
        List<String> remainingWords = pickRandomWords(difficulty);
        // Player clicked start, so we start making labels "rain"!
        Collection<JLabel> fallingLabels = window.getFallingLabels();

        while(!remainingWords.isEmpty() || someLabelHasText(fallingLabels)) {
            for (JLabel label: fallingLabels) {
                FallingWordsUpdater.updateLabel(label, window.getWordFallingBounds());
                // Update the text on labels that player matched.
                if (label.getText().equals("") && !remainingWords.isEmpty()) {
                    label.setText(remainingWords.remove(0));
                    wordsLeftCounter = remainingWords.size();
                }
            }
            pause(difficulty.getPauseDuration()); // Wait a bit allowing labels to fall again
            window.updateWordsLeftCounter(wordsLeftCounter);
        }
    }

    /**
     * Pauses execution of the game.
     *
     * @param pauseDuration How long to pause for.
     */
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
        window.showWindow(player);
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

    private class WordInputFieldListener implements ActionListener {
        /**
         * Runs whenever the player presses the RETURN (ENTER) key on keyboard after having typed a word.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField wordInputField = (JTextField) e.getSource();
            String playerText = wordInputField.getText();
            java.awt.Color color = java.awt.Color.RED;
            for (JLabel label: window.getFallingLabels()) {
                String fallingWordText = label.getText();
                if (playerText.equals(fallingWordText)) {
                    color = java.awt.Color.GREEN;
                    label.setText("");
                    // update player's score in game.
                    player.setScore(player.getScore() + 1);
                    // update player's score in UI... window.updateScore()
                    window.updateScore(player.getScore());
                }
            }
            wordInputField.setText("");
            window.updateEchoLabel(playerText, color);
        }
    }
}