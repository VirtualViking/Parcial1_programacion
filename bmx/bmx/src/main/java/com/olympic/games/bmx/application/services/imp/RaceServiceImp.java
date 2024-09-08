package com.olympic.games.bmx.application.services.imp;

import com.olympic.games.bmx.application.services.RaceService;
import com.olympic.games.bmx.domain.models.Competitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RaceServiceImp implements RaceService {
    private  Integer ROUND = 1;
    @Override
    public void simulateRace(List<Competitor> competitors) {
        Set<Integer> usedPositions = new HashSet<>();
        competitors.forEach(competitor -> {

            int position = generatePosition(competitors.size());

            while (usedPositions.contains(position)) {
                position =  generatePosition(competitors.size());
            }
            usedPositions.add(position);
            competitor.addScore(ROUND, position);
        });
        ROUND++;
    }


    private int generatePosition(int competitorsSize) {
        return (int) (Math.random() * competitorsSize) + 1;
    }
}
