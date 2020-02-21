package Tests;

import Model.Board;
import Model.Piece;
import Model.Team;
import Model.TeamType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PieceTest {
  /**
   * Test to check the constructor of the Piece class by using a
   * new Piece's x and y coordinates and team information.
   */
  @Test
  public void testValidConstructor() {
    int width = 3;
    int length = 3;
    Board board = new Board(width, length);
    int xCoordinate = 1;
    int yCoordinate = 2;
    Team teamO = board.getTeam(TeamType.Os)
    Piece piece = new Piece(teamO, xCoordinate, yCoordinate);
    assertEquals(xCoordinate, piece.getxCoordinate());
    assertEquals(yCoordinate, piece.getyCoordinate());
    assertEquals(teamO, piece.getTeam());
    assertEquals(TeamType.Os, piece.getTeamType());
  }
}
