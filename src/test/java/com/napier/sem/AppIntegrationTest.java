package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        City city = new City();

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