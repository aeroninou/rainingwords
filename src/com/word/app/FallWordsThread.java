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