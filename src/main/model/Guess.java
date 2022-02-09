package model;

// Represents one guess
public class Guess {
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
}
