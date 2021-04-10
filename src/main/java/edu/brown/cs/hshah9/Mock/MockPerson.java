package edu.brown.cs.hshah9.Mock;

import java.util.List;

/**
 * This class represents a MockPerson.
 */
public class MockPerson {
  private String firstName;
  private String lastName;
  private String dob;
  private String email;
  private String gender;
  private String streetAddress;
  private String eyeColor;

  /**
   * Instantiates a MockPerson.
   * @param mockData list of csv file data
   */
  public MockPerson(List<String> mockData)  {
    this.firstName = mockData.get(0);
    this.lastName = mockData.get(1);
    this.dob = mockData.get(2);
    this.email = mockData.get(3);
    this.gender = mockData.get(4);
    this.streetAddress = mockData.get(5);
    this.eyeColor = mockData.get(6);


    // constraints
    if (this.firstName == null || this.lastName == null) {
      throw new NullPointerException("Names cannot be null");
    }
    if (!this.email.contains("@")) {
      System.out.println("Email must have @ sign");
    }
  }

  /**
   * Prints out a MockPerson's attributes.
   * @param person mockPerson
   */
  public void toString(MockPerson person) {
    System.out.println("First Name: " + person.firstName + ", "
            + "Last Name: " + person.lastName + ", "
            + "DOB: " + person.dob + ", "
            + "Email: " + person.email + ", "
            + "Gender: " + person.gender + ", "
            + "Street Address: " + person.streetAddress + ", "
            + "Eye Color: " + person.eyeColor);
  }
}

