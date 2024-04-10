package ui;

import model.Connect4Game;
import model.Event;
import model.EventLog;
import model.GameBoard;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//This class manages and represents the input and output for the output to the Graphical user interface
public class GraphicUI extends JFrame implements ActionListener, MouseListener {
    private static final String FILEPATH = "./data/memory.json";

    private final Reader reader;
    private final Writer writer;

    private Connect4Game connect4Game;
    private GameBoard gameBoard;
    private JFrame saveFrame;
    private JFrame statsFrame;

    private boolean isTitleScreen;

//    creates a new GraphicUI object to manage the graphical version of the game
    public GraphicUI() {
        reader = new Reader(FILEPATH);
        writer = new Writer(FILEPATH);
        isTitleScreen = true;

        gameBoard = new GameBoard(6, 7);
        connect4Game = new Connect4Game();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connect 4");
        setSize(500, 500);
        setPreferredSize(new Dimension(600, 600));
        setResizable(true);
        addMouseListener(this);

        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel titleScreen = new JPanel();
        add(titleScreen);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: decides which version of the main frame is to be painted, calls title screen or board
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        g2D.setBackground(new Color(0xff666666));
        g2D.clearRect(0, 0, width, height);
        if (isTitleScreen) {
            paintTitleScreen(g2D, width, height);
        } else {
            paintBoard(g2D);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the main title screen with 4 buttons and corresponding text
    private void paintTitleScreen(Graphics2D g2D, int width, int height) {
        g2D.setColor(new Color(0xff5e61bd));
        g2D.fillRoundRect(width / 16, 0, width * 7 / 8, height * 7 / 8, 5,5);
        g2D.setColor(new Color(0xff78230e));
        g2D.setFont(new Font("TimesNewRoman", Font.BOLD, 40));
        g2D.drawString("Connect 4", (width - g2D.getFontMetrics().stringWidth("Connect 4")) / 2, height * 2 / 15);
        g2D.fillRect(width / 3, height / 5, width / 3, height / 15);
        g2D.fillRect(width / 3, height / 3, width / 3, height / 15);
        g2D.fillRect(width / 3, height * 7 / 15, width / 3, height / 15);
        g2D.fillRect(width / 3, height * 3 / 5, width / 3, height / 15);
        g2D.setColor(new Color(0xff000000));
        g2D.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
        g2D.drawString("Start", (width - g2D.getFontMetrics().stringWidth("Start")) / 2, (int) (height * 3.5 / 15));
        g2D.drawString("Stats", (width - g2D.getFontMetrics().stringWidth("Stats")) / 2, (int) (height * 5.5 / 15));
        g2D.drawString("Load", (width - g2D.getFontMetrics().stringWidth("Load")) / 2, (int) (height * 7.5 / 15));
        g2D.drawString("Quit", (width - g2D.getFontMetrics().stringWidth("Quit")) / 2, (int) (height * 9.5 / 15));
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Component source = e.getComponent();
        if (source instanceof JFrame) {
            JFrame origin = (JFrame) source;

            if (origin == this) {
                if (isTitleScreen) {
                    manageTitleScreen(e);
                } else {
                    manageGame(e);
                }
            } else if (origin == saveFrame) {
                manageSaveFrame(e);
            } else if (origin == statsFrame) {
                statsFrame.dispose();
                statsFrame = null;
            }
        }
        repaint();
    }

    // REQUIRES: isTitleScreen
    // EFFECTS: determines which button on the main screen was clicked
    private void manageTitleScreen(MouseEvent e) {
        if (e.getX() >= getWidth() / 3 && e.getX() <= getWidth() * 2 / 3) {
            if (e.getY() >= getHeight() / 5 && e.getY() <= getHeight() * 4 / 15) {
                isTitleScreen = false;
            } else if (e.getY() >= getHeight() / 3 && e.getY() <= getHeight() * 2 / 5) {
                paintStats();
            } else if (e.getY() >= getHeight() * 7 / 15 && e.getY() <= getHeight() * 8 / 15) {
                System.out.println("load");
                loadGames();
            } else if (e.getY() >= getHeight() * 3 / 5 && e.getY() <= getHeight() * 2 / 3) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                dispose();
            }
        }
    }

    // REQUIRES: !isTitleScreen
    // MODIFIES: gameBoard
    // EFFECTS: takes the coordinates of the MouseEvent and finds which column the player wanted to place their piece in
    // then places the piece into the column and waits for the computer to move
    private void manageGame(MouseEvent e) {
        if (gameBoard.isPlayerTurn()) {
            int x = e.getX() / (getWidth() / 7);
            if (gameBoard.placePieceInBoard(x)) {
                repaint();
                return;
            }
            gameBoard.setPlayerTurn(false);
        } else {
            computerInputForGame();
        }
        repaint();
        checkGame();
    }

    // MODIFIES: saveFrame, connect4Game
    // EFFECTS: takes input from the user to see if they want the game to be displayed and saved.
    // Then puts the game into a list depending on
    private void manageSaveFrame(MouseEvent e) {
        if (e.getX() >= saveFrame.getWidth() / 3 && e.getX() <= saveFrame.getWidth() * 2 / 3) {
            if (e.getY() >= saveFrame.getHeight() * 4 / 15 && e.getY() <= saveFrame.getHeight() * 5 / 15) {
                connect4Game.addGameToSave(gameBoard);
                saveGame();
            } else if (e.getY() >= saveFrame.getHeight() * 6 / 15 && e.getY() <= saveFrame.getHeight() * 7 / 15) {
                saveGame();
            }
        }
    }

    //REQUIRES: !gameBoard.isPlayerTurn()
    //MODIFIES: gameBoard
    //EFFECTS: generates a random column, then tries to place a piece in that column
    // will keep trying until it successfully inputs a piece into the board
    private void computerInputForGame() {
        int input = GameBoard.randomNumberGenerator(6, 0);
        while (gameBoard.placePieceInBoard(input)) {
            input = GameBoard.randomNumberGenerator(6, 0);
        }
        gameBoard.setPlayerTurn(true);
    }

    // REQUIRES: !isTitleScreen
    // MODIFIES: this
    // EFFECTS: takes gameBoard and paints it to be visually represented for the player
    private void paintBoard(Graphics2D g2D) {
        g2D.setColor(new Color(0xff3e5ebd));
        g2D.fillRect(10,10, getWidth() - 20, getHeight() - 20);
        for (int x = 0; x < gameBoard.getColumnCount(); x++) {
            for (int y = 0; y < gameBoard.getRowCount(); y++) {
                g2D.setColor(gameBoard.getColumns().get(x).getCells().get(y).getPieceColor());
                g2D.fillOval(x * (getWidth() - 56) / 7 + 15,
                        (getHeight() - 120 - y * 80),
                        Math.min(getWidth() / 7, getHeight() / 6) - 10,
                        Math.min(getWidth() / 7, getHeight() / 6) - 10);
            }
        }
    }

    // MODIFIES: gameBoard
    // EFFECTS: runs the checks on the board to see if either player has won, or if there is a draw
    private void checkGame() {
        gameBoard.checkDraw();
        gameBoard.checkWin();
        if (!(gameBoard.isWin() || gameBoard.isDraw())) {
            return;
        }
        if (saveFrame == null) {
            paintSaveScreen();
        }
    }

    // MODIFIES: saveFrame
    // EFFECTS: creates a new screen to visually prompt the user if they want the game to be saved
    private void paintSaveScreen() {
        saveFrame = initJFrame("Save Game?");
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2D = (Graphics2D) g;
                int width = saveFrame.getWidth();
                int height = saveFrame.getHeight();
                g2D.setFont(new Font("TimesNewRoman", Font.PLAIN, 30));
                saveScreenHelper(g2D, width, height);
                g2D.drawString("Would you like to save this game?",
                        (width - g2D.getFontMetrics().stringWidth("Would you like to save this game?")) / 2,
                        height * 2 / 15);
                g2D.fillRect(width / 3, height / 3, width / 3, height / 15);
                g2D.fillRect(width / 3, height / 5, width / 3, height / 15);
                g2D.setColor(new Color(0xffffffff));
                g2D.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
                g2D.drawString("Yes", (width - g2D.getFontMetrics().stringWidth("Yes")) / 2, (int) (height * 3.5 / 15));
                g2D.drawString("No", (width - g2D.getFontMetrics().stringWidth("No")) / 2, (int) (height * 5.5 / 15));
            }
        };
        saveFrame.add(panel);

        saveFrame.setVisible(true);
    }

    // MODIFIES: saveFrame
    // EFFECTS: finds the final game state and displays it onto the save screen
    private void saveScreenHelper(Graphics2D g2D, int width, int height) {
        if (gameBoard.isWin() && !gameBoard.isPlayerTurn()) {
            g2D.drawString("You Win!", (width - g2D.getFontMetrics().stringWidth("You Win!")) / 2, height / 15);
        } else if (gameBoard.isWin() && gameBoard.isPlayerTurn()) {
            g2D.drawString("You Lose!", (width - g2D.getFontMetrics().stringWidth("You Lose!")) / 2, height / 15);
        } else if (gameBoard.isDraw()) {
            g2D.drawString("Draw!", (width - g2D.getFontMetrics().stringWidth("Draw")) / 2, height / 15);
        }
    }

    // MODIFIES: statsFrame
    // EFFECTS: creates a new screen with the connect4Game lists and displays to user
    private void paintStats() {
        statsFrame = new ScrollScreen(connect4Game);
    }

    // MODIFIES: connect4Game
    // EFFECTS: loads the past games from FILEPATH
    private void loadGames() {
        try {
            connect4Game = reader.read();
        } catch (IOException e) {
            System.out.println("Error occurred " + FILEPATH + " not found");
        }
    }

    // MODIFIES: connect4Game
    // EFFECTS: adds the recent game into the connect4Game list and creates a new game
    private void saveGame() {
        connect4Game.addGameToList(gameBoard);
        saveGames();
        gameBoard = new GameBoard(6, 7);
        isTitleScreen = true;
        repaint();
        saveFrame.dispose();
        saveFrame = null;
    }

    // EFFECTS: saves the connect4Game into the FILEPATH
    private void saveGames() {
        try {
            writer.start();
            writer.write(connect4Game);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("failed to save");
        }
    }

    // EFFECTS: creates a new basic JFrame template for any screens
    private JFrame initJFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setResizable(false);
        frame.addMouseListener(this);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        return frame;
    }

    //ActionListener and MouseListener method implementations that were not needed
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
