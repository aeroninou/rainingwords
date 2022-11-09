package com.word.app;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class FallingWordsUpdater {
    private static final Random rand = new Random();
    private static final int GREEN_CUT_OFF = 100;
    private static final int ORANGE_CUTOFF = 250;
    private static final int DISAPPEAR_Y_CUTOFF = 400;
    private static final int RANGE_X = 100;
    private static final double MOVE_LEFT_CHANCE = 0.5;

    public static void updateLabelPosition(JLabel label) {
        // Do not update empty labels.
        if (label.getText().equals(""))
            return;

        // Player missed this one; empty it and bring it back top.
        if (label.getBounds().y >= DISAPPEAR_Y_CUTOFF) {
            label.setText("");
            label.setLocation(350, -20);
            return;
        }

        int x = label.getBounds().x;
        do {
            x = getNewXCoordinate(x);
        } while(x < 5 || x > 775);
        int y = label.getBounds().y;
        y += 20;
        label.setLocation(x, y);
        Color color;
        if (y <= GREEN_CUT_OFF)
            color = Color.GREEN;
        else if (y <= ORANGE_CUTOFF)
            color = Color.ORANGE;
        else
            color = Color.RED;
        label.setForeground(color);
    }

    private static int getNewXCoordinate(int xCoordinate) {
        int deltaX = rand.nextInt(RANGE_X) + 1;
        if (negative()) deltaX = -deltaX;
        return xCoordinate + deltaX;
    }

    private static boolean negative() {
        return Math.random() < MOVE_LEFT_CHANCE;
    }
}