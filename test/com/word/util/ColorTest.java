package com.word.util;

import com.word.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {
    private final String text = "hello";

    @Test
    public void setFontColor_shouldContainOriginalText() {
        String greenText = Color.GREEN.setFontColor(text);
        assertTrue(greenText.contains(text));
        // regular text color.
        System.out.println(text);
        // should be green color.
        System.out.println(greenText);
    }

    @Test
    public void setBackgroundColor_shouldContainOriginalText() {
        String greenText = Color.GREEN.setBackgroundColor(text);
        assertTrue(greenText.contains(text));
        // regular text color.
        System.out.println(text);
        // should be green color.
        System.out.println(greenText);
    }
}