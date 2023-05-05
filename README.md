# CurrencyExchange

Task:
Java Test Task
Create an API. It should contain two methods:
1. Request for a list of exchange rates for all sources, with average market rates
2. Request to issue a list of average exchange rates for all sources for the period

On a regular basis, the web application loads data from a third-party service into an internal database. Implemented API requests must operate on a local database (i.e. retrieve data from a database).
What to use:
1. Use public API
a. https://api.monobank.ua/docs/
b. https://minfin.com.ua/ua/developers/api/ c. https://api.privatbank.ua/#p24/exchange
For each of the providers, implement a common interface and separate implementations
2. Cron job - to implement data synchronization with api providers. 3. Java/Kotlin, Tomcat, Spring Boot, Hibernate, PostgresQL; GIT
4. Requests should be documented using Swagger.
5. Use Gradle as project builder
