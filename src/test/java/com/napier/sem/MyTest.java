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
    void PrintCitiesTestNormal(){
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.SetName("London");
        cities.add(city);

        app.PrintCities(cities);
    }

    @Test
    void formatCountryTest()
    {
        Country country = new Country();
        country.SetCode("CHN");
        country.SetName("China");
        country.SetPopulation(1277558000);

        assertEquals("CHN | China | 1277558000", App.formatCountry(country));
    }

    @Test
    void sortCountriesByPopulationOrdersLargestToSmallest()
    {
        Country china = new Country();
        china.SetName("China");
        china.SetPopulation(1_400_000_000);

        Country india = new Country();
        india.SetName("India");
        india.SetPopulation(1_300_000_000);

        Country iceland = new Country();
        iceland.SetName("Iceland");
        iceland.SetPopulation(400_000);

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(iceland);
        countries.add(china);
        countries.add(india);

        ArrayList<Country> sortedCountries = App.sortCountriesByPopulation(countries);

        assertEquals("China", sortedCountries.get(0).GetName());
        assertEquals("India", sortedCountries.get(1).GetName());
        assertEquals("Iceland", sortedCountries.get(2).GetName());
        assertTrue(sortedCountries.get(0).GetPopulation()
                >= sortedCountries.get(1).GetPopulation());
        assertTrue(sortedCountries.get(1).GetPopulation()
                >= sortedCountries.get(2).GetPopulation());
    }
}
