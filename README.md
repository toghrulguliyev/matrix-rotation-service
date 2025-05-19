# Matrix Rotation Service

A Spring Boot microservice that rotates the layers of a 2D matrix by a given number of rotations.

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Technology Stack](#technology-stack)
4. [Prerequisites](#prerequisites)
5. [Getting Started](#getting-started)

    * [Clone Repository](#clone-repository)
    * [Build](#build)
    * [Run](#run)
6. [Configuration](#configuration)
7. [API Endpoints](#api-endpoints)

    * [Rotate Matrix](#rotate-matrix)
8. [Error Handling](#error-handling)
9. [Testing](#testing)
10. [Project Structure](#project-structure)
11. [Swagger / OpenAPI](#swagger--openapi)
12. [Packaging and Distribution](#packaging-and-distribution)
13. [Performance Tests](#performance-tests)

---

## Overview

This service exposes a single REST endpoint that accepts a 2D matrix and a rotation count as JSON, rotates the matrix layers by the specified number counter clockwise, and returns the rotated matrix.

The rotation algorithm works layer by layer (outermost first), using GCD-based cycle rotation for optimal performance even on large matrices.

## Features

* Rotate any M×N matrix counter clockwise by an arbitrary number of rotations
* Handles empty or null matrices gracefully
* Validates negative rotation counts and returns descriptive errors
* Comprehensive unit tests using JUnit 5
* Auto-generated API documentation via Springdoc / Swagger UI

## Technology Stack

* Java 24
* Spring Boot 3.4.5
* Spring Web (REST)
* Lombok (boilerplate reduction)
* Springdoc OpenAPI (Swagger UI)
* JUnit 5 (unit testing)
* Maven (build and dependency management)

## Prerequisites

* JDK 24 or higher
* Maven 3.6+ (or the Maven Wrapper included)

## Getting Started

### Clone Repository

```bash
git clone https://github.com/toghrulguliyev/matrix-rotation-service.git
cd matrix-rotation-service
```

### Build

Compile and package into a fat-jar:

```bash
mvn clean install
```

### Run

From the project root:

```bash
# via Spring Boot plugin
mvn spring-boot:run

# or using the packaged jar
java -jar target/matrix-rotation-service-0.0.1-SNAPSHOT.jar
```

By default, the application starts on port 8080.

## Configuration

All settings live in `src/main/resources/application.yaml`:

```properties
# HTTP port\ nserver.port=8080
# (Optional) customize Swagger paths:
# springdoc.api-docs.path=/api-docs
# springdoc.swagger-ui.path=/docs
```

## API Endpoints

### Rotate Matrix

* **URL:** `/api/rotate-matrix`
* **Method:** `POST`
* **Content-Type:** `application/json`
* **Request Body:**

  ```json
  {
    "matrix": [[1,2,3],[4,5,6],[7,8,9]],
    "rotations": 2
  }
  ```
* **Response:** `200 OK`

  ```json
  {
    "rotatedMatrix": [[3,6,9],[2,5,8],[1,4,7]]
  }
  ```

## Error Handling

* **400 Bad Request** for invalid inputs:

    * Negative rotation count
    * Malformed JSON

Example error response:

```json
{
  "error": "Rotation count must be non-negative, got: -5"
}
```

## Testing

Unit tests are under `src/test/java` using JUnit 5 with `@Nested` groupings and Mockito support:

```bash
mvn test
```

Tests cover:

* Basic rotations (2×2, 3×3)
* No-op cases (zero rotations, empty/null matrices)
* Negative rotation validation
* Stress test on a 200×300 matrix with a huge rotation count

### Example test structure

```
MatrixRotationServiceTest
 ├─ Valid rotations
 ├─ No-op and empty inputs
 ├─ Invalid rotation counts
 └─ Stress / large-matrix scenarios
```

## Project Structure

```
src/main/java/com/example/matrixrotation
 ├─ Application.java           # Spring Boot entry point
 ├─ controller/
 │   └─ MatrixRotationController.java
 ├─ dto/
 │   ├─ RotationRequest.java
 │   └─ RotationResponse.java
 ├─ service/
 │   └─ MatrixRotationService.java
 └─ exception/
     ├─ InvalidRotationException.java
     └─ GlobalExceptionHandler.java

src/test/java/com/example/matrixrotation
 └─ service/
     └─ MatrixRotationServiceTest.java
```

## Swagger / OpenAPI

With `springdoc-openapi-starter-webmvc-ui` on the classpath, visit:

* Swagger UI: `http://localhost:8080/swagger-ui.html` or `/swagger-ui/index.html`
* OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Packaging and Distribution

After `mvn clean package`, the fat-jar is located at:

```
target/matrix-rotation-service-0.0.1-SNAPSHOT.jar
```

Run with:

```bash
java -jar target/matrix-rotation-service-0.0.1-SNAPSHOT.jar
```

## Performance Tests

A stress test in the unit suite generates a 200×300 matrix and rotates it by a multiple of its perimeter, verifying a full-cycle no-op.

