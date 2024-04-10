package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private Cell testCell;

    @BeforeEach
    void setup() {
        testCell = new Cell();
    }

    @Test
    void constructorTest() {
        assertEquals(' ', testCell.getPieceSymbol());
        assertFalse(testCell.hasPiece());
    }

    @Test
    void placePieceInCellTestPlayer() {
        assertFalse(testCell.hasPiece());
        testCell.placePieceInCell(true);
        assertEquals('X', testCell.getPieceSymbol());
        assertNotNull(testCell.getPieceColor());
        assertTrue(testCell.hasPiece());
    }

    @Test
    void placePieceInCellTestComputer() {
        assertFalse(testCell.hasPiece());
        testCell.placePieceInCell(false);
        assertEquals('O', testCell.getPieceSymbol());
        assertNotNull(testCell.getPieceColor());
        assertTrue(testCell.hasPiece());
    }
}
