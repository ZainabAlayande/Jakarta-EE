package com.example.jarkataee.config;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;

public class DatabaseConfig {

    private static String url;
    private static String user;
    private static String password;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                logger.error("Sorry, unable to find application.properties");
            }
            prop.load(input);
            url = prop.getProperty("database.url");
            user = prop.getProperty("database.user");
            password = prop.getProperty("database.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Failed to load Oracle JDBC driver", exception);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
