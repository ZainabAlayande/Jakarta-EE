package com.example.jarkataee.domain;

public class City {

    private String name;
    private int population;
    private String countryName;

    public City(String name, int population, String countryName) {
        this.name = name;
        this.population = population;
        this.countryName = countryName;
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    @Override
    public String toString() {
        return "City{name='" + name + "', population=" + population + ", countryName='" + countryName + "'}";
    }

}
