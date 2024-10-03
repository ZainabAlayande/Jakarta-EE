package com.example.jarkataee;

import com.example.jarkataee.config.DatabaseConfig;
import com.example.jarkataee.dao.CountryDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            if (connection != null) {
                logger.info("Database connected successfully!");
            } else {
                logger.error("Failed to connect to the database.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


}
