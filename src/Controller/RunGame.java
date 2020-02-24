package Controller;

import Model.Board;
import Model.Coordinate;
import Model.Piece;
import Model.Team;
import Model.TeamType;
import View.Gui;

public class RunGame {
  private static TeamType teamType = TeamType.X; // Which team's turn it is
  private static Gui gui; // Stores GUI attributes

  /**
   * Main function to set-up the gui. If X is a computer, start the game loop.
   * @param args: any arguments
   */
  public static void main(String args[]) {
    gui = new Gui();
    if (gui.gameBoard.isTeamComputer(TeamType.X)) {
      RunGame.makeComputerMove();
    }
  }

  /**
   * Get the best x/y coordinate to put piece down using the Minimax
   * algorithm and use that to make the computer's move.
   * URL to algorithm documentation:
   * https://www.freecodecamp.org/news/
   * how-to-make-your-tic-tac-toe-game-unbeatable-
   * by-using-the-minimax-algorithm-9d690bad4b37/
   */
  private static void makeComputerMove() {
    Coordinate coordinate = gui.gameBoard.getBestCoordinates(teamType);
    makeMove(coordinate.getxCoordinate(), coordinate.getyCoordinate());
  }

  /**
   * Reset the game to make it X's turn. If X is a computer, start the game
   * loop.
   */
  public static void resetTeamTurn() {
    gui.gameBoard.resetNumMovesAvailable();
    teamType = TeamType.X;
    if (gui.gameBoard.isTeamComputer(TeamType.X)) {
      RunGame.makeComputerMove();
    }
  }

  /**
   * Process a team's move to specified X and Y coordinates.
   * @param xCoordinate: X-Coordinate of move
   * @param yCoordinate: Y-Coordinate of move
   */
  public static void makeMove(
    int xCoordinate,
    int yCoordinate) {
    Board board = gui.gameBoard;
    Piece piece = new Piece(teamType, xCoordinate, yCoordinate);
    boolean isValid = board.isValidMove(piece);
    System.out.println("Attempting Move to x: " + xCoordinate + " y: " + yCoordinate);
    if (!isValid) {
      gui.errorMessage("Invalid Move");
      return;
    }
    int endGame =
      board.isEndGame(
        teamType, piece, board.getPositions(), board.getNumMovesAvailable());
    board.addPieceToBoard(piece);
    Gui.makeMove(piece);
    if (endGame == 1) {
      System.out.println("Game Over. Winner: " + teamType);
      gui.isGameOver(teamType);
      resetTeamTurn();
    } else if (endGame == 0) {
      System.out.println("Game resulted in a draw.");
      board.incrementNumDraws();
      gui.isDraw();
      resetTeamTurn();
    } else {
      teamType = teamType.switchTeam();
      if (board.isTeamComputer(teamType)) {
        // TODO: make a game loop for when both players are computers so we
        // don't get into a recursive mess. Current if both players are
        // computers makeMove calls makeComputerMove calls makeMove etc.
        makeComputerMove();
      }
    }
  }

  /**
   * Forfeit the game for the team which is currently playing. This gives a
   * win to the opposing team.
   */
  public static void forfeitGame() {
    Team notForfeitTeam = gui.gameBoard.getTeam(teamType.switchTeam());
    notForfeitTeam.incrementTeamScore();
  }
}
