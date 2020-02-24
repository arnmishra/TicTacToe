package Model;


public class Team {
  private TeamType teamType; // X's team or O's team
  private String teamName; // Name of team
  private int score = 0; // Number of times Team has won
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

  public int getScore() {
    return this.score;
  }

  public void incrementTeamScore() {
    this.score++;
  }
}
