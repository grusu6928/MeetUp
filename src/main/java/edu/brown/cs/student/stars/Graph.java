package edu.brown.cs.student.stars;

import java.time.LocalTime;
import java.util.*;

public class Graph {

  private GraphEntry[][] adjMatrix;

  private List<LookerNode> lookers;
  private List<StarterNode> centroids;

  private Map<Integer, Integer> lookerIdToCol; // looker id -> col in adjMatrix
  private Map<Integer, Integer> centroidIdToRow; // starter id -> row in adjMatrix
  private HashMap<StarterNode, Integer> capacityMap; // max capacity at each event

  private final int numLookers;
  private final int numNodes;
  private int numIters = 10;



  /**
   *
   * @param lookers list of lookers (from front-end)
   * @param starters list of starters (from front-end)
   */
  public Graph(List<LookerNode> lookers, List<StarterNode> starters) {

    this.numNodes = lookers.size() + starters.size();
    this.numLookers = lookers.size();

    this.lookers = lookers;
    this.centroids = starters;

    this.capacityMap = new HashMap<>();
    this.setCapacityMap();

    this.centroidIdToRow = new HashMap<>();
    this.lookerIdToCol = new HashMap<>();
    this.setIdToIndInMatrixMaps(lookers, starters);

    // n x L adjacency matrix: first L rows -> lookers; second S rows -> starters
    this.adjMatrix = new GraphEntry[this.numNodes][this.numLookers];

    this.setEdgeWeights();
  }


  /**
   * Maps [ looker id -> column index for that looker in adjMatrix]
   * Maps [ starter id -> row index for that starter in adjMatrix]
   * @param lookers
   * @param starters
   */
  private void setIdToIndInMatrixMaps(List<LookerNode> lookers, List<StarterNode> starters) {
    for (int l = 0; l < lookers.size(); l++) {
      LookerNode lNode = lookers.get(l);
      this.lookerIdToCol.put(lNode.getId(), l);
    }

    int rowOffset = this.numLookers; // since first L row indices are for lookers
    for (int s = 0; s < starters.size(); s++) {
      StarterNode sNode = starters.get(s);
      this.centroidIdToRow.put(sNode.getId(), s + rowOffset);
    }
  }


  private Map<StarterNode, PriorityQueue<GraphEntry
          <StarterNode>>> mapCentroidsToEntries() {

    Map<StarterNode, PriorityQueue<GraphEntry<StarterNode>>> starterToLookerEntries = new HashMap<>();

    // maps [centroid -> priority queue of entries in its row]
    for (StarterNode centroid : this.centroids) {
      PriorityQueue<GraphEntry<StarterNode>> pq = new PriorityQueue<>(new WeightComparator());

      int row = this.centroidIdToRow.get(centroid.getId()); // gets row in adj matrix corresponding to centroid's id

      GraphEntry<StarterNode>[] entries = this.adjMatrix[row];



      Collections.addAll(pq, entries);
      starterToLookerEntries.put(centroid, pq);
    }


    return starterToLookerEntries;
  }


  /**
   * Returns a map of the optimal cluster arrangement.
   * A group of lookers tied to each starter (i.e. event)
   * @return optimal map
   */
  public Map<StarterNode, List<LookerNode>> runAlgorithm() {
    // hold data for every iteration
    List<Map<StarterNode, List<LookerNode>>> potentialGroupings = new ArrayList<>();
    double[] scatters = new double[this.numIters];


    for (int iter = 0; iter < numIters; iter++) {

      Collections.shuffle(this.centroids);

      Map<StarterNode, PriorityQueue<GraphEntry<StarterNode>>>
              starterToLookerEntries = this.mapCentroidsToEntries();


      int numMatchedLookers = 0;
      Map<StarterNode, List<LookerNode>> grouping = new HashMap<>();
      // PART 2


      while (!this.allEventsAtCapacity() && numMatchedLookers < this.numLookers) {


        for (StarterNode event : this.centroids) {
          if (!this.eventAtCapacity(event)) {

            PriorityQueue<GraphEntry<StarterNode>> pq = starterToLookerEntries.get(event);
            GraphEntry<StarterNode> entry = pq.poll();
            LookerNode looker = entry.getTo();
            // NOTE: i don't think entry will ever be null, b/c if numMatchedLookers < this.numLookers
            // (which it has to be b/c while loop condition), then there's always something to poll

            // add looker to an event list
            List<LookerNode> attendees;
            if (grouping.get(event) == null) {
              attendees = new ArrayList<>();
            } else {
              attendees = grouping.get(event);
            }
            attendees.add(looker);
            grouping.put(event, attendees);
            ////

            event.incrementAttendees();
            numMatchedLookers++;

            // make sure that looker can't attend other events
            // REMOVES POSSIBILITY OF THIS LOOKER BEING IN ANY OTHER GROUP
            Collection<PriorityQueue<GraphEntry<StarterNode>>> queues = starterToLookerEntries.values();
            for (PriorityQueue q : queues) {
              q.remove(looker);
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


  private double calculateScatter(Map<StarterNode, List<LookerNode>> groupings) {

    double totalScatter = 0;
    Set<StarterNode> clusters = groupings.keySet();

    for (StarterNode cluster : clusters) {
      double kScatter = 0;

      // get centroid row
      int centroidRow = this.centroidIdToRow.get(cluster.getId());
      List<LookerNode> lookers = groupings.get(cluster);
      List<Integer> lookerCols = new ArrayList<>();

      // get list of looker columns
      for (LookerNode l : lookers) {
        int lCol = this.lookerIdToCol.get(l.getId());
        lookerCols.add(lCol);
      }

      // calc scatter btwn centroid and everything in lookerCols
      for (Integer col: lookerCols) {
        kScatter += this.adjMatrix[centroidRow][col].getWeight();
      }

      // calc scatter btwn everything in lookerCols and everything in lookerCols
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
    for (StarterNode s: this.centroids) {
      this.capacityMap.put(s, s.getCapacity());
    }
  }

  /**
   * Determines if all events are at full capacity or not.
   * @return
   */
  private boolean allEventsAtCapacity() {
    for (int i = 0; i < centroids.size(); i++) {
      StarterNode event = centroids.get(i);
      if (!this.eventAtCapacity(event)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if an event is at capacity
   * @param event
   * @return
   */
  private boolean eventAtCapacity(StarterNode event) {
    int currAttendance = event.getNumAttendees();
    int maxCapacity = this.capacityMap.get(event);

    if (currAttendance < maxCapacity) {
      return false;
    }
    return true;
  }



  private double computeHeuristic(GraphNode n1, GraphNode n2) {



//    Friends friendsDB = new Friends();

    // int areFriends = friendsDB.checkFriendShip(n1.getId(), n2.getId()); // no username field, or should change queries to take in ids instead of username
    // int sameEventPref = (n1.getEvent().equals(n2.getEvent())) ? 1 : 0;
    // int timeCompatability = this.timeOverlap(n1.getStartTime(), n1.getEndTime(),
    //         n2.getStartTime(), n2.getEndTime());
    // Skip location for now

    // TODO: think about this math, b/c time compatability is automatically downweighted
    return 0;
    // (1/3) * (areFriends + sameEventPref + timeCompatability);
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

  // TODO: figure out how to normalize
  // TODO: what does overlap mean
  // private int timeOverlap(LocalTime start1, LocalTime end1,
  //                             LocalTime start2, LocalTime end2) {

  //   if (end2.compareTo(start1) < 0 || start2.compareTo(end1) > 0) {
  //     return 0;
  //   }
  //   // remaining 2 cases: overlap
  //   else if (start1.compareTo(start2) < 0) {
  //     return 1;
  //   } else {
  //     return 1;
  //   }
  // }


  /**
   * Fills in the adjacency matrix with GraphEntries.
   */
  private void setEdgeWeights() {
    this.setLookersToLookers();
    this.setStartersToLookers();
  }

  /**
   * Fills in the first L rows of the adjacency matrix. L = numLookers.
   * Each entry: looker - looker
   */
  private void setLookersToLookers() {
    for (int lrow = 0; lrow < this.lookers.size(); lrow++) {
      for (int lcol = 0; lcol < this.lookers.size(); lcol++) {

        GraphEntry<LookerNode> entry;
        if (lrow == lcol) {
          entry = new GraphEntry<>(null, null, lrow, lcol, Double.NEGATIVE_INFINITY);
        } else {
          LookerNode from = this.lookers.get(lrow);
          LookerNode to = this.lookers.get(lcol);
          double weight = this.computeHeuristic(from, to);
          entry = new GraphEntry<>(from, to, lrow, lcol, weight);
        }
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
    for (int srow = rowOffset; srow < this.numNodes; srow++) {
      for (int lcol = 0; lcol < this.numLookers; lcol++) {

        StarterNode from = this.centroids.get(srow - rowOffset);
        LookerNode to = this.lookers.get(lcol);
        double weight = this.computeHeuristic(from, to);
        GraphEntry<StarterNode> entry = new GraphEntry<>(from, to, srow, lcol, weight);

        this.adjMatrix[srow][lcol] = entry;
      }
    }
  }


}
