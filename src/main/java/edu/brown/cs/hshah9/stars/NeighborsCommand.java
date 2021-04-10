package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.CommandManager.CommandManager;
import edu.brown.cs.hshah9.EuclideanDistComparator.EuclideanDistComparator;
import edu.brown.cs.hshah9.KDTree.KDNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * This class holds functionality for the Neighbors command.
 * It's a subclass of MainFourCommands. A KDTree
 * is used to find the k nearest neighbors.
 */
public class NeighborsCommand extends MainFourCommands implements CommandManager {

  // store stars in PQ
  private PriorityQueue<Star> pq;
  private ArrayList<Star> finalList = new ArrayList<>();

  /**
   * Instantiates neighbors command.
   */
  public NeighborsCommand() { }

  /**
   * Point of execution for the command. First makes call to
   * errorCheck before processing user's input
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
      boolean nameVersion = (numArgs == 2);
      Integer k = Integer.parseInt(input[1]);

      // if name version -> starName is the star's name (o.w. null)
      String starName = null;
      // removes quotes
      if (nameVersion) {
        starName = input[2].substring(1, input[2].length() - 1);
      }

      // get POI
      double[] poi = super.determinePOI(input, nameVersion);
      Integer totalDims = poi.length;
      Integer startingDim = 0;

      // get root of KD tree
      KDNode<Star> root = SharedData.getInstance().getRoot();

      ArrayList<Star> printList = new ArrayList<>();
      // if looking for non-0 number of neighbors
      if (k > 0) {
        // pq to hold list of neighbors
        // sortOrder: -1 -> furthest to closest (descending order)
        pq = new PriorityQueue<>(k, new EuclideanDistComparator(-1));

        // call to find k nearest neighbors
        this.runAlgorithm(root, poi, k, totalDims, startingDim, nameVersion, starName);

        // converts pq to list + sorts list + prints ids
        ArrayList<Star> list = this.pqToList(pq);
        printList = super.sortList(list, new EuclideanDistComparator(1));
        super.printIds(printList);
      }
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


  private ArrayList<Star> pqToList(PriorityQueue<Star> pQueue) {
    ArrayList<Star> list = new ArrayList<>();
    Iterator<Star> value = pQueue.iterator();

    while (value.hasNext()) {
      Star star = value.next();
      list.add(star);
    }
    return list;
  }


  private void addToPQ(Star currStar, double[] poi, Integer k) {
    currStar.setDistance(super.calcDistance(poi, currStar));

    // adding / removing + adding to PQ
    // pq.peek() will always get furthest neighbor
    if (pq.size() < k) {
      pq.add(currStar);
    } else if (currStar.getDistance().compareTo(pq.peek().getDistance()) < 0) {
      pq.poll();
      pq.add(currStar);
    } else if (currStar.getDistance().compareTo(pq.peek().getDistance()) == 0) {
      Random r = new Random();
      Integer max = 1;
      Integer min  = 0;
      Integer rand = r.nextInt((max - min) + 1) + min;

      // 50% of the time swap a star out
      if (rand == 0) {
        pq.poll();
        pq.add(currStar);
      }
    }

  }


  /**
   * Recursive function to search through KDTree and select the k-nearest neighbors.
   * @param node current search (parent) node
   * @param poi point of interest
   * @param k num neighbors to find
   * @param totalDims num dims of the tree
   * @param dim current dimension to sort by
   * @param nameVersion true: nameVersion; false: corVersion
   * @param starName if nameVersion -> name of specified star; o.w. null
   */
  private void runAlgorithm(KDNode<Star> node, double[] poi,
                           Integer k, Integer totalDims, Integer dim,
                            boolean nameVersion, String starName) {

    // base case
    if (node == null) {
      return;
    }

    Integer axis = dim % totalDims;

    // calculate + save a star's distance from POI
    Star currStar = node.getValue();

    // i.e. if it's name version -> don't add the star itself (o.w. add!)
    if (!(nameVersion && currStar.getName().equals(starName))) {
      this.addToPQ(currStar, poi, k);
    }

    Double distTargetToFurthNeigh = 0.0; // Random initial initialization
    if (pq.size() != 0) {
      distTargetToFurthNeigh = super.calcDistance(poi, pq.peek());
    }

    Double corRelAxis = currStar.getCoors()[axis];
    Double poiRelAxis = poi[axis];
    Double distTargetToCurr = Math.abs(corRelAxis - poiRelAxis);

    // any time you recur -> increment dimension
    dim++;

    // if nodes of interest could be on either side -> recur on both sides
    // ; o.w. just on relevant side
    // special cases: pq empty, any equality case -> recur on both sides
    // tree doesn't specify direction in cases of equality
    if (distTargetToFurthNeigh.compareTo(distTargetToCurr) >= 0 || pq.size() == 0) {
      this.runAlgorithm(node.getLeft(), poi, k, totalDims, dim, nameVersion, starName);
      this.runAlgorithm(node.getRight(), poi, k, totalDims, dim, nameVersion, starName);
    } else {
      if (corRelAxis.compareTo(poiRelAxis) < 0) {
        this.runAlgorithm(node.getRight(), poi, k, totalDims, dim, nameVersion, starName);
      } else if (corRelAxis.compareTo(poiRelAxis) > 0) {
        this.runAlgorithm(node.getLeft(), poi, k, totalDims, dim, nameVersion, starName);
      } else {
        this.runAlgorithm(node.getLeft(), poi, k, totalDims, dim, nameVersion, starName);
        this.runAlgorithm(node.getRight(), poi, k, totalDims, dim, nameVersion, starName);
      }
    }

  }
}
