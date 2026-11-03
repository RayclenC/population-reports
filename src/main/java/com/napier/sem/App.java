package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * <p>
 *     The App class establishes a connection with the local database with error handling
 *     if the request to connect takes too long or if a connection cannot be established.
 * </p>
 */
public class App
{
    private static final String DEFAULT_DB_URL =
            "jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true";

    public static void main(String[] args)
    {
        App ThisApp = new App();
        ThisApp.Connect();
    }

    public static ArrayList<Country> getCountriesByPopulation(Connection con) throws SQLException {
        String query = "SELECT country.Code, country.Name, country.Continent, country.Region, "
                + "country.Population, city.Name AS Capital "
                + "FROM country LEFT JOIN city ON country.Capital = city.ID "
                + "ORDER BY country.Population DESC, country.Name ASC";

        ArrayList<Country> countries = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                Country country = new Country();
                country.code = results.getString("Code");
                country.name = results.getString("Name");
                country.continent = results.getString("Continent");
                country.region = results.getString("Region");
                country.population = results.getInt("Population");
                country.capital = results.getString("Capital");
                countries.add(country);
            }
        }
        return countries;
    }

    /**
     * Report 3: All the countries in a region organised by largest population to smallest.
     */
    public static ArrayList<Country> getCountriesByRegion(Connection con, String region) {
        try {
            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital " +
                            "FROM country " +
                            "WHERE Region = ? " +
                            "ORDER BY Population DESC";

            PreparedStatement stmt = con.prepareStatement(strSelect);
            stmt.setString(1, region);
            ResultSet rset = stmt.executeQuery();

            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("Code");
                c.name = rset.getString("Name");
                c.continent = rset.getString("Continent");
                c.region = rset.getString("Region");
                c.population = rset.getInt("Population");
                c.capital = rset.getString("Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println("Failed to get country details by region: " + e.getMessage());
            return null;
        }
    }

    private void Connect(){
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(30000);
                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        if (con != null)
        {
            try
            {
                ArrayList<Country> countries = getCountriesByPopulation(con);
                System.out.println("All countries by population (largest to smallest):");
                for (Country country : countries)
                {
                    System.out.println(country.code + " | " + country.name + " | "
                            + country.continent + " | " + country.region + " | "
                            + country.population + " | " + country.capital);
                }

                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}
