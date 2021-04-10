package edu.brown.cs.hshah9.main;

import java.util.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import edu.brown.cs.hshah9.csvReader.CsvReader;
import edu.brown.cs.hshah9.KDTree.KDTree;
import edu.brown.cs.hshah9.KDTree.KDNode;
import edu.brown.cs.hshah9.stars.Star;
import edu.brown.cs.hshah9.stars.SharedData;
import edu.brown.cs.hshah9.stars.NaiveNeighborsCommand;

import static org.junit.Assert.*;

public class NaiveNeighborsTest {

  private NaiveNeighborsCommand naiveNeighborsCommand;
  private CsvReader csvReader;
  private ArrayList<List<String>> data;
  private ArrayList<Star> stars;

  @Before
  public void setUp() {

    naiveNeighborsCommand = new NaiveNeighborsCommand();

    String properHeader = "StarID,ProperName,X,Y,Z";
    csvReader = new CsvReader(properHeader);
    data = csvReader.readInData("data/stars/ten-star.csv");
    stars = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      stars.add(new Star((data.get(i))));
    }

    SharedData.getInstance().setStarsData(stars);
    SharedData.getInstance().setNameToStar(stars);

  }

  @After
  public void tearDown() {
    naiveNeighborsCommand = null;
    csvReader = null;
    data = null;
    stars = null;
  }

//  @Test
//  public void testGetStarsOfInterest() {
//    setUp();
//
//    double thresholdDist = 20.0;
//    String starName = "FAKE NAME";
//    boolean nameVersion = false;
//    System.out.println("HERE ");
//    System.out.println(stars == null);
//    ArrayList<Star> retList = naiveNeighborsCommand.getStarsOfInterest(stars, thresholdDist, starName, nameVersion);
//
//    assertTrue(retList.size() <= stars.size());
//
//    tearDown();
//  }

  @Test
  public void testChooseRandomly() {
    setUp();

    List<String> starOneData = Arrays.asList("1", "Star One", "5.0", "4.0", "6.0");
    List<String> starTwoData = Arrays.asList("2", "Star Two", "5.0", "4.0", "6.0");
    List<String> starThreeData = Arrays.asList("3", "Star Three", "5.0", "4.0", "6.0");
    List<String> starFourData = Arrays.asList("4", "Star Four", "5.0", "4.0", "6.0");

    Star star1 = new Star(starOneData);
    Star star2 = new Star(starTwoData);
    Star star3 = new Star(starThreeData);
    Star star4 = new Star(starFourData);

    // size of 4
    ArrayList<Star> equalitySet = new ArrayList<>();
    equalitySet.add(star1);
    equalitySet.add(star2);
    equalitySet.add(star3);
    equalitySet.add(star4);

    ArrayList<Star> forSureSet = stars;

    Random r = new Random();

    int counter = 0;
    while (counter < 2) {
      counter++;

      Integer min = 0;
      Integer max = stars.size() - 1;
      Integer rand = r.nextInt((max - min) + 1) + min;
      forSureSet.remove(rand);
    }

    int forSureSetSize = forSureSet.size();

    int numToSelect = 2;

    ArrayList<Star> finalSet = naiveNeighborsCommand.chooseRandomly(equalitySet, numToSelect, forSureSet);

    assertTrue(finalSet.size() == forSureSetSize + numToSelect);

    tearDown();

  }

}