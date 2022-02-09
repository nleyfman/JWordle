package model;

import java.util.ArrayList;
import java.util.List;

// Represents the game state
public class Game {
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
    }

    public int getNumGuesses() {
        return guesses.size();
    }

}
