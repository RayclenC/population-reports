package com.napier.sem;

public class Region {

    private String name;
    private Country country;

    Region(String name, Country country){
        this.name = name;
        this.country = country;
    }

    public String getName(){
        return name;
    }

    public Country getCountry(){
        return country;
    }
}
