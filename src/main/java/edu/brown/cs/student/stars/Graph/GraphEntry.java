package edu.brown.cs.student.stars.Graph;

/**
 * Represents an entry in the graph. Each entry holds a weight
 * for the edge between the from and to nodes of the entry.
 * @param <T>
 */
public class GraphEntry <T extends Event> {

  private T from;
  private Looker to;
  private double weight;

  /**
   * Instantiates an entry in the graph.
   * @param from GraphNode from which the edge comes
   * @param to LookerNode to which the edge goes
   * @param weight weight on the edge
   */
  public GraphEntry(T from, Looker to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  /**
   * Returns an edge's weight, which is, as set in the Graph class,
   * a linear combination of four similarity metrics between two GraphNodes.
   * @return edge weight
   */
  public double getWeight() {
    return this.weight;
  }

  /**
   * Returns the LookerNode to which an edge points.
   * @return LookerNode to which an edge points
   */
  public Looker getTo() {
    return this.to;
  }
}
