package model;

import java.util.ArrayList;
import java.util.List;

// Represents the game state
public class Game {
    private final List<Guess> guesses;

    public Game() {
        guesses = new ArrayList<>();
    }

    // add guess to list of guesses
    public void addGuess(Guess guess) {
        guesses.add(guess);
    }

    public int getNumGuesses() {
        return guesses.size();
    }

}
