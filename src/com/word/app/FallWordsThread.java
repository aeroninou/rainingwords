package com.word.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

class FallWordsThread extends Thread {
    private final Random rand = new Random();
    private static final int GREEN_CUT_OFF = 100;
    private static final int ORANGE_CUTOFF = 250;
    private static final int DISAPPEAR_Y_CUTOFF = 400;
    private static final int RANGE_X = 100;
    private static final double MOVE_LEFT_CHANCE = 0.5;
    private final java.util.List<String> remainingWords;

    public JLabel wordLabel;

    public FallWordsThread(List<String> remainingWords, JLabel wordLabel) {
        this.remainingWords = remainingWords;
        this.wordLabel = wordLabel;
    }

    @Override
    public void run() {
        // See if there are any other words to make fall.
        while (!remainingWords.isEmpty()) {
            String nextWord = remainingWords.remove(0);
            wordLabel.setText(nextWord);
            // Move it back to the top
            wordLabel.setLocation(350, -20);
            // Make the word "fall".
            // If the text is blank, it means the player matched it or the word went out of bounds.
            int yCoordinate;
            int xCoordinate;
            do {
                xCoordinate = wordLabel.getBounds().x;
                yCoordinate = wordLabel.getBounds().y;

                // Figure out new position.
                xCoordinate = getNewXCoordinate(xCoordinate);
                // Recalculate x position so the label can be seen.
                if (xCoordinate < 5 || xCoordinate > 775)
                    continue;
                yCoordinate += 20;
                wordLabel.setLocation(xCoordinate, yCoordinate);

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
            } while( yCoordinate < DISAPPEAR_Y_CUTOFF && !wordLabel.getText().equals(""));
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