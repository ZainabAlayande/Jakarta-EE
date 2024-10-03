package com.example.jarkataee;

import com.example.jarkataee.config.DatabaseConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {


    public static void executeSqlScript(String filePath) {
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }
            statement.execute(sql.toString());
            System.out.println("Database tables created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
