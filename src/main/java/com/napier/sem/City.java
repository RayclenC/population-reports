package com.napier.sem;

public class City {

    private int id;
    private String name;
    private String CountryCode;
    private String district;
    private double population;

    //getters and setters. Name does not get a setter

    public int GetId(){return id;}
    public void SetId(int id){this.id = id;}

    public String GetName(){return name;}
    public void SetName(String name){this.name = name;}

    public String GetCountryCode(){return CountryCode;}
    public void SetCountryCode(String CountryCode){this.CountryCode = CountryCode;}

    public String GetDistrict(){return district;}
    public void SetDistrict(String district){this.district = district;}

    public double GetPopulation(){return population;}
    public void SetPopulation(double population){this.population = population;}

}

