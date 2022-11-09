package com.word.app;

import com.apps.util.Prompter;
import com.word.Option;
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
    public void promptForDifficulty_shouldCreateEnum_whenUserProvidesFirstLetterOfEnum() {

    }

    @Test
    public void welcome_shouldReadFileAndDisplayMessage_withoutIOException() {

    }

    @Test
    public void displayQuitMessage_shouldReadFileAndDisplayMessage_withoutIOException() {

    }

    @Test
    public void promptForOption_shouldReturnOptionEnum_whenUserProvidesValidInput() {
        // basically when input matches the regex.
        Menu.setScannerSource("responses/promptForOption_valid.txt");
        assertEquals(Option.PLAY, Menu.promptForOption());
        assertEquals(Option.PLAY, Menu.promptForOption());
        assertEquals(Option.QUIT, Menu.promptForOption());
        assertEquals(Option.QUIT, Menu.promptForOption());
    }

    @Test
    public void prompForOption_shouldContinueToPromptUser_whileUserProvidedInvalidInput() {
        Menu.setScannerSource("responses/promptForOption_invalid.txt");
        assertEquals(Option.PLAY, Menu.promptForOption());

    }

}