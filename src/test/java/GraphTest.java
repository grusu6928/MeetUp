import edu.brown.cs.student.stars.Graph;
import edu.brown.cs.student.stars.LookerNode;
import edu.brown.cs.student.stars.StarterNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTest {

  private Graph graph;
  private List<LookerNode> lookers;
  private List<StarterNode> starters;

  @Before
  public void setUp() {
    lookers = new ArrayList<>();
    this.addLookers();
    starters = new ArrayList<>();
    this.addStarters();
    graph = new Graph(lookers, starters);
  }

  @After
  public void tearDown() {
    lookers = null;
    starters = null;
    graph = null;
  }

  private void addLookers() {
    lookers.add(new LookerNode(1, "looker1", "study",
            "11:15", "12:30"));
    lookers.add(new LookerNode(2, "looker2", "study",
            "15:00", "16:00"));
    lookers.add(new LookerNode(3, "looker3", "prayer",
            "21:30", "21:50"));
    lookers.add(new LookerNode(4, "looker4", "prayer",
            "09:15", "09:40"));
    lookers.add(new LookerNode(5, "looker5", "prayer",
            "22:00", "23:00"));
    lookers.add(new LookerNode(6, "looker6", "prayer",
            "17:50", "18:10"));
    lookers.add(new LookerNode(7, "looker7", "prayer",
            "23:00", "23:45"));
    lookers.add(new LookerNode(8, "looker8", "meal",
            "19:30", "22:30"));
    lookers.add(new LookerNode(9, "looker9", "meal",
            "16:15", "19:30"));
    lookers.add(new LookerNode(10, "looker10", "meal",
            "10:00", "11:30"));
  }


  private void addStarters() {
    starters.add(new StarterNode(1, "starter1", "study", "15:00",
            "16:00", "Sciences Library", 5));
    starters.add(new StarterNode(2, "starter2", "prayer", "22:00",
            "22:30", "Main Green", 3));
    starters.add(new StarterNode(3, "starter3", "meal", "12:00",
            "23:00", "Ratty", 10));
  }


  @Test
  public void testAlgorithm() {
    setUp();

    Map<StarterNode, List<LookerNode>> arrangement = graph.runAlgorithm();
    System.out.println("arrangement size " + arrangement.size());
    int numStarters = arrangement.keySet().size();
    int numMatchedLookers = 0;
    for (List<LookerNode> list : arrangement.values()) {
      numMatchedLookers += list.size();

      System.out.println("NEW GROUP");
      for (LookerNode l : list) {
        System.out.println(l.getId());
      }
    }
    assertEquals(3, numStarters);
    assertEquals(10, numMatchedLookers);

    tearDown();
  }


}


// THINGS TO TEST
    // num keys in arrangement = numStarters
    // num values in arrangement = numLookers (MAX)
        // could be less b/c capacity constraints -> CHECK THIS AS SEPARATE CASE
    // for each grp in arrangement: no looker is repeated
