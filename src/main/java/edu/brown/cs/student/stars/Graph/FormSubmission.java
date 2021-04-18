package edu.brown.cs.student.stars.Graph;

/**
 * Specifies fields common to both Starters and Lookers when they
 * fill out event submission/request forms.
 */
public interface FormSubmission {

  /**
   * Returns unique id of starters/looker who filled out the form.
   * @return unique id
   */
  int getId();

  /**
   * Returns unique username of starter/looker who filled out the form.
   * @return unique username
   */
  String getUsername();

  /**
   * Returns activity inputted on the form.
   * @return activity
   */
  String getActivity();

  /**
   * Returns start time inputted on the form.
   * @return start time
   */
  String getStartTime();

  /**
   * Returns end time inputted on form.
   * @return end time
   */
  String getEndTime();

  /**
   * Returns location inputted on form.
   * @return location
   */
  double[] getLocation();
}