package edu.brown.cs.hshah9.HasCoordinates;

/**
 * This interface binds implementing classes to implement methods for getting their coordinates and
 * their distance from an interest point.
 */
public interface HasCoordinates {
  /**
   * Gets array of coordinates.
   * @return double array of coordinates
   */
  double[] getCoors();

  /**
   * Gets distance from point of interest.
   * @return distance.
   */
  Double getDistance();
}
