package edu.brown.cs.hshah9.REPL;

import edu.brown.cs.hshah9.Parser.Parser;
import edu.brown.cs.hshah9.stars.StarsApplication;

import java.io.InputStreamReader;

/**
 * This class represents a REPL.
 */
public class REPL {

  private Parser inputParser;
  private StarsApplication starsApplication;

  /**
   * Runs the REPL.
   */
  public void runREPL() {

    inputParser = new Parser(new InputStreamReader(System.in));
    starsApplication = new StarsApplication();

    while (true) {

      // parses user input
      String[] input = inputParser.parse(starsApplication.getDelim(),
              starsApplication.getParseLimit());

      // EOF condition
      if (input == null) {
        break;
      }

      // runs the Stars application
      starsApplication.run(input);
    }
  }
}
