package model;

import org.json.JSONArray;
import org.json.JSONObject;
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

    @Test
    void testToJsonKey() {
        Guess guess = new Guess("tails");
        testGame.addGuess(guess);
        JSONObject jsonGame = testGame.toJson();
        Iterable<String> keys = jsonGame.keySet();
        boolean contains = false;
        for(final String key : keys) {
            if (key.equals("guesses")) {
                contains = true;
                break;
            }
        }
        assertTrue(contains);
    }

    @Test
    void testToJsonValue() {
        Guess guess = new Guess ("tails");
        testGame.addGuess(guess);
        JSONObject jsonGame = testGame.toJson();
        JSONArray jsonArray = jsonGame.getJSONArray("guesses");
        JSONObject jsonGuess = (JSONObject) jsonArray.get(0);
        assertEquals("tails", jsonGuess.getString("guess"));
    }

}
