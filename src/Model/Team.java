package Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
  enum TeamType {
    Xs,
    Os
  }

  private TeamType teamType; // X's team or O's team
  private String teamName; // Name of team
  private List<Piece> pieces = new ArrayList<Piece>(); // Pieces played so far
  // TODO (not required): Track team moves in order to allow undo operations

  /**
   * Constructor to create new Team object
   * @param teamType: whether this is the X's team or the O's team.
   */
  public Team(TeamType teamType) {
    this.teamType = teamType;
  }

  public TeamType getTeamType() {
    return this.teamType;
  }

  public String getTeamName() {
    return this.teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public List<Piece> getPieces() {
    return this.pieces;
  }

  public void addPiece(Piece piece) {
    this.pieces.add(piece);
  }
}
