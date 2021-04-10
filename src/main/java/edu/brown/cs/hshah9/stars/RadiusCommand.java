package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.CommandManager.CommandManager;
import edu.brown.cs.hshah9.EuclideanDistComparator.EuclideanDistComparator;
import edu.brown.cs.hshah9.KDTree.KDNode;

import java.util.ArrayList;

/**
 * This class holds functionality for the Radius command. It's a subclass of MainFourCommands.
 * A KDTree is used to find all stars within a specified
 * radius from a point of interest.
 */
public class RadiusCommand extends MainFourCommands implements CommandManager {

  // store stars in list
  private ArrayList<Star> list;

  /**
   * Instantiates radius command.
   */
  public RadiusCommand() { }

  /**
   * Point of execution for the command. First makes call to errorCheck
   * before processing user's input and calling necessary private methods
   * to execute the command.
   * @param input caller's input
   * @return -1 on error; 0 o.w.
   */
  @Override
  public int execute(String[] input) {

    // commandType: 1 -> radius command
    if (!super.errorCheck(input, 1)) {

      ArrayList<Star> data = SharedData.getInstance().getStarsData();

      Integer numArgs = input.length - 1;
      boolean nameVersion = (numArgs == 2);
      double r = Double.parseDouble(input[1]);

      String starName = null;
      // removes quotes
      if (nameVersion) {
        starName = input[2].substring(1, input[2].length() - 1);
      }

      // get POI
      double[] poi = super.determinePOI(input, nameVersion);

      Integer totalDims = poi.length;
      Integer staringDim = 0;

      KDNode<Star> root = SharedData.getInstance().getRoot();
      list = new ArrayList<>();

      // call to run radius algo
      this.runAlgorithm(root, poi, r, totalDims, staringDim, nameVersion, starName);

      // sort + print list
      super.printIds(sortList(list, new EuclideanDistComparator(1)));

      return 0;
    }

    return -1;
  }

  /**
   * Returns the final, sorted list of star IDs.
   * @return list of sorted IDs
   */
  public ArrayList<Star> getFinalList() {
    return this.list;
  }

  /**
   * Recusively searches KDTree to find all stars within a specified radius
   * from a point of interest.
   * @param node search (parent) node
   * @param poi point of interest coordinates
   * @param r search radius
   * @param totalDims total dimensions of KDTree
   * @param dim current search dimension
   * @param nameVersion true: nameVersion; false: corVersion
   * @param starName if nameVersion -> name of specified star; o.w. null
   */
  private void runAlgorithm(KDNode<Star> node, double[] poi, double r,
                            Integer totalDims, Integer dim,
                            boolean nameVersion, String starName) {

    if (node == null) {
      return;
    }

    Integer axis = dim % totalDims;

    Star currStar = node.getValue();
    currStar.setDistance(super.calcDistance(poi, currStar));

    // if dist <= radius + NOT(name version & trying to add current star) - > add to list
    if (Double.compare(currStar.getDistance(), r) <= 0) {
      if (!(nameVersion && currStar.getName().equals(starName))) {
        list.add(currStar);
      }
    }

    Double corRelAxis = currStar.getCoors()[axis];
    Double poiRelAxis = poi[axis];
    Double distTargetToCurr = Math.abs(corRelAxis - poiRelAxis);

    // inc dim before recurring
    dim++;


    // if nodes of interest could be on either side -> recur on both sides
    // ; o.w. just on relevant side
    // special cases: pq empty, any equality case -> recur on both sides
    // tree doesn't specify direction in cases of equality
    if (Double.compare(r, distTargetToCurr) >= 0) {
      this.runAlgorithm(node.getLeft(), poi, r, totalDims, dim, nameVersion, starName);
      this.runAlgorithm(node.getRight(), poi, r, totalDims, dim, nameVersion, starName);
    } else {
      if (corRelAxis.compareTo(poiRelAxis) < 0) {
        this.runAlgorithm(node.getRight(), poi, r, totalDims, dim, nameVersion, starName);
      } else if (corRelAxis.compareTo(poiRelAxis) > 0) {
        this.runAlgorithm(node.getLeft(), poi, r, totalDims, dim, nameVersion, starName);
      } else {
        this.runAlgorithm(node.getLeft(), poi, r, totalDims, dim, nameVersion, starName);
        this.runAlgorithm(node.getRight(), poi, r, totalDims, dim, nameVersion, starName);
      }
    }
  }

}


