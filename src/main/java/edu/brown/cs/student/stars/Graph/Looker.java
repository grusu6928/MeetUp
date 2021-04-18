package edu.brown.cs.student.stars.Graph;

/**
 * Stores information related to a looker who submits a form intending to
 * be matched to an event. The looker inputs their preferred activity and location
 * as well as the bounds of their availability.
 */
public class Looker implements FormSubmission {

  private final int id;
  private final String username;
  private final String activity;
  private final String startTime;
  private final String endTime;
  private final double latitude;
  private final double longitude;

  /**
   * Initializes a Looker object - a user who submitted their preferences
   * and is looking to be matched to an event.
   * @param id unique id
   * @param username unique username
   * @param activity activity of interest
   * @param startTime beginning of free time
   * @param endTime end of free time
   * @param latitude latitude of desired location
   * @param longitude longitude of desired location
   */
  public Looker(int id, String username, String activity, String startTime,
                String endTime, double latitude, double longitude) {
    this.id = id;
    this.username = username;
    this.activity = activity;
    this.startTime = startTime;
    this.endTime = endTime;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Returns looker's unique id.
   * @return id
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * Returns looker's unique username.
   * @return username
   */
  @Override
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns looker's desired activity.
   * @return activity
   */
  @Override
  public String getActivity() {
    return this.activity;
  }

  /**
   * Returns beginning of looker's availability.
   * @return beginning of free time
   */
  @Override
  public String getStartTime() {
    return this.startTime;
  }

  /**
   * Returns end of looker's availability.
   * @return end of free time
   */
  @Override
  public String getEndTime() {
    return this.endTime;
  }

  /**
   * Returns looker's desired meet-up location.
   * @return location [latitude, longitude]
   */
  @Override
  public double[] getLocation() {
    return new double[]{this.latitude, this.longitude};
  }


}
