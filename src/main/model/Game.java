package model;

import java.util.ArrayList;
import java.util.List;

// keep track of the game state
public class Game {
    private List<Guess> guesses;

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
