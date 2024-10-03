package com.example.jarkataee.services;

import com.example.jarkataee.domain.City;
import com.example.jarkataee.domain.Country;
import com.example.jarkataee.dto.CountryDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CountryService {

    List<String> getCities(String country) throws SQLException, IOException, InterruptedException;
    String getLargestCity(String country);

    void saveCountry(Country country) throws SQLException;
}
