package com.example.jarkataee.dao;

import com.example.jarkataee.config.DatabaseConfig;
import com.example.jarkataee.domain.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryDaoImpl implements CountryDao {

    private static final String GET_CITIES_BY_COUNTRY_QUERY = "SELECT cities_id FROM COUNTRIES WHERE name = ?";
    private static final String INSERT_COUNTRY_QUERY = "INSERT INTO COUNTRIES (name, cities_id, largest_city) VALUES (?, ?, ?)";
    private static final String INSERT_CITY_QUERY = "INSERT INTO CITIES (cities) VALUES (?)";
    private static final String GET_CITY_BY_ID_QUERY = "SELECT cities FROM CITIES WHERE id = ?";
    private static final String GET_LARGEST_CITY_QUERY = "SELECT largest_city FROM COUNTRIES WHERE name = ?";



    @Override
    public String getLargestCityByCountry(String countryName) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_LARGEST_CITY_QUERY)) {
            stmt.setString(1, countryName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("largest_city");
            }
        }
        return null;
    }
    @Override
    public List<String> getCitiesByCountry(String countryName) throws SQLException {
        System.out.println("Hi 7");
        List<String> cities = new ArrayList<>();
        int citiesId = -1;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement getIdStatement = conn.prepareStatement(GET_CITIES_BY_COUNTRY_QUERY)) {

            getIdStatement.setString(1, countryName);
            ResultSet idResultSet = getIdStatement.executeQuery();

            if (idResultSet.next()) {
                citiesId = idResultSet.getInt("cities_id");
            }
        }

        if (citiesId != -1) {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement getCitiesStatement = conn.prepareStatement(GET_CITY_BY_ID_QUERY)) {
                getCitiesStatement.setInt(1, citiesId);
                ResultSet citiesResultSet = getCitiesStatement.executeQuery();
                if (citiesResultSet.next()) {
                    String citiesData = citiesResultSet.getString("cities");
                    cities.addAll(Arrays.asList(citiesData.split(",")));
                }
            }
        }
        System.out.println("Hi 8");
        System.out.println(cities);
        return cities;
    }


    @Override
    public void saveCountry(Country country) throws SQLException {
        System.out.println("Hi 26");
        try (Connection conn = DatabaseConfig.getConnection()) {
            System.out.println("Hi 27");

            System.out.println("Hi 28");
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_COUNTRY_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, country.getName());
                stmt.setLong(2, country.getCitiesId());
                stmt.setString(3, country.getLargestCity());
                stmt.executeUpdate();

                System.out.println("Hi 29");
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int countryId = rs.getInt(1);
                    }
                }
                System.out.println("Hi 30");
            }
        }
    }



    public Long saveCitiesAndGetId(List<String> cities) throws SQLException {
        System.out.println("Hi 21");
        String citiesData = String.join(",", cities);

        System.out.println("Hi 22");
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_CITY_QUERY, new String[]{"id"})) {
            System.out.println("Hi 23");

            statement.setString(1, citiesData);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Failed to retrieve cities_id.");
                }
            }
        }
    }









}
