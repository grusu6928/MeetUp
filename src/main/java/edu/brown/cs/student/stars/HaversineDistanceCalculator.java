package edu.brown.cs.student.stars;

/**
 * This class instantiates a HaversineDistanceCalculator object and
 * is able to compute Haversine Distance between two objects that both
 * implement the HasCoordinates interface.
 */
public class HaversineDistanceCalculator {


  /**
   * Instantiates a HaversineDistanceCalculator object.
   */
  public HaversineDistanceCalculator() { }

  /**
   * Calculates the Haversine Distance between two objects that implement
   * the HasCoordinates interface.
   * @return haversine distance between origin and destination
   */
  public double calcHaversineDistance(double[] coors1, double[] coors2) {

    double originLat = Math.toRadians(coors1[0]);
    double originLong = Math.toRadians(coors1[1]);
    double destLat = Math.toRadians(coors2[0]);
    double destLong = Math.toRadians(coors2[1]);

    int earthRadius = 6371000;

    return 2 * earthRadius * Math.asin(Math.sqrt(this.computeHavAngle(originLat, destLat)
            + Math.cos(originLat) * Math.cos(destLat)
            * this.computeHavAngle(originLong, destLong)));
  }

  private double computeHavAngle(double coor1, double coor2) {
    return Math.pow(Math.sin((coor2 - coor1) / 2), 2);
  }
}
