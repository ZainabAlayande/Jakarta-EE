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


    public List<City> fetchCitiesFromApi(String countryName) throws IOException, InterruptedException {
        // Define the API endpoint and payload
        String apiUrl = "https://countriesnow.space/api/v0.1/countries/cities";
        String jsonPayload = String.format("{\"country\": \"%s\"}", countryName);

        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create the HTTP request with the POST method and JSON body
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        List<City> cities = new ArrayList<>();

        // Extract cities from the "data" array if there is no error
        if (!rootNode.get("error").asBoolean()) {
            JsonNode citiesNode = rootNode.get("data");
            for (JsonNode cityNode : citiesNode) {
                cities.add(new City(cityNode.asText()));
            }
        } else {
            System.out.println("Error fetching cities: " + rootNode.get("msg").asText());
        }

        return cities;
    }


    @Override
    public String getLargestCity(String country) {
        // Business logic to retrieve the largest city
        return countryDao.getLargestCityFromDbOrApi(country);
    }

}
