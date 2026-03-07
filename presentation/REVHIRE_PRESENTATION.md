# Project Presentation Deck - RevHire

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 5 members
**Template Version:** 1.0

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
+-----------------------------------------------------------------+
|                                                                 |
|                         RevHire                                 |
|              Job Portal Recruitment Platform                    |
|                                                                 |
|     Phase 1: Monolithic Application (AWS Deployment)            |
|     Phase 2: Microservices Architecture (Docker)                |
|                                                                 |
|     Team Members (Full Stack Developers):                       |
|     - Niranjan - Authentication and Notification                |
|     - Madhusudhan - Jobseeker                                   |
|     - Venkatesh - Employer                                      |
|     - Anil - Job Search Management                              |
|     - Guru Prasadh - Application Module                         |
|                                                                 |
|     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]               |
|                                                                 |
+-----------------------------------------------------------------+
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A comprehensive job portal application that connects job seekers with employers, enabling job seekers to manage profiles, build resumes, search and apply for jobs, while employers can post positions, manage applications, and evaluate candidates.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | Job Seeker Registration & Profile | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-02 | Resume Management (Create, Upload) | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-03 | Job Search with Multiple Filters | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-04 | Employer Registration & Company Profile | Use Case 2 (14 pts) | Yes | Yes |
| FR-05 | Job Posting Management (CRUD) | Use Case 2 (14 pts) | Yes | Yes |
| FR-06 | Applicant Evaluation & Management | Use Case 2 (14 pts) | Yes | Yes |
| FR-07 | JWT Authentication & Password Recovery | Common Features (6 pts) | Yes | Yes |
| FR-08 | Application Status Tracking | Common Features (6 pts) | Yes | Yes |
| FR-09 | In-App Notifications | Common Features (6 pts) | Yes | Yes |
| FR-10 | Job Statistics & Analytics | Common Features | Yes | Yes |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Authentication | JWT with BCrypt |
| Security | Password Storage | BCrypt Encoding |
| Security | Input Validation | XSS & SQL Injection Prevention |
| Performance | API Response | < 500ms |
| Availability | Uptime | 99% |
| Scalability | Architecture | Microservices (Phase 2) |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL database for production |
| A-02 | AWS Free Tier resources accessible for Phase 1 |
| A-03 | Docker environment available for Phase 2 microservices |
| A-04 | Team has GitHub repository access |
| A-05 | Two user roles: JOB_SEEKER and EMPLOYER |
| A-06 | Resume files stored as BLOB in database (max 2MB) |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Inter-service communication failures | High | OpenFeign with fallback handling |
| Service discovery failures | High | Eureka health checks and retry logic |
| File upload size exceeds limits | Medium | File size validation (2MB max) |
| Insufficient test coverage | High | Unit tests with JUnit & Mockito |
| Password reset token expiry | Low | 30-minute token validity |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
+-----------------------------------------------------------------+
|                    Angular 16 Frontend                          |
|              (TypeScript, RxJS, Bootstrap)                      |
+-----------------------------------------------------------------+
                              |
                    REST API (HTTP/JSON)
                              |
+-----------------------------------------------------------------+
|                   Spring Boot 3.3.2 Backend                     |
|     +-------------------------------------------------------+   |
|     |  Auth | JobSeeker | Employer | Job | Application |...|   |
|     +-------------------------------------------------------+   |
|     |              Spring Security + JWT                     |  |
|     +-------------------------------------------------------+   |
|     |              Spring Data JPA / Hibernate               |  |
|     +-------------------------------------------------------+   |
+-----------------------------------------------------------------+
                              |
+-----------------------------------------------------------------+
|                    MySQL 8 Database                             |
|  users | job_postings | applications | resumes | notifications  |
+-----------------------------------------------------------------+
```

**Technologies:** Java 21, Spring Boot 3.3.2, Spring Security, Spring Data JPA, JWT (JJWT 0.11.5), BCrypt, MySQL 8, Angular 16, Log4j2

---

### SLIDE 5: Phase 2 - Microservices Architecture (2 minutes)

```
+-----------------------------------------------------------------+
|                       Angular Frontend                          |
+-----------------------------------------------------------------+
                              |
+-----------------------------------------------------------------+
|                   API Gateway (Port 8080)                       |
|              (JWT Validation, Request Routing)                  |
+-----------------------------------------------------------------+
           |          |          |          |          |
     +----------+ +----------+ +----------+ +----------+ +----------+
     |   Auth   | |JobSeeker | | Employer | |   Job    | |Application|
     | Service  | | Service  | | Service  | | Service  | | Service  |
     |  (8081)  | |  (8082)  | |  (8083)  | |  (8084)  | |  (8085)  |
     +----------+ +----------+ +----------+ +----------+ +----------+
           |                                                   |
     +----------+                                        +----------+
     |Notification|                                      | Config   |
     | Service   |                                       | Server   |
     |  (8086)   |                                       |  (8888)  |
     +----------+                                        +----------+
                              |
+-----------------------------------------------------------------+
|               Discovery Server - Eureka (Port 8761)             |
+-----------------------------------------------------------------+
```

**Service Ports:**
| Service | Port | Owner |
|---------|------|-------|
| Config Server | 8888 | Shared |
| Eureka Server | 8761 | Shared |
| API Gateway | 8080 | Shared |
| Auth Service | 8081 | Niranjan |
| JobSeeker Service | 8082 | Madhusudhan |
| Employer Service | 8083 | Venkatesh |
| Job Service | 8084 | Anil |
| Application Service | 8085 | Guru Prasadh |
| Notification Service | 8086 | Niranjan |

---

### SLIDE 6: ERD - Entity Relationship Diagram (1-2 minutes)

```
+------------------+          +------------------+
|      users       |          |   job_postings   |
+------------------+          +------------------+
| PK auth_id       |<-------->| PK id            |
| username (UK)    |    |     | FK employer_id   |
| email (UK)       |    |     | company_name     |
| password (BCrypt)|    |     | title            |
| role (ENUM)      |    |     | description      |
| full_name        |    |     | skills           |
| mobile_number    |    |     | education        |
| security_question|    |     | max_experience   |
| security_answer  |    |     | location         |
| location         |    |     | min_salary       |
| employment_status|    |     | max_salary       |
| profile_completed|    |     | job_type         |
| created_at       |    |     | openings         |
+------------------+    |     | deadline         |
       |                |     | status (ENUM)    |
       |                |     | created_at       |
       v                |     +------------------+
+------------------+    |            |
| jobseeker_profiles|   |            |
+------------------+    |            |
| PK id            |    |            |
| FK user_id (UK)  |    |            v
| skills           |    |     +------------------+
| education        |    |     | job_applications |
| certifications   |    |     +------------------+
| headline         |    +---->| PK id            |
| summary          |          | FK job_id        |
| created_at       |          | FK jobseeker_id  |
+------------------+          | cover_letter     |
                              | status (ENUM)    |
+------------------+          | employer_notes   |
|     resumes      |          | applied_at       |
+------------------+          | withdrawn_reason |
| PK id            |          +------------------+
| FK user_id (UK)  |
| objective        |          +------------------+
| education_csv    |          |  notifications   |
| experience_csv   |          +------------------+
| projects_csv     |          | PK id            |
| certifications   |          | FK recipient_id  |
| skills           |          | job_id           |
| uploaded_file    |          | type (ENUM)      |
| file_data (BLOB) |          | message          |
| created_at       |          | is_read          |
+------------------+          | created_at       |
                              +------------------+
+------------------+
| employer_profiles|
+------------------+
| PK id            |
| FK user_id (UK)  |
| company_name     |
| industry         |
| company_size     |
| description      |
| website          |
| location         |
| created_at       |
+------------------+

+------------------+
|password_reset_tokens|
+------------------+
| PK id            |
| token (UUID)     |
| FK user_id       |
| expiry_at        |
+------------------+
```

**Enums:**
- **User Roles:** JOB_SEEKER, EMPLOYER
- **Employment Status:** FRESHER, EMPLOYED, UNEMPLOYED
- **Job Status:** OPEN, CLOSED, FILLED
- **Application Status:** APPLIED, SHORTLISTED, REJECTED, WITHDRAWN
- **Notification Types:** APPLICATION_RECEIVED, APPLICATION_UPDATE, JOB_RECOMMENDATION, SYSTEM

---

### SLIDE 7: Security Implementation (1-2 minutes)

#### Authentication Flow

```
+--------+     +----------+     +-------------+     +----------+
| Client | --> |  Login   | --> | Validate    | --> | Generate |
|        |     | Request  |     | Credentials |     | JWT      |
+--------+     +----------+     +-------------+     +----------+
                                      |                   |
                              BCrypt Verify          24hr Expiry
                                      |                   |
                              +-------------+     +----------+
                              |   User DB   |     | Return   |
                              |             |     | Token    |
                              +-------------+     +----------+
```

#### Security Features

| Feature | Implementation |
|---------|---------------|
| Password Hashing | BCrypt Encoding |
| Token Library | JJWT 0.11.5 |
| Token Expiry | 24 hours (86400000ms) |
| Password Recovery | Security Question + Reset Token |
| Reset Token Expiry | 30 minutes |
| Input Sanitization | XSS & SQL Injection Prevention |
| Authorization | Spring Security on all endpoints |

#### Phase 2 Gateway Security

```
Request --> API Gateway --> Validate JWT --> Route to Service
                |                                   |
                v                           Forward X-User-Id
         AuthenticationFilter               Header to Service
```

---

### SLIDE 8: Testing Strategy (1-2 minutes)

#### Testing Approach

| Test Type | Framework | Coverage |
|-----------|-----------|----------|
| Unit Tests | JUnit 4, Mockito | Service & Controller layers |
| Integration Tests | Spring Boot Test | API endpoints |
| Frontend Tests | Jasmine + Karma | Components & Services |

#### Test Coverage by Module

| Module | Test Files |
|--------|------------|
| Auth | AuthControllerTest, AuthServiceTest, JwtServiceTest |
| JobSeeker | JobSeekerProfileControllerTest, JobSeekerProfileServiceTest |
| Employer | EmployerControllerTest |
| Job | JobControllerTest, JobSeekerJobControllerTest |
| Application | ApplicationControllerTest |
| Notification | NotificationControllerTest, NotificationServiceTest |
| Security | JwtAuthenticationFilterTest, CustomUserDetailsServiceTest |
| Utilities | InputSanitizerTest, LegacyPasswordUtilTest |

#### Test Execution

```bash
cd backend
mvn test
```

---

### SLIDE 9: API Documentation (1 minute)

#### Key API Endpoints

**Authentication (Auth Service - 8081):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | User registration |
| POST | /api/auth/login | Login with JWT |
| POST | /api/auth/forgot-password | Initiate password recovery |
| POST | /api/auth/reset-password | Reset password with token |
| POST | /api/auth/change-password | Change password (authenticated) |
| GET | /api/auth/validate | Token validation (inter-service) |

**JobSeeker (JobSeeker Service - 8082):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/jobseeker/profile | Get profile |
| PUT | /api/jobseeker/profile | Update profile |
| GET | /api/resume | Get resume |
| PUT | /api/resume | Update resume |
| POST | /api/resume/upload | Upload resume file |

**Employer (Employer Service - 8083):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/employer/company-profile | Get company profile |
| PUT | /api/employer/company-profile | Update company profile |
| GET | /api/employer/statistics | Get statistics |

**Jobs (Job Service - 8084):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/jobs | Get all open jobs |
| GET | /api/jobseeker/jobs/search | Search jobs with filters |
| POST | /api/employer/jobs | Create job posting |
| PATCH | /api/employer/jobs/{id}/close | Close job |

---

### SLIDE 10: Use Case 1 - Authentication and Notification (Niranjan) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- User registration with role-based access (JOB_SEEKER/EMPLOYER)
- Login with JWT token generation
- Password management (change, forgot, reset)
- Security question-based recovery
- Notification management for all events

#### Authentication Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | Register/   | --> | Validate &  | --> | Generate |
|        |     | Login       |     | Hash Pass   |     | JWT      |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+     +----------+
              | Store User  | --> | Return      | --> | Profile  |
              | in Database |     | Auth Token  |     | Complete |
              +-------------+     +-------------+     +----------+
```

#### API Endpoints Owned

**Auth Service (8081):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | User registration |
| POST | /api/auth/login | Login with JWT |
| POST | /api/auth/logout | User logout |
| POST | /api/auth/forgot-password | Password recovery |
| POST | /api/auth/reset-password | Reset with token |
| POST | /api/auth/change-password | Change password |
| POST | /api/auth/profile-completion/{userId} | Complete profile |

**Notification Service (8086):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/notifications/user/{userId} | Get notifications |
| GET | /api/notifications/unread-count | Unread count |
| PATCH | /api/notifications/{id}/read | Mark as read |
| POST | /api/notifications/application-received | New application alert |
| POST | /api/notifications/application-status-change | Status update alert |

#### Code Highlight (AuthService.java)

```java
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setSecurityQuestion(request.getSecurityQuestion());
        user.setSecurityAnswer(passwordEncoder.encode(request.getSecurityAnswer()));

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, mapToResponse(user));
    }
}
```

---

### SLIDE 11: Use Case 2 - Jobseeker (Madhusudhan) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Job seeker profile management
- Skills, education, certifications tracking
- Resume creation and management
- Resume file upload (PDF, DOC, DOCX)
- Employment status tracking

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | Complete    | --> | Update      | --> | Build    |
|        |     | Profile     |     | Skills/Edu  |     | Resume   |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                                          v
                                   +-------------+
                                   | Upload File |
                                   | (PDF/DOC)   |
                                   +-------------+
```

#### API Endpoints Owned (JobSeeker Service - 8082)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/jobseeker/profile | Get profile (X-User-Id) |
| PUT | /api/jobseeker/profile | Update profile |
| GET | /api/resume | Get resume details |
| PUT | /api/resume | Update resume |
| POST | /api/resume/upload | Upload resume file |
| GET | /api/resume/download/{userId} | Download resume (internal) |

#### Code Highlight (JobSeekerProfileService.java)

```java
@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository profileRepository;
    private final ResumeRepository resumeRepository;
    private final AuthServiceClient authClient;

    public JobSeekerProfileResponse updateProfile(Long userId,
            JobSeekerProfileRequest request) {

        // Verify user exists via Auth Service
        authClient.getUser(userId);

        JobSeekerProfile profile = profileRepository.findByUserId(userId)
            .orElse(new JobSeekerProfile());

        profile.setUserId(userId);
        profile.setSkills(sanitize(request.getSkills()));
        profile.setEducation(sanitize(request.getEducation()));
        profile.setCertifications(sanitize(request.getCertifications()));
        profile.setHeadline(sanitize(request.getHeadline()));
        profile.setSummary(sanitize(request.getSummary()));
        profile.setEmploymentStatus(request.getEmploymentStatus());

        return mapToResponse(profileRepository.save(profile));
    }
}
```

---

### SLIDE 12: Use Case 3 - Employer (Venkatesh) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Employer company profile management
- Company details (name, industry, size, description)
- Statistics aggregation from Job and Application services
- Integration with other services via Feign clients

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
| Employer| --> | Create      | --> | Update      | --> | View     |
|        |     | Company     |     | Profile     |     | Stats    |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+
              | Job Service | --> | Application |
              | Statistics  |     | Statistics  |
              +-------------+     +-------------+
```

#### API Endpoints Owned (Employer Service - 8083)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/employer/company-profile | Get company profile |
| PUT | /api/employer/company-profile | Update company profile |
| GET | /api/employer/statistics | Get employer statistics |
| GET | /api/employer/health | Service health check |

#### Code Highlight (EmployerService.java)

```java
@Service
public class EmployerService {

    private final EmployerProfileRepository profileRepository;
    private final JobServiceClient jobServiceClient;
    private final ApplicationServiceClient applicationServiceClient;

    public EmployerStatisticsResponse getStatistics(Long userId) {
        // Aggregate statistics from multiple services
        var jobStats = jobServiceClient.getEmployerJobStatistics(userId);
        var appStats = applicationServiceClient.getEmployerStatistics(userId);

        return EmployerStatisticsResponse.builder()
            .totalJobs(jobStats.getTotalJobs())
            .openJobs(jobStats.getOpenJobs())
            .closedJobs(jobStats.getClosedJobs())
            .totalApplications(appStats.getTotalApplications())
            .pendingApplications(appStats.getPendingApplications())
            .shortlistedApplications(appStats.getShortlistedApplications())
            .build();
    }
}
```

---

### SLIDE 13: Use Case 4 - Job Search Management (Anil) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Job posting CRUD operations for employers
- Job search with multiple filters for job seekers
- Job status management (Open, Closed, Filled)
- Job statistics for employers
- Integration with Notification service

#### Job Search Filters

| Filter | Description |
|--------|-------------|
| title | Job title keyword |
| location | Job location |
| companyName | Company name |
| jobType | Full-time, Part-time, Contract |
| maxExperienceYears | Maximum experience required |
| minSalary | Minimum salary |
| maxSalary | Maximum salary |
| datePostedAfter | Jobs posted after date |

#### API Endpoints Owned (Job Service - 8084)

**Public Endpoints:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/jobs | Get all open jobs |
| GET | /api/jobs/{jobId} | Get job by ID |

**Job Seeker Endpoints:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/jobseeker/jobs/search | Search with filters |

**Employer Endpoints:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/employer/jobs | Get employer's jobs |
| POST | /api/employer/jobs | Create new job |
| PUT | /api/employer/jobs/{jobId} | Update job |
| DELETE | /api/employer/jobs/{jobId} | Delete job |
| PATCH | /api/employer/jobs/{jobId}/close | Close job |
| PATCH | /api/employer/jobs/{jobId}/reopen | Reopen job |
| PATCH | /api/employer/jobs/{jobId}/fill | Mark as filled |
| GET | /api/employer/jobs/statistics | Get job statistics |

#### Code Highlight (JobService.java)

```java
@Service
public class JobService {

    private final JobPostingRepository jobRepository;
    private final NotificationServiceClient notificationClient;

    public List<JobResponse> searchJobs(JobSearchRequest request) {
        return jobRepository.searchJobs(
            request.getTitle(),
            request.getLocation(),
            request.getCompanyName(),
            request.getJobType(),
            request.getMaxExperienceYears(),
            request.getMinSalary(),
            request.getMaxSalary(),
            request.getDatePostedAfter()
        ).stream()
         .map(this::mapToResponse)
         .collect(Collectors.toList());
    }

    public JobResponse createJob(Long employerId, JobRequest request) {
        JobPosting job = new JobPosting();
        job.setEmployerId(employerId);
        job.setTitle(sanitize(request.getTitle()));
        job.setDescription(sanitize(request.getDescription()));
        job.setSkills(sanitize(request.getSkills()));
        job.setStatus(JobStatus.OPEN);

        return mapToResponse(jobRepository.save(job));
    }
}
```

---

### SLIDE 14: Use Case 5 - Application Module (Guru Prasadh) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Job application submission for job seekers
- Application status tracking (Applied, Shortlisted, Rejected, Withdrawn)
- Application withdrawal with reason
- Employer applicant management
- Application statistics

#### Application Status Flow

```
+----------+     +-------------+     +-------------+     +----------+
| APPLIED  | --> | SHORTLISTED | --> | (Interview) | --> | HIRED or |
|          |     |             |     |             |     | REJECTED |
+----------+     +-------------+     +-------------+     +----------+
     |
     v
+-----------+
| WITHDRAWN |
| (optional)|
+-----------+
```

#### API Endpoints Owned (Application Service - 8085)

**Job Seeker Endpoints:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/applications/apply | Apply to job |
| GET | /api/applications/my-applications | Get my applications |
| PATCH | /api/applications/{id}/withdraw | Withdraw application |

**Employer Endpoints:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/applications/employer | Get applications for my jobs |
| PATCH | /api/applications/{id}/status | Update application status |
| PUT | /api/applications/{id}/notes | Add employer notes |
| GET | /api/applications/employer/statistics | Get statistics |

#### Code Highlight (ApplicationService.java)

```java
@Service
public class ApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final NotificationClient notificationClient;
    private final JobSeekerServiceClient jobSeekerClient;

    public ApplicationResponse applyToJob(Long jobSeekerId, ApplyRequest request) {
        // Check for duplicate application
        if (applicationRepository.existsByJobIdAndJobSeekerId(
                request.getJobId(), jobSeekerId)) {
            throw new ConflictException("Already applied to this job");
        }

        JobApplication application = new JobApplication();
        application.setJobId(request.getJobId());
        application.setJobSeekerId(jobSeekerId);
        application.setCoverLetter(sanitize(request.getCoverLetter()));
        application.setStatus(ApplicationStatus.APPLIED);

        JobApplication saved = applicationRepository.save(application);

        // Notify employer
        notificationClient.sendApplicationReceivedNotification(
            request.getJobId(), jobSeekerId);

        return mapToResponse(saved);
    }

    public ApplicationResponse updateStatus(Long applicationId,
            ApplicationStatus newStatus, Long employerId) {
        JobApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new NotFoundException("Application not found"));

        application.setStatus(newStatus);
        applicationRepository.save(application);

        // Notify job seeker of status change
        notificationClient.sendStatusChangeNotification(
            application.getJobSeekerId(), newStatus);

        return mapToResponse(application);
    }
}
```

---

### SLIDE 15: Phase 2 - Microservices Deep Dive (2 minutes)

#### Service Architecture Details

| Service | Port | Database | Key Dependencies |
|---------|------|----------|------------------|
| Config Server | 8888 | None | Spring Cloud Config |
| Eureka Server | 8761 | None | Eureka Server |
| API Gateway | 8080 | None | Spring Cloud Gateway, JWT |
| Auth Service | 8081 | revhire_auth_db | Spring Security, JWT |
| JobSeeker Service | 8082 | revhire_jobseeker_db | OpenFeign (Auth) |
| Employer Service | 8083 | revhire_employer_db | OpenFeign (Auth, Job, App) |
| Job Service | 8084 | revhire_job_db | OpenFeign (Auth, Employer, Notification) |
| Application Service | 8085 | revhire_application_db | OpenFeign (All services) |
| Notification Service | 8086 | revhire_notification_db | OpenFeign (Auth) |

#### Inter-Service Communication

```
+----------------+     OpenFeign     +----------------+
| Employer       | <--------------> | Job Service    |
| Service        |                  |                |
+----------------+                  +----------------+
        |                                   |
        |          +----------------+       |
        +--------> | Application    | <-----+
                   | Service        |
                   +----------------+
                          |
                          v
                   +----------------+
                   | Notification   |
                   | Service        |
                   +----------------+
```

#### Feign Client Example

```java
@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    @GetMapping("/api/auth/users/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable Long userId);

    @GetMapping("/api/auth/validate")
    ApiResponse<Boolean> validateToken(@RequestParam String token);
}
```

---

### SLIDE 16: Deployment Architecture (1-2 minutes)

#### Phase 1: AWS Deployment

```
+---------------------------+
|    AWS Cloud              |
|  +---------------------+  |
|  |    EC2 Instance     |  |
|  |  +--------------+   |  |
|  |  | Spring Boot  |   |  |
|  |  | Application  |   |  |
|  |  +--------------+   |  |
|  |         |           |  |
|  |  +--------------+   |  |
|  |  |    MySQL     |   |  |
|  |  |   Database   |   |  |
|  |  +--------------+   |  |
|  +---------------------+  |
+---------------------------+
```

#### Phase 2: Docker Deployment

```
+--------------------------------------------------+
|              Docker Compose                       |
|  +------------+  +------------+  +------------+  |
|  |  Config    |  |  Eureka    |  |    API     |  |
|  |  Server    |  |  Server    |  |  Gateway   |  |
|  +------------+  +------------+  +------------+  |
|                                                   |
|  +--------+ +--------+ +--------+ +--------+     |
|  |  Auth  | |JobSeeker| |Employer| |  Job   |    |
|  +--------+ +--------+ +--------+ +--------+     |
|                                                   |
|  +------------+  +------------+                   |
|  |Application |  |Notification|                   |
|  +------------+  +------------+                   |
|                                                   |
|  Each service has its own MySQL database          |
+--------------------------------------------------+
```

#### Docker Commands

```bash
# Build all services
docker-compose build

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

---

### SLIDE 17: Demo Walkthrough (2-3 minutes)

#### Demo Scenario

**Step 1: User Registration**
- Register as Job Seeker with profile details
- Register as Employer with company details

**Step 2: Job Seeker Flow**
- Complete profile (skills, education, experience)
- Build and upload resume
- Search jobs with filters
- Apply to job with cover letter
- View application status

**Step 3: Employer Flow**
- Create company profile
- Post job opening with details
- View applicants
- Shortlist/Reject candidates
- View statistics

**Step 4: Notifications**
- View notifications for status changes
- Mark notifications as read

**Step 5: Microservices Demo (Phase 2)**
- Show Eureka dashboard (http://localhost:8761)
- Demonstrate service discovery
- Show inter-service communication

---

### SLIDE 18: Challenges & Solutions (1-2 minutes)

| Challenge | Solution |
|-----------|----------|
| JWT validation across services | API Gateway centralized validation, X-User-Id header forwarding |
| Large resume file uploads | BLOB storage with 2MB limit, file type validation |
| Password recovery security | Security questions + time-limited reset tokens (30 min) |
| Cross-service statistics | Feign clients for aggregation in Employer Service |
| Application duplicate prevention | Unique constraint on (job_id, jobseeker_id) |
| Input sanitization | Centralized InputSanitizer utility for XSS prevention |

---

### SLIDE 19: Key Learnings (1 minute)

| Area | Learning |
|------|----------|
| Microservices | Each service should own its database for loose coupling |
| Security | JWT with short expiry and proper secret management |
| Inter-service | OpenFeign simplifies REST client implementation |
| File Storage | BLOB storage works for small files, consider S3 for scale |
| Testing | Mock external services in unit tests |
| DevOps | Docker Compose enables consistent local development |

---

### SLIDE 20: Future Enhancements (1 minute)

| Enhancement | Description |
|-------------|-------------|
| Email Notifications | Send email alerts for important events |
| Real-time Notifications | WebSocket for instant updates |
| Advanced Search | Elasticsearch for full-text job search |
| Resume Parsing | AI-powered resume data extraction |
| Interview Scheduling | Calendar integration for interviews |
| Analytics Dashboard | Charts and insights for employers |
| Mobile App | React Native mobile application |
| OAuth2 Integration | Social login (Google, LinkedIn) |

---

### SLIDE 21: Repository & Resources (30 seconds)

#### GitHub Repositories

| Phase | Repository |
|-------|------------|
| Phase 1 (Monolithic) | https://github.com/niranjan2024/REVHIRE_-2 |
| Phase 2 (Microservices) | https://github.com/niranjan2024/RevHire_3 |

#### Technology Documentation

- [Spring Boot 3.3.2](https://spring.io/projects/spring-boot)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Angular 16](https://angular.io/)
- [Docker Compose](https://docs.docker.com/compose/)

---

### SLIDE 22: Team Contributions Summary

| Team Member | Phase 1 Contribution | Phase 2 Service(s) |
|-------------|---------------------|-------------------|
| Niranjan | Auth module, Notifications | Auth Service (8081), Notification Service (8086) |
| Madhusudhan | JobSeeker profile, Resume | JobSeeker Service (8082) |
| Venkatesh | Employer profile, Statistics | Employer Service (8083) |
| Anil | Job posting, Job search | Job Service (8084) |
| Guru Prasadh | Applications, Status tracking | Application Service (8085) |

---

### SLIDE 23: Q&A (5-10 minutes)

```
+-----------------------------------------------------------------+
|                                                                 |
|                     Questions & Answers                         |
|                                                                 |
|     Thank you for your attention!                               |
|                                                                 |
|     Contact:                                                    |
|     - GitHub: github.com/niranjan2024                           |
|                                                                 |
+-----------------------------------------------------------------+
```

---

## PRESENTATION NOTES

### Time Allocation

| Section | Duration |
|---------|----------|
| Introduction & Overview (Slides 1-3) | 3 minutes |
| Architecture (Slides 4-6) | 5 minutes |
| Security & Testing (Slides 7-8) | 3 minutes |
| API Documentation (Slide 9) | 1 minute |
| Team Member Use Cases (Slides 10-14) | 10 minutes |
| Microservices Deep Dive (Slide 15) | 2 minutes |
| Deployment & Demo (Slides 16-17) | 4 minutes |
| Wrap-up (Slides 18-23) | 4 minutes |
| **Total** | **32 minutes** |

### Demo Preparation Checklist

- [ ] MySQL databases created for all services
- [ ] Backend servers started (all microservices)
- [ ] Frontend running on Angular dev server
- [ ] Test accounts created (Job Seeker, Employer)
- [ ] Sample job postings created
- [ ] Docker Compose environment ready (Phase 2)
- [ ] Eureka dashboard accessible

---

*RevHire Team - 2024*
