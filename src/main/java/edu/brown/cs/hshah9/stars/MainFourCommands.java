package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This is a superclass for NaiveNeighborsCommand, NaiveRadiusCommand, NeighborsCommand,
 * and RadiusCommand. It holds functionality shared by all four subclasses
 * (e.g. error checking, calculating the distance from a given star to a
 * point of interest, etc.
 */
public class MainFourCommands {

  // valid commands only have 2 or 4 arguments
  private ArrayList<Integer> numPossibleArgs = new ArrayList<Integer>(Arrays.asList(2, 4));

  private String errorMessage;

  /**
   * Instantiates the superclass.
   */
  public MainFourCommands() {
  }



  /**
   * Gets the error that occurred (if an error occurred).
   * @return the error
   */
  public String getErrorMessage() {
    return this.errorMessage;
  }



  /**
   * Error checks the caller's input and if data has been loaded before the caller calls a command.
   * Makes calls to helper functions: numNeighOrRadiusErrorCheck, nameVersionErrorCheck,
   * corVersionErrorCheck.
   * @param input the caller's input
   * @param commandType 0 specifies naive/optimized neighbors call;
   *                    1 specifies naive/optimized radius call
   * @return true if there's an error; false o.w.
   */
  public boolean errorCheck(String[] input, int commandType) {

    if (input == null) {
      this.errorMessage = "ERROR: input is null";
      System.out.println(this.errorMessage);
      return true;
    }

    ArrayList<Star> data = SharedData.getInstance().getStarsData();

    if (data == null) {
      this.errorMessage = "ERROR: no csv file loaded";
      System.out.println(this.errorMessage);
      return true;
    }

    Integer numArgs = input.length - 1;
    boolean nameVersion = numArgs == 2;
    boolean corVersion = numArgs == 4;

    if (!numPossibleArgs.contains(numArgs)) {
      this.errorMessage = "ERROR: incorrect number of arguments";
      System.out.println(this.errorMessage);
      return true;
    }

    // HELPER: type checks and non-negative checks radius and numNeighbors
    if (this.numNeighOrRadiusErrorCheck(commandType, input[1])) {
      return true;
    }

    // HERLPER: more specific checks for name or coordinate version
    if (nameVersion) {
      return nameVersionErrorCheck(input);
    } else if (corVersion) {
      return corVersionErrorCheck(input);
    }

    return false;
  }

  /**
   * Type checks and non-negative checks the number of neighbors / the radius
   * specifed by the caller.
   * @param commandType 0: neighbors; 1: radius
   * @param value the 1st argument of the user's input
   * @return true if error; false o.w.
   */
  private boolean numNeighOrRadiusErrorCheck(int commandType, String value) {
    boolean neighborsCommand = commandType == 0;
    boolean radiusCommand = commandType == 1;

    if (neighborsCommand) {
      try {
        int numNeighbors = Integer.parseInt(value);
        if (numNeighbors < 0) {
          this.errorMessage = "ERROR: number of neighbors to find must be non-negative";
          System.out.println(this.errorMessage);
          return true;
        }
      } catch (NumberFormatException e) {
        this.errorMessage = "ERROR: Improperly formatted number of neighbors to find inputted";
        System.out.println(this.errorMessage);
        return true;
      }
    } else if (radiusCommand) {
      try {
        double radius = Double.parseDouble(value);
        if (Double.compare(radius, 0) < 0) {
          this.errorMessage = "ERROR: radius must be non-negative";
          System.out.println(this.errorMessage);
          return true;
        }
      } catch (NumberFormatException e) {
        this.errorMessage = "ERROR: Improperly formatted radius inputted";
        System.out.println(this.errorMessage);
        return true;
      }
    }

    return false;
  }


  private boolean nameVersionErrorCheck(String[] input) {

    String starName = input[2];
    char firstChar = starName.charAt(0);
    char lastChar = starName.charAt(starName.length() - 1);

    if (!(firstChar == '"') || !(lastChar == '"')) {
      this.errorMessage = "ERROR: Star name must be enclosed in quotation marks";
      System.out.println(this.errorMessage);
      return true;
    }

    starName = starName.substring(1, starName.length() - 1);

    if (!starExists(starName) || starName.equals("")) {
      this.errorMessage = "ERROR: No such star exists in the loaded file";
      System.out.println(this.errorMessage);
      return true;
    }

    return false;
  }


  private boolean corVersionErrorCheck(String[] input) {

    String xCor = input[2];
    String yCor = input[3];
    String zCor = input[4];

    try {
      Double.parseDouble(xCor);
      Double.parseDouble(yCor);
      Double.parseDouble(zCor);
    } catch (NumberFormatException e) {
      this.errorMessage = "ERROR: Improperly formatted coordinate(s) inputted";
      System.out.println(this.errorMessage);
      return true;
    }

    return false;
  }


  /**
   * Checks if starName is in the SharedData HashMap (efficient check).
   * @param starName starName to look for
   * @return true if exists; false otherwise
   */
  public boolean starExists(String starName) {
    HashMap<String, Star> nameToStar = SharedData.getInstance().getNameToStar();
    return nameToStar.keySet().contains(starName);
  }

  /**
   * Returns the coordinates of a star after quick HashMap lookup.
   * @param starName starName to look for
   * @return double array of star's coordinates
   */
  public double[] getCoordinates(String starName) {
    HashMap<String, Star> nameToStar = SharedData.getInstance().getNameToStar();
    return nameToStar.get(starName).getCoors();
  }

  /**
   * Determines the coordinates of the caller-specified point of interest. Handles both when
   * a star name is specified and, more trivially, when the coordinates are provided.
   * @param input user input
   * @param nameVersion true: nameVersion; false: corVersion
   * @return array of doubles holding point of interest
   */
  public double[] determinePOI(String[] input, boolean nameVersion) {
    double[] coors;
    if (nameVersion) {
      // remove quotes around name
      String starName = input[2].substring(1, input[2].length() - 1);
      coors = this.getCoordinates(starName);
    } else {
      coors = new double[3];
      coors[0] = Double.parseDouble(input[2]);
      coors[1] = Double.parseDouble(input[3]);
      coors[2] = Double.parseDouble(input[4]);
    }
    return coors;
  }



  /**
   * Calculates Euclidean distance between point of interest and a star.
   * @param poi coordinates of point of interest
   * @param star star from which to calc distance to poi
   * @return Euclidean distance from star to poi
   */
  public double calcDistance(double[] poi, Star star) {
    Double xPOI = poi[0];
    Double yPOI = poi[1];
    Double zPOI = poi[2];
    Double starX = star.getCoors()[0];
    Double starY = star.getCoors()[1];
    Double starZ = star.getCoors()[2];

    return Math.sqrt(Math.pow(xPOI - starX, 2) + Math.pow(yPOI - starY, 2)
            + Math.pow(zPOI - starZ, 2));
  }




  /**
   * Sorts a list by the comparator specified by the caller. The comparator can only compare
   * objects that implement the HasCoordinates interface.
   * @param list list to sort
   * @param comparator comparator by which to sort
   * @return sorted list of stars
   */
  public ArrayList<Star> sortList(ArrayList<Star> list, Comparator<HasCoordinates> comparator) {

    Collections.sort(list, comparator); // sort stars from closest to furthest
    return list;
  }


  /**
   * Prints out the IDs of the stars in a list.
   * @param list list of stars from which to print IDs
   */
  public void printIds(ArrayList<Star> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).getId());
    }
  }



}
