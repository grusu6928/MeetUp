package edu.brown.cs.student.stars;

import java.util.Comparator;

public class WeightComparator implements Comparator<Double> {

  @Override
  public int compare(Double weight1, Double weight2) {
    return weight1.compareTo(weight2);
  }

}

