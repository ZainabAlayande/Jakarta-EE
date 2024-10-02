package com.example.jarkataee.dao;

import com.example.jarkataee.domain.City;

import java.sql.SQLException;
import java.util.List;

public interface CountryDao {

    void createTableIfNotExists();

    List<City> getCitiesByCountry(String countryName) throws SQLException;

    void saveCities(List<City> cities);

    String getLargestCityFromDbOrApi(String country);

}
