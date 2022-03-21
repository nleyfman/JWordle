package persistence;

import model.Game;
import model.Guess;
import model.Statistics;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.GameController;
import ui.GameControllerPrev;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Code reference: JsonSerializationDemo from Phase 2 example file
// Represents a reader that reads game state from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads GameController from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameController readGameController() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject gameControllerObject = jsonObject.getJSONObject("gameController");
        return parseGameController(gameControllerObject);
    }

    // EFFECTS: parses GameController from JSON object and returns it
    private GameController parseGameController(JSONObject jsonObject) {
        String target = jsonObject.getString("target");
        int maxGuesses = jsonObject.getInt("maxGuesses");
        Game game = new Game();
        JSONObject gameObject = jsonObject.getJSONObject("game");
        JSONArray jsonArray = gameObject.getJSONArray("guesses");
        for (Object json : jsonArray) {
            JSONObject nextGuess = (JSONObject) json;
            Guess guess = new Guess(nextGuess.getString("guess"));
            game.addGuess(guess);
        }
        return new GameController(target, game, maxGuesses);
    }

    // EFFECTS: reads Statistics from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Statistics readStats() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject statsObject = jsonObject.getJSONObject("stats");
        return parseStatistics(statsObject);
    }

    // EFFECTS: parses Statistics from JSON object and returns it
    private Statistics parseStatistics(JSONObject jsonObject) {
        int numWins = jsonObject.getInt("numWins");
        int numLosses = jsonObject.getInt("numLosses");
        JSONArray jsonArray = jsonObject.getJSONArray("winStats");
        ArrayList<Integer> stats = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            int numAttempts = jsonArray.getInt(i);
            stats.add(numAttempts);
        }
        return new Statistics(numWins, numLosses, stats);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

}
