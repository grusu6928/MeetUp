package edu.brown.cs.hshah9.KDTree;

import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents a KDTree, which is comprised of KDNodes whose values are objects
 * implementing the HasCoordinates interface.
 * @param <T> object that implements HasCoordinates interface
 */
public final class KDTree<T extends HasCoordinates> {

  private KDNode<T> root;
  private int currDim;
//  private int totalDims;


  /**
   * Instantiates KDTree from a list.
   * @param data list from which to make tree
   * @param totalDims dimensions of tree
   * @param startingDim first dimension to sort by
   */
  public KDTree(ArrayList<T> data, int totalDims, int startingDim) {
//    this.totalDims = totalDims;
    this.currDim = startingDim;
    Collections.sort(data, new NodeComparator(currDim)); // sort by 1st dimension -> X
    root = this.addNode(data, currDim, totalDims); // first recursive call
  }

  /**
   * Adds a node to the KDTree. This is a recursive function. Note that in the case of equality,
   * it is not specified whether a node will be added to the left or right subtree.
   * @param data list of nodes from which to select the node to add
   * @param dim current iteration's dimension of coordinate comparison
   * @param totalDims total number of dimensions the KDNode objects have
   * @return added node
   */
  public KDNode<T> addNode(List<T> data, Integer dim, Integer totalDims) {

    // base case (out of data)
    if (data.size() == 0) {
      return null;
    }

    // new node is in middle of sorted list
    int mid = data.size() / 2;
    KDNode<T> node = new KDNode(data.get(mid), null, null);

    // increment dimension before recursion
    dim = (dim + 1) % totalDims;

    // create left and right sublists
    List<T> leftSublist = data.subList(0, mid);
    List<T> rightSublist = data.subList(mid + 1, data.size());

    // sort sublists
    Collections.sort(leftSublist, new NodeComparator(dim));
    Collections.sort(rightSublist, new NodeComparator(dim));

    // recursive calls
    node.setLeftChild(this.addNode(leftSublist, dim, totalDims));
    node.setRightChild(this.addNode(rightSublist, dim, totalDims));

    return node;
  }

  /**
   * Gets the root of the KDTree, which is useful for traversals.
   * @return root of tree
   */
  public KDNode<T> getRoot() {
    return root;
  }
}
