package ui;

import model.Statistics;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameMasterUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JComboBox<String> printCombo;
    private JDesktopPane desktop;
    private JInternalFrame game;
    private GameMaster gm;
    private JTextField textField;
    private static final String JSON_STORE = "./data/gamestate.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

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

    private boolean startUp() {
        int loadBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to load a previous game?",
                "Load game?",
                JOptionPane.YES_NO_OPTION);
        if (loadBoolean == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    // Code reference: JsonSerializationDemo from Phase 2 example file
    // MODIFIES: this
    // EFFECTS: loads game state from file
    private void loadGameState() {
        try {
            GameController gc = jsonReader.readGameController();
            Statistics stats = jsonReader.readStats();
            run(gc, stats);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Code reference: JsonSerializationDemo from Phase 2 example file
    // EFFECTS: saves game state to file
    private void saveGameState(GameController gc, Statistics stats) {
        try {
            jsonWriter.open();
            jsonWriter.write(gc, stats);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: starts new games as long as user wishes to continue; prints out stats at the end
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void run(GameController gc, Statistics stats) {
        boolean continuePlaying = true;
        if (stats == null || gc == null) {
            stats = new Statistics(0, 0, new ArrayList<>());
            gc = new GameController("", null, 0);
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

    private boolean askForStats() {
        int statsBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to see your statistics?",
                "View Statistics?",
                JOptionPane.YES_NO_OPTION);
        if (statsBoolean == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    private boolean askToPlayAgain() {
        int playAgainBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to play again?",
                "Play again?",
                JOptionPane.YES_NO_OPTION);
        if (playAgainBoolean == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    private boolean askToSave() {
        int saveBoolean = JOptionPane.showConfirmDialog(
                null,
                "Would you like to save your game?",
                "Save game?",
                JOptionPane.YES_NO_OPTION);
        if (saveBoolean == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

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

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            GameMasterUI.this.requestFocusInWindow();
        }
    }

    private void centerOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    public static void main(String[] args) {
        new GameMasterUI();
    }
}

