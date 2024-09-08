package com.olympic.games.bmx.domain.enums;

public enum Country {
  COLOMBIA("Colombia", "COL"),
  FRANCE("France", "FRA"),
  UNITED_STATES("United States", "USA"),
  SPAIN("Spain", "ESP"),
  BRAZIL("Brazil", "BRA"),
  CANADA("Canada", "CAN"),
  GERMANY("Germany", "GER"),
  AUSTRALIA("Australia", "AUS"),
  ITALY("Italy", "ITA"),
  JAPAN("Japan", "JPN"),
  NETHERLANDS("Netherlands", "NLD"),
  SWITZERLAND("Switzerland", "CHE"),
  GREAT_BRITAIN("Great Britain", "GBR"),
  RUSSIA("Russia", "RUS"),
  ARGENTINA("Argentina", "ARG"),
  SWEDEN("Sweden", "SWE"),
  DENMARK("Denmark", "DNK"),
  BELGIUM("Belgium", "BEL"),
  CZECH_REPUBLIC("Czech Republic", "CZE"),
  CHILE("Chile", "CHL"),
  MEXICO("Mexico", "MEX"),
  NEW_ZEALAND("New Zealand", "NZL"),;

    private final String name;
    private final String code;

    Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
