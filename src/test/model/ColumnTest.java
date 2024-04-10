package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnTest {
    private Column testColumn;

    @BeforeEach
    void setup() {
        testColumn = new Column(6);
    }

    @Test
    void placePieceInColumnTestOnce() {
        assertTrue(testColumn.placePieceInColumn(true));
    }

    @Test
    void placePieceInColumnTestMultiple() {
        assertTrue(testColumn.placePieceInColumn(true));
        assertTrue(testColumn.placePieceInColumn(false));
    }

    @Test
    void placePieceInColumnTestFullColumn() {
        assertTrue(testColumn.placePieceInColumn(true));
        assertTrue(testColumn.placePieceInColumn(false));
        assertTrue(testColumn.placePieceInColumn(true));
        assertTrue(testColumn.placePieceInColumn(false));
        assertTrue(testColumn.placePieceInColumn(true));
        assertTrue(testColumn.placePieceInColumn(false));
        assertFalse(testColumn.placePieceInColumn(true));
    }

    @Test
    void isColumnFullTest() {
        for (int x = 0; x < 6; x++) {
            assertFalse(testColumn.isColumnFull());
            testColumn.placePieceInColumn(true);
        }
        assertTrue(testColumn.isColumnFull());
    }

    @Test
    void checkConnect4ColumnTestPlayer() {
        for (int x = 0; x < 4; x++) {
            assertFalse(testColumn.checkConnect4Column());
            testColumn.placePieceInColumn(true);
        }
        assertTrue(testColumn.checkConnect4Column());
    }

    @Test
    void checkConnect4ColumnTestComputer() {
        for (int x = 0; x < 4; x++) {
            assertFalse(testColumn.checkConnect4Column());
            testColumn.placePieceInColumn(false);
        }
        assertTrue(testColumn.checkConnect4Column());
    }

    @Test
    void checkConnect4ColumnTestMix() {
        assertFalse(testColumn.checkConnect4Column());
        testColumn.placePieceInColumn(false);
        assertFalse(testColumn.checkConnect4Column());
        testColumn.placePieceInColumn(true);
        assertFalse(testColumn.checkConnect4Column());
        testColumn.placePieceInColumn(false);
        assertFalse(testColumn.checkConnect4Column());
        testColumn.placePieceInColumn(false);
        assertFalse(testColumn.checkConnect4Column());
        testColumn.placePieceInColumn(true);
        assertFalse(testColumn.checkConnect4Column());
        testColumn.placePieceInColumn(false);
        assertFalse(testColumn.checkConnect4Column());
    }
}
