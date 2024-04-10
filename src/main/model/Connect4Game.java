package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//This class represents a collection of past games, and operates on
//the previous games played
public class Connect4Game {
    private final ArrayList<GameBoard> pastGames;
    private final ArrayList<GameBoard> savedPastGames;
    private double winPercentage;
    private int gamesPlayed;

    //Creates a new Connect4Game with an empty list of past games
    public Connect4Game() {
        pastGames = new ArrayList<>();
        savedPastGames = new ArrayList<>();
        winPercentage = 0;
        gamesPlayed = 0;
    }

    //Creates a new Connect4Game with an empty list of past games, but with a set winPercentage and gamePlayed
    public Connect4Game(double winPercentage, int gamesPlayed) {
        pastGames = new ArrayList<>();
        savedPastGames = new ArrayList<>();
        this.winPercentage = winPercentage;
        this.gamesPlayed = gamesPlayed;
    }

    //REQUIRES: pastGames.size() > 0
    //EFFECTS: finds the win% of the player in the current instance,
    //  draws are treated as 1 win and 1 loss
    public double getWinPercentage() {
        double winCount = 0;
        gamesPlayed = pastGames.size();
        int gameCount = gamesPlayed;
        if (gamesPlayed == 0) {
            throw new ArithmeticException();
        }
        for (GameBoard gameBoard : pastGames) {
            if (gameBoard.isWin() && !gameBoard.isPlayerTurn()) {
                winCount++;
            } else if (gameBoard.isDraw()) {
                winCount++;
                gameCount++;
//                gamesPlayed++;
            }
        }
        winPercentage = (int) ((winCount / gameCount) * 10000) / 100.0;
        EventLog.getInstance().logEvent(new Event("recalculated win percentage to be: " + winPercentage));
        return winPercentage;
    }

    //REQUIRES: gameBoard.isWin() || gameBoard.isDraw()
    //MODIFIES: This
    //EFFECTS: adds a finished game to the list of past games.
    public void addGameToList(GameBoard gameBoard) {
        EventLog.getInstance().logEvent(new Event("Added game to pastGames"));
        pastGames.add(gameBoard);
    }

    //REQUIRES: gameBoard.isWin() || gameBoard.isDraw()
    //MODIFIES: This
    //EFFECTS: adds a finished game to the list of saved past games.
    public void addGameToSave(GameBoard gameBoard) {
        EventLog.getInstance().logEvent(new Event("Added game to savedPastGames"));
        savedPastGames.add(gameBoard);
    }

    //EFFECTS: makes the list of pastGames into a JSONObject, allowing for writing
    public JSONObject toJson() {
        getWinPercentage();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gameCount", gamesPlayed);
        jsonObject.put("winRate", winPercentage);
        jsonObject.put("games", savedPastGames);
        System.out.println(jsonObject);
        EventLog.getInstance().logEvent(new Event("saved games"));
        return jsonObject;
    }

    //EFFECTS: Getters for pastGames and savedPastGames

    public List<GameBoard> getPastGames() {
        return List.copyOf(pastGames);
    }

    public List<GameBoard> getSavedPastGames() {
        return List.copyOf(savedPastGames);
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
