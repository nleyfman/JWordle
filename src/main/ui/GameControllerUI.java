package ui;

import model.Game;
import model.Guess;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Code reference: ListDemo in Oracle Swing documentation
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/
// uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// Represents UI for one game
public class GameControllerUI extends JPanel implements ListSelectionListener, Writable {
    private JList list;
    private DefaultListModel listModel;
    private String target;
    private Game game;
    private boolean won;
    private int maxGuesses;

    private static final String addGuess = "Add Guess";
    private static final String viewResult = "View Result";
    private JButton compareButton;
    private JButton guessButton;
    private JTextField textInput;
    private ImageIcon legend;
    private ImageIcon winIcon;
    private ImageIcon loseIcon;

    // EFFECTS: target is set to a random 5 letter word from list if not given
    //          initializes a game
    //          obtains max guesses
    //          initializes list graphic
    //          sets won to false
    public GameControllerUI(String target, Game game, int maxGuesses) {
        super(new BorderLayout());
        if (target.equals("")) {
            this.target = generateTarget();
        } else {
            this.target = target;
        }
        this.game = game;
        won = false;
        this.maxGuesses = maxGuesses;
        listModel = new DefaultListModel();
        listModel.addElement("Guess");
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);
        addButtonPane();
        initializeIcons();
    }

    // EFFECTS: initializes icons with images
    private void initializeIcons() {
        legend = new ImageIcon("data/legend.png");
        winIcon = new ImageIcon("data/melodycheer.png");
        loseIcon = new ImageIcon("data/melodycry.png");
    }

    // EFFECTS: adds pane of buttons to frame
    private void addButtonPane() {
        guessButton = new JButton(addGuess);
        GuessListener guessListener = new GuessListener(guessButton);
        guessButton.setActionCommand(addGuess);
        guessButton.addActionListener(guessListener);
        guessButton.setEnabled(false);

        compareButton = new JButton(viewResult);
        compareButton.setActionCommand(viewResult);
        compareButton.addActionListener(new CompareResultListener());

        textInput = new JTextField(10);
        textInput.addActionListener(guessListener);
        textInput.getDocument().addDocumentListener(guessListener);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(compareButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(textInput);
        buttonPane.add(guessButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // REQUIRES: maxGuesses > 0
    // MODIFIES: this
    // EFFECTS: processes user inputs for guesses and returns if game was won or not
    public boolean play() {
        if (maxGuesses == 0) {
            String maxNumString = (String)JOptionPane.showInputDialog(
                    null,
                    "Please enter the maximum guesses you would like to play with.",
                    "Guess Number Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "6");
            maxGuesses = Integer.parseInt(maxNumString);
        }
        while (listModel.size() <= maxGuesses) {
            if (won) {
                gameWon();
                return true;
            }
        }
        if (!won && listModel.size() > maxGuesses) {
            JOptionPane.showMessageDialog(null, "You lost the game :( The correct word was " + target + ".",
                    "You lost :(", JOptionPane.INFORMATION_MESSAGE, loseIcon);
        }
        return won;
    }

    // Represents the action taken when the user wants to compare their guess to the target word
    class CompareResultListener extends AbstractAction {

        CompareResultListener() {
            super("View Comparison");
        }

        // MODIFIES: GameControllerUI
        // EFFECTS: sets won to true if the guess is equivalent to the target word
        @Override
        public void actionPerformed(ActionEvent e) {
            Guess guess = new Guess(listModel.getElementAt(
                    list.getSelectedIndex()).toString());
            if (guess.getWord().equals(target)) {
                won = true;
            } else {
                JOptionPane.showMessageDialog(null,
                        String.join("", guess.compare(getTarget())),
                        "Comparison",JOptionPane.INFORMATION_MESSAGE, legend);
            }
        }
    }

    // Represents the action taken when the user wants to add a guess to the game
    class GuessListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public GuessListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: GameControllerUI, game
        // EFFECTS: adds guesses to game
        public void actionPerformed(ActionEvent e) {
            String guess = textInput.getText();

            if (guess.equals("") || alreadyInList(guess) || !isValid(new Guess(guess))) {
                Toolkit.getDefaultToolkit().beep();
                textInput.requestFocusInWindow();
                textInput.selectAll();
                return;
            }
            game.addGuess(new Guess(guess));
            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listModel.insertElementAt(textInput.getText(), index);

            textInput.requestFocusInWindow();
            textInput.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // EFFECTS: return true if the word has already been guessed
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // EFFECTS: enables the button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // EFFECTS: handles when text field is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                compareButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                compareButton.setEnabled(true);
            }
        }
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

    // EFFECTS: generates a popup window if the user has correctly guessed the word
    public void gameWon() {
        JOptionPane.showMessageDialog(null, "You guessed the word!", "You won!",
                JOptionPane.INFORMATION_MESSAGE, winIcon);
    }

    public Game getGame() {
        return game;
    }

    public String getTarget() {
        return target;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    // EFFECTS: returns GameControllerUI as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("target", target);
        json.put("game", game.toJson());
        json.put("maxGuesses", maxGuesses);
        return json;
    }
}
