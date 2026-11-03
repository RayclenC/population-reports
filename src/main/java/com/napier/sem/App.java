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
                        Thread.sleep(30000);
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
                ArrayList<Cities> cities = getCitiesByPopulation(con);
                System.out.println("All countries by population (largest to smallest):");
                for (Cities city : cities) {
                    System.out.println(city.id + " | " + city.name + " | "
                            + city.country_code + " | " + city.district + " | "
                            + city.population);
                }
            }
            catch (SQLException e)
            {
                System.err.println("Could not retrieve cities: " + e.getMessage());
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

    public static ArrayList<Cities> getCitiesByPopulation(Connection con) throws SQLException {
        String query = "SELECT city.id, city.name, city.country_code, city.district, "
                + "city.population, city.name AS Capital "
                + "FROM city"
                + "ORDER BY city.population DESC, city.name ASC";

        ArrayList<Cities> cities = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                Cities city = new Cities();
                city.id = results.getInt("ID");
                city.name = results.getString("Name");
                city.country_code = results.getString("CountryCode");
                city.district = results.getString("District");
                city.population = results.getInt("Population");
                cities.add(city);
            }
        }
        return cities;

    }
}
