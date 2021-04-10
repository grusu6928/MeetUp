package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import edu.brown.cs.hshah9.stars.StarsCommand;
import edu.brown.cs.hshah9.stars.SharedData;

import static org.junit.Assert.*;

public class StarsCommandTest {

  private StarsCommand starsCommand;

  @Before
  public void setUp() {
    starsCommand = new StarsCommand();
  }

  @After
  public void tearDown() {
    starsCommand = null;

  }

  @Test
  public void testNullInput() {
    setUp();

    String[] input = null;

    assertTrue(starsCommand.execute(input) == -1);

    tearDown();
  }

  @Test
  public void testEmptyInput() {
    setUp();

    String[] input = new String[0];

    assertTrue(starsCommand.execute(input) == -1);

    tearDown();
  }

  @Test
  public void testIncorrectNumArgs() {
    setUp();

    String[] input = new String[] {"stars", "data/stars/stardata.csv", "extra argument"};

    assertTrue(starsCommand.execute(input) == -1);

    tearDown();
  }

  @Test
  public void testInvalidFileName() {
    setUp();

    String[] input = new String[] {"stars", "data/stars/dne.csv"};

    assertTrue(starsCommand.execute(input) == -1);

    tearDown();
  }

  @Test
  public void testCorrectDataReadIn() {
    setUp();

    String[] input = new String[] {"stars", "data/stars/stardata.csv"};

    assertTrue(starsCommand.execute(input) == 0);
    assertTrue(SharedData.getInstance().getStarsData().size() == 119617);

    tearDown();

  }


}
