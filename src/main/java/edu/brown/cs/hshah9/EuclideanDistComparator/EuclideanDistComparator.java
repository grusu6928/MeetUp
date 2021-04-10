package edu.brown.cs.hshah9.EuclideanDistComparator;

import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;

import java.util.Comparator;

/**
 * For objects that implement the HasCoordinates interface, compares the Euclidean distance
 * of objects to a specific interest point.
 */
public class EuclideanDistComparator implements Comparator<HasCoordinates> {

  private int sortOrder;


  /**
   * Instantiates comparator that can sort based on ascending and descending order.
   * @param sortOrder 1 is ascending; 0 is descending
   */
  public EuclideanDistComparator(int sortOrder) {
    this.sortOrder = sortOrder;
  }


  @Override
  public int compare(HasCoordinates s1, HasCoordinates s2) {

    if (s1.getDistance() < s2.getDistance()) {
      return -1 * sortOrder;
    } else if (s1.getDistance() > s2.getDistance()) {
      return 1 * sortOrder;
    } else {
      return 0;
    }
  }
}
