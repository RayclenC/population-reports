package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * <p>
 *     The App class establishes a connection with the local database with error handling
 *     if the request to connect takes too long or if a connection cannot be established.
 *
 *     Inside the App class is the methods used to generate reports.
 *
 * </p>
 */
public class App
{
    private Connection con = null;

    public static void main(String[] args)
    {
        App ThisApp = new App();

        //edits connection if there is arguments
        if(args.length < 1){
            ThisApp.Connect("localhost:33060", 30000);
        }else{
            ThisApp.Connect(args[0], Integer.parseInt(args[1]));
        }



        /*
        Issues here
         */


        //issue #20
        Continent asia = new Continent("Asia");
        ThisApp.printCities(ThisApp.getCitiesInContinent(asia));

        //issue #19
        ArrayList<City> cities = ThisApp.getCitiesInPopulationSizeOrder();
        System.out.println("All cities by population (largest to smallest):");
        ThisApp.printCities(cities);

        //issue #?
        ArrayList<Country> countries = ThisApp.getCountriesByPopulation();
        System.out.println("All countries by population (largest to smallest):");
        for (Country country : countries)
        {
            System.out.println(formatCountry(country));
        }




        /*
        End of issues
         */

        ThisApp.Disconnect();
    }

    public ArrayList<Country> getCountriesByPopulation()
    {
        try {

            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            ArrayList<Country> countries = new ArrayList<>();
            while (results.next()) {
                Country country = new Country(results.getString("Code"),
                        results.getString("Name"),
                        results.getString("Continent"),
                        results.getString("Region"),
                        results.getInt("Population"),
                        results.getString("Capital")
                );
                countries.add(country);
            }

            return sortCountriesByPopulation(countries);

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }

    static ArrayList<Country> sortCountriesByPopulation(ArrayList<Country> countries)
    {
        countries.sort(Comparator.comparingDouble(Country::getPopulation).reversed());
        return countries;
    }

    static String formatCountry(Country country)
    {
        return country.getCode() + " | " + country.getName() + " | "
                + (long) country.getPopulation();
    }

    public void printCities(ArrayList<City> cities){
        if (cities == null){
            System.out.println("No cities to print");
        }
        else{
            for (City city : cities){
                if (city != null) {
                    String city_string = "";

                    if (city.getId() != 0) {city_string += " | " + city.getId();}
                    if (city.getName() != null) {city_string += " | " + city.getName();}
                    if (city.getCountryCode() != null) {city_string += " | " + city.getCountryCode();}
                    if (city.getDistrict() != null) {city_string += " | " + city.getDistrict();}
                    if (city.getPopulation() != 0) {city_string += " | " + city.getPopulation();}

                    if(!city_string.isEmpty()) {System.out.println(city_string);}
                }
            }
        }
    }


    // Report 20
    public ArrayList<City> getCitiesInContinent(Continent continent){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT ID, Name, CountryCode, District, Population"
                            + "FROM city "
                            + "LEFT JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.Continent LIKE '" + continent.getName() + "'"
                            + " ORDER BY city.Population ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next())
            {
                City city = new City(   rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println("ViewCitiesInContinent failed: " + e.getMessage());
            return null;
        }
    }

    // Report 19
    public ArrayList<City> getCitiesInPopulationSizeOrder()
    {
        try
        {
            // Create string for SQL statement
            String strSelect =
                    "SELECT ID, Name, CountryCode, District, Population "
                            + "FROM city " +
                            "ORDER BY Population DESC";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned

            ArrayList<City> cities = new ArrayList<>();
            while (rset.next())
            {
                City city = new City(   rset.getInt("ID"),
                                        rset.getString("Name"),
                                        rset.getString("CountryCode"),
                                        rset.getString("District"),
                                        rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Report 27
    public ArrayList<Country> getNPopulousCitiesInRegion()
    {
        try {

            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            ArrayList<Country> countries = new ArrayList<>();
            while (results.next()) {
                Country country = new Country(results.getString("Code"),
                        results.getString("Name"),
                        results.getString("Continent"),
                        results.getString("Region"),
                        results.getInt("Population"),
                        results.getString("Capital")
                );
                countries.add(country);
            }

            return sortCountriesByPopulation(countries);

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }


    public void Connect(String location, int delay){
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
        con = null;
        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                // Wait a bit
                System.out.println("Succesfully connected");
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
    }

    public void Disconnect(){
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