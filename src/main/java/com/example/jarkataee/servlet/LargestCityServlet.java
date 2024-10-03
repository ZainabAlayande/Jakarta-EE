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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LargestCityServlet", urlPatterns = {"/largest-city"})
public class LargestCityServlet extends HttpServlet {
    private final CountryService countryService = new CountryServiceImpl(new CountryDaoImpl());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryName = request.getParameter("country");
        response.setContentType("application/json");

        try {
            if (countryName == null || countryName.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new Gson().toJson(new ApiResponse<>(false, "Country name is required", null)));
                return;
            }

            String largestCity = countryService.getLargestCity(countryName);

            if (largestCity != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                Map<String, String> data = new HashMap<>();
                data.put("largestCity", largestCity);
                response.getWriter().write(new Gson().toJson(new ApiResponse<>(true, "Successfully retrieved largest city", data)));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new Gson().toJson(new ApiResponse<>(false, "Country or city not found", null)));
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new Gson().toJson(new ApiResponse<>(false, "Error fetching data: " + e, null)));
        }
    }


}
