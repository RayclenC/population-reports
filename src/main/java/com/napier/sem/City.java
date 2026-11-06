package com.napier.sem;

/**
 *
 * <p>
 *     The City class builds a 'city' object from the 'city' database table, allowing us to set and get different elements of the database table.
 * </p>
 *
 */

public class City {

    private int id;
    private String name;
    private String CountryCode;
    private String district;
    private double population;

    public City(int id, String name, String countryCode, String district, double population) {
        this.id = id;
        this.name = name;
        CountryCode = countryCode;
        this.district = district;
        this.population = population;
    }


    //getters and setters.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }


    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", CountryCode='" + CountryCode + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                '}';
    }
}

