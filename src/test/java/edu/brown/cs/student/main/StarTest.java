package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import edu.brown.cs.hshah9.stars.Star;

import static org.junit.Assert.*;

public class StarTest {

  private Star star;

  @Before
  public void setUp() {
    List<String> starData = Arrays.asList("1", "Star One", "1", "2", "3");
    star = new Star(starData);
  }

  @After
  public void tearDown() {
    star = null;
  }

  @Test
  public void testStarProperties() {
    setUp();

    assertTrue(star.getId().equals("1"));
    assertTrue(star.getName().equals("Star One"));
    assertTrue(star.getCoors()[0] == 1);
    assertTrue(star.getCoors()[1] == 2);
    assertTrue(star.getCoors()[2] == 3);

    tearDown();
  }

  @Test
  public void testStarDistance() {
    setUp();

    double dist = 10;
    star.setDistance(dist);
    assertTrue(star.getDistance() == dist);

    tearDown();
  }

  // no setUp or teardown (different Star data/Star used)
  @Test
  public void testNullInput() {
    List<String> starData2 = Arrays.asList(null, null, "1", "2", "3");
    Star star2 = new Star(starData2);

    assertTrue(star2.getId() == null);
    assertTrue(star2.getName() == null);
  }

}