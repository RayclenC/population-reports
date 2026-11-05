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
        City testCity = new City();

        // Populating the city class
        testCity.setId(460);
        testCity.setName("Edinburgh");
        testCity.setCountryCode("GBR");
        testCity.setDistrict("District");
        testCity.setPopulation(450180);

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
        City testCity = new City();

        testCity.setId(0);
        testCity.setPopulation(0);

        cities.add(testCity);

        app.printCities(cities);

    }

    @Test
    void sortCountriesByPopulationOrdersLargestToSmallest()
    {
        Country china = new Country("Asia", "Eastern Asia", "Beijing");
        china.SetName("China");
        china.SetPopulation(1_400_000_000);

        Country india = new Country("Asia", "Southern and Central Asia", "New Delhi");
        india.SetName("India");
        india.SetPopulation(1_300_000_000);

        Country iceland = new Country("Europe", "Nordic Countries", "Reykjavik");
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

    @Test
    void formatCountryIncludesCodeNameAndPopulation()
    {
        Country country = new Country("Asia", "Eastern Asia", "Beijing");
        country.SetCode("CHN");
        country.SetName("China");
        country.SetPopulation(1_277_558_000);

        assertEquals("CHN | China | 1277558000", App.formatCountry(country));
    }

}