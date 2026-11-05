package com.napier.sem;

public class City {

    private int ID;
    final String name;
    private String CountryCode;
    private District district;
    private double population;

    City(String name) {
        this.name = name;
    }

    //getters and setters. Name does not get a setter
    public String GetName(){return name;}

    public int GetId(){return ID;}
    public void SetId(int ID){this.ID = ID;}

    public String GetCountryCode(){return CountryCode;}
    public void SetCountryCode(String CountryCode){this.CountryCode = CountryCode;}

    public District GetDistrict(){return district;}
    public void SetDistrict(District district){this.district = district;}

    public double GetPopulation(){return population;}
    public void SetPopulation(double population){this.population = population;}

}

