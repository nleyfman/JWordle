package ui;

import model.Game;
import model.Guess;
import org.json.JSONObject;
import persistence.Writable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Manipulates the game state in response to user input
public class GameControllerPrev implements Writable {
    private String target;
    private Game game;
    private GameView gameView;
    private boolean won;
    private int maxGuesses;
    private boolean quit;

    // EFFECTS: target is set to a random 5-letter word from the list if not given;
    //          initializes a game
    //          initializes a gameview
    //          sets won to false
    //          initializes max guesses
    //          sets quit to false
    public GameControllerPrev(String target, Game game, int maxGuesses) {
        if (target.equals("")) {
            this.target = generateTarget();
        } else {
            this.target = target;
        }
        if (game == null) {
            this.game = new Game();
        } else {
            this.game = game;
        }
        gameView = new GameView();
        won = false;
        this.maxGuesses = maxGuesses;
        quit = false;
    }

    // REQUIRES: maxGuesses > 0
    // MODIFIES: this
    // EFFECTS: processes user inputs for guesses and returns if game was won or not
    public GameResult play() {
        if (maxGuesses == 0) {
            maxGuesses = gameView.selectMaxGuesses();
        }
        gameView.printResultKey();
        while (game.getNumGuesses() < maxGuesses) {
            processWord(gameView.makeGuess());
            if (won) {
                return GameResult.WON;
            }
            if (quit) {
                return GameResult.SAVE;
            }
        }
        gameView.gameLost(target);
        return GameResult.LOST;
    }

    // MODIFIES: game
    // EFFECTS: checks whether word is a quit request, whether it is a valid word and adds it to
    // the game if it is, compares guess and target, and checks whether the game has been won
    public void processWord(Guess guess) {
        if (guess.getWord().equals("q")) {
            quit = true;
            return;
        }
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
        List<String> comparison = guess.compare(target);
        gameView.printGuess(guess, comparison);
    }

    // EFFECTS: searches through WordList for user guess; returns true if found, false if not found
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

    // EFFECTS: generates a random word from the WordList
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

    public Game getGame() {
        return game;
    }

    // Represents result of a play
    public enum GameResult {
        WON, LOST, SAVE
    }

    public String getTarget() {
        return target;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    // EFFECTS: returns GameController as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("target", target);
        json.put("game", game.toJson());
        json.put("maxGuesses", maxGuesses);
        return json;
    }
}
