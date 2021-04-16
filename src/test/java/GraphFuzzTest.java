import edu.brown.cs.student.stars.Graph;
import edu.brown.cs.student.stars.LookerNode;
import edu.brown.cs.student.stars.StarterNode;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GraphFuzzTest {
//
//  private Graph graph;
//  private List<LookerNode> lookers;
//  private List<StarterNode> starters;
//
//  List<String> eventTypes;
//
//  @Before
//  public void setUp() {
//
//    // id -> easily increment an int
//    // username -> generateName()
//    // event -> generateEvent()
//    // startTime, endTime -> generateTime() *end after start*
//
//    // event
//    eventTypes = new ArrayList<>();
//    eventTypes.add("meal");
//    eventTypes.add("study");
//    eventTypes.add("sport");
//    eventTypes.add("chill");
//    eventTypes.add("prayer");
//    eventTypes.add("other");
//
//
//
//    lookers = new ArrayList<>();
//    starters = new ArrayList<>();
//
//    graph = new Graph(lookers, starters);
//  }
//
//  @After
//  public void tearDown() {
//    lookers = null;
//    starters = null;
//    graph = null;
//  }
//
//  private LookerNode generateLookers() {
//    int id = 0;
//    String username = "";
//
//  }
//
//  private StarterNode generateStarters() {
//
//  }
//
//  private String generateName() {
//    int stringLen = ThreadLocalRandom.current().nextInt(0, 20);
//    StringBuilder randomString = new StringBuilder();
//    String allPossibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//            + "0123456789"
//            + "abcdefghijklmnopqrstuvxyz";
//
//    for (int i = 0; i < stringLen; i++) {
//      int index = ThreadLocalRandom.current().nextInt(0, allPossibleValues.length());
//      randomString.append(allPossibleValues.charAt(index));
//    }
//
////    stringSet.add(randomString); // used a set to make sure no repeats
//    return randomString.toString();
//  }
//
//  private String generateEvent() {
//    Collections.shuffle(eventTypes);
//    return eventTypes.get(0);
//  }
//
//  private String generateTime() {
//    StringBuilder randomTime = new StringBuilder();
//    String allPossibleValues = "0123456789";
//
//    for (int i = 0; i < 5; i++) {
//      if (i == 2) {
//        randomTime.append(":");
//      } else {
//        int index = ThreadLocalRandom.current().nextInt(0, allPossibleValues.length());
//        randomTime.append(allPossibleValues.charAt(index));
//      }
//    }
//    return randomTime.toString();
//  }
//



}
