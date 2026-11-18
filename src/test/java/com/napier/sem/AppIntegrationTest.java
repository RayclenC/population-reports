package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

    @BeforeAll
    static void init() {

        app = new App();

        app.Connect("localhost:33060", 30000);

        testCity = new City(460,
                "Edinburgh",
                "GBR",
                "District",
                450180);

        testContinent = new Continent("Asia");

        testRegion = new Region("Eastern Africa");

        testDistrict = new District("England");

        testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");
    }

    // Integrated test methods go here


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

    @Test
    void testGetNPopulousCitiesInDistrict() {

        ArrayList<City> cities = app.getNPopulousCitiesInDistrict(7, testDistrict);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());
    }

    @Test
    void testGetCapitalCities() {
        ArrayList<City> cities = app.getCapitalCities();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    void testGetNCapitalCities() {
        ArrayList<City> cities = app.getNCapitalCities(7);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());

    }

    @Test
    void testGetNCapitalCitiesInContinent() {

        ArrayList<City> cities = app.getNCapitalCitiesInContinent(7, testContinent);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());
    }

    @Test
    void testGetNCapitalCitiesInRegion() {

        ArrayList<City> cities = app.getNCapitalCitiesInRegion(7, testRegion);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        // Testing to see if the N param works
        assertEquals(7, cities.size());
    }

    @Test
    void testGetCitiesInRegion() {
        ArrayList<City> cities = app.getCitiesInRegion(testRegion);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    void getCitiesInDistrictReturnsCitiesInDescendingPopulationOrder()
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

    @Test
    void getCitiesInCountryReturnsAllCitiesInDescendingPopulationOrder()
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

    @Test
    void testGetCountriesInContinent(){
        ArrayList<Country> countries = app.getCountriesInContinent(testContinent);

        assertNotNull(countries);
        assertFalse(countries.isEmpty());
    }

    @Test
    void testGetCountriesInContinentInvalidContinent(){
        testContinentIncorrect = new Continent("Mableh");
        app.getCountriesInContinent(testContinentIncorrect);
    }

    @Test
    void testGetCities() {
        // testing to see if the method to return by size order returns an array of populated data.
        if (app.getCitiesInPopulationSizeOrder() != null) {
            assertNotEquals(null, app.getCitiesInPopulationSizeOrder());
        } else {
            throw new IllegalArgumentException("Type Error: Expected an ArrayList but got " + app.getCitiesInPopulationSizeOrder().getClass().getSimpleName());
        }
    }

    @Test
    void getCountriesInRegionReturnsCountriesInDescendingPopulationOrder()
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

    @Test
    void getCountriesByPopulationReturnsCountriesInDescendingPopulationOrder()
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


    @Test
    void testGetTotalPopulation()
    {
        long totalPop = app.getTotalPopulation();

        // Checking to see if there is a valid population
        assertNotEquals(0, totalPop);

    }

    @Test
    void testGetTotalPopulationByContinent()
    {
        long continentPop = app.getTotalPopulationByContinent(testContinent);

        // Checking to see if there is a valid population
        assertNotEquals(0, continentPop);

    }

    @Test
    void testGetTotalPopulationByRegion()
    {
        long regionPop = app.getTotalPopulationByRegion(testRegion);

        // Checking to see if there is a valid population
        assertNotEquals(0, regionPop);

    }

    @Test
    void testGetTotalPopulationByCountry()
    {
        long countryPop = app.getTotalPopulationByCountry(testCountry);

        // Checking to see if there is a valid population
        assertNotEquals(0, countryPop);

    }

    @Test
    void testGetTotalPopulationByDistrict() {
        long districtPop = app.getTotalPopulationByDistrict(testDistrict);

        // Checking to see if there is a valid population
        assertNotEquals(0, districtPop);

    }

    @Test
    void testGetTotalPopulationByCity() {
        long cityPop = app.getTotalPopulationByCity(testCity);

        // Checking to see if there is a valid population
        assertNotEquals(0, cityPop);

    }

}
