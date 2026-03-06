# Job Service - Project Summary

## Project Information
- **Service Name**: Job Service
- **Module Owner**: Anil
- **Platform**: RevHire Recruitment Platform
- **Port**: 8084
- **Database**: revhire_job_db (MySQL)
- **Spring Boot Version**: 3.3.2
- **Spring Cloud Version**: 2023.0.2
- **Java Version**: 17

## Complete File Structure

```
p3-job-service/
├── pom.xml
├── .gitignore
├── README.md
├── API-DOCUMENTATION.md
├── PROJECT-SUMMARY.md
├── database-setup.sql
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── revature/
    │   │           └── jobservice/
    │   │               ├── JobServiceApplication.java (Main Application)
    │   │               ├── client/
    │   │               │   ├── AuthServiceClient.java
    │   │               │   ├── EmployerServiceClient.java
    │   │               │   └── NotificationServiceClient.java
    │   │               ├── controller/
    │   │               │   ├── JobController.java (Public endpoints)
    │   │               │   ├── JobSearchController.java (Job seeker endpoints)
    │   │               │   └── EmployerJobController.java (Employer endpoints)
    │   │               ├── dto/
    │   │               │   ├── ApiResponse.java
    │   │               │   ├── JobRequest.java
    │   │               │   ├── JobResponse.java
    │   │               │   ├── JobSearchRequest.java
    │   │               │   └── JobStatisticsResponse.java
    │   │               ├── entity/
    │   │               │   └── JobPosting.java
    │   │               ├── enums/
    │   │               │   └── JobStatus.java (OPEN, CLOSED, FILLED)
    │   │               ├── exception/
    │   │               │   ├── ApiException.java
    │   │               │   ├── BadRequestException.java
    │   │               │   ├── ForbiddenException.java
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   └── NotFoundException.java
    │   │               ├── repository/
    │   │               │   └── JobPostingRepository.java
    │   │               ├── service/
    │   │               │   └── JobService.java (361 lines - Complete business logic)
    │   │               └── util/
    │   │                   └── InputSanitizer.java
    │   └── resources/
    │       └── application.yml
    └── test/
        └── java/
            └── com/
                └── revature/
                    └── jobservice/

```

## Files Created (27 total)

### Configuration Files (3)
1. `pom.xml` - Maven configuration with all dependencies
2. `application.yml` - Spring Boot application configuration
3. `.gitignore` - Git ignore file

### Documentation Files (3)
4. `README.md` - Project overview and setup instructions
5. `API-DOCUMENTATION.md` - Complete API documentation
6. `database-setup.sql` - Database setup script

### Java Source Files (22)

#### Main Application (1)
7. `JobServiceApplication.java` - Spring Boot application entry point

#### Entities (1)
8. `JobPosting.java` - Job posting entity with all fields

#### Enums (1)
9. `JobStatus.java` - Job status enumeration (OPEN, CLOSED, FILLED)

#### DTOs (5)
10. `ApiResponse.java` - Generic API response wrapper
11. `JobRequest.java` - Job creation/update request DTO
12. `JobResponse.java` - Job response DTO
13. `JobSearchRequest.java` - Job search filter request DTO
14. `JobStatisticsResponse.java` - Job statistics response DTO

#### Repositories (1)
15. `JobPostingRepository.java` - JPA repository with custom queries

#### Services (1)
16. `JobService.java` - Complete business logic (361 lines)

#### Controllers (3)
17. `JobController.java` - Public job endpoints
18. `JobSearchController.java` - Job seeker search endpoints
19. `EmployerJobController.java` - Employer job management endpoints

#### Feign Clients (3)
20. `AuthServiceClient.java` - Auth service integration
21. `EmployerServiceClient.java` - Employer service integration
22. `NotificationServiceClient.java` - Notification service integration

#### Exceptions (5)
23. `ApiException.java` - Base API exception
24. `BadRequestException.java` - 400 Bad Request exception
25. `ForbiddenException.java` - 403 Forbidden exception
26. `NotFoundException.java` - 404 Not Found exception
27. `GlobalExceptionHandler.java` - Global exception handler

#### Utilities (1)
28. `InputSanitizer.java` - Input sanitization utility

## Features Implemented

### Job Management
- ✅ Create job posting
- ✅ Update job posting
- ✅ Delete job posting
- ✅ Get job by ID
- ✅ Get all open jobs
- ✅ Get employer's jobs
- ✅ Search jobs with filters
- ✅ Close job
- ✅ Reopen job
- ✅ Mark job as filled
- ✅ Get job statistics

### Job Search Filters
- ✅ Filter by title (partial match)
- ✅ Filter by location (partial match)
- ✅ Filter by company name (partial match)
- ✅ Filter by job type (exact match)
- ✅ Filter by max experience years
- ✅ Filter by minimum salary
- ✅ Filter by maximum salary
- ✅ Filter by date posted

### Security & Validation
- ✅ Input sanitization (XSS & SQL injection prevention)
- ✅ Jakarta validation on request DTOs
- ✅ Ownership verification for employer operations
- ✅ Business logic validation
- ✅ Global exception handling

### Inter-Service Communication
- ✅ Feign client for Auth Service
- ✅ Feign client for Employer Service
- ✅ Feign client for Notification Service

### Database
- ✅ JPA entity with proper mappings
- ✅ Custom repository methods
- ✅ Automatic timestamp management
- ✅ Indexes for performance

## API Endpoints Summary

### Public (2 endpoints)
- GET /api/jobs - Get all open jobs
- GET /api/jobs/{jobId} - Get job by ID

### Job Seeker (1 endpoint)
- GET /api/jobseeker/jobs/search - Search jobs with filters

### Employer (8 endpoints)
- GET /api/employer/jobs - Get employer's jobs
- POST /api/employer/jobs - Create job
- PUT /api/employer/jobs/{jobId} - Update job
- DELETE /api/employer/jobs/{jobId} - Delete job
- PATCH /api/employer/jobs/{jobId}/close - Close job
- PATCH /api/employer/jobs/{jobId}/reopen - Reopen job
- PATCH /api/employer/jobs/{jobId}/fill - Mark as filled
- GET /api/employer/jobs/statistics - Get statistics

**Total: 11 API endpoints**

## Dependencies

### Spring Boot Starters
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation

### Spring Cloud
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign

### Database
- mysql-connector-j

### Utilities
- lombok

## Database Schema

### Table: job_postings
- id (BIGINT, PK, Auto Increment)
- employer_id (BIGINT, NOT NULL)
- company_name (VARCHAR(255), NOT NULL)
- title (VARCHAR(255), NOT NULL)
- description (TEXT)
- skills (TEXT)
- education (VARCHAR(200))
- max_experience_years (INT)
- location (VARCHAR(255), NOT NULL)
- min_salary (DECIMAL(10,2))
- max_salary (DECIMAL(10,2))
- job_type (VARCHAR(50))
- openings (INT, NOT NULL, DEFAULT 1)
- application_deadline (DATE)
- status (VARCHAR(20), NOT NULL, DEFAULT 'OPEN')
- created_at (TIMESTAMP, NOT NULL)
- updated_at (TIMESTAMP)

## How to Run

1. **Prerequisites**
   - JDK 17 installed
   - Maven installed
   - MySQL Server running
   - Eureka Server running on port 8761

2. **Setup Database**
   ```bash
   mysql -u root -p < database-setup.sql
   ```

3. **Update Database Credentials** (if needed)
   Edit `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       username: your_username
       password: your_password
   ```

4. **Build the Project**
   ```bash
   cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-job-service
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

6. **Verify Service is Running**
   ```bash
   curl http://localhost:8084/api/jobs
   ```

## Testing the API

### Example: Create a Job
```bash
curl -X POST http://localhost:8084/api/employer/jobs \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "title": "Senior Java Developer",
    "description": "We are looking for an experienced Java developer.",
    "skills": "Java, Spring Boot, Microservices",
    "education": "Bachelor degree",
    "maxExperienceYears": 5,
    "location": "New York, NY",
    "minSalary": 100000,
    "maxSalary": 150000,
    "jobType": "Full-time",
    "openings": 2,
    "applicationDeadline": "2024-12-31"
  }'
```

### Example: Search Jobs
```bash
curl "http://localhost:8084/api/jobseeker/jobs/search?title=Java&location=New%20York&minSalary=80000"
```

### Example: Get Statistics
```bash
curl -X GET http://localhost:8084/api/employer/jobs/statistics \
  -H "X-User-Id: 1"
```

## Key Design Patterns

1. **Repository Pattern** - Data access abstraction
2. **Service Layer Pattern** - Business logic separation
3. **DTO Pattern** - Data transfer between layers
4. **Builder Pattern** - Response object creation
5. **Exception Handling Pattern** - Centralized error handling

## Best Practices Implemented

- ✅ Clean code architecture (Controller → Service → Repository)
- ✅ Separation of concerns
- ✅ Input validation at multiple levels
- ✅ Security measures (sanitization)
- ✅ Comprehensive error handling
- ✅ Logging for debugging
- ✅ Transaction management
- ✅ RESTful API design
- ✅ Proper HTTP status codes
- ✅ Consistent response format

## Next Steps

1. Add unit tests for service layer
2. Add integration tests for controllers
3. Implement pagination for job listings
4. Add caching for frequently accessed data
5. Implement rate limiting
6. Add API documentation using Swagger/OpenAPI
7. Implement advanced search with Elasticsearch
8. Add job posting analytics
9. Implement job expiration based on deadline

## Support

For questions or issues, contact Anil (Module Owner).
