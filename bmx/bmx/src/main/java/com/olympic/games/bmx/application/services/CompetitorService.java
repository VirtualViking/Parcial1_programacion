package com.olympic.games.bmx.application.services;

import com.olympic.games.bmx.application.searchcriteria.CompetitorSearchCriteria;
import com.olympic.games.bmx.application.searchcriteria.Sorting;
import com.olympic.games.bmx.domain.models.Competitor;

import java.util.List;
import java.util.Optional;

public interface CompetitorService {
  void addCompetitor(Competitor competitor);
  List<Competitor> getCompetitors();
  List<Competitor> getCompetitors(Sorting sorting, CompetitorSearchCriteria competitorSearchCriteria);
  Optional<Competitor> getCompetitorById(Long id);
  void deleteCompetitor(Long id);
}
