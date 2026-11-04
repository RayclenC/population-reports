package com.napier.sem;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppUnitTest
{
    static App app;

    @BeforeAll
    static void init(){
        app = new App();
    }


    @Test
    void printCitiesTest(){

        // Testing when the array is null
        ArrayList<City> cities = null;
        app.printCities(cities);

        // Testing when the array list is empty
        cities = new ArrayList<>();
        app.printCities(cities);

        // Testing when the city is populated
        City testCity = new City();
        testCity.setId(460);
        testCity.setName("Edinburgh");
        testCity.setCountryCode("GBR");
        testCity.setDistrict("District");
        testCity.setPopulation(450180);
        cities.add(testCity);

        app.printCities(cities);

        // Testing edge cases of Integers

        // Resetting the array and city object
        cities = new ArrayList<>();
        testCity = new City();

        testCity.setId(0);
        testCity.setPopulation(0);

        cities.add(testCity);

        app.printCities(cities);


    }

}
