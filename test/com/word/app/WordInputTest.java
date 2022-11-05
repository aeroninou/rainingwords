package com.word.app;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WordInputTest {
    private Robot robot;

    @Before
    public void setUp() throws Exception {
        robot = new Robot();
    }

    @Test
    public void shouldCloseImmediately_whenWordsMatch() {
        List<String> wordsToMatch = new ArrayList<>(List.of("aeron", "sergio", "vlad"));
        WordInput input = new WordInput(wordsToMatch);

        // Correct words.lad
        while(!wordsToMatch.isEmpty()) {
            String word = wordsToMatch.get(0);
            for(char letter: word.toCharArray()) {
                robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(letter));
                robot.delay(100);
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(250);
        }
        assertTrue(input.allWordsMatched());
    }

    @Test
    public void shouldColorForegroundRed_whenIncorrectWord() throws InterruptedException {
        // Incorrect word; should be marked red.
        List<String> wordsToMatch = new ArrayList<>(List.of("CORRECT"));
        WordInput input = new WordInput(wordsToMatch);

        String incorrect = "INCORRECT";
        for(char letter: incorrect.toCharArray()) {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(letter));
            robot.delay(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
        assertFalse(input.allWordsMatched());
    }
}