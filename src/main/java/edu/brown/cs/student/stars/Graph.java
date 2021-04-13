package edu.brown.cs.student.stars;

import java.util.*;

public class Graph {

  private double[][] adjMatrix; // used for calculating within cluster point scatter
//  private double[][] starterAdjMatrix;

  private ArrayList<GraphNode> allNodes;
  private ArrayList<LookerNode> lookers;
  private ArrayList<StarterNode> centroids;

  // for both: id -> object
  private Map<Integer, LookerNode> lookersMap;
  private Map<Integer, StarterNode> startersMap;

  // id -> row/col in adjacency matrix
  private Map<Integer, Integer> centroidIdToRow;
  private Map<Integer, Integer> lookerIdToCol;


  private HashMap<StarterNode, Integer> capacityMap; // max capacity at each event

  private int numLookers;
  private int numStarters;
  private int numNodes;

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
//    this.lookersMap = new HashMap<>();
//    this.startersMap = new HashMap<>();
//    this.setStarterLookerMaps(lookers, starters);

    this.lookers = lookers;
    this.centroids = starters;

    // creates list of all nodes (to be used in adjacency matrix initialization)
    // VERIFY: addAll PRESERVES ORDER of lookers and of starters
    this.allNodes = new ArrayList<>();
    this.allNodes.addAll(lookers); // ADDING LOOKERS BEFORE STARTERS MATTERS
    this.allNodes.addAll(starters);
    // n x L adjacency matrix: first L rows -> lookers; second S rows -> starters

    this.centroidIdToRow = new HashMap<>();
    this.lookerIdToCol = new HashMap<>();
    this.setIdToIndInMatrixMaps(lookers, starters);


    this.numNodes = this.allNodes.size();
    this.numLookers = lookers.size();
    this.numStarters = starters.size();

    this.adjMatrix = new double[this.numNodes][this.numLookers];
//    this.starterAdjMatrix = new double[numStarters][numNodes];

    this.setCapacityMap();

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

//  /**
//   * Populates HashMaps, which map lookers' and starters' ids to their respective objects.
//   * @param lookers
//   * @param starters
//   */
//  private void setStarterLookerMaps(ArrayList<LookerNode> lookers, ArrayList<StarterNode> starters) {
//    for (LookerNode l : lookers) {
//      this.lookersMap.put(l.getId(), l);
//    }
//    for (StarterNode s: starters) {
//      this.startersMap.put(s.getId(), s);
//    }
//  }


  private void runAlgorithm() {
    List<Map<StarterNode, List<LookerNode>>> potentialGroupings = new ArrayList<>();
    double[] scatters = new double[numIters];

    for (int iter = 0; iter < numIters; iter++) {
      Collections.shuffle(this.centroids);
      int numMatchedLookers = 0;

      Map<StarterNode, List<LookerNode>> grouping = new HashMap<>();
      Map<StarterNode, PriorityQueue<Double>> starterToLookerWeights = new HashMap<>();

      // for each centroid -> create a priority queue of the weights in the centroid's row in
      // the adjacency matrix
      for (int c = 0; c < this.centroids.size(); c++) {
        PriorityQueue<Double> pq = new PriorityQueue<>(new WeightComparator());

        StarterNode centroid = this.centroids.get(c);
        int row = this.centroidIdToRow.get(centroid.getId()); // gets row in adj matrix corresponding to centroid's id
        double[] weights = this.adjMatrix[row];

        for (double w : weights) {
          pq.add(w);
        }
        starterToLookerWeights.put(centroid, pq);
      }

      // PART 2
      while (!this.allEventsAtCapacity() || numMatchedLookers < this.numLookers) {

        // don't forget to increment numMatchedLookers

        for (int c = 0; c < centroids.size(); c++) {
          StarterNode event = centroids.get(c);
          if (!this.eventAtCapacity(event)) {

            PriorityQueue<Double> pq = starterToLookerWeights.get(event);
            // LookerNode looker = pq.poll(); // PROBLEM

            // add looker to grouping.get(event).getValue()

//            removeLookerFromStarterAdjMatrix(); // REMOVES POSSIBILITY OF THIS LOOKER BEING IN ANY OTHER GROUP
          }
        }

      }

      potentialGroupings.add(grouping);
//      scatters[iter] =
    }

  }


  private void removeLookerFromStarterAdjMatrix(LookerNode looker) {
    // iterate through starterToLookerWeights map and remove looker from each priority queue

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
   * Populates the overall adjacency matrix.
   */
  private void setEdgeWeights() {

    // allNodes is a list of L lookers and then S starters (in that order)

    // produces n x L adjacency matrix
    for (int i = 0; i < this.numNodes; i++) {
      for (int j = 0; j < this.numLookers; j++) {
        // if weight is on initial value -> compute a heuristic for it
        if (Double.compare(adjMatrix[i][j], Double.POSITIVE_INFINITY) == 0) {
          double heuristic = this.computeHeuristic(this.allNodes.get(i), this.allNodes.get(j));
          this.adjMatrix[i][j] = heuristic;
        }
      }
    }

  }

  /**
   * Sets the weight between looker i, looker i to negative infinity.
   */
  private void preProcessAdjMatrix() {
    // initialize all weights to positive infinity
    Arrays.fill(this.adjMatrix, Double.POSITIVE_INFINITY);

    // weight between looker i and looker i = > Negative Infinity
    for (int index = 0; index < this.numLookers; index++) {
      this.adjMatrix[index][index] = Double.NEGATIVE_INFINITY;
    }
  }



  /**
   * Sets the capacity map, which will be used to keep track
   * of how many spots are left in each starter's event.
   */
  private void setCapacityMap() {
    for (StarterNode s: this.centroids) {
      capacityMap.put(s, s.getCapacity());
    }

  }
}
