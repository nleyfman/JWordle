package model;

import ui.GameView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Manipulates the game state in response to user input
public class GameController {
    private String target;
    private Game game;
    private GameView gameView;
    private boolean won;
    private List<String> comparison;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public GameController() {
        target = generateTarget();
        game = new Game();
        gameView = new GameView();
        won = false;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public boolean play() {
        int maxGuesses = gameView.selectMaxGuesses();
        gameView.printResultKey();
        while (game.getNumGuesses() < maxGuesses) {
            processWord(gameView.makeGuess());
            if (won) {
                break;
            }
        }
        if (!won) {
            gameView.gameLost(target);
        }
        return won;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void processWord(Guess guess) {
        if (!isValid(guess)) {
            gameView.notValidWord();
            return;
        }
        game.addGuess(guess);
        if (guess.getWord().equals(target)) {
            gameView.gameWon();
            won = true;
            return;
        }
        comparison = new ArrayList<>();
        for (int i = 0; i < guess.getWord().length(); i++) {
            char guessLetter = guess.getWord().charAt(i);
            char targetLetter = target.charAt(i);
            if (guessLetter == targetLetter) {
                comparison.add("c");
            } else if (target.indexOf(guessLetter) != -1) {
                comparison.add("i");
            } else {
                comparison.add("x");
            }
        }
        gameView.printGuess(guess, comparison);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public boolean isValid(Guess guess) {
        try {
            Scanner scan = new Scanner(new File("./data/WordList.txt"));
            while (scan.hasNext()) {
                String next = scan.nextLine();
                if (guess.getWord().equals(next)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public String generateTarget() {
        List<String> words = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File("./data/WordList.txt"));
            while (scan.hasNext()) {
                String next = scan.nextLine();
                words.add(next);
            }
            Random rand = new Random();
            int index = rand.nextInt(words.size());
            return words.get(index);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean getWon() {
        return won;
    }

    public String getTarget() {
        return target;
    }

    public Game getGame() {
        return game;
    }

    public List<String> getComparison() {
        return comparison;
    }

}
