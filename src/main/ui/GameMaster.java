package ui;

// checks if game is done, responsible for stats
public class GameMaster {

    public void run() {
        Boolean continuePlaying = true;
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
