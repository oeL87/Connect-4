package model;

import java.util.ArrayList;

//This class represents the game board that the game of connect4 will
//be played on.
public class GameBoard {
    private final ArrayList<Column> columns;
    private final int rowCount;
    private final int columnCount;

    private boolean playerTurn;
    private boolean isWin;
    private boolean isDraw;

    //EFFECTS: creates new GameBoard with an empty columns list, randomly chooses if it's the player's turn
    // and row and columns with their sizes
    public GameBoard(int rowCount, int columnCount) {
        columns = new ArrayList<>();
        playerTurn = randomNumberGenerator(2, 1) == 1;
        isWin = false;
        isDraw = false;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        for (int x = 0; x < columnCount; x++) {
            columns.add(new Column(rowCount));
        }
    }

    //EFFECTS: creates new GameBoard with rowCount, columnCount, columns, playerTurn, isWin, and isDraw
    public GameBoard(int rowCount, int columnCount, ArrayList<Column> columns,
                     boolean playerTurn, boolean isWin, boolean isDraw) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.columns = columns;
        this.playerTurn = playerTurn;
        this.isWin = isWin;
        this.isDraw = isDraw;
    }

    //No requires because if max < min, the math still works
    //EFFECTS: generates a random number between max and min (inclusive)
    public static int randomNumberGenerator(int max, int min) {
        int random = (int)(Math.random() * (max - min + 1) + min);
        EventLog.getInstance().logEvent(new Event("random number " + random + " generated"));
        return random;
    }

    //REQUIRES: 0 <= columnNum <= columnCount
    //MODIFIES: this
    //EFFECTS: attempts to place a piece in the column, and returns if it was successful or not
    public boolean placePieceInBoard(int columnNum) {
        boolean placed = !columns.get(columnNum).placePieceInColumn(playerTurn);
        EventLog.getInstance().logEvent(new Event("piece placed in column #" + columnNum + " "
                + (placed ? "unsuccessfully" : "successfully")));
        return placed;
    }

    //EFFECTS: checks the 3 types of wins to see if either player has won
    public void checkWin() {
        isWin = checkDiagonals() || checkRows() || checkColumns();
    }

    //EFFECTS: checks if any vertical wins have occurred
    private boolean checkColumns() {
        for (Column column : columns) {
            if (column.checkConnect4Column()) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: checks if any horizontal wins have occurred
    private boolean checkRows() {
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount - 3; x++) {
                if (!columns.get(x).getCells().get(y).hasPiece()) {
                    continue;
                }

                char firstChar = columns.get(x).getCells().get(y).getPieceSymbol();
                char secondChar = columns.get(x + 1).getCells().get(y).getPieceSymbol();
                char thirdChar = columns.get(x + 2).getCells().get(y).getPieceSymbol();
                char fourthChar = columns.get(x + 3).getCells().get(y).getPieceSymbol();

                if ((firstChar == secondChar) && (secondChar == thirdChar) && (thirdChar == fourthChar)) {
                    return true;
                }
            }
        }
        return false;
    }

    //EFFECTS: checks if any diagonal wins have occurred
    private boolean checkDiagonals() {
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                if (checkDiagonal(x, y, true) || checkDiagonal(x, y, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    //EFFECTS: checks if the diagonal for the cell x, y given by isUp is a connect 4 or not
    private boolean checkDiagonal(int x, int y, boolean isUp) {
        char previous = columns.get(x).getCells().get(y).getPieceSymbol();
        try {
            for (int z = 1; z < 4; z++) {
                if (!columns.get(x).getCells().get(y).hasPiece()) {
                    return false;
                }
                if (isUp && previous != columns.get(x + z).getCells().get(y + z).getPieceSymbol()) {
                    return false;
                } else if (!isUp && previous != columns.get(x + z).getCells().get(y - z).getPieceSymbol()) {
                    return false;
                }
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    //REQUIRES: !isWin
    //EFFECTS: runs logic to see if the game is in a draw state
    public void checkDraw() {
        for (Column column : columns) {
            if (!column.isColumnFull()) {
                isDraw = false;
                return;
            }
        }
        isDraw = true;
    }

    //GETTERS AND SETTERS
    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        this.isWin = win;
    }
}
