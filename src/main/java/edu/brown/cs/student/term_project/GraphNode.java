package edu.brown.cs.student.term_project;

import java.time.LocalTime;

public interface GraphNode {

  int getId(); // unique
  String getEvent();
  LocalTime getStartTime();
  LocalTime getEndTime();
  boolean isStarter();
}
