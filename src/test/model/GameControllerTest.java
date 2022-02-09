package model;

import java.io.FileNotFoundException;
import java.io.File;

import jdk.jfr.StackTrace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController testGameController;

    @BeforeEach
    void runBefore() {
        testGameController = new GameController();
    }

    @Test
    void testConstructor() {
        assertFalse(testGameController.getWon());
        assertEquals(5, testGameController.getTarget().length());
    }

    @Test
    void testCorrectProcessWord() {
        String target = testGameController.getTarget();
        Guess guess = new Guess(target);
        testGameController.processWord(guess);
        assertTrue(testGameController.getWon());
        assertEquals(1, testGameController.getGame().getNumGuesses());
    }

    @Test
    void testInvalidProcessWord() {
        Guess guess = new Guess("sevens");
        testGameController.processWord(guess);
        assertFalse(testGameController.getWon());
        assertEquals(0, testGameController.getGame().getNumGuesses());
    }

    @Test
    void testIncorrectProcessWord() {
        String target = testGameController.getTarget();
        Guess guess;
        if (target.equals("aback")) {
            guess = new Guess("abate");
        } else {
            guess = new Guess("aback");
        }
        testGameController.processWord(guess);
        assertFalse(testGameController.getWon());
        assertEquals(5, testGameController.getComparison().size());
    }
}
