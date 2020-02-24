package Model;

public class Piece {
  private TeamType teamType; // Which team this piece is on
  private int xCoordinate; // X-Coordinate of Piece
  private int yCoordinate; // Y-Coordinate of Piece

  /**
   * Constructor to create new Piece
   * @param teamType: Whether this piece is on the X team or the O team
   * @param xCoordinate: X-Coordinate of Piece on board
   * @param yCoordinate: Y-Coordinate of Piece on board
   */
  public Piece(TeamType teamType, int xCoordinate, int yCoordinate) {
    this.teamType = teamType;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
  }

  public TeamType getTeamType() {
    return this.teamType;
  }

  public int getxCoordinate() {
    return this.xCoordinate;
  }

  public int getyCoordinate() {
    return this.yCoordinate;
  }
}
