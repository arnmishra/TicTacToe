import Model.Piece;
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
    int xCoordinate = 1;
    int yCoordinate = 2;
    Piece piece = new Piece(TeamType.O, xCoordinate, yCoordinate);
    assertEquals(xCoordinate, piece.getxCoordinate());
    assertEquals(yCoordinate, piece.getyCoordinate());
    assertEquals(TeamType.O, piece.getTeamType());
  }
}
