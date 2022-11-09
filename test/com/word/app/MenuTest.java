package com.word.app;

import com.apps.util.Prompter;
import com.word.Difficulty;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MenuTest {



    @Test
    public void promptForName_shouldReturnNameOfUser_whenValidInputProvided() {
        // Valid user means between two and 16 characters.
        Menu.setScannerSource("responses/responses.txt");
        String name = Menu.promptForName();
        assertEquals("Jay", name);

    }

    @Test
    public void promptForName_shouldPromptAgain_whenInvalidInputProvided() {
        // for example, a number is invalid.
    }

    @Test
    public void promptToContinue_shouldContinueToPromptUser_whileUserProvidedInvalidInput() {
        Menu.setScannerSource("responses/responses.txt");
        Boolean continuePromt = Menu.promptToContinue();
        assertEquals(true, continuePromt);
    }

    @Test
    public void promptForDifficulty_shouldContinueToPrompt_whenUserProvidedInvalidInput() {
        Menu.setScannerSource("responses/promptForDifficulty_invalidInput.txt");
        assertEquals(Difficulty.EASY, Menu.promptForDifficulty());
    }

    @Test
    public void promptForDifficulty_shouldCreateEnum_whenUserProvidesFirstLetterOfEnum() {
        Menu.setScannerSource("responses/promptForDifficulty_valid.txt");
        assertEquals(Difficulty.EASY, Menu.promptForDifficulty());
        assertEquals(Difficulty.EASY, Menu.promptForDifficulty());
        assertEquals(Difficulty.MEDIUM, Menu.promptForDifficulty());
        assertEquals(Difficulty.MEDIUM, Menu.promptForDifficulty());
        assertEquals(Difficulty.HARD, Menu.promptForDifficulty());
        assertEquals(Difficulty.HARD, Menu.promptForDifficulty());
    }

    @Test
    public void welcome_shouldReadFileAndDisplayMessage_withoutIOException() {
        try {
            Menu.welcome();
        } catch(Exception e) {
            fail("Should not have thrown an exception. Is it unable to read the file?");
        }
    }

    @Test
    public void displayQuitMessage_shouldReadFileAndDisplayMessage_withoutIOException() {

    }

    @Test
    public void promptForOption_shouldReturnOptionEnum_whenUserProvidesValidInput() {
        // basically when input matches the regex.
    }
}