package com.olympic.games.bmx.application.services;

import com.olympic.games.bmx.domain.models.Competitor;

import java.util.List;

public interface RaceService {
    void simulateRace(List<Competitor> competitors);
}
