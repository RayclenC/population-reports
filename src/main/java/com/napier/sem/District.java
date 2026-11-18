package com.napier.sem;

/**
 *
 * <p>
 *     The District class builds a 'district' object with getters, setters and toString method
 * </p>
 *
 */
public class District {
    public String name;

    public District(String name) {
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
        return "District{" +
                "name='" + name + '\'' +
                '}';
    }
}
