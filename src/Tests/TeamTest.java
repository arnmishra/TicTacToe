package Tests;

import Model.Team;
import Model.TeamType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TeamTest {
  /**
   * Test to check the constructor by ensuring that a Team's teamType
   * is set properly.
   */
  @Test
  public void testValidConstructor() {
    Team team = new Team(TeamType.O);
    assertEquals(team.getTeamType(), TeamType.O);
  }

  /**
   * Test setting and getting the Team Name
   */
  @Test
  public void teatSetTeamName() {
    Team team = new Team(TeamType.O);
    String teamName = "TestName";
    team.setTeamName(teamName);
    assertEquals(team.getTeamName(), teamName);
  }
}
