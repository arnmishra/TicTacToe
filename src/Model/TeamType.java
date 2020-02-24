package Model;

/**
 * The two types of teams: X team and O team. N refers to an empty spot.
 */
public enum TeamType {
  X,
  O,
  N;

  /**
   * Switch between X and O team enum
   * @return Opposite team
   */
  public TeamType switchTeam() {
    if (this == TeamType.X) {
      return TeamType.O;
    } else if (this == TeamType.O) {
      return TeamType.X;
    } else {
      return TeamType.N;
    }
  }
}
