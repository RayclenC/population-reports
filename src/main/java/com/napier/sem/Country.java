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


    public Country(String code, String name, String continentString, String regionString, double population, String capital) {

        Continent continent = new Continent(continentString);
        Region region = new Region(regionString);

        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    //getters and setters. Values in the constructor do not get a setter
    public String getCode(){return code;}
    public void setCode(String code){this.code = code;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public Continent getContinent(){return continent;}

    public Region getRegion(){return region;}

    public double getPopulation(){return population;}
    public void setPopulation(double population){this.population = population;}

    public String getCapital(){return capital;}


    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", continent=" + continent +
                ", region=" + region +
                ", population=" + population +
                ", capital='" + capital + '\'' +
                '}';
    }
}