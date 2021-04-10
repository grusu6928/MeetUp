package edu.brown.cs.student.term_project;

import java.util.*;

public class Graph {

  private int numNodes;
  private double[][] adjMatrix;
  private double[][] starterAdjMatrix;
  private GraphNode[] allNodes;
  private ArrayList<StarterNode> centroids; // originally held only starterNodes
  private HashMap<StarterNode, Integer> capacityMap;

  private int numIters = 10;
  private int numLookers;

  public Graph(int numNodes, GraphNode[] allNodes) {
    this.numNodes = numNodes;
    this.adjMatrix = new double[numNodes][numNodes];
    this.starterAdjMatrix = new double[centroids.size()][numNodes];
    this.allNodes = allNodes;

    this.numLookers = this.numNodes - centroids.size();

    this.setCentroids();
    this.setCapacityMap();

  }


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
      for (int c = 0; c < centroids.size(); c++) {
        PriorityQueue<Double> pq = new PriorityQueue<>(new WeightComparator());
        double[] weights = starterAdjMatrix[c];
        for (double w : weights) {
          pq.add(w);
        }
        starterToLookerWeights.put(centroids.get(c), pq);
      }

      while (!allEventsAtCapacity() || numMatchedLookers < this.numLookers) {

        for (int c = 0; c < centroids.size(); c++) {
          StarterNode event = centroids.get(c);
          if (!eventAtCapacity(event)) {

            PriorityQueue<Double> pq = starterToLookerWeights.get(event);
            LookerNode looker = pq.poll(); // PROBLEM

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
   * Populates the overall and starter-subset adjacency matrix.
   */
  private void setEdgeWeights() {

    // computationally faster to create starter-only matrix in same loop

    int starterCounter = 0;

    for (int i = 0; i < numNodes; i++) {

      for (int j = 0; j < numNodes; j++) {
        // if weight is on initial value -> compute a heuristic for it
        if (Double.compare(adjMatrix[i][j], Double.POSITIVE_INFINITY) == 0) {
          double heuristic = computeHeuristic(allNodes[i], allNodes[j]);
          adjMatrix[i][j] = heuristic;
          adjMatrix[j][i] = heuristic;
        }
      }

      // populates starter adjacency matrix
      boolean isStarter = allNodes[i].isStarter();
      if (isStarter) {
        System.arraycopy(adjMatrix[i], 0, starterAdjMatrix[starterCounter], 0, starterAdjMatrix[starterCounter].length);
      }
      starterCounter++;

    }

  }


  /**
   * Sets the weight between a) two starters and b) a node and itself to Negative Infinity.
   */
  private void preProcessAdjMatrix() {
    // initialize all weights to positive infinity
    Arrays.fill(adjMatrix, Double.POSITIVE_INFINITY);

    // negative infinity weights
    for (int i = 0; i < numNodes; i++) {
      for (int j = 0; j < numNodes; j++) {
        if (bothStarters(allNodes[i], allNodes[j]) || atDiagonal(i, j)) {
          adjMatrix[i][j] = Double.NEGATIVE_INFINITY;
        }
      }
    }
  }

  private boolean bothStarters(GraphNode n1, GraphNode n2) {
    return n1.isStarter() && n2.isStarter();
  }

  private boolean atDiagonal(int num1, int num2) {
    return num1 == num2;
  }

  /**
   * Creates the list of centroid nodes.
   */
  private void setCentroids() {
    for (GraphNode n : this.allNodes) {
      if (n.isStarter()) {
        centroids.add((StarterNode) n); // bad code
      }
    }
  }
  // typeOf === starterNode

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
