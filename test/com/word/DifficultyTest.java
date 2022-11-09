package com.word;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DifficultyTest {

    @Test
    public void fromAlias_returnsValidEnum_whenMatchingAliasPassed() {
    }

    @Test
    public void fromAlias_returnsNull_whenGivenInvalidAlias() {

    }

    @Test
    public void getWords_shouldRemainUnmodified_whenClientTriesToModify() {
        List<String> easyWords = Difficulty.EASY.getWords();
        easyWords.remove(0);    // should not be allowed! read-only access
        // In the getWords, change it to return a copy of the list instead.
    }
}