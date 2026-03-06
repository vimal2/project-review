# Employer Service - Project Structure

## Directory Structure

```
p3-employer-service/
├── pom.xml
├── README.md
├── .gitignore
├── PROJECT_STRUCTURE.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── revhire/
│   │   │           └── employerservice/
│   │   │               ├── EmployerServiceApplication.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   └── EmployerProfile.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── ApiResponse.java
│   │   │               │   ├── ApplicationResponse.java
│   │   │               │   ├── EmployerCompanyProfileRequest.java
│   │   │               │   ├── EmployerCompanyProfileResponse.java
│   │   │               │   ├── EmployerStatisticsResponse.java
│   │   │               │   ├── JobResponse.java
│   │   │               │   └── UserDetailsResponse.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   └── EmployerProfileRepository.java
│   │   │               │
│   │   │               ├── client/
│   │   │               │   ├── AuthServiceClient.java
│   │   │               │   ├── JobServiceClient.java
│   │   │               │   └── ApplicationServiceClient.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   └── EmployerService.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── EmployerController.java
│   │   │               │
│   │   │               ├── util/
│   │   │               │   └── InputSanitizer.java
│   │   │               │
│   │   │               └── exception/
│   │   │                   ├── ApiException.java
│   │   │                   ├── BadRequestException.java
│   │   │                   ├── NotFoundException.java
│   │   │                   ├── ForbiddenException.java
│   │   │                   └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       └── application.yml
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── revhire/
│       │           └── employerservice/
│       │               └── EmployerServiceApplicationTests.java
│       │
│       └── resources/
│           └── application-test.yml
```

## Component Overview

### Core Application
- **EmployerServiceApplication.java**: Main Spring Boot application class with Eureka and Feign enabled

### Entity Layer
- **EmployerProfile**: JPA entity representing employer company profiles
  - Fields: id, userId, companyName, industry, companySize, companyDescription, website, companyLocation, timestamps

### Data Transfer Objects (DTOs)
- **EmployerCompanyProfileRequest**: Request DTO for creating/updating company profiles
- **EmployerCompanyProfileResponse**: Response DTO for company profile data
- **EmployerStatisticsResponse**: Aggregated statistics from multiple services
- **ApiResponse**: Generic wrapper for API responses
- **JobResponse**: DTO for job data from Job Service
- **ApplicationResponse**: DTO for application data from Application Service
- **UserDetailsResponse**: DTO for user data from Auth Service

### Repository Layer
- **EmployerProfileRepository**: Spring Data JPA repository with custom queries
  - findByUserId(Long userId)
  - existsByUserId(Long userId)

### Feign Clients (External Service Integration)
- **AuthServiceClient**: Communicates with AUTH-SERVICE
  - getUserById(Long userId)

- **JobServiceClient**: Communicates with JOB-SERVICE
  - getJobsByEmployer(Long employerId)
  - getJobStatistics(Long employerId)

- **ApplicationServiceClient**: Communicates with APPLICATION-SERVICE
  - getApplicationsByEmployer(Long employerId)
  - getApplicationStatistics(Long employerId)

### Service Layer
- **EmployerService**: Business logic for employer operations
  - getCompanyProfile(Long userId)
  - updateCompanyProfile(Long userId, EmployerCompanyProfileRequest request)
  - getStatistics(Long userId) - Aggregates data from Job and Application services

### Controller Layer
- **EmployerController**: REST API endpoints
  - GET /api/employer/company-profile
  - PUT /api/employer/company-profile
  - GET /api/employer/statistics
  - GET /api/employer/health

### Utilities
- **InputSanitizer**: Input validation and sanitization
  - XSS protection
  - SQL injection prevention
  - Email and URL sanitization

### Exception Handling
- **ApiException**: Base exception class with HTTP status
- **BadRequestException**: HTTP 400 errors
- **NotFoundException**: HTTP 404 errors
- **ForbiddenException**: HTTP 403 errors
- **GlobalExceptionHandler**: Centralized exception handling with @RestControllerAdvice

### Configuration
- **application.yml**: Main configuration
  - Server port: 8083
  - Database: MySQL (revhire_employer_db)
  - Eureka client configuration
  - Feign client configuration
  - Logging configuration

- **application-test.yml**: Test configuration with H2 in-memory database

## Key Features

### Security
- Input sanitization for all user inputs
- XSS attack prevention
- SQL injection protection
- Request validation using Jakarta Validation

### Service Integration
- Eureka service discovery
- Feign clients for inter-service communication
- Circuit breaker support
- Timeout configuration

### Data Management
- JPA/Hibernate for ORM
- MySQL database
- Automatic schema generation
- Timestamp auditing

### API Design
- RESTful endpoints
- Consistent response format using ApiResponse wrapper
- Header-based authentication (X-User-Id)
- Comprehensive error handling

### Monitoring
- Health check endpoints
- Actuator endpoints (health, info, metrics, prometheus)
- Detailed logging with SLF4J
- SQL query logging for debugging

## Database Schema

### employer_profiles
| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| user_id | BIGINT | UNIQUE, NOT NULL |
| company_name | VARCHAR(200) | NOT NULL |
| industry | VARCHAR(100) | |
| company_size | VARCHAR(50) | |
| company_description | VARCHAR(2000) | |
| website | VARCHAR(255) | |
| company_location | VARCHAR(255) | |
| created_at | TIMESTAMP | NOT NULL |
| updated_at | TIMESTAMP | |

## API Request/Response Examples

### Update Company Profile Request
```json
{
  "companyName": "Tech Innovations Inc",
  "industry": "Information Technology",
  "companySize": "100-500",
  "companyDescription": "Leading provider of cloud solutions",
  "website": "https://techinnovations.com",
  "companyLocation": "San Francisco, CA"
}
```

### Company Profile Response
```json
{
  "success": true,
  "message": "Company profile retrieved successfully",
  "data": {
    "id": 1,
    "userId": 123,
    "companyName": "Tech Innovations Inc",
    "industry": "Information Technology",
    "companySize": "100-500",
    "companyDescription": "Leading provider of cloud solutions",
    "website": "https://techinnovations.com",
    "companyLocation": "San Francisco, CA",
    "createdAt": "2024-03-06T10:00:00",
    "updatedAt": "2024-03-06T15:30:00"
  },
  "timestamp": "2024-03-06T15:30:00"
}
```

### Statistics Response
```json
{
  "success": true,
  "message": "Statistics retrieved successfully",
  "data": {
    "totalJobsPosted": 15,
    "activeJobs": 10,
    "closedJobs": 5,
    "totalApplicationsReceived": 245,
    "applicationsByStatus": {
      "PENDING": 50,
      "REVIEWED": 100,
      "ACCEPTED": 45,
      "REJECTED": 50
    },
    "pendingApplications": 50,
    "reviewedApplications": 100,
    "acceptedApplications": 45,
    "rejectedApplications": 50
  },
  "timestamp": "2024-03-06T15:30:00"
}
```

## Dependencies

### Spring Boot Starters
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-test

### Spring Cloud
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign

### Database
- mysql-connector-j

### Utilities
- lombok

## Module Ownership
**Owner:** Venkatesh
**Purpose:** Employer profile management and statistics aggregation for RevHire recruitment platform
