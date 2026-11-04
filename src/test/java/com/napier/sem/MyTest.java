package com.napier.sem;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


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
        app.PrintCities(cities);
    }

    @Test
    void PrintCitiesTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.PrintCities(cities);
    }

    @Test
    void PrintCitiesTestNormal(){}
    ArrayList<City> cities = new ArrayList<>();

    public ArrayList<City> getCities() {
        return cities;
    }
}
