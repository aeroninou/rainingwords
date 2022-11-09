package com.word;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DifficultyTest {



    @Test
    public void fromAlias_returnsValidEnum_whenMatchingAliasPassed() {
        assertEquals(Difficulty.EASY, Difficulty.fromAlias("E"));
        assertEquals(Difficulty.MEDIUM, Difficulty.fromAlias("M"));
        assertEquals(Difficulty.HARD, Difficulty.fromAlias("H"));
        assertEquals(Difficulty.EASY, Difficulty.fromAlias("e"));
        assertEquals(Difficulty.MEDIUM, Difficulty.fromAlias("m"));
        assertEquals(Difficulty.HARD, Difficulty.fromAlias("h"));
    }

    @Test
    public void fromAlias_returnsNull_whenGivenInvalidAlias() {
        assertEquals(null, Difficulty.fromAlias("WRONG_ANSWER"));
    }

    @Test
    public void getWords_shouldRemainUnmodified_whenClientTriesToModify() {
        List<String> easyWords = Difficulty.EASY.getWords();
        easyWords.remove(0); // should change original easy word, read-only access
        assertNotEquals(easyWords, Difficulty.EASY.getWords());
    }
}