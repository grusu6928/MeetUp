package edu.brown.cs.student.stars.Graph;


public interface Event {

  int getId();
  String getUsername();
  String getActivity();
  String getStartTime();
  String getEndTime();
  double[] getLocation();
}