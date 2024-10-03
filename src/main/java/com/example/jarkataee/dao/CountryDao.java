package com.example.jarkataee.dao;

import com.example.jarkataee.domain.City;
import com.example.jarkataee.domain.Country;

import java.sql.SQLException;
import java.util.List;

public interface CountryDao {

    void createTableIfNotExists() throws SQLException;

    List<String> getCitiesByCountry(String countryName) throws SQLException;


    void saveCountry(Country country) throws SQLException;

    String getLargestCityFromDbOrApi(String country);

}
