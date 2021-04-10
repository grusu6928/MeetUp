package edu.brown.cs.hshah9.main;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import edu.brown.cs.hshah9.Parser.Parser;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.Reader;

import static org.junit.Assert.*;

public class ParserTest {

  private Parser parser;

  @Before
  public void setUp() {

  }

  @After
  public void tearDown() {
    parser = null;
  }

  @Test
  public void testParseString() {
    String inputString = "CS32, is, a, class, that, teaches, software, engineering";
    parser = new Parser(new StringReader(inputString));

    String[] parsedString = parser.parse(", ", -1);

    assertTrue(parsedString.length == 8);
    assertTrue(parsedString[0].equals("CS32"));
    assertTrue(parsedString[1].equals("is"));
    assertTrue(parsedString[2].equals("a"));
    assertTrue(parsedString[3].equals("class"));
    assertTrue(parsedString[4].equals("that"));
    assertTrue(parsedString[5].equals("teaches"));
    assertTrue(parsedString[6].equals("software"));
    assertTrue(parsedString[7].equals("engineering"));

    tearDown();
  }

  @Test
  public void testDelimiterNotFound() {
    String inputString = "AHDBCWHJEHBCJKAXKJ";
    parser = new Parser(new StringReader(inputString));

    String[] parsedString = parser.parse(" ", -1);

    assertTrue(parsedString.length == 1);

    tearDown();
  }
}
