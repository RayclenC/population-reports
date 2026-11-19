package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *<p>
 *     The AppIntegrationTest class is where our Integration tests (Tests against the database) are carried out.
 *
 *</p>
 */

public class AppIntegrationTest {
    static App app;
    static City testCity;
    static Continent testContinent;
    static Continent testContinentIncorrect;
    static Region testRegion;
    static Country testCountry;
    static District testDistrict;

    /**
     * Initializing variables and establishing a connection to the database.
     * This is then reused throughout the testing.
     */
    @BeforeAll
    static void init() {

        app = new App();

        // Connecting to the database
        app.Connect("localhost:33060", 30000);

        testCity = new City(460,
                "Edinburgh",
                "GBR",
                "District",
                450180);


        // Creating Test objects of each class to be re-used instead of creating duplicate instances.
        testContinent = new Continent("Asia");

        testRegion = new Region("Eastern Africa");

        testDistrict = new District("England");

        testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");
    }

    // Integrated test methods go here

    /**
     * Verifies that {@link App#getCitiesInContinent(Continent)} returns a valid list of cities
     */
    @Test
    void testGetCitiesInContinent() {
        ArrayList<City> cities = app.getCitiesInContinent(testContinent);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

    }

    @Test
    void getNPopulousCitiesInContinentReturnsTopCitiesInPopulationOrder()
    {
        ArrayList<City> allCities = app.getCitiesInContinent(testContinent);
        ArrayList<City> topCities = app.getNPopulousCitiesInContinent(5, testContinent);

        assertNotNull(allCities);
        assertNotNull(topCities);
        assertEquals(5, topCities.size());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (int i = 0; i < topCities.size(); i++)
        {
            assertEquals(allCities.get(i).getId(), topCities.get(i).getId());
            assertTrue(topCities.get(i).getPopulation() <= previousPopulation);
            previousPopulation = topCities.get(i).getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getNPopulousCitiesInDistrict(int, District)}
     * returns a valid list of cities of the N length provided
     */
    @Test
    void testGetNPopulousCitiesInDistrict() {

        ArrayList<City> cities = app.getNPopulousCitiesInDistrict(7, testDistrict);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());
    }

    /**
     * Verifies that {@link App#getCapitalCities()} returns a valid list of cities
     */
    @Test
    void testGetCapitalCities() {
        ArrayList<City> cities = app.getCapitalCities();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    /**
     * Verifies that {@link App#getNCapitalCities(int N)} returns a valid list of cities of the N length provided
     */
    @Test
    void testGetNCapitalCities() {
        ArrayList<City> cities = app.getNCapitalCities(7);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());

    }

    /**
     * Verifies that {@link App#getNCapitalCitiesInContinent(int, Continent)} returns a valid list of cities of the N length provided
     */
    @Test
    void testGetNCapitalCitiesInContinent() {

        ArrayList<City> cities = app.getNCapitalCitiesInContinent(7, testContinent);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());
    }

    /**
     * Verifies that {@link App#getCapitalCitiesInContinent(Continent)} returns all the capital cities in descending order.
     */
    @Test
    void testGetCapitalCitiesInContinent()
    {
        ArrayList<City> cities = app.getCapitalCitiesInContinent(testContinent);

        assertNotNull(cities);
        assertTrue(cities.size() > 7);
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (City city : cities)
        {
            assertNotNull(city.getCountryCode());
            assertTrue(city.getPopulation() <= previousPopulation);
            previousPopulation = city.getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getCapitalCitiesInRegion(Region)} returns all the capital cities in descending order.
     */
    @Test
    void testGetCapitalCitiesInRegion()
    {
        ArrayList<City> cities = app.getCapitalCitiesInRegion(testRegion);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (City city : cities)
        {
            assertNotNull(city.getCountryCode());
            assertTrue(city.getPopulation() <= previousPopulation);
            previousPopulation = city.getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getNCapitalCitiesInRegion(int, Region)} returns a valid list of cities of the N length provided
     */
    @Test
    void testGetNCapitalCitiesInRegion() {

        ArrayList<City> cities = app.getNCapitalCitiesInRegion(7, testRegion);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());
    }

    /**
     * Verifies that {@link App#getCitiesInRegion(Region)} returns a valid list of cities
     */
    @Test
    void testGetCitiesInRegion() {
        ArrayList<City> cities = app.getCitiesInRegion(testRegion);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    /**
     * Verifies that {@link App#getCitiesInDistrict(District)} returns all the cities in descending order.
     */
    @Test
    void testGetCitiesInDistrict()
    {
        ArrayList<City> cities = app.getCitiesInDistrict(testDistrict);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (City city : cities)
        {
            assertEquals(testDistrict.getName(), city.getDistrict());
            assertTrue(city.getPopulation() <= previousPopulation);
            previousPopulation = city.getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getCitiesInCountry(Country)} returns all the cities in descending order.
     */
    @Test
    void testGetCitiesInCountry()
    {
        ArrayList<City> cities = app.getCitiesInCountry(testCountry);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (City city : cities)
        {
            assertEquals("CAN", city.getCountryCode());
            assertTrue(city.getPopulation() <= previousPopulation);
            previousPopulation = city.getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getCountriesInContinent(Continent)} returns a valid list of countries
     */
    @Test
    void testGetCountriesInContinent(){
        ArrayList<Country> countries = app.getCountriesInContinent(testContinent);

        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        testContinentIncorrect = new Continent("Mableh");
        assertNull(app.getCountriesInContinent(testContinentIncorrect));
    }

    /**
     * Verifies that {@link App#getCitiesInPopulationSizeOrder()} returns a valid list of cities in the correct size order
     */
    @Test
    void testGetCities() {
        // testing to see if the method to return by size order returns an array of populated data.
        if (app.getCitiesInPopulationSizeOrder() != null) {
            assertNotEquals(null, app.getCitiesInPopulationSizeOrder());
        } else {
            throw new IllegalArgumentException("Type Error: Expected an ArrayList but got " + app.getCitiesInPopulationSizeOrder().getClass().getSimpleName());
        }
    }

    /**
     * Verifies that {@link App#getCountriesInRegion(Region)} returns all the countries in descending order.
     */
    @Test
    void testGetCountriesInRegion()
    {
        ArrayList<Country> countries = app.getCountriesInRegion(testRegion);

        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (Country country : countries)
        {
            assertEquals("Eastern Africa", country.getRegion().getName());
            assertTrue(country.getPopulation() <= previousPopulation);
            previousPopulation = country.getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getCountriesByPopulation} returns all the countries in descending population order.
     */
    @Test
    void testGetCountriesByPopulation()
    {
        ArrayList<Country> countries = app.getCountriesByPopulation();

        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        double previousPopulation = Double.POSITIVE_INFINITY;
        for (Country country : countries)
        {
            assertNotNull(country.getCode());
            assertNotNull(country.getName());
            assertTrue(country.getPopulation() <= previousPopulation);
            previousPopulation = country.getPopulation();
        }
    }

    /**
     * Verifies that {@link App#getTotalPopulation()} returns a positive integer.
     */
    @Test
    void testGetTotalPopulation()
    {
        long totalPop = app.getTotalPopulation();

        // Checking to see if there is a valid population
        assertNotEquals(0, totalPop);

    }

    /**
     * Verifies that {@link App#getTotalPopulationByContinent(Continent)} returns a positive integer.
     */
    @Test
    void testGetTotalPopulationByContinent()
    {
        long continentPop = app.getTotalPopulationByContinent(testContinent);

        // Checking to see if there is a valid population
        assertNotEquals(0, continentPop);

    }

    @Test
    void printPopulationBreakdownsByContinentDisplaysPopulationAndPercentages()
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        try
        {
            System.setOut(new PrintStream(output));
            app.printPopulationBreakdownsByContinent();
        }
        finally
        {
            System.setOut(originalOutput);
        }

        String report = new String(output.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(report.contains("Asia: total population"));
        assertTrue(report.contains("living in cities"));
        assertTrue(report.contains("not living in cities"));
        assertTrue(report.contains("%"));
    }

    @Test
    void printPopulationBreakdownsByRegionDisplaysPopulationAndPercentages()
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        try
        {
            System.setOut(new PrintStream(output));
            app.printPopulationBreakdownsByRegion();
        }
        finally
        {
            System.setOut(originalOutput);
        }

        String report = new String(output.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(report.contains("Eastern Africa: total population"));
        assertTrue(report.contains("living in cities"));
        assertTrue(report.contains("not living in cities"));
        assertTrue(report.contains("%"));
    }

    @Test
    void printPopulationBreakdownsByCountryDisplaysPopulationAndPercentages()
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        try
        {
            System.setOut(new PrintStream(output));
            app.printPopulationBreakdownsByCountry();
        }
        finally
        {
            System.setOut(originalOutput);
        }

        String report = new String(output.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(report.contains("Canada: total population"));
        assertTrue(report.contains("living in cities"));
        assertTrue(report.contains("not living in cities"));
        assertTrue(report.contains("%"));
    }

    @Test
    void printLanguageStatisticsShowsAllRequestedLanguages()
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        try
        {
            System.setOut(new PrintStream(output));
            app.printLanguageStatistics();
        }
        finally
        {
            System.setOut(originalOutput);
        }

        String report = new String(output.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(report.contains("Chinese:"));
        assertTrue(report.contains("English:"));
        assertTrue(report.contains("Hindi:"));
        assertTrue(report.contains("Spanish:"));
        assertTrue(report.contains("Arabic:"));
        assertTrue(report.contains("speakers ("));
        assertTrue(report.contains("% of world population)"));
    }

    /**
     * Verifies that {@link App#getTotalPopulationByRegion(Region)} returns a positive integer.
     */
    @Test
    void testGetTotalPopulationByRegion()
    {
        long regionPop = app.getTotalPopulationByRegion(testRegion);

        // Checking to see if there is a valid population
        assertNotEquals(0, regionPop);

    }

    /**
     * Verifies that {@link App#getTotalPopulationByCountry(Country)} returns a positive integer.
     */
    @Test
    void testGetTotalPopulationByCountry()
    {
        long countryPop = app.getTotalPopulationByCountry(testCountry);

        // Checking to see if there is a valid population
        assertNotEquals(0, countryPop);

    }

    /**
     * Verifies that {@link App#getTotalPopulationByDistrict(District)} returns a positive integer.
     */
    @Test
    void testGetTotalPopulationByDistrict() {
        long districtPop = app.getTotalPopulationByDistrict(testDistrict);

        // Checking to see if there is a valid population
        assertNotEquals(0, districtPop);

    }

    /**
     * Verifies that {@link App#getTotalPopulationByCity(City)} returns a positive integer.
     */
    @Test
    void testGetTotalPopulationByCity() {
        long cityPop = app.getTotalPopulationByCity(testCity);

        // Checking to see if there is a valid population
        assertNotEquals(0, cityPop);

    }

}
