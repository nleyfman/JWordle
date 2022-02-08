package ui;

import model.GameController;

// checks if game is done, responsible for stats
public class GameMaster {

    public GameMaster() {
        run();
    }

    public void run() {
        boolean continuePlaying = true;
        while (continuePlaying) {
            GameController gameController = new GameController();
            gameController.play();
            continuePlaying = askToPlayAgain();
        }
    }

    private boolean askToPlayAgain() {
        return false;
    }
}
