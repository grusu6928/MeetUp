package edu.brown.cs.hshah9.KDTree;

import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;

/**
 * This class represents a KDNode, which is the comprising unit of a KDTree. A KDNode can hold
 * any object that implements the HasCoordinates interface.
 * @param <T> object that implements HasCoordinates interface
 */
public class KDNode<T extends HasCoordinates> {


  private T value;
  private KDNode<T> left;
  private KDNode<T> right;

  /**
   * Instantiates a KDNode.
   * @param value object that implements HasCoordinates
   * @param left left child
   * @param right right child
   */
  public KDNode(T value, KDNode<T> left, KDNode<T> right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  /**
   * Sets the node's left child.
   * @param leftChild left child
   */
  public void setLeftChild(KDNode<T> leftChild) {
    this.left = leftChild;
  }

  /**
   * Sets the node's right child.
   * @param rightChild right child
   */
  public void setRightChild(KDNode<T> rightChild) {
    this.right = rightChild;
  }

  /**
   * Gets the node's left child.
   * @return left child
   */
  public KDNode<T> getLeft() {
    return this.left;
  }

  /**
   * Gets the node's right child.
   * @return right child
   */
  public KDNode<T> getRight() {
    return this.right;
  }

  /**
   * Gets the value stored at the node.
   * @return value
   */
  public T getValue() {
    return this.value;
  }


}
