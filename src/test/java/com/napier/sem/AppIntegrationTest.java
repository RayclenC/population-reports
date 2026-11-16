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

        testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");
        testCountry.setCode("Can");
        testCountry.setName("Canada");
        testCountry.setPopulation(31147000);
    }

    // Integrated test methods go here


    /**
     * Tests the City Class. Making sure that a valid city can be created
     * to mirror the 'city' database table.
     */
    @Test
    void testAddCity() {
        assertEquals(460, testCity.getId());
        assertEquals("Edinburgh", testCity.getName());
        assertEquals("GBR", testCity.getCountryCode());
        assertEquals("District", testCity.getDistrict());
        assertEquals(450180, testCity.getPopulation());
    }

    @Test
    void testAddContinent() {
        assertEquals("Asia", testContinent.getName());
    }

    @Test
    void testGetNPopulousCountriesInRegion() {
        assertEquals("Ethiopia", app.getNPopulousCountriesInRegion(5, testRegion).get(0).getName());
    }

    /**
     * Tests the Country Class. Making sure that a valid country can be created
     * to mirror the 'country' database table.
     */
    @Test
    void testAddCountry() {
        assertEquals("Can", testCountry.getCode());
        assertEquals("Canada", testCountry.getName());
        assertEquals("North America", testCountry.getContinent().getName());
        assertEquals("North America", testCountry.getRegion().getName());
        assertEquals(31147000, testCountry.getPopulation());
        assertEquals("Ottowa",   testCountry.getCapital());
    }

    @Test
    void testAddRegion() {
        // In progress
        Country testCountry = new Country("North America", "North America", "Ottowa");
        Region testRegion = new Region("Belgium", testCountry);
    }


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
        if (app.getCitiesInPopulationSizeOrder() instanceof ArrayList<City>) {
            assertNotEquals(app.getCitiesInPopulationSizeOrder(), null);
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
