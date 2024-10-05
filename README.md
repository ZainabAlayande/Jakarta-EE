# Jakarta EE Application: Country-City Service

A Jakarta EE-based application that retrieves cities based on country name and identifies the largest city by population. The application leverages a database and API integration to provide accurate city information.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Logging](#logging)

## Overview

This application provides an API for retrieving city information based on the country name and determining the largest city by population. It is built using Jakarta EE and integrates with an Oracle database. The service will first check the database and, if necessary, fetch data from an external API.

## Features

- Retrieve a list of cities for a given country.
- Determine the largest city by population.
- Save country and city data in the database.
- Support for logging using SLF4J and Logback.

## Technologies

- **Jakarta EE**
- **Java 11+**
- **Oracle Database**
- **SLF4J for Logging**
- **Maven for Dependency Management**
- **Gson for JSON Parsing**
- **JDBC for Database Connection**

## Installation

### Prerequisites

- JDK 11+
- Oracle Database (or any other database supported by the application)
- Maven 3.x
- Git

### Clone the repository

```
git clone https://github.com/yourusername/your-repository-name.git
cd your-repository-name
```

### Build the project
```
mvn clean install
```

## Configuration

### Database Configuration
Ensure your database connection details are set correctly in the src/main/resources/application.properties file:

### Properties
```
database.url=jdbc:oracle:thin:@localhost:1521:your_db_name
database.user=your_db_username
database.password=your_db_password
```

