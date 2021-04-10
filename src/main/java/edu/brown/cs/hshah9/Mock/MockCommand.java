package edu.brown.cs.hshah9.Mock;

import edu.brown.cs.hshah9.csvReader.CsvReader;
import edu.brown.cs.hshah9.CommandManager.CommandManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class takes care of functionality for the MockCommand.
 */
public class MockCommand implements CommandManager {

  private ArrayList<Integer> numActualArgs = new ArrayList<Integer>(Arrays.asList(1));
  private ArrayList<MockPerson> mockData = new ArrayList<MockPerson>();


  private boolean errorCheck(String[] input) {

    if (!numActualArgs.contains(input.length - 1)) {
      System.out.println("ERROR: incorrect number of arguments");
      return true;
    }

    // checks if file exists
    try {
      String fileName = input[1];
      BufferedReader in = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return true;
    }
    return false;
  }

  /**
   * Executes the mock command to load the CSV and instantiates MockPeople,
   * which is where their attrbites are printed.
   * @param input user input
   * @return -1 on error; 0 o.w.
   */
  public int execute(String[] input)  {

    if (!this.errorCheck(input)) {
      String inputFile = input[1];
      CsvReader reader = new CsvReader();
      ArrayList<List<String>> data = reader.readInData(inputFile);

      for (int i = 0; i < data.size(); i++) {
        MockPerson person = new MockPerson(data.get(i));
        person.toString(person);
      }

      return 0;
    }
    return -1;
  }
}
