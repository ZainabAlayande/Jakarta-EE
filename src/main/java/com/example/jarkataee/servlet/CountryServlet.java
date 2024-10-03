package com.example.jarkataee.servlet;

import com.example.jarkataee.dao.CountryDaoImpl;
import com.example.jarkataee.dto.ApiResponse;
import com.example.jarkataee.dto.CountryDTO;
import com.example.jarkataee.services.CountryService;
import com.example.jarkataee.services.CountryServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "CountryServlet", urlPatterns = {"/cities"})
public class CountryServlet extends HttpServlet {

    private final CountryService countryService = new CountryServiceImpl(new CountryDaoImpl());


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryName = request.getParameter("country");
        List<String> cities;

        try {
            cities = countryService.getCities(countryName);
            if (cities.isEmpty()) {
                ApiResponse<String> apiResponse = new ApiResponse<>(true, "No cities found for the given country", null);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(apiResponse));
            } else {
                ApiResponse<List<String>> apiResponse = new ApiResponse<>(false, "List of cities for " + countryName + " fetched successfully", cities);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(apiResponse));
            }
        } catch (SQLException | InterruptedException exception) {
            ApiResponse<String> apiResponse = new ApiResponse<>(true, "Error fetching cities: " + exception.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(apiResponse));
        }
    }

}
