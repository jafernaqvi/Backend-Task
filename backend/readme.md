# Backend Skeleton
This folder contains a runnable Spring application with a implementation that you can use as a base for implementing your tasks.

## Running the Application
You can run the application by executing the main in [SmartEnergyApplication](src/main/java/net/grandcentrix/assessment/smartenergy/SmartEnergyApplication.java). Alternatively, you can run it via gradle with `./gradlew :backend:bootRun` (in the root folder).

## Testing the API
We already integrated the swagger UI in the application. You can use it to test the api you implemented. You can access it after starting the application at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

For further documentation check out [springdoc-openapi](https://springdoc.org/).

## Accessing the Database
For this skeleton we decided to integrate an embedded H2 database. With this approach you don't need to install any other software. You can access the data via the _h2 console_ at [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/). The login credentials are:

```
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: sa
```

## About the (Pre-) Implementation

In the existing implementation you are able to create a user, and a device (a smart plug is a device) already. Both entities are considered as users by Spring Security and are able to access `smartenergy/users/**` and `smartenergy/devices/**` endpoints respectively. Check out the `DeviceSecurityConfig` and `AppUserSecurityConfig` for more details. The devices are not choosing a password directly, so we generate a password when they register with the backend and send it back to it. This password is used by them to authenticate on other endpoints. E.g. when sending energy consumption data to the backend.

The existing implementation is held as easy and accessible as possible. That means that it might not be perfect for every task that you need to implement. We encourage you to change everything you want. Changing the implementation completely is your free choice, and we love to hear your reasoning about it. 

We also decided to not use [Lombok](https://projectlombok.org/) in the pre-implementation to make it more accessible when you are not familiar with the library. Of course, you can still integrate it if you like working with it.
