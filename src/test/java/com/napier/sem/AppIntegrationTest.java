package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
}
