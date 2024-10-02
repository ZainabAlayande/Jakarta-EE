package com.example.jarkataee.services;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jarkataee.dao.CountryDao;
import com.example.jarkataee.domain.City;
import com.example.jarkataee.dto.CityDTO;

public class CountryServiceImpl implements CountryService {

    private CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
        this.countryDao.createTableIfNotExists();
    }

    @Override
    public void saveCities(List<City> cities) {
        countryDao.saveCities(cities);
    }

    @Override
    public List<CityDTO> getCities(String countryName) throws SQLException {
        List<City> cities = countryDao.getCitiesByCountry(countryName);

        // If the list is empty, fetch from the external API and save to the database
        if (cities.isEmpty()) {
            cities = fetchCitiesFromApi(countryName);
            countryDao.saveCities(cities);
        }

        // Convert List<City> to List<CityDTO> to send to the servlet
        return cities.stream()
                .map(city -> new CityDTO(city.getName(), city.getPopulation()))
                .collect(Collectors.toList());
    }



    @Override
    public String getLargestCity(String country) {
        // Business logic to retrieve the largest city
        return countryDao.getLargestCityFromDbOrApi(country);
    }

}
