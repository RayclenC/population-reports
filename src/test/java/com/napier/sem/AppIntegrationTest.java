package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppIntegrationTest
{
    private static Connection connection;
    private static App app;

    @BeforeAll
    static void connectToDatabase() throws SQLException
    {
        SQLException lastError = null;
        for (int attempt = 0; attempt < 60; attempt++)
        {
            try
            {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                app = new App();
                app.Connect("localhost:33060", 30000);
                return;
            }
            catch (SQLException e)
            {
                lastError = e;
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException interrupted)
                {
                    Thread.currentThread().interrupt();
                    throw new SQLException("Interrupted while waiting for the database", interrupted);
                }
            }
        }
        throw lastError;
    }

    @AfterAll
    static void closeDatabaseConnections() throws SQLException
    {
        if (connection != null)
        {
            connection.close();
        }
        if (app != null)
        {
            app.Disconnect();
        }
    }

    @Test
    void getCountriesByPopulationReturnsEveryCountryInDescendingOrder() throws SQLException
    {
        int expectedCountryCount;
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM country"))
        {
            assertTrue(results.next());
            expectedCountryCount = results.getInt(1);
        }

        ArrayList<Country> countries = App.getCountriesByPopulation(connection);

        assertFalse(countries.isEmpty());
        assertEquals(expectedCountryCount, countries.size());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (Country country : countries)
        {
            assertNotNull(country.GetCode());
            assertNotNull(country.GetName());
            assertTrue(country.GetPopulation() <= previousPopulation);
            previousPopulation = country.GetPopulation();
        }
    }

    @Test
    void getCitiesInContinentReturnsCities()
    {
        ArrayList<City> cities = app.getCitiesInContinent(new Continent("Asia"));

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    void getCitiesInPopulationSizeOrderReturnsDescendingPopulations()
    {
        ArrayList<City> cities = app.getCitiesInPopulationSizeOrder();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (City city : cities)
        {
            assertTrue(city.getPopulation() <= previousPopulation);
            previousPopulation = city.getPopulation();
        }
    }
}