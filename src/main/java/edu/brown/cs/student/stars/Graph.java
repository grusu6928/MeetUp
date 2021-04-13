package edu.brown.cs.student.stars;

import java.util.*;

public class Graph {

  private GraphEntry[][] adjMatrix;

  private ArrayList<LookerNode> lookers;
  private ArrayList<StarterNode> centroids;

  private Map<Integer, Integer> lookerIdToCol; // looker id -> col in adjMatrix
  private Map<Integer, Integer> centroidIdToRow; // starter id -> row in adjMatrix
  private HashMap<StarterNode, Integer> capacityMap; // max capacity at each event

  private final int numLookers;
  private final int numNodes;
  private int numIters = 10;


  // TODO: 1) finish out last bits of algorithm, 2) Heuristic computation
  // INTEGRATION w/ Amin -> use friends SQL table in Heuristic comp

  // INTEGRATION w/ Front-End ->
  // 1) receive lookers & starters lists
  // 2) run algorithm
  // 3) send back list of groups


  /**
   *
   * @param lookers list of lookers (from front-end)
   * @param starters list of starters (from front-end)
   */
  public Graph(ArrayList<LookerNode> lookers, ArrayList<StarterNode> starters) {

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

    this.runAlgorithm();

  }


  /**
   * Maps [ looker id -> column index for that looker in adjMatrix]
   * Maps [ starter id -> row index for that starter in adjMatrix]
   * @param lookers
   * @param starters
   */
  private void setIdToIndInMatrixMaps(ArrayList<LookerNode> lookers, ArrayList<StarterNode> starters) {
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




  private void runAlgorithm() {
    List<Map<StarterNode, List<LookerNode>>> potentialGroupings = new ArrayList<>();
    double[] scatters = new double[this.numIters];

    for (int iter = 0; iter < numIters; iter++) {
      Collections.shuffle(this.centroids);


      Map<StarterNode, PriorityQueue<GraphEntry<StarterNode>>> starterToLookerEntries = new HashMap<>();

      // maps [centroid -> priority queue of entries in its row]
      for (StarterNode centroid : this.centroids) {
        PriorityQueue<GraphEntry<StarterNode>> pq = new PriorityQueue<>(new WeightComparator());

        int row = this.centroidIdToRow.get(centroid.getId()); // gets row in adj matrix corresponding to centroid's id
        GraphEntry<StarterNode>[] entries = this.adjMatrix[row];
        Collections.addAll(pq, entries);
        starterToLookerEntries.put(centroid, pq);
      }

      int numMatchedLookers = 0;
      Map<StarterNode, List<LookerNode>> grouping = new HashMap<>();
      // PART 2
      while (!this.allEventsAtCapacity() || numMatchedLookers < this.numLookers) {

        for (StarterNode event : centroids) {
          if (!this.eventAtCapacity(event)) {

            PriorityQueue<GraphEntry<StarterNode>> pq = starterToLookerEntries.get(event);
            GraphEntry<StarterNode> entry = pq.poll();
            LookerNode looker = entry.getTo();

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

            numMatchedLookers++;

            // make sure that looker can't attend other events
            // REMOVES POSSIBILITY OF THIS LOOKER BEING IN ANY OTHER GROUP
            Collection queues = starterToLookerEntries.values();
            for (PriorityQueue q : queues) {
              q.remove(looker);
            }
          }
        }

      }

      // after all groups are set
      potentialGroupings.add(grouping);
      scatters[iter] = this.calculateScatter(potentialGroupings);
    }

  }


  private double calculateScatter(List<Map<StarterNode, List<LookerNode>>> potentialGroupings) {

    double totalScatter = 0;

    // for a cluster in potentialGroupings


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
    return 0;
  }


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
