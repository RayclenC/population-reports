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
    private static final String REGIONAL_CITIES_QUERY =
            "SELECT city.ID, city.Name AS city_name, city.CountryCode, city.District, "
                    + "city.Population, country.Name AS country_name "
                    + "FROM city JOIN country ON city.CountryCode = country.Code "
                    + "WHERE country.Region = ? ORDER BY city.Population DESC, city.ID ASC";

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


        Scanner input = new Scanner(System.in);

        City thisCity = new City(460,
                "Edinburgh",
                "GBR",
                "District",
                450180);
        Country thisCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");
        Continent thisContinent = new Continent("Asia");
        Region thisRegion = new Region("Eastern Africa");
        District thisDistrict = new District("England");


        /*
        Reports here
         */


        //Report #16
        ArrayList<Country> countries = ThisApp.getCountriesByPopulation();
        ThisApp.printCountries(countries);

        //Report #17
        countries = ThisApp.getCountriesInContinent(thisContinent);
        ThisApp.printCountries(countries);

        //Report #18
        ArrayList<Country> countriesInRegion = ThisApp.getCountriesInRegion(thisRegion);
        ThisApp.printCountries(countriesInRegion);

        //Report #19
        ArrayList<City> cities = ThisApp.getCitiesInPopulationSizeOrder();
        ThisApp.printCities(cities);

        //Report #25
        int numberOfCountries = readPositiveInteger(input, "Enter the number of top countries to display: ", 5);
        ArrayList<Country> topCountries = ThisApp.getNPopulousCountries(numberOfCountries);
        ThisApp.printCountries(topCountries);

        //Report #26
        String continentName = readLineOrDefault(input, "Enter a continent (for example, Asia): ", "Asia");
        int numberOfContinentCountries = readPositiveInteger(
                input, "Enter the number of top countries to display: ", 5);
        Continent continent = new Continent(continentName);
        ArrayList<Country> topCountriesInContinent =
                ThisApp.getNPopulousCountriesInContinent(numberOfContinentCountries, continent);
        ThisApp.printCountries(topCountriesInContinent);

        //Report #28
        ThisApp.printCities(ThisApp.getCitiesInContinent(thisContinent));

        //Report #31
        ArrayList<City> citiesInCountry = ThisApp.getCitiesInCountry(thisCountry);
        ThisApp.printCities(citiesInCountry);

        //Report #32
        String districtName = readLineOrDefault(input, "Enter a district (for example, England): ", "England");
        ArrayList<City> citiesInDistrict = ThisApp.getCitiesInDistrict(new District(districtName));
        ThisApp.printCities(citiesInDistrict);

        //Reports #21 and #36 use the same region.
        String defaultRegion = "Eastern Africa";
        String regionName = readLineOrDefault(input, "Enter a region for the city and country reports "
                + "(press Enter for Eastern Africa): ", defaultRegion);
        Region reportRegion = new Region(regionName.isBlank() ? defaultRegion : regionName);
        ThisApp.printRegionalCityReport(reportRegion);

        //Report #36
        int numberOfRegionCountries = readPositiveInteger(
                input, "Enter the number of top countries to display: ", 5);
        ArrayList<Country> topCountriesInRegion =
                ThisApp.getNPopulousCountriesInRegion(numberOfRegionCountries, reportRegion);
        ThisApp.printCountries(topCountriesInRegion);

        //Report #38
        int numberOfContinentCities = readPositiveInteger(
                input, "Enter the number of top cities to display: ", 5);
        ArrayList<City> topCitiesInContinent =
                ThisApp.getNPopulousCitiesInContinent(numberOfContinentCities, continent);
        ThisApp.printCities(topCitiesInContinent);

        //Report #39
        ThisApp.printCities(ThisApp.getNPopulousCitiesInRegion(5, thisRegion));

        //Report #40
        String countryCode = readLineOrDefault(input, "Enter a country code (for example, CAN): ", "USA");
        int numberOfCities = readPositiveInteger(input, "Enter the number of top cities to display: ", 5);
        ArrayList<City> topCities = ThisApp.getNPopulousCitiesInCountry(numberOfCities, countryCode);
        ThisApp.printCities(topCities);

        //Report #41
        ThisApp.printCities(ThisApp.getNPopulousCitiesInDistrict(5, thisDistrict));

        //Report #42
        ThisApp.printCities(ThisApp.getCapitalCities());

        // Report #44
        ThisApp.printCities(ThisApp.getCapitalCitiesInRegion(thisRegion));

        //Report #45
        ThisApp.printCities(ThisApp.getNCapitalCities(7));

        // Report #46
        ThisApp.printCities(ThisApp.getNCapitalCitiesInContinent(7, thisContinent));

        // Report #43
        ThisApp.printCities(ThisApp.getCapitalCitiesInContinent(thisContinent));

        // Report #47
        ThisApp.printCities(ThisApp.getNCapitalCitiesInRegion(7, thisRegion));

        // Report #48
        ThisApp.printPopulationBreakdownsByContinent();

        // Report #49
        ThisApp.printPopulationBreakdownsByRegion();

        // Report #50
        ThisApp.printPopulationBreakdownsByCountry();

        // Report #51
        System.out.println(ThisApp.getTotalPopulation());

        // Report #52
        System.out.println(ThisApp.getTotalPopulationByContinent(thisContinent));

        // Report #53
        System.out.println(ThisApp.getTotalPopulationByRegion(thisRegion));

        // Report #54
        System.out.println(ThisApp.getTotalPopulationByCountry(thisCountry));

        // Report #55
        System.out.println(ThisApp.getTotalPopulationByDistrict(thisDistrict));

        // Report #56
        System.out.println(ThisApp.getTotalPopulationByCity(thisCity));

        // Report #57
        ThisApp.printLanguageStatistics();

        /*
        End of Reports
         */



        ThisApp.Disconnect();
    }

    /**
     * This method for every city in cities prints the fields of the city that aren't null
     * @param cities list of city objects
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
     * @param countries list of Country objects
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
     * @param countries list of Country Objects
     * @return list of Country objects sorted by their population
     */
    static ArrayList<Country> sortCountriesByPopulation(ArrayList<Country> countries)
    {
        countries.sort(Comparator.comparingDouble(Country::getPopulation).reversed());
        return countries;
    }

    private static Country mapCountry(ResultSet results) throws SQLException
    {
        return new Country(results.getString("Code"),
                results.getString("Name"),
                results.getString("Continent"),
                results.getString("Region"),
                results.getInt("Population"),
                results.getString("Capital"));
    }

    static String readLineOrDefault(Scanner input, String prompt, String defaultValue)
    {
        System.out.print(prompt);
        System.out.flush();
        if (input.hasNextLine())
        {
            return input.nextLine().trim();
        }
        System.out.println("No input available; using " + defaultValue + ".");
        return defaultValue;
    }

    static int readPositiveInteger(Scanner input, String prompt, int defaultValue)
    {
        while (true)
        {
            System.out.print(prompt);
            System.out.flush();
            if (!input.hasNextLine())
            {
                System.out.println("No input available; using " + defaultValue + ".");
                return defaultValue;
            }

            try
            {
                int number = Integer.parseInt(input.nextLine().trim());
                if (number > 0)
                {
                    return number;
                }
            }
            catch (NumberFormatException e)
            {
                // Ask again below when the input is not a number.
            }

            System.out.println("Please enter a positive whole number.");
        }
    }

    /**
     * Report #16
     * This method returns a list of all countries in the world organized by population,
     * @return list of all Country objects sorted by population
     */
    public ArrayList<Country> getCountriesByPopulation()
    {
        try {

            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            ArrayList<Country> countries = new ArrayList<>();
            while (results.next()) {
                countries.add(mapCountry(results));
            }

            return sortCountriesByPopulation(countries);

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report 17
     * This method returns a list of all Countries in the world by a Continent.
     * @param continent the Continent you want to filter by.
     * @return list of all Country objects filtered by a given continent.
     */
    public ArrayList<Country> getCountriesInContinent(Continent continent){
        try {

            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent LIKE '"
                    + continent.getName() + "' ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            ArrayList<Country> countries = new ArrayList<>();
            while (results.next()) {
                countries.add(mapCountry(results));
            }

            if (countries.isEmpty()) { throw new Exception("No SQL query returned"); }

            return sortCountriesByPopulation(countries);

        } catch (Exception e) {
            System.out.println("getCountriesByPopulation error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #18
     * This method returns a list of all Countries in the world by a Region.
     * @param region the Region you want to filter by.
     * @return list of all Country objects filtered by a given Region.
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
                    countries.add(mapCountry(results));
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
     * Report #19
     * This method returns a list of all countries in the world organized by population (largest to smallest),
     * @return list of all City objects sorted by population
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
     * This method returns a list of all Cities in the world by a Continent.
     * @param continent the Continent you want to filter by.
     * @return list of all City objects filtered by a given continent.
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
                            + " ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

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
     * Report #25
     * Returns the N most populous countries in the world.
     * @param N maximum number of countries to return
     * @return countries ordered by population from largest to smallest
     */
    public ArrayList<Country> getNPopulousCountries(int N)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException("N must be greater than zero");
        }

        String query = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country ORDER BY Population DESC LIMIT ?";
        ArrayList<Country> countries = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setInt(1, N);
            try (ResultSet results = statement.executeQuery())
            {
                while (results.next())
                {
                    countries.add(mapCountry(results));
                }
            }
            return countries;
        }
        catch (SQLException e)
        {
            System.out.println("getNPopulousCountries error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #26
     * Returns the N most populous countries in the world by a given continent.
     * @param N maximum number of countries to return
     * @param continent which continent to filter
     * @return countries ordered by population from largest to smallest
     */
    public ArrayList<Country> getNPopulousCountriesInContinent(int N, Continent continent)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException("N must be greater than zero");
        }
        if (continent == null || continent.getName() == null || continent.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Continent must not be empty");
        }

        String query = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country WHERE Continent = ? ORDER BY Population DESC LIMIT ?";
        ArrayList<Country> countries = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, continent.getName().trim());
            statement.setInt(2, N);
            ResultSet results = statement.executeQuery();
            while (results.next())
            {
                countries.add(mapCountry(results));
            }
            return countries;
        }
        catch (SQLException e)
        {
            System.out.println("getNPopulousCountriesInContinent error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report 27
     * This method inputs N and a region to view the top N populated countries in that region
     * @param N maximum number of countries to return
     * @param region which region to filter
     * @return list of N cities in that region
     */
    public ArrayList<Country> getNPopulousCountriesInRegion(int N, Region region)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException("N must be greater than zero");
        }
        if (region == null || region.getName() == null || region.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Region must not be empty");
        }

        String query = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";
        ArrayList<Country> countries = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, region.getName().trim());
            statement.setInt(2, N);
            ResultSet results = statement.executeQuery();
            while (results.next())
            {
                countries.add(mapCountry(results));
            }
            return countries;
        }
        catch (SQLException e)
        {
            System.out.println("getNPopulousCountriesInRegion error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Reports #21 and #30
     * This method returns a list of all Cities in the world by a Region.
     * @param region the Region you want to filter by.
     * @return list of all City objects filtered by a given Region.
     */
    public ArrayList<City> getCitiesInRegion(Region region)
    {
        if (region == null || region.getName() == null || region.getName().isBlank())
        {
            System.out.println("Please provide a region name.");
            return null;
        }
        if (con == null)
        {
            System.out.println("Cannot retrieve regional cities because the database connection is unavailable.");
            return null;
        }

        ArrayList<City> cities = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(REGIONAL_CITIES_QUERY))
        {
            statement.setString(1, region.getName().strip());
            try (ResultSet results = statement.executeQuery())
            {
                while (results.next())
                {
                    cities.add(new City(results.getInt("ID"), results.getString("city_name"),
                            results.getString("CountryCode"), results.getString("District"),
                            results.getInt("Population")));
                }
            }
            return cities;
        }
        catch (SQLException e)
        {
            System.out.println("Could not retrieve cities for the region: " + e.getMessage());
            return null;
        }
    }

    private void printRegionalCityReport(Region region)
    {
        if (con == null)
        {
            System.out.println("Cannot retrieve regional cities because the database connection is unavailable.");
            return;
        }
        try (PreparedStatement statement = con.prepareStatement(REGIONAL_CITIES_QUERY))
        {
            statement.setString(1, region.getName().strip());
            try (ResultSet results = statement.executeQuery())
            {
                if (!results.next())
                {
                    System.out.printf("No cities found for region '%s'.%n", region.getName());
                    return;
                }

                System.out.printf("%nCities in %s by population%n", region.getName());
                System.out.printf("%-35s %-52s %-20s %12s%n", "Name", "Country", "District", "Population");
                do
                {
                    System.out.printf("%-35s %-52s %-20s %,12d%n", results.getString("city_name"),
                            results.getString("country_name"), results.getString("District"),
                            results.getInt("Population"));
                } while (results.next());
            }
        }
        catch (SQLException e)
        {
            System.out.println("Could not retrieve cities for the region: " + e.getMessage());
        }
    }

    /**
     * Report 31: Returns all cities in a country ordered by population from largest to smallest.
     * @param country country whose cities should be returned
     * @return cities in the country ordered by population
     */
    public ArrayList<City> getCitiesInCountry(Country country)
    {
        if (country == null || country.getCode() == null || country.getCode().trim().isEmpty())
        {
            throw new IllegalArgumentException("A country with a valid code is required");
        }

        String query = "SELECT ID, Name, CountryCode, District, Population "
                + "FROM city WHERE CountryCode = ? ORDER BY Population DESC";
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, country.getCode().trim().toUpperCase(Locale.ROOT));
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
            System.out.println("getCitiesInCountry error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report 32
     * This method returns a list of all Cities in the world by a District.
     * @param district the District you want to filter by.
     * @return list of all City objects filtered by a given District.
     */
    public ArrayList<City> getCitiesInDistrict(District district)
    {
        if (district == null || district.getName() == null || district.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("District must not be empty");
        }

        String query = "SELECT ID, Name, CountryCode, District, Population "
                + "FROM city WHERE District = ? ORDER BY Population DESC";
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, district.getName().trim());
            ResultSet results = statement.executeQuery();
            while (results.next())
            {
                cities.add(new City(results.getInt("ID"),
                        results.getString("Name"),
                        results.getString("CountryCode"),
                        results.getString("District"),
                        results.getInt("Population")));
            }
            return cities;
        }
        catch (SQLException e)
        {
            System.out.println("getCitiesInDistrict error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Report 38
     * This method inputs N and a continent to view the top N populated cities in that continent
     * @param N maximum number of countries to return
     * @param continent which continent to filter
     * @return list of N cities in a given continent
     */
    // Reuse the continent report's population order and keep only the first N cities.
    public ArrayList<City> getNPopulousCitiesInContinent(int N, Continent continent)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException("N must be greater than zero");
        }
        if (continent == null || continent.getName() == null || continent.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Continent must not be empty");
        }

        ArrayList<City> cities = getCitiesInContinent(continent);
        if (cities == null)
        {
            return null;
        }
        return new ArrayList<>(cities.subList(0, Math.min(N, cities.size())));
    }

    /**
     * Report 39
     * This method inputs N and a Region to view the top N populated cities in that Region
     * @param N maximum number of countries to return
     * @param region which Region to filter
     * @return list of N cities in a given Region
     */
    public ArrayList<City> getNPopulousCitiesInRegion(int N, Region region)
    {
        try {

            String query =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city "
                            + "LEFT JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.Region LIKE '" + region.getName() + "'"
                            + " ORDER BY city.Population DESC";
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);


            ArrayList<City> cities = new ArrayList<>();

            int counter = 1;

            while (rset.next() && counter <= N)
            {
                City city = new City(   rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
                counter++;
            }
            return cities;

        } catch (Exception e) {
            System.out.println("getCitiesByPopulation error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Report 40
     * Returns the N most populous cities in a country.
     * @param N maximum number of cities to return
     * @param countryCode code of the country whose cities should be returned
     * @return cities ordered by population from largest to smallest
     */
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
     * Report 41
     * This method inputs N and a District to view the top N populated cities in that District
     * @param N maximum number of countries to return
     * @param district which District to filter
     * @return list of N cities in a given District
     */
    public ArrayList<City> getNPopulousCitiesInDistrict(int N, District district)
    {
        try {

            String query =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city "
                            + "WHERE city.District LIKE '" + district.getName() + "'"
                            + " ORDER BY city.Population DESC";
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);


            ArrayList<City> cities = new ArrayList<>();

            int counter = 1;

            while (rset.next() && counter <= N)
            {
                City city = new City(   rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
                counter++;
            }
            return cities;

        } catch (Exception e) {
            System.out.println("getCitiesByPopulation error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #42
     * View all capital cities in the world organized by population
     * @return list of all the cities in the world ordered by population
     */
    public ArrayList<City> getCapitalCities()
    {
        try {

            String query = "SELECT ID, city.Name, CountryCode, District, city.Population FROM country "
                    + "LEFT JOIN city ON city.ID = country.Capital ORDER BY Population DESC";
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

    /**
     * Report #43
     * Returns all capital cities in a continent ordered by population from largest to smallest.
     * @param continent continent whose capital cities should be returned
     * @return capital cities in the continent ordered by population
     */
    public ArrayList<City> getCapitalCitiesInContinent(Continent continent)
    {
        if (continent == null || continent.getName() == null || continent.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("A continent is required");
        }

        String query = "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                + "FROM country JOIN city ON city.ID = country.Capital "
                + "WHERE country.Continent = ? ORDER BY city.Population DESC";
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, continent.getName().trim());
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
            System.out.println("getCapitalCitiesInContinent error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #44
     * Returns all capital cities in a region ordered by population from largest to smallest.
     * @param region region whose capital cities should be returned
     * @return capital cities in the region ordered by population
     */
    public ArrayList<City> getCapitalCitiesInRegion(Region region)
    {
        if (region == null || region.getName() == null || region.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("A region is required");
        }

        String query = "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                + "FROM country JOIN city ON city.ID = country.Capital "
                + "WHERE country.Region = ? ORDER BY city.Population DESC";
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(query))
        {
            statement.setString(1, region.getName().trim());
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
            System.out.println("getCapitalCitiesInRegion error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #45
     * This method returns the top N populated capital cities in the world
     * @param N the number of capital cities you would like to return.
     * @return all the N capital cities in the world
     */
    public ArrayList<City> getNCapitalCities(int N)
    {
        try {

            String query = "SELECT ID, city.Name, CountryCode, District, city.Population FROM country "
                    + "LEFT JOIN city ON city.ID = country.Capital ORDER BY Population DESC";
            Statement statement = con.createStatement();
            ResultSet rset = statement.executeQuery(query);

            ArrayList<City> cities = new ArrayList<>();

            int counter = 1;

            while (rset.next() && counter <= N) {
                City city = new City(rset.getInt("ID"),
                        rset.getString("City.Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
                counter++;
            }

            return cities;
        }
        catch (Exception e)
        {
            System.out.println("ViewCitiesInRegion failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #46
     * This method shows the top N populated capital cities by a given continent in the world
     * @param N the number of capital cities you would like to return.
     * @param continent the continent you would like to filter by
     * @return all the N capital cities in the world by a given continent
     */
    public ArrayList<City> getNCapitalCitiesInContinent(int N, Continent continent)
    {
        try {

            String query =
                    "SELECT city.ID, city.Name, CountryCode, District, city.Population "
                            + "FROM country "
                            + "LEFT JOIN city ON city.ID = country.Capital "
                            + "WHERE country.Continent LIKE '" + continent.getName() + "'"
                            + " ORDER BY city.Population DESC";

            Statement statement = con.createStatement();
            ResultSet rset = statement.executeQuery(query);

            ArrayList<City> cities = new ArrayList<>();

            int counter = 1;

            while (rset.next() && counter <= N) {
                City city = new City(rset.getInt("ID"),
                        rset.getString("City.Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
                counter++;
            }

            return cities;
        }
        catch (Exception e)
        {
            System.out.println("ViewCitiesInRegion failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #47
     * This method shows the top N populated capital cities by a given Region in the world
     * @param N the number of capital cities you would like to return.
     * @param region the Region you would like to filter by
     * @return all the N capital cities in the world by a given Region
     */
    public ArrayList<City> getNCapitalCitiesInRegion(int N, Region region)
    {
        try {

            String query =
                    "SELECT city.ID, city.Name, CountryCode, District, city.Population "
                            + "FROM country "
                            + "LEFT JOIN city ON city.ID = country.Capital "
                            + "WHERE country.Region LIKE '" + region.getName() + "'"
                            + " ORDER BY city.Population DESC";

            Statement statement = con.createStatement();
            ResultSet rset = statement.executeQuery(query);

            ArrayList<City> cities = new ArrayList<>();

            int counter = 1;

            while (rset.next() && counter <= N) {
                City city = new City(rset.getInt("ID"),
                        rset.getString("City.Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population"));
                cities.add(city);
                counter++;
            }

            return cities;
        }
        catch (Exception e)
        {
            System.out.println("ViewCitiesInRegion failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Report #51
     * View the total population of the world
     * @return the total population of every country in the world
     */
    public long getTotalPopulation()
    {
        try
        {
            // Create string for SQL statement
            // Query from country to get the total population of everyone in the world
            String query =
                    "SELECT SUM(Population) AS total "
                            + "FROM country ";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            /*
                Using a 'long' instead of Int because a total population
                of a large dataset could exceed the standard limit of Int and
             */
            long totalPop = 0;

            while(rset.next()){
                totalPop = rset.getLong("total");
            }

            return totalPop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
        }
    }

    /**
     * Report #52
     * View the total population of a continent
     * @param continent the continent that you want to filter by.
     * @return the total population of the continent inputted
     */
    public long getTotalPopulationByContinent(Continent continent)
    {
        try
        {
            // Create string for SQL statement
            String query =
                    "SELECT SUM(Population) AS total "
                            + "FROM country "
                            + "WHERE country.Continent LIKE '" + continent.getName() + "'";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            /*
                Using a 'long' instead of Int because a total population
                of a large dataset could exceed the standard limit of Int and
             */
            long totalPop = 0;

            while(rset.next()){
                totalPop = rset.getLong("total");
            }

            return totalPop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
        }
    }

    // Report #48
    public void printPopulationBreakdownsByContinent()
    {
        String query = "SELECT country.Continent, "
                + "SUM(country.Population) AS total_population, "
                + "COALESCE(SUM(city_population), 0) AS urban_population "
                + "FROM country "
                + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS city_population "
                + "FROM city GROUP BY CountryCode) AS city_totals "
                + "ON country.Code = city_totals.CountryCode "
                + "GROUP BY country.Continent ORDER BY country.Continent";

        try
        {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            while(results.next())
            {
                String continent = results.getString("Continent");
                long total = results.getLong("total_population");
                long cityPopulation = results.getLong("urban_population");
                long nonCityPopulation = total - cityPopulation;

                double cityPercent = getPopulationPercentage(cityPopulation, total);
                double nonCityPercent = getPopulationPercentage(nonCityPopulation, total);

                System.out.println(continent + ": total population " + total
                        + ", living in cities " + cityPopulation + " (" + cityPercent + "%)"
                        + ", not living in cities " + nonCityPopulation
                        + " (" + nonCityPercent + "%)");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static double getPopulationPercentage(long population, long totalPopulation)
    {
        if(totalPopulation == 0)
        {
            return 0;
        }
        return population * 100.0 / totalPopulation;
    }

    // Report #57
    public void printLanguageStatistics()
    {
        String query = "SELECT countrylanguage.Language, "
                + "SUM(country.Population * countrylanguage.Percentage / 100) AS speakers "
                + "FROM countrylanguage JOIN country ON countrylanguage.CountryCode = country.Code "
                + "WHERE countrylanguage.Language IN "
                + "('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') "
                + "GROUP BY countrylanguage.Language ORDER BY speakers DESC";

        try
        {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);
            long worldPopulation = getTotalPopulation();

            while(results.next())
            {
                String language = results.getString("Language");
                long speakers = Math.round(results.getDouble("speakers"));
                double percentage = getPopulationPercentage(speakers, worldPopulation);

                System.out.println(language + ": " + speakers + " speakers ("
                        + percentage + "% of world population)");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    // Report #49
    public void printPopulationBreakdownsByRegion()
    {
        try
        {
            String regionQuery = "SELECT Region, SUM(Population) AS total_population "
                    + "FROM country GROUP BY Region ORDER BY Region";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(regionQuery);

            ArrayList<String> regions = new ArrayList<>();
            ArrayList<Long> totalPopulations = new ArrayList<>();

            while(results.next())
            {
                regions.add(results.getString("Region"));
                totalPopulations.add(results.getLong("total_population"));
            }

            String cityQuery = "SELECT COALESCE(SUM(city.Population), 0) AS city_population "
                    + "FROM city JOIN country ON city.CountryCode = country.Code "
                    + "WHERE country.Region = ?";
            PreparedStatement cityStatement = con.prepareStatement(cityQuery);

            for(int i = 0; i < regions.size(); i++)
            {
                String region = regions.get(i);
                long total = totalPopulations.get(i);

                cityStatement.setString(1, region);
                ResultSet cityResults = cityStatement.executeQuery();
                cityResults.next();
                long cityPopulation = cityResults.getLong("city_population");
                long nonCityPopulation = total - cityPopulation;

                double cityPercent = getPopulationPercentage(cityPopulation, total);
                double nonCityPercent = getPopulationPercentage(nonCityPopulation, total);

                System.out.println(region + ": total population " + total
                        + ", living in cities " + cityPopulation + " (" + cityPercent + "%)"
                        + ", not living in cities " + nonCityPopulation
                        + " (" + nonCityPercent + "%)");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    // Report #50
    public void printPopulationBreakdownsByCountry()
    {
        try
        {
            String countryQuery = "SELECT Code, Name, Population FROM country ORDER BY Name";
            Statement countryStatement = con.createStatement();
            ResultSet countries = countryStatement.executeQuery(countryQuery);

            String cityQuery = "SELECT COALESCE(SUM(Population), 0) AS city_population "
                    + "FROM city WHERE CountryCode = ?";
            PreparedStatement cityStatement = con.prepareStatement(cityQuery);

            while(countries.next())
            {
                String countryCode = countries.getString("Code");
                String countryName = countries.getString("Name");
                long total = countries.getLong("Population");

                cityStatement.setString(1, countryCode);
                ResultSet cityResults = cityStatement.executeQuery();
                cityResults.next();
                long cityPopulation = cityResults.getLong("city_population");
                long nonCityPopulation = total - cityPopulation;

                double cityPercent = getPopulationPercentage(cityPopulation, total);
                double nonCityPercent = getPopulationPercentage(nonCityPopulation, total);

                System.out.println(countryName + ": total population " + total
                        + ", living in cities " + cityPopulation + " (" + cityPercent + "%)"
                        + ", not living in cities " + nonCityPopulation
                        + " (" + nonCityPercent + "%)");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Report #53
     * View the total population of a Region
     * @param region the Region that you want to filter by.
     * @return the total population of the Region inputted
     */
    public long getTotalPopulationByRegion(Region region)
    {
        try
        {
            // Create string for SQL statement
            String query =
                    "SELECT SUM(Population) AS total "
                            + "FROM country "
                            + "WHERE country.Region LIKE '" + region.getName() + "'";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            /*
                Using a 'long' instead of Int because a total population
                of a large dataset could exceed the standard limit of Int and
             */
            long totalPop = 0;

            while(rset.next()){
                totalPop = rset.getLong("total");
            }

            return totalPop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
        }
    }

    /**
     * Report #54
     * View the total population of a Country
     * @param country the Country that you want to filter by.
     * @return the total population of the Country inputted
     */
    public long getTotalPopulationByCountry(Country country)
    {
        try
        {
            // Create string for SQL statement
            String query =
                    "SELECT SUM(Population) AS total "
                            + "FROM country "
                            + "WHERE country.Name LIKE '" + country.getName() + "'";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            /*
                Using a 'long' instead of Int because a total population
                of a large dataset could exceed the standard limit of Int and
             */
            long totalPop = 0;

            while(rset.next()){
                totalPop = rset.getLong("total");
            }

            return totalPop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
        }
    }

    /**
     * Report #55
     * View the total population of a district
     */
    public long getTotalPopulationByDistrict(District district)
    {
        try
        {
            // Create string for SQL statement
            String query =
                    "SELECT SUM(Population) AS total "
                            + "FROM country "
                            + "WHERE country.District LIKE '" + district.getName() + "'";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            /*
                Using a 'long' instead of Int because a total population
                of a large dataset could exceed the standard limit of Int and
             */
            long totalPop = 0;

            while(rset.next()){
                totalPop = rset.getLong("total");
            }

            return totalPop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
        }
    }

    /**
     * Report #55
     * View the total population of a City
     * @param city the City that you want to filter by.
     * @return the total population of the City inputted
     */
    public long getTotalPopulationByCity(City city)
    {
        try
        {
            // Create string for SQL statement
            String query =
                    "SELECT SUM(country.Population) AS total "
                            + "FROM city "
                            + "LEFT JOIN country ON country.Code = city.CountryCode "
                            + "WHERE city.Name LIKE '" + city.getName() + "'";
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            /*
                Using a 'long' instead of Int because a total population
                of a large dataset could exceed the standard limit of Int and
             */
            long totalPop = 0;

            while(rset.next()){
                totalPop = rset.getLong("total");
            }

            return totalPop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
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
