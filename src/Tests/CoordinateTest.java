import Model.Coordinate;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CoordinateTest {
  /**
   * Test to check the constructor of the Piece class by using a
   * new Piece's x and y coordinates and team information.
   */
  @Test
  public void testValidConstructor() {
    int xCoordinate = 1;
    int yCoordinate = 2;
    Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
    assertEquals(xCoordinate, coordinate.getxCoordinate());
    assertEquals(yCoordinate, coordinate.getyCoordinate());
    xCoordinate = 3;
    coordinate.setxCoordinate(xCoordinate);
    assertEquals(xCoordinate, coordinate.getxCoordinate());
  }
}
