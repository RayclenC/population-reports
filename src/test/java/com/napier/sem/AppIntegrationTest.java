package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    void GetCitiesInContinentTestAssert()
    {
        Continent continent = new Continent("Asia");

        //ArrayList<City> city = app.GetCitiesInContinent(continent);
    }


}