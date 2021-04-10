package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import edu.brown.cs.hshah9.stars.Star;
import edu.brown.cs.hshah9.stars.NaiveRadiusCommand;
import edu.brown.cs.hshah9.stars.RadiusCommand;
import edu.brown.cs.hshah9.stars.SharedData;
import edu.brown.cs.hshah9.KDTree.KDTree;
import edu.brown.cs.hshah9.KDTree.KDNode;

import static org.junit.Assert.*;


public class PBRadiusTest {

  private static final int MIN_DATA_SIZE = 0;
  private static final int MAX_DATA_SIZE = 5000;

  private static final double MIN_COR = -1000000.0;
  private static final double MAX_COR = 1000000.0;

  private static final int MIN_ID = 0;
  private static final int MAX_ID = 2000000;

  private static final double MIN_RADIUS = 0.0;
  private static final double MAX_RADIUS = 75000.0;

  private HashSet<Integer> intSet = new HashSet<>();
  private HashSet<String> stringSet = new HashSet<>();


  //  // radius queries
  private String[] rRandomArgs;
  private String[] radiusQuery;
  private String[] naiveRadiusQuery;

  // radius commands
  private RadiusCommand radiusCommand;
  private NaiveRadiusCommand naiveRadiusCommand;



  @Before
  public void setUp() {
    // Singleton method set data to random Data
    ArrayList<Star> data = this.generateRandomData();
    SharedData.getInstance().setStarsData(data);
    SharedData.getInstance().setNameToStar(data);
    KDTree<Star> tree = new KDTree<>(data, 3, 0);
    KDNode root = tree.getRoot();
    SharedData.getInstance().setRoot(root);


    // random queries for radius commands
    rRandomArgs = this.generateRadiusArgs();



    radiusQuery = this.generateRandomQuery("radius", rRandomArgs);
    naiveRadiusQuery = this.generateRandomQuery("naive_radius", rRandomArgs);


    // radius command
    radiusCommand = new RadiusCommand();
    radiusCommand.execute(this.radiusQuery);
    naiveRadiusCommand = new NaiveRadiusCommand();
    naiveRadiusCommand.execute(this.naiveRadiusQuery);


  }

  @After
  public void tearDown() {
    intSet = null;
    stringSet = null;


    // radius queries
    rRandomArgs = null;
    radiusQuery = null;
    naiveRadiusQuery = null;

    // radius commands
    radiusCommand = null;
    naiveRadiusCommand = null;
  }





  @Test
  public void pbt() {
    setUp();


//    // RADIUS

    ArrayList<Star> listFromRadius = radiusCommand.getFinalList();
    ArrayList<Star> listFromNaiveRadius = naiveRadiusCommand.getFinalList();

    assertTrue(listFromRadius.size() == listFromNaiveRadius.size());


    for (int i = 0; i < listFromNaiveRadius.size(); i++) {
      assertTrue(Double.compare(listFromRadius.get(i).getDistance(), listFromNaiveRadius.get(i).getDistance()) == 0);
    }

    tearDown();
  }




  public ArrayList<Star> generateRandomData() {
    int len = ThreadLocalRandom.current().nextInt(MIN_DATA_SIZE, MAX_DATA_SIZE);

    ArrayList<Star> randomData = new ArrayList<>(len);

    for (int i = 0; i < len; i++) {
      randomData.add(this.generateRandomStar());
    }

    return randomData;
  }

  public String[] generateRandomQuery(String command, String[] args) {
    int numArgs = args.length;

    String[] query = new String[numArgs + 1];
    query[0] = command;

    for (int i = 1; i < query.length; i++) {
      query[i] = args[i - 1];
    }

    return query;
  }


  public String[] generateRadiusArgs() {


    int numArgs;
    if (Math.random() < 0.5) {
      numArgs = 2;
    } else {
      numArgs = 4;
    }

    String[] args = new String[numArgs];

    // depending on command type, generate k or r

//    int k = this.generateRandomInt();
//    args[0] = String.valueOf(k);

    double r = this.generateRandomDouble(MIN_RADIUS, MAX_RADIUS);
    args[0] = String.valueOf(r);



    if (numArgs == 2) {
      String name = "";
      int setSize = this.stringSet.size();
      int index = ThreadLocalRandom.current().nextInt(0, setSize);
      int counter = 0;
      // gets random string from set of names
      for (String s : this.stringSet) {
        if (counter == index) {
          name = s;
          break;
        }
        counter++;
      }

      args[1] = '"' + name + '"';
    } else if (numArgs == 4) {
      double x = this.generateRandomDouble(MIN_COR, MAX_COR);
      double y = this.generateRandomDouble(MIN_COR, MAX_COR);
      double z = this.generateRandomDouble(MIN_COR, MAX_COR);

      args[1] = String.valueOf(x);
      args[2] = String.valueOf(y);
      args[3] = String.valueOf(z);
    }

    return args;
  }



  public Star generateRandomStar() {

    int id;
    do {
      id = this.generateRandomInt();
    } while (!intSet.contains(id));

    int stringLen = ThreadLocalRandom.current().nextInt(0, 20);
    String name;
    do {
      name = this.generateRandomName(stringLen);
    } while (!stringSet.contains(name));

    double x = this.generateRandomDouble(MIN_COR, MAX_COR);
    double y = this.generateRandomDouble(MIN_COR, MAX_COR);
    double z = this.generateRandomDouble(MIN_COR, MAX_COR);

    List<String> data = Arrays.asList(String.valueOf(id), name, String.valueOf(x), String.valueOf(y), String.valueOf(z));

    return new Star(data);

  }



  public double generateRandomDouble(double min, double max) {
    return ThreadLocalRandom.current().nextDouble(min, max);

  }


  public int generateRandomInt() {
    int id = ThreadLocalRandom.current().nextInt(MIN_ID, MAX_ID);
    intSet.add(id);
    return id;
  }

  public String generateRandomName(int len) {
    String randomString = "";
    String allPossibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";


    for (int i = 0; i < len; i++) {
      int index = ThreadLocalRandom.current().nextInt(0, allPossibleValues.length());
      randomString = randomString + allPossibleValues.charAt(index);
    }

    stringSet.add(randomString);
    return randomString;


  }


}
