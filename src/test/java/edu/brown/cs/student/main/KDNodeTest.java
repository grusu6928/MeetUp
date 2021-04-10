package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import edu.brown.cs.hshah9.KDTree.KDNode;
import edu.brown.cs.hshah9.stars.Star;

import static org.junit.Assert.*;

public class KDNodeTest {

  private KDNode<Star> node;
  private List<String> nodeStarProperties;
  private List<String> leftChildProperties;
  private List<String> rightChildProperties;

  private Star nodeStar;
  private Star leftChildStar;
  private Star rightChildStar;

  private KDNode<Star> leftChild;
  private KDNode<Star> rightChild;


  @Before
  public void setUp() {
    nodeStarProperties = Arrays.asList("1", "Star1", "4", "5", "6");
    leftChildProperties = Arrays.asList("2", "Star2", "1", "2", "3");
    rightChildProperties = Arrays.asList("3", "Star3", "7", "8", "9");

    nodeStar = new Star(nodeStarProperties);
    leftChildStar = new Star(leftChildProperties);
    rightChildStar = new Star(rightChildProperties);

    leftChild = new KDNode<Star>(leftChildStar, null, null);
    rightChild = new KDNode<Star>(rightChildStar, null, null);

    node = new KDNode<Star>(nodeStar, leftChild, rightChild);
  }

  @After
  public void tearDown() {
    node = null;
    nodeStarProperties = null;
    leftChildProperties = null;
    rightChildProperties = null;

    nodeStar = null;
    leftChildStar = null;
    rightChildStar = null;

    leftChild = null;
    rightChild = null;
  }

  @Test
  public void testGetNodeValue() {
    setUp();

    Star val = node.getValue();

    assertTrue(val.equals(this.nodeStar));

    tearDown();
  }

  @Test
  public void testGetChildren() {
    setUp();

    KDNode<Star> left = node.getLeft();
    assertTrue(left.equals(this.leftChild));
    KDNode<Star> right = node.getRight();
    assertTrue(right.equals(this.rightChild));

    tearDown();
  }

  @Test
  public void testSetChildren() {
    setUp();

    assertTrue(leftChild.getLeft() == null);
    List<String> newStarProperties = Arrays.asList("4", "Star4", "20", "30", "40");
    leftChild.setLeftChild(new KDNode<Star>(new Star(newStarProperties), null, null));
    assertFalse(leftChild.getLeft() == null);


    assertTrue(leftChild.getRight() == null);
    List<String> newStarProperties2 = Arrays.asList("5", "Star5", "50", "60", "70");
    leftChild.setRightChild(new KDNode<Star>(new Star(newStarProperties2), null, null));
    assertFalse(leftChild.getRight() == null);

    tearDown();
  }

}