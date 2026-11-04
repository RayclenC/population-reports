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

public class AppIntegrationTest
{
    static Connection connection;

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
    static void closeDatabaseConnection() throws SQLException
    {
        if (connection != null)
        {
            connection.close();
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

}