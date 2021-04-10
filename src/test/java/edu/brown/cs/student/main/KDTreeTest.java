package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import edu.brown.cs.hshah9.csvReader.CsvReader;
import edu.brown.cs.hshah9.stars.Star;
import edu.brown.cs.hshah9.KDTree.KDTree;
import edu.brown.cs.hshah9.KDTree.KDNode;
import edu.brown.cs.hshah9.KDTree.NodeComparator;


import static org.junit.Assert.*;

public class KDTreeTest {

  private CsvReader csvReader;
  private ArrayList<List<String>> data;
  private ArrayList<Star> stars;


  @Before
  public void setUp() {

    String properHeader = "StarID,ProperName,X,Y,Z";
    csvReader = new CsvReader(properHeader);
    data = csvReader.readInData("data/stars/ten-star.csv");
    stars = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      stars.add(new Star((data.get(i))));
    }

  }

  @After
  public void tearDown() {
    csvReader = null;
    data = null;
    stars = null;
  }


  @Test
  public void testBuild10NodeTree() {
    setUp();

    int totalDims = 3;
    int dim = 0;


    KDTree<Star> tree = new KDTree<Star>(stars, totalDims, dim);
    KDNode<Star> root = tree.getRoot();


    // root
    assertTrue(root.getValue().getId().equals("0"));


    // DEPTH 1
    assertTrue(root.getLeft().getValue().getId().equals("71454")); //
    assertTrue(root.getRight().getValue().getId().equals("3")); ///

    // DEPTH 2
    assertTrue(root.getLeft().getLeft().getValue().getId().equals("87666")); //
    assertTrue(root.getLeft().getRight().getValue().getId().equals("118721")); //
    assertTrue(root.getRight().getLeft().getValue().getId().equals("1")); ///
    assertTrue(root.getRight().getRight().getValue().getId().equals("3759")); ///


    // DEPTH 3

    // 87666 children
    assertTrue(root.getLeft().getLeft().getLeft().getValue().getId().equals("71457")); //
    assertTrue(root.getLeft().getLeft().getRight() == null); // null

    // 118721 children
    assertTrue(root.getLeft().getRight().getLeft().getValue().getId().equals("70667")); //
    assertTrue(root.getLeft().getRight().getRight() == null); // null

    // 1 children
    assertTrue(root.getRight().getLeft().getLeft().getValue().getId().equals("2")); ///
    assertTrue(root.getRight().getLeft().getRight() == null); /// null

    // 3759 children
    assertTrue(root.getRight().getRight().getLeft() == null); /// null
    assertTrue(root.getRight().getRight().getRight() == null); /// null

    tearDown();
  }

}