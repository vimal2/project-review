# Job Service - RevHire Recruitment Platform

## Overview
The Job Service is a microservice for managing job postings in the RevHire recruitment platform. It provides functionality for employers to create, update, and manage job postings, and for job seekers to search and view available jobs.

## Technology Stack
- Java 17
- Spring Boot 3.3.2
- Spring Cloud 2023.0.2
- Spring Data JPA
- MySQL
- Eureka Client (Service Discovery)
- OpenFeign (Inter-service Communication)
- Lombok
- Maven

## Port
- **8084**

## Database
- **Name**: revhire_job_db
- **Host**: localhost:3306
- **Username**: root
- **Password**: root

## Features

### Job Management
- Create, update, and delete job postings
- View all open jobs
- Search jobs with multiple filters
- Close, reopen, and mark jobs as filled
- Get employer job statistics

### Job Status
- **OPEN**: Job is currently accepting applications
- **CLOSED**: Job is temporarily closed
- **FILLED**: Job position has been filled

## API Endpoints

### Public Endpoints (JobController)
- `GET /api/jobs` - Get all open jobs
- `GET /api/jobs/{jobId}` - Get job by ID

### Job Seeker Endpoints (JobSearchController)
- `GET /api/jobseeker/jobs/search` - Search jobs with filters
  - Query Parameters:
    - title
    - location
    - companyName
    - jobType
    - maxExperienceYears
    - minSalary
    - maxSalary
    - datePostedAfter

### Employer Endpoints (EmployerJobController)
All endpoints require `X-User-Id` header

- `GET /api/employer/jobs` - Get employer's jobs
- `POST /api/employer/jobs` - Create new job
- `PUT /api/employer/jobs/{jobId}` - Update job
- `DELETE /api/employer/jobs/{jobId}` - Delete job
- `PATCH /api/employer/jobs/{jobId}/close` - Close job
- `PATCH /api/employer/jobs/{jobId}/reopen` - Reopen job
- `PATCH /api/employer/jobs/{jobId}/fill` - Mark job as filled
- `GET /api/employer/jobs/statistics` - Get job statistics

## Project Structure
```
src/main/java/com/revature/jobservice/
├── JobServiceApplication.java
├── client/
│   ├── AuthServiceClient.java
│   ├── EmployerServiceClient.java
│   └── NotificationServiceClient.java
├── controller/
│   ├── EmployerJobController.java
│   ├── JobController.java
│   └── JobSearchController.java
├── dto/
│   ├── ApiResponse.java
│   ├── JobRequest.java
│   ├── JobResponse.java
│   ├── JobSearchRequest.java
│   └── JobStatisticsResponse.java
├── entity/
│   └── JobPosting.java
├── enums/
│   └── JobStatus.java
├── exception/
│   ├── ApiException.java
│   ├── BadRequestException.java
│   ├── ForbiddenException.java
│   ├── GlobalExceptionHandler.java
│   └── NotFoundException.java
├── repository/
│   └── JobPostingRepository.java
├── service/
│   └── JobService.java
└── util/
    └── InputSanitizer.java
```

## Configuration
See `application.yml` for detailed configuration.

## Building and Running

### Prerequisites
- JDK 17
- Maven
- MySQL Server
- Eureka Server running on port 8761

### Setup Database
```sql
CREATE DATABASE revhire_job_db;
```

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

## Inter-Service Communication

### Feign Clients
- **AUTH-SERVICE**: Validates user authentication
- **EMPLOYER-SERVICE**: Retrieves employer/company information
- **NOTIFICATION-SERVICE**: Sends notifications for job events

## Validation
- Input sanitization to prevent XSS and SQL injection
- Request validation using Jakarta Validation
- Business logic validation (e.g., salary ranges, ownership verification)

## Error Handling
- Global exception handler for consistent error responses
- Custom exceptions: ApiException, BadRequestException, NotFoundException, ForbiddenException
- Validation error mapping

## Author
Anil's Module - RevHire Recruitment Platform
