# Employee Management REST API - Comprehensive Documentation

## Overview

This is a Spring Boot REST API application for managing employee data. It provides complete CRUD (Create, Read, Update, Delete) operations for employee management with an in-memory H2 database.

### Technology Stack
- **Spring Boot 3.3.2**
- **Java 22**
- **Spring Data JPA** - For data persistence
- **H2 Database** - In-memory database for testing
- **ModelMapper** - For DTO-Entity mapping
- **Lombok** - For reducing boilerplate code

## Project Structure

```
src/main/java/com/EmpDetails/Emp/
├── EmpApplication.java          # Main application class
├── ModelMapperConfig.java       # ModelMapper configuration
├── controller/
│   └── EmployeeController.java  # REST API endpoints
├── dto/
│   └── EmployeeDto.java         # Data Transfer Object
├── entities/
│   └── EmployeeEntity.java      # JPA Entity
├── repository/
│   └── EmployeeRepository.java  # Data access layer
└── services/
    └── EmployeeService.java     # Business logic layer
```

## Configuration

### Application Properties
```properties
spring.application.name=Emp
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### Database Access
- **H2 Console URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)

## Data Models

### Employee Entity (`EmployeeEntity.java`)

```java
@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private LocalDate dateOfJoining;
    @JsonProperty("isactive")
    private boolean isactive;
}
```

**Fields:**
- `id` (int): Auto-generated primary key
- `name` (String): Employee name
- `dateOfJoining` (LocalDate): Date when employee joined
- `isactive` (boolean): Employee active status

### Employee DTO (`EmployeeDto.java`)

```java
@Data
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private LocalDate dateOfJoining;
    private boolean isactive;
}
```

**Purpose:** Data Transfer Object for API requests/responses, separating internal entity structure from external API contract.

## Repository Layer

### Employee Repository (`EmployeeRepository.java`)

```java
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
}
```

**Inherited Methods:**
- `save(entity)` - Save an employee
- `findById(id)` - Find employee by ID
- `findAll()` - Get all employees
- `deleteById(id)` - Delete employee by ID
- `existsById(id)` - Check if employee exists

## Service Layer

### Employee Service (`EmployeeService.java`)

#### Constructor
```java
public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository)
```

#### Public Methods

##### `getEmployeeById(Long id)`
**Purpose:** Retrieve an employee by their ID
**Parameters:** 
- `id` (Long): Employee ID
**Returns:** `EmployeeDto` - Employee data
**Example Usage:**
```java
EmployeeDto employee = employeeService.getEmployeeById(1L);
```

##### `createEmployee(EmployeeDto employeeDto)`
**Purpose:** Create a new employee
**Parameters:** 
- `employeeDto` (EmployeeDto): Employee data to create
**Returns:** `EmployeeDto` - Created employee with generated ID
**Example Usage:**
```java
EmployeeDto newEmployee = new EmployeeDto();
newEmployee.setName("John Doe");
newEmployee.setDateOfJoining(LocalDate.now());
newEmployee.setIsactive(true);

EmployeeDto createdEmployee = employeeService.createEmployee(newEmployee);
```

##### `getAllEmployees()`
**Purpose:** Retrieve all employees
**Parameters:** None
**Returns:** `List<EmployeeDto>` - List of all employees
**Example Usage:**
```java
List<EmployeeDto> employees = employeeService.getAllEmployees();
```

##### `deleteEmployeeById(Long id)`
**Purpose:** Delete an employee by ID
**Parameters:** 
- `id` (Long): Employee ID to delete
**Returns:** `boolean` - true if deleted successfully, false if employee not found
**Example Usage:**
```java
boolean deleted = employeeService.deleteEmployeeById(1L);
```

## REST API Endpoints

### Base URL
```
http://localhost:8080/employees
```

### 1. Get Employee by ID

**Endpoint:** `GET /employees/{id}`
**Description:** Retrieve a specific employee by their ID

**Path Parameters:**
- `id` (Long): Employee ID

**Response:**
```json
{
    "id": 1,
    "name": "John Doe",
    "dateOfJoining": "2024-01-15",
    "isactive": true
}
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/employees/1
```

**Java Example:**
```java
@GetMapping(path="{id}")
public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
    return employeeService.getEmployeeById(id);
}
```

### 2. Create New Employee

**Endpoint:** `POST /employees`
**Description:** Create a new employee

**Request Body:**
```json
{
    "name": "Jane Smith",
    "dateOfJoining": "2024-01-20",
    "isactive": true
}
```

**Response:**
```json
{
    "id": 2,
    "name": "Jane Smith",
    "dateOfJoining": "2024-01-20",
    "isactive": true
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "dateOfJoining": "2024-01-20",
    "isactive": true
  }'
```

**Java Example:**
```java
@PostMapping()
public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
    return employeeService.createEmployee(employeeDto);
}
```

### 3. Get All Employees

**Endpoint:** `GET /employees/allemp`
**Description:** Retrieve all employees

**Response:**
```json
[
    {
        "id": 1,
        "name": "John Doe",
        "dateOfJoining": "2024-01-15",
        "isactive": true
    },
    {
        "id": 2,
        "name": "Jane Smith",
        "dateOfJoining": "2024-01-20",
        "isactive": true
    }
]
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/employees/allemp
```

### 4. Delete Employee

**Endpoint:** `DELETE /employees/{id}`
**Description:** Delete an employee by ID

**Path Parameters:**
- `id` (Long): Employee ID to delete

**Response:**
```json
true
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/employees/1
```

**Java Example:**
```java
@DeleteMapping(path = "/{id}")
public boolean deleteEmployeeById(@PathVariable Long id) {
    return employeeService.deleteEmployeeById(id);
}
```

## Configuration Components

### ModelMapper Configuration (`ModelMapperConfig.java`)

```java
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

**Purpose:** Provides a ModelMapper bean for automatic mapping between Entity and DTO objects.

### Main Application Class (`EmpApplication.java`)

```java
@SpringBootApplication
public class EmpApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmpApplication.class, args);
    }
}
```

**Purpose:** Main entry point for the Spring Boot application.

## Usage Instructions

### 1. Running the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or using Maven
mvn spring-boot:run

# Application will start on http://localhost:8080
```

### 2. Testing the API

#### Create an Employee
```bash
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "dateOfJoining": "2024-01-25",
    "isactive": true
  }'
```

#### Get All Employees
```bash
curl -X GET http://localhost:8080/employees/allemp
```

#### Get Specific Employee
```bash
curl -X GET http://localhost:8080/employees/1
```

#### Delete Employee
```bash
curl -X DELETE http://localhost:8080/employees/1
```

### 3. Database Access

Access the H2 database console at: `http://localhost:8080/h2-console`

**Connection Details:**
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password: (leave empty)

### 4. Sample Data Operations

#### Complete Workflow Example
```bash
# 1. Create first employee
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "dateOfJoining": "2024-01-15", "isactive": true}'

# 2. Create second employee  
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Smith", "dateOfJoining": "2024-01-20", "isactive": false}'

# 3. Get all employees
curl -X GET http://localhost:8080/employees/allemp

# 4. Get specific employee by ID
curl -X GET http://localhost:8080/employees/1

# 5. Delete employee
curl -X DELETE http://localhost:8080/employees/1
```

## Error Handling

The application uses Spring Boot's default error handling. Common scenarios:

- **404 Not Found**: When requesting a non-existent employee ID
- **400 Bad Request**: When sending invalid JSON data
- **500 Internal Server Error**: For database or mapping errors

## Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## API Testing with Postman

### Collection Setup
1. Create a new Postman collection named "Employee Management API"
2. Set base URL variable: `{{baseUrl}}` = `http://localhost:8080`

### Example Requests

#### 1. Create Employee
- **Method**: POST
- **URL**: `{{baseUrl}}/employees`
- **Headers**: `Content-Type: application/json`
- **Body**: 
```json
{
    "name": "{{$randomFullName}}",
    "dateOfJoining": "2024-01-{{$randomInt}}",
    "isactive": true
}
```

#### 2. Get All Employees
- **Method**: GET  
- **URL**: `{{baseUrl}}/employees/allemp`

#### 3. Get Employee by ID
- **Method**: GET
- **URL**: `{{baseUrl}}/employees/{{employeeId}}`

#### 4. Delete Employee
- **Method**: DELETE
- **URL**: `{{baseUrl}}/employees/{{employeeId}}`

## Best Practices Implemented

1. **Separation of Concerns**: Clear separation between Controller, Service, Repository, and Entity layers
2. **DTO Pattern**: Using DTOs to decouple API contracts from internal data models
3. **Dependency Injection**: Constructor-based dependency injection for better testability
4. **RESTful Design**: Following REST conventions for API endpoints
5. **Configuration Management**: Externalized configuration in application.properties

## Future Enhancements

1. **Input Validation**: Add @Valid annotations and custom validators
2. **Exception Handling**: Implement custom exception handlers
3. **Pagination**: Add pagination support for getAllEmployees
4. **Search and Filtering**: Add search capabilities
5. **Update Operation**: Implement PUT/PATCH endpoints for updates
6. **Authentication**: Add security layer
7. **API Documentation**: Integrate Swagger/OpenAPI
8. **Unit Tests**: Add comprehensive test coverage