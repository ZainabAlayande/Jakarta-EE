package com.example.jarkataee.services;

import com.example.jarkataee.domain.City;
import com.example.jarkataee.dto.CityDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CountryService {

    List<CityDTO> getCities(String country) throws SQLException, IOException, InterruptedException;
    String getLargestCity(String country);

    void saveCities(List<City> cities);
}
