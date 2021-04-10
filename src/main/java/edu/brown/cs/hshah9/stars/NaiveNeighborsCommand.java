package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.CommandManager.CommandManager;
import edu.brown.cs.hshah9.EuclideanDistComparator.EuclideanDistComparator;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class holds functionality for the NaiveNeighors command.
 * It's a subclass of MainFourCommands.
 */
public class NaiveNeighborsCommand extends MainFourCommands implements CommandManager {

  private ArrayList<Star> finalList = new ArrayList<>();

  /**
   * Instantiates naive neighbors command.
   */
  public NaiveNeighborsCommand() { }

  /**
   * Point of execution for the command. First makes call to errorCheck
   * before processing user's input.
   * and calling necessary private methods to execute the command.
   * @param input caller's input
   * @return -1 on error; 0 o.w.
   */
  @Override
  public int execute(String[] input) {

    // commandType 0: neighbors command
    if (!super.errorCheck(input, 0)) {

      ArrayList<Star> data = SharedData.getInstance().getStarsData();

      Integer numArgs = input.length - 1;
      Integer k = Integer.parseInt(input[1]);
      boolean nameVersion = (numArgs == 2);

      // for each star -> get dist from POI
      double[] poi = super.determinePOI(input, nameVersion);
      for (int i = 0; i < data.size(); i++) {
        Double dist = super.calcDistance(poi, data.get(i));
        data.get(i).setDistance(dist); // set Star's distance
      }

      // sort data based on Euclidean distance
      super.sortList(data, new EuclideanDistComparator(1));

      String starName;
      if (nameVersion) {
        starName = input[2].substring(1, input[2].length() - 1);
      } else {
        starName = null;
      }

      // HELPER: get list of ids to print
      ArrayList<Star> printList = this.getPrintList(k, data, nameVersion, starName);

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


  // 1 startIndex when nameVersion is true; 0 otherwise
  private ArrayList<Star> getPrintList(Integer k, ArrayList<Star> data,
                                       boolean nameVersion, String starName) {

    ArrayList<Star> printList = new ArrayList<>();


    // only compute if k greater than 0
    if (k > 0) {

      // if user wants more neighbors than exist stars -> whole data is of interest
      // else -> need to pick neighbors selectively
      if (k >= data.size()) {
        // copy data into printList
        for (int i = 0; i < data.size(); i++) {
          printList.add(data.get(i));
        }
        // nameVersion -> remove specified star from printList
        if (nameVersion) {
          Star toRemove = SharedData.getInstance().getNameToStar().get(starName);
          printList.remove(toRemove);
        }
      } else {

        ArrayList<Star> starsOfInterest;

        // if it's nameVersion, go one index up so that the
        // thresholdDist is based upon 1 extra star (since starName won't be added)
        int endIndex;
        if (nameVersion) {
          endIndex = k;
        } else {
          endIndex = k - 1;
        }

        // get kth str's distance
        Double thresholdDist = data.get(endIndex).getDistance();

        // get all stars w/ dist <= threshold
        starsOfInterest = this.getStarsOfInterest(data, thresholdDist, starName, nameVersion);


        // if > k stars of interest -> randomly choose from those equal to get finalList
        // else -> print ids of the k stars of interest
        if (starsOfInterest.size() > k) {
          // indices of equality set
          Integer begin = this.getFirstIndex(starsOfInterest, thresholdDist);
          Integer end = starsOfInterest.size();

          // end is begin for forSureSet
          ArrayList<Star> forSureSet = this.createForSureSet(starsOfInterest, begin);
          ArrayList<Star> equalitySet = this.createEqualitySet(begin, end, starsOfInterest);
          Integer numberToSelect = k - begin;

          // breaks ties and returns size of k list
          printList = this.chooseRandomly(equalitySet, numberToSelect, forSureSet);
        } else {
          printList = starsOfInterest;
        }

      }
    }

    return printList;
  }


  private ArrayList<Star> getStarsOfInterest(ArrayList<Star> data, double thresholdDist,
                                             String starName, boolean nameVersion) {
    ArrayList<Star> starsOfInterest = new ArrayList<>();
    Integer currIndex = 0;
    Star currStar = data.get(currIndex);

    // adds all stars w dist <= threshold to list
    while (Double.compare(currStar.getDistance(), thresholdDist) <= 0) {


      // add name to list of interest
      // (EXCEPT: when nameversion + currStar's name is the specified name)
      if (!(nameVersion && currStar.getName().equals(starName))) {
        starsOfInterest.add(currStar);
      }

      // updates
      currIndex++;
      // break out of while loop, ran out of stars
      if (currIndex >= data.size()) {
        break;
      } else {
        currStar = data.get(currIndex);
      }
    }

    return starsOfInterest;

  }



  /**
   * Randomly chooses from stars with equal distance to POI and returns the final set of stars.
   * @param equalitySet list of stars w/ equal distance
   * @param numberToSelect number of stars from equalitySet to select
   * @param forSureSet list of stars with distance less then the equality distance
   * @return final list of stars to print
   */
  public ArrayList<Star> chooseRandomly(ArrayList<Star> equalitySet,
                                        Integer numberToSelect, ArrayList<Star> forSureSet) {
    Random r = new Random();

    while (numberToSelect > 0) {
      Integer min = 0;
      Integer max = equalitySet.size() - 1;
      Integer rand = r.nextInt((max - min) + 1) + min;

      // add to forSureSet
      forSureSet.add(equalitySet.get(rand));

      // remove from equality set the star just added to forSure
      equalitySet.remove(rand);
      numberToSelect--;
    }
    return forSureSet;
  }



  private ArrayList<Star> createForSureSet(ArrayList<Star> starsOfInterest, Integer end) {
    ArrayList<Star> forSureSet = new ArrayList<>();
    for (int i = 0; i < end; i++) {
      forSureSet.add(starsOfInterest.get(i));
    }
    return forSureSet;
  }


  private ArrayList<Star> createEqualitySet(Integer begin,
                                           Integer end, ArrayList<Star> starsOfInterest) {
    ArrayList<Star> equalitySet = new ArrayList<>();

    for (int i = begin; i < end; i++) {
      equalitySet.add(starsOfInterest.get(i));
    }
    return equalitySet;
  }


  /**
   * Returns first index where a star's distance equals a threshold distance.
   * @param starsOfInterest list of stars
   * @param thresholdDist threshold distance
   * @return first index meeting the condition
   */
  private Integer getFirstIndex(ArrayList<Star> starsOfInterest, Double thresholdDist) {
    for (int i = 0; i < starsOfInterest.size(); i++) {
      if (starsOfInterest.get(i).getDistance().compareTo(thresholdDist) == 0) {
        return i;
      }
    }
    return starsOfInterest.size() - 1; // the return should always happen in the loop
  }
}




