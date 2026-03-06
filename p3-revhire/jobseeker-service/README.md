# JobSeeker Service

JobSeeker Service is a microservice for the RevHire recruitment platform that manages job seeker profiles and resumes.

## Module Owner
Madhusudhan

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Spring Cloud 2023.0.2
- Spring Data JPA
- MySQL Database
- Netflix Eureka Client
- OpenFeign
- Lombok

## Features

### Job Seeker Profile Management
- Create and update job seeker profiles
- Store skills, education, certifications
- Track employment status (FRESHER, EMPLOYED, UNEMPLOYED)
- Store professional headline and summary
- Location tracking

### Resume Management
- Create and update resume details
- Store objective, education, experience, projects, certifications
- Upload formatted resume files (PDF, DOC, DOCX)
- File size limit: 2MB
- Download resume files (internal endpoint for employer service)
- Store resume data in database

## API Endpoints

### Job Seeker Profile Controller
- `GET /api/jobseeker/profile` - Get profile (header: X-User-Id)
- `PUT /api/jobseeker/profile` - Update profile (header: X-User-Id)

### Resume Controller
- `GET /api/resume` - Get resume (header: X-User-Id)
- `PUT /api/resume` - Update resume (header: X-User-Id)
- `POST /api/resume/upload` - Upload resume file (header: X-User-Id)
- `GET /api/resume/download/{userId}` - Download resume (internal)

## Configuration

### Application Properties
- **Port**: 8082
- **Service Name**: JOBSEEKER-SERVICE
- **Database**: revhire_jobseeker_db
- **Eureka Server**: http://localhost:8761/eureka/

### Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revhire_jobseeker_db
    username: root
    password: root
```

## Database Schema

### jobseeker_profiles
- id (Primary Key)
- user_id (Unique)
- skills (TEXT)
- education (TEXT)
- certifications (TEXT)
- headline (VARCHAR 500)
- summary (TEXT)
- location (VARCHAR 255)
- employment_status (ENUM)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

### resumes
- id (Primary Key)
- user_id (Unique)
- objective (TEXT)
- education_csv (TEXT)
- experience_csv (TEXT)
- projects_csv (TEXT)
- certifications_csv (TEXT)
- skills (TEXT)
- uploaded_file_name (VARCHAR 255)
- uploaded_file_type (VARCHAR 100)
- uploaded_file_size (BIGINT)
- uploaded_file_reference (VARCHAR 500)
- uploaded_file_data (LONGBLOB)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Eureka Server running on port 8761

### Database Setup
```sql
CREATE DATABASE revhire_jobseeker_db;
```

### Running the Service
```bash
mvn clean install
mvn spring-boot:run
```

The service will start on port 8082 and register with Eureka Server.

## Dependencies

### External Services
- **AUTH-SERVICE**: User authentication and authorization

## Error Handling

The service includes comprehensive error handling:
- `NotFoundException`: Resource not found (404)
- `BadRequestException`: Invalid request data (400)
- `ApiException`: General API errors (500)
- Validation errors for request payloads
- File upload size limit exceeded (413)

## Security Features

- Input sanitization for all text fields
- File type validation (PDF, DOC, DOCX only)
- File size validation (2MB max)
- HTML tag removal from user inputs
- User verification via Auth Service

## Developer Notes

### Employment Status Enum
- `FRESHER`: New to job market
- `EMPLOYED`: Currently employed
- `UNEMPLOYED`: Not currently employed

### Allowed File Types
- application/pdf
- application/msword
- application/vnd.openxmlformats-officedocument.wordprocessingml.document

### File Storage
Resume files are stored as BLOB data in MySQL database with metadata including file name, type, size, and reference.

## Integration with Other Services

### Auth Service
- Validates user existence before profile/resume operations
- Endpoint: `GET /api/auth/users/{userId}`

### Employer Service
- Provides resume download endpoint
- Endpoint: `GET /api/resume/download/{userId}`

## Logging

The service uses SLF4J with Logback for logging:
- DEBUG level for service layer operations
- INFO level for controller requests
- ERROR level for exceptions and failures
