package model;

import java.awt.*;

//This class represents a single cell that can contain a piece within the board
//with a colour, and symbol
public class Cell {
    private boolean hasPiece;
    private Color pieceColor;
    private char pieceSymbol;

    //Creates a new cell with grey as the colour, a space in the char, and no piece in it
    public Cell() {
        this.pieceColor = new Color(0xffbbbbbb);
        this.hasPiece = false;
        this.pieceSymbol = ' ';
    }

    //Creates a new Cell with grey as the colour, pieceSymbol, and hasPiece depending on pieceSymbol
    public Cell(char pieceSymbol) {
        this.pieceColor = new Color(0xffbbbbbb);
        this.pieceSymbol = pieceSymbol;
        this.hasPiece = pieceSymbol != ' ';
    }

    //REQUIRES: !hasPiece
    //MODIFIES: this
    //EFFECTS: places the piece determined by playerTurn, and sets the color based on it
    public void placePieceInCell(boolean playerTurn) {
        hasPiece = true;
        if (playerTurn) {
            pieceSymbol = 'X';
            pieceColor = new Color(0xfffbff00);
        } else {
            pieceSymbol = 'O';
            pieceColor = new Color(0xff0000);
        }
    }

    //GETTERS
    public boolean hasPiece() {
        return hasPiece;
    }

    public char getPieceSymbol() {
        return pieceSymbol;
    }

    public Color getPieceColor() {
        return pieceColor;
    }
}
