package com.example.jarkataee.dto;

public class CityPopulationData {

    private String city;
    private long population;

    public CityPopulationData(String city, long population) {
        this.city = city;
        this.population = population;
    }

    public String getCity() {
        return city;
    }

    public long getPopulation() {
        return population;
    }

}
