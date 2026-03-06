# Employer Service

## Overview
The Employer Service is a microservice in the RevHire recruitment platform that manages employer company profiles and aggregates statistics from other services.

**Module Owner:** Venkatesh

## Features
- Employer company profile management
- Profile creation and updates
- Statistics aggregation from Job Service and Application Service
- Integration with Auth Service for user validation
- Input sanitization and validation

## Technology Stack
- Java 17
- Spring Boot 3.3.2
- Spring Data JPA
- Spring Cloud (Eureka Client, OpenFeign)
- MySQL 8
- Lombok
- Maven

## API Endpoints

### Company Profile Management
- `GET /api/employer/company-profile` - Get company profile (requires X-User-Id header)
- `PUT /api/employer/company-profile` - Update company profile (requires X-User-Id header)

### Statistics
- `GET /api/employer/statistics` - Get employer statistics (requires X-User-Id header)

### Health Check
- `GET /api/employer/health` - Service health check

## Configuration

### Database Configuration
```yaml
Database: revhire_employer_db
Host: localhost
Port: 3306
```

### Service Port
```
Port: 8083
```

### Service Registration
```
Eureka Server: http://localhost:8761/eureka/
Service Name: EMPLOYER-SERVICE
```

## Entity Model

### EmployerProfile
- `id` - Primary key
- `userId` - User ID (unique, references Auth Service)
- `companyName` - Company name (required)
- `industry` - Industry sector
- `companySize` - Company size category
- `companyDescription` - Company description
- `website` - Company website URL
- `companyLocation` - Company location
- `createdAt` - Profile creation timestamp
- `updatedAt` - Last update timestamp

## DTOs

### Request DTOs
- `EmployerCompanyProfileRequest` - For creating/updating company profiles

### Response DTOs
- `EmployerCompanyProfileResponse` - Company profile response
- `EmployerStatisticsResponse` - Aggregated statistics response
- `ApiResponse<T>` - Generic API response wrapper

## Feign Clients

### AuthServiceClient
- Communicates with Auth Service for user validation

### JobServiceClient
- Fetches job statistics for employers
- Endpoints: `/api/jobs/employer`, `/api/jobs/employer/statistics`

### ApplicationServiceClient
- Fetches application statistics for employers
- Endpoints: `/api/applications/employer`, `/api/applications/employer/statistics`

## Exception Handling
- `ApiException` - Base exception class
- `BadRequestException` - HTTP 400 errors
- `NotFoundException` - HTTP 404 errors
- `ForbiddenException` - HTTP 403 errors
- `GlobalExceptionHandler` - Centralized exception handling

## Security Features
- Input sanitization using `InputSanitizer` utility
- XSS protection
- SQL injection prevention
- Request validation using Jakarta Validation

## Running the Service

### Prerequisites
- Java 17 or higher
- MySQL 8
- Eureka Server running on port 8761

### Steps
1. Clone the repository
2. Configure MySQL database credentials in `application.yml`
3. Run the service:
   ```bash
   mvn spring-boot:run
   ```
4. Service will start on port 8083

## Database Setup
The service will automatically create the database if it doesn't exist. Table schemas are auto-generated using Hibernate DDL.

## Development

### Building the Project
```bash
mvn clean install
```

### Running Tests
```bash
mvn test
```

## Integration with Other Services
- **Auth Service**: User authentication and validation
- **Job Service**: Job postings and statistics
- **Application Service**: Application tracking and statistics
- **Eureka Server**: Service discovery and registration

## API Usage Examples

### Get Company Profile
```bash
curl -X GET http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123"
```

### Update Company Profile
```bash
curl -X PUT http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123" \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "Tech Corp",
    "industry": "Technology",
    "companySize": "100-500",
    "companyDescription": "Leading tech company",
    "website": "https://techcorp.com",
    "companyLocation": "San Francisco, CA"
  }'
```

### Get Statistics
```bash
curl -X GET http://localhost:8083/api/employer/statistics \
  -H "X-User-Id: 123"
```

## Contributing
This service is part of the RevHire platform developed by Revature training program participants.

## License
Copyright 2024 Revature. All rights reserved.
