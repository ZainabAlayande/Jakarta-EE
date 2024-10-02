package com.example.jarkataee.servlet;

import com.example.jarkataee.dao.CountryDaoImpl;
import com.example.jarkataee.dto.CityDTO;
import com.example.jarkataee.services.CountryService;
import com.example.jarkataee.services.CountryServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name = "LargestCityServlet", urlPatterns = {"/largest-city"})
public class LargestCityServlet extends HttpServlet {
    private final CountryService countryService = new CountryServiceImpl(new CountryDaoImpl());

    public LargestCityServlet() throws SQLException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryName = request.getParameter("country");
//        CityDTO largestCity = countryService.getLargestCity(countryName);
        CityDTO largestCity = null;
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(largestCity));
    }

}
