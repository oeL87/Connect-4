package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameBoardTest {

    private GameBoard game;

    @BeforeEach
    void setup() {
        game = new GameBoard(6,7);
    }

    @Test
    void constructorTest() {
        assertFalse(game.isWin());
        assertFalse(game.isDraw());
        assertEquals(6, game.getRowCount());
        assertEquals(7, game.getColumnCount());
    }

    @Test
    void placePieceInBoardTestOnce() {
        assertFalse(game.getColumns().get(0).getCells().get(0).hasPiece());
        game.placePieceInBoard(0);
        assertTrue(game.getColumns().get(0).getCells().get(0).hasPiece());
    }

    @Test
    void placePieceInBoardTestMultipleInSameColumn() {
        assertFalse(game.getColumns().get(0).getCells().get(0).hasPiece());
        game.placePieceInBoard(0);
        assertFalse(game.getColumns().get(0).getCells().get(1).hasPiece());
        assertTrue(game.getColumns().get(0).getCells().get(0).hasPiece());
        game.placePieceInBoard(0);
        assertTrue(game.getColumns().get(0).getCells().get(0).hasPiece());
        assertTrue(game.getColumns().get(0).getCells().get(1).hasPiece());
    }

    @Test
    void placePieceInBoardTestMultipleInDiffColumn() {
        assertFalse(game.getColumns().get(0).getCells().get(0).hasPiece());
        game.placePieceInBoard(0);
        assertFalse(game.getColumns().get(0).getCells().get(1).hasPiece());
        assertTrue(game.getColumns().get(0).getCells().get(0).hasPiece());
        game.placePieceInBoard(1);
        assertTrue(game.getColumns().get(0).getCells().get(0).hasPiece());
        assertFalse(game.getColumns().get(0).getCells().get(1).hasPiece());
        assertTrue(game.getColumns().get(1).getCells().get(0).hasPiece());
    }

    @Test
    void placePieceInBoardSameColumnUntilFull() {
        assertFalse(game.placePieceInBoard(0));
        assertFalse(game.placePieceInBoard(0));
        assertFalse(game.placePieceInBoard(0));
        assertFalse(game.placePieceInBoard(0));
        assertFalse(game.placePieceInBoard(0));
        assertFalse(game.placePieceInBoard(0));
        assertTrue(game.placePieceInBoard(0));
    }

    @Test
    void checkWinTestDiagonalUp() {
        List<Integer> loopList = new ArrayList<>(List.of(0,1,1,3,2,2,3,3,2,2,3));
        for (int i : loopList) {
            assertFalse(game.isWin());
            assertFalse(game.isDraw());
            game.placePieceInBoard(i);
            game.checkWin();
            game.setPlayerTurn(!game.isPlayerTurn());
        }
        assertTrue(game.isWin());
        assertFalse(game.isDraw());
    }

    @Test
    void checkWinTestDiagonalDown() {
        List<Integer> loopList = new ArrayList<>(List.of(3,2,2,0,1,1,0,0,1,1,0));
        for (int i : loopList) {
            assertFalse(game.isWin());
            assertFalse(game.isDraw());
            game.placePieceInBoard(i);
            game.checkWin();
            game.setPlayerTurn(!game.isPlayerTurn());
        }
        assertTrue(game.isWin());
        assertFalse(game.isDraw());
    }

    @Test
    void checkWinTestRows() {
        List<Integer> loopList = new ArrayList<>(List.of(0,0,1,1,2,2,3));
        for (int i : loopList) {
            assertFalse(game.isWin());
            assertFalse(game.isDraw());
            game.placePieceInBoard(i);
            game.checkWin();
            game.setPlayerTurn(!game.isPlayerTurn());
        }
        assertTrue(game.isWin());
        assertFalse(game.isDraw());
    }

    @Test
    void checkWinTestColumns() {
        List<Integer> loopList = new ArrayList<>(List.of(0,1,0,1,0,1,0));
        for (int i : loopList) {
            assertFalse(game.isWin());
            assertFalse(game.isDraw());
            game.placePieceInBoard(i);
            game.checkWin();
            game.setPlayerTurn(!game.isPlayerTurn());
        }
        assertTrue(game.isWin());
        assertFalse(game.isDraw());
    }

    @Test
    void checkDrawTest(){
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 7; x++) {
                assertFalse(game.isDraw());
                assertFalse(game.isWin());
                game.placePieceInBoard(x);
                game.placePieceInBoard(x);
                game.placePieceInBoard(x);
                game.setPlayerTurn(!game.isPlayerTurn());
                game.checkDraw();
                game.checkWin();
            }
        }
        assertTrue(game.isDraw());
        assertFalse(game.isWin());
    }
}
