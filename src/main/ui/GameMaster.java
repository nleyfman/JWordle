package ui;

import model.Statistics;

import java.util.Scanner;

// Checks if game is done, responsible for stats
public class GameMaster {

    // EFFECTS: runs the GameMaster
    public GameMaster() {
        run();
    }

    // MODIFIES: this
    // EFFECTS: starts new games as long as user wishes to continue; prints out stats at the end
    public void run() {
        Statistics stats = new Statistics();
        boolean continuePlaying = true;
        while (continuePlaying) {
            GameController gameController = new GameController();
            if (gameController.play()) {
                stats.addWin();
                stats.addWinStat(gameController.getGame().getNumGuesses());
            } else {
                stats.addLoss();
            }
            continuePlaying = askToPlayAgain();
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
}
