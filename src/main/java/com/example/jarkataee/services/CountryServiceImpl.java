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
import com.example.jarkataee.dto.CityDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryServiceImpl implements CountryService {

    private CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao)  {
        this.countryDao = countryDao;
    }

    @Override
    public void saveCities(List<City> cities) {
        countryDao.saveCities(cities);
    }

    @Override
    public List<CityDTO> getCities(String countryName) throws SQLException, IOException, InterruptedException {
        System.out.println("Hello Here 3");
        List<City> cities = countryDao.getCitiesByCountry(countryName);

        System.out.println("Hello Here 4");
        if (cities.isEmpty()) {
            System.out.println("Hello Here 5");
            cities = fetchCitiesFromApi(countryName);
            System.out.println("Hello Here 6");
            saveCities(cities);
            System.out.println("Hello Here 7");
        }

        System.out.println("Hello Here 8");
        return cities.stream().map(city -> new CityDTO(city.getName(), city.getPopulation())).collect(Collectors.toList());
    }


    public List<City> fetchCitiesFromApi(String countryName) throws InterruptedException, IOException {
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

            List<City> cities = new ArrayList<>();

            if (!rootNode.get("error").asBoolean()) {
                JsonNode citiesNode = rootNode.get("data");
                for (JsonNode cityNode : citiesNode) {
                    cities.add(new City(cityNode.asText()));
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
        // Business logic to retrieve the largest city
        return countryDao.getLargestCityFromDbOrApi(country);
    }

}
