package com.olympic.games.bmx.application.services.imp;

import com.olympic.games.bmx.application.searchcriteria.CompetitorSearchCriteria;
import com.olympic.games.bmx.application.searchcriteria.Sorting;
import com.olympic.games.bmx.application.services.CompetitorService;
import com.olympic.games.bmx.domain.enums.Country;
import com.olympic.games.bmx.domain.exceptions.InvalidInputException;
import com.olympic.games.bmx.domain.models.Competitor;
import com.olympic.games.bmx.domain.utils.constants.CompetitorConstant;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CompetitorServiceImp implements CompetitorService {
  private  List<Competitor> competitors;
  public CompetitorServiceImp() {
    this.competitors = new ArrayList<>(initializeCompetitors());
  }
  @Override
  public void addCompetitor(Competitor competitor) {
    competitors.add(competitor);
  }

  @Override
  public List<Competitor> getCompetitors() {
    return competitors;
  }

  @Override
  public List<Competitor> getCompetitors(Sorting sorting, CompetitorSearchCriteria competitorSearchCriteria) {
    sorting = setDefaultSorting(sorting);
    return competitors
            .stream()
            .filter(competitor -> matchAllPredicates(competitor, createPredicates(competitorSearchCriteria)))
            .sorted(createComparator(sorting))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<Competitor> getCompetitorById(Long id) {
    return competitors.stream().filter(competitor -> Objects.equals(competitor.getId(), id)).findFirst();
  }

  @Override
  public void deleteCompetitor(Long id) {
    competitors = competitors.stream().filter(competitor -> !Objects.equals(competitor.getId(), id)).collect(Collectors.toList());
  }


  private Sorting setDefaultSorting(Sorting sorting) {
    if (sorting.getField() == null || sorting.getField().isEmpty()){
      return new Sorting("totalScore", "asc");
    }
    return sorting;
  }

  private List<Predicate<Competitor>> createPredicates(CompetitorSearchCriteria competitorSearchCriteria) {
    List<Predicate<Competitor>> predicates = new ArrayList<>();
    if(competitorSearchCriteria == null) return predicates;

    if (competitorSearchCriteria.getFirstName() != null && !competitorSearchCriteria.getFirstName().isEmpty()) {
      predicates.add(competitor -> competitor.getFirstName().toLowerCase().contains(competitorSearchCriteria.getFirstName().toLowerCase()));
    }

    if (competitorSearchCriteria.getLastName() != null && !competitorSearchCriteria.getLastName().isEmpty()) {
      predicates.add(competitor -> competitor.getLastName().toLowerCase().contains(competitorSearchCriteria.getLastName().toLowerCase()));
    }

    if (competitorSearchCriteria.getCountry() != null) {
      predicates.add(competitor -> competitor.getCountry().getName().equals(competitorSearchCriteria.getCountry().getName()));
    }
    return predicates;
  }

  private Boolean matchAllPredicates(Competitor competitor, List<Predicate<Competitor>> predicates) {
    return predicates.stream().allMatch(predicate -> predicate.test(competitor));
  }

  private Comparator<Competitor> createComparator(Sorting sorting) {
    validateSortingFields(sorting);
    Comparator<Competitor> comparator;
    switch (sorting.getField()){
      case "firstName":
        comparator = Comparator.comparing(Competitor::getFirstName);
        break;
      case "lastName":
        comparator = Comparator.comparing(Competitor::getLastName);
        break;
      case "country":
        comparator = Comparator.comparing(competitor -> competitor.getCountry().getName());
        break;
      case "birthDate":
        comparator = Comparator.comparing(Competitor::getBirthDate);
        break;
      default:
        comparator = Comparator.comparing(Competitor::getTotalScore);
    }
    if(sorting.getOrder().equals("desc")){
      comparator = comparator.reversed();
    }
    return comparator;
  }

  private void validateSortingFields(Sorting sorting) {
    if (!CompetitorConstant.VALID_SORTING_FIELDS.contains(sorting.getField())) {
      throw new InvalidInputException(CompetitorConstant.INVALID_SORTING_FIELD_EXCEPTION_MESSAGE);
    }
  }

  private List<Competitor> initializeCompetitors() {
    return List.of(
            new Competitor("John", "Merchan", Country.COLOMBIA, LocalDate.of(1998, 12, 12), "The best BMX rider in the world"),
            new Competitor("Santiago", "Giraldo", Country.CHILE, LocalDate.of(1999, 11, 11), "The best BMX rider in the world"),
            new Competitor("Juan", "Perez", Country.ARGENTINA, LocalDate.of(1997, 10, 10), "The best BMX rider in the world"),
            new Competitor("Pedro", "Gonzalez", Country.MEXICO, LocalDate.of(1996, 9, 9), "The best BMX rider in the world"),
            new Competitor("Maria", "Lopez", Country.SPAIN, LocalDate.of(1995, 8, 8), "The best BMX rider in the world"),
            new Competitor("Laura", "Gomez", Country.COLOMBIA, LocalDate.of(1994, 7, 7), "The best BMX rider in the world"),
            new Competitor("Sofia", "Garcia", Country.ARGENTINA, LocalDate.of(1993, 6, 6), "The best BMX rider in the world"),
            new Competitor("Camila", "Martinez", Country.MEXICO, LocalDate.of(1992, 5, 5), "The best BMX rider in the world"),
            new Competitor("Andres", "Gutierrez", Country.SPAIN, LocalDate.of(1991, 4, 4), "The best BMX rider in the world")
    );
  }
}
