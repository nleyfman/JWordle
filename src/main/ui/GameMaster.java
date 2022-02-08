package ui;

import model.GameController;
import model.Statistics;

import java.util.Scanner;

// Checks if game is done, responsible for stats
public class GameMaster {

    public GameMaster() {
        run();
    }

    public void run() {
        Statistics stats = new Statistics();
        boolean continuePlaying = true;
        while (continuePlaying) {
            GameController gameController = new GameController();
            if (gameController.play()) {
                stats.addWin();
            } else {
                stats.addLoss();
            }
            continuePlaying = askToPlayAgain();
        }
        if (askForStats()) {
            printStats(stats);
        }
    }

    private boolean askToPlayAgain() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to play another game? [y/n]");
        return scan.next().equals("y");
    }

    private boolean askForStats() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to see your statistics? [y/n]");
        return scan.next().equals("y");
    }

    private void printStats(Statistics stats) {
        int wins = stats.getNumWins();
        int losses = stats.getNumLosses();
        System.out.println("wins - " + wins);
        System.out.println("losses - " + losses);
    }
}
