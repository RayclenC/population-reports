package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyTest
{
    @Test
    void formatCountryTest()
    {
        Country country = new Country();
        country.code = "CHN";
        country.name = "China";
        country.population = 1277558000;

        String result = App.formatCountry(country);

        assertEquals("CHN | China | 1277558000", result);
    }
}
