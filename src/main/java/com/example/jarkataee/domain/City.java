package com.example.jarkataee.domain;

public class City {

    private Long countryId; 
    private String cityName;

    public City(Long countryId, String cityName) {
        this.countryId = countryId;
        this.cityName = cityName;
    }


    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }





    @Override
    public String toString() {
        return "City{name='" + cityName + "'}";
    }

}
