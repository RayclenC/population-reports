package com.napier.sem;

/**
 *
 * <p>
 *     The Region class builds a 'region' object with getters, setters and toString method
 * </p>
 *
 */
public class Region {

    private String name;

    public Region(String name) {
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