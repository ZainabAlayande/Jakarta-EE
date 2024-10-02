package com.example.jarkataee.servlet;

import com.example.jarkataee.dao.CountryDaoImpl;
import com.example.jarkataee.dto.CityDTO;
import com.example.jarkataee.services.CountryService;
import com.example.jarkataee.services.CountryServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "CityServlet", urlPatterns = {"/cities"})
public class CityServlet extends HttpServlet {

    private final CountryService countryService = new CountryServiceImpl(new CountryDaoImpl());

    public CityServlet() throws SQLException {
    }


////    @Override
////    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
////        // Read request body as a string
////        System.out.println("Hello Here 1");
////        StringBuilder requestBody = new StringBuilder();
////        BufferedReader reader = request.getReader();
////        String line;
////        while ((line = reader.readLine()) != null) {
////            requestBody.append(line);
////        }
////
////        // Parse the JSON body to extract the "country" field
////        JsonObject jsonObject = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
////        String countryName = jsonObject.get("country").getAsString();
////
////        // Call the service to fetch cities for the country
////        List<CityDTO> cities = null;
////        try {
////            cities = countryService.getCities(countryName);
////            System.out.println("Hello Here 2");
////        } catch (SQLException | InterruptedException exception) {
////            throw new RuntimeException(exception);
////        }
////
////        // Send the cities back as a JSON response
//        response.setContentType("application/json");
//        response.getWriter().write(new Gson().toJson(cities));
//    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryName = request.getParameter("country");
        System.out.println("Hello Here 1");
        List<CityDTO> cities = null;
        try {
            cities = countryService.getCities(countryName);
            System.out.println("Hello Here 2");
        } catch (SQLException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(cities));
    }

}
