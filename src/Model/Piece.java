package Model;

public class Piece {
  private Team team; // Which team this piece is on
  private int xCoordinate; // X-Coordinate of Piece
  private int yCoordinate; // Y-Coordinate of Piece

  /**
   * Constructor to create new Piece
   * @param team: Whether this piece is on the Xs team or the Os team
   * @param xCoordinate: X-Coordinate of Piece on board
   * @param yCoordinate: Y-Coordinate of Piece on board
   */
  public Piece(Team team, int xCoordinate, int yCoordinate) {
    this.team = team;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
  }

  public Team getTeam() {
    return this.team;
  }

  public Team.TeamType getTeamType() {
    return this.team.getTeamType();
  }

  public int getxCoordinate() {
    return this.xCoordinate;
  }

  public int getyCoordinate() {
    return this.yCoordinate;
  }
}
