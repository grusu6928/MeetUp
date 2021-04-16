import edu.brown.cs.student.stars.Graph;
import edu.brown.cs.student.stars.LookerNode;
import edu.brown.cs.student.stars.StarterNode;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphFuzzTest {

  public static final int MAX_LOOKERS = 1000;
  public static final int MAX_STARTERS = 100;
  public static final int MAX_CAPACITY = 50;

  private Graph graph;
  private List<LookerNode> lookers;
  private List<StarterNode> starters;
  private List<String> eventTypes;
  private int summedCapacity;


  @Before
  public void setUp() {

    // event
    eventTypes = new ArrayList<>();
    eventTypes.add("meal");
    eventTypes.add("study");
    eventTypes.add("sport");
    eventTypes.add("chill");
    eventTypes.add("prayer");
    eventTypes.add("other");

    lookers = new ArrayList<>();
    starters = new ArrayList<>();
  }

  private void setUp2(int numLookers, int numStarters) {

    for (int l = 0; l < numLookers; l ++) {
      this.lookers.add(this.generateLooker(l));
    }
    for (int s = 0; s < numStarters; s++) {
      this.starters.add(this.generateStarter(s));
    }
    graph = new Graph(lookers, starters);
  }

  @After
  public void tearDown() {
    lookers = null;
    starters = null;
    graph = null;
  }

  // TODO: change to Junit5 and do repeatedTest

  /**
   * Runs the graph algorithm on a randomized input of lookers and starters
   * (random in both their properties and quantity), and ensures the following constraints are met
   * 1) number of events returned = number of starters
   * 2) number of lookers matched = min(number of lookers, total capacity summed across all events)
   * 3) each looker is matched to maximum one event, and only appears once on that event list
   */
  @Test
  public void fuzzTest() {

    int numLookers = this.genRandomNum(0, MAX_LOOKERS);
    int numStarters = this.genRandomNum(0, MAX_STARTERS);
    this.setUp2(numLookers, numStarters);
    Map<StarterNode, List<LookerNode>> groups = graph.runAlgorithm();

    int numEvents = groups.keySet().size();

    // constraint 1
    assertEquals(numStarters, numEvents);


    int numMatchedLookers = 0;
    Map<LookerNode, Integer> lookerCount = new HashMap<>();

    for (List<LookerNode> list : groups.values()) {
      numMatchedLookers += list.size();
      for (LookerNode l : list) {
        lookerCount.merge(l, 1, Integer::sum);
      }
    }

    // constraint 2
    assertEquals(Math.min(numLookers, this.summedCapacity), numMatchedLookers);

    // constraint 3
    assertTrue(lookerCount.values().stream().allMatch(i -> i <= 1));

  }

  private LookerNode generateLooker(int id) {
    int stringLen = ThreadLocalRandom.current().nextInt(0, 20);
    String username = this.generateName(stringLen);
    String event = this.generateEvent();
    String startTime = this.generateStartTime();
    String endTime = this.generateEndTime(startTime);
    return new LookerNode(id, username, event, startTime, endTime);
  }

  private StarterNode generateStarter(int id) {
    int stringLen = ThreadLocalRandom.current().nextInt(0, 20);
    String username = this.generateName(stringLen);
    String event = this.generateEvent();
    String startTime = this.generateStartTime();
    String endTime = this.generateEndTime(startTime);
    int capacity = this.generateCapacity();

    this.summedCapacity += capacity;

    return new StarterNode(id, username, event, startTime, endTime, "", capacity);
  }

  private String generateName(int len) {
    StringBuilder randomString = new StringBuilder();
    String allPossibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    for (int i = 0; i < len; i++) {
      int index = ThreadLocalRandom.current().nextInt(0, allPossibleValues.length());
      randomString.append(allPossibleValues.charAt(index));
    }
    return randomString.toString();
  }

  private String generateEvent() {
    Collections.shuffle(this.eventTypes);
    return this.eventTypes.get(0);
  }


  private String generateStartTime() {
    int hour;
    int minute;
    do {
      hour = this.genRandomNum(0, 24);
      minute = this.genRandomNum(0, 60);
    } while(hour == 23 && minute == 59);

    return this.numToString(hour) + ":" + this.numToString(minute);
  }


  /**
   * Generates an end time, constrained by start time to ensure
   * that the end time comes after the start time.
   * @param startTime start time by which to constrain end time
   * @return a stochastic end time
   */
  private String generateEndTime(String startTime) {
    LocalTime sTime = LocalTime.parse(startTime);

    int startHour = sTime.getHour();
    int startMin = sTime.getMinute();

    int endHour = this.genRandomNum(startHour, 24);
    int endMin;

    if (startHour == endHour && startMin == 59) {
      endHour = this.genRandomNum(startHour + 1, 24);
      endMin = this.genRandomNum(0, 60);
    } else if (startHour == endHour) {
      endMin = this.genRandomNum(startMin + 1, 60);
    } else {
      endMin = this.genRandomNum(0, 60);
    }
    return this.numToString(endHour) + ":" + this.numToString(endMin);
  }

  /**
   * Converts a number to a string, and if the number is a single digit,
   * then also pads that number with one leading zero.
   * @param num number to convert to string
   * @return number as a string
   */
  private String numToString(int num) {
    String sNum;
    if (num < 10) {
      sNum = String.format("%02d", num);
    } else {
      sNum = String.valueOf(num);
    }
    return sNum;
  }

  private int generateCapacity() {
    return this.genRandomNum(0, MAX_CAPACITY);
  }

  private int genRandomNum(int low, int high) {
    Random r = new Random();
    return r.nextInt(high-low) + low;
  }




}
