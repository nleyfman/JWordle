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

    @Test
    void testCompare() {
        String target1 = "grain";
        String target2 = "meals";
        assertEquals(5, testGuess.compare(target1).size());
        assertEquals("x", testGuess.compare(target1).get(0));
        assertEquals("c", testGuess.compare(target1).get(2));
        assertEquals("i", testGuess.compare(target2).get(3));
    }
}
