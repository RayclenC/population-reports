package com.napier.sem;

public class Continent {

    private String name;

    public Continent(String name) {
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
        return "Continent{" +
                "name='" + name + '\'' +
                '}';
    }
}
