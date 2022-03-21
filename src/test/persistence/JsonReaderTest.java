package persistence;

import model.Game;
import model.Guess;
import model.Statistics;
import org.junit.jupiter.api.Test;
import ui.GameControllerUI;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code reference: JsonSerializationDemo from Phase 2 example file
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameControllerUI gc = reader.readGameController();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNewGameController() {
        JsonReader reader = new JsonReader("./data/testReaderNewGameState");
        try {
            GameControllerUI gc = reader.readGameController();
            assertEquals(0, gc.getMaxGuesses());
            assertEquals("adage", gc.getTarget());
            Game game = gc.getGame();
            assertEquals(0, game.getNumGuesses());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGameController() {
        JsonReader reader = new JsonReader("./data/testReaderGameState");
        try {
            GameControllerUI gc = reader.readGameController();
            Game game = gc.getGame();
            assertEquals(2, game.getNumGuesses());
            List<Guess> guesses = game.getGuesses();
            assertEquals("claim", guesses.get(0).getWord());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderStatistics() {
        JsonReader reader = new JsonReader("./data/testReaderGameState");
        try {
            Statistics stats = reader.readStats();
            assertEquals(1, stats.getNumWins());
            assertEquals(1, stats.getNumLosses());
            List<Integer> winStats = stats.getWinStats();
            assertEquals(2, winStats.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
