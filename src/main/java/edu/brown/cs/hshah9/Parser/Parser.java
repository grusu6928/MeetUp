package edu.brown.cs.hshah9.Parser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


/**
 * This class parses a string input based on a ReaderType specific by the caller.
 */
public class Parser {

  private BufferedReader in;

  /**
   * Insyantiates a parser given a reader Type.
   * @param readerType specified reader type
   * @param <T> generic for reader type
   */
  public <T> Parser(T readerType) {
    in = new BufferedReader((Reader) readerType);
  }

  /**
   * Parses a string acquired from the specified readerType as an array.
   * @param delim what to delimit the string on
   * @param limit controls # times the parsing pattern is applied
   *              (non-positive signifies as many times as possible)
   * @return array of strings, each element being separated by the delimiter in the original string
   */
  public String[] parse(String delim, Integer limit) {
    String input = "";
    try {
      input = in.readLine();
    } catch (IOException e) {
      System.out.println("ERROR: I/O error");
    }

    // EOF condition
    if (input == null) {
      return null;
    }

    return input.split(delim, limit);
  }
}
