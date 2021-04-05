package edu.brown.cs.student.term_project;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

  private int numNodes;
  private double[][] adjMatrix;
  private GraphNode[] allNodes;
  private ArrayList<StarterNode> centroids; // originally held only starterNodes
  private HashMap<StarterNode, Integer> capacityMap;

  public Graph(int numNodes, GraphNode[] allNodes) {
    this.numNodes = numNodes;
    this.adjMatrix = new double[numNodes][numNodes];
    this.allNodes = allNodes;

    this.setCentroids();
    this.setCapacityMap();
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
