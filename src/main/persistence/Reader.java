package persistence;

import model.Cell;
import model.Column;
import model.Connect4Game;
import model.GameBoard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads ArrayList<Gameboard> from JSON data stored in file
public class Reader {
    private final String filepath;

    //Creates a new reader with filepath
    public Reader(String filepath) {
        this.filepath = filepath;
    }

    //EFFECTS: goes into the file specified by filepath, and creates a Connect4Game using the JSONObject and JSONArray
    // classes. Calls readColumns and readFile
    public Connect4Game read() throws IOException {
        String data = readFile(filepath);
        JSONObject object = new JSONObject(data);
        int gamePlayed = object.getInt("gameCount");
        double winRatio = object.getDouble("winRate");
        Connect4Game connect4Game = new Connect4Game(winRatio, gamePlayed);
        JSONArray games = object.getJSONArray("games");
        for (Object g : games) {
            JSONObject game = (JSONObject) g;
            JSONArray columns = game.getJSONArray("columns");

            connect4Game.addGameToList(new GameBoard(game.getInt("rowCount"), game.getInt("columnCount"),
                    readColumns(data, columns), game.getBoolean("playerTurn"), game.getBoolean("win"),
                    game.getBoolean("draw")));
        }
        return connect4Game;
    }

    //EFFECTS: uses columnsData to create an ArrayList of Columns with the cells filled and returns it
    private ArrayList<Column> readColumns(String data, JSONArray columnsData) {
        ArrayList<Column> columns = new ArrayList<>();
        for (Object c : columnsData) {
            JSONObject column = (JSONObject) c;
            JSONArray cellsData = column.getJSONArray("cells");
            ArrayList<Cell> cells = new ArrayList<>();
            for (Object ce : cellsData) {
                JSONObject cellData = (JSONObject) ce;
                cells.add(new Cell(cellData.getString("pieceSymbol").charAt(0)));
            }
            columns.add(new Column(cells));
        }
        return columns;
    }

    //Taken from JSONSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo'
    //EFFECTS: reads source file as string and returns it
    public String readFile(String filepath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filepath), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }


}
