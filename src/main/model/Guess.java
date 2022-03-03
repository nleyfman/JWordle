package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

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

    // REQUIRES: target and words are the same length
    // EFFECTS: compares this to a given string and produces a string array of letter comparisons
    public List<String> compare(String target) {
        List<String> comparison = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char guessLetter = word.charAt(i);
            char targetLetter = target.charAt(i);
            if (guessLetter == targetLetter) {
                comparison.add("c");
            } else if (target.indexOf(guessLetter) != -1) {
                comparison.add("i");
            } else {
                comparison.add("x");
            }
        }
        return comparison;
    }

    // EFFECTS: returns guess as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guess", word);
        return json;
    }
}
