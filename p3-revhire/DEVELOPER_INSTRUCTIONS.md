# RevHire P3 Microservices - Developer Commit Instructions

## Project Overview

The RevHire monolithic application has been decomposed into a microservices architecture. This document provides instructions for each team member to create their own repository with proper commit history.

## Architecture Overview

```
                        ┌─────────────────┐       ┌─────────────────┐
                        │  Eureka Server  │       │  Config Server  │
                        │     (8761)      │       │     (8888)      │
                        │Service Discovery│       │ Centralized Cfg │
                        └────────▲────────┘       └────────▲────────┘
                                 │                         │
                 ┌───────────────┴─────────────────────────┴───────────────┐
                 │          All services register & fetch config           │
                 └───────────────┬─────────────────────────────────────────┘
                                 │
┌────────────────────────────────┼────────────────────────────────────────┐
│                                ▼                                        │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │                        API Gateway (8080)                        │   │
│  │                     (JWT Validation, Routing)                    │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│                                 │                                       │
│         ┌───────────┬───────────┼───────────┬───────────┐               │
│         ▼           ▼           ▼           ▼           ▼               │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐
│  │   Auth     │ │ Jobseeker  │ │  Employer  │ │ Job Search │ │Application │
│  │  Service   │ │  Service   │ │  Service   │ │  Service   │ │  Service   │
│  │  (8081)    │ │  (8082)    │ │  (8083)    │ │  (8086)    │ │  (8085)    │
│  │  Niranjan  │ │Madhusudhan │ │ Venkatesh  │ │    Anil    │ │Guru Prasadh│
│  └─────┬──────┘ └──────┬─────┘ └─────┬──────┘ └────────────┘ └──────┬─────┘
│        │               │             │                              │     │
│        │               └─────────────┼──────────────────────────────┘     │
│        │                             │                                    │
│        │    ┌────────────────────────┴────────────────────────┐           │
│        │    │        Inter-service Communication (Feign)      │           │
│        │    │  - Jobseeker calls Auth for user validation     │           │
│        │    │  - Employer calls Auth, Application services    │           │
│        │    │  - Application calls Auth, Jobseeker, Employer  │           │
│        │    └─────────────────────────────────────────────────┘           │
│        │                                                                  │
│        └──────────────────────┐                                           │
│                               ▼                                           │
│                    ┌─────────────────┐                                    │
│                    │  Notification   │                                    │
│                    │    Service      │                                    │
│                    └─────────────────┘                                    │
│                                                                           │
│                         Business Services Layer                           │
└───────────────────────────────────────────────────────────────────────────┘
```

## Service Ports

| Service | Port | Developer |
|---------|------|-----------|
| Eureka Server | 8761 | Shared (First commit) |
| Config Server | 8888 | Shared (First commit) |
| API Gateway | 8080 | Shared (First commit) |
| Auth Service | 8081 | Niranjan |
| Jobseeker Service | 8082 | Madhusudhan |
| Employer Service | 8083 | Venkatesh |
| Application Service | 8085 | Guru Prasadh |
| Job Search Service | 8086 | Anil |

---

## PHASE 1: Infrastructure Setup (All Team Members)

Before each developer starts their individual service, the team should set up the shared infrastructure.

### Step 1: Create the Main Repository

One team member (preferably Niranjan as the auth lead) creates the main repo:

```bash
# Create new repository
mkdir p3-revhire-microservices
cd p3-revhire-microservices
git init

# Create base structure
mkdir -p eureka-server config-server api-gateway
```

### Step 2: Add Eureka Server (First Shared Commit)

```bash
# Copy Eureka Server
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/eureka-server/* eureka-server/

# Commit
git add eureka-server/
git commit -m "feat: Add Eureka Service Discovery server

- Add Spring Cloud Netflix Eureka Server
- Configure server on port 8761
- Add README with setup instructions

🤖 Generated with Claude Code"
```

### Step 3: Add Config Server (Second Shared Commit)

```bash
# Copy Config Server
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/config-server/* config-server/

git add config-server/
git commit -m "feat: Add Spring Cloud Config Server

- Add centralized configuration server on port 8888
- Add service configurations for all microservices
- Configure native profile for local config files

🤖 Generated with Claude Code"
```

### Step 4: Add API Gateway (Third Shared Commit)

```bash
# Copy API Gateway
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/api-gateway/* api-gateway/

git add api-gateway/
git commit -m "feat: Add Spring Cloud Gateway

- Add API Gateway on port 8080
- Configure routes for all microservices
- Add JWT validation filter
- Add CORS configuration for Angular frontend

🤖 Generated with Claude Code"
```

---

## PHASE 2: Individual Developer Commits

Each developer should now add their service to the repository. Follow the order below to maintain proper dependency chain.

---

## NIRANJAN - Authentication & Notification Service

### Your Responsibility
- User registration, login, logout
- Password management (change, forgot, reset)
- JWT token generation and validation
- Notification system

### Step 1: Create Auth Service Directory

```bash
cd p3-revhire-microservices
mkdir auth-service
```

### Step 2: Copy Your Service Files

```bash
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/auth-service/* auth-service/
```

### Step 3: Make Your Commits (in order)

#### Commit 1: Project Setup
```bash
git add auth-service/pom.xml auth-service/.gitignore
git commit -m "feat(auth): Initialize auth-service project

- Add Spring Boot 3.2 with Spring Security
- Add Spring Cloud dependencies (Eureka, Config, OpenFeign)
- Add JWT dependencies (jjwt 0.11.5)
- Add MySQL connector

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 2: Entity Classes
```bash
git add auth-service/src/main/java/com/revhire/auth/entity/
git commit -m "feat(auth): Add entity classes

- Add User entity with role and employment status
- Add Role enum (JOB_SEEKER, EMPLOYER)
- Add EmploymentStatus enum
- Add PasswordResetToken entity
- Add Notification and NotificationType

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 3: Repositories
```bash
git add auth-service/src/main/java/com/revhire/auth/repository/
git commit -m "feat(auth): Add repository interfaces

- Add UserRepository with custom queries
- Add PasswordResetTokenRepository
- Add NotificationRepository

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 4: DTOs
```bash
git add auth-service/src/main/java/com/revhire/auth/dto/
git commit -m "feat(auth): Add DTO classes

- Add RegisterRequest with validation
- Add LoginRequest DTO
- Add AuthResponse DTO
- Add ChangePasswordRequest, ForgotPasswordRequest, ResetPasswordRequest
- Add NotificationResponse and UserDTO

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 5: Security Configuration
```bash
git add auth-service/src/main/java/com/revhire/auth/config/
git commit -m "feat(auth): Add security configuration

- Add SecurityConfig with JWT filter
- Add JwtAuthenticationFilter
- Add CustomUserDetailsService
- Add FeignClientConfig
- Configure CORS and endpoint authorization

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 6: Services
```bash
git add auth-service/src/main/java/com/revhire/auth/service/
git commit -m "feat(auth): Add service layer

- Add JwtService for token generation/validation
- Add AuthService for registration, login, password management
- Add NotificationService for notification CRUD

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 7: Controllers
```bash
git add auth-service/src/main/java/com/revhire/auth/controller/
git commit -m "feat(auth): Add REST controllers

- Add AuthController for /api/auth/** endpoints
- Add NotificationController for /api/notifications/**
- Add InternalUserController for service-to-service calls

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 8: Exception Handling
```bash
git add auth-service/src/main/java/com/revhire/auth/exception/
git commit -m "feat(auth): Add exception handling

- Add GlobalExceptionHandler
- Add custom exceptions (BadRequest, NotFound, Conflict, Unauthorized)

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

#### Commit 9: Application Configuration
```bash
git add auth-service/src/main/java/com/revhire/auth/AuthServiceApplication.java
git add auth-service/src/main/resources/
git commit -m "feat(auth): Add application configuration

- Add main application class
- Add application.yml with database and Eureka config
- Configure JWT settings

🤖 Generated with Claude Code

Co-Authored-By: Niranjan <niranjan@example.com>"
```

### Your API Endpoints
```
POST /api/auth/register      - User registration
POST /api/auth/login         - User login
POST /api/auth/logout        - User logout
POST /api/auth/change-password
POST /api/auth/forgot-password
POST /api/auth/reset-password
GET  /api/auth/validate      - Token validation
GET  /api/notifications      - Get user notifications
PATCH /api/notifications/{id}/read
GET  /internal/users/{id}    - Internal endpoint
```

---

## MADHUSUDHAN - Jobseeker Service

### Your Responsibility
- Job seeker profile management (skills, education, certifications)
- Resume management (create, update, upload)
- Profile data persistence

### Step 1: Create Jobseeker Service Directory

```bash
cd p3-revhire-microservices
mkdir jobseeker-service
```

### Step 2: Copy Your Service Files

```bash
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/jobseeker-service/* jobseeker-service/
```

### Step 3: Make Your Commits

#### Commit 1: Project Setup
```bash
git add jobseeker-service/pom.xml jobseeker-service/.gitignore
git commit -m "feat(jobseeker): Initialize jobseeker-service project

- Add Spring Boot 3.2 with JPA
- Add Spring Cloud dependencies
- Add OpenFeign for service communication

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 2: Entities
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/entity/
git commit -m "feat(jobseeker): Add entity classes

- Add JobSeekerProfile entity
- Add Resume entity with file upload support

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 3: Repository Layer
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/repository/
git commit -m "feat(jobseeker): Add repository interfaces

- Add JobSeekerProfileRepository
- Add ResumeRepository

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 4: DTOs
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/dto/
git commit -m "feat(jobseeker): Add DTO classes

- Add JobSeekerProfileRequest/Response
- Add ResumeRequest/Response
- Add ResumeUploadResponse
- Add UserDTO for Feign client

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 5: Feign Client
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/client/
git commit -m "feat(jobseeker): Add Feign client for auth-service

- Add AuthServiceClient for user validation

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 6: Service Layer
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/service/
git commit -m "feat(jobseeker): Add service layer

- Add JobSeekerProfileService with validation
- Add ResumeService with file upload (2MB limit, PDF/DOCX)

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 7: Controllers
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/controller/
git commit -m "feat(jobseeker): Add REST controllers

- Add JobSeekerProfileController
- Add ResumeController with file upload
- Add InternalJobSeekerController

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 8: Exception Handling & Utils
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/exception/
git add jobseeker-service/src/main/java/com/revhire/jobseeker/util/
git commit -m "feat(jobseeker): Add exception handling and utilities

- Add GlobalExceptionHandler
- Add custom exceptions
- Add InputSanitizer for XSS prevention

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

#### Commit 9: Configuration
```bash
git add jobseeker-service/src/main/java/com/revhire/jobseeker/config/
git add jobseeker-service/src/main/java/com/revhire/jobseeker/JobSeekerServiceApplication.java
git add jobseeker-service/src/main/resources/
git commit -m "feat(jobseeker): Add application configuration

- Add FeignClientConfig with header propagation
- Add main application class
- Add application.yml

🤖 Generated with Claude Code

Co-Authored-By: Madhusudhan <madhusudhan@example.com>"
```

### Your API Endpoints
```
GET  /api/jobseeker/profile    - Get profile
PUT  /api/jobseeker/profile    - Update profile
GET  /api/resume               - Get resume
PUT  /api/resume               - Update resume
POST /api/resume/upload        - Upload resume file
GET  /internal/jobseeker/users/{userId}/has-resume
```

---

## VENKATESH - Employer Service

### Your Responsibility
- Employer company profile management
- Job posting management (CRUD, status changes)
- Applicant tracking and status updates
- Employer statistics

### Step 1: Create Employer Service Directory

```bash
cd p3-revhire-microservices
mkdir employer-service
```

### Step 2: Copy Your Service Files

```bash
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/employer-service/* employer-service/
```

### Step 3: Make Your Commits

#### Commit 1: Project Setup
```bash
git add employer-service/pom.xml employer-service/.gitignore
git commit -m "feat(employer): Initialize employer-service project

- Add Spring Boot 3.2 with JPA
- Add Spring Cloud dependencies
- Add OpenFeign for service communication

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 2: Entities
```bash
git add employer-service/src/main/java/com/revhire/employer/entity/
git commit -m "feat(employer): Add entity classes

- Add EmployerProfile entity
- Add JobPosting entity
- Add JobStatus enum (OPEN, CLOSED, FILLED)

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 3: Repository Layer
```bash
git add employer-service/src/main/java/com/revhire/employer/repository/
git commit -m "feat(employer): Add repository interfaces

- Add EmployerProfileRepository
- Add JobPostingRepository with custom queries

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 4: DTOs
```bash
git add employer-service/src/main/java/com/revhire/employer/dto/
git commit -m "feat(employer): Add DTO classes

- Add EmployerProfileRequest/Response
- Add JobPostingRequest/Response
- Add EmployerStatisticsResponse
- Add ApplicantResponse

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 5: Feign Clients
```bash
git add employer-service/src/main/java/com/revhire/employer/client/
git commit -m "feat(employer): Add Feign clients

- Add AuthServiceClient
- Add ApplicationServiceClient
- Add NotificationClient

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 6: Service Layer
```bash
git add employer-service/src/main/java/com/revhire/employer/service/
git commit -m "feat(employer): Add service layer

- Add EmployerProfileService
- Add JobPostingService with CRUD operations
- Add ApplicantService for tracking

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 7: Controllers
```bash
git add employer-service/src/main/java/com/revhire/employer/controller/
git commit -m "feat(employer): Add REST controllers

- Add EmployerProfileController
- Add JobPostingController
- Add ApplicantController
- Add internal endpoints

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 8: Exception Handling
```bash
git add employer-service/src/main/java/com/revhire/employer/exception/
git commit -m "feat(employer): Add exception handling

- Add GlobalExceptionHandler
- Add custom exceptions

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

#### Commit 9: Configuration
```bash
git add employer-service/src/main/java/com/revhire/employer/config/
git add employer-service/src/main/java/com/revhire/employer/EmployerServiceApplication.java
git add employer-service/src/main/resources/
git commit -m "feat(employer): Add application configuration

- Add FeignClientConfig
- Add main application class
- Add application.yml

🤖 Generated with Claude Code

Co-Authored-By: Venkatesh <venkatesh@example.com>"
```

### Your API Endpoints
```
GET  /api/employer/company-profile
PUT  /api/employer/company-profile
GET  /api/employer/jobs
POST /api/employer/jobs
PUT  /api/employer/jobs/{id}
DELETE /api/employer/jobs/{id}
PATCH /api/employer/jobs/{id}/close
PATCH /api/employer/jobs/{id}/reopen
PATCH /api/employer/jobs/{id}/fill
GET  /api/employer/statistics
GET  /api/employer/applicants
PATCH /api/employer/applicants/{id}/status
GET  /internal/employer/jobs/{id}
```

---

## ANIL - Job Search Service

### Your Responsibility
- Public job listing
- Advanced job search with filters
- Job details retrieval

### Step 1: Create Job Search Service Directory

```bash
cd p3-revhire-microservices
mkdir job-search-service
```

### Step 2: Copy Your Service Files

```bash
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/job-search-service/* job-search-service/
```

### Step 3: Make Your Commits

#### Commit 1: Project Setup
```bash
git add job-search-service/pom.xml job-search-service/.gitignore
git commit -m "feat(job-search): Initialize job-search-service project

- Add Spring Boot 3.2 with JPA
- Add Spring Cloud dependencies

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 2: Entities
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/entity/
git commit -m "feat(job-search): Add entity classes

- Add JobPosting entity (read-only copy)
- Add JobStatus enum

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 3: Repository with Search Queries
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/repository/
git commit -m "feat(job-search): Add repository with search queries

- Add JobPostingRepository
- Add @Query methods for filtering by title, location, company
- Add salary range and experience filters

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 4: DTOs
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/dto/
git commit -m "feat(job-search): Add DTO classes

- Add JobSearchRequest with filter parameters
- Add JobSearchResponse (paginated)
- Add JobDetailResponse

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 5: Service Layer
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/service/
git commit -m "feat(job-search): Add service layer

- Add JobSearchService with multi-filter search
- Add JobSyncService for data synchronization

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 6: Controllers
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/controller/
git commit -m "feat(job-search): Add REST controllers

- Add PublicJobController for /api/jobs
- Add JobSearchController for authenticated search

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 7: Exception Handling
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/exception/
git commit -m "feat(job-search): Add exception handling

- Add GlobalExceptionHandler
- Add custom exceptions

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

#### Commit 8: Configuration
```bash
git add job-search-service/src/main/java/com/revhire/jobsearch/config/
git add job-search-service/src/main/java/com/revhire/jobsearch/JobSearchServiceApplication.java
git add job-search-service/src/main/resources/
git commit -m "feat(job-search): Add application configuration

- Add main application class
- Add application.yml

🤖 Generated with Claude Code

Co-Authored-By: Anil <anil@example.com>"
```

### Your API Endpoints
```
GET  /api/jobs                 - Public job listing
GET  /api/jobs/{id}            - Get job details
GET  /api/jobseeker/jobs/search?title=...&location=...&company=...&jobType=...&maxExperienceYears=...&minSalary=...&maxSalary=...&datePosted=...
```

### Search Filters
- title (partial match, case-insensitive)
- location (partial match)
- company (partial match)
- jobType (exact match)
- maxExperienceYears (numeric comparison)
- minSalary, maxSalary (range overlap)
- datePosted (on or after)

---

## GURU PRASADH - Application Service

### Your Responsibility
- Job application submission
- Application status management
- Withdrawal management
- Status change notifications

### Step 1: Create Application Service Directory

```bash
cd p3-revhire-microservices
mkdir application-service
```

### Step 2: Copy Your Service Files

```bash
cp -r /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revhire/application-service/* application-service/
```

### Step 3: Make Your Commits

#### Commit 1: Project Setup
```bash
git add application-service/pom.xml application-service/.gitignore
git commit -m "feat(application): Initialize application-service project

- Add Spring Boot 3.2 with JPA
- Add Spring Cloud dependencies
- Add OpenFeign for service communication

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 2: Entities
```bash
git add application-service/src/main/java/com/revhire/application/entity/
git commit -m "feat(application): Add entity classes

- Add JobApplication entity
- Add ApplicationStatus enum

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 3: Repository
```bash
git add application-service/src/main/java/com/revhire/application/repository/
git commit -m "feat(application): Add repository interfaces

- Add JobApplicationRepository
- Add queries for seeker and job filtering

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 4: DTOs
```bash
git add application-service/src/main/java/com/revhire/application/dto/
git commit -m "feat(application): Add DTO classes

- Add ApplyRequest
- Add WithdrawRequest
- Add ApplicationResponse
- Add ApplicationStatusUpdateRequest

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 5: Feign Clients
```bash
git add application-service/src/main/java/com/revhire/application/client/
git commit -m "feat(application): Add Feign clients

- Add AuthServiceClient
- Add JobseekerServiceClient (resume validation)
- Add EmployerServiceClient (job validation)
- Add NotificationClient

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 6: Service Layer
```bash
git add application-service/src/main/java/com/revhire/application/service/
git commit -m "feat(application): Add service layer

- Add ApplicationService with status transitions
- Validate resume exists before applying
- Validate job is OPEN before applying
- Implement withdrawal with reason

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 7: Controllers
```bash
git add application-service/src/main/java/com/revhire/application/controller/
git commit -m "feat(application): Add REST controllers

- Add JobSeekerApplicationController
- Add EmployerApplicationController
- Add internal endpoints

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 8: Exception Handling
```bash
git add application-service/src/main/java/com/revhire/application/exception/
git commit -m "feat(application): Add exception handling

- Add GlobalExceptionHandler
- Add custom exceptions

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

#### Commit 9: Configuration
```bash
git add application-service/src/main/java/com/revhire/application/config/
git add application-service/src/main/java/com/revhire/application/ApplicationServiceApplication.java
git add application-service/src/main/resources/
git commit -m "feat(application): Add application configuration

- Add FeignClientConfig
- Add main application class
- Add application.yml

🤖 Generated with Claude Code

Co-Authored-By: Guru Prasadh <guruprasadh@example.com>"
```

### Your API Endpoints
```
POST /api/jobseeker/jobs/{jobId}/apply
POST /api/jobseeker/jobs/{jobId}/withdraw
GET  /api/jobseeker/jobs/applied
GET  /api/jobseeker/jobs/applications
POST /api/jobseeker/jobs/applications/{id}/withdraw
PATCH /api/applications/{id}/status
GET  /internal/applications/job/{jobId}
GET  /internal/applications/seeker/{seekerId}
```

### Status Transitions
```
APPLIED -> UNDER_REVIEW, SHORTLISTED, REJECTED
UNDER_REVIEW -> SHORTLISTED, REJECTED
SHORTLISTED -> REJECTED
WITHDRAWN (terminal)
REJECTED (terminal)
```

---

## PHASE 3: Push to Remote Repository

After all team members have committed their services:

```bash
# Add remote origin
git remote add origin https://github.com/your-org/p3-revhire-microservices.git

# Push to main branch
git push -u origin main
```

---

## Running the Application Locally

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Docker (optional)

### Database Setup

Create the required databases:

```sql
CREATE DATABASE revhire_auth;
CREATE DATABASE revhire_jobseeker;
CREATE DATABASE revhire_employer;
CREATE DATABASE revhire_jobs;
CREATE DATABASE revhire_applications;
```

### Start Order (Important!)

1. **Start Eureka Server first**
   ```bash
   cd eureka-server && mvn spring-boot:run
   ```

2. **Start Config Server second**
   ```bash
   cd config-server && mvn spring-boot:run
   ```

3. **Start API Gateway third**
   ```bash
   cd api-gateway && mvn spring-boot:run
   ```

4. **Start Auth Service fourth** (other services depend on it)
   ```bash
   cd auth-service && mvn spring-boot:run
   ```

5. **Start remaining services in any order**
   ```bash
   cd jobseeker-service && mvn spring-boot:run
   cd employer-service && mvn spring-boot:run
   cd job-search-service && mvn spring-boot:run
   cd application-service && mvn spring-boot:run
   ```

### Verify Services

- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Config Server: http://localhost:8888

---

## Testing the API

### Register a Job Seeker
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "jobseeker1",
    "email": "seeker@example.com",
    "password": "Password@123",
    "confirmPassword": "Password@123",
    "fullName": "John Doe",
    "mobileNumber": "1234567890",
    "securityQuestion": "Pet name?",
    "securityAnswer": "Max",
    "location": "New York",
    "employmentStatus": "FRESHER",
    "role": "JOB_SEEKER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "jobseeker1",
    "password": "Password@123"
  }'
```

### Use the returned token for authenticated requests
```bash
curl -X GET http://localhost:8080/api/jobseeker/profile \
  -H "Authorization: Bearer <your-token>"
```

---

## Contact Information

| Developer | Module | Email |
|-----------|--------|-------|
| Niranjan | Auth & Notification | niranjan@example.com |
| Madhusudhan | Jobseeker | madhusudhan@example.com |
| Venkatesh | Employer | venkatesh@example.com |
| Anil | Job Search | anil@example.com |
| Guru Prasadh | Application | guruprasadh@example.com |

---

## Notes

1. Replace `<developer>@example.com` with actual email addresses
2. Each developer should commit from their own Git account
3. Follow the commit order to maintain proper dependency chain
4. Test your service locally before pushing
5. Ensure all services register with Eureka before testing inter-service communication

---

Generated with Claude Code - March 2026
