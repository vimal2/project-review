# Job Search Service

**Developer:** Anil
**Responsibility:** Job Search and Filtering

## Overview

The Job Search Service is a Spring Boot microservice that provides comprehensive job search and filtering capabilities for the RevHire platform. This service maintains a READ-ONLY copy of job postings synchronized from the Employer Service via Kafka events.

## Features

- **Advanced Job Search**: Multi-criteria filtering with pagination
- **Public Job Listings**: Unauthenticated access to open jobs
- **Application Status Integration**: Check if authenticated users have already applied
- **Event-Driven Sync**: Real-time synchronization with Employer Service via Kafka
- **Filter Options**: Get distinct values for locations, job types, and companies

## Technology Stack

- **Framework**: Spring Boot 3.2.5
- **Java Version**: 17
- **Database**: MySQL
- **Message Broker**: Apache Kafka
- **Service Discovery**: Netflix Eureka
- **Configuration**: Spring Cloud Config
- **Inter-Service Communication**: OpenFeign

## Architecture

### Data Flow

1. **Job Posting Events**: Employer Service publishes job posting events (CREATE, UPDATE, DELETE, STATUS_CHANGE) to Kafka
2. **Event Consumption**: Job Search Service consumes these events and syncs its local database
3. **Search Operations**: Job seekers search jobs using various filters
4. **Application Check**: When authenticated, the service calls Application Service to check if user has applied

### Database Schema

**Table: job_postings**
- Primary fields: id, employerId, companyName, title, description
- Search fields: location, jobType, skills, education
- Filter fields: maxExperienceYears, minSalary, maxSalary
- Status fields: status (OPEN, CLOSED, FILLED), applicationDeadline
- Timestamps: createdAt, updatedAt

## API Endpoints

### Public Endpoints (No Authentication)

#### Get All Open Jobs
```
GET /api/jobs?page=0&size=20
```

#### Get Job Details
```
GET /api/jobs/{jobId}
```

#### Get Filter Options
```
GET /api/jobs/filters/locations
GET /api/jobs/filters/job-types
GET /api/jobs/filters/companies
```

### Authenticated Endpoints (Job Seeker)

#### Advanced Search
```
POST /api/jobseeker/jobs/search
Content-Type: application/json
X-User-Id: {userId}

{
  "title": "Software Engineer",
  "location": "New York",
  "companyName": "TechCorp",
  "jobType": "Full-time",
  "maxExperience": 5,
  "minSalary": 80000,
  "maxSalary": 120000,
  "postedAfter": "2024-01-01",
  "page": 0,
  "size": 20,
  "sortBy": "createdAt",
  "sortDirection": "DESC"
}
```

#### Get Job Details with Application Status
```
GET /api/jobseeker/jobs/{jobId}
X-User-Id: {userId}
```

#### Simple Search with Query Parameters
```
GET /api/jobseeker/jobs/search?title=Engineer&location=NYC&page=0&size=20
X-User-Id: {userId}
```

## Search Filters

The service supports the following filters:

- **title**: Partial match, case-insensitive
- **location**: Partial match, case-insensitive
- **companyName**: Partial match, case-insensitive
- **jobType**: Exact match
- **maxExperience**: Maximum years of experience required
- **minSalary/maxSalary**: Salary range overlap check
- **postedAfter**: Jobs posted on or after date
- **status**: Job status (OPEN, CLOSED, FILLED) - defaults to OPEN for public searches

## Kafka Events

### Consumed Topics

**Topic:** `job-postings`

**Event Types:**
- `CREATED`: New job posting created
- `UPDATED`: Job posting details updated
- `STATUS_CHANGED`: Job status changed (OPEN → CLOSED/FILLED)
- `DELETED`: Job posting deleted

**Event Structure:**
```json
{
  "eventType": "CREATED",
  "id": 123,
  "employerId": 456,
  "companyName": "TechCorp",
  "title": "Software Engineer",
  "description": "...",
  "skills": "Java, Spring Boot, MySQL",
  "education": "Bachelor's in CS",
  "maxExperienceYears": 5,
  "location": "New York, NY",
  "minSalary": 80000,
  "maxSalary": 120000,
  "jobType": "Full-time",
  "openings": 2,
  "applicationDeadline": "2024-12-31",
  "status": "OPEN",
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-15T10:00:00"
}
```

## Configuration

### Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE revhire_job_search;
```

2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/revhire_job_search
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Kafka Setup

1. Start Kafka and Zookeeper
2. Create topic:
```bash
kafka-topics --create --topic job-postings --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

### Eureka Setup

Ensure Eureka server is running on `http://localhost:8761`

## Running the Service

### Prerequisites

- Java 17+
- MySQL 8.0+
- Apache Kafka
- Eureka Server (for service discovery)
- Application Service (for application status checks)

### Build and Run

```bash
# Build the project
mvn clean install

# Run the service
mvn spring-boot:run
```

The service will start on port **8083**.

## Service Dependencies

- **Application Service**: To check if users have applied to jobs
- **Employer Service**: Source of job posting events (via Kafka)
- **Eureka Server**: For service registration and discovery
- **Config Server** (optional): For centralized configuration

## Monitoring

Actuator endpoints are available at:
- Health: `http://localhost:8083/actuator/health`
- Info: `http://localhost:8083/actuator/info`
- Metrics: `http://localhost:8083/actuator/metrics`

## Development Notes

### Database Indexes

The JobPosting entity includes indexes on frequently queried fields:
- status
- location
- jobType
- createdAt
- employerId

### Performance Considerations

- Maximum page size limited to 100 items
- Database indexes optimize search performance
- Feign client has 5-second timeout for inter-service calls
- Kafka consumer processes events asynchronously

### Error Handling

- Global exception handler for consistent error responses
- Kafka listener includes retry logic and dead letter queue support
- Feign client fallbacks for Application Service unavailability

## Future Enhancements

- [ ] Full-text search using Elasticsearch
- [ ] Advanced filtering with skills matching
- [ ] Job recommendation engine
- [ ] Saved searches for job seekers
- [ ] Email alerts for new matching jobs
- [ ] Analytics and reporting dashboard

## Contact

**Developer:** Anil
**Service:** Job Search and Filtering
