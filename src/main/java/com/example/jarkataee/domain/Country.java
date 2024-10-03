package com.example.jarkataee.domain;

import java.util.List;

public class Country {

    private String name;
    private List<String> cities;

    private String largestCity;

    public Country(String name, List<String> cities, String largestCity) {
        this.name = name;
        this.cities = cities;
        this.largestCity = largestCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getLargestCity() {
        return largestCity;
    }

    public void setLargestCity(String largestCity) {
        this.largestCity = largestCity;
    }
}
