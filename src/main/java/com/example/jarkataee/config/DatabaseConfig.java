package com.example.jarkataee.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "system";
    private static final String PASSWORD = "admin";


    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Failed to load Oracle JDBC driver", exception);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
