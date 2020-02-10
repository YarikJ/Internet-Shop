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
- Create schema and tables (use sql requests in **src/main/resources/init_db.sql** )
- Replace * after user and password  on 33 line of **src/main/java/internetshop/factory/Factory.java** 
class  with your name and password of MySql credentials.

If you want to test code without establishing connection to database you can use inner storage option.
To work with inner storage you need to replace every object of Dao-layer 
in **src/main/java/internetshop/factory/Factory.java** that ends with *DaoJdbcImpl(connection) with *DaoImpl().  
Provide all necessary imports to corresponding classes. Comment out static code block of Factory class.


Run the project:

Main page is at URL: .../{context_path}/shop

To create default ADMIN user with login "ADMIN" and password "111" use URL: .../{context_path}/inject.
Users with USER role are created during registration on URL: .../{context_path}/registration.

## Author
 [Yaroslava Bondarchuk](https://github.com/YarikJ)
