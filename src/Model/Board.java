package Model;

import java.util.List;

public class Board {
  private int width; // Width of Board
  private int length; // Length of Board
  private Team teamX = new Team(TeamType.Xs); // Xs team
  private Team teamO = new Team(TeamType.Os); // Os team
  private Piece[][] positions; // Tracks the board and where each piece is

  /**
   * Constructor to make new Board
   * @param width: width of board, usually 3
   * @param length: length of board, usually 3
   */
  public Board(int width, int length) {
    // TODO (not required): Allow 3rd dimension (or Nth dimension?)
    this.width = width;
    this.length = length;
    this.positions = new Piece[length][width];
  }

  public int getWidth() {
    return this.width;
  }

  public int getLength() {
    return this.length;
  }

  public Piece[][] getPositions() {
    return this.positions;
  }

  public Team getTeam(TeamType teamType) {
    if (teamType == TeamType.Os) {
      return this.teamO;
    } else {
      return this.teamX;
    }
  }

  private List<Piece> getTeamPieces(TeamType teamType) {
    Team team = getTeam(teamType);
    return team.getPieces();
  }

  /**
   * Adds a new piece to the board. First checks that no piece already exists
   * at those coordinates.
   * @param piece: New piece to add
   */
  public void addPieceToBoard(Piece piece) {
    int xCoordinate = piece.getxCoordinate();
    int yCoordinate = piece.getyCoordinate();
    // TODO: Improve this assert check to be more detailed
    assert(positions[yCoordinate][xCoordinate] == null);
    positions[yCoordinate][xCoordinate] = piece;
  }

  public boolean isEndGame(TeamType teamType) {
    List<Piece> teamPieces = getTeamPieces(teamType);

  }
}
