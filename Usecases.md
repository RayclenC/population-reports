# Population Reporting System — Use Cases

## UC01 View Countries by Population
**Primary actor:** Organisation user.  
**Goal:** View countries ranked from largest population to smallest.
**Preconditions:** The system is available and can access the SQL database.
**Trigger:** The user selects a country population report.
**Main flow:**
1. The system offers the user a choice of scope: world, continent, or region.
2. The user selects a scope.
3. If the scope is continent or region, the system asks the user to select one.
4. The system retrieves the countries in that scope and their population data.
5. The system sorts the countries from largest population to smallest.
6. The system displays each country’s code, name, continent, region, population, and capital.
**Alternative flows:**
- **1  No matching countries:** The system displays a message that no countries were found for the selected scope.
- **2  Missing data:** If a country has missing population or capital data, the system displays the available values and identifies the missing information.
**Postconditions:** The requested country report is displayed. The database is unchanged.

## UC02 View the Top N Countries by Population
**Primary actor:** Organisation user  
**Goal:** View the N most populated countries in a selected scope.
**Preconditions:** The system can access the database.
**Trigger:** The user selects a “top countries” report.
**Main flow:**
1. The system offers the user a scope world, continent, or region.
2. The user selects a scope and, when required, chooses a continent or region.
3. The system asks the user to enter N.
4. The user enters the N.
5. The system checks that the N is a positive whole number.
6. The system retrieves countries in the selected scope and sorts them by population, largest first.
7. The system displays up to N countries with code, name, continent, region, population, and capital.
**Alternative flows:**
- **1  Invalid N:** If N is blank, or less than 1, the system asks the user to enter a valid value.
- **2  N exceeds available countries:** The system displays all matching countries and indicates that fewer than N were available.
- **3  No matching countries:** The system reports that no countries were found.
**Postconditions:** The requested top-N country report is displayed. The database is unchanged.

## UC03 View Cities by Population
**Primary actor:** Organisation user  
**Goal:** View cities ranked from largest population to smallest.
**Preconditions:** The system can access the database.
**Trigger:** The user selects a city population report.
**Main flow:**
1. The system offers the user a scope: world, continent, region, country, or district.
2. The user selects a scope.
3. If needed, the system asks the user to select the relevant continent, region, country, or district.
4. The system retrieves the cities in that scope and their population data.
5. The system sorts the cities from largest population to smallest.
6. The system displays each city’s name, country, district, population, and whether it is a capital city.
**Alternative flows:**
- **1 No matching cities:** The system reports that no cities were found for the selected scope.
- **2 Missing data:** The system displays the available city information and identifies missing values.
**Postconditions:** The requested city report is displayed. The database is unchanged.

## UC04 View the Top N Cities by Population
**Primary actor:** Organisation user  
**Goal:** View the N most populated cities in a selected scope.
**Preconditions:** The system can access the database.
**Trigger:** The user selects a “top cities” report.
**Main flow:**
1. The system offers the user a scope: world, continent, region, country, or district.
2. The user selects a scope and, when required, chooses the relevant location.
3. The system asks the user to enter N.
4. The user enters N.
5. The system checks that N is a positive whole number.
6. The system retrieves and sorts matching cities by population, largest first.
7. The system displays up to N cities with name, country, district, population, and capital status.
**Alternative flows:**
- **1 Invalid N:** The system asks the user to enter a positive whole number.
- **2 N exceeds available cities:** The system displays all matching cities and indicates that fewer than N were available.
- **3 No matching cities:** The system reports that no cities were found.
**Postconditions:** The requested top-N city report is displayed. The database is unchanged.

## UC05 View Capital Cities by Population
**Primary actor:** Organisation user  
**Goal:** View capital cities ranked from largest population to smallest.
**Preconditions:** The system can access the database.
**Trigger:** The user selects a capital city population report.
**Main flow:**
1. The system offers the user a scope: world, continent, or region.
2. The user selects a scope and, when required, chooses the relevant continent or region.
3. The system identifies capital cities in that scope using the country-to-capital relationship in the database.
4. The system sorts the capital cities by population, largest first.
5. The system displays each capital’s name, country, and population.
**Alternative flows:**
- **1 No matching capitals:** The system reports that no capital cities were found.
- **2 Missing capital data:** The system excludes entries that cannot be identified as a capital and reports that some capital data was unavailable.
**Postconditions:** The requested capital city report is displayed. The database is unchanged.

## UC06 View the Top N Capital Cities by Population
**Primary actor:** Organisation user  
**Goal:** View the N most populated capital cities in a selected scope.
**Preconditions:** The system can access the database.
**Trigger:** The user selects a “top capital cities” report.
**Main flow:**
1. The system offers the user a scope: world, continent, or region.
2. The user selects a scope and, when required, chooses a continent or region.
3. The system asks the user to enter N.
4. The user enters N.
5. The system checks that N is a positive whole number.
6. The system identifies capital cities in the selected scope and sorts them by population, largest first.
7. The system displays up to N capitals with name, country, and population.
**Alternative flows:**
- **1 Invalid N:** The system asks the user to enter a positive whole number.
- **2 N exceeds available capitals:** The system displays all matching capitals and indicates that fewer than N were available.
- **3 No matching capitals:** The system reports that no capital cities were found.
**Postconditions:** The requested top-N capital report is displayed. The database is unchanged.

## UC07 View Urban and Non-Urban Population by Continent, Region, or Country
**Primary actor:** Organisation user  
**Goal:** Compare total population, city population, and population not living in cities.
**Preconditions:** The system can access population data for locations and their cities.
**Trigger:** The user selects a population distribution report.
**Main flow:**
1. The system offers the user a grouping: continent, region, or country.
2. The user selects a grouping.
3. The system retrieves the required population and city information.
4. For each result, the system calculates:total population; population living in cities; percentage living in cities; population not living in cities; percentage not living in cities.
5. The system displays the location name and all calculated values.
**Alternative flows:**
- **1 Missing or incomplete data:** The system identifies locations where a complete calculation could not be made.
- **2 Total population is zero or unavailable:** The system does not calculate percentages for that location and marks them unavailable.
**Postconditions:** The population distribution report is displayed. The database is unchanged.

## UC08 Look Up the Population of a Location
**Primary actor:** Organisation user  
**Goal:** Find the population of a selected geographic entity.
**Preconditions:** The system can access the database.
**Trigger:** The user selects a population lookup.
**Main flow:**
1. The system offers the user a location type: world, continent, region, country, district, or city.
2. The user selects the type.
3. If the selection is not world, the system asks the user to select or enter the relevant location.
4. The system retrieves the population for that location.
5. The system displays the location name, location type, and population.
**Alternative flows:**
- **1 Location not found:** The system informs the user and allows them to select another location.
- **2 Population unavailable:** The system reports that population data is unavailable for the selected location.
**Postconditions:** The requested population value is displayed. The database is unchanged.

## UC09 View Language Speaker Estimates
**Primary actor:** Organisation user  
**Goal:** Compare the estimated number of people speaking Chinese, English, Hindi, Spanish, and Arabic.
**Preconditions:** The system can access country population and language data, including each language’s percentage of a country’s population.
**Trigger:** The user selects the language population report.
**Main flow:**
1. The system retrieves country population and language-percentage data for the five specified languages.
2. For each language, the system estimates speakers by applying the language percentage to the relevant country population and aggregating the results.
3. The system calculates each language’s percentage of the world population.
4. The system sorts languages from greatest estimated speaker count to smallest.
5. The system displays language, estimated speaker count, and percentage of world population.
**Alternative flows:**
- **1 Language not found in the database:** The system reports that an estimate could not be calculated for that language.
- **2 Missing country or language data:** The system calculates using available data and indicates that the result may be incomplete.
- **3 World population unavailable or zero:** The system displays estimated speaker counts where possible but marks the world-population percentage unavailable.
**Postconditions:** The language report is displayed. The database is unchanged.
