package com.olympic.games.bmx.domain.utils.constants;

import java.util.Set;

public class CompetitorConstant {
  public static final String FIRST_NAME_CHARACTER_LIMIT_EXCEPTION_MESSAGE = "The first name must be between 2 and 40 characters long";
  public static final String LAST_NAME_CHARACTER_LIMIT_EXCEPTION_MESSAGE = "The last name must be between 2 and 40 characters long";
  public static final String MIN_AGE_EXCEPTION_MESSAGE = "The competitor must be at least 18 years old";
  public static final Integer MIN_AGE = 18;
  public static final Set<String> VALID_SORTING_FIELDS = Set.of("firstName", "lastName", "country", "birthDate", "totalScore");
  public static final String INVALID_SORTING_FIELD_EXCEPTION_MESSAGE = "Invalid sorting field provided available fields are: firstName, lastName, country, birthDate, totalScore";
  private CompetitorConstant() {
  }
}
