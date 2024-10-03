package com.example.jarkataee.domain;

import java.util.List;

public class City {

    private Long id;

    private List<String> cities;

    public City(Long id, List<String> cities) {
        this.id = id;
        this.cities = cities;
    }


    @Override
    public String toString() {
        return "City{id='" + id + "', cities='" + cities + "'}";
    }

}
