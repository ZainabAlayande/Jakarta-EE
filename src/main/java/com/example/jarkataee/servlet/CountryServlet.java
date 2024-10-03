package com.example.jarkataee.servlet;

import com.example.jarkataee.dao.CountryDaoImpl;
import com.example.jarkataee.dto.ApiResponse;
import com.example.jarkataee.services.CountryService;
import com.example.jarkataee.services.CountryServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@WebServlet(name = "CountryServlet", urlPatterns = {"/cities"})
public class CountryServlet extends HttpServlet {

    private final CountryService countryService = new CountryServiceImpl(new CountryDaoImpl());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Hi 1");
        String countryName = request.getParameter("country");
        List<String> cities;

        try {
            System.out.println("Hi 2");
            cities = countryService.getCities(countryName);
            if (cities.isEmpty()) {
                System.out.println("Hi 3");
                ApiResponse<String> apiResponse = new ApiResponse<>(true, "No cities found for the given country", null);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(apiResponse));
            } else {
                System.out.println("Hi 4");
                ApiResponse<List<String>> apiResponse = new ApiResponse<>(false, "List of cities for " + countryName + " fetched successfully", cities);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(apiResponse));
            }
        } catch (SQLException | InterruptedException | ExecutionException exception) {
            System.out.println("Hi 5");
            ApiResponse<String> apiResponse = new ApiResponse<>(true, "Error fetching cities: " + exception.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(apiResponse));
        }
    }

}
