package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

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


        Continent asia = new Continent("Asia");
        Region region = new Region("Caribbean");


        /*
        Issues here
         */


        //issue #16
        ArrayList<Country> countries = ThisApp.getCountriesByPopulation();
        //System.out.println("All countries by population (largest to smallest):");
        //ThisApp.printCountries(countries);

        //issue #17
        countries = ThisApp.getCountriesInContinent(asia);
        //ThisApp.printCountries(countries);

        //issue #18
        ArrayList<Country> countriesInRegion = ThisApp.getCountriesInRegion(region);
        //System.out.println("Countries in " + region.getName() + " by population:");
        //ThisApp.printCountries(countriesInRegion);

        //issue #19
        ArrayList<City> cities = ThisApp.getCitiesInPopulationSizeOrder();
        //System.out.println("All cities by population (largest to smallest):");
        //ThisApp.printCities(cities);

        //issue #28
        //ThisApp.printCities(ThisApp.getCitiesInContinent(asia));

        //issue #27
        Region thisRegion = new Region("Eastern Africa");
        //ThisApp.printCountries(ThisApp.getNPopulousCountriesInRegion(5, thisRegion));

        //issue #42
        ThisApp.printCities(ThisApp.getCapitalCities());
      
        //issue #40
        String countryCode;
        int numberOfCities;
        if (args.length >= 4) {
            countryCode = args[2];
            numberOfCities = Integer.parseInt(args[3]);
        } else if (System.console() == null) {
            countryCode = args.length >= 3 ? args[2] : "USA";
            numberOfCities = 5;
            System.out.println("No interactive console; showing the top 5 cities in " + countryCode + ".");
        } else {
            Scanner input = new Scanner(System.in);
            if (args.length >= 3) {
                countryCode = args[2];
            } else {
                System.out.print("Enter a country code (for example, CAN): ");
                countryCode = input.nextLine();
            }
            System.out.print("Enter the number of top cities to display: ");
            numberOfCities = Integer.parseInt(input.nextLine());
        }
        ThisApp.printCities(ThisApp.getNPopulousCitiesInCountry(numberOfCities, countryCode));

        //issue #30
        ThisApp.printCities(ThisApp.getCitiesInRegion(thisRegion));

        /*
        End of issues
         */



        ThisApp.Disconnect();
    }

    /**
     * This method for every city in cities prints the fields of the city that aren't null
     * @param cities
     */
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

    /**
     * This method for every country in countries prints the fields of the country that aren't null
     * @param countries
     */
    public void printCountries(ArrayList<Country> countries){
        if (countries == null){
            System.out.println("No countries to print");
        }
        else{
            for (Country country : countries){
                if (country != null) {
                    String country_string = "";

                    if (country.getCode() != null) {country_string += " | " + country.getCode();}
                    if (country.getName() != null) {country_string += " | " + country.getName();}
                    if (country.getContinent() != null) {country_string += " | " + country.getContinent();}
                    if (country.getRegion() != null) {country_string += " | " + country.getRegion();}
                    if (country.getPopulation() != 0) {country_string += " | population: " + country.getPopulation();}
                    if (country.getCapital() != null) {country_string += " | " + country.getCapital();}

                    if(!country_string.isEmpty()) {System.out.println(country_string);}
                }
            }
        }
    }

    /**
     * This method takes a list of countries and outputs a list of countries sorted by population
     * @param countries
     * @return
     */
    static ArrayList<Country> sortCountriesByPopulation(ArrayList<Country> countries)
    {
        countries.sort(Comparator.comparingDouble(Country::getPopulation).reversed());
        return countries;
    }

    /**
     * Issue #16
     * This method returns a list of all countries sorted by population
     * @return
     */
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

    /**
     * Issue #18
     * This method inputs a region and returns a list of all the countries in that region
     * @param region
     * @return
     */
    public ArrayList<Country> getCountriesInRegion(Region region)
    {
        String query = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country WHERE Region = ? ORDER BY Population DESC";
        ArrayList<Country> countries = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, region.getName());
            try (ResultSet results = statement.executeQuery())
            {
                while (results.next())
                {
                    countries.add(new Country(results.getString("Code"),
                            results.getString("Name"),
                            results.getString("Continent"),
                            results.getString("Region"),
                            results.getInt("Population"),
                            results.getString("Capital")));
                }
            }
            return sortCountriesByPopulation(countries);
        }
        catch (SQLException e)
        {
            System.out.println("getCountriesInRegion error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report 17
     * This method inputs a coninent and returns a list of all the countries in that continent
     * @param continent
     * @return
     */
    public ArrayList<Country> getCountriesInContinent(Continent continent){
        try {

            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent LIKE '"
                    + continent.getName() + "' ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            int counter = 0;
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
                counter += 1;
            }

            if (counter == 0) { throw new Exception("No SQL query returned"); }

            return sortCountriesByPopulation(countries);

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report 19
     * This method returns a list of every city in the world sorted by population
     * @return
     */
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

    /**
     * Report 20
     * This method inputs a continent and returns a list of all the cities in that continent
     * @param continent
     * @return
     */
    public ArrayList<City> getCitiesInContinent(Continent continent){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
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

    /**
     * Returns the N most populous cities in a country.
     * @param N maximum number of cities to return
     * @param countryCode code of the country whose cities should be returned
     * @return cities ordered by population from largest to smallest
     */
    // Report 40
    public ArrayList<City> getNPopulousCitiesInCountry(int N, String countryCode)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException("N must be greater than zero");
        }
        if (countryCode == null || countryCode.trim().isEmpty())
        {
            throw new IllegalArgumentException("A country code is required");
        }

        String query = "SELECT ID, Name, CountryCode, District, Population "
                + "FROM city WHERE CountryCode = ? ORDER BY Population DESC LIMIT ?";
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, countryCode.trim().toUpperCase(Locale.ROOT));
            statement.setInt(2, N);
            try (ResultSet results = statement.executeQuery())
            {
                while (results.next())
                {
                    cities.add(new City(results.getInt("ID"),
                            results.getString("Name"),
                            results.getString("CountryCode"),
                            results.getString("District"),
                            results.getInt("Population")));
                }
            }
            return cities;
        }
        catch (SQLException e)
        {
            System.out.println("getNPopulousCitiesInCountry error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report 27
     * This method gets the top N countries from the list of countries in a continent sorted by population
     * @param N
     * @param region
     * @return
     */
    public ArrayList<Country> getNPopulousCountriesInRegion(int N, Region region)
    {
        try {

            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE  Region LIKE '"
            + region.getName() + "' ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            ArrayList<Country> countries = new ArrayList<>();
            int counter = 1;
            while (results.next() && counter <= N) {
                Country country = new Country(results.getString("Code"),
                        results.getString("Name"),
                        results.getString("Continent"),
                        results.getString("Region"),
                        results.getInt("Population"),
                        results.getString("Capital")
                );
                countries.add(country);
                counter += 1;
            }

            return sortCountriesByPopulation(countries);

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }
                                     
    /**
     * This method inputs a region and it returns all the cities in the region ordered by population
     * @param region
     * @return
     */
    //Report 30
    public ArrayList<City> getCitiesInRegion(Region region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city "
                            + "LEFT JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.Region LIKE '" + region.getName() + "'"
                            + " ORDER BY city.Population ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next()) {
                City city = new City(rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
            }
            return cities;

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }
          
          
          
              //issue #42
    public ArrayList<City> getCapitalCities()
    {
        try {

            String query = "SELECT ID, city.Name, CountryCode, District, city.Population FROM country "
            + "LEFT JOIN city ON city.ID = country.Capital ORDER BY Population ASC";
            Statement statement = con.createStatement();
            ResultSet rset = statement.executeQuery(query);

            ArrayList<City> cities = new ArrayList<>();
            while (rset.next()) {
                City city = new City(rset.getInt("ID"),
                        rset.getString("City.Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
            }                       

            return cities;
        }
        catch (Exception e)
        {
            System.out.println("ViewCitiesInRegion failed: " + e.getMessage());
            return null;
        }
    }

    /*
    **********************************************************
       DO NOT TOUCH THE CONNECT AND DISCONNECT METHODS
    **********************************************************
     */

    /**
     * This method connects to the database
     * @param location
     * @param delay
     */
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
                Thread.sleep(1000);
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

    /**
     * This method disconnects from any database connection
     */
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