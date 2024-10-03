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
        System.out.println("Hi 25");
        countryDao.saveCountry(country);
    }

    @Override
    public List<String> getCities(String countryName) throws SQLException, IOException, InterruptedException, ExecutionException {
        System.out.println("Hi 6");
        List<String> cities = countryDao.getCitiesByCountry(countryName);
        if (cities.isEmpty()) {
            cities = fetchCitiesFromApi(countryName);

            if (cities != null && !cities.isEmpty()) {
                String largestCity = getLargestCityByPopulation(cities);
                System.out.println("Hi 20");
                Long citiesId = countryDao.saveCitiesAndGetId(cities);
                System.out.println("Hi 24");
                System.out.println("largestCity" + largestCity);
                saveCountry(new Country(countryName, citiesId, largestCity));
            }
        }

        return cities;
    }


    private String getLargestCityByPopulation(List<String> cities) throws ExecutionException, InterruptedException {
        System.out.println("Hi 15");
        String largestCity = "";
        long maxPopulation = 0;

        for (String city : cities) {
            CityPopulationData populationData = fetchPopulationForCity(city);

            System.out.println("Hi 18");
            if (populationData != null && populationData.getPopulation() > maxPopulation) {
                maxPopulation = populationData.getPopulation();
                largestCity = populationData.getCity();
            }
        }
        System.out.println("Hi 19");

        return largestCity;
    }

    private CityPopulationData fetchPopulationForCity(String cityName) {
        System.out.println("Hi 16");
        try {
            URL url = new URL(POPULATION_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            JsonObject requestPayload = new JsonObject();
            requestPayload.addProperty("city", cityName);
            String response = ApiHelper.postRequest(String.valueOf(url), requestPayload.toString());

            System.out.println("Hi 17");
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            if (!jsonResponse.get("error").getAsBoolean()) {
                JsonObject cityData = jsonResponse.getAsJsonObject("data");
                String population = cityData.getAsJsonArray("populationCounts")
                        .get(0).getAsJsonObject().get("value").getAsString();

                return new CityPopulationData(cityName, Long.parseLong(population));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<String> fetchCitiesFromApi(String countryName) throws InterruptedException, IOException {
        System.out.println("Hi 9");
        String apiUrl = "https://countriesnow.space/api/v0.1/countries/cities";
        String jsonPayload = String.format("{\"country\": \"%s\"}", countryName);

        System.out.println("Hi 10");
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS).build();

        System.out.println("Hi 11");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        System.out.println("Hi 12");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Hi 13");
        if (response.statusCode() == 200) {
            System.out.println("Hi 14");
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
            System.out.println("Hi 15");
            logger.error("Failed API response. Status code: " + response.statusCode());
            return Collections.emptyList();
        }
    }

    public String getLargestCity(String countryName) throws SQLException {
        return countryDao.getLargestCityByCountry(countryName);
    }






}
