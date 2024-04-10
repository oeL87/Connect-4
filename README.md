# My Personal Project

## by Leo Zhang

### Overview

---

- The project will be a recreation of the game *Connect4*, where the goal is to
  connect 4 pieces of the same team (will start off as symbols, then colours) in either a horizontal, vertical, or
  diagonal line.
- All code will be in Java, and be a desktop application at the end.
- The GUI will be coloured circles on a rectangle, the console phase will be pretty text
  (if I can figure that out)
- The saving and restoring will be implemented in the way that the player can save a game they
  are playing and leave and come back.
- The non-trivial X will be the player's pieces, the power-ups, and the enemies pieces.
- The non-trivial Y will be a list of the aforementioned X's.
- The game will prompt the user if they want the game they just played to be saved.
- There will be a screen to view the past games that the user wanted to be saved.

### User stories

---

- As a user, I want to be able to view a start menu that contains a few options of START, STATS, and QUIT
- As a user, I want to be able to select a column and add a piece to it
- As a user, I want to be able to view the board state after I drop a piece
- As a user, I want to be able to play against a basic opponent
- As a user, I want to be able to play multiple games in a single game instance
- As a user, I want to be able to see my overall win percentage
- As a user, I want to be able to save my current game, my previous 10 games, and my win percentage to a file
- As a user, I want to be able to load the past games I chose to be saved, and win percentage from a file
- As a user, I want to be able to use the graphical user interface to play against an opponent

### Instructions for Grader

---

- Click on Start Game
- Play a game on the graphically drawn GameBoard (visual component)
- When the save screen pops up, choose whether to save the game or not
- (either way the game is added to the list of past games, saving chooses if it is saved into memory.json)
- View the past saved games (games the user has chosen to save) by clicking the stats button on the main screen
- Clicking the load button on the main screen will load from memory.json, but will not have any indication on success
- Clicking the yes on the save screen will also immediately save all games that have been chosen, including the current
  one.

## Phase 4: Task 2

Fri Apr 05 08:55:36 PDT 2024

random number 1 generated

Fri Apr 05 08:55:39 PDT 2024

piece placed in column #3 successfully

Fri Apr 05 08:55:40 PDT 2024

random number 5 generated

Fri Apr 05 08:55:40 PDT 2024

piece placed in column #5 successfully

Fri Apr 05 08:55:40 PDT 2024

piece placed in column #3 successfully

Fri Apr 05 08:55:41 PDT 2024

random number 0 generated

Fri Apr 05 08:55:41 PDT 2024

piece placed in column #0 successfully

Fri Apr 05 08:55:41 PDT 2024

piece placed in column #3 successfully

Fri Apr 05 08:55:42 PDT 2024

random number 6 generated

Fri Apr 05 08:55:42 PDT 2024

piece placed in column #6 successfully

Fri Apr 05 08:55:42 PDT 2024

piece placed in column #3 successfully

Fri Apr 05 08:55:44 PDT 2024

Added game to savedPastGames

Fri Apr 05 08:55:44 PDT 2024

Added game to pastGames

Fri Apr 05 08:55:44 PDT 2024

recalculated win percentage to be: 100.0

Fri Apr 05 08:55:44 PDT 2024

saved games

Fri Apr 05 08:55:44 PDT 2024

random number 2 generated

Fri Apr 05 08:55:47 PDT 2024

recalculated win percentage to be: 100.0

Fri Apr 05 08:55:51 PDT 2024

Added game to pastGames

## Phase 4: Task 3

I would likely extract repeated code from the two UIs that are present in the project, and put it in a abstract class
that the two both extend. This would help avoid repetition and make it so the user could choose which version of the
game they would like to play at the start of the program.

Another thing I would have liked to implement is two different GameBoard classes that have similar functionality,
except one is for playing on and the other is for displaying only. This would make it so that the toJson methods would
not have to save as much information into the json files.

The final refactoring I would have liked to do is to make the Column and GameBoard classes as Iterables, which would
allow easier iterating through when both running the game and displaying the game.