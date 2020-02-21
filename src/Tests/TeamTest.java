package Tests;

import Model.Team;
import Model.TeamType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TeamTest {
  /**
   * Test to check the constructor by ensuring that a Team's teamType
   * is set properly.
   * @throws Exception
   */
  @Test
  public void testValidConstructor() {
    Team team = new Team(TeamType.Os);
    assertEquals(team.getTeamType(), TeamType.Os);
  }

  /**
   * Test setting and getting the Team Name
   */
  @Test
  public void teatSetTeamName() {
    Team team = new Team(TeamType.Os);
    String teamName = "TestName";
    team.setTeamName(teamName);
    assertEquals(team.getTeamName(), teamName);
  }
}
