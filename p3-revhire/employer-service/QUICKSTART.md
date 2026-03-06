# Employer Service - Quick Start Guide

## Prerequisites

Before running the Employer Service, ensure you have:

1. **Java 17** or higher installed
   ```bash
   java -version
   ```

2. **Maven 3.6+** installed
   ```bash
   mvn -version
   ```

3. **MySQL 8.0+** installed and running
   ```bash
   mysql --version
   ```

4. **Eureka Server** running on port 8761
   - The service registers with Eureka for service discovery

## Quick Start Steps

### Step 1: Clone/Navigate to the Project
```bash
cd /path/to/p3-employer-service
```

### Step 2: Configure Database

#### Option A: Automatic Setup (Recommended)
The application will automatically create the database on startup.

#### Option B: Manual Setup
```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source database-setup.sql

# Or create manually
CREATE DATABASE revhire_employer_db;
```

### Step 3: Update Configuration (Optional)

Edit `src/main/resources/application.yml` if needed:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revhire_employer_db
    username: root        # Update if different
    password: root        # Update with your password
```

### Step 4: Build the Project
```bash
mvn clean install
```

### Step 5: Run the Application

#### Option A: Using Maven
```bash
mvn spring-boot:run
```

#### Option B: Using Java
```bash
java -jar target/employer-service-1.0.0.jar
```

#### Option C: Using Docker
```bash
# Build the image
docker build -t employer-service:1.0.0 .

# Run with Docker Compose
docker-compose up -d
```

### Step 6: Verify the Service is Running

```bash
# Check health endpoint
curl http://localhost:8083/api/employer/health
```

Expected response:
```json
{
  "success": true,
  "message": "Employer Service is running",
  "data": "OK",
  "timestamp": "2024-03-06T15:30:00"
}
```

## Testing the API

### 1. Create/Update a Company Profile

```bash
curl -X PUT http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123" \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "Tech Innovations Inc",
    "industry": "Information Technology",
    "companySize": "100-500",
    "companyDescription": "Leading provider of cloud solutions",
    "website": "https://techinnovations.com",
    "companyLocation": "San Francisco, CA"
  }'
```

### 2. Get Company Profile

```bash
curl -X GET http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123"
```

### 3. Get Employer Statistics

```bash
curl -X GET http://localhost:8083/api/employer/statistics \
  -H "X-User-Id: 123"
```

## Using Postman

1. Import the Postman collection:
   - File: `Employer-Service-API.postman_collection.json`

2. Set the environment variables:
   - `baseUrl`: http://localhost:8083
   - `userId`: Your test user ID (e.g., 123)

3. Run the requests in the collection

## Troubleshooting

### Issue: Database Connection Failed

**Error:** `Communications link failure`

**Solution:**
1. Verify MySQL is running:
   ```bash
   sudo systemctl status mysql    # Linux
   brew services list             # macOS
   ```

2. Check MySQL credentials in `application.yml`

3. Ensure database exists:
   ```bash
   mysql -u root -p -e "SHOW DATABASES LIKE 'revhire_employer_db';"
   ```

### Issue: Eureka Server Not Found

**Error:** `Discovery client failed to connect to Eureka server`

**Solution:**
1. Start Eureka Server first (port 8761)
2. Verify Eureka URL in `application.yml`:
   ```yaml
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka/
   ```

### Issue: Port Already in Use

**Error:** `Port 8083 is already in use`

**Solution:**
1. Find process using the port:
   ```bash
   lsof -i :8083          # macOS/Linux
   netstat -ano | findstr :8083    # Windows
   ```

2. Kill the process or change the port in `application.yml`:
   ```yaml
   server:
     port: 8084  # Use different port
   ```

### Issue: Feign Client Errors

**Error:** `FeignException: Service unavailable`

**Solution:**
1. Ensure all dependent services are running:
   - Auth Service (port 8081)
   - Job Service (port 8084)
   - Application Service (port 8085)

2. Check service registration in Eureka dashboard:
   - http://localhost:8761

### Issue: Build Failures

**Error:** Maven build fails

**Solution:**
```bash
# Clean Maven cache
mvn clean

# Force update dependencies
mvn clean install -U

# Skip tests if needed
mvn clean install -DskipTests
```

## Directory Structure Quick Reference

```
p3-employer-service/
├── src/main/java/com/revhire/employerservice/
│   ├── EmployerServiceApplication.java    # Main class
│   ├── controller/                        # REST endpoints
│   ├── service/                           # Business logic
│   ├── repository/                        # Database access
│   ├── entity/                            # JPA entities
│   ├── dto/                               # Data transfer objects
│   ├── client/                            # Feign clients
│   ├── exception/                         # Exception handling
│   └── util/                              # Utilities
├── src/main/resources/
│   └── application.yml                    # Configuration
└── pom.xml                                # Maven dependencies
```

## Default Configuration

| Setting | Value |
|---------|-------|
| Server Port | 8083 |
| Database | revhire_employer_db |
| DB Host | localhost:3306 |
| DB Username | root |
| DB Password | root |
| Eureka Server | http://localhost:8761/eureka/ |
| Service Name | EMPLOYER-SERVICE |

## Next Steps

1. **Review API Documentation**: See `API_DOCUMENTATION.md`
2. **Explore Project Structure**: See `PROJECT_STRUCTURE.md`
3. **Run Tests**: `mvn test`
4. **Check Logs**: Review console output for errors
5. **Monitor Service**: Access Eureka dashboard at http://localhost:8761

## Common Commands

```bash
# Build without tests
mvn clean package -DskipTests

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# View logs
tail -f logs/employer-service.log

# Check running processes
ps aux | grep employer-service

# Stop the service
pkill -f employer-service

# View Maven dependencies
mvn dependency:tree

# Update dependencies
mvn versions:display-dependency-updates
```

## Development Mode

For development with auto-reload:

1. Add Spring Boot DevTools to pom.xml (already included in starter)
2. Run with Maven: `mvn spring-boot:run`
3. Make changes to code
4. Application auto-restarts on save

## Production Deployment

### Using JAR file:
```bash
# Build production JAR
mvn clean package -Pprod

# Run with production profile
java -jar -Dspring.profiles.active=prod target/employer-service-1.0.0.jar
```

### Using Docker:
```bash
# Build image
docker build -t employer-service:1.0.0 .

# Run container
docker run -d \
  -p 8083:8083 \
  -e SPRING_PROFILES_ACTIVE=prod \
  --name employer-service \
  employer-service:1.0.0
```

## Monitoring

Access monitoring endpoints:

- Health: http://localhost:8083/actuator/health
- Info: http://localhost:8083/actuator/info
- Metrics: http://localhost:8083/actuator/metrics
- Prometheus: http://localhost:8083/actuator/prometheus

## Support & Resources

- **README**: Comprehensive project overview
- **API Documentation**: Complete API reference
- **Project Structure**: Detailed architecture guide
- **Postman Collection**: Ready-to-use API tests

## Module Owner

**Name:** Venkatesh
**Service:** Employer Service
**Platform:** RevHire Recruitment Platform
**Version:** 1.0.0

---

**You're all set! The Employer Service is ready to use.**

For detailed API usage, see `API_DOCUMENTATION.md`
