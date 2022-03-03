package ui;

import model.Game;
import model.Statistics;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Checks if game is done, responsible for stats
public class GameMaster {
    private static final String JSON_STORE = "./data/gamestate.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the GameMaster
    public GameMaster() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        boolean prevLoad = startUp();
        if (prevLoad) {
            loadGameState();
        } else {
            run(null, null);
        }
    }

    // MODIFIES: this
    // EFFECTS: starts new games as long as user wishes to continue; prints out stats at the end
    public void run(GameController gc, Statistics stats) {
        boolean continuePlaying = true;
        if (stats == null && gc == null) {
            stats = new Statistics(0, 0, new ArrayList<>());
            gc = new GameController("", null, 0);
        }
        while (continuePlaying) {
            GameController.GameResult result = gc.play();
            if (result == GameController.GameResult.SAVE) {
                saveGameState(gc, stats);
                return;
            } else if (result == GameController.GameResult.WON) {
                stats.addWin();
                stats.addWinStat(gc.getGame().getNumGuesses());
            } else {
                stats.addLoss();
            }
            if (askToSave()) {
                saveGameState(new GameController("", null, 0), stats);
            }
            continuePlaying = askToPlayAgain();
            if (continuePlaying) {
                gc = new GameController("", null, 0);
            }
        }
        if (askForStats()) {
            printStats(stats);
        }
    }



    // REQUIRES: scanner input is non-empty string
    // EFFECTS: returns true if yes, false otherwise
    private boolean askToPlayAgain() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to play another game? [y/n]");
        return scan.next().equals("y");
    }

    private boolean askToSave() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to save your game? [y/n]");
        return scan.next().equals("y");
    }

    // REQUIRES: scanner input is non-empty string
    // EFFECTS: returns true if yes, false otherwise
    private boolean askForStats() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to see your statistics? [y/n]");
        return scan.next().equals("y");
    }

    // EFFECTS: prints out statistics
    private void printStats(Statistics stats) {
        int wins = stats.getNumWins();
        int losses = stats.getNumLosses();
        System.out.println("wins - " + wins);
        System.out.println("losses - " + losses);
        if (stats.averageGuesses() != -1) {
            System.out.println("Average number of guesses per win - " + stats.averageGuesses());
        }
    }

    private boolean startUp() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to JWordle! Please enter [y] to load the last saved game, "
                + "or [n] to continue on a fresh file! You can also enter [q] instead of a guess to quit and save.");
        return scan.next().equals("y");
    }

    // Code reference: JsonSerializationDemo
    private void saveGameState(GameController gc, Statistics stats) {
        try {
            jsonWriter.open();
            jsonWriter.write(gc, stats);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Code reference: JsonSerializationDemo
    private void loadGameState() {
        try {
            GameController gc = jsonReader.readGameController();
            Statistics stats = jsonReader.readStats();
            run(gc, stats);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}


