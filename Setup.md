# YU Bazaar - Setup Guide

This document provides the setup instructions for YU Bazaar, specifically for initializing the Java Spring Boot environment. Follow these steps to prepare your development environment.

## Prerequisites

Make sure you have the following tools installed before proceeding:

- **Java Development Kit (JDK)** - Version 11 or higher
- **Apache Maven** - Version 3.6 or higher
- **Spring Boot CLI** (optional) - Version 2.5 or higher
- **Git**

### Verify Installations

1. **Java**
   Check your Java installation by running:
   ```bash
   java -version
Ensure it’s version 11 or higher.

2. Maven
   Verify Maven installation by running:
   ```bash
   mvn -version
 This should return Maven’s version and the Java version it’s using.
 
4. Spring Boot CLI (optional)
   Run the following to confirm installation:
   ```bash
   spring --version
  Spring Boot CLI is optional, but it can speed up development by providing commands for quickly creating Spring Boot projects.

   ## Initial Project Setup

   Since this is Sprint 0, we’ll set up the project structure without adding actual code.

* ### Step 1: Clone the Repository
Clone the YU Bazaar repository from GitHub:
```bash
  git clone https://github.com/shah-krish/yu-bazaar.git
  cd YU-Bazaar
```
* ### Step 2: Initialize a Spring Boot Project
If you have the Spring Boot CLI installed, you can quickly generate a project structure by running:
```
spring init --dependencies=web,data-jpa,mysql,lombok --package-name=com.yubazaar YUBazaar
```

Alternatively, you can use Spring Initializr to generate the project:

  1. Select Project: Maven Project
  2. Select Language: Java
  3. Enter Spring Boot Version: 2.5 or higher
  4. Add Dependencies:
      * Spring Web
      * Spring Data JPA
      * MySQL Driver
      * Lombok (for boilerplate code reduction)
      
  5. Download the project and extract it into the YU-Bazaar directory.

* ### Step 3: Configure Database (MySQL)
  We’ll use MySQL for our database. Make sure MySQL is installed and running on your machine.

1. Create a Database
Open your MySQL command line or preferred MySQL client and create a new database:
```
CREATE DATABASE yubazaar_db;
```

2. Configure Application Properties
Open the src/main/resources/application.properties file and configure the database connection:
```
spring.datasource.url=jdbc:mysql://localhost:3306/yubazaar_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```
* ### Step 4: Run the Application

To verify the setup, run the Spring Boot application. In the project root directory, execute:
```
mvn spring-boot:run
```
If everything is set up correctly, you should see the Spring Boot application starting up successfully.

### Additional Resources

[Spring Boot Documentation](https://docs.spring.io/spring-boot/)
[Maven Documentation](https://maven.apache.org/guides/index.html)
[Java JDK Download](https://www.oracle.com/java/technologies/downloads/?er=221886)
[MySQL Documentation](https://dev.mysql.com/doc/)