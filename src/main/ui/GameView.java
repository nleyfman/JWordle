package ui;

import model.Guess;

import java.util.List;
import java.util.Scanner;

// Renders the game state and takes in user input
public class GameView {
    private final Scanner input;

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

    // REQUIRES:
    //
    public int selectMaxGuesses() {
        System.out.println("Enter the maximum number of guesses you wish to play with.");
        return input.nextInt();
    }

    public Guess makeGuess() {
        System.out.println("Enter a 5 letter word for your guess.");
        return new Guess(input.next());
    }

    public void printResultKey() {
        System.out.println("\nThe results of your guess will be indicated as follows:");
        System.out.println("\tc - the letter is in the correct place in the word");
        System.out.println("\ti - the letter is present in the word, but is in the wrong place");
        System.out.println("\tx - the letter is not present in the word\n");
    }

    public void gameLost(String target) {
        System.out.println("You lost the game :( The correct word was " + target + ".");
    }
}
