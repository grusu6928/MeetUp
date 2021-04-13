package edu.brown.cs.student.stars;

public class GraphEntry <T extends GraphNode> {

  private T from;
  private LookerNode to;

  // in adjacency matrix
//  private int row;
//  private int col;

  private double weight;

  public GraphEntry(T from, LookerNode to, int row, int col, double weight) {
    this.from = from;
    this.to = to;
//    this.row = row;
//    this.col = col;
    this.weight = weight;
  }

  public double getWeight() {
    return this.weight;
  }

  public LookerNode getTo() {
    return this.to;
  }
}
