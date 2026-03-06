# Job Service - Quick Start Guide

## Prerequisites Checklist
- [ ] JDK 17 installed
- [ ] Maven installed
- [ ] MySQL Server running
- [ ] Eureka Server running on port 8761
- [ ] Employer Service running (for company name lookup)
- [ ] Notification Service running (optional, for notifications)

## Step 1: Database Setup

```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source database-setup.sql

# Or manually create database
CREATE DATABASE IF NOT EXISTS revhire_job_db;
exit;
```

## Step 2: Configure Application

Edit `src/main/resources/application.yml` if your MySQL credentials are different:

```yaml
spring:
  datasource:
    username: your_mysql_username
    password: your_mysql_password
```

## Step 3: Build the Project

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-job-service
mvn clean install
```

## Step 4: Run the Application

```bash
mvn spring-boot:run
```

You should see:
```
Started JobServiceApplication in X seconds
```

## Step 5: Verify Service Registration

Check Eureka Dashboard at http://localhost:8761

You should see **JOB-SERVICE** registered.

## Step 6: Test the API

### Test 1: Get All Jobs (Should return empty array initially)
```bash
curl http://localhost:8084/api/jobs
```

Expected Response:
```json
{
  "success": true,
  "message": "Jobs retrieved successfully",
  "data": []
}
```

### Test 2: Create a Job (Requires Employer Service to be running)
```bash
curl -X POST http://localhost:8084/api/employer/jobs \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "title": "Senior Java Developer",
    "description": "We are looking for an experienced Java developer to join our team.",
    "skills": "Java, Spring Boot, Microservices, MySQL, Docker",
    "education": "Bachelor degree in Computer Science",
    "maxExperienceYears": 5,
    "location": "New York, NY",
    "minSalary": 100000,
    "maxSalary": 150000,
    "jobType": "Full-time",
    "openings": 2,
    "applicationDeadline": "2024-12-31"
  }'
```

### Test 3: Search Jobs
```bash
curl "http://localhost:8084/api/jobseeker/jobs/search?title=Java"
```

### Test 4: Get Job Statistics
```bash
curl -X GET http://localhost:8084/api/employer/jobs/statistics \
  -H "X-User-Id: 1"
```

## Common Issues and Solutions

### Issue 1: Port 8084 already in use
**Solution:** Change the port in `application.yml`:
```yaml
server:
  port: 8085  # or any other available port
```

### Issue 2: Cannot connect to MySQL
**Solution:**
- Check if MySQL is running: `mysql -u root -p`
- Verify credentials in `application.yml`
- Ensure database `revhire_job_db` exists

### Issue 3: Cannot connect to Eureka Server
**Solution:**
- Ensure Eureka Server is running on port 8761
- Check Eureka URL in `application.yml`

### Issue 4: "Invalid employer ID" when creating job
**Solution:**
- Ensure Employer Service is running
- Make sure the employer exists in Employer Service
- The X-User-Id header must match an existing employer

### Issue 5: Build fails
**Solution:**
```bash
# Clean and rebuild
mvn clean
mvn install -U
```

## Directory Structure Quick Reference

```
p3-job-service/
├── src/main/java/com/revature/jobservice/
│   ├── JobServiceApplication.java          ← Start here
│   ├── controller/                         ← API endpoints
│   ├── service/                           ← Business logic
│   ├── repository/                        ← Database access
│   ├── entity/                            ← Database models
│   ├── dto/                               ← Request/Response objects
│   ├── client/                            ← External service calls
│   ├── exception/                         ← Error handling
│   ├── enums/                             ← Constants
│   └── util/                              ← Helper utilities
└── src/main/resources/
    └── application.yml                     ← Configuration
```

## API Endpoints Quick Reference

### Public Access
- `GET /api/jobs` - List all open jobs
- `GET /api/jobs/{id}` - Get job details

### Job Seeker (X-User-Id header optional)
- `GET /api/jobseeker/jobs/search` - Search with filters

### Employer (X-User-Id header required)
- `GET /api/employer/jobs` - My jobs
- `POST /api/employer/jobs` - Create job
- `PUT /api/employer/jobs/{id}` - Update job
- `DELETE /api/employer/jobs/{id}` - Delete job
- `PATCH /api/employer/jobs/{id}/close` - Close job
- `PATCH /api/employer/jobs/{id}/reopen` - Reopen job
- `PATCH /api/employer/jobs/{id}/fill` - Mark as filled
- `GET /api/employer/jobs/statistics` - Get stats

## Sample Test Data

Use this to quickly test the API:

```json
{
  "title": "Full Stack Developer",
  "description": "Looking for a full stack developer with experience in React and Spring Boot. You will work on building microservices-based applications.",
  "skills": "React, JavaScript, Java, Spring Boot, MySQL, Docker, Kubernetes",
  "education": "Bachelor's degree in Computer Science or related field",
  "maxExperienceYears": 3,
  "location": "San Francisco, CA",
  "minSalary": 90000,
  "maxSalary": 130000,
  "jobType": "Full-time",
  "openings": 3,
  "applicationDeadline": "2024-12-31"
}
```

## Health Check

```bash
# Check if service is running
curl http://localhost:8084/api/jobs

# Check if service is registered with Eureka
curl http://localhost:8761/eureka/apps/JOB-SERVICE
```

## Logs Location

Logs are printed to console. Look for:
- `INFO com.revature.jobservice` - Application logs
- `DEBUG org.springframework.cloud.openfeign` - Feign client logs

## Next Steps

1. Read `API-DOCUMENTATION.md` for detailed API specs
2. Read `PROJECT-SUMMARY.md` for complete project overview
3. Test all endpoints using Postman or curl
4. Integrate with other services (Application Service, etc.)

## Need Help?

- Check `README.md` for detailed information
- Check `API-DOCUMENTATION.md` for API details
- Check `PROJECT-SUMMARY.md` for architecture overview
- Contact Anil (Module Owner)

## Quick Commands

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Run in debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Skip tests
mvn clean install -DskipTests

# Run tests only
mvn test

# Package as JAR
mvn package
```

## Production Checklist

Before deploying to production:
- [ ] Change database credentials
- [ ] Update Eureka server URL
- [ ] Set `ddl-auto` to `validate` instead of `update`
- [ ] Enable HTTPS
- [ ] Configure proper logging levels
- [ ] Set up monitoring and alerting
- [ ] Configure connection pooling
- [ ] Enable database query caching
- [ ] Set up backup strategy
- [ ] Configure rate limiting
- [ ] Enable CORS if needed
- [ ] Set up CI/CD pipeline

Good luck!
