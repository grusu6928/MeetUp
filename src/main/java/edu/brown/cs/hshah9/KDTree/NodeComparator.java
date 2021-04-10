package edu.brown.cs.hshah9.KDTree;

import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;

import java.util.Comparator;


/**
 * For objects that implement the HasCoordinates interface, compare their coordinates
 * on the dimension specified by the caller.
 */
public class NodeComparator implements Comparator<HasCoordinates> {

  private Integer dim;

  /**
   * Instantiates NodeComparator, compares nodes coordinates
   * on passed in dimension.
   * @param dim dimension on which to compare
   */
  public NodeComparator(Integer dim) {
    this.dim = dim;
  }

  @Override
  public int compare(HasCoordinates s1, HasCoordinates s2) {

    if (s1.getCoors()[dim] < s2.getCoors()[dim]) {
      return -1;
    } else if (s1.getCoors()[dim] > s2.getCoors()[dim]) {
      return 1;
    } else {
      return 0;
    }
  }

}
