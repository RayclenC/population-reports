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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppIntegrationTest
{
    private static Connection connection;

    @BeforeAll
    static void connectToDatabase() throws SQLException
    {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false",
                "root",
                "example");
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
    void getCountriesByPopulationReturnsAllCountriesInDescendingOrder() throws SQLException
    {
        int countryCount;
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM country"))
        {
            assertTrue(results.next());
            countryCount = results.getInt(1);
        }

        ArrayList<Country> countries = App.getCountriesByPopulation(connection);

        assertFalse(countries.isEmpty());
        assertEquals(countryCount, countries.size());

        int previousPopulation = Integer.MAX_VALUE;
        for (Country country : countries)
        {
            assertNotNull(country.code);
            assertNotNull(country.name);
            assertTrue(country.population <= previousPopulation);
            previousPopulation = country.population;
        }
    }
}
