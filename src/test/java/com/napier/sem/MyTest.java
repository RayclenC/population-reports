package com.napier.sem;

import org.junit.jupiter.api.*;

import java.util.ArrayList;


class MyTest
{
    static App app;

    @BeforeAll
    static void init(){
        app = new App();
    }


    @Test
    void PrintCitiesTestNull()
    {
        ArrayList<City> cities = null;
        app.printCities(cities);
    }

    @Test
    void PrintCitiesTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printCities(cities);
    }

    @Test
    void PrintCitiesTestNormal(){
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setName("London");
        cities.add(city);

        app.printCities(cities);
    }
}
