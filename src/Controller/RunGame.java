package Controller;

import Model.Board;
import Model.Piece;
import Model.TeamType;
import View.Gui;
import java.util.Scanner;

public class RunGame {
  private static TeamType teamType = TeamType.X;

  /**
   * Main function to set-up the board and the gui.
   * @param args: any arguments
   */
  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter new Dimensions");
    int dimension = scanner.nextInt();

    Board board = new Board(dimension);
    Gui gui = new Gui(board);
    boolean isEndGame = false;

    while (!isEndGame) {
      System.out.println("Enter new X Coordinate");
      int xCoordinate = scanner.nextInt();
      System.out.println("Enter new Y Coordinate");
      int yCoordinate = scanner.nextInt();
      isEndGame = makeMove(xCoordinate, yCoordinate, board);
      board.printBoard();
    }
  }

  public static Boolean makeMove(
    int xCoordinate,
    int yCoordinate,
    Board board) {
    Piece piece = new Piece(board.getTeam(teamType), xCoordinate, yCoordinate);
    boolean isEndGame = board.isEndGame(teamType, piece);
    if (isEndGame) {
      System.out.println("Game Over. Winner: " + teamType);
    }
    board.addPieceToBoard(piece);
    teamType = teamType.switchTeam();
    return isEndGame;
  }
}
