package com.example.jarkataee;

import com.example.jarkataee.dao.CountryDao;
import com.example.jarkataee.dao.CountryDaoImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CountryDao countryDao = new CountryDaoImpl();
        countryDao.createTableIfNotExists();  // Create table when app starts
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }

}
