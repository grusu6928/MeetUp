package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.CommandManager.CommandManager;
import edu.brown.cs.hshah9.EuclideanDistComparator.EuclideanDistComparator;

import java.util.ArrayList;

/**
 * This class holds functionality for the NaiveRadius command. It's a subclass of MainFourCommands.
 */
public class NaiveRadiusCommand extends MainFourCommands implements CommandManager {

  private ArrayList<Star> finalList = new ArrayList<>();

  /**
   * Instantiates naive radius command.
   */
  public NaiveRadiusCommand() { }


  /**
   * Point of execution for the command.
   * First makes call to errorCheck before processing user's input
   * and calling necessary private methods to execute the command.
   * @param input caller's input
   * @return -1 on error; 0 o.w.
   */
  @Override
  public int execute(String[] input) {

    // commandType: 1 -> radius command
    if (!super.errorCheck(input, 1)) {

      ArrayList<Star> data = SharedData.getInstance().getStarsData();

      Integer numArgs = input.length - 1;
      double r = Double.parseDouble(input[1]);
      boolean nameVersion = (numArgs == 2);

      // for each point: calculating Euclidean dist from POI
      double[] poi = super.determinePOI(input, nameVersion);
      for (int i = 0; i < data.size(); i++) {
        Star currStar = data.get(i);
        Double dist = super.calcDistance(poi, currStar);
        currStar.setDistance(dist); // set Star's distance
      }

      // sorting the data
      super.sortList(data, new EuclideanDistComparator(1));

      // if name Version -> getting starName
      String starName;
      if (nameVersion) {
        starName = input[2].substring(1, input[2].length() - 1);
      } else {
        starName = null;
      }

      // call to get list of stars to print
      ArrayList<Star> printList = this.getPrintList(r, data, nameVersion, starName);

      super.printIds(printList);
      this.finalList = printList;

      return 0;
    }

    return -1;
  }

  /**
   * Returns the final, sorted list of star IDs.
   * @return list of sorted IDs
   */
  public ArrayList<Star> getFinalList() {
    return this.finalList;
  }



  /**
   * Adds stars, with distance within specified radius,
   * to print list from a passed in list that's already sorted by distance from poi.
   * @param r radius of interest
   * @param data list of stars sorted by distance
   * @param nameVersion true: nameVersion; false: corVersion
   * @param starName if nameVersion -> star's name; o.w. null
   * @return final list of stars to print
   */
  private ArrayList<Star> getPrintList(double r, ArrayList<Star> data,
                                       boolean nameVersion, String starName) {

    ArrayList<Star> printList = new ArrayList<>();

    for (int i = 0; i < data.size(); i++) {
      Star currStar = data.get(i);

      // if star out of radius -> condition to break out of loop and stop printing
      if (Double.compare(currStar.getDistance(), r) > 0) {
        break;
      }
      // add to print list UNLESS (nameVersion + we're visiting the inputted name)
      if (!(nameVersion && currStar.getName().equals(starName))) {
        printList.add(currStar);
      }
    }
    return printList;
  }

}

