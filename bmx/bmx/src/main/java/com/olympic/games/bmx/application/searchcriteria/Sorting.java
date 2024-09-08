package com.olympic.games.bmx.application.searchcriteria;

public class Sorting {
    private final String field;
    private final String order;

    public Sorting(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public String getOrder() {
        return order;
    }
}
