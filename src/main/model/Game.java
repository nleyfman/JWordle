package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents the game state
public class Game implements Writable {
    private List<Guess> guesses;

    // MODIFIES: this
    // EFFECTS: guesses is set to a new empty ArrayList
    public Game() {
        guesses = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new guess to guesses
    public void addGuess(Guess guess) {
        guesses.add(guess);
        EventLog.getInstance().logEvent(new Event("Guess added."));
    }

    public int getNumGuesses() {
        return guesses.size();
    }

    // EFFECTS: returns game as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guesses", guessesToJson());
        return json;
    }

    // EFFECTS: returns guesses in this game as a JSON array
    public JSONArray guessesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Guess g : guesses) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }
}
