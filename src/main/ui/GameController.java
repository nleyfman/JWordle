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

public class GameController extends JPanel implements ListSelectionListener, Writable {
    private JList list;
    private DefaultListModel listModel;
    private String target;
    private Game game;
    private boolean won;
    private int maxGuesses;

    private static final String addGuess = "Add Guess";
    private static final String viewResult = "View Result";
    private JButton compareButton;
    private JTextField textInput;

    public GameController(String target, Game game, int maxGuesses) {
        super(new BorderLayout());

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
        won = false;
        this.maxGuesses = maxGuesses;

        listModel = new DefaultListModel();
        listModel.addElement("Guess");

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton guessButton = new JButton(addGuess);
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
        String word = listModel.getElementAt(
                list.getSelectedIndex()).toString();

        //Create a panel that uses BoxLayout.
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

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("target", target);
        json.put("game", game.toJson());
        json.put("maxGuesses", maxGuesses);
        return json;
    }

    class CompareResultListener extends AbstractAction {

        CompareResultListener() {
            super("View Comparison");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Guess guess = new Guess(listModel.getElementAt(
                    list.getSelectedIndex()).toString());
            if (guess.getWord().equals(target)) {
                won = true;
            } else {
                JOptionPane.showMessageDialog(null,
                        String.join("", guess.compare(getTarget())));
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class GuessListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public GuessListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String guess = textInput.getText();


            //User didn't type in a unique word...
            if (guess.equals("") || alreadyInList(guess) || !isValid(new Guess(guess))) {
                Toolkit.getDefaultToolkit().beep();
                textInput.requestFocusInWindow();
                textInput.selectAll();
                return;
            }
            game.addGuess(new Guess(guess));
            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(textInput.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            textInput.requestFocusInWindow();
            textInput.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
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

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

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

    public void gameWon() {
        JOptionPane.showMessageDialog(null, "You guessed the word!");
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
        while (listModel.size() <= maxGuesses + 1) {
            if (won) {
                gameWon();
                return true;
            }
        }
        if (!won && listModel.size() > maxGuesses) {
            JOptionPane.showMessageDialog(null, "You lost the game :( The correct word was " + target + ".");
        }
        return won;
    }
}
