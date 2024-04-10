package persistence;

import model.Cell;
import model.Column;
import model.Connect4Game;
import model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    private Reader reader;
    private GameBoard game0Answer;
    private GameBoard game1Answer;
    private GameBoard game2Answer;
    private GameBoard game3Answer;
    private ArrayList<GameBoard> gameAnswers;

    @BeforeEach
    void setup() {
        ArrayList<ArrayList<Character>> character2DArray = new ArrayList<>();
        character2DArray.add(new ArrayList<>(Arrays.asList('X','X','X','O','O',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','X',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','X','O',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','X',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList(' ',' ',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList(' ',' ',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O',' ',' ',' ',' ',' ')));
        ArrayList<Column> columns = new ArrayList<>();
        for (ArrayList<Character> array : character2DArray) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (Character c : array) {
                cells.add(new Cell(c));
            }
            columns.add(new Column(cells));
        }
        game0Answer = new GameBoard(6,7, columns, false, true, false);

        character2DArray = new ArrayList<>();
        character2DArray.add(new ArrayList<>(Arrays.asList('O',' ',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','O',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O',' ',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','O',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','X','X',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','X','X','O','O',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','X','X','X',' ',' ')));
        columns = new ArrayList<>();
        for (ArrayList<Character> array : character2DArray) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (Character c : array) {
                cells.add(new Cell(c));
            }
            columns.add(new Column(cells));
        }
        game1Answer = new GameBoard(6,7, columns, false, true, false);

        character2DArray = new ArrayList<>();
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','X','X','X','O')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','O',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','O','X',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','O',' ',' ',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','O','X',' ',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','O','X','X','X',' ')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','X','O','X','X','O')));
        columns = new ArrayList<>();
        for (ArrayList<Character> array : character2DArray) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (Character c : array) {
                cells.add(new Cell(c));
            }
            columns.add(new Column(cells));
        }
        game2Answer = new GameBoard(6,7, columns, true, true, false);

        character2DArray = new ArrayList<>();
        character2DArray.add(new ArrayList<>(Arrays.asList('X','X','O','X','O','X')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','O','X','O','O','X')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','O','X','O','X','O')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','X','X','O','X')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','X','O','X','O','O')));
        character2DArray.add(new ArrayList<>(Arrays.asList('X','O','O','X','O','X')));
        character2DArray.add(new ArrayList<>(Arrays.asList('O','X','X','O','X','X')));
        columns = new ArrayList<>();
        for (ArrayList<Character> array : character2DArray) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (Character c : array) {
                cells.add(new Cell(c));
            }
            columns.add(new Column(cells));
        }
        game3Answer = new GameBoard(6,7, columns, true, false, true);

        gameAnswers = new ArrayList<>(Arrays.asList(game0Answer,game1Answer,
                game2Answer,game3Answer));
    }

    @Test
    void testReadingInvalidFile() {
        reader = new Reader("./data/Tests/doesNotExist.json");
        try {
            reader.read();
            fail();
        } catch (IOException e) {
            //works as intended
        }
    }

    @Test
    void testReadingBooleans() {
        reader = new Reader("./data/Tests/readerTest.json");
        try {
            Connect4Game test = reader.read();
            assertFalse(test.getPastGames().get(0).isPlayerTurn());
            assertFalse(test.getPastGames().get(0).isDraw());
            assertTrue(test.getPastGames().get(0).isWin());
            assertFalse(test.getPastGames().get(1).isPlayerTurn());
            assertFalse(test.getPastGames().get(1).isDraw());
            assertTrue(test.getPastGames().get(1).isWin());
            assertTrue(test.getPastGames().get(2).isPlayerTurn());
            assertFalse(test.getPastGames().get(2).isDraw());
            assertTrue(test.getPastGames().get(2).isWin());
            assertTrue(test.getPastGames().get(3).isPlayerTurn());
            assertTrue(test.getPastGames().get(3).isDraw());
            assertFalse(test.getPastGames().get(3).isWin());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReadingColumnsAndCells() {
        reader = new Reader("./data/Tests/readerTest.json");
        try {
            Connect4Game test = reader.read();
            for (int x = 0; x < 4; x++) {
                GameBoard actual = test.getPastGames().get(x);
                GameBoard expected = gameAnswers.get(x);
                assertEquals(expected.getColumnCount(), actual.getColumnCount());
                assertEquals(expected.getRowCount(), actual.getRowCount());
                for (int y = 0; y < actual.getColumnCount(); y++) {
                    testReadingCells(expected.getColumns().get(y), actual.getColumns().get(y));
                }
            }
            //check if columns match
        } catch (Exception e) {
            fail();
        }
    }

    private void testReadingCells(Column expected, Column actual) {
        assertEquals(expected.getCells().size(), actual.getCells().size());
        for (int x = 0; x < actual.getCells().size(); x++) {
            assertEquals(expected.getCells().get(x).getPieceSymbol(), actual.getCells().get(x).getPieceSymbol());
        }
    }
}
