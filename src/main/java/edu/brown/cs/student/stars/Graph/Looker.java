package edu.brown.cs.student.stars.Graph;

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
   * @param activity
   * @param startTime
   * @param endTime
   * @param latitude
   * @param longitude
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


  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getActivity() {
    return this.activity;
  }

  @Override
  public String getStartTime() {
    return this.startTime;
  }

  @Override
  public String getEndTime() {
    return this.endTime;
  }

  @Override
  public double[] getLocation() {
    return new double[]{this.latitude, this.longitude};
  }


}
