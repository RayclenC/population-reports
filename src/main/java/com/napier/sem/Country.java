package com.napier.sem;


/**
 *
 * <p>
 *     The Country class builds a 'country' object from the 'country' database table, allowing us to set and get different elements of the database table.
 * </p>
 *
 */

public class Country {

    private String code;
    private String name;
    final Continent continent;
    final Region region;
    private double population;
    private String capital;

    public Country(){
        this("", "", "");
    }

    //The constructor automatically creates objects for its continent, region and capital
    Country(String continent, String region, String capital){
        this.continent = new Continent(continent);
        this.region = new Region(region, this);
        this.capital = capital;
    }


    //getters and setters. Values in the constructor do not get a setter
    public String GetCode(){return code;}
    public void SetCode(String code){this.code = code;}

    public String GetName(){return name;}
    public void SetName(String name){this.name = name;}

    public Continent GetContinent(){return continent;}

    public Region GetRegion(){return region;}

    public double GetPopulation(){return population;}
    public void SetPopulation(double population){this.population = population;}

    public String GetCapital(){return capital;}


}