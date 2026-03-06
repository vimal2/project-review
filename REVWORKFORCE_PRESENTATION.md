# Project Presentation Deck - RevWorkforce

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 5 members
**Template Version:** 1.0

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                      RevWorkforce                           │
│          Human Resource Management System                   │
│                                                             │
│     Phase 1: Monolithic Application (AWS Deployment)        │
│     Phase 2: Microservices Architecture (Local)             │
│                                                             │
│     Team Members (Full Stack Developers):                   │
│     • Krishna Babu - Admin, Login, Testing, Notification    │
│     • Saideep - Performance, Goals & Feedback               │
│     • Ganesh - Authentication & Authorization               │
│     • Sai Krishna - Leave, Leave-Notifications              │
│     • Vamsi - Employee, Directory                           │
│                                                             │
│     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A comprehensive Human Resource Management system to streamline employee management, leave tracking, and performance review processes across three user roles—Employees, Managers, and Admins.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | Leave Application & Tracking | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-02 | Leave Approval/Rejection Workflow | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-03 | Performance Review Creation & Feedback | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-04 | Goals Management with Progress Tracking | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-05 | User Authentication (Login/JWT) | Common Features (6 pts) | ✓ | ✓ |
| FR-06 | CRUD Operations for Employees/Departments | Common Features (6 pts) | ✓ | ✓ |
| FR-07 | Input Validation & Error Handling | Common Features (6 pts) | ✓ | ✓ |
| FR-08 | Employee Directory & Search | Common Features | ✓ | ✓ |
| FR-09 | Notification System | Common Features | ✓ | ✓ |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Password Storage | BCrypt Hashing |
| Security | Authentication | JWT Tokens (Stateless) |
| Performance | API Response | < 500ms |
| Availability | Uptime (P1) | 99% |
| Logging | Audit Trail | Request/Method Logging with Correlation IDs |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL database available (local + AWS RDS) |
| A-02 | AWS Free Tier resources accessible |
| A-03 | Modern browser support (Chrome, Firefox, Edge) |
| A-04 | Team has GitHub repository access |
| A-05 | Three distinct user roles: Employee, Manager, Admin |
| A-06 | Employees have single manager assignment (self-referential relationship) |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Limited microservices experience | Medium | Pair programming, documentation |
| AWS deployment issues | High | Practice deployments, local fallback |
| Insufficient test coverage | High | TDD approach, code reviews |
| Complex leave balance calculations | Medium | Clear business rules, unit testing |
| Role-based access complexity | Medium | Well-defined security annotations |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
┌────────────────────────────────────────────────────────────┐
│                  ANGULAR 16 FRONTEND                        │
│              (Components, Services, Guards)                 │
│                     Bootstrap 5 UI                          │
└─────────────────────────┬──────────────────────────────────┘
                          │ HTTP/REST + JWT
┌─────────────────────────┴──────────────────────────────────┐
│               SPRING BOOT 2.7 MONOLITH                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ Controllers │→ │  Services   │→ │ JPA Repositories    │ │
│  │ Auth, User  │  │ Leave, Goal │  │                     │ │
│  │ Leave, Perf │  │ Performance │  │                     │ │
│  │ Goal, Admin │  │ Notification│  │                     │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ Security: JWT Filter → Authentication → Authorization │  │
│  │           @PreAuthorize Role-Based Access Control     │  │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ Logging: RequestLoggingFilter + AOP Method Logging   │  │
│  │          Log4j2 with Correlation IDs                 │  │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────┬──────────────────────────────────┘
                          │ JDBC/JPA
┌─────────────────────────┴──────────────────────────────────┐
│                   MySQL DATABASE                            │
│                   (revworkforcep2)                          │
└────────────────────────────────────────────────────────────┘
```

#### Phase 2: Microservices Architecture

```
┌────────────────────────────────────────────────────────────┐
│                    ANGULAR FRONTEND                         │
└─────────────────────────┬──────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────┐
│                API GATEWAY (Port 8080)                      │
│            JWT Validation + Request Routing                 │
└────────┬─────────┬──────────┬──────────┬───────────────────┘
         │         │          │          │
    ┌────┴────┐ ┌──┴───┐ ┌────┴────┐ ┌───┴────┐
    │Auth Svc │ │Leave │ │ Perform │ │Employee│
    │  8081   │ │ Svc  │ │   Svc   │ │  Svc   │
    │         │ │ 8082 │ │  8083   │ │  8084  │
    └────┬────┘ └──┬───┘ └────┬────┘ └───┬────┘
         │         │          │          │
         └─────────┴──────────┴──────────┘
                          │
              ┌───────────┴───────────┐
              │    Eureka Server      │
              │      Port 8761        │
              └───────────────────────┘
                          │
              ┌───────────┴───────────┐
              │   MySQL Database(s)   │
              └───────────────────────┘
```

---

## CORE FEATURES SECTION (40 Points)

---

### SLIDE 5: Main Use Case 1 - Leave Management Design (20 Points) (2 minutes)

**Use Case:** Leave Management System

#### User Stories

> As an **Employee**, I want to apply for leave and track my leave balance so that I can manage my time off effectively.

> As a **Manager**, I want to review and approve/reject leave requests from my team so that I can ensure proper team coverage.

> As an **Admin**, I want to configure leave policies and manage holiday calendars so that the organization's leave rules are enforced.

#### Feature Flow

```
┌─────────────┐   ┌──────────────┐   ┌───────────────┐   ┌─────────────┐
│  Employee   │──►│ Apply Leave  │──►│ Manager       │──►│ Notification│
│  Request    │   │ (Validation) │   │ Approval      │   │ Sent        │
└─────────────┘   └──────────────┘   └───────────────┘   └─────────────┘
                                            │
                                            ▼
                                     ┌─────────────┐
                                     │ Balance     │
                                     │ Updated     │
                                     └─────────────┘
```

#### API Endpoints for Leave Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/leave/apply | Submit leave request |
| GET | /api/leave/my-requests | Get employee's leave requests |
| GET | /api/leave/team-requests | Get team's pending requests (Manager) |
| PUT | /api/leave/{id}/approve | Approve leave request |
| PUT | /api/leave/{id}/reject | Reject leave request |
| PUT | /api/leave/{id}/cancel | Cancel pending leave |
| GET | /api/leave/balance | Get leave balance summary |
| GET | /api/holidays | Get holiday calendar |

#### Implementation Highlights

- **Leave Types:** Casual, Sick, and Paid Leave categories
- **Balance Validation:** Prevents application when balance is zero
- **Status Workflow:** Pending → Approved/Rejected with manager comments
- **Notification Integration:** Automatic notifications on status changes

---

### SLIDE 6: Main Use Case 1 - Leave Management DEMO (3-4 minutes)

**Live Demo Script:**

| Step | Action | Expected Result | Points Demonstrated |
|------|--------|-----------------|---------------------|
| 1 | Login as Employee | Dashboard loads with leave balance | Authentication |
| 2 | View Leave Balance | Shows Casual, Sick, Paid balances | Data Display |
| 3 | Apply for Leave | Form with date range, type, reason | Form Validation |
| 4 | Submit Leave Request | Success message, status: Pending | CRUD Create |
| 5 | View My Requests | Request listed with Pending status | CRUD Read |
| 6 | Login as Manager | See team requests dashboard | Role-based Access |
| 7 | Approve Leave | Add comment, approve request | CRUD Update |
| 8 | Check Employee Notification | Employee sees approval notification | Notification System |
| 9 | Verify Balance Deducted | Leave balance reduced | Business Logic |
| 10 | Show backend logs | Request/response logged with correlation ID | Logging |

**Backup:** Screenshots/video recording prepared for demo failure scenarios

---

### SLIDE 7: Use Case 2 - Performance Management Design (14 Points) (1-2 minutes)

**Use Case:** Performance Review & Goals Management

#### User Stories

> As an **Employee**, I want to create performance reviews documenting my accomplishments and set goals for tracking my progress.

> As a **Manager**, I want to provide feedback and ratings on my team members' performance reviews and goals.

#### Feature Components

```
┌─────────────────────────────────────────────────────────────────┐
│                  PERFORMANCE MANAGEMENT FLOW                     │
│                                                                  │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│   │   Employee   │───►│   Manager    │───►│   Final Review   │  │
│   │ Self-Review  │    │   Feedback   │    │   with Rating    │  │
│   └──────────────┘    └──────────────┘    └──────────────────┘  │
│                                                                  │
│   Key Features:                                                  │
│   • Key Deliverables Documentation                               │
│   • Accomplishments & Areas of Improvement                       │
│   • Self-Rating (1-5 scale)                                      │
│   • Manager Feedback & Rating                                    │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      GOALS MANAGEMENT FLOW                       │
│                                                                  │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│   │  Create Goal │───►│   Progress   │───►│    Completion    │  │
│   │  with Deadline│   │   Updates    │    │   (Irreversible) │  │
│   └──────────────┘    └──────────────┘    └──────────────────┘  │
│                                                                  │
│   Goal Properties:                                               │
│   • Description, Deadline, Priority Level                        │
│   • Status Tracking (Active/Completed)                           │
│   • Manager Comments & Guidance                                  │
└─────────────────────────────────────────────────────────────────┘
```

#### API Endpoints for Performance Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/performance-reviews | Create performance review |
| GET | /api/performance-reviews/my-reviews | Get employee's reviews |
| GET | /api/performance-reviews/team | Get team reviews (Manager) |
| PUT | /api/performance-reviews/{id}/feedback | Submit manager feedback |
| POST | /api/goals | Create new goal |
| GET | /api/goals | List employee goals |
| PUT | /api/goals/{id} | Update goal status |
| PUT | /api/goals/{id}/complete | Mark goal as completed |

---

### SLIDE 8: Use Case 2 - Performance Management DEMO (2-3 minutes)

**Live Demo Script:**

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Login as Employee | Access Performance section |
| 2 | Create Performance Review | Fill key deliverables, accomplishments, areas of improvement |
| 3 | Add Self-Rating | Rate 1-5 scale |
| 4 | Create New Goal | Set description, deadline, priority |
| 5 | Login as Manager | View team performance dashboard |
| 6 | Provide Review Feedback | Add manager feedback and rating |
| 7 | Add Goal Comment | Provide guidance on employee goal |
| 8 | Login as Admin | Can review any employee's performance |
| 9 | Show notification | Employee notified of feedback |

---

### SLIDE 9: Common Features (6 Points) (1 minute)

#### Authentication Flow

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    Login     │───►│   Validate   │───►│   Generate   │───►│    Return    │
│   Request    │    │   Credentials│    │     JWT      │    │    Token     │
│ (email/pass) │    │   (BCrypt)   │    │  (24h exp)   │    │   + Role     │
└──────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
```

#### Common Features Implemented

| Feature | Implementation | Status |
|---------|---------------|--------|
| User Login | JWT Authentication + BCrypt | ✓ |
| Session Management | Stateless Token-based | ✓ |
| Role-Based Access | @PreAuthorize (Employee, Manager, Admin) | ✓ |
| Input Validation | Bean Validation (@Valid, @NotNull, @Email) | ✓ |
| Error Handling | GlobalExceptionHandler | ✓ |
| CORS Configuration | Allowed origins: localhost:4200, AWS domain | ✓ |
| Employee Directory | Search by name, department, designation | ✓ |
| Notification System | In-app notifications with read/unread status | ✓ |
| Holiday Calendar | Admin-managed company holidays | ✓ |
| Announcements | Company-wide announcements | ✓ |

**Quick Demo:** Login → Access protected route → View Directory → Logout

---

## TECHNICAL STANDARDS SECTION (35 Points)

---

### SLIDE 10: Code Organization (10 Points) (2 minutes)

#### Backend Package Structure

```
com.revworkforce/
├── controller/              # REST Controllers (API endpoints)
│   ├── AuthController.java
│   ├── UserController.java
│   ├── LeaveController.java
│   ├── PerformanceController.java
│   ├── GoalController.java
│   ├── NotificationController.java
│   └── AdminController.java
├── service/                 # Business Logic Layer
│   ├── AuthService.java
│   ├── UserService.java
│   ├── LeaveService.java
│   ├── PerformanceService.java
│   ├── GoalService.java
│   └── NotificationService.java
├── repository/              # Data Access Layer (JPA)
│   ├── EmployeeRepository.java
│   ├── LeaveRequestRepository.java
│   ├── PerformanceReviewRepository.java
│   ├── GoalRepository.java
│   └── NotificationRepository.java
├── entity/                  # JPA Entities
│   ├── Employee.java
│   ├── Role.java
│   ├── Department.java
│   ├── Designation.java
│   ├── LeaveRequest.java
│   ├── PerformanceReview.java
│   ├── Goal.java
│   ├── Notification.java
│   ├── Holiday.java
│   └── Announcement.java
├── dto/                     # Data Transfer Objects
│   ├── request/
│   └── response/
├── config/                  # Configuration Classes
│   ├── SecurityConfig.java
│   └── CorsConfig.java
├── security/                # Security Components
│   ├── JwtUtil.java
│   └── JwtAuthenticationFilter.java
├── logging/                 # Logging Components
│   ├── RequestLoggingFilter.java
│   └── MethodLoggingAspect.java
└── exception/               # Exception Handling
    ├── GlobalExceptionHandler.java
    └── CustomExceptions.java
```

#### Frontend Structure

```
src/app/
├── core/                    # Core module (singleton services)
│   └── interceptors/        # HTTP interceptors (JWT attachment)
├── shared/                  # Shared components, pipes, directives
├── features/                # Feature modules
│   ├── auth/                # Login component
│   ├── dashboard/           # Role-based dashboard
│   ├── leave/               # Leave management
│   ├── performance/         # Performance reviews
│   ├── goals/               # Goals management
│   ├── directory/           # Employee directory
│   ├── notifications/       # Notification center
│   └── admin/               # Admin panel
├── services/                # API services
├── guards/                  # Route guards (AuthGuard, RoleGuard)
└── models/                  # TypeScript interfaces
```

#### Design Patterns Used

| Pattern | Where Used | Benefit |
|---------|-----------|---------|
| Repository Pattern | Data access layer | Abstraction, testability |
| Service Layer | Business logic | Separation of concerns |
| DTO Pattern | API communication | Decouple entities from API |
| Dependency Injection | Throughout | Loose coupling |
| Interceptor Pattern | HTTP requests, Logging | Cross-cutting concerns |
| AOP (Aspect-Oriented) | Method logging | Non-invasive logging |
| Filter Chain | Security, Logging | Request processing pipeline |

---

### SLIDE 11: Database & Security (15 Points) (2-3 minutes)

#### Entity Relationship Diagram (ERD)

```
┌─────────────────┐          ┌─────────────────────┐
│      ROLES      │          │    DEPARTMENTS      │
├─────────────────┤          ├─────────────────────┤
│ PK role_id      │          │ PK dept_id          │
│    role_name    │          │    dept_name        │
└────────┬────────┘          └──────────┬──────────┘
         │                              │
         │ 1:N                          │ 1:N
         ▼                              ▼
┌─────────────────────────────────────────────────────────┐
│                      EMPLOYEES                           │
├─────────────────────────────────────────────────────────┤
│ PK employee_id (BIGINT)                                  │
│ UK email (VARCHAR)                                       │
│    password (HASHED)                                     │
│    name, phone, address, emergency_contact               │
│    status (ACTIVE/INACTIVE)                              │
│    created_at, updated_at                                │
│ FK role_id → ROLES                                       │
│ FK dept_id → DEPARTMENTS                                 │
│ FK desig_id → DESIGNATIONS                               │
│ FK manager_id → EMPLOYEES (self-referential)             │
└───────────┬────────────────────────┬────────────────────┘
            │                        │
     ┌──────┴──────┐          ┌──────┴──────┐
     │ 1:N         │          │ 1:N         │
     ▼             ▼          ▼             ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│LEAVE_REQUESTS│ │PERF_REVIEWS  │ │    GOALS     │ │NOTIFICATIONS │
├──────────────┤ ├──────────────┤ ├──────────────┤ ├──────────────┤
│PK id         │ │PK id         │ │PK id         │ │PK id         │
│FK employee_id│ │FK employee_id│ │FK employee_id│ │FK employee_id│
│  start_date  │ │  key_deliver │ │  description │ │  message     │
│  end_date    │ │  accomplish  │ │  deadline    │ │  read_flag   │
│  leave_type  │ │  areas_improv│ │  priority    │ │  created_at  │
│  reason      │ │  self_rating │ │  status      │ │              │
│  status      │ │  mgr_feedback│ │  mgr_comment │ │              │
│  mgr_comment │ │  mgr_rating  │ │              │ │              │
└──────────────┘ └──────────────┘ └──────────────┘ └──────────────┘

┌─────────────────┐          ┌─────────────────────┐
│   DESIGNATIONS  │          │      HOLIDAYS       │
├─────────────────┤          ├─────────────────────┤
│ PK desig_id     │          │ PK id               │
│    desig_name   │          │    date             │
└─────────────────┘          │    description      │
                             └─────────────────────┘
┌─────────────────────┐
│   ANNOUNCEMENTS     │
├─────────────────────┤
│ PK id               │
│    title            │
│    content          │
│    created_at       │
└─────────────────────┘
```

#### Security Implementation

| Security Layer | Implementation | Details |
|----------------|---------------|---------|
| **Authentication** | JWT + Spring Security | Stateless, 24h expiry |
| **Password Storage** | BCrypt | Salt rounds: 10 |
| **Authorization** | @PreAuthorize | Role-based endpoint protection |
| **Input Validation** | Bean Validation | @NotNull, @Size, @Email |
| **SQL Injection** | JPA/Hibernate | Parameterized queries |
| **XSS Protection** | Angular sanitization | Built-in protection |
| **CORS** | Configured origins | localhost:4200, AWS domain |

#### Security Flow

```
Request → CORS Filter → Request Logging Filter → JWT Filter
    → Authentication → @PreAuthorize → Controller → Response
```

---

### SLIDE 12: UX Design (10 Points) (1-2 minutes)

#### UI/UX Highlights

| Aspect | Implementation |
|--------|---------------|
| **Responsive Design** | Bootstrap 5, Mobile-first approach |
| **Navigation** | Role-based sidebar menu, breadcrumbs |
| **Forms** | Inline validation, clear error messages |
| **Feedback** | Loading spinners, success/error toasts |
| **Data Tables** | Pagination, sorting, filtering |
| **Dashboards** | Role-specific widgets and summaries |

#### Key Screens

```
┌─────────────────────────────────────────────────────────────────┐
│  [Login Page]                 [Employee Dashboard]              │
│                                                                 │
│  • Clean login form           • Leave balance widgets           │
│  • Email/password validation  • Quick apply leave action        │
│  • Error messages displayed   • Recent notifications            │
│  • Role-based redirect        • Upcoming holidays               │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Leave Management]           [Admin Panel]                     │
│                                                                 │
│  • Leave request form         • Department management           │
│  • Balance summary display    • Designation management          │
│  • Request history table      • Holiday calendar editor         │
│  • Status indicators          • User activation controls        │
└─────────────────────────────────────────────────────────────────┘
```

#### User Flow Optimization

- Role-based dashboard on login
- Minimal clicks to complete common tasks
- Clear visual hierarchy with Bootstrap components
- Confirmation dialogs for destructive actions
- Helpful error messages with recovery suggestions

---

## TESTING & DOCUMENTATION SECTION (25 Points)

---

### SLIDE 13: Testing (15 Points) (2 minutes)

#### Test Coverage Summary

| Test Type | Framework | Coverage | Status |
|-----------|-----------|----------|--------|
| Unit Tests (Backend) | JUnit 4 + Mockito | Service + Controller layers | ✓ |
| Unit Tests (Frontend) | Jasmine + Karma | Components | [INFO NOT AVAILABLE] |
| Integration Tests | MockMvc (Standalone) | API endpoints | ✓ |
| Manual Testing | Functional Scenarios | 13 test scenarios | ✓ |

#### Service Unit Test Classes

| Test Class | What It Tests |
|------------|---------------|
| AuthServiceTest | Login validation, JWT generation |
| UserServiceTest | Profile management, user operations |
| LeaveServiceTest | Leave application, approval workflows |
| NotificationServiceTest | Notification delivery, read status |
| PerformanceServiceTest | Review creation, feedback submission |
| GoalServiceTest | Goal CRUD, status transitions |

#### Controller Unit Test Classes

| Test Class | What It Tests |
|------------|---------------|
| AuthControllerTest | Login endpoint, authentication |
| UserControllerTest | User CRUD endpoints |
| LeaveControllerTest | Leave management endpoints |
| PerformanceControllerTest | Performance review endpoints |
| GoalControllerTest | Goal management endpoints |
| AdminControllerTest | Admin operations endpoints |

#### Sample Test Case

```java
@Test
void applyLeave_WithValidData_ReturnsCreated() {
    // Arrange
    LeaveRequest request = new LeaveRequest(
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(3),
        "CASUAL",
        "Personal work"
    );
    when(leaveRepository.save(any())).thenReturn(mockLeaveRequest);

    // Act
    LeaveResponse response = leaveService.applyLeave(employeeId, request);

    // Assert
    assertNotNull(response);
    assertEquals("PENDING", response.getStatus());
    verify(notificationService, times(1)).notifyManager(any());
}
```

#### Test Execution

```bash
# Run all tests
mvn test

# Test Results:
Tests run: [X], Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

### SLIDE 14: Logging (5 Points) (1 minute)

#### Logging Implementation

| Log Level | Usage | Example |
|-----------|-------|---------|
| INFO | Normal operations | User login, leave applied |
| DEBUG | Development details | Request/response payloads |
| WARN | Potential issues | Invalid input attempts |
| ERROR | Failures | Exceptions, failed operations |

#### Three-Layer Logging Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│  1. REQUEST LOGGING FILTER                                       │
│     • Captures HTTP inbound/outbound traffic                    │
│     • Generates unique requestId (correlation ID)               │
│     • Logs: method, URI, status, duration                       │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  2. AOP METHOD LOGGING                                          │
│     • Tracks entry/exit of methods                              │
│     • Logs execution duration                                   │
│     • Applied to Controllers, Services, Repositories            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  3. LOG4J2 OUTPUT                                               │
│     • Console appender (development)                            │
│     • Rolling file appender (production)                        │
│     • Pattern: timestamp, thread, level, logger, message        │
└─────────────────────────────────────────────────────────────────┘
```

#### Log Configuration (application.properties)
```properties
logging.level.root=INFO
logging.level.com.revworkforce=DEBUG
logging.level.org.springframework.security=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/application.log
```

#### Log Demo Points

- Show application startup logs
- Demonstrate request logging with correlation ID
- Show method entry/exit logging
- Show exception logging via GlobalExceptionHandler

---

### SLIDE 15: Deliverables (5 Points) (1 minute)

#### Project Deliverables Checklist

| Deliverable | Location | Status |
|-------------|----------|--------|
| **Source Code** | GitHub Repository | ✓ |
| **README.md** | Root directory | ✓ |
| **ERD Documentation** | /ERD.md | ✓ |
| **Architecture Diagram** | /ARCHITECTURE.md | ✓ |
| **Testing Documentation** | /TESTING.md | ✓ |
| **Setup Instructions** | README.md | ✓ |
| **CI/CD Pipeline** | [INFO NOT AVAILABLE] | [INFO NOT AVAILABLE] |

#### Repository Structure

```
RevWorkForce-p2/
├── README.md                 # Project overview, setup instructions
├── ARCHITECTURE.md           # System architecture documentation
├── ERD.md                    # Entity relationship diagram
├── TESTING.md                # Testing strategy and coverage
├── backend/
│   ├── pom.xml               # Maven dependencies
│   ├── src/main/java/        # Java source code
│   ├── src/main/resources/   # application.properties
│   └── src/test/java/        # Test classes
└── frontend/
    ├── package.json          # npm dependencies
    ├── angular.json          # Angular configuration
    └── src/app/              # Angular components
```

---

### SLIDE 16: Deployment Architecture (2 minutes)

#### Phase 1: AWS Deployment

```
┌──────────────────────────────────────────────────────────────┐
│                        GITHUB                                 │
│  ┌──────────┐     ┌────────────────────────────────────┐     │
│  │  Push to │────►│         GitHub Actions              │     │
│  │   main   │     │  Build → Test → Deploy to AWS       │     │
│  └──────────┘     └─────────────────┬──────────────────┘     │
└─────────────────────────────────────┼────────────────────────┘
                                      │
                                      ▼
┌──────────────────────────────────────────────────────────────┐
│                         AWS CLOUD                             │
│  ┌────────────────────────────────────────────────────────┐  │
│  │                    VPC                                  │  │
│  │  ┌──────────────────┐      ┌────────────────────────┐  │  │
│  │  │    EC2 Instance  │      │    RDS (MySQL)         │  │  │
│  │  │  ┌────────────┐  │      │                        │  │  │
│  │  │  │Spring Boot │  │◄────►│  revworkforcep2        │  │  │
│  │  │  │    :8080   │  │ JDBC │  (Private Subnet)      │  │  │
│  │  │  └────────────┘  │      │                        │  │  │
│  │  │  ┌────────────┐  │      └────────────────────────┘  │  │
│  │  │  │   nginx    │  │                                   │  │
│  │  │  │  (Angular) │  │                                   │  │
│  │  │  │    :80     │  │                                   │  │
│  │  │  └────────────┘  │                                   │  │
│  │  └──────────────────┘                                   │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

#### Phase 2: Local Microservices

```
┌──────────────────────────────────────────────────────────────┐
│                   LOCAL DEVELOPMENT                           │
│                                                               │
│  Angular (:4200) ──► API Gateway (:8080) ──┬──► Auth (:8081) │
│                                            ├──► Leave (:8082)│
│                                            ├──► Perf (:8083) │
│                                            └──► Employee(:8084)│
│                                                    │          │
│                          Eureka Server (:8761) ◄──┘          │
│                                    │                          │
│                             MySQL (:3306)                     │
└──────────────────────────────────────────────────────────────┘
```

---

### SLIDE 17: Development Methodology (1 minute)

#### Git Workflow

```
main ─────────────────────────────────────────────►
  │                                    ▲
  └──► develop ────────────────────────┤
         │        ▲        ▲           │
         │        │        │           │
         └──► feature/leave-management─┘
         └──► feature/performance──────┘
         └──► feature/authentication───┘
         └──► feature/employee-directory
         └──► feature/admin-panel──────┘
```

#### Development Workflow

| Phase | Activity | Tools |
|-------|----------|-------|
| Planning | User stories, task breakdown | GitHub Issues |
| Development | Feature branches, commits | Git, IDE |
| Review | Pull requests, code review | GitHub PRs |
| Testing | Automated tests, manual testing | JUnit, Mockito |
| Deployment | CI/CD pipeline | GitHub Actions, AWS |

#### Local Environment Setup

```bash
# Prerequisites: Java 17, Maven, Node 18+, MySQL 8

# Database Setup
mysql -u root -p
CREATE DATABASE revworkforcep2;

# Backend (Port 8080)
cd backend && mvn spring-boot:run

# Frontend (Port 4200)
cd frontend && npm install && npm start
```

#### Default Test Credentials

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@revworkforce.com | Admin@123 |
| Manager | manager@revworkforce.com | Manager@123 |
| Employee | employee@revworkforce.com | Employee@123 |

---

## INDIVIDUAL CONTRIBUTIONS SECTION (Each member: 1-2 minutes)

---

### SLIDE 18: Krishna Babu - Contributions

**Feature Ownership:** Admin Panel, Login, Testing, Notification System

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Admin Controller, Auth Service, Notification Service | AdminController.java, AuthService.java, NotificationService.java |
| Backend | Test Classes | AuthServiceTest.java, NotificationServiceTest.java, AdminControllerTest.java |
| Frontend | Admin Panel, Login Component | admin/, auth/ modules |
| Database | Roles, Departments, Designations, Holidays, Announcements | Admin-managed tables |

##### Code Highlight

```java
// JWT Token Generation with Role Claims
public String generateToken(Employee employee) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", employee.getRole().getRoleName());
    claims.put("employeeId", employee.getEmployeeId());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(employee.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [TECHNICAL_SKILL_GAINED]
- [PROBLEM_SOLVING_APPROACH]
- [COLLABORATION_INSIGHT]

---

### SLIDE 19: Saideep - Contributions

**Feature Ownership:** Performance Reviews, Goals & Feedback System

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Performance Controller, Goal Controller, Services | PerformanceController.java, GoalController.java |
| Backend | Performance & Goal Services | PerformanceService.java, GoalService.java |
| Backend | Test Classes | PerformanceServiceTest.java, GoalServiceTest.java, PerformanceControllerTest.java, GoalControllerTest.java |
| Frontend | Performance Review & Goals Components | performance/, goals/ modules |
| Database | PERFORMANCE_REVIEWS, GOALS tables | Entity design and relationships |

##### Code Highlight

```java
// Manager Feedback Submission with Role Verification
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public PerformanceReviewResponse submitFeedback(Long reviewId, FeedbackRequest request, Long managerId) {
    PerformanceReview review = performanceReviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

    // Verify manager has authority over this employee
    if (!isManagerOf(managerId, review.getEmployee().getEmployeeId())) {
        throw new UnauthorizedException("Not authorized to review this employee");
    }

    review.setManagerFeedback(request.getFeedback());
    review.setManagerRating(request.getRating());

    // Notify employee of feedback
    notificationService.notifyEmployee(review.getEmployee(), "Performance feedback received");

    return mapToResponse(performanceReviewRepository.save(review));
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 20: Ganesh - Contributions

**Feature Ownership:** Authentication & Authorization

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | JWT Authentication Filter | JwtAuthenticationFilter.java |
| Backend | Security Configuration | SecurityConfig.java, CorsConfig.java |
| Backend | JWT Utility Class | JwtUtil.java |
| Frontend | Auth Guards, HTTP Interceptors | AuthGuard.ts, JwtInterceptor.ts |
| Security | Role-based Access Control | @PreAuthorize annotations across controllers |

##### Code Highlight

```java
// JWT Authentication Filter
@Override
protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain) {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.extractUsername(jwt);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

    filterChain.doFilter(request, response);
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 21: Sai Krishna - Contributions

**Feature Ownership:** Leave Management, Leave Notifications

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Leave Controller, Leave Service | LeaveController.java, LeaveService.java |
| Backend | Leave Repository with Custom Queries | LeaveRequestRepository.java |
| Backend | Test Classes | LeaveServiceTest.java, LeaveControllerTest.java |
| Frontend | Leave Application, Leave History, Balance Display | leave/ module components |
| Database | LEAVE_REQUESTS table, Leave Balance Logic | Entity and business rules |

##### Code Highlight

```java
// Leave Application with Balance Validation
public LeaveResponse applyLeave(Long employeeId, LeaveApplicationRequest request) {
    Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

    // Validate leave balance
    int remainingBalance = calculateRemainingBalance(employeeId, request.getLeaveType());
    int requestedDays = calculateDays(request.getStartDate(), request.getEndDate());

    if (remainingBalance < requestedDays) {
        throw new InsufficientBalanceException("Insufficient " + request.getLeaveType() + " balance");
    }

    LeaveRequest leaveRequest = LeaveRequest.builder()
        .employee(employee)
        .startDate(request.getStartDate())
        .endDate(request.getEndDate())
        .leaveType(request.getLeaveType())
        .reason(request.getReason())
        .status("PENDING")
        .build();

    // Notify manager
    notificationService.notifyManager(employee.getManager(),
        "New leave request from " + employee.getName());

    return mapToResponse(leaveRequestRepository.save(leaveRequest));
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 22: Vamsi - Contributions

**Feature Ownership:** Employee Management, Employee Directory

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | User Controller, User Service | UserController.java, UserService.java |
| Backend | Employee Repository with Search | EmployeeRepository.java |
| Backend | Test Classes | UserServiceTest.java, UserControllerTest.java |
| Frontend | Employee Directory, Profile Management | directory/, profile/ components |
| Database | EMPLOYEES table design | Core entity with self-referential manager relationship |

##### Code Highlight

```java
// Employee Directory Search with Multiple Criteria
public Page<EmployeeDTO> searchEmployees(String searchTerm, Long departmentId,
        Long designationId, Pageable pageable) {

    Specification<Employee> spec = Specification.where(null);

    if (searchTerm != null && !searchTerm.isEmpty()) {
        spec = spec.and((root, query, cb) ->
            cb.or(
                cb.like(cb.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("email")), "%" + searchTerm.toLowerCase() + "%")
            ));
    }

    if (departmentId != null) {
        spec = spec.and((root, query, cb) ->
            cb.equal(root.get("department").get("deptId"), departmentId));
    }

    if (designationId != null) {
        spec = spec.and((root, query, cb) ->
            cb.equal(root.get("designation").get("desigId"), designationId));
    }

    return employeeRepository.findAll(spec, pageable)
        .map(this::mapToDTO);
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 23: Team Challenges & Collective Learnings (1 minute)

#### Team-Level Challenges

| Challenge | Impact | How We Solved It Together |
|-----------|--------|---------------------------|
| Coordinating parallel feature development | Merge conflicts, integration issues | Clear feature branch naming, daily standups |
| Self-referential employee-manager relationship | Complex queries, circular reference handling | Careful JPA mapping, DTO pattern for responses |
| Role-based access across all features | Inconsistent security | Centralized @PreAuthorize annotations, shared security config |
| Notification integration across features | Duplicate code, inconsistent delivery | Centralized NotificationService used by all features |
| [TEAM_CHALLENGE_1] | [IMPACT] | [SOLUTION] |

#### Collective Learnings

| Area | What We Learned as a Team |
|------|---------------------------|
| **Technical** | Full-stack development with Angular + Spring Boot, JWT authentication, JPA relationships |
| **Collaboration** | Git workflow, code reviews, API contract communication |
| **Problem Solving** | Debugging authentication issues, resolving merge conflicts |
| **Professional** | Meeting deadlines, documentation importance, testing discipline |

---

### SLIDE 24: Q&A (5-10 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                       QUESTIONS?                            │
│                                                             │
│                                                             │
│     GitHub Phase 1:                                         │
│     https://github.com/Krishnababuchintpalli/RevWorkForce-p2│
│                                                             │
│     GitHub Phase 2:                                         │
│     https://github.com/Krishnababuchintpalli/RevWorkForce-p3│
│                                                             │
│     Live Demo: [AWS_URL]                                    │
│                                                             │
│     Individual GitHub Contributions:                        │
│     • Krishna Babu: [X commits, Y PRs]                      │
│     • Saideep: [X commits, Y PRs]                           │
│     • Ganesh: [X commits, Y PRs]                            │
│     • Sai Krishna: [X commits, Y PRs]                       │
│     • Vamsi: [X commits, Y PRs]                             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Q&A Tips:**
- Any team member can answer questions about their feature area
- Be honest about limitations and future improvements
- Reference specific code/commits when explaining technical decisions

---

## NOTES FOR PRESENTERS

### Information Not Available (To Be Filled)

The following information could not be determined from the provided repositories and should be filled in by the team:

1. **Batch ID and Presentation Date** - Slide 1
2. **Individual Challenges & Solutions** - Slides 18-22 (each team member should fill their specific challenges)
3. **Individual Key Learnings** - Slides 18-22
4. **Exact Git Commit Counts** - Slide 24
5. **AWS Deployment URL** - Slide 24
6. **CI/CD Pipeline Status** - Slide 15
7. **Frontend Test Coverage Details** - Slide 13
8. **Phase 2 Microservices Implementation Details** - The p3 repository appears to be in early stages

### Demo Preparation Checklist

- [ ] Database seeded with test data
- [ ] All three test accounts working (Admin, Manager, Employee)
- [ ] Backend running on port 8080
- [ ] Frontend running on port 4200
- [ ] AWS deployment active (if applicable)
- [ ] Backup screenshots/video prepared
- [ ] Log files accessible for demo

### Presentation Tips

1. Each member should practice their 1-2 minute contribution section
2. Prepare to explain any code snippet shown
3. Have terminal ready to show logs during demo
4. Be ready to answer questions about design decisions
5. Know your commit count and key PRs

---

## APPENDIX: Default Test Scenarios

Based on the TESTING.md documentation, the following 13 functional test scenarios should work:

1. Login as Employee
2. Login as Manager
3. Login as Admin
4. Password reset with employee ID matching
5. Apply leave request
6. Cancel leave request
7. Manager approve/reject leave
8. Leave balance validation (block when zero)
9. Notification delivery and read toggle
10. Manager review direct reports' performance
11. Admin review any employee's performance
12. Goal creation and status updates
13. Admin CRUD for departments, designations, holidays, announcements
