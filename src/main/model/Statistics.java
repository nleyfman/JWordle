package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents the number of wins and losses over the entire run of the program
public class Statistics implements Writable {
    private int numWins;
    private int numLosses;
    private List<Integer> winStats;

    // MODIFIES: this
    // EFFECTS: initializes wins and losses to zero
    public Statistics(int wins, int losses, ArrayList<Integer> stats) {
        numWins = wins;
        numLosses = losses;
        winStats = stats;
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

    // MODIFIES: this
    // EFFECTS: adds the number of attempts it took to reach a win to winStats
    public void addWinStat(int num) {
        winStats.add(num);
    }

    // EFFECTS: returns the average number of attempts it took to reach a win, -1 if no games were won
    public double averageGuesses() {
        double total = 0;
        if (winStats.size() == 0) {
            return -1;
        }
        for (int num : winStats) {
            total += num;
        }
        return total / winStats.size();
    }

    public int getNumWins() {
        return numWins;
    }

    public int getNumLosses() {
        return numLosses;
    }

    public List<Integer> getWinStats() {
        return winStats;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("numWins", numWins);
        json.put("numLosses", numLosses);
        json.put("winStats", winStatsToJson());
        return json;
    }

    // EFFECTS: returns list of attempts until each win as a JSON array
    public JSONArray winStatsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i : winStats) {
            jsonArray.put(i);
        }
        return jsonArray;
    }


}
