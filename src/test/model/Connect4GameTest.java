package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Connect4GameTest {
    private Connect4Game c4g;
    private GameBoard game1;
    private GameBoard game2;
    private GameBoard game3;

    @BeforeEach
    void setup() {
        c4g = new Connect4Game();
        game1 = new GameBoard(6, 7);
        game2 = new GameBoard(6, 7);
        game3 = new GameBoard(6, 7);
    }

    @Test
    void addGameToListTestOnce() {
        c4g.addGameToList(game1);
        assertEquals(game1, c4g.getPastGames().get(0));
    }

    @Test
    void addGameToListTestMultiple() {
        c4g.addGameToList(game1);
        c4g.addGameToList(game2);
        c4g.addGameToList(game3);
        assertEquals(game1, c4g.getPastGames().get(0));
        assertEquals(game2, c4g.getPastGames().get(1));
        assertEquals(game3, c4g.getPastGames().get(2));
    }

    @Test
    void getWinPercentageTestOneWinNoException() {
        try {
            game1.setWin(true);
            game1.setPlayerTurn(false);
            c4g.addGameToList(game1);
            assertEquals(100.0, c4g.getWinPercentage());
        } catch (ArithmeticException e) {
            fail("Unexpected: ArithmeticException e");
        }
    }

    @Test
    void getWinPercentageTestDrawNoException() {
        try {
            game1.setDraw(true);
            c4g.addGameToList(game1);
            assertEquals(50.0, c4g.getWinPercentage());
        } catch (ArithmeticException e) {
            fail("Unexpected: ArithmeticException e");
        }
    }

    @Test
    void getWinPercentageTestOneLossNoException() {
        try {
            game1.setWin(true);
            game1.setPlayerTurn(true);
            c4g.addGameToList(game1);
            assertEquals(0.0, c4g.getWinPercentage());
        } catch (ArithmeticException e) {
            fail("Unexpected: ArithmeticException e");
        }
    }

    @Test
    void getWinPercentageTestMultipleGamesNoException() {
        try {
            game1.setWin(true);
            game2.setWin(true);
            game3.setWin(true);
            game1.setPlayerTurn(true);
            game2.setPlayerTurn(true);
            game3.setPlayerTurn(false);
            c4g.addGameToList(game1);
            assertEquals(0.0, c4g.getWinPercentage());
            c4g.addGameToList(game2);
            assertEquals(0.0, c4g.getWinPercentage());
            c4g.addGameToList(game3);
            assertEquals(33.33, c4g.getWinPercentage());
        } catch (ArithmeticException e) {
            fail("Unexpected: ArithmeticException e");
        }

    }

    @Test
    void getWinPercentageArithmeticExceptionExpected() {
        try {
            c4g.getWinPercentage();
            fail("No Exception thrown (Expected: ArithmeticException)");
        } catch (ArithmeticException e) {
            // Expected the exception to be thrown
        }
    }

    @Test
    void addToSaveGames() {
        c4g.addGameToSave(game1);
        assertEquals(game1, c4g.getSavedPastGames().get(0));
    }
}
