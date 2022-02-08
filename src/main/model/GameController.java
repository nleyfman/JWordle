package model;

import model.Game;
import model.Guess;
import ui.GameView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// manipulates the game state in response to user input
public class GameController {
    private int maxGuesses;
    private String target;
    private Game game;
    private GameView gameView;
    private List<String> comparison;

    public GameController() {
        target = "hello";
        game = new Game();
        gameView = new GameView();
    }

    public void play() {
        Guess n = new Guess("hello");
        processWord(n);
    }


    public void processWord(Guess guess) {
        if (isValid(guess)) {
            comparison = new ArrayList<>();
            game.addGuess(guess);
            String guessedWord = guess.getWord();
            for (int i = 0; i < guessedWord.length(); i++) {
                for (int j = 0; j < target.length(); j++) {
                    if (guessedWord.substring(i, i + 1).equals(target.substring(j, j + 1))) {
                        if (i == j) {
                            comparison.add("c");
                        } else {
                            comparison.add("i");
                        }
                        break;
                    }
                }
                comparison.add("x");
            }
            gameView.printGuess(guess, comparison);
        } else {
            gameView.notValidWord();
        }
    }

    public boolean isValid(Guess guess) {
        Scanner scan = new Scanner("./data/WordList.txt");
        while (scan.hasNext()) {
            String next = scan.nextLine().toString();
            if (guess.getWord().equals(next)) {
                return true;
            }
        }
        return false;
    }

}
