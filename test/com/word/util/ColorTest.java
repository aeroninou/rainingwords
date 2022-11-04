package com.word.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {
    @Test
    public void setFontColor_shouldContainOriginalText() {
        String text = "hello world";
        String greenText = Color.GREEN.setFontColor(text);
        assertTrue(greenText.contains(text));
        // regular text color.
        System.out.println(text);
        // should be green color.
        System.out.println(greenText);
    }
}