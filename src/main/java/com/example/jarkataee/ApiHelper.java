package com.example.jarkataee;

import com.example.jarkataee.config.DatabaseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiHelper {


//    public static String postRequest(String urlString, String payload) throws IOException {
//        URL url = new URL(urlString);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setDoOutput(true);
//
//        try (OutputStream os = connection.getOutputStream()) {
//            byte[] input = payload.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//
//        int status = connection.getResponseCode();
//        BufferedReader in;
//        if (status > 299) {
//            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//        } else {
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        }
//
//        StringBuilder response = new StringBuilder();
//        String line;
//        while ((line = in.readLine()) != null) {
//            response.append(line);
//        }
//
//        in.close();
//        connection.disconnect();
//        return response.toString();
//    }

    public static String postRequest(String urlString, String payload) throws IOException {


        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = payload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = connection.getResponseCode();
        BufferedReader in;
        if (status > 299) {
            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();
        connection.disconnect();
        return response.toString();
    }


}
