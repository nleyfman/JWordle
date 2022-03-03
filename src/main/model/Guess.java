package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents one guess
public class Guess implements Writable {
    private final String word;

    // REQUIRES: word is a 5-letter word
    // MODIFIES: this
    // EFFECTS: word in guess is set to word
    public Guess(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guess", word);
        return json;
    }
}
