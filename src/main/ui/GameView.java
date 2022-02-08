package ui;

import model.Guess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// renders the game state
public class GameView {
    private Scanner input;

    public GameView() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void printGuess(Guess guess, List<String> comparison) {
        System.out.println(guess.getWord());
        System.out.println(String.join("", comparison));
    }

    public void notValidWord() {
        System.out.println("Word not in word list. Please try again.");
    }

    public void gameWon() {
        System.out.println("You guessed the word!");
    }
}
