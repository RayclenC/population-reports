# Population Reporting System - Use Cases
**Primary Actor:** Organisation User (the person running the Java CLI app)
**Secondary Actor:** MySQL world Database

## UC1: View Country Reports
**Goal**: The user wants to view a list of countries sorted by population (largest to smallest), with options to filter by continent, region, or top N.
**Preconditions:** The app is running and successfully connected to the MySQL database.

### Basic Flow (World Report):

1. User selects option 1 (All countries in the world).
2. System executes a SQL query joining country and city (to get the capital name) ordered by population descending.
3. System prints the formatted country report (Code, Name, Continent, Region, Population, Capital).

### Alternative Flows:

* Continent Report: User selects option 2. System prompts for a continent (e.g., "Europe"). System queries the DB filtering by that exact continent, then prints the report.

* Region Report: User selects option 3. System prompts for a region. System queries the DB filtering by that region, then prints the report.

* Top N Reports: User selects option 4, 5, or 6. System asks for an integer N (and a continent/region if applicable). System appends LIMIT N to the SQL query and prints the truncated report.

### Exception Handling:

* If the user enters a continent or region that doesn't exist, the database returns 0 rows and the system prints a blank table.

* If the user enters a negative number for N, the system catches the SQL error and prints "Invalid number".

* If the database connection drops, the system prints an error and exits.

# UC2: View City Reports
**Goal:** The user wants to view a list of cities sorted by population, with options to filter by world, continent, region, country, or district.
**Preconditions:** App is running, DB is connected.

### Basic Flow (World Report):

1. User selects option 7 (All cities in the world). 
2. System executes a SQL query joining city and country to get the country name, ordered by population descending. 
3. System prints the formatted city report (City Name, Country, District, Population).

### Alternative Flows:

* Country/District Filter: User selects the appropriate option. System prompts for the country code (e.g., "GBR") or district name. System queries the DB filtering by that value, then prints the report.
* Top N Cities: User selects a "Top N" option. System asks for N. System appends LIMIT N to the SQL query.

### Exception Handling:

* If the user enters a country code that doesn't exist (e.g., "XYZ"), the system prints a blank table.

* If the user enters a district name, the system returns all cities in that district globally (since district names can repeat across countries).

# UC3: View Capital City Reports
**Goal:** The user wants to view a list of capital cities sorted by population.
**Preconditions**: App is running, DB is connected.

### Basic Flow (World Report):

1. User selects the "All capital cities" option. 
2. System queries the DB, joining country and city specifically on country.Capital = city.ID. 
3. System prints the capital city report (Capital Name, Country, Population).

### Alternative Flows:

* Continent/Region Filter: System prompts for the specific area and filters the SQL query accordingly. 
* Top N Capitals: System prompts for N and applies a LIMIT.

### Exception Handling:

* Some countries have NULL for their Capital ID (e.g., Antarctica). The system must use a LEFT JOIN so these countries don't break the query, even if the capital column is empty.

# UC4: View Population Breakdown Report
**Goal:** The user wants to see the total population of an area, how many people live in cities, how many don't, and the percentage breakdown.
**Preconditions:** App is running, DB is connected.

### Basic Flow:

1. User selects the Population Breakdown option. 
2. System asks for the scope: Continent, Region, or Country. 
3. User selects "Continent" and types "Europe". 
4. System runs a query to get the total population of Europe from the country table. 
5. System runs a second query (or a subquery) to get the total population of all cities in Europe from the city table. 
6. System subtracts City Population from Total Population to get Non-City Population. 
7. System calculates the percentages. 
8. System prints the breakdown report.

### Exception Handling:

* If the user enters an invalid continent, the system prints an error message and returns to the main menu.

* Note on SQL: The system must not cross-join country and city directly for this report, as it will duplicate country populations for every city. Subqueries or separate queries must be used.

# UC5: View Specific Population
**Goal:** The user wants a quick lookup of the total population for a specific entity (World, Continent, Region, Country, District, or City).
**Preconditions:** App is running, DB is connected.

### Basic Flow:

1. User selects the Specific Population option. 
2. System asks for the entity type (e.g., "Country"). 
3. User enters the entity name/code (e.g., "GBR"). 
4. System queries the appropriate table (country, city, etc.) for that specific record. 
5. System prints the name and total population.

### Exception Handling:

* If the entity is not found, the system prints "Entity not found". 
* If the user enters invalid data, the system catches the error and re-prompts.

# UC6: View Language Report
**Goal:** The user wants to see the number of speakers for Chinese, English, Hindi, Spanish, and Arabic, sorted by the number of speakers, including the percentage of the world population.
**Preconditions:** App is running, DB is connected.

### Basic Flow:

1. User selects the Language Report option. 
2. System queries the countrylanguage table, filtering for the 5 specific languages. 
3. System joins this with the country table to get the total population of each country. 
4. System calculates the total speakers for each language: SUM(country.Population * (countrylanguage.Percentage / 100)). 
5. System calculates the world population: SELECT SUM(Population) FROM country. 
6. System calculates the percentage: (Language Speakers / World Population) * 100. 
7. System sorts the results by total speakers descending. 
8. System prints the report.

### Exception Handling:

* If the database connection fails, the system prints an error. 
* If the world population is 0 (unlikely), the system avoids a divide-by-zero error by printing 0%.

