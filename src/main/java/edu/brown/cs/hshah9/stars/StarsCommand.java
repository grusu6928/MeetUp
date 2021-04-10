package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.CommandManager.CommandManager;
import edu.brown.cs.hshah9.KDTree.KDNode;
import edu.brown.cs.hshah9.KDTree.KDTree;
import edu.brown.cs.hshah9.csvReader.CsvReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class holds functionality for the "stars" command, which loads in CSV files.
 */
public class StarsCommand implements CommandManager {

  private ArrayList<Integer> numActualArgs = new ArrayList<Integer>(Arrays.asList(1));
  private ArrayList<Star> stars = new ArrayList<>();

  private String csvHeader = "StarID,ProperName,X,Y,Z";
  private String errorMessage;


  /**
   * Instantiates stars command.
   */
  public StarsCommand() { }


  /**
   * Error checks the star command.
   * @param input user input
   * @return True: error; False: no error
   */
  private boolean errorCheck(String[] input) {

    if (input == null) {
      this.errorMessage = "ERROR: input is null";
      System.out.println(this.errorMessage);
      return true;
    }

    // checks # of args
    if (!numActualArgs.contains(input.length - 1)) {
      this.errorMessage = "ERROR: incorrect number of arguments";
      System.out.println(this.errorMessage);
      return true;
    }

    // checks if file exists
    try {
      new BufferedReader(new FileReader(input[1])); // input1 is the file name
    } catch (FileNotFoundException e) {
      this.errorMessage = "ERROR: file does not exist";
      System.out.println(this.errorMessage);
      return true;
    }
    return false;
  }

  /**
   * Executes the stars command to load in a CSV, first making a call to errorCheck.
   * @param input caller input
   * @return -1 on error; 0 o.w.
   */
  @Override
  public int execute(String[] input) {

    if (!errorCheck(input)) {

      String inputFile = input[1];
      CsvReader reader = new CsvReader(csvHeader);
      ArrayList<List<String>> data = reader.readInData(inputFile);

      // if CSV properly formatted -> continue as usual
      // else -> print error
      if (reader.correctHeader()) {
        System.out.println("Read " + data.size() + " stars from " + inputFile);

        for (int i = 0; i < data.size(); i++) {
          stars.add(new Star((data.get(i))));
        }
        SharedData.getInstance().setStarsData(stars); // set List of stars data!
        SharedData.getInstance().setNameToStar(stars); // set HashMap of stars data

        KDTree<Star> tree = new KDTree<>(stars, 3, 0);
        KDNode<Star> root = tree.getRoot();
        SharedData.getInstance().setRoot(root);

      } else {
        System.out.println("ERROR: malformed CSV file");
      }

      return 0;
    }
    return -1;
  }
}
