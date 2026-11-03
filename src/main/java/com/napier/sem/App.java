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

