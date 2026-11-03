package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
public class App
{
    private Connection con = null;
    public static void main(String[] args)
    {
        App ThisApp = new App();
        ThisApp.Connect();
        if (args.length > 0)
        {
            ThisApp.printCitiesInCountry(args[0]);
        }
        else
        {
            System.out.println("Please provide the country name or the country code to view its cities.");
        }
        ThisApp.Disconnect();
    }
    public void printCitiesInCountry(String country)
    {
        if (con == null)
        {
            System.out.println("Cannot retrieve the cities because the database connection is unavailable.");
            return;
        }
        if (country == null || country.trim().isEmpty())
        {
            System.out.println("Please provide a non-empty country name or country code.");
            return;
        }
        String sql = "SELECT city.Name AS city_name, country.Name AS country_name, " + "city.District AS district, city.Population AS population " + "FROM city JOIN country ON city.CountryCode = country.Code " + "WHERE country.Name = ? OR country.Code = ? " + "ORDER BY city.Population DESC, city.Name ASC";
        try (PreparedStatement statement = con.prepareStatement(sql))
        {
            String countryIdentifier = country.trim();
            statement.setString(1, countryIdentifier);
            statement.setString(2, countryIdentifier);
            try (ResultSet results = statement.executeQuery())
            {
                System.out.printf("Cities in %s, ordered by population (largest to smallest):%n", countryIdentifier);
                System.out.printf("%-35s %-35s %-25s %12s%n", "Name", "Country", "District", "Population");
                boolean found = false;
                while (results.next())
                {
                    found = true;
                    System.out.printf("%-35s %-35s %-25s %,12d%n",
                            results.getString("city_name"),
                            results.getString("country_name"),
                            results.getString("district"),
                            results.getInt("population"));
                }
                if (!found)
                {
                    System.out.printf("No cities found for country '%s'.%n", countryIdentifier);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Could not retrieve the cities for country '" + country.trim() + "': " + e.getMessage());
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
            System.out.println("Could not load the SQL driver");
            System.exit(-1);
        }
        // Connection to the database
        con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33060/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "example");
                System.out.println("Successfully connected");
                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }

        }
    }
    private void Disconnect(){
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
