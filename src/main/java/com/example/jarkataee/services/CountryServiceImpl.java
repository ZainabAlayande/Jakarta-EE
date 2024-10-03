package com.example.jarkataee.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jarkataee.dao.CountryDao;
import com.example.jarkataee.domain.City;
import com.example.jarkataee.domain.Country;
import com.example.jarkataee.dto.CountryDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryServiceImpl implements CountryService {

    private CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao)  {
        this.countryDao = countryDao;
    }


    @Override
    public void saveCountry(Country country) throws SQLException {
        countryDao.saveCountry(country);
    }

    @Override
    public List<String> getCities(String countryName) throws SQLException, IOException, InterruptedException {
        List<String> cities = countryDao.getCitiesByCountry(countryName);

        if (cities.isEmpty()) {
            cities = fetchCitiesFromApi(countryName);
            if (cities != null && !cities.isEmpty()) saveCountry(new Country(countryName, cities, ""));
        }

        return cities;
    }




    public List<String> fetchCitiesFromApi(String countryName) throws InterruptedException, IOException {
        String apiUrl = "https://countriesnow.space/api/v0.1/countries/cities";
        String jsonPayload = String.format("{\"country\": \"%s\"}", countryName);

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Successful API Response");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            List<String> cities = new ArrayList<>();

            if (!rootNode.get("error").asBoolean()) {
                JsonNode citiesNode = rootNode.get("data");
                for (JsonNode cityNode : citiesNode) {
                    cities.add(cityNode.asText());
                }
            } else {
                System.out.println("Error fetching cities: " + rootNode.get("msg").asText());
            }

            return cities;
        } else {
            System.out.println("Failed API response. Status code: " + response.statusCode());
            return Collections.emptyList();
        }
    }



    @Override
    public String getLargestCity(String country) {
        return countryDao.getLargestCityFromDbOrApi(country);
    }



}
