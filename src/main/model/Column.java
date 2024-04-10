package model;

import java.util.ArrayList;

//This class represents a column of cells within the game board.
//Lower index means closer to bottom
public class Column {
    private final ArrayList<Cell> cells;

    //Creates a new column and populates it with rowCount amounts of cells.
    public Column(int rowCount) {
        cells = new ArrayList<>();
        for (int x = 0; x < rowCount; x++) {
            cells.add(new Cell());
        }
    }

    //Creates a new Column with cells
    public Column(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    //MODIFIES: this
    //EFFECTS: places a piece determined by playerTurn into the column
    // at the lowest possible cell not taken, if column is full it returns false
    public boolean placePieceInColumn(boolean playerTurn) {
        if (isColumnFull()) {
            return false;
        }
        System.out.println("placed");
        for (Cell cell : cells) {
            if (!cell.hasPiece()) {
                cell.placePieceInCell(playerTurn);
                break;
            }
        }
        return true;
    }

    //REQUIRES: cells.size() > 0
    //EFFECTS: checks if every cell in cells is full
    public boolean isColumnFull() {
        return !(cells.get(cells.size() - 1).getPieceSymbol() == ' ');
    }

    //EFFECTS: checks if there are 4 cells in a row that are the same
    // assumes that there are no floating pieces
    public boolean checkConnect4Column() {
        char previous = cells.get(0).getPieceSymbol();
        int count = 0;
        for (Cell cell : cells) {
            if (!cell.hasPiece()) {
                return false;
            } else if (previous == cell.getPieceSymbol()) {
                count++;
            } else {
                count = 1;
                previous = cell.getPieceSymbol();
            }
            if (count >= 4) {
                return true;
            }
        }
        return false;
    }

    //GETTER for cells
    public ArrayList<Cell> getCells() {
        return cells;
    }
}
