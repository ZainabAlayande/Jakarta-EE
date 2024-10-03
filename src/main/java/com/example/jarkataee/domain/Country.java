package com.example.jarkataee.domain;

public class Country {

    private Long id;
    private String name;
    private Long citiesId;
    private String largestCity;


    public Country(String name, Long citiesId, String largestCity) {
        this.name = name;
        this.citiesId = citiesId;
        this.largestCity = largestCity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLargestCity() {
        return largestCity;
    }

    public void setLargestCity(String largestCity) {
        this.largestCity = largestCity;
    }

    public Long getCitiesId() {
        return citiesId;
    }

    public void setCitiesId(Long citiesId) {
        this.citiesId = citiesId;
    }


    @Override
    public String toString() {
        return "City{id='" + id + "', name='" + name + "', citiesId='" + citiesId + "', largestCity='" + largestCity + "'}";
    }

}
