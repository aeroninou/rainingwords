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

    private static final String FONT_NAME = "Monospaced";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final JPanel playerInfoArea = new JPanel(new GridLayout(2, 1, 15, 15));
    private final JLabel playerNameLabel = new JLabel("Name", SwingConstants.CENTER);
    private final JPanel scoreArea = new JPanel();
    private final JLabel totalWordsLeftLabel = new JLabel("Number of words left: ");
    private final JLabel totalWordsLeftCountLabel = new JLabel("0");
    private final JLabel wordsCorrectLabel = new JLabel("  Correct Words: ");
    private final JLabel wordsCorrectCountLabel = new JLabel("0");

    private final JPanel wordFallingArea = new JPanel(null);
    private final Collection<JLabel> fallingLabels = new ArrayList<>();

    // Area where player types a word.
    private final JPanel inputArea = new JPanel(new GridBagLayout());
    private final JLabel typeHerePromptLabel = new JLabel("Type Here: ");
    private final JTextField wordInputField = new JTextField(15);
    private final JLabel wordEchoLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton startButton = new JButton("Start");
    private boolean isStartClicked;

    // Constructor(s)
    public GameWindow(Player player, int fallingLabelCount) {
        super(Game.TITLE);
        // Build the UI
        buildPlayerInfoArea(player);
        buildPlayerScore();
        buildWordFallingArea(fallingLabelCount);
        buildWordInputArea();
        setFrameOptions();
    }

    public Rectangle getWordFallingBounds(){
        return wordFallingArea.getBounds();
    }

    private void buildPlayerScore(){

        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        totalWordsLeftCountLabel.setFont(font);
        totalWordsLeftCountLabel.setForeground(Color.CYAN);
        totalWordsLeftLabel.setFont(font);


        wordsCorrectLabel.setFont(font);
        wordsCorrectCountLabel.setFont(font);
        wordsCorrectCountLabel.setForeground(Color.CYAN);

    }

    public Collection<JLabel> getFallingLabels() {
        return fallingLabels;
    }

    public boolean isStartClicked() {
        return isStartClicked;
    }

    public JTextField getPlayerInput() {
        return wordInputField;
    }

    public void updateScore(int score) {
        wordsCorrectCountLabel.setText(String.valueOf(score));
    }

    public void updateEchoLabel(String text, Color color) {
        wordEchoLabel.setText(text);
        wordEchoLabel.setForeground(color);
    }

    public void showWindow(Player player){
        playerNameLabel.setText(player.getName());
        updateScore(player.getScore());
        updateEchoLabel(" ", Color.BLACK);
        startButton.setVisible(true);
        isStartClicked = false;
    }

    public void reset() {
        // Reset the score to 0.
        int wordScore = 0;
        updateScore(wordScore);
        wordsCorrectCountLabel.setText(String.valueOf(wordScore));
        updateEchoLabel(" ", Color.BLACK);
        // Clear the echo label.
        wordEchoLabel.setText(" ");
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void buildPlayerInfoArea(Player player) {

        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        wordsCorrectLabel.setFont(font);
        wordsCorrectCountLabel.setFont(font);

        playerNameLabel.setFont(font);
        playerNameLabel.setText("Player: " + player.getName());
        playerNameLabel.setForeground(Color.GRAY);

        scoreArea.add(totalWordsLeftLabel);
        scoreArea.add(totalWordsLeftCountLabel);
        scoreArea.add(wordsCorrectLabel);
        scoreArea.add(wordsCorrectCountLabel);

        playerInfoArea.add(playerNameLabel);
        playerInfoArea.add(scoreArea);
        playerInfoArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(BorderLayout.NORTH, playerInfoArea);
    }

    private void buildWordFallingArea(int fallingLabelCount) {
        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        wordFallingArea.setBackground(Color.white);
        // Add labels of the falling words to the word falling area.
        for (int i = 0; i < fallingLabelCount; i++) {
            JLabel wordLabel = new JLabel("");
            wordLabel.setBounds(0, 0, 100, 25);
            wordLabel.setFont(font);
            fallingLabels.add(wordLabel); // Create empty labels.
            wordFallingArea.add(wordLabel);
        }
        wordFallingArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(BorderLayout.CENTER, wordFallingArea);
    }

    private void buildWordInputArea() {
        Font font = new Font(FONT_NAME, Font.BOLD, 18);
        inputArea.setBackground(Color.BLUE);
        typeHerePromptLabel.setFont(font);
        typeHerePromptLabel.setForeground(Color.WHITE);
        wordInputField.setForeground(Color.BLACK);
        wordInputField.setFont(font);
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

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            wordInputField.requestFocus();
            isStartClicked = true;
            // Hide the start button.
            startButton.setVisible(false);
        }
    }
}
