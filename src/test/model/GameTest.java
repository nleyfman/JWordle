package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game testGame;

    @BeforeEach
    void runBefore() {
        testGame = new Game();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testGame.getNumGuesses());
    }

    @Test
    void testAddGuess() {
        Guess guess = new Guess("tails");
        testGame.addGuess(guess);
        assertEquals(1, testGame.getNumGuesses());
    }

}
