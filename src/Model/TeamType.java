package Model;

/**
 * The two types of teams: X team and O team. N refers to an empty spot.
 */
public enum TeamType {
  X,
  O,
  N;

  public TeamType switchTeam() {
    if (this == TeamType.X) {
      return TeamType.O;
    } else {
      return TeamType.X;
    }
  }
}
