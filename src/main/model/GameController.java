package model;

import model.Game;
import model.Guess;
import ui.GameView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Manipulates the game state in response to user input
public class GameController {
    private int maxGuesses;
    private String target;
    private Game game;
    private GameView gameView;
    private List<String> comparison;

    public GameController() {
        target = generateTarget();
        game = new Game();
        gameView = new GameView();
    }

    public void play() {
        maxGuesses = gameView.selectMaxGuesses();
        gameView.key();
        processWord(gameView.makeGuess());
    }


    public void processWord(Guess guess) {
        if (isValid(guess)) {
            comparison = new ArrayList<>();
            game.addGuess(guess);
            String guessedWord = guess.getWord();
            for (int i = 0; i < guessedWord.length(); i++) {
                char guessLetter = guessedWord.charAt(i);
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
        } else {
            gameView.notValidWord();
        }
    }

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
        }
        return false;
    }

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
            return "Error: File not found";
        }
    }

}
