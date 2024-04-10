package persistence;

import model.Cell;
import model.Column;
import model.Connect4Game;
import model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    private Writer writer;
    private Connect4Game connect4Game;

    @BeforeEach
    void setup() {
        connect4Game = new Connect4Game();
        ArrayList<Column> columns = new ArrayList<Column>();
        for (int x = 0; x < 7; x++) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (int y = 0; y < 6; y++) {
                cells.add(new Cell('X'));
            }
            columns.add(new Column(cells));
        }
        connect4Game.addGameToList(new GameBoard(6,7, columns, false, false, false));
        connect4Game.addGameToSave(new GameBoard(6,7, columns, false, false, false));
        columns = new ArrayList<Column>();
        for (int x = 0; x < 7; x++) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (int y = 0; y < 6; y++) {
                if (y%2 == 0) {
                    cells.add(new Cell(' '));
                } else {
                    cells.add(new Cell('X'));
                }
            }
            columns.add(new Column(cells));
        }
        connect4Game.addGameToList(new GameBoard(6,7, columns, false, false, false));
        connect4Game.addGameToSave(new GameBoard(6,7, columns, false, false, false));
        columns = new ArrayList<Column>();
        for (int x = 0; x < 7; x++) {
            ArrayList<Cell> cells = new ArrayList<>();
            for (int y = 0; y < 6; y++) {
                if (y%(x+1) == 0) {
                    cells.add(new Cell('X'));
                } else {
                    cells.add(new Cell(' '));
                }
            }
            columns.add(new Column(cells));
        }
        connect4Game.addGameToList(new GameBoard(6,7, columns, false, false, false));
        connect4Game.addGameToSave(new GameBoard(6,7, columns, false, false, false));
    }

    @Test
    void testInvalidFile() {
        try {
            writer = new Writer("./data/Tests/name\0\n.json");
            writer.start();
            fail();
        } catch (FileNotFoundException e) {
            //works as intended
        }
    }

    @Test
    void testWriterFirstGame() {
        try {
            writer = new Writer("./data/Tests/writerTest.json");
            writer.start();
            writer.write(connect4Game);
            writer.close();

            Reader reader = new Reader("./data/Tests/writerTest.json");
            connect4Game = reader.read();
            assertEquals(3, connect4Game.getGamesPlayed());
            assertFalse(connect4Game.getPastGames().get(0).isPlayerTurn());
            assertFalse(connect4Game.getPastGames().get(0).isDraw());
            assertFalse(connect4Game.getPastGames().get(0).isWin());
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 6; y++) {
                    assertEquals('X',
                            connect4Game.getPastGames().get(0).getColumns().get(x).getCells().get(y).getPieceSymbol());
                }
            }
        } catch (IOException e) {
            fail();
        }

    }

    @Test
    void testWriterSecondGame() {
        try {
            writer = new Writer("./data/Tests/writerTest.json");
            writer.start();
            writer.write(connect4Game);
            writer.close();

            Reader reader = new Reader("./data/Tests/writerTest.json");
            connect4Game = reader.read();
            assertEquals(3, connect4Game.getPastGames().size());
            assertFalse(connect4Game.getPastGames().get(1).isPlayerTurn());
            assertFalse(connect4Game.getPastGames().get(1).isDraw());
            assertFalse(connect4Game.getPastGames().get(1).isWin());
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 6; y++) {
                    if (y%2 == 0) {
                        assertEquals(' ',
                                connect4Game.getPastGames().get(1).getColumns().get(x).getCells().get(y).getPieceSymbol());
                    } else {
                        assertEquals('X',
                                connect4Game.getPastGames().get(1).getColumns().get(x).getCells().get(y).getPieceSymbol());
                    }
                }
            }
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriterThirdGame() {
        try {
            writer = new Writer("./data/Tests/writerTest.json");
            writer.start();
            writer.write(connect4Game);
            writer.close();

            Reader reader = new Reader("./data/Tests/writerTest.json");
            connect4Game = reader.read();
            assertEquals(3, connect4Game.getPastGames().size());
            assertFalse(connect4Game.getPastGames().get(2).isPlayerTurn());
            assertFalse(connect4Game.getPastGames().get(2).isDraw());
            assertFalse(connect4Game.getPastGames().get(2).isWin());
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 6; y++) {
                    if (y%(x+1) == 0) {
                        assertEquals('X',
                                connect4Game.getPastGames().get(2).getColumns().get(x).getCells().get(y).getPieceSymbol());
                    } else {
                        assertEquals(' ',
                                connect4Game.getPastGames().get(2).getColumns().get(x).getCells().get(y).getPieceSymbol());
                    }
                }
            }
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void nullPointer() {
        try {
            writer.close();
            fail();
        } catch (NullPointerException e) {
            //works
        }
    }

}
