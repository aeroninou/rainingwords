package com.word.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GameWindow extends JFrame {

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

    private final java.util.List<String> remainingWords;
    private final JPanel wordFallingArea = new JPanel(null);
    private final Collection<JLabel> fallingLabels = new ArrayList<>();

    // Area where player types a word.
    private final JPanel inputArea = new JPanel(new GridBagLayout());
    private final JLabel typeHerePromptLabel = new JLabel("Type Here: ");
    private final JTextField wordInputField = new JTextField(15);
    private final JLabel wordEchoLabel = new JLabel("", SwingConstants.CENTER);

    // Constructor(s)
    public GameWindow(Player player, java.util.List<String> words) {
        super(GAME_TITLE);
        this.remainingWords = words;
        buildUI(player);
        setFrameOptions();

        System.out.println("northPanel: " + playerInfoArea.getBounds());
        System.out.println("centerPanel: "  + wordFallingArea.getBounds());
        System.out.println("southPanel: " + inputArea.getBounds());
        for (JLabel wordLabel: fallingLabels) {
            wordLabel.setText(wordLabel.getText());
            wordLabel.setBounds(350, -20, 100, 25);
            new FallWordsThread(wordLabel).start();
        }
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

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JLabel wordLabel: fallingLabels) {
                wordLabel.setText(wordLabel.getText());
                wordLabel.setBounds(350, -20, 100, 25);
                new FallWordsThread(wordLabel).start();
            }
        }
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

    private class FallWordsThread extends Thread {
        private final Random rand = new Random();
        private static final int GREEN_CUT_OFF = 100;
        private static final int ORANGE_CUTOFF = 250;
        private static final int DISAPPEAR_Y_CUTOFF = 400;
        private static final int RANGE_X = 100;
        private static final double MOVE_LEFT_CHANCE = 0.5;

        public JLabel wordLabel;

        public FallWordsThread(JLabel wordLabel) {
            this.wordLabel = wordLabel;
        }

        @Override
        public void run() {
            while(!wordLabel.getText().equals("")) {
                int yCoordinate = wordLabel.getBounds().y;
                int xCoordinate = wordLabel.getBounds().x;
                while( yCoordinate < DISAPPEAR_Y_CUTOFF && !wordLabel.getText().equals("")) {
                    // Figure out new position.
                    xCoordinate = getNewXCoordinate(xCoordinate);
                    if (xCoordinate < 5 || xCoordinate > 775)
                        continue;
                    yCoordinate += 20;
                    wordLabel.setBounds(xCoordinate, yCoordinate, 100, 25);

                    // See if color needs to change.
                    Color color;
                    if (yCoordinate <= GREEN_CUT_OFF)
                        color = Color.GREEN;
                    else if (yCoordinate <= ORANGE_CUTOFF)
                        color = Color.ORANGE;
                    else
                        color = Color.RED;
                    wordLabel.setForeground(color);

                    pause(300);
                }
                // Make word disappear...
                wordLabel.setText("");
                // See if there are any other words to make fall
                if (!remainingWords.isEmpty()) {
                    // Set the text to the next word.
                    String nextWord = remainingWords.remove(0); // [aeron, sergio, vlad], so remove aeron
                    wordLabel.setText(nextWord);

                    // Move it back to the top
                    wordLabel.setBounds(350, -20, 100, 25);
                }
            }

        }

        private void pause(long pause) {
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ignored) {
            }
        }

        private int getNewXCoordinate(int xCoordinate) {
            int deltaX = rand.nextInt(RANGE_X) + 1;
            if (negative()) deltaX = -deltaX;
            return xCoordinate + deltaX;
        }

        private boolean negative() {
            return Math.random() < MOVE_LEFT_CHANCE;
        }


    }
}
