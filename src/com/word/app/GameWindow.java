package com.word.app;

import com.word.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

public class GameWindow extends JFrame {

    private static final String FONT_NAME = "SansSerif";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private int wordScore;

    private final JPanel playerInfoArea = new JPanel(new GridLayout(2, 1, 15, 15));
    private final JLabel playerNameLabel = new JLabel();
    private final JPanel scoreArea = new JPanel();
    private final JLabel wordsCorrectLabel = new JLabel("Correct Words: ");
    private final JLabel wordsCorrectCountLabel = new JLabel();

    private final JPanel wordFallingArea = new JPanel(null);
    private final Collection<JLabel> fallingLabels = new ArrayList<>();

    // Area where player types a word.
    private final JPanel inputArea = new JPanel(new GridBagLayout());
    private final JLabel typeHerePromptLabel = new JLabel("Type Here: ");
    private final JTextField wordInputField = new JTextField(15);
    private final JLabel wordEchoLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton startButton = new JButton("Start");
    private boolean isStartClicked;
    private final Player player;

    // Constructor(s)
    public GameWindow(Player player, int fallingLabelCount) {
        super(Game.TITLE);
        this.player = player;
        // Build the UI
        buildPlayerInfoArea(player);
        buildWordFallingArea(fallingLabelCount);
        buildWordInputArea();
        setFrameOptions();
    }

    public Rectangle getWordFallingBounds(){
        return wordFallingArea.getBounds();
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

    private void buildWordFallingArea(int fallingLabelCount) {
        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        // Add labels of the falling words to the word falling area.
        for (int i = 0; i < fallingLabelCount; i++) {
            JLabel wordLabel = new JLabel("");
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
        wordEchoLabel.setText(" ");
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
        playerNameLabel.setText(player.getName());
        wordScore = player.getScore();
        wordsCorrectCountLabel.setText(String.valueOf(wordScore));
        wordEchoLabel.setText(" ");
        startButton.setVisible(true);
        isStartClicked = false;
    }

    public void reset() {
        // Reset the score to 0.
        wordScore = 0;
        wordsCorrectCountLabel.setText(String.valueOf(wordScore));

        // Clear text on falling labels and bring them back to the top.
        for(JLabel fallingLabel: fallingLabels) {
            fallingLabel.setText("");
            fallingLabel.setLocation(350, -20);
        }

        // Clear the echo label.
        wordEchoLabel.setText(" ");
    }

    public Collection<JLabel> getFallingLabels() {
        return fallingLabels;
    }

    public boolean isStartClicked() {
        return isStartClicked;
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            wordInputField.requestFocus();
            isStartClicked = true;
            // Hide the start button.
            startButton.setVisible(false);
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
                    fallingLabel.setLocation(350, -20);
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
            player.setScore(wordScore);
            wordsCorrectCountLabel.setText(String.valueOf(wordScore));
        }
    }
}
