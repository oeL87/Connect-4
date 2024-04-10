package ui;

import model.Connect4Game;
import model.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// This class represents the stats screen, which requires the use of the JScrollPane class
public class ScrollScreen extends JFrame {
    // Creates a new Scroll Screen class with a given connect4Game
    public ScrollScreen(Connect4Game connect4Game) {
        setTitle("Statistics");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setResizable(false);

        List<GameBoard> savedGames = connect4Game.getSavedPastGames();
        List<JPanel> panelList = new ArrayList<>();
        for (GameBoard gameBoard : savedGames) {
            GamePanel panel = new GamePanel(gameBoard);
            panelList.add(panel);
        }
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(helper(connect4Game));
        for (JPanel panel : panelList) {
            contentPane.add(panel);
        }
        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a label based off the winrate of the user and puts it at the top of the scroll screen
    private JPanel helper(Connect4Game connect4Game) {
        JLabel label;
        try {
            double winPercentage = connect4Game.getWinPercentage();
            label = new JLabel("You have won " + winPercentage + "% of the time.");
        } catch (ArithmeticException e) {
            label = new JLabel("Play a game first");
        }
        label.setFont(new Font("TimesNewRoman", Font.PLAIN, 30));
        JPanel statPanel = new JPanel();
        statPanel.setPreferredSize(new Dimension(450, 50));
        statPanel.setSize(new Dimension(450, 50));
        statPanel.setBackground(new Color(0xffffffff));
        statPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statPanel.add(label);
        return statPanel;
    }
}