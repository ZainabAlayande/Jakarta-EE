package com.example.jarkataee;

import com.example.jarkataee.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            if (connection != null) {
                System.out.println("Database connected successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
