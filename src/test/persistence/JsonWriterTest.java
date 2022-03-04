package persistence;

import model.Game;
import model.Guess;
import model.Statistics;
import org.junit.jupiter.api.Test;
import ui.GameController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code reference: JsonSerializationDemo from Phase 2 example file
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game();
            GameController gc = new GameController("claim", game, 5);
            Statistics stats = new Statistics(0, 0, new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/my\0/illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGameController() {
        try {
            Game game = new Game();
            game.addGuess(new Guess("claim"));
            GameController gc = new GameController("brain", game, 5);
            Statistics stats = new Statistics(0, 0, new ArrayList<>());
            stats.addLoss();
            stats.addWin();
            stats.addWinStat(4);
            JsonWriter writer = new JsonWriter("./data/testWriterGameState");
            writer.open();
            writer.write(gc, stats);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGameState");
            gc = reader.readGameController();
            game = gc.getGame();
            assertEquals(1, game.getNumGuesses());
            List<Guess> guesses = game.getGuesses();
            assertEquals("claim", guesses.get(0).getWord());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterStatistics() {
        try {
            Game game = new Game();
            game.addGuess(new Guess("claim"));
            GameController gc = new GameController("brain", game, 5);
            Statistics stats = new Statistics(0, 0, new ArrayList<>());
            stats.addLoss();
            stats.addWin();
            stats.addWinStat(4);
            JsonWriter writer = new JsonWriter("./data/testWriterGameState");
            writer.open();
            writer.write(gc, stats);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGameState");
            stats = reader.readStats();
            assertEquals(1, stats.getNumWins());
            assertEquals(1, stats.getNumLosses());
            List<Integer> winStats = stats.getWinStats();
            assertEquals(4, winStats.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
