# Employer Service - Complete File Index

## Quick Navigation Guide

### Start Here
1. **QUICKSTART.md** - Get the service running in minutes
2. **README.md** - Overview of features and capabilities
3. **PROJECT_SUMMARY.md** - Comprehensive project summary

---

## Documentation Files (6 files)

| File | Purpose | Start Here If... |
|------|---------|------------------|
| [README.md](README.md) | Project overview and features | You want a general understanding |
| [QUICKSTART.md](QUICKSTART.md) | Quick start guide | You want to run the service now |
| [API_DOCUMENTATION.md](API_DOCUMENTATION.md) | Complete API reference | You need API endpoint details |
| [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) | Architecture guide | You want to understand the code structure |
| [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) | Comprehensive summary | You want complete project details |
| [FILE_INDEX.md](FILE_INDEX.md) | This file - navigation guide | You need to find a specific file |

---

## Configuration Files (4 files)

### Build & Dependencies
| File | Purpose | Lines |
|------|---------|-------|
| [pom.xml](pom.xml) | Maven build configuration with all dependencies | 105 |

### Application Configuration
| File | Purpose | Environment |
|------|---------|-------------|
| [src/main/resources/application.yml](src/main/resources/application.yml) | Main application configuration | Production/Development |
| [src/test/resources/application-test.yml](src/test/resources/application-test.yml) | Test environment configuration | Testing |

### Version Control
| File | Purpose |
|------|---------|
| [.gitignore](.gitignore) | Git ignore rules |

---

## Java Source Files (23 files)

### Main Application (1 file)
| File | Description |
|------|-------------|
| [src/main/java/com/revhire/employerservice/EmployerServiceApplication.java](src/main/java/com/revhire/employerservice/EmployerServiceApplication.java) | Main Spring Boot application class with @SpringBootApplication, @EnableDiscoveryClient, @EnableFeignClients |

### Entity Layer (1 file)
| File | Description | Fields |
|------|-------------|--------|
| [src/main/java/com/revhire/employerservice/entity/EmployerProfile.java](src/main/java/com/revhire/employerservice/entity/EmployerProfile.java) | JPA entity for employer profiles | id, userId, companyName, industry, companySize, companyDescription, website, companyLocation, timestamps |

### DTO Layer (7 files)

#### Core DTOs
| File | Type | Purpose |
|------|------|---------|
| [src/main/java/com/revhire/employerservice/dto/EmployerCompanyProfileRequest.java](src/main/java/com/revhire/employerservice/dto/EmployerCompanyProfileRequest.java) | Request DTO | Create/update company profile |
| [src/main/java/com/revhire/employerservice/dto/EmployerCompanyProfileResponse.java](src/main/java/com/revhire/employerservice/dto/EmployerCompanyProfileResponse.java) | Response DTO | Return company profile data |
| [src/main/java/com/revhire/employerservice/dto/EmployerStatisticsResponse.java](src/main/java/com/revhire/employerservice/dto/EmployerStatisticsResponse.java) | Response DTO | Return aggregated statistics |
| [src/main/java/com/revhire/employerservice/dto/ApiResponse.java](src/main/java/com/revhire/employerservice/dto/ApiResponse.java) | Generic Wrapper | Wrap all API responses |

#### Integration DTOs
| File | Purpose |
|------|---------|
| [src/main/java/com/revhire/employerservice/dto/UserDetailsResponse.java](src/main/java/com/revhire/employerservice/dto/UserDetailsResponse.java) | Receive user data from Auth Service |
| [src/main/java/com/revhire/employerservice/dto/JobResponse.java](src/main/java/com/revhire/employerservice/dto/JobResponse.java) | Receive job data from Job Service |
| [src/main/java/com/revhire/employerservice/dto/ApplicationResponse.java](src/main/java/com/revhire/employerservice/dto/ApplicationResponse.java) | Receive application data from Application Service |

### Repository Layer (1 file)
| File | Description | Custom Methods |
|------|-------------|----------------|
| [src/main/java/com/revhire/employerservice/repository/EmployerProfileRepository.java](src/main/java/com/revhire/employerservice/repository/EmployerProfileRepository.java) | Spring Data JPA repository | findByUserId, existsByUserId |

### Feign Client Layer (3 files)
| File | Target Service | Methods |
|------|----------------|---------|
| [src/main/java/com/revhire/employerservice/client/AuthServiceClient.java](src/main/java/com/revhire/employerservice/client/AuthServiceClient.java) | AUTH-SERVICE | getUserById |
| [src/main/java/com/revhire/employerservice/client/JobServiceClient.java](src/main/java/com/revhire/employerservice/client/JobServiceClient.java) | JOB-SERVICE | getJobsByEmployer, getJobStatistics |
| [src/main/java/com/revhire/employerservice/client/ApplicationServiceClient.java](src/main/java/com/revhire/employerservice/client/ApplicationServiceClient.java) | APPLICATION-SERVICE | getApplicationsByEmployer, getApplicationStatistics |

### Service Layer (1 file)
| File | Description | Key Methods |
|------|-------------|-------------|
| [src/main/java/com/revhire/employerservice/service/EmployerService.java](src/main/java/com/revhire/employerservice/service/EmployerService.java) | Business logic implementation | getCompanyProfile, updateCompanyProfile, getStatistics |

### Controller Layer (1 file)
| File | Description | Endpoints |
|------|-------------|-----------|
| [src/main/java/com/revhire/employerservice/controller/EmployerController.java](src/main/java/com/revhire/employerservice/controller/EmployerController.java) | REST API endpoints | GET /company-profile, PUT /company-profile, GET /statistics, GET /health |

### Utility Layer (1 file)
| File | Description | Key Methods |
|------|-------------|-------------|
| [src/main/java/com/revhire/employerservice/util/InputSanitizer.java](src/main/java/com/revhire/employerservice/util/InputSanitizer.java) | Input validation and sanitization | sanitize, sanitizeEmail, sanitizeUrl, truncate |

### Exception Layer (5 files)

#### Exception Classes
| File | HTTP Status | Usage |
|------|-------------|-------|
| [src/main/java/com/revhire/employerservice/exception/ApiException.java](src/main/java/com/revhire/employerservice/exception/ApiException.java) | Variable | Base exception class |
| [src/main/java/com/revhire/employerservice/exception/BadRequestException.java](src/main/java/com/revhire/employerservice/exception/BadRequestException.java) | 400 | Invalid requests |
| [src/main/java/com/revhire/employerservice/exception/NotFoundException.java](src/main/java/com/revhire/employerservice/exception/NotFoundException.java) | 404 | Resource not found |
| [src/main/java/com/revhire/employerservice/exception/ForbiddenException.java](src/main/java/com/revhire/employerservice/exception/ForbiddenException.java) | 403 | Access denied |

#### Exception Handler
| File | Description |
|------|-------------|
| [src/main/java/com/revhire/employerservice/exception/GlobalExceptionHandler.java](src/main/java/com/revhire/employerservice/exception/GlobalExceptionHandler.java) | Centralized exception handling with @RestControllerAdvice |

### Test Files (2 files)
| File | Type | Purpose |
|------|------|---------|
| [src/test/java/com/revhire/employerservice/EmployerServiceApplicationTests.java](src/test/java/com/revhire/employerservice/EmployerServiceApplicationTests.java) | Unit Test | Context load test |
| [src/test/resources/application-test.yml](src/test/resources/application-test.yml) | Config | Test configuration with H2 database |

---

## Database Files (1 file)

| File | Purpose | Contents |
|------|---------|----------|
| [database-setup.sql](database-setup.sql) | Database initialization | Create database, create tables, sample data, useful queries |

---

## Docker Files (2 files)

| File | Purpose | Features |
|------|---------|----------|
| [Dockerfile](Dockerfile) | Container image definition | Multi-stage build, non-root user, health check |
| [docker-compose.yml](docker-compose.yml) | Multi-container setup | MySQL + Employer Service with networking |

---

## Testing & API Files (1 file)

| File | Format | Purpose |
|------|--------|---------|
| [Employer-Service-API.postman_collection.json](Employer-Service-API.postman_collection.json) | JSON | Postman collection with all API endpoints and test scenarios |

---

## File Organization by Purpose

### Getting Started
```
QUICKSTART.md          # Start here to run the service
README.md              # Overview and features
pom.xml                # Dependencies
application.yml        # Configuration
```

### Understanding the Code
```
PROJECT_STRUCTURE.md   # Architecture guide
PROJECT_SUMMARY.md     # Comprehensive details
EmployerServiceApplication.java  # Entry point
```

### API Development
```
API_DOCUMENTATION.md   # Complete API reference
EmployerController.java    # Endpoint definitions
Employer-Service-API.postman_collection.json  # API tests
```

### Business Logic
```
EmployerService.java       # Core business logic
EmployerProfile.java       # Data model
EmployerProfileRepository.java  # Data access
```

### Integration
```
AuthServiceClient.java         # Auth service integration
JobServiceClient.java          # Job service integration
ApplicationServiceClient.java  # Application service integration
```

### Error Handling
```
GlobalExceptionHandler.java    # Centralized error handling
ApiException.java             # Base exception
BadRequestException.java      # 400 errors
NotFoundException.java         # 404 errors
ForbiddenException.java        # 403 errors
```

### Security
```
InputSanitizer.java           # Input validation
EmployerCompanyProfileRequest.java  # Request validation
```

### Deployment
```
Dockerfile                    # Container image
docker-compose.yml           # Multi-container setup
database-setup.sql           # Database initialization
```

---

## Code Statistics

### Total Files: 34
- Java Files: 23
- Configuration Files: 4
- Documentation Files: 6
- Database Files: 1
- Docker Files: 2
- Test Files: 2

### Lines of Code (Approximate)
- Java Code: ~1,500 lines
- Configuration: ~150 lines
- Documentation: ~2,500 lines
- SQL: ~100 lines
- Total: ~4,250 lines

---

## Package Structure

```
com.revhire.employerservice
├── EmployerServiceApplication.java     (Main)
├── client/                             (3 files - Feign clients)
├── controller/                         (1 file - REST endpoints)
├── dto/                                (7 files - Data transfer objects)
├── entity/                             (1 file - JPA entity)
├── exception/                          (5 files - Error handling)
├── repository/                         (1 file - Data access)
├── service/                            (1 file - Business logic)
└── util/                               (1 file - Utilities)
```

---

## Dependencies Reference

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

---

## Key Concepts by File

### Data Flow
1. **EmployerController.java** - Receives HTTP requests
2. **EmployerService.java** - Processes business logic
3. **EmployerProfileRepository.java** - Accesses database
4. **EmployerProfile.java** - Represents data model
5. **ApiResponse.java** - Wraps response

### Service Integration
1. **AuthServiceClient.java** - Validates users
2. **JobServiceClient.java** - Fetches job data
3. **ApplicationServiceClient.java** - Fetches application data
4. **EmployerService.java** - Aggregates statistics

### Error Handling Flow
1. Exception occurs in service/controller
2. **GlobalExceptionHandler.java** catches it
3. Maps to appropriate HTTP status
4. Returns **ApiResponse** with error details

---

## Common Tasks & Relevant Files

### Task: Add New Endpoint
Files to modify:
1. EmployerController.java (add endpoint)
2. EmployerService.java (add business logic)
3. API_DOCUMENTATION.md (document endpoint)
4. Employer-Service-API.postman_collection.json (add test)

### Task: Add New Field to Profile
Files to modify:
1. EmployerProfile.java (add field)
2. EmployerCompanyProfileRequest.java (add to request)
3. EmployerCompanyProfileResponse.java (add to response)
4. EmployerService.java (map field)

### Task: Add New Integration
Files to create/modify:
1. Create new Feign client in client/
2. Add dependency in pom.xml (if needed)
3. Update EmployerService.java to use client
4. Update application.yml for configuration

### Task: Add Validation
Files to modify:
1. EmployerCompanyProfileRequest.java (add annotations)
2. InputSanitizer.java (add sanitization logic)
3. GlobalExceptionHandler.java (handle validation errors)

---

## Quick File Lookup

### Need to find...
- **API endpoints?** → EmployerController.java
- **Database queries?** → EmployerProfileRepository.java
- **Business logic?** → EmployerService.java
- **Data model?** → EmployerProfile.java
- **Configuration?** → application.yml
- **Dependencies?** → pom.xml
- **Error handling?** → GlobalExceptionHandler.java
- **External service calls?** → client/ directory
- **Input validation?** → InputSanitizer.java
- **API documentation?** → API_DOCUMENTATION.md
- **Setup instructions?** → QUICKSTART.md
- **Project overview?** → README.md

---

## File Modification Frequency

### Frequently Modified
- EmployerService.java (business logic changes)
- EmployerController.java (new endpoints)
- application.yml (configuration updates)

### Occasionally Modified
- EmployerProfile.java (schema changes)
- DTO files (API changes)
- Feign clients (integration changes)

### Rarely Modified
- EmployerServiceApplication.java (main class)
- Exception classes (error handling)
- InputSanitizer.java (utility methods)
- pom.xml (dependency updates)

---

## Development Workflow by Files

### 1. Initial Setup
- Read: QUICKSTART.md
- Configure: application.yml
- Run: database-setup.sql

### 2. Development
- Understand: PROJECT_STRUCTURE.md
- Modify: Service and Controller files
- Test: Postman collection

### 3. Testing
- Unit tests: EmployerServiceApplicationTests.java
- API tests: Postman collection
- Manual: API_DOCUMENTATION.md

### 4. Deployment
- Build: pom.xml (mvn clean install)
- Package: Dockerfile
- Deploy: docker-compose.yml

---

**This index provides quick navigation to all 34 files in the Employer Service project.**

For detailed information about any component, refer to PROJECT_STRUCTURE.md or PROJECT_SUMMARY.md.
