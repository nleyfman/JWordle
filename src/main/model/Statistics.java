package model;

// Represents the number of wins and losses over the entire run of the program
public class Statistics {
    private int numWins;
    private int numLosses;

    // MODIFIES: this
    // EFFECTS: initializes wins and losses to zero
    public Statistics() {
        numWins = 0;
        numLosses = 0;
    }

    public int getNumWins() {
        return numWins;
    }

    public int getNumLosses() {
        return numLosses;
    }

    // MODIFIES: this
    // EFFECTS: adds one win to numWins
    public void addWin() {
        numWins++;
    }

    // MODIFIES: this
    // EFFECTS: adds one loss to numLosses
    public void addLoss() {
        numLosses++;
    }
}
