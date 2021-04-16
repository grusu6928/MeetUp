package edu.brown.cs.student.stars;

public class GraphEntry <T extends GraphNode> {

  private T from;
  private LookerNode to;



  private double weight;

  public GraphEntry(T from, LookerNode to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public double getWeight() {
    return this.weight;
  }

  public LookerNode getTo() {
    return this.to;
  }
}
