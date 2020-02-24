# Tic-Tac-Toe

## Description
This repository contains the implementation of a Tic-Tac-Toe game where the
computer will always either Win or Tie. You are given the option of being the
first player (X) or the second player (O). There is a score counter that
determines the current standings and you have the option to Undo a move or
Forfeit or Restart your game.

## How To Start
You must have Java installed on your machine. Once you have cloned the
repository, simply `cd TicTacToe/out/production/TicTacToe` and run
`java Controller.RunGame`. This will open a Java Swing UI with the Tic-Tac-Toe
game.

When starting the game, you have a few options. First, you must decide on the
size of the board. If you plan to play against a computer, please choose a 3x3
board. If you wish to play against another person, feel free to use larger board
sizes. Then, you will be asked whether you want the X or O teams to be
computers. **If you choose both teams to be computers, they will continue to
draw again and again since Tic-Tac-Toe cannot be won when played properly by
both sides.** Lastly, you can set the team names of any human players.

During the game, X always goes first. When it is your turn, select a tile and
that is where your piece will go. If you want to forfeit or restart the game or
if you want to start a new game, select the relevant option under the File menu.

## Implementation Details
I used Java Swing for the GUI of this application and the Computer player uses
the Minimax Algorithm to determine what to do in each turn.

There are various implementation improvements that can be added:
1. Improve minimax speed/runtime to account for larger board sizes for computers
2. Allow undo functionality for players
3. Add color-coating on piece movement (one color for last move made, and a
green border around a successful win)
4. Allow more dimensions to the game (i.e. 3-D Tic-Tac-Toe).
5. Create levels of difficulty for the CPU (this can be done by determining max
recursion depth a computer can hit - easier the cpu, lower the max recursion
depth).
6. Add user-input data checking - ensure user-given dimensions are an integer,
ensure both teams don't have the same name, etc.
7. Allow for rectangular boards (and determine win-condition for diagonals)

## Testing
All tests are under the "src/Tests" directory.

## Demo Game:
[Download Game Demo](https://github.com/arnmishra/TicTacToe/raw/master/Demo.mov)
