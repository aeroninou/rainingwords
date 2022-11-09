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
    private static final int DELTA_Y = 20;  // Fixed distance each word falls each time.

    public static void updateLabel(JLabel label, Rectangle bounds) {
        if (label.getText().isBlank() || label.getBounds().y >= DISAPPEAR_Y_CUTOFF) {
            moveBackTop(label, bounds); // do nothing.
        } else {
            int x = label.getBounds().x;
            do {
                x = getNewXCoordinate(x);
            } while(x < 5 || x + label.getWidth() > bounds.getWidth());
            int y = label.getBounds().y;
            y += DELTA_Y;
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
    }

    public static void moveBackTop(JLabel label, Rectangle bounds) {
        label.setText("");
        int centerX = bounds.x + bounds.width / 2;
        int topY = -20;
        label.setLocation(centerX, topY);
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