package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessTest {
    private Guess testGuess;

    @BeforeEach
    void runBefore() {
        testGuess = new Guess("blame");
    }

    @Test
    void testConstructor() {
        assertEquals("blame", testGuess.getWord());
    }
}
