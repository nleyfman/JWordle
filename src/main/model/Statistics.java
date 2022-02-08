package model;

// Represents the number of wins and losses over the entire run of the program
public class Statistics {
    private int numWins;
    private int numLosses;

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

    public void addWin() {
        numWins++;
    }

    public void addLoss() {
        numLosses++;
    }
}
