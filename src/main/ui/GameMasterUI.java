package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMasterUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JComboBox<String> printCombo;
    private JDesktopPane desktop;
    private JInternalFrame game;
    private GameMaster gm;
    private JTextField textField;

    public GameMasterUI() {

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        game = new JInternalFrame("Game", false, false, false, false);
        game.setLayout(new BorderLayout());

        textField = new JTextField(20);

        addGamePanel();

        setContentPane(desktop);
        setTitle("JWordle");
        setSize(WIDTH, HEIGHT);

        desktop.add(textField);

        game.pack();
        game.setVisible(true);
        desktop.add(game);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centerOnScreen();
        setVisible(true);
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            GameMasterUI.this.requestFocusInWindow();
        }
    }

    private void centerOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private void addGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(0, 5));

    }

    public static void main(String[] args) {
        new GameMasterUI();
    }
}

