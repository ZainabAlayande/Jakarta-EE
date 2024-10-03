package com.example.jarkataee.services;

import com.example.jarkataee.domain.City;
import com.example.jarkataee.domain.Country;
import com.example.jarkataee.dto.CountryDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CountryService {

    List<String> getCities(String country) throws SQLException, IOException, InterruptedException, ExecutionException;

    String getLargestCity(String country) throws SQLException;

    void saveCountry(Country country) throws SQLException;
}
