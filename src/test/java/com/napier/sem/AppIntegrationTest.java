package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 *<p>
 *     The AppIntegrationTest class is where our Integration tests (Tests against the database) are carried out.
 *
 *</p>
 */

public class AppIntegrationTest {
    static App app;

    @BeforeAll
    static void init() {

        app = new App();

        app.Connect("localhost:33060", 30000);
    }

    // Integrated test methods go here


    /**
     * Tests the City Class. Making sure that a valid city can be created
     * to mirror the 'city' database table.
     */
    @Test
    void testAddCity() {
        City testCity = new City(460,
                "Edinburgh",
                "GBR",
                "District",
                450180);

        assertEquals(testCity.getId(), 460);
        assertEquals(testCity.getName(), "Edinburgh");
        assertEquals(testCity.getCountryCode(), "GBR");
        assertEquals(testCity.getDistrict(), "District");
        assertEquals(testCity.getPopulation(), 450180);


    }

    @Test
    void testAddContinent() {
        Continent testContinent = new Continent("Asia");

        assertEquals(testContinent.getName(), "Asia");
    }

    /**
     * Tests the Country Class. Making sure that a valid country can be created
     * to mirror the 'country' database table.
     */
    @Test
    void testAddCountry() {
        Country testCountry = new Country("North America", "North America", "Ottowa");

        testCountry.setCode("Can");
        testCountry.setName("Canada");
        testCountry.setPopulation(31147000);

        assertEquals(testCountry.getCode(), "Can");
        assertEquals(testCountry.getName(), "Canada");
//        assertEquals(testCountry.GetContinent(),"North America");
//        assertEquals(testCountry.getRegion(),"North America");
        assertEquals(testCountry.getPopulation(), 31147000);
        assertEquals(testCountry.getCapital(), "Ottowa");


    }

    @Test
    void testAddRegion() {
        // In progress
        Country testCountry = new Country("North America", "North America", "Ottowa");
        Region testRegion = new Region("Belgium", testCountry);
    }


    @Test
    void testGetCitiesInContinent() {
        Continent continent = new Continent("Asia");

        app.getCitiesInContinent(continent);

        // Testing if the city is entered is empty
        continent = null;

        app.getCitiesInContinent(continent);


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
}
