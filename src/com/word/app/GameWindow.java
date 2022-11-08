package com.word.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class GameWindow extends JFrame {

    private static final String FONT_NAME = "SansSerif";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private int wordScore;


    // At most 3 words can "fall" or "rain" at the same time.
    private final int WORD_FALLING_COUNT = 3;

    /**
     * TODO: Display their name at the top, and the number of words they have gotten.
     *
     * Maybe only show the score at the end.
     * In addition, perhaps, to the number of words per minute (the rate).
     */

    private final JPanel playerInfoArea = new JPanel(new GridLayout(2, 1, 15, 15));
    private final JLabel playerNameLabel = new JLabel();
    private final JPanel scoreArea = new JPanel();
    private final JLabel wordsCorrectLabel = new JLabel("Correct Words: ");
    private final JLabel wordsCorrectCountLabel = new JLabel();

    private java.util.List<String> remainingWords;
    private final JPanel wordFallingArea = new JPanel(null);
    private final Collection<JLabel> fallingLabels = new ArrayList<>();

    // Area where player types a word.
    private final JPanel inputArea = new JPanel(new GridBagLayout());
    private final JLabel typeHerePromptLabel = new JLabel("Type Here: ");
    private final JTextField wordInputField = new JTextField(15);
    private final JLabel wordEchoLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton startButton = new JButton("Start");

    // Constructor(s)
    public GameWindow(Player player, java.util.List<String> words) {
        super(Game.TITLE);
        setRemainingWords(words);
        buildUI(player);
        setFrameOptions();
    }

    public void setRemainingWords(java.util.List<String> words) {
        // Make a copy of the words being passed in.
        this.remainingWords = new ArrayList<>(words);
    }

    public Rectangle getWordFallingBounds(){
        return wordFallingArea.getBounds();
    }

    private void buildUI(Player player) {
        buildPlayerInfoArea(player);
        buildWordFallingArea();
        buildWordInputArea();
    }

    private void buildPlayerInfoArea(Player player) {

        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        wordsCorrectLabel.setFont(font);
        wordsCorrectCountLabel.setFont(font);

        playerNameLabel.setFont(font);
        playerNameLabel.setText("Player: " + player.getName());
        playerNameLabel.setForeground(Color.GRAY);

        scoreArea.add(wordsCorrectLabel);
        scoreArea.add(wordsCorrectCountLabel);

        playerInfoArea.add(playerNameLabel);
        playerInfoArea.add(scoreArea);
        playerInfoArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(BorderLayout.NORTH, playerInfoArea);
    }

    private void buildWordFallingArea() {
        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        // Add labels of the falling words to the word falling area.
        for (int i = 0; i < WORD_FALLING_COUNT; i++) {
            String word = this.remainingWords.remove(0);
            JLabel wordLabel = new JLabel(word);
            wordLabel.setBounds(350, -20, 100, 25);
            wordLabel.setFont(font);
            fallingLabels.add(wordLabel); // Create empty labels.
            wordFallingArea.add(wordLabel);
        }
        wordFallingArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(BorderLayout.CENTER, wordFallingArea);
    }

    private void buildWordInputArea() {
        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        typeHerePromptLabel.setFont(font);
        wordInputField.setFont(font);
        wordInputField.addActionListener(new WordInputFieldListener());
        wordEchoLabel.setFont(font);
        wordEchoLabel.setText("");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.addActionListener(new StartButtonListener());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputArea.add(typeHerePromptLabel, gbc);
        gbc.gridx = 1;
        inputArea.add(wordInputField, gbc);
        gbc.gridy = 1;
        inputArea.add(wordEchoLabel, gbc);
        inputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(BorderLayout.SOUTH, inputArea);
        inputArea.add(startButton);
    }

    private void setFrameOptions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setSize(WIDTH, HEIGHT);
        // To prevent errors with inconsistency,like the word not falling all the way to bottom, prevent resizing window.
        setResizable(false);
        // Places the window in the center of the screen.
        setLocationRelativeTo(null);
        // TODO: Window is visible as soon as it's created. May not want this to be the case...
        setVisible(true);
    }

    public void showWindow(){

        playerNameLabel.setText(Player.getName());
        wordScore = Player.getScore();
        wordsCorrectCountLabel.setText(String.valueOf(wordScore));

        for(JLabel fallingLabel: fallingLabels) {
            fallingLabel.setText("");
            getWordFallingBounds();
        }
        wordEchoLabel.setText("");
    }

    public void reset() {
        // Reset the score to 0.
        wordScore = 0;
        wordsCorrectCountLabel.setText(String.valueOf(wordScore));

        // Clear text on falling labels and bring them back to the top.
        for(JLabel fallingLabel: fallingLabels) {
            fallingLabel.setText("");
            fallingLabel.setBounds(350, -20, 100, 25);
        }

        // Clear the echo label.
        wordEchoLabel.setText("");
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            wordInputField.requestFocus();
            for (JLabel wordLabel: fallingLabels) {
                wordLabel.setText(wordLabel.getText());
                wordLabel.setBounds(350, -20, 100, 25);
                new FallWordsThread(remainingWords, wordLabel).start();
            }
        }

    }
    private class WordInputFieldListener implements ActionListener {
        /**
         * Runs whenever the player presses the RETURN (ENTER) key on keyboard after having typed a word.
         */
        // Constructor that receives the game.

        @Override
        public void actionPerformed(ActionEvent e) {
            String word = wordInputField.getText();
            // Start by assuming wrong word was typed.
            Color color = Color.RED;
            // See if any falling words has matching text.
            for (JLabel fallingLabel: fallingLabels) {
                String fallingWord = fallingLabel.getText();
                if (fallingWord.equals(word)){
                    // Pass the word to the game or do it in here, let the game handle updating the list and the score.
                    // game.updateScore(fallingWord);
                    // TODO: for now just make the word disappear.. but this may not be what we want long term.
                    fallingLabel.setText("");
                    // Correct word, so echo the word in green to indicate they got it right.
                    color = Color.GREEN;
                    wordScore++;
                    break;  // since we already found a matching word, we exit loop.
                }
            }
            // Always reset input field after user has pressed enter.
            wordInputField.setText("");
            // Echo word in green if correct, or red if incorrect.
            wordEchoLabel.setText(word);
            wordEchoLabel.setForeground(color);
            Player.setScore(wordScore);
            wordsCorrectCountLabel.setText(String.valueOf(wordScore));
        }

    }

}
