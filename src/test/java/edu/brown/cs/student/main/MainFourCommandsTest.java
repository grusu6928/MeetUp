package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import edu.brown.cs.hshah9.csvReader.CsvReader;
import edu.brown.cs.hshah9.KDTree.KDTree;
import edu.brown.cs.hshah9.KDTree.KDNode;
import edu.brown.cs.hshah9.stars.Star;
import edu.brown.cs.hshah9.stars.SharedData;
import edu.brown.cs.hshah9.stars.MainFourCommands;
import edu.brown.cs.hshah9.EuclideanDistComparator.EuclideanDistComparator;

import static org.junit.Assert.*;


public class MainFourCommandsTest {

  private MainFourCommands mainFourCommands;
  private CsvReader csvReader;
  private ArrayList<List<String>> data;
  private ArrayList<Star> stars;

  @Before
  public void setUp() {

    mainFourCommands = new MainFourCommands();

    String properHeader = "StarID,ProperName,X,Y,Z";
    csvReader = new CsvReader(properHeader);
    data = csvReader.readInData("data/stars/ten-star.csv");
    stars = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      stars.add(new Star((data.get(i))));
    }

    SharedData.getInstance().setStarsData(stars);
    SharedData.getInstance().setNameToStar(stars);

    int totalDims = 3;
    int dim = 0;
    KDTree<Star> tree = new KDTree<>(stars, totalDims, dim);
    KDNode<Star> root = tree.getRoot();
    SharedData.getInstance().setRoot(root);

  }

  @After
  public void tearDown() {
    mainFourCommands = null;
    csvReader = null;
    data = null;
    stars = null;
  }

  @Test
  public void testNullInput() {
    setUp();

    String[] input = null;
    assertTrue(mainFourCommands.errorCheck(input, 0));
    assertTrue(mainFourCommands.getErrorMessage().equals("ERROR: input is null"));

    tearDown();
  }

  @Test
  public void testStarExists() {
    setUp();

    String realStarName = "Sol";
    String fakeStarName = "Bob";

    assertTrue(mainFourCommands.starExists(realStarName));
    assertFalse(mainFourCommands.starExists(fakeStarName));

    tearDown();
  }

  @Test
  public void testGetCoordinates() {
    setUp();

    String starName = "Proxima Centauri";
    double[] coors = mainFourCommands.getCoordinates(starName);

    assertTrue(Double.compare(coors[0], -0.47175) == 0);
    assertTrue(Double.compare(coors[1], -0.36132) == 0);
    assertTrue(Double.compare(coors[2], -1.15037) == 0);

    tearDown();
  }

  @Test
  public void testDeterminePOINameVersion() {
    setUp();

    boolean nameVersion = true;
    String[] input = new String[] {"radius", "5.0", "\"Sol\""};
    double[] coors = mainFourCommands.determinePOI(input, nameVersion);

    assertTrue(coors[0] == 0);
    assertTrue(coors[1] == 0);
    assertTrue(coors[2] == 0);

    tearDown();
  }

  @Test
  public void testDeterminePOICorVersion() {
    setUp();

    boolean nameVersion = false;
    String[] input = new String[] {"radius", "5.0", "10.3", "11.6", "-190.9"};
    double[] coors = mainFourCommands.determinePOI(input, nameVersion);

    assertTrue(Double.compare(coors[0], 10.3) == 0);
    assertTrue(Double.compare(coors[1], 11.6) == 0);
    assertTrue(Double.compare(coors[2], -190.9) == 0);

    tearDown();
  }

  @Test
  public void testCalcDistance() {
    setUp();

    double[] poi = new double[] {1.0, 2.0, 3.0};
    List<String> starData = Arrays.asList("1", "Star One", "5.0", "4.0", "6.0");
    Star star = new Star(starData);

    double dist = mainFourCommands.calcDistance(poi, star);

    assertTrue(Double.compare(dist, Math.sqrt(29)) == 0);

    tearDown();
  }

  @Test
  public void testSortList() {
    setUp();

    List<String> starOneData = Arrays.asList("1", "Star One", "5.0", "4.0", "6.0");
    List<String> starTwoData = Arrays.asList("2", "Star Two", "5.0", "4.0", "6.0");
    List<String> starThreeData = Arrays.asList("3", "Star Three", "5.0", "4.0", "6.0");
    List<String> starFourData = Arrays.asList("4", "Star Four", "5.0", "4.0", "6.0");

    Star star1 = new Star(starOneData);
    Star star2 = new Star(starTwoData);
    Star star3 = new Star(starThreeData);
    Star star4 = new Star(starFourData);

    star1.setDistance(10.0);
    star2.setDistance(8.0);
    star3.setDistance(25.0);
    star4.setDistance(11.0);

    ArrayList<Star> starList = new ArrayList<>();
    starList.add(star1);
    starList.add(star2);
    starList.add(star3);
    starList.add(star4);

    ArrayList<Star> preSortedList = new ArrayList<>();

    for (int i = 0; i < starList.size(); i++) {
      preSortedList.add(starList.get(i));
    }

    mainFourCommands.sortList(starList, new EuclideanDistComparator(1));

    assertTrue(starList.get(0).getDistance() == preSortedList.get(1).getDistance());
    assertTrue(starList.get(1).getDistance() == preSortedList.get(0).getDistance());
    assertTrue(starList.get(2).getDistance() == preSortedList.get(3).getDistance());
    assertTrue(starList.get(3).getDistance() == preSortedList.get(2).getDistance());

    tearDown();

  }

}