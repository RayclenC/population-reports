# Product Backlog

## High Priority (sprint 1)
1. As a Developer, I want to set up the GitHub repository using GitFlow (master, develop, and release branches) and add a Code of Conduct,
   so that the team can collaborate smoothly and satisfy Code Review 1 criteria.
2. As a developer, I want to package the application into a self-contained executable JAR using Maven, so that it builds consistently.
3. As DevOps engineers, we want to containerise the application and SQL database using Docker and Docker Compose, so that the entire environment runs reliably with a single command.
4. As DevOps engineers, we want to configure a GitHub Actions CI workflow, so that every push to develop automatically runs Maven builds and verifies the Docker container creation.

### Country Reports:
1. As a user, I want to view all countries in the world organized by population (largest to smallest), so that I can analyze global population distribution.
2. As a user, I want to view all countries in a continent organized by population (largest to smallest), so that I can analyze continental demographics.
3. As a user, I want to view all countries in a region organized by population (largest to smallest), so that I can analyze regional demographics.

### City Reports:
1. As a user, I want to view all cities in the world organized by population (largest to smallest), so that I can inspect urban populations globally.
2. As a user, I want to view all cities in a continent organized by population (largest to smallest), so that I can inspect urban populations by continent.
3. As a user, I want to view all cities in a region organized by population (largest to smallest), so that I can inspect urban populations by region.
4. As a user, I want to view all cities in a country organized by population (largest to smallest), so that I can inspect urban populations by country.
5. As a user, I want to view all cities in a district organized by population (largest to smallest), so that I can inspect local district populations.

## Medium priority (Sprint 2)
#### Agile & Management
1. As a Scrum Master, I want to map user stories to GitHub Issues, manage tasks via a GitHub Kanban board, and draw Use Case diagrams,
   so that project tracking satisfies Code Review 2 criteria

#### Top N country report
1. As a user, I want to view the top N populated countries in the world (where N is provided by the user), so that I can focus on the largest global nations.
2. As a user, I want to view the top N populated countries in a continent (where N is provided by the user), so that I can focus on key continental nations.
3. As a user, I want to view the top N populated countries in a region (where N is provided by the user), so that I can focus on key regional nations.

#### Top N City Report
1. As a user, I want to view the top N populated cities in the world (where N is provided by the user), so that I can highlight major global cities.
2. As a user, I want to view the top N populated cities in a continent (where N is provided by the user), so that I can highlight major continental cities.
3. As a user, I want to view the top N populated cities in a region (where N is provided by the user), so that I can highlight major regional cities.
4. As a user, I want to view the top N populated cities in a country (where N is provided by the user), so that I can highlight major national cities.
5. As a user, I want to view the top N populated cities in a district (where N is provided by the user), so that I can highlight major district cities.

#### Capital City Report
1. As a user, I want to view all capital cities in the world organized by population (largest to smallest), so that I can compare global capitals.
2. As a user, I want to view all capital cities in a continent organized by population (largest to smallest), so that I can compare continental capitals.
3. As a user, I want to view all capital cities in a region organized by population (largest to smallest), so that I can compare regional capitals.
4. As a user, I want to view the top N populated capital cities in the world (where N is provided by the user), so that I can isolate major global capital hubs.
5. As a user, I want to view the top N populated capital cities in a continent (where N is provided by the user), so that I can isolate major continental capital hubs.
6. As a user, I want to view the top N populated capital cities in a region (where N is provided by the user), so that I can isolate major regional capital hubs.

## Low Priority(Sprint 3 & 4)
#### Population Distribution Report
1. As a user, I want to view the population of people, people living in cities, and people not living in cities (including percentages) in each continent, so that I can analyze continental urbanization.
   2. As a user, I want to view the population of people, people living in cities, and people not living in cities (including percentages) in each region, so that I can analyze regional urbanization.
3. As a user, I want to view the population of people, people living in cities, and people not living in cities (including percentages) in each country, so that I can analyze national urbanization.

#### Exact headcount queries
1. As a user, I want to view the total population of the world, so that I can see global headcount.
2. As a user, I want to view the total population of a continent, so that I can see continental headcount.
3. As a user, I want to view the total population of a region, so that I can see regional headcount.
4. As a user, I want to view the total population of a country, so that I can see national headcount.
5. As a user, I want to view the total population of a district, so that I can see local district headcount.
6. As a user, I want to view the total population of a city, so that I can see specific city headcount.


#### Global Language Statistics ReportAs
1. AS a user, I want to view the number of people who speak Chinese, English, Hindi, Spanish, and Arabic from greatest to smallest, including percentage of the world population, so that I can evaluate language distribution.

#### Testing & Quality Assurance (Code Review 3)
1. As a developer, I want to implement unit tests (using mocks) and integration tests running inside GitHub Actions, so that database queries and output schemas are automatically validated.

#### Continuous Deployment & Bug Management (Code Review 4)
1. As a DevOps engineer, I want to configure automated deployment packaging and GitHub Issue bug report templates, so that final release artifacts are built seamlessly and user bugs can be tracked.
