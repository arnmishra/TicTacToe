import Model.Coordinate;
import Model.Piece;
import Model.Board;
import Model.TeamType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BoardTest {

  /**
   * Test for the Board constructor to ensure that the dimensions are
   * properly set.
   */
  @Test
  public void testValidConstructor() {
    int dimension = 3;
    Board board = new Board(dimension);
    assertEquals(board.getDimension(), dimension);
    assertEquals(board.getNumMovesAvailable(), dimension * dimension);
    assertEquals(board.getPositions()[0][0].getTeamType(), TeamType.N);
  }

  /**
   * Test that adding a piece for either team works but adding a piece to the
   * same spot fails
   */
  @Test()
  public void testAddPiece() {
    int dimension = 3;
    Board board = new Board(dimension);

    Piece piece = new Piece(TeamType.X, 0, 0);
    board.addPieceToBoard(piece);
    assertEquals(board.getPositions()[0][0], piece);

    Piece piece2 = new Piece(TeamType.O, 0, 1);
    board.addPieceToBoard(piece2);
    assertEquals(board.getPositions()[1][0], piece2);
  }

  /**
   * Test Column Win condition
   */
  @Test
  public void testColumnWin() {
    int dimension = 3;
    Board board = new Board(dimension);
    board.addPieceToBoard(new Piece(TeamType.X, 0, 0));
    board.addPieceToBoard(new Piece(TeamType.X, 0, 1));
    Piece winningPiece = new Piece(TeamType.X, 0, 2);
    int isEndGame = board.isEndGame(TeamType.X, winningPiece,
      board.getPositions(), board.getNumMovesAvailable());
    assertEquals(isEndGame, 1);
  }

  /**
   * Test Row Win condition
   */
  @Test
  public void testRowWin() {
    int dimension = 3;
    Board board = new Board(dimension);
    board.addPieceToBoard(new Piece(TeamType.X, 0, 0));
    board.addPieceToBoard(new Piece(TeamType.X, 1, 0));
    Piece winningPiece = new Piece(TeamType.X, 2, 0);
    int isEndGame = board.isEndGame(TeamType.X, winningPiece,
      board.getPositions(), board.getNumMovesAvailable());
    assertEquals(isEndGame, 1);
  }

  /**
   * Test Diagonal Win condition
   */
  @Test
  public void testDiagonalWin() {
    int dimension = 3;
    Board board = new Board(dimension);
    board.addPieceToBoard(new Piece(TeamType.X, 0, 0));
    board.addPieceToBoard(new Piece(TeamType.X, 1, 1));
    Piece winningPiece = new Piece(TeamType.X, 2, 2);
    int isEndGame = board.isEndGame(TeamType.X, winningPiece,
      board.getPositions(), board.getNumMovesAvailable());
    assertEquals(isEndGame, 1);
  }

  /**
   * Test No Win
   */
  @Test
  public void testNotWin() {
    int dimension = 3;
    Board board = new Board(dimension);
    board.addPieceToBoard(new Piece(TeamType.X, 0, 0));
    Piece winningPiece = new Piece(TeamType.X, 2, 2);
    int isEndGame = board.isEndGame(TeamType.X, winningPiece,
      board.getPositions(), board.getNumMovesAvailable());
    assertEquals(isEndGame, -1);
  }

  /**
   * Test Draw
   */
  @Test
  public void testDraw() {
    int dimension = 3;
    Board board = new Board(dimension);
    board.addPieceToBoard(new Piece(TeamType.X, 0, 0));
    board.addPieceToBoard(new Piece(TeamType.O, 0, 1));
    board.addPieceToBoard(new Piece(TeamType.X, 0, 2));
    board.addPieceToBoard(new Piece(TeamType.O, 1, 0));
    board.addPieceToBoard(new Piece(TeamType.X, 1, 1));
    board.addPieceToBoard(new Piece(TeamType.O, 1, 2));
    board.addPieceToBoard(new Piece(TeamType.O, 2, 0));
    board.addPieceToBoard(new Piece(TeamType.X, 2, 1));
    Piece winningPiece = new Piece(TeamType.O, 2, 2);
    int isEndGame = board.isEndGame(TeamType.O, winningPiece,
      board.getPositions(), board.getNumMovesAvailable());
    assertEquals(isEndGame, 0);
  }

  /**
   * Test Validity of Moves based on board dimensions
   */
  @Test
  public void testValidMove() {
    int dimension = 3;
    Board board = new Board(dimension);
    Piece invalidPiece = new Piece(TeamType.X, -1, 0);
    assertFalse(board.isValidMove(invalidPiece));
    Piece invalidPiece2 = new Piece(TeamType.X, 3, 0);
    assertFalse(board.isValidMove(invalidPiece2));
    Piece invalidPiece3 = new Piece(TeamType.X, 0, -1);
    assertFalse(board.isValidMove(invalidPiece3));
    Piece invalidPiece4 = new Piece(TeamType.X, 0, 3);
    assertFalse(board.isValidMove(invalidPiece4));
    Piece validPiece = new Piece(TeamType.X, 0, 0);
    assertTrue(board.isValidMove(validPiece));
  }

  /**
   * Test Deep Copy of Array
   */
  @Test
  public void testDeepCopyObjectArray() {
    int dimension = 3;
    Board board = new Board(dimension);
    Piece[][] shallowCopyPieces = board.getPositions();
    assertEquals(board.getPositions()[0][0].getTeamType(), TeamType.N);
    Piece replacementShallowPiece = new Piece(TeamType.X, 0, 0);
    shallowCopyPieces[0][0] = replacementShallowPiece;
    assertEquals(board.getPositions()[0][0].getTeamType(), TeamType.X);
    assertEquals(shallowCopyPieces[0][0].getTeamType(), TeamType.X);
    Piece[][] deepCopyPieces = board.deepCopyPositions(board.getPositions());
    Piece replacementDeepPiece = new Piece(TeamType.X, 1, 1);
    deepCopyPieces[1][1] = replacementDeepPiece;
    assertEquals(board.getPositions()[1][1].getTeamType(), TeamType.N);
    assertEquals(deepCopyPieces[1][1].getTeamType(), TeamType.X);
  }

  @Test
  public void testMiniMax() {
    int dimension = 3;
    Board board = new Board(dimension);
    board.addPieceToBoard(new Piece(TeamType.X, 0, 0));
    board.addPieceToBoard(new Piece(TeamType.X, 1, 1));
    board.addPieceToBoard(new Piece(TeamType.O, 2, 2));
    board.addPieceToBoard(new Piece(TeamType.O, 1, 2));
    // Should be 0,2 to prevent the 3 in a row
    Coordinate best = board.getBestCoordinates(TeamType.X);
    assertEquals(best.getxCoordinate(), 0);
    assertEquals(best.getyCoordinate(), 2);

    board.addPieceToBoard(new Piece(TeamType.X, 0, 2));
    // Should be 0,1 to prevent the 3 in a row
    Coordinate best2 = board.getBestCoordinates(TeamType.O);
    assertEquals(best2.getxCoordinate(), 0);
    assertEquals(best2.getyCoordinate(), 1);
  }
}
