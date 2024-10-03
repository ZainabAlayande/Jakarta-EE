package com.example.jarkataee;

import com.example.jarkataee.dao.CountryDao;
import com.example.jarkataee.dao.CountryDaoImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        DatabaseInitializer.executeSqlScript("C:\\Users\\ADMIN\\IdeaProjects\\JarkataEE\\src\\main\\resources\\sql\\create_tables.sql");
//        CountryDao countryDao = new CountryDaoImpl();
//        try {
//            countryDao.createTableIfNotExists();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }

}
