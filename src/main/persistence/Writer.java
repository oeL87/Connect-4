package persistence;

import model.Connect4Game;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Represents a file writer that saves the past games, and the board state of those games.
public class Writer {
    private static final int TAB_SIZE = 4;
    private PrintWriter writer;
    private final String filepath;

    //Creates a new Writer using filepath
    public Writer(String filepath) {
        this.filepath = filepath;
    }

    //MODIFIES: this
    //EFFECTS: creates a new PrintWriter with the filepath given,
    // if the given file is not found, throws FileNotFoundException
    public void start() throws FileNotFoundException {
        writer = new PrintWriter(filepath);
    }

    //EFFECTS: saves the past games into the file
    public void write(Connect4Game connect4Game) {
        JSONObject memory = connect4Game.toJson();
        writer.print(memory.toString(TAB_SIZE));
    }

    //EFFECTS: closes the PrintWriter
    public void close() {
        writer.close();
    }
}
