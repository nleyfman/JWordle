package ui;

import model.Game;
import model.Guess;
import ui.GameView;

import java.io.File;

// manipulates the game state in response to user input
public class GameController {
    private int maxGuesses;
    private String target;
    private Game game;
    private GameView gameView;

    public GameController() {
        game = new Game();
        gameView = new GameView();
    }

    public void play() {
    }

    public void processWord(String word) {}

    public boolean isValid(Guess guess) {
        return false;
    }

}
