package com.napier.sem;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


class AppUnitTest
{
    static App app;
    static City testCity;
    static Continent testContinent;
    static Continent testContinentIncorrect;
    static Region testRegion;
    static Country testCountry;
    static District testDistrict;

    @BeforeAll
    static void init() {

        app = new App();

        testCity = new City(460,
                "Edinburgh",
                "GBR",
                "Scotland",
                450180);

        testContinent = new Continent("Asia");

        testRegion = new Region("Caribbean");

        testDistrict = new District("England");

        testCountry = new Country("CAN","Canada","North America","North America",31147000,"Ottowa");
    }

    /**
     * Tests the City Class. Making sure that a valid city can be created
     * to mirror the "city" database table.
     */
    @Test
    void testAddCity() {

        // Testing Constructors
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

    /**
     * Tests the Continent Class. Making sure that a valid Continent can be created
     * to mirror the "Continent" value from the country database table.
     */
    @Test
    void testAddContinent() {

        // Testing Constructors
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
     *
     * @see Country
     */
    @Test
    void testAddCountry() {

        // Testing Constructors
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

    /**
     * Tests the District Class. Making sure that a valid District can be created
     * to mirror the "District" value from the city database table.
     *
     * @see District
     */
    @Test
    void testAddDistrict() {

        // Testing Constructors
        assertEquals("England", testDistrict.getName());

        // Testing Setters

        testDistrict.setName("Scotland");

        assertEquals("Scotland", testDistrict.getName());

        // Testing toString

        assertEquals("District{" +
                "name='" + "Scotland" + '\'' +
                '}', testDistrict.toString());

    }

    /**
     * Tests the Region Class. Making sure that a valid Region can be created
     * to mirror the "Region" value from the country database table.
     *
     * @see Region
     */
    @Test
    void testAddRegion() {

        // Testing Constructors
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
     * Testing when the cities array is null
     *
     * @see App#printCities(ArrayList)
     */
    @Test
    void printCitiesTestNull(){

        ArrayList<City> cities = null;
        app.printCities(cities);
    }

    /**
     *  Testing when the cities array list is empty
     *
     *  @see App#printCities(ArrayList)
     */
    @Test
    void printCitiesTestEmpty(){

        ArrayList<City> cities = new ArrayList<>();
        app.printCities(cities);
    }

    /**
     *  Testing when the city is populated
     *
     *  @see App#printCities(ArrayList)
     */
    @Test
    void printCitiesTestPopulated(){

        ArrayList<City> cities = new ArrayList<>();

        // Adding the populated object to the array
        cities.add(testCity);

        app.printCities(cities);

    }

    /**
     *  Testing edge cases of Integers from the cities class
     *
     *  @see App#printCities(ArrayList)
     */
    @Test
    void printCitiesTestEdgeCase(){

        ArrayList<City> cities = new ArrayList<>();

        testCity.setId(0);
        testCity.setPopulation(0);

        cities.add(testCity);

        app.printCities(cities);

    }

    /**
     * Testing when the countries array is null
     *
     * @see App#printCountries(ArrayList)
     */
    @Test
    void printCountriesTestNull(){
        ArrayList<Country> countries = null;
        app.printCountries(countries);
    }

    /**
     *  Testing when the countries array list is empty
     *
     *  @see App#printCountries(ArrayList)
     */
    @Test
    void printCountriesTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printCountries(countries);
    }

    /**
     *  Testing when the country is populated
     *
     *  @see App#printCountries(ArrayList)
     */
    @Test
    void printCountriesTestPopulated(){
        ArrayList<Country> countries = new ArrayList<>();

        countries.add(testCountry);

        app.printCountries(countries);
    }

    /**
     *  Testing edge cases of Integers from the countries class
     *
     *  @see App#printCountries(ArrayList)
     */
    @Test
    void printCountriesTestEdgeCase(){

        ArrayList<Country> countries = new ArrayList<>();

        testCountry.setPopulation(0);

        countries.add(testCountry);

        app.printCountries(countries);

    }

    /**
     *
     * Testing that an array list of countries inputted, are correctly sorted by their population from largest to smallest.
     * {@link App#sortCountriesByPopulation}
     */
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
    /**
     *
     * Testing that an array list of countries (in the same region) inputted, are correctly sorted
     * by their population from largest to smallest.
     * {@link App#sortCountriesByPopulation}
     */

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
    void readPositiveIntegerAcceptsMultipleUserInputsFromOneScanner()
    {
        Scanner input = new Scanner("USA\n2\n7\n");

        assertEquals("USA", App.readLineOrDefault(input, "Country: ", "CAN"));
        assertEquals(2, App.readPositiveInteger(input, "Top cities: ", 5));
        assertEquals(7, App.readPositiveInteger(input, "Top countries: ", 5));
    }

    @Test
    void readPositiveIntegerAsksAgainAfterInvalidInput()
    {
        assertEquals(3, App.readPositiveInteger(
                new Scanner("many\n0\n3"), "Top countries: ", 5));
    }

    @Test
    void inputReadersUseDefaultsWhenStandardInputIsClosed()
    {
        Scanner input = new Scanner("");

        assertEquals("USA", App.readLineOrDefault(input, "Country: ", "USA"));
        assertEquals(5, App.readPositiveInteger(input, "Top countries: ", 5));
    }

    @Test
    void getNPopulousCountriesRejectsNonPositiveLimit()
    {
        assertThrows(IllegalArgumentException.class, () -> app.getNPopulousCountries(0));
        assertThrows(IllegalArgumentException.class, () -> app.getNPopulousCountries(-1));
    }

    @Test
    void getNPopulousCountriesInContinentRejectsInvalidInput()
    {
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCountriesInContinent(0, new Continent("Asia")));
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCountriesInContinent(5, null));
    }

    @Test
    void getNPopulousCountriesInRegionRejectsInvalidInput()
    {
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCountriesInRegion(0, new Region("Eastern Africa")));
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCountriesInRegion(5, null));
    }

    @Test
    void getNPopulousCitiesInContinentRejectsInvalidInput()
    {
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCitiesInContinent(0, new Continent("Asia")));
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCitiesInContinent(5, null));
    }

    @Test
    void getCapitalCitiesInContinentRejectsInvalidInput()
    {
        assertThrows(IllegalArgumentException.class, () -> app.getCapitalCitiesInContinent(null));
        assertThrows(IllegalArgumentException.class,
                () -> app.getCapitalCitiesInContinent(new Continent(" ")));
    }

    @Test
    void getCapitalCitiesInRegionRejectsInvalidInput()
    {
        assertThrows(IllegalArgumentException.class, () -> app.getCapitalCitiesInRegion(null));
        assertThrows(IllegalArgumentException.class,
                () -> app.getCapitalCitiesInRegion(new Region(" ")));
    }

    @Test
    void getCitiesInDistrictRejectsMissingDistrict()
    {
        assertThrows(IllegalArgumentException.class, () -> app.getCitiesInDistrict(null));
        assertThrows(IllegalArgumentException.class, () -> app.getCitiesInDistrict(new District(" ")));
    }

    @Test
    void getCitiesInCountryRejectsMissingCountryCode()
    {
        assertThrows(IllegalArgumentException.class, () -> app.getCitiesInCountry(null));
        Country countryWithoutCode = new Country(" ", "Canada", "North America",
                "North America", 31147000, "Ottawa");
        assertThrows(IllegalArgumentException.class, () -> app.getCitiesInCountry(countryWithoutCode));
    }

    @Test
    void populationPercentagesAddUpToOneHundred()
    {
        double cityPercent = App.getPopulationPercentage(400, 1000);
        double nonCityPercent = App.getPopulationPercentage(600, 1000);

        assertEquals(40.0, cityPercent);
        assertEquals(60.0, nonCityPercent);
        assertEquals(100.0, cityPercent + nonCityPercent);
        assertEquals(0.0, App.getPopulationPercentage(0, 0));
    }

    @Test
    void populationPercentageCalculatesLanguageShareOfWorld()
    {
        assertEquals(10.0, App.getPopulationPercentage(100, 1000));
    }

    @Test
    void getNPopulousCitiesInCountryRejectsNonPositiveLimit()
    {
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCitiesInCountry(0, "CAN"));
    }

    @Test
    void getNPopulousCitiesInCountryRejectsMissingCountryCode()
    {
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCitiesInCountry(5, " "));
        assertThrows(IllegalArgumentException.class,
                () -> app.getNPopulousCitiesInCountry(5, null));
    }
}