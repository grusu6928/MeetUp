package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;

import java.util.List;



/**
 * This class represents a Star and its attributes.
 */
public class Star implements HasCoordinates {

  private String id;
  private String name;

  private double[] coordinates;
  private Double distance;

  private int numDimensions = 3;


  /**
   * Instantiates star object given data.
   * @param starData data from which to create Star
   */
  public Star(List<String> starData) {
    this.id = starData.get(0);
    this.name = starData.get(1);
    this.coordinates = new double[numDimensions];

    for (int c = 0; c < numDimensions; c++) {
      this.coordinates[c] = Double.parseDouble(starData.get(c + 2));
    }
  }

  /**
   * Sets a star's distance.
   * @param dist distance to set
   */
  public void setDistance(Double dist) {
    this.distance = dist;
  }

  /**
   * Gets a star's distance.
   * @return distance
   */
  public Double getDistance() {
    return this.distance;
  }

  /**
   * Gets a star's ID.
   * @return star id
   */
  public String getId() {
    return this.id;
  }

  /**
   * Gets a star's name.
   * @return star name
   */
  public String getName() {
    return this.name;
  }


  /**
   * Gets a star's coordinates.
   * @return double array of coordinates
   */
  @Override
  public double[] getCoors() {
    return this.coordinates;
  }



}
