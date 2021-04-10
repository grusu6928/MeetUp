package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import edu.brown.cs.hshah9.csvReader.CsvReader;

import static org.junit.Assert.*;


public class CsvReaderTest {

  private CsvReader reader;
  private String inputFile;
  private String properHeader;

  @Before
  public void setUp() {
    properHeader = "StarID,ProperName,X,Y,Z";
    reader = new CsvReader(properHeader);
    inputFile = "data/stars/three-star.csv";

  }

  @After
  public void tearDown() {
    reader = null;
    inputFile = null;
    properHeader = null;

  }

  @Test
  public void testDataReadIn() {
    setUp();
    ArrayList<List<String>> data = reader.readInData(inputFile);

    // row 1
    assertTrue(data.get(0).get(0).equals("1"));
    assertTrue(data.get(0).get(1).equals("Star One"));
    assertTrue(data.get(0).get(2).equals("1"));
    assertTrue(data.get(0).get(3).equals("0"));
    assertTrue(data.get(0).get(4).equals("0"));

    // row 2
    assertTrue(data.get(1).get(0).equals("2"));
    assertTrue(data.get(1).get(1).equals("Star Two"));
    assertTrue(data.get(1).get(2).equals("2"));
    assertTrue(data.get(1).get(3).equals("0"));
    assertTrue(data.get(1).get(4).equals("0"));

    // row 3
    assertTrue(data.get(2).get(0).equals("3"));
    assertTrue(data.get(2).get(1).equals("Star Three"));
    assertTrue(data.get(2).get(2).equals("3"));
    assertTrue(data.get(2).get(3).equals("0"));
    assertTrue(data.get(2).get(4).equals("0"));

    tearDown();
  }
}