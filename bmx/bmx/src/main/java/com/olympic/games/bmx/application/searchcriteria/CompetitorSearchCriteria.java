package com.olympic.games.bmx.application.searchcriteria;

import com.olympic.games.bmx.domain.enums.Country;

public class CompetitorSearchCriteria {
  private String firstName;
  private String lastName;
  private Country country;

  public CompetitorSearchCriteria(String firstName, String lastName, Country country) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }
}
