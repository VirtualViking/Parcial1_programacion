package com.olympic.games.bmx.domain.models;

import com.olympic.games.bmx.domain.enums.Country;
import com.olympic.games.bmx.domain.exceptions.InvalidInputException;
import com.olympic.games.bmx.domain.utils.constants.CompetitorConstant;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class Competitor {
    private Long id;
    private String firstName;
    private String lastName;
    private Country country;
    private LocalDate birthDate;
    private Map<Integer,Integer> scores;
    private Integer totalScore;
    private String description;
    private static Long idCounter = 0L;

    public Competitor(String firstName, String lastName, Country country, LocalDate birthDate, String description) {
        this.id = idCounter++;
        setFirstName(firstName);
        setLastName(lastName);
        setCountry(country);
        setBirthDate(birthDate);
        this.scores = new HashMap<>();
        this.totalScore = 0;
        setDescription(description);
    }

    public void setFirstName(String firstName) {
        if(firstName.length() < 2 || firstName.length() > 40){
            throw new InvalidInputException(CompetitorConstant.FIRST_NAME_CHARACTER_LIMIT_EXCEPTION_MESSAGE);
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if(lastName.length() < 2 || lastName.length() > 40){
            throw new InvalidInputException(CompetitorConstant.LAST_NAME_CHARACTER_LIMIT_EXCEPTION_MESSAGE);
        }
        this.lastName = lastName;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setBirthDate(LocalDate birthDate) {
        if(Period.between(birthDate, LocalDate.now()).getYears() < CompetitorConstant.MIN_AGE){
            throw new InvalidInputException(CompetitorConstant.MIN_AGE_EXCEPTION_MESSAGE);
        }
        this.birthDate = birthDate;
    }

    public void setScores(Map<Integer, Integer> scores) {
        this.scores = scores;
        this.totalScore = scores.values().stream().mapToInt(Integer::intValue).sum();
    }
    public void addScore(Integer round, Integer score){
        this.scores.put(round, score);
        this.totalScore += score;
        System.out.println(this.scores.toString());
        System.out.println("Competitor " + this.firstName + " " + this.lastName + " has scored " + score + " in round " + round + " total score: " + this.totalScore);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Country getCountry() {
        return country;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public Integer getTotalScore() {
        return totalScore;
    }
    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }
}
