package Controller;

import Model.Board;
import Model.Piece;
import Model.TeamType;

public class RunGame {
  /**
   * Main function to set-up the board and the gui.
   * @param args: any arguments
   */
  public static void main(String args[]) {
    Board board = new Board(3);
    Piece piece = new Piece(board.getTeam(TeamType.O), 0, 1);
    board.addPieceToBoard(piece);
    board.printBoard();
  }


}
