package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {

        app = new App();

        app.Connect("localhost:33060", 30000);
    }

    // Integrated test methods go here


    @Test
    void testAddCity()
    {
        City testCity = new City();

        testCity.setId(460);
        testCity.setName("Edinburgh");
        testCity.setCountryCode("GBR");
        testCity.setDistrict("District");
        testCity.setPopulation(450180);

        assertEquals(testCity.getId(),460);
        assertEquals(testCity.getName(),"Edinburgh");
        assertEquals(testCity.getCountryCode(),"GBR");
        assertEquals(testCity.getDistrict(),"District");
        assertEquals(testCity.getPopulation(),450180);


    }

    @Test
    void testAddContinent()
    {
        Continent testContinent = new Continent("Asia");

        assertEquals(testContinent.getName(),"Asia");
    }

    @Test
    void testAddCountry()
    {
        Country testCountry = new Country("North America", "North America", "Ottowa");

        testCountry.SetCode("Can");
        testCountry.SetName("Canada");
        testCountry.SetPopulation(31147000);

        assertEquals(testCountry.GetCode(),"Can");
        assertEquals(testCountry.GetName(),"Canada");
//        assertEquals(testCountry.GetContinent(),"North America");
//        assertEquals(testCountry.GetRegion(),"North America");
        assertEquals(testCountry.GetPopulation(),31147000);
        assertEquals(testCountry.GetCapital(),"Ottowa");


    }

    @Test
    void testAddRegion()
    {
        // In progress
        Country testCountry = new Country("North America", "North America", "Ottowa");
        Region testRegion = new Region("Belgium", testCountry);
    }


    @Test
    void testGetCitiesInContinent()
    {
        Continent continent = new Continent("Asia");

        app.getCitiesInContinent(continent);

        // Testing if the city is entered is empty
        continent = null;

        app.getCitiesInContinent(continent);


    }


}