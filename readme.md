# South African Numbers Validation

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

To run this application on your local machine you can execute the `main` method in the `com.olx.number.NumberApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Available endpoints


```shell
/api/phone-number/upload/
```
Endpoint that receives a file to persist phone numbers and returns details of processed file.

```shell
/api/phone-number/upload/{uuid}
```
Endpoint that returns details of processed file. Uuuid is a file identifier.

```shell
/api/phone-number/{number}
```
Endpoint to test a single number.
