package edu.brown.cs.student.stars.Graph;

import edu.brown.cs.student.stars.Database.Friends;
import edu.brown.cs.student.stars.HaversineDistanceCalculator;
import edu.brown.cs.student.stars.WeightComparator;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


public class Graph {

  private final GraphEntry[][] adjMatrix;

  private final List<Looker> lookers;
  private final List<Starter> centroids;

  private final Map<Integer, Integer> lookerIdToCol; // looker id -> col in adjMatrix
  private final Map<Integer, Integer> centroidIdToRow; // starter id -> row in adjMatrix
  private final HashMap<Starter, Integer> capacityMap; // max capacity at each event

  private final int numLookers;
  private final int numNodes;
  private int numIters = 10; // TODO: think about this. the num of diff scatters is the num of diff arrangements



  /**
   *
   * @param lookers list of lookers (from front-end)
   * @param starters list of starters (from front-end)
   */
  public Graph(List<Looker> lookers, List<Starter> starters) {

    System.out.println("graph a");
    this.numNodes = lookers.size() + starters.size();
    System.out.println("graph b");
    this.numLookers = lookers.size();
    System.out.println("graph c");

    this.lookers = lookers;
    System.out.println("graph d");
    this.centroids = starters;
    System.out.println("graph e");

    this.capacityMap = new HashMap<>();
    System.out.println("graph f");
    this.setCapacityMap();
    System.out.println("graph g");

    this.centroidIdToRow = new HashMap<>();
    System.out.println("graph h");
    this.lookerIdToCol = new HashMap<>();
    System.out.println("graph i");
    this.setIdToIndInMatrixMaps(lookers, starters);
    System.out.println("graph j");

    // n x L adjacency matrix: first L rows -> lookers; second S rows -> starters
    this.adjMatrix = new GraphEntry[this.numNodes][this.numLookers];
    System.out.println("graph k");

    this.setEdgeWeights();
    System.out.println("graph L");
  }


  /**
   * Maps [ looker id -> column index for that looker in adjMatrix]
   * Maps [ starter id -> row index for that starter in adjMatrix]
   * @param lookers
   * @param starters
   */
  private void setIdToIndInMatrixMaps(List<Looker> lookers, List<Starter> starters) {
    for (int l = 0; l < lookers.size(); l++) {
      Looker lNode = lookers.get(l);
      this.lookerIdToCol.put(lNode.getId(), l);
    }

    int rowOffset = this.numLookers; // since first L row indices are for lookers
    for (int s = 0; s < starters.size(); s++) {
      Starter sNode = starters.get(s);
      this.centroidIdToRow.put(sNode.getId(), s + rowOffset);
    }
  }


  private Map<Starter, PriorityQueue<GraphEntry
          <Starter>>> mapCentroidsToEntries() {

    Map<Starter, PriorityQueue<GraphEntry<Starter>>> starterToLookerEntries = new HashMap<>();

    // maps [centroid -> priority queue of entries in its row]
    for (Starter centroid : this.centroids) {
      PriorityQueue<GraphEntry<Starter>> pq = new PriorityQueue<>(new WeightComparator());

      int row = this.centroidIdToRow.get(centroid.getId()); // gets row in adj matrix corresponding to centroid's id

      GraphEntry<Starter>[] entries = this.adjMatrix[row];
      Collections.addAll(pq, entries);
      starterToLookerEntries.put(centroid, pq);
    }
    return starterToLookerEntries;
  }

  /**
   * At the beginning of each iteration, initializes all events to be
   * mapped to an empty list of attendees.
   * @return Map of events to empty attendees' lists
   */
  private Map<Starter, List<Looker>> initializeEventsToEmpty() {
    Map<Starter, List<Looker>> grouping = new HashMap<>();

    for (Starter event: this.centroids) {
      grouping.put(event, new ArrayList<>());
    }
    return grouping;
  }


  /**
   * At the beginning of each iteration, resets each event's attendance
   * counter to 0.
   */
  private void resetEventAttendance() {
    for (Starter event: this.centroids) {
      event.setNumAttendees(0);
    }
  }


  /**
   * Returns a map of the optimal cluster arrangement.
   * A group of lookers tied to each starter (i.e. event)
   * @return optimal map
   */
  public Map<Starter, List<Looker>> runAlgorithm() {

    // hold data for every iteration
    List<Map<Starter, List<Looker>>> potentialGroupings = new ArrayList<>();
    double[] scatters = new double[this.numIters];


    for (int iter = 0; iter < numIters; iter++) {

      Collections.shuffle(this.centroids);

      Map<Starter, PriorityQueue<GraphEntry<Starter>>>
              starterToLookerEntries = this.mapCentroidsToEntries();

      this.resetEventAttendance();

      int numMatchedLookers = 0;

      // maps each event (starterNode) to empty list
      Map<Starter, List<Looker>> grouping = this.initializeEventsToEmpty();

      // PART 2
      while (!this.allEventsAtCapacity() && numMatchedLookers < this.numLookers) {

        for (Starter event : this.centroids) {
          PriorityQueue<GraphEntry<Starter>> pq = starterToLookerEntries.get(event);

          if (!this.eventAtCapacity(event) && pq.size() > 0) {

            GraphEntry<Starter> entry = pq.poll();
            Looker looker = entry.getTo();
            // NOTE: i don't think entry will ever be null, b/c if numMatchedLookers < this.numLookers
            // (which it has to be b/c while loop condition), then there's always something to poll

            // add looker to an event list
            List<Looker> attendees = grouping.get(event);
            attendees.add(looker);
            grouping.put(event, attendees);
            ////

            event.incrementAttendees();
            numMatchedLookers++;

            // make sure that looker can't attend other events
            // REMOVES POSSIBILITY OF THIS LOOKER BEING IN ANY OTHER GROUP
            for (Starter s : starterToLookerEntries.keySet()) {
              PriorityQueue<GraphEntry<Starter>> q = starterToLookerEntries.get(s);
              q.removeIf(ge -> ge.getTo().getId() == looker.getId());
              starterToLookerEntries.put(s, q); // re-put in queue
            }

          }
        }
      }

      // done making this iter's grouping
      potentialGroupings.add(grouping);
      scatters[iter] = this.calculateScatter(grouping);
    }

    // after all groups are set
    int bestIter = argmin(scatters);
    return potentialGroupings.get(bestIter);

  }

  /**
   * Gets the argmin of a list.
   * @param list
   * @return
   */
  private int argmin(double[] list) {
    double min = Double.POSITIVE_INFINITY;
    int argmin = 0;
    for (int i = 0; i < list.length; i++) {
      double elem = list[i];
      if (Double.compare(elem, min) < 0) {
        min = elem;
        argmin = i;
      }
    }
    return argmin;
  }


  /**
   * Calculates the scatter for a grouping.
   * @param groupings
   * @return
   */
  private double calculateScatter(Map<Starter, List<Looker>> groupings) {

    double totalScatter = 0;
    Set<Starter> clusters = groupings.keySet();

    for (Starter cluster : clusters) {
      double kScatter = 0;

      // get centroid row
      int centroidRow = this.centroidIdToRow.get(cluster.getId());
      List<Looker> lookers = groupings.get(cluster);
      List<Integer> lookerCols = new ArrayList<>();

      // get list of looker columns
      for (Looker l : lookers) {
        int lCol = this.lookerIdToCol.get(l.getId());
        lookerCols.add(lCol);
      }

      // calc scatter btwn centroid and everything in lookerCols
      for (Integer col: lookerCols) {
        kScatter += this.adjMatrix[centroidRow][col].getWeight();
      }

      // calc scatter between everything in lookerCols and everything in lookerCols
          // skips over self-loops
      for (Integer row: lookerCols) {
        for (Integer col: lookerCols) {
          if (!row.equals(col)) {
            kScatter += this.adjMatrix[row][col].getWeight();
          }
        }
      }

      int n = lookers.size() + 1;
      totalScatter += n * kScatter;
    }

    return totalScatter;
  }


  /**
   * Sets the capacity map, which will be used to keep track
   * of how many spots are left in each starter's event.
   */
  private void setCapacityMap() {
    for (Starter s: this.centroids) {
      this.capacityMap.put(s, s.getCapacity());
    }
  }

  /**
   * Determines if all events are at full capacity or not.
   * @return true if all at capacity, false o.w.
   */
  private boolean allEventsAtCapacity() {
    for (Starter event : this.centroids) {
      if (!this.eventAtCapacity(event)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if an event is at capacity.
   * @param event event to check if at capacity
   * @return true if at capacity, false o.w.
   */
  private boolean eventAtCapacity(Starter event) {
    int currAttendance = event.getNumAttendees();
    int maxCapacity = this.capacityMap.get(event);

    return currAttendance >= maxCapacity;
  }



  private double computeHeuristic(FormSubmission n1, FormSubmission n2) {

//    int areFriends = Friends.getInstance().checkFriendShip(n1.getUsername(), n2.getUsername()) ? 1 : 0;
    System.out.println("entered");
    int areFriends = 1;
    int sameActivityPref = (n1.getActivity().equals(n2.getActivity())) ? 1 : 0;
    System.out.println("sameActivity");
    System.out.println("ID: " + n1.getId());
    System.out.println(n1.getUsername());
    System.out.println("startTimes: " +n1.getStartTime() + " " + n2.getStartTime());
    System.out.println("endTimes: " +n1.getEndTime() + " " + n2.getEndTime());
    System.out.println("stuck");
    double timeCompat = this.timeOverlap(n1.getStartTime(), n1.getEndTime(),
            n2.getStartTime(), n2.getEndTime());
    System.out.println("timeCompat");
    double locationDist = new HaversineDistanceCalculator().calcHaversineDistance(n1.getLocation(), n2.getLocation());
    // TODO: how to normalize location dist

    // Skip location for now
    System.out.println("returned");
    return (1.0 / 3.0) * (areFriends + sameActivityPref + timeCompat);
  }

  /**
   * Return normalized value between 0 and 1.
   * 1 if perfect overlap, 0 if no overlap
   * @param start1
   * @param end1
   * @param start2
   * @param end2
   * @return
   */
   private double timeOverlap(String start1, String end1,
                               String start2, String end2) {

     LocalTime s1 = LocalTime.parse(start1);
     LocalTime e1 = LocalTime.parse(end1);
     LocalTime s2 = LocalTime.parse(start2);
     LocalTime e2 = LocalTime.parse(end2);

     // if statement -> no overlap
     if (e2.compareTo(s1) < 0 || s2.compareTo(e1) > 0) {
       return 0;
     } else {
       return this.calcTimeOverlap(s1, e1, s2, e2);
     }
   }

  /**
   * Helper function to timeOverlap(). Computes a normalized value of time overlap between
   * two time ranges. Returns (A intersect B) / (A U B), where A and B are time ranges.
   * @param s1
   * @param e1
   * @param s2
   * @param e2
   * @return
   */
   private double calcTimeOverlap(LocalTime s1, LocalTime e1,
                              LocalTime s2, LocalTime e2) {

     double durationA = Duration.between(s1, e1).toMinutes(); // if this is negative -> it's an error
     double durationB = Duration.between(s2, e2).toMinutes(); // if this is negative -> it's an error
     double overlap;

     if (s1.compareTo(s2) < 0) {
       overlap = Duration.between(s2, e1).toMinutes();
     } else {
       overlap = Duration.between(s1, e2).toMinutes();
     }

     return overlap / (durationA + durationB - overlap);
   }


  /**
   * Fills in the adjacency matrix with GraphEntries.
   */
  private void setEdgeWeights() {
    // TODO: HAVE an initial LOOP TO CALCULATE haversineDist, cache [row, col] -> dist
      // TODO: use this comp to get min & max so can normalize dist
    // THEN -> do the existing loops in these methods.
    System.out.println(" graph M");
    this.setLookersToLookers();
    System.out.println(" graph N");
    this.setStartersToLookers();
    System.out.println(" graph O");
  }

  /**
   * Fills in the first L rows of the adjacency matrix. L = numLookers.
   * Each entry: looker - looker
   */
  private void setLookersToLookers() {
    for (int lrow = 0; lrow < this.lookers.size(); lrow++) {
      for (int lcol = 0; lcol < this.lookers.size(); lcol++) {

        GraphEntry<Looker> entry;
        Looker from = this.lookers.get(lrow);
        Looker to = this.lookers.get(lcol);

        // sets self-loop weights to negative infinity
        double weight;
        if (lrow == lcol) {
          weight = Double.NEGATIVE_INFINITY;
        } else {
          weight = this.computeHeuristic(from, to);
        }
        entry = new GraphEntry<>(from, to, weight);

        this.adjMatrix[lrow][lcol] = entry;
      }
    }
  }

  /**
   * Fills in the next S rows of the adjacency matrix. S = numStarters.
   * Each entry: starter - looker
   */
  private void setStartersToLookers() {
    int rowOffset = this.numLookers;
    System.out.println("rowOffset:" + rowOffset);
    System.out.println("before for loop");
    for (int srow = rowOffset; srow < this.numNodes; srow++) {
      for (int lcol = 0; lcol < this.numLookers; lcol++) {
        System.out.println("from");
        Starter from = this.centroids.get(srow - rowOffset);
        System.out.println("to");
        Looker to = this.lookers.get(lcol);
        System.out.println("weight");
        double weight = this.computeHeuristic(from, to);
        System.out.println("entry");
        GraphEntry<Starter> entry = new GraphEntry<>(from, to, weight);
        System.out.println("adj");
        this.adjMatrix[srow][lcol] = entry;
      }
    }
    System.out.println("after for loop");
  }


}
