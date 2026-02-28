# Student Management System

This is a Spring Boot 4.0.3 web application for managing student information, developed as a demonstration project for the CNPMNC course at HCMUT - CSE.

The system is a simple read-only application that displays student data stored in a PostgreSQL database.

--------------------------------------------------

ARCHITECTURE OVERVIEW

The application follows a standard Spring Boot MVC architecture using JPA (Hibernate) for data persistence.

Frontend: Thymeleaf (Server-side HTML rendering)
Backend: Spring Boot (REST + MVC Controllers)
Database: PostgreSQL
ORM: Hibernate (JPA)
Build Tool: Maven

--------------------------------------------------

KEY COMPONENTS

1. Entity Layer - Student.java

Represents the student model in the system.

Fields:
- id (String - manual primary key)
- name
- email
- age

Mapped to the "students" table in PostgreSQL.

--------------------------------------------------

2. Repository Layer - StudentRepository.java

Handles database interaction.

Extends:
JpaRepository<Student, String>

Custom method:
findByNameContainingIgnoreCase(String keyword)

Used for case-insensitive name search.

--------------------------------------------------

3. Service Layer - StudentService.java

Contains business logic.

Main methods:
- getAll()
- getById(String id)
- searchByName(String keyword)

--------------------------------------------------

4. Controller Layer

StudentWebController (@Controller)
Handles web requests.

Endpoint:
GET /students
Displays student list with optional name search.

Uses Thymeleaf template:
students.html

StudentController (@RestController)
Provides REST API.

Endpoints:
GET /api/students
GET /api/students/{id}

--------------------------------------------------

5. Frontend - students.html

- Displays student list in table format
- Includes search form
- Students under 18 are highlighted in red
- Uses simple inline CSS styling

--------------------------------------------------

DATABASE

Database: PostgreSQL
Table: students
Primary Key: id (String - manually assigned)

--------------------------------------------------

TECH STACK

- Spring Boot 4
- Spring MVC
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Maven
