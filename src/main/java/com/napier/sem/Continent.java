package com.napier.sem;

/**
 *
 * <p>
 *     The Continent class builds a 'continent' object with getters, setters and toString method
 * </p>
 *
 */
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