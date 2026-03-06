# Employer Service - RevHire Platform

## Developer
**Venkatesh**

## Responsibility
Employer Profile and Job Posting Management

## Overview
The Employer Service is a microservice responsible for managing employer company profiles, job postings, and applicant interactions. It provides comprehensive APIs for employers to create and manage their company profiles, post job openings, and view applicants.

## Technology Stack
- **Java**: 17
- **Spring Boot**: 3.2.5
- **Spring Cloud**: 2023.0.1
- **Database**: MySQL
- **Service Discovery**: Eureka Client
- **Configuration**: Spring Cloud Config Client
- **Inter-service Communication**: OpenFeign
- **Build Tool**: Maven

## Architecture
This service follows a layered architecture:
- **Controller Layer**: REST API endpoints
- **Service Layer**: Business logic
- **Repository Layer**: Data access
- **Entity Layer**: Domain models
- **DTO Layer**: Data transfer objects
- **Client Layer**: Feign clients for inter-service communication
- **Event Layer**: Event publishing and listening

## Key Features

### 1. Employer Profile Management
- Create and update company profiles
- Store company information (name, industry, size, location, website)
- Retrieve employer profile by user ID

### 2. Job Posting Management
- Create new job postings
- Update existing job postings
- Delete job postings
- Change job status (OPEN, CLOSED, FILLED)
- Search and filter job postings
- View all jobs for an employer

### 3. Applicant Management
- View all applicants for employer's jobs
- Filter applicants by status
- Search applicants by name, email, or skills
- Update application status
- Add notes to applications
- Shortlist or reject applicants

### 4. Event Publishing
- Job posting events (created, updated, status changed)
- Integration with notification service
- Asynchronous event processing

### 5. Internal APIs
- Inter-service communication endpoints
- Job data access for other services
- Employer verification

## Database Schema

### employer_profiles Table
```sql
CREATE TABLE employer_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    company_name VARCHAR(200) NOT NULL,
    industry VARCHAR(100),
    company_size VARCHAR(50),
    company_description VARCHAR(2000),
    website VARCHAR(255),
    company_location VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

### job_postings Table
```sql
CREATE TABLE job_postings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employer_id BIGINT NOT NULL,
    company_name VARCHAR(120),
    title VARCHAR(150) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    skills VARCHAR(1000),
    education VARCHAR(250),
    max_experience_years INT NOT NULL,
    location VARCHAR(120) NOT NULL,
    min_salary DECIMAL(12, 2) NOT NULL,
    max_salary DECIMAL(12, 2) NOT NULL,
    job_type VARCHAR(50) NOT NULL,
    openings INT NOT NULL,
    application_deadline DATE,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

## API Endpoints

### Employer Profile Endpoints
- `GET /api/employer/company-profile` - Get company profile
- `PUT /api/employer/company-profile` - Update company profile
- `GET /api/employer/company-profile/statistics` - Get employer statistics

### Job Posting Endpoints
- `GET /api/employer/jobs` - Get all jobs for employer
- `GET /api/employer/jobs/{jobId}` - Get specific job
- `POST /api/employer/jobs` - Create new job posting
- `PUT /api/employer/jobs/{jobId}` - Update job posting
- `DELETE /api/employer/jobs/{jobId}` - Delete job posting
- `PATCH /api/employer/jobs/{jobId}/close` - Close job posting
- `PATCH /api/employer/jobs/{jobId}/reopen` - Reopen job posting
- `PATCH /api/employer/jobs/{jobId}/fill` - Mark job as filled

### Applicant Management Endpoints
- `GET /api/employer/applicants` - Get all applicants (with filtering)
- `GET /api/employer/applicants/job/{jobId}` - Get applicants for specific job
- `PATCH /api/employer/applicants/{applicationId}/status` - Update application status
- `PATCH /api/employer/applicants/{applicationId}/shortlist` - Shortlist applicant
- `PATCH /api/employer/applicants/{applicationId}/reject` - Reject applicant
- `PATCH /api/employer/applicants/{applicationId}/under-review` - Mark as under review
- `PATCH /api/employer/applicants/{applicationId}/notes` - Update application notes

### Internal Endpoints (Inter-service Communication)
- `GET /internal/employer/profile/{userId}` - Get employer profile by user ID
- `GET /internal/employer/jobs/open` - Get all open jobs
- `GET /internal/employer/jobs/{jobId}` - Get job by ID
- `GET /internal/employer/verify/{userId}` - Verify employer exists
- `GET /internal/employer/{userId}/company-name` - Get company name
- `GET /internal/employer/health` - Internal health check

## Feign Clients

### AuthServiceClient
Communicates with Auth Service to retrieve user details.

### ApplicationServiceClient
Communicates with Application Service to retrieve application data and statistics.

### NotificationClient
Sends notifications to Notification Service for job posting events and applicant updates.

## Event System

### Events Published
- **JOB_CREATED**: When a new job posting is created
- **JOB_UPDATED**: When a job posting is updated
- **JOB_CLOSED**: When a job posting is closed
- **JOB_REOPENED**: When a closed job is reopened
- **JOB_FILLED**: When a job is marked as filled
- **JOB_DELETED**: When a job posting is deleted

### Event Listeners
- `JobPostingEventListener`: Listens to job posting events and triggers notifications

## Configuration

### Application Properties
The service is configured via `application.yml`:
- Server port: 8083
- Database: MySQL (revhire_employer_db)
- Eureka server: http://localhost:8761/eureka/

### Environment Variables
Required environment variables:
- `DB_URL`: Database connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `EUREKA_URL`: Eureka server URL

## Dependencies

### External Services
- **Auth Service**: User authentication and authorization
- **Application Service**: Job application management
- **Notification Service**: Notification delivery
- **Eureka Server**: Service discovery
- **Config Server**: Centralized configuration

## Security
- User authentication via X-User-Id header
- Authorization checks for employer-specific resources
- Input sanitization to prevent XSS attacks
- CORS configuration for frontend integration

## Error Handling
Global exception handling with standardized error responses:
- `NotFoundException` (404): Resource not found
- `BadRequestException` (400): Invalid request data
- `ForbiddenException` (403): Unauthorized access
- `ValidationException` (400): Request validation errors
- `FeignException`: External service communication errors

## Logging
- DEBUG level for application logs
- INFO level for Spring Cloud logs
- Request/response logging for Feign clients
- Event processing logs

## Build and Run

### Prerequisites
- Java 17
- Maven 3.6+
- MySQL 8.0+
- Eureka Server running on port 8761

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

### Docker
```bash
docker build -t employer-service .
docker run -p 8083:8083 employer-service
```

## Testing
Run tests with:
```bash
mvn test
```

## Monitoring
Actuator endpoints available at:
- `/actuator/health` - Health check
- `/actuator/info` - Service information
- `/actuator/metrics` - Metrics
- `/actuator/prometheus` - Prometheus metrics

## Package Structure
```
com.revhire.employerservice
├── client                  # Feign clients
│   ├── ApplicationServiceClient
│   ├── AuthServiceClient
│   ├── NotificationClient
│   └── JobServiceClient
├── config                  # Configuration classes
│   ├── AppConfig
│   ├── AsyncConfig
│   ├── CorsConfig
│   └── FeignConfig
├── controller              # REST controllers
│   ├── ApplicantController
│   ├── EmployerProfileController
│   ├── InternalController
│   └── JobPostingController
├── dto                     # Data transfer objects
│   ├── ApiResponse
│   ├── ApplicantResponse
│   ├── ApplicationResponse
│   ├── EmployerProfileRequest
│   ├── EmployerProfileResponse
│   ├── EmployerStatisticsResponse
│   ├── JobPostingRequest
│   ├── JobPostingResponse
│   ├── JobResponse
│   └── UserDetailsResponse
├── entity                  # JPA entities
│   ├── EmployerProfile
│   ├── JobPosting
│   └── JobStatus
├── event                   # Event publishing/listening
│   ├── EventPublisher
│   ├── JobPostingEvent
│   └── JobPostingEventListener
├── exception               # Exception handling
│   ├── ApiException
│   ├── BadRequestException
│   ├── ForbiddenException
│   ├── GlobalExceptionHandler
│   └── NotFoundException
├── repository              # JPA repositories
│   ├── EmployerProfileRepository
│   └── JobPostingRepository
├── service                 # Business logic
│   ├── ApplicantService
│   ├── EmployerService
│   └── JobPostingService
├── util                    # Utility classes
│   └── InputSanitizer
└── EmployerServiceApplication  # Main application class
```

## Future Enhancements
- Job posting analytics and insights
- Employer dashboard with visualizations
- Advanced applicant filtering and ranking
- Interview scheduling integration
- Job posting templates
- Bulk job posting operations
- Export applicant data to CSV/PDF
- Integration with external job boards

## Support
For issues or questions, contact Venkatesh or create an issue in the repository.

## License
Copyright (c) 2024 RevHire Platform
