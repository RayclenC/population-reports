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

        String dbUrl = System.getenv().getOrDefault("DB_URL", DEFAULT_DB_URL);
        Connection con = null;
        int retries = 30;
        for (int attempt = 1; attempt <= retries; attempt++)
        {
            System.out.printf("Connecting to database (attempt %d/%d)...%n", attempt, retries);
            try
            {
                con = DriverManager.getConnection(dbUrl, "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.err.println("Failed to connect: " + sqle.getMessage());
                if (attempt < retries) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        System.err.println("Interrupted while waiting to retry the database connection");
                        break;
                    }
                }
            }
        }

        if (con != null)
        {
            try
            {
                ArrayList<Country> countries = getCountriesByPopulation(con);
                System.out.println("All countries by population (largest to smallest):");
                for (Country country : countries) {
                    System.out.println(country.code + " | " + country.name + " | "
                            + country.continent + " | " + country.region + " | "
                            + country.population + " | " + country.capital);
                }
            }
            catch (SQLException e)
            {
                System.err.println("Could not retrieve countries: " + e.getMessage());
            }
            finally
            {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection to database: " + e.getMessage());
                }
            }
        }
        else
        {
            System.err.println("Could not connect to database at " + dbUrl);
            System.exit(1);
        }
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
}
