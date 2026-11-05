package com.napier.sem;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AppUnitTest
{
    static App app;

    @BeforeAll
    static void init(){
        app = new App();
    }


    /**
     * Testing when the array is null
     */
    @Test
    void printCitiesTestNull(){

        ArrayList<City> cities = null;
        app.printCities(cities);
    }

    /**
     *  Testing when the array list is empty
     */
    @Test
    void printCitiesTestEmpty(){

        ArrayList<City> cities = new ArrayList<>();
        app.printCities(cities);
    }

    /**
     *  Testing when the city is populated
     */
    @Test
    void printCitiesTestPopulated(){

        ArrayList<City> cities = new ArrayList<>();

        // Populating the city class
        City testCity = new City(460,
                "Edinburgh",
                "GBR",
                "District",
                450180);

        // Adding the populated object to the array
        cities.add(testCity);

        app.printCities(cities);

    }

    /**
     *  Testing edge cases of Integers
     */
    @Test
    void printCitiesTestEdgeCase(){

        ArrayList<City> cities = new ArrayList<>();
        City testCity = new City(460,
                "Edinburgh",
                "GBR",
                "District",
                450180);

        testCity.setId(0);
        testCity.setPopulation(0);

        cities.add(testCity);

        app.printCities(cities);

    }

    @Test
    void sortCountriesByPopulationOrdersLargestToSmallest()
    {

        Country china = new Country("CHN","China","Asia","Eastern Asia",9572900.00,"Beijing");

        Country india = new Country("IND", "India", "Asia", "Southern and Central Asia",3287263, "New Delhi");

        Country iceland = new Country("ISL","Iceland","Europe","Nordic Countries", 396500, "Reykjavik");

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(iceland);
        countries.add(china);
        countries.add(india);

        ArrayList<Country> sortedCountries = App.sortCountriesByPopulation(countries);

        assertEquals("China", sortedCountries.get(0).getName());
        assertEquals("India", sortedCountries.get(1).getName());
        assertEquals("Iceland", sortedCountries.get(2).getName());
        assertTrue(sortedCountries.get(0).getPopulation()
                >= sortedCountries.get(1).getPopulation());
        assertTrue(sortedCountries.get(1).getPopulation()
                >= sortedCountries.get(2).getPopulation());
    }

    @Test
    void formatCountryIncludesCodeNameAndPopulation()
    {
        Country country = new Country("CHN","China","Asia","Eastern Asia",1277558000,"Beijing");

        assertEquals("CHN | China | 1277558000", App.formatCountry(country));
    }

}