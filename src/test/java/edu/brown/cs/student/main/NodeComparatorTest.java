package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import edu.brown.cs.hshah9.KDTree.NodeComparator;
import edu.brown.cs.hshah9.HasCoordinates.HasCoordinates;
import edu.brown.cs.hshah9.stars.Star;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NodeComparatorTest {

  private NodeComparator nComp;
  private HasCoordinates o1;
  private HasCoordinates o2;

  @Before
  public void setUp() {
    int dim = 1;
    nComp = new NodeComparator(dim);

    List<String> star1Data = Arrays.asList("1", "Star One", "10", "11", "12");
    o1 = new Star(star1Data);

    List<String> star2Data = Arrays.asList("2", "Star Two", "9", "13", "2");
    o2 = new Star(star2Data);
  }

  @After
  public void tearDown() {
    nComp = null;
    o1 = null;
    o2 = null;
  }

  @Test
  public void testAccurateComparison() {
    assertTrue(nComp.compare(o1, o2) == -1);
  }

}