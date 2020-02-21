package Tests;

import Model.Piece;
import Model.Board;
import Model.TeamType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BoardTest {

  /**
   * Test for the Board constructor to ensure that the width and
   * length are properly set.
   */
  @Test
  public void testValidConstructor() {
    int width = 3;
    int length = 4;
    Board board = new Board(width, length);
    assertEquals(board.getWidth(), width);
    assertEquals(board.getLength(), length);
  }

  /**
   * Test that adding a piece for either team works but adding a piece to the
   * same spot fails
   */
  @Test(expected = Exception.class)
  public void testAddPiece() {
    int width = 3;
    int length = 3;
    Board board = new Board(width, length);

    Piece piece = new Piece(board.getTeam(TeamType.Xs), 0, 0);
    board.addPieceToBoard(piece);
    assertEquals(board.getPositions()[0][0], piece);

    Piece badPiece = new Piece(board.getTeam(TeamType.Xs), 0, 0);
    // Should throw exception since same coordinates being added
    board.addPieceToBoard(badPiece);

    Piece piece2 = new Piece(board.getTeam(TeamType.Os), 0, 1);
    board.addPieceToBoard(piece2);
    assertEquals(board.getPositions()[1][0], piece2);
  }

}
