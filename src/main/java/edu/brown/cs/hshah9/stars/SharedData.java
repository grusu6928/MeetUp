package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.KDTree.KDNode;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * This class follows the Singleton pattern and holds three pieces of data
 * shared across the program.
 * 1) stars data in the form of a list
 * 2) stars data in the form of a HashMap of star name to star data (useful for quickly looking up
 * a star's coordinate by its name)
 * 3) root of the KDTree
 */
public final class SharedData {

  private static SharedData sharedData = null;

  private HashMap<String, Star> nameToStar;
  private ArrayList<Star> starsData;
  private KDNode<Star> root;

  SharedData() {
    this.starsData = null; // changed
    this.nameToStar = new HashMap<>(); // changed
    this.root = null;
  }


  /**
   * Gets the instance of the class.
   * @return the class's instance
   */
  public static SharedData getInstance() {
    if (sharedData == null) {
      sharedData = new SharedData();
    }
    return sharedData;
  }

  /**
   * Sets the KDTree root value.
   * @param root root to set
   */
  public void setRoot(KDNode<Star> root) {
    this.root = root;
  }

  /**
   * Gets the KDTree root value.
   * @return root
   */
  public KDNode<Star> getRoot() {
    return this.root;
  }

  /**
   * Sets the star data list.
   * @param data list to set
   */
  public void setStarsData(ArrayList<Star> data) {
    this.starsData = data;
  }

  /**
   * Gets the star data list.
   * @return the list of data
   */
  public ArrayList<Star> getStarsData() {
    return this.starsData;
  }

  /**
   * Sets the star data HashMap.
   * @param data HashMap to set
   */
  public void setNameToStar(ArrayList<Star> data) {
    for (int i = 0; i < data.size(); i++) {
      this.nameToStar.put(data.get(i).getName(), data.get(i));
    }
  }

  /**
   * Gets the star data HashMap.
   * @return star data HashMap
   */
  public HashMap<String, Star> getNameToStar() {
    return this.nameToStar;
  }

}
