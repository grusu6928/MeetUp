package edu.brown.cs.hshah9.csvReader;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;



/**
 * This class reads in a CSV file specified by the user and outputs it as a list of lists.
 */
public class CsvReader {

  private String properHeader;
  private String readInHeader;

  /**
   * Instantiates a CSVReader, caller specifies properHeading.
   * @param properHeader proper heading
   */
  public CsvReader(String properHeader) {
    this.properHeader = properHeader;
  }

  /**
   * Overloaded constructor for Mock csv, since there's no
   * specific valid header.
   */
  public CsvReader() { }


  /**
   * Reads in a caller-specified file and returns the file as a list of lists of strings, in which
   * each line of the csv is its own list.
   * @param inputFile the caller-specified file
   * @return data represented as list of lists of strings
   */
  public ArrayList<List<String>> readInData(String inputFile) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(inputFile));
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: file not found");
    }

    Stream<String> stream = in.lines();

    // collecting CSV data into ArrayList (one line = one element)
    List<String> rawEntries = stream.collect(ArrayList::new, ArrayList::add,
            ArrayList::addAll);

    this.readInHeader = rawEntries.get(0);

    // take CSV data out of List -> parse row by row (each row into a List) + add to new ArrayList
    ArrayList<List<String>> data = new ArrayList<>();
    for (int i = 1; i < rawEntries.size(); i++) { // starting from 1 to get rid of header
      List<String> entry = Arrays.asList(rawEntries.get(i).split(",", -1));
      data.add(entry);
    }

    return data;

  }

  /**
   * Verifies that the desired header specified by the caller
   * matches the header read in from the CSV file.
   * @return true if they match; false o.w.
   */
  public boolean correctHeader() {
    return this.readInHeader.equals(this.properHeader);
  }
}
