package com.example.jarkataee.servlet;

import com.example.jarkataee.dao.CountryDaoImpl;
import com.example.jarkataee.dto.CityDTO;
import com.example.jarkataee.services.CountryService;
import com.example.jarkataee.services.CountryServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "LargestCityServlet", urlPatterns = {"/largest-city"})
public class LargestCityServlet extends HttpServlet {
    private final CountryService countryService = new CountryServiceImpl(new CountryDaoImpl());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryName = request.getParameter("country");
        CityDTO largestCity = countryService.getLargestCity(countryName);
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(largestCity));
    }

}
