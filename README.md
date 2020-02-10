# InternetShop 

This is a simple representation of online store

## Table of contents 
* [Description](#description)
* [Prerequisites](#prerequisites)
* [Technologies used](#technologies-used)
* [Deployment](#deployment)
* [Author](#author)


## Description
 
The store model has role based authorization and authentication with USER and ADMIN roles. <br />

Registration, log in/log out options are also implemented.

USER can look through the list of products, add chosen product to a cart, delete items from cart 
and complete an order. 
USER also can review all completed orders. <br />
ADMIN can add/delete products to/from a list of products and overview the list of all users.


### Prerequisites

To run this project you need to install next software: 
* [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) - 
Development environment 
* [Maven](https://maven.apache.org/) - Dependency Management
* [Tomcat](http://tomcat.apache.org/) - Web Server
* [MySQL](https://www.mysql.com/) - Database

## Technologies used

*  MavenCheckstylePlugin 3.1.0
*  javax.servlet 3.1.0
*  javax.jstl 1.2
*  mysql-connector-java 8.0.18
*  log4j 1.2.17

## Deployment

Add this project to your IDE as Maven project.

Add Java SDK 11 in project structure.

Configure Tomcat:
- Add artifact
- Add Java SDK 11

Change a path to your Log file in **src/main/resources/log4j.properties** on line 12.


To work with MySQL you need to:
- Use file **src/main/resources/init_db.sql** to create schema and all the tables required by this app in MySQL DB
- Change username and password to match with MySQL in **src/main/java/internetshop/factory/Factory.java** class on 33 line

If you want to test code without establishing connection to database you can use inner storage option.
To work with inner Storage you need to replace every object of Dao-layer 
in **src/main/java/internetshop/factory/Factory.java** from *DaoJdbcImpl(connection) to *DaoImpl().  
Provide all necessary imports to corresponding classes. Comment out static code block of Factory class.


Run the project:

Main page is at URL: .../{context_path}/shop

To create default admin user run 
For MySQL DAO only **on first run** of the project, for inner Storage **on every launch**, 
to create default users open URL: .../{context_path}/inject


By default there are one user with an USER role (login = "user", password = "123"),<br />
one with an ADMIN role (login = "admin", password = "123"),<br />
and one with both roles (login = "superuser", password = "123").

## Author
 [Yaroslava Bondarchuk](https://github.com/YarikJ)
