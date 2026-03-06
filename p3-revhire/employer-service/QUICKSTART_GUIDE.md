# Employer Service - Quick Start Guide

## Prerequisites
Before running the Employer Service, ensure you have:
- Java 17 or higher installed
- Maven 3.6+ installed
- MySQL 8.0+ running
- Eureka Server running on port 8761

## Setup Steps

### 1. Database Setup
Create the database for the service:

```sql
CREATE DATABASE IF NOT EXISTS revhire_employer_db;
USE revhire_employer_db;
```

The tables will be created automatically by Hibernate when you run the application.

### 2. Configuration
Update the database credentials in `src/main/resources/application.yml` if needed:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revhire_employer_db?createDatabaseIfNotExist=true&useSSL=false
    username: root
    password: root  # Change this to your MySQL password
```

### 3. Build the Service
Navigate to the project directory and build:

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-employer-service
mvn clean install
```

### 4. Run the Service
Start the service:

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/employer-service-1.0.0.jar
```

### 5. Verify the Service
Once started, verify the service is running:

**Health Check:**
```bash
curl http://localhost:8083/actuator/health
```

**Check Eureka Registration:**
Visit http://localhost:8761 and verify "EMPLOYER-SERVICE" is registered.

## Testing the APIs

### 1. Create/Update Employer Profile
```bash
curl -X PUT http://localhost:8083/api/employer/company-profile \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "companyName": "Tech Solutions Inc",
    "industry": "Information Technology",
    "companySize": "100-500",
    "companyDescription": "Leading tech company",
    "website": "https://techsolutions.com",
    "companyLocation": "San Francisco, CA"
  }'
```

### 2. Get Employer Profile
```bash
curl -X GET http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 1"
```

### 3. Create Job Posting
```bash
curl -X POST http://localhost:8083/api/employer/jobs \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "title": "Senior Java Developer",
    "description": "We are looking for an experienced Java developer with strong Spring Boot skills...",
    "skills": "Java, Spring Boot, MySQL, REST APIs, Microservices",
    "education": "Bachelor degree in Computer Science or related field",
    "maxExperienceYears": 5,
    "location": "San Francisco, CA",
    "minSalary": 100000,
    "maxSalary": 150000,
    "jobType": "FULL_TIME",
    "openings": 2,
    "applicationDeadline": "2024-12-31"
  }'
```

### 4. Get All Jobs for Employer
```bash
curl -X GET http://localhost:8083/api/employer/jobs \
  -H "X-User-Id: 1"
```

### 5. Close a Job Posting
```bash
curl -X PATCH http://localhost:8083/api/employer/jobs/1/close \
  -H "X-User-Id: 1"
```

### 6. Get Applicants
```bash
curl -X GET "http://localhost:8083/api/employer/applicants?status=APPLIED" \
  -H "X-User-Id: 1"
```

### 7. Update Application Status
```bash
curl -X PATCH http://localhost:8083/api/employer/applicants/1/shortlist \
  -H "X-User-Id: 1"
```

### 8. Get Statistics
```bash
curl -X GET http://localhost:8083/api/employer/company-profile/statistics \
  -H "X-User-Id: 1"
```

## Common Issues and Solutions

### Issue 1: Database Connection Error
**Error:** `java.sql.SQLException: Access denied for user 'root'@'localhost'`

**Solution:**
- Verify MySQL is running
- Check database credentials in `application.yml`
- Ensure database exists: `CREATE DATABASE revhire_employer_db;`

### Issue 2: Eureka Connection Error
**Error:** `com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server`

**Solution:**
- Ensure Eureka Server is running on port 8761
- Update `eureka.client.service-url.defaultZone` in `application.yml` if Eureka is on a different host/port

### Issue 3: Port Already in Use
**Error:** `Port 8083 is already in use`

**Solution:**
- Stop the existing process using port 8083
- Or change the port in `application.yml`:
  ```yaml
  server:
    port: 8084  # Change to available port
  ```

### Issue 4: Feign Client Errors
**Error:** `feign.RetryableException: Connection refused`

**Solution:**
- Ensure dependent services (Auth Service, Application Service, Notification Service) are running
- Check service names in Eureka match the Feign client names
- Verify network connectivity between services

## Default Configuration
- **Service Name:** EMPLOYER-SERVICE
- **Port:** 8083
- **Database:** revhire_employer_db
- **Eureka Server:** http://localhost:8761/eureka/
- **Log Level:** DEBUG for com.revhire.employerservice

## API Documentation
For complete API documentation, see:
- **README_MICROSERVICE.md** - Comprehensive service documentation
- **API_DOCUMENTATION.md** - Detailed API endpoint documentation (if available)

## Developer Tools

### View Logs
Logs are output to the console. To save to a file:
```bash
mvn spring-boot:run > app.log 2>&1
```

### Database Schema
View auto-generated schema:
```sql
USE revhire_employer_db;
SHOW TABLES;
DESCRIBE employer_profiles;
DESCRIBE job_postings;
```

### Actuator Endpoints
Available at http://localhost:8083/actuator:
- `/actuator/health` - Health status
- `/actuator/info` - Service information
- `/actuator/metrics` - Metrics
- `/actuator/prometheus` - Prometheus metrics

## Next Steps
1. Review the API documentation in README_MICROSERVICE.md
2. Test all endpoints using Postman or cURL
3. Integrate with other microservices
4. Set up monitoring and alerting
5. Configure production environment variables
6. Set up CI/CD pipeline

## Support
For issues or questions:
- Check SERVICE_CREATION_SUMMARY.md for component details
- Review README_MICROSERVICE.md for architecture and design
- Contact: Venkatesh (Developer)

## Additional Resources
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Spring Cloud Documentation: https://spring.io/projects/spring-cloud
- Netflix Eureka: https://github.com/Netflix/eureka
- OpenFeign: https://github.com/OpenFeign/feign
