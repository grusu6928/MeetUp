package edu.brown.cs.student.stars;


import java.util.Comparator;

public class WeightComparator implements Comparator<GraphEntry> {


  @Override
  public int compare(GraphEntry e1, GraphEntry e2) {
    return Double.compare(e1.getWeight(), e2.getWeight());
  }
}

