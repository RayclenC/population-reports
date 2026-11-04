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