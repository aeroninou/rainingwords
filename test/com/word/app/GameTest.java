package com.word.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void run_shouldQuit_whenPlayerChoosesQuitOption() {
    }

    @Test
    public void run_shouldStartGame_whenPlayerChoosesPlayOption() {
    }

    @Test
    public void someLabelHasText_shouldReturnFalse_whenAllLabelsEmpty() {
    }

    @Test
    public void someLabelHasText_shouldReturnTrue_whenSomeLabelHasNonEmptyTextString() {
    }

    @Test
    public void pickRandomWords_shouldReturnConfiguredNumberOfWords() {
        // there's a constant in game that decides how many words are returned
        // the size should be that big.
    }

    @Test
    public void pickRandomWords_shouldLeaveDifficultyWordListUnchanged() {
        // for example, if we took 10 words out of easy, and easy had 500 words, then it should still have 500 after
    }

    @Test
    public void showGameWindow_shouldHaveVisibleWindow() {
        // if it has a getVisible method or something, it should be true
    }

    @Test
    public void hideGameWindow_shouldHaveInvisibleWindow() {
    }
}