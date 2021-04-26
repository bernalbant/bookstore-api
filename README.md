# BookStore-API

This repository contains Rest API implementation for bookstore.

## About Project

This project is implemented using Spring Boot with Java. It includes JUnit, performing tests and integration tests.
Docker-compose is included in the project to run the application on a Docker container along with MongoDB database.
Testcontainers library is used for running the integration tests on a dockerized database instance.
The project also contains Swagger UI.
Specific changes on entities are logged by a logging class implemented with AspectJ.
All endpoints are secured with Jwt bearer token authentication.


**Technologies**
* Java 11
* Spring Boot 2.4.3
* MongoDB 3.2.6
* Spring JPA
* Testcontainer
* Lombok
* Docker-compose 3.0
* Springfox-swagger for Documentation

### Swagger Documentation ##

This template gives you free auto generated documentation, if you define your joi schemas and use them in your route definitions.
See `/swagger-ui/` after you start the application.

### Building

To build a runnable Jar.

run `./gradlew build`

Since this is a gradle project, it comes with its own gradle wrapper, no need to install gradle on your machine.
Often confused with `gradle build` which means the specific gradle version may need to be installed on your local machine.

`./gradlew` indicates you are using a gradle wrapper. The wrapper is generally part of a project and it facilitates installation of gradle.

### Docker ###
* `docker-compose up` to instantiate the app and MongoDB database on Docker container


### Testing

Template includes JUnit as the testing platform. To run tests
run  `./gradlew test` in terminal

### About Integration Tests

* Testcontainers version 1.15.0-rc2 is included in the project
    * It enables running integration tests on the dockerized instance of MongoDB
* MongoDB 3.2.4 version is used for integration testing

### Postman Collection

A postman collection is provided under "postman" folder for testing all endpoints.

