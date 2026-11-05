package com.napier.sem;

public class Region {

    private String name;

    Region(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                '}';
    }
}
