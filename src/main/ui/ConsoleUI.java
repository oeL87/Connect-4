package ui;

import model.*;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//This class manages and represents the input and output for the output to the console
public class ConsoleUI {
    private static final String SPACER = "---------------";
    private static final String DELIMITER = SPACER.repeat(3);
    private static final String FILEPATH = "./data/memory.json";
    private static final Scanner INPUT = new Scanner(System.in);

    private final Reader reader;
    private final Writer writer;

    private Connect4Game connect4Game;
    private GameBoard gameBoard;

    private boolean titleScreen;

//    creates a new ConsoleUI to manage the console version of the game
    public ConsoleUI() {
        titleScreen = true;

        gameBoard = new GameBoard(6,7);
        connect4Game = new Connect4Game();
        reader = new Reader(FILEPATH);
        writer = new Writer(FILEPATH);
    }

    //EFFECTS: start of the game cycle, starts by outputting the menu,
    // then taking input from the player and then if the player wants to start
    // then it starts the first game.
    public void startGame() {
        int input = 2;
        while (input != 4) {
            if (titleScreen) {
                input = displayMenu();
            }
            if (input == 1) {
                manageGame();
            }
        }
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
        System.out.println("closed");
        writer.close();
    }



    //MODIFIES: this
    //EFFECTS: loops the game until someone wins or until a draw, then restarts the cycle
    public void manageGame() {
        while (!(gameBoard.isWin() || gameBoard.isDraw())) {
            tick();
        }
        displayGame();
//        gameBoard.setPlayerTurn(!gameBoard.isPlayerTurn());
        if (gameBoard.isWin() && !gameBoard.isPlayerTurn()) {
            System.out.println("You Win!");
        } else if (gameBoard.isDraw()) {
            System.out.println("It's a Draw!");
        } else {
            System.out.println("You Lose!");
        }
        System.out.println("Would you like to save this game?\n"
                + "1. Yes\n"
                + "2. No");
        int input = takeInputFromConsole(1, 2);
        connect4Game.addGameToList(gameBoard);
        if (input == 1) {
            connect4Game.addGameToSave(gameBoard);
        }
        saveGames();
        titleScreen = true;
        gameBoard = new GameBoard(6,7);
    }

    //REQUIRES: game has been set up
    //MODIFIES: this
    //EFFECTS: displays the board, then prompts either player or computer for input,
    // depending on whose turn it is
    public void tick() {
        displayGame();
        if (gameBoard.isPlayerTurn()) {
            System.out.println("player turn");
            takeInputForGame();
        } else {
            System.out.println("computer turn");
            computerInputForGame();
        }
        gameBoard.checkWin();
        gameBoard.checkDraw();
    }

    //REQUIRES: !titleScreen
    //EFFECTS: outputs the contents of the board into the console
    public void displayGame() {
        System.out.println(DELIMITER);
        System.out.println("-0-1-2-3-4-5-6-");
        for (int x = gameBoard.getRowCount() - 1; x >= 0; x--) {
            System.out.println(createRowString(x));
            System.out.println(SPACER);
        }
    }

    //REQUIRES: titleScreen
    //MODIFIES: this
    //EFFECTS: outputs to the console the main menu, and takes the player input and outputs the corresponding
    //      choice
    public int displayMenu() {
        System.out.println("Welcome to Connect4, what would you like to do? \n"
                + "1. Start New Game \n"
                + "2. View Stats\n"
                + "3. Load previous games from file\n"
                + "4. Quit");
        int choice = takeInputFromConsole(1, 4);
        if (choice == 1) {
            titleScreen = false;
        } else if (choice == 2) {
            try {
                System.out.println("You have won " + connect4Game.getWinPercentage() + "% of the time");
            } catch (ArithmeticException e) {
                System.out.println("Play a game first.");
            }
        } else if (choice == 3) {
            loadGames();
        }

        return choice;
    }

    //REQUIRES: 0 < index < columns.size()
    //EFFECTS: creates a string from the given row in gameBoard determined by index
    // returns it as a String
    public String createRowString(int index) {
        ArrayList<Column> columns = gameBoard.getColumns();
        StringBuilder output = new StringBuilder("|");

        for (Column column : columns) {
            output.append(column.getCells().get(index).getPieceSymbol());
            output.append("|");
        }
        return output.toString();
    }

    //REQUIRES: gameBoard.isPlayerTurn()
    //MODIFIES: this
    //EFFECTS: takes input from player and forces it to be in the bounds of the game, then calls
    //          gameBoard.placePiece
    public void takeInputForGame() {
        int input = takeInputFromConsole(0, gameBoard.getColumnCount() - 1);
        while (gameBoard.placePieceInBoard(input)) {
            input = takeInputFromConsole(0, gameBoard.getColumnCount() - 1);
            System.out.println("failed");
        }
        gameBoard.setPlayerTurn(false);
        System.out.println("place");

    }

    //REQUIRES: !gameBoard.isPlayerTurn()
    //MODIFIES: this
    //EFFECTS: generates a random column, then tries to place a piece in that column
    // will keep trying until it successfully inputs a piece into the board
    public void computerInputForGame() {
        int input = GameBoard.randomNumberGenerator(6, 0);
        while (gameBoard.placePieceInBoard(input)) {
            input = GameBoard.randomNumberGenerator(6, 0);
        }
        gameBoard.setPlayerTurn(true);
        System.out.println("computer place");
    }

    //EFFECTS: makes sure the input is an int, and is in the bounds of the board
    // then returns it.
    public int takeInputFromConsole(int min, int max) {
        int input = -1;
        do {
            try {
                input = INPUT.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("try again");
                INPUT.next();
            }
        } while (input < min || input > max);
        return input;
    }

    // MODIFIES: connect4Game
    // EFFECTS: loads the past games from FILEPATH
    private void loadGames() {
        try {
            connect4Game = reader.read();
            System.out.println("Loaded past games from " + FILEPATH);
        } catch (IOException e) {
            System.out.println("Error occurred, could not load from file");
        }
    }

    // EFFECTS: saves the connect4Game into the FILEPATH
    private void saveGames() {
        try {
            writer.start();
            writer.write(connect4Game);
            writer.close();
            System.out.println("Games saved");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save");
        }
    }

}
