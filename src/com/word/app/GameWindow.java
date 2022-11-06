package com.word.app;

import com.word.Word;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;

class GameWindow extends JFrame {

    public static final String GAME_TITLE = "Raining Words";
    private static final String FONT_NAME = "SansSerif";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;


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
    private final JLabel wordsCorrectCountLabel = new JLabel("0");

    private final JPanel wordFallingArea = new JPanel(null);
    private final Collection<JLabel> fallingLabels = new ArrayList<>();

    // Area where player types a word.
    private final JPanel inputArea = new JPanel(new GridBagLayout());
    private final JLabel typeHerePromptLabel = new JLabel("Type Here: ");
    private final JTextField wordInputField = new JTextField(15);
    private final JLabel wordEchoLabel = new JLabel("", SwingConstants.CENTER);

    // Constructor(s)
    public GameWindow(Player player) {
        super(GAME_TITLE);
        buildUI(player);
        setFrameOptions();

        System.out.println("northPanel: " + playerInfoArea.getBounds());
        System.out.println("centerPanel: "  + wordFallingArea.getBounds());
        System.out.println("southPanel: " + inputArea.getBounds());
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
            JLabel wordLabel = new JLabel("Word" + 1);
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
        wordEchoLabel.setText("echo");

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

    private class WordInputFieldListener implements ActionListener {
        /**
         * Runs whenever the player presses the RETURN (ENTER) key on keyboard after having typed a word.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String word = wordInputField.getText();
            // Start by assuming wrong word was typed.
            Color color = Color.RED;
            // See if any falling words has matching text.
            for (JLabel fallingLabel: fallingLabels) {
                String fallingWord = fallingLabel.getText();
                if (fallingWord.equals(word)){
                    // TODO: for now just make the word disappear.. but this may not be what we want long term.
                    fallingLabel.setText("");
                    // Correct word, so echo the word in green to indicate they got it right.
                    color = Color.GREEN;
                }
            }
            // Always reset input field after user has pressed enter.
            wordInputField.setText("");
            // Echo word in green if correct, or red if incorrect.
            wordEchoLabel.setText(word);
            wordEchoLabel.setForeground(color);
        }
    }

    public static void main(String[] args) {
        new GameWindow(new Player("Sergio"));
    }
}
