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

        assertEquals(460, testCity.getId());
        assertEquals("Edinburgh", testCity.getName());
        assertEquals("GBR", testCity.getCountryCode());
        assertEquals("District", testCity.getDistrict());
        assertEquals(450180, testCity.getPopulation());


    }

    @Test
    void testAddContinent() {
        Continent testContinent = new Continent("Asia");

        assertEquals("Asia", testContinent.getName());
    }

    /**
     * Tests the Country Class. Making sure that a valid country can be created
     * to mirror the 'country' database table.
     */
    @Test
    void testAddCountry() {
        Country testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");

        testCountry.setCode("Can");
        testCountry.setName("Canada");
        testCountry.setPopulation(31147000);

        assertEquals("Can", testCountry.getCode());
        assertEquals("Canada", testCountry.getName());
        assertEquals("North America", testCountry.getContinent().getName());
        assertEquals("North America", testCountry.getRegion().getName());
        assertEquals(31147000, testCountry.getPopulation());
        assertEquals("Ottowa",   testCountry.getCapital());


    }

    @Test
    void testAddRegion() {
        // In progress
        Country testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");
        Region testRegion = new Region("Belgium");
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