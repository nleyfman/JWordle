package ui;

import model.Event;
import model.EventLog;
import model.Game;
import model.Statistics;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Checks if game is done, responsible for stats
public class GameMasterUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/gamestate.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the GameMaster
    public GameMasterUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        boolean prevLoad = startUp();
        if (prevLoad) {
            loadGameState();
        } else {
            run(null, null);
        }
    }

    // EFFECTS: generates a user input window to ask if the user wants to load a previous game
    private boolean startUp() {
        int loadBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to load a previous game?",
                "Load game?",
                JOptionPane.YES_NO_OPTION);
        return loadBoolean == JOptionPane.YES_OPTION;
    }

    // MODIFIES: this
    // EFFECTS: starts new games as long as user wishes to continue; generates a popup with stats at the end
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void run(GameControllerUI gc, Statistics stats) {
        boolean continuePlaying = true;
        if (stats == null || gc == null) {
            stats = new Statistics(0, 0, new ArrayList<>());
            gc = new GameControllerUI("", new Game(), 0);
        }

        while (continuePlaying) {
            gc.setBorder(new TitledBorder("Game"));
            JFrame frame = new JFrame("Jwordle");
            frame.setContentPane(gc);
            frame.setSize(WIDTH,HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            centerOnScreen();
            frame.setVisible(true);
            if (gc.play()) {
                stats.addWin();
                stats.addWinStat(gc.getGame().getNumGuesses());
            } else {
                stats.addLoss();
            }
            if (askToSave()) {
                saveGameState(new GameControllerUI("", new Game(), 0), stats);
            }
            continuePlaying = askToPlayAgain();
            if (continuePlaying) {
                gc = new GameControllerUI("", new Game(), 0);
            }
        }
        if (askForStats()) {
            printStats(stats);
        }
        printLog(EventLog.getInstance());
    }

    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // EFFECTS: generate y/n popup to ask if user would like to see statistics
    private boolean askForStats() {
        int statsBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to see your statistics?",
                "View Statistics?",
                JOptionPane.YES_NO_OPTION);
        return statsBoolean == JOptionPane.YES_OPTION;
    }

    // EFFECTS: generate y/n popup to ask if user would like to play again
    private boolean askToPlayAgain() {
        int playAgainBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to play again?",
                "Play again?",
                JOptionPane.YES_NO_OPTION);
        return playAgainBoolean == JOptionPane.YES_OPTION;
    }

    // EFFECTS: generate y/n popup to ask if user would like to save their game
    private boolean askToSave() {
        int saveBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to save your game?",
                "Save game?",
                JOptionPane.YES_NO_OPTION);
        return saveBoolean == JOptionPane.YES_OPTION;
    }

    // EFFECTS: generates a popup window with statistics
    private void printStats(Statistics stats) {
        int wins = stats.getNumWins();
        int losses = stats.getNumLosses();
        if (stats.averageGuesses() != -1) {
            JOptionPane.showMessageDialog(null, "wins - " + wins + "\n" + "losses - " + losses
                    + "\n" + "Average number of guesses per win - " + stats.averageGuesses());
        } else {
            JOptionPane.showMessageDialog(null, "wins - " + wins + "\n" + "losses - " + losses);
        }
    }

    // EFFECTS: centers window on screen
    private void centerOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // Code reference: JsonSerializationDemo from Phase 2 example file
    // MODIFIES: this
    // EFFECTS: loads game state from file
    private void loadGameState() {
        try {
            GameControllerUI gc = jsonReader.readGameController();
            Statistics stats = jsonReader.readStats();
            run(gc, stats);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Code reference: JsonSerializationDemo from Phase 2 example file
    // EFFECTS: saves game state to file
    private void saveGameState(GameControllerUI gc, Statistics stats) {
        try {
            jsonWriter.open();
            jsonWriter.write(gc, stats);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}

