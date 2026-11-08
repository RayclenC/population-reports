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
     * to mirror the "city" database table.
     */
    @Test
    void testAddCity() {

        // Testing Constructors
        City testCity = new City(460,
                "Edinburgh",
                "GBR",
                "Scotland",
                450180);

        assertEquals(460, testCity.getId());
        assertEquals("Edinburgh", testCity.getName());
        assertEquals("GBR", testCity.getCountryCode());
        assertEquals("Scotland", testCity.getDistrict());
        assertEquals(450180, testCity.getPopulation());

        // Testing Setters

        testCity.setId(5);
        testCity.setName("Amsterdam");
        testCity.setCountryCode("NLD");
        testCity.setDistrict("Noord-Holland");
        testCity.setPopulation(731200.0);

        assertEquals(5, testCity.getId());
        assertEquals("Amsterdam", testCity.getName());
        assertEquals("NLD", testCity.getCountryCode());
        assertEquals("Noord-Holland", testCity.getDistrict());
        assertEquals(731200.0, testCity.getPopulation());

        // Testing toString

        assertEquals("City{" +
                "id=" + 5 +
                ", name='" + "Amsterdam" + '\'' +
                ", CountryCode='" + "NLD" + '\'' +
                ", district='" + "Noord-Holland" + '\'' +
                ", population=" + 731200.0 +
                '}', testCity.toString());

    }

    @Test
    void testAddContinent() {

        // Testing Constructors
        Continent testContinent = new Continent("Asia");

        assertEquals("Asia", testContinent.getName());


        // Testing Setters
        testContinent.setName("Europe");

        assertEquals("Europe", testContinent.getName());

        // Testing toString

        assertEquals("Continent{" +
                "name='" + "Europe" + '\'' +
                '}', testContinent.toString());

    }

    /**
     * Tests the Country Class. Making sure that a valid country can be created
     * to mirror the "country" database table.
     */
    @Test
    void testAddCountry() {

        // Testing Constructors
        Country testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");

        assertEquals("CAN", testCountry.getCode());
        assertEquals("Canada", testCountry.getName());
        assertEquals("North America", testCountry.getContinent().getName());
        assertEquals("North America", testCountry.getRegion().getName());
        assertEquals(31147000, testCountry.getPopulation());
        assertEquals("Ottowa",   testCountry.getCapital());

        // Testing Setters

        testCountry.setCode("GBR");
        testCountry.setName("United Kingdom");
        testCountry.setPopulation(1296830.0);
        testCountry.setCapital("London");

        // Continent and Region do not need to be tested as they are separate classes
        assertEquals("GBR", testCountry.getCode());
        assertEquals("United Kingdom", testCountry.getName());
        assertEquals(1296830.0, testCountry.getPopulation());
        assertEquals("London",   testCountry.getCapital());

        // Testing toString

        assertEquals("Country{" +
                "code='" + "GBR" + '\'' +
                ", name='" + "United Kingdom" + '\'' +
                ", continent=" + testCountry.getContinent() +
                ", region=" + testCountry.getRegion() +
                ", population=" + 1296830.0 +
                ", capital='" + "London" + '\'' +
                '}', testCountry.toString());

    }

    @Test
    void testAddDistrict() {

        // Testing Constructors
        District testDistrict = new District("England");

        assertEquals("England", testDistrict.getName());

        // Testing Setters

        testDistrict.setName("Scotland");

        assertEquals("Scotland", testDistrict.getName());

        // Testing toString

        assertEquals("District{" +
                "name='" + "Scotland" + '\'' +
                '}', testDistrict.toString());

    }

    @Test
    void testAddRegion() {

        // Testing Constructors
        Region testRegion = new Region("Caribbean");

        assertEquals("Caribbean", testRegion.getName());

        // Testing Setters

        testRegion.setName("Southern Europe");

        assertEquals("Southern Europe", testRegion.getName());

        // Testing toString

        assertEquals("Region{" +
                "name='" + "Southern Europe" + '\'' +
                '}', testRegion.toString());
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
                "Scotland",
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
    void sortCountriesInRegionByPopulationOrdersLargestToSmallest()
    {
        Country jamaica = new Country("JAM", "Jamaica", "North America", "Caribbean", 2583000, "Kingston");
        Country cuba = new Country("CUB", "Cuba", "North America", "Caribbean", 11201000, "La Habana");
        Country bahamas = new Country("BHS", "Bahamas", "North America", "Caribbean", 307000, "Nassau");

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(jamaica);
        countries.add(cuba);
        countries.add(bahamas);

        ArrayList<Country> sortedCountries = App.sortCountriesByPopulation(countries);

        assertEquals("Cuba", sortedCountries.get(0).getName());
        assertEquals("Jamaica", sortedCountries.get(1).getName());
        assertEquals("Bahamas", sortedCountries.get(2).getName());
        assertEquals("Caribbean", sortedCountries.get(0).getRegion().getName());
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