package ui;

import model.GameBoard;

import javax.swing.*;
import java.awt.*;

// This class represents a single gameBoard on the stats screen
public class GamePanel extends JPanel {
    private final GameBoard gameBoard;

    // creates a new panel based on the given GameBoard
    public GamePanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setPreferredSize(new Dimension(450, 450));
        setSize(new Dimension(450, 450));
        setBackground(new Color(0xffffffff));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // Overrides the paintComponent method to display a single gameBoard
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(new Color(0xff3e5ebd));
        g2D.fillRect(0,0, getWidth(), getHeight());
        for (int x = 0; x < gameBoard.getColumnCount(); x++) {
            for (int y = 0; y < gameBoard.getRowCount(); y++) {
                g2D.setColor(gameBoard.getColumns().get(x).getCells().get(y).getPieceColor());
                g2D.fillOval(x * getWidth() / 7 + 10,
                        getHeight() - y * 60 - 90,
                        Math.min(getWidth() / 7, getHeight() / 6) - 20,
                        Math.min(getWidth() / 7, getHeight() / 6) - 20);
            }
        }
    }
}
