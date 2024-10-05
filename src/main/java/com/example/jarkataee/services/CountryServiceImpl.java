package com.example.jarkataee.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.example.jarkataee.config.DatabaseConfig;
import com.example.jarkataee.dao.CountryDao;
import com.example.jarkataee.ApiHelper;
import com.example.jarkataee.domain.City;
import com.example.jarkataee.domain.Country;
import com.example.jarkataee.dto.CityPopulationData;
import com.example.jarkataee.dto.CountryDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CountryServiceImpl implements CountryService {

    private static final String POPULATION_API_URL = "https://countriesnow.space/api/v0.1/countries/population/cities";
    private final CountryDao countryDao;
    private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    public CountryServiceImpl(CountryDao countryDao)  {
        this.countryDao = countryDao;
    }


    @Override
    public void saveCountry(Country country) throws SQLException {
        countryDao.saveCountry(country);
    }

    @Override
    public List<String> getCities(String countryName) throws SQLException, IOException, InterruptedException, ExecutionException {
        List<String> cities = countryDao.getCitiesByCountry(countryName);
        if (cities.isEmpty()) {
            cities = fetchCitiesFromApi(countryName);

            if (cities != null && !cities.isEmpty()) {
                String largestCity = getLargestCityByPopulation(cities);
                Long citiesId = countryDao.saveCitiesAndGetId(cities);
                saveCountry(new Country(countryName, citiesId, largestCity));
            }
        }

        return cities;
    }


    private String getLargestCityByPopulation(List<String> cities) throws ExecutionException, InterruptedException, IOException {
        System.out.println("cities" + cities);
        String largestCity = "";
        long maxPopulation = 0;

        for (String city : cities) {
            CityPopulationData populationData = fetchPopulationForCity(city);

            if (populationData != null && populationData.getPopulation() > maxPopulation) {
                maxPopulation = populationData.getPopulation();
                largestCity = populationData.getCity();
            }
        }

        return largestCity;
    }


    private CityPopulationData fetchPopulationForCity(String cityName) throws IOException, InterruptedException {
        String apiUrl = POPULATION_API_URL;
        String jsonPayload = String.format("{\"city\": \"%s\"}", cityName);

        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            if (!rootNode.get("error").asBoolean()) {
                JsonNode cityData = rootNode.get("data");

                String population = cityData.get("populationCounts").get(0).get("value").asText();

                return new CityPopulationData(cityName, Long.parseLong(population));
            } else {
                logger.error("Error fetching population data: " + rootNode.get("message").asText());
            }
        } else {
            logger.error("Failed API request. Status code: " + response.statusCode());
        }

        return null;
    }


    public List<String> fetchCitiesFromApi(String countryName) throws InterruptedException, IOException {
        String apiUrl = "https://countriesnow.space/api/v0.1/countries/cities";
        String jsonPayload = String.format("{\"country\": \"%s\"}", countryName);

        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            logger.info("Successful API Response");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            List<String> cities = new ArrayList<>();

            if (!rootNode.get("error").asBoolean()) {
                JsonNode citiesNode = rootNode.get("data");
                for (JsonNode cityNode : citiesNode) {
                    cities.add(cityNode.asText());
                }
            } else {
                logger.error("Error fetching cities: " + rootNode.get("msg").asText());
            }

            return cities;
        } else {
            logger.error("Failed API response. Status code: " + response.statusCode());
            return Collections.emptyList();
        }
    }

    public String getLargestCity(String countryName) throws SQLException {
        return countryDao.getLargestCityByCountry(countryName);
    }






}
