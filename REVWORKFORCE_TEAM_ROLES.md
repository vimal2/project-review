# RevWorkForce Team Roles and Responsibilities

This document outlines the comprehensive roles and responsibilities for each team member across both the monolithic (P2) and microservices (P3) projects.

---

## Team Overview

| Developer | Primary Domain | P2 Components | P3 Service |
|-----------|---------------|---------------|------------|
| **Ganesh** | Authentication & Authorization | JWT, Security Config, Auth Filter | auth-service (Port 8081) |
| **Vamsi** | Employee & Directory | User/Employee Management, Search | employee-service (Port 8082) |
| **Sai Krishna** | Leave & Leave-Notifications | Leave CRUD, Approval Workflow | leave-service (Port 8083) |
| **Saideep Reddy** | Performance, Goals & Feedback | Reviews, Goals, Feedback | performance-service (Port 8084) |
| **Krishna Babu** | Admin, Login, Testing, Notification | Master Data, Announcements | admin-service (Port 8085) |

---

## Ganesh - Authentication & Authorization

### P2 Monolithic Responsibilities

#### Backend Components
- **Security Package**:
  - `JwtUtil.java` - JWT token generation, validation, claims extraction
  - `JwtAuthenticationFilter.java` - OncePerRequestFilter for token validation
  - `AppUserDetailsService.java` - Custom UserDetailsService implementation

- **Configuration**:
  - `SecurityConfig.java` - Spring Security filter chain, CORS, BCrypt encoder
  - Method-level security with `@PreAuthorize`

- **Enums**:
  - `Role.java` - EMPLOYEE, MANAGER, ADMIN

- **Key Features**:
  - HMAC SHA256 token signing
  - 24-hour token expiration
  - Stateless session management
  - Role-based access control (RBAC)

### P3 Microservices Responsibilities

#### auth-service (Port 8081)
- **Entities**: User, RefreshToken, RoleMaster
- **DTOs**:
  - LoginRequest, AuthResponse
  - RegisterRequest, PasswordResetRequest
  - TokenRefreshRequest, TokenRefreshResponse
  - UserValidationResponse (for internal validation)
- **Repository**: UserRepository, RefreshTokenRepository, RoleMasterRepository
- **Service**: AuthService, JwtService, RefreshTokenService
- **Controller**: AuthController
  - POST `/api/auth/login` - Authenticate and get JWT
  - POST `/api/auth/register` - Register new user (admin only)
  - POST `/api/auth/refresh` - Refresh JWT token
  - POST `/api/auth/logout` - Logout (invalidate refresh token)
  - POST `/api/auth/reset-password` - Reset password
  - GET `/api/auth/validate` - Validate token (internal endpoint)
  - GET `/api/auth/user/{userId}` - Get user by ID (internal)

#### Key Features to Implement
1. JWT token generation with claims (userId, email, role)
2. Refresh token mechanism for token renewal
3. Password hashing with BCrypt
4. Token validation endpoint for other services
5. User validation endpoint for cross-service calls
6. Role management (EMPLOYEE, MANAGER, ADMIN)

---

## Vamsi - Employee & Directory

### P2 Monolithic Responsibilities

#### Backend Components
- **Controller**: `UserController.java`
  - GET `/api/users/me` - Get current user profile
  - PUT `/api/users/me` - Update profile
  - GET `/api/users/search` - Search users
  - GET `/api/users/team` - Get manager's team
  - GET `/api/users` - Get all users (admin)
  - POST `/api/users` - Create user (admin)
  - PATCH `/api/users/{id}/active` - Activate/deactivate user
  - PATCH `/api/users/{id}/manager/{managerId}` - Assign manager

- **Service Layer**:
  - `UserService.java` - Employee CRUD operations
  - `CurrentUserService.java` - Get current authenticated user

- **Entity**:
  - `User.java` - Main employee entity with all fields

- **DTOs**:
  - `AdminCreateUserRequest.java` - User creation with validation

### P3 Microservices Responsibilities

#### employee-service (Port 8082)
- **Entities**: Employee, EmployeeProfile
- **DTOs**:
  - EmployeeCreateRequest, EmployeeUpdateRequest
  - EmployeeResponse, EmployeeProfileResponse
  - EmployeeSearchResponse, TeamMemberResponse
  - EmployeeSummaryResponse (for other services)
- **Repository**: EmployeeRepository
- **Service**: EmployeeService, DirectoryService
- **Feign Clients**:
  - AuthServiceClient (to validate tokens and get user info)
- **Controller**: EmployeeController, DirectoryController
  - GET `/api/employees/profile` - Get current employee profile
  - PUT `/api/employees/profile` - Update profile
  - GET `/api/employees/search` - Search employees
  - GET `/api/employees/team` - Get manager's team
  - GET `/api/employees` - Get all employees (admin)
  - POST `/api/employees` - Create employee (admin)
  - PATCH `/api/employees/{id}/status` - Activate/deactivate
  - PATCH `/api/employees/{id}/manager/{managerId}` - Assign manager
  - GET `/api/employees/{id}` - Get employee by ID
  - GET `/api/internal/employees/{id}` - Internal endpoint for other services
  - GET `/api/internal/employees/batch` - Get multiple employees

#### Key Features to Implement
1. Employee profile CRUD (linked to auth-service userId)
2. Employee directory with search
3. Team member management (manager-reportee relationship)
4. Employee activation/deactivation
5. Manager assignment
6. Internal endpoints for cross-service communication

---

## Sai Krishna - Leave & Leave-Notifications

### P2 Monolithic Responsibilities

#### Backend Components
- **Controller**: `LeaveController.java`
  - POST `/api/leaves` - Apply for leave
  - GET `/api/leaves/my` - Get user's leaves
  - GET `/api/leaves/summary` - Get leave balance
  - GET `/api/leaves/team` - Get team's leaves
  - PATCH `/api/leaves/{id}/cancel` - Cancel leave
  - PATCH `/api/leaves/{id}/approve` - Approve leave
  - PATCH `/api/leaves/{id}/reject` - Reject leave

- **Service Layer**:
  - `LeaveService.java` - Leave business logic
  - Integration with NotificationService

- **Entity**:
  - `LeaveRequest.java` - Leave request with dates, type, status

- **Enums**:
  - `LeaveType.java` - SICK, PERSONAL, VACATION, etc.
  - `LeaveStatus.java` - PENDING, APPROVED, REJECTED, CANCELED

- **DTOs**:
  - `LeaveApplyRequest.java`, `LeaveDecisionRequest.java`
  - `LeaveSummaryResponse.java`

### P3 Microservices Responsibilities

#### leave-service (Port 8083)
- **Entities**: LeaveRequest, LeaveBalance, LeaveNotification
- **DTOs**:
  - LeaveApplyRequest, LeaveDecisionRequest
  - LeaveResponse, LeaveSummaryResponse
  - TeamLeaveResponse, LeaveNotificationResponse
- **Repository**: LeaveRequestRepository, LeaveBalanceRepository, LeaveNotificationRepository
- **Service**: LeaveService, LeaveNotificationService, LeaveBalanceService
- **Feign Clients**:
  - EmployeeServiceClient (to get employee and manager info)
- **Controller**: LeaveController, LeaveNotificationController
  - POST `/api/leaves` - Apply for leave
  - GET `/api/leaves/my` - Get user's leaves
  - GET `/api/leaves/summary` - Get leave balance
  - GET `/api/leaves/team` - Get team's leaves
  - PATCH `/api/leaves/{id}/cancel` - Cancel leave
  - PATCH `/api/leaves/{id}/approve` - Approve leave
  - PATCH `/api/leaves/{id}/reject` - Reject leave
  - GET `/api/leaves/notifications` - Get leave notifications
  - PATCH `/api/leaves/notifications/{id}/read` - Mark notification read

#### Key Features to Implement
1. Leave application with validation
2. Leave balance calculation (24 days default)
3. Leave approval workflow (manager/admin only)
4. Leave cancellation
5. Leave notifications (embedded in service)
6. Team leave view for managers
7. Leave type management

---

## Saideep Reddy - Performance, Goals & Feedback

### P2 Monolithic Responsibilities

#### Backend Components
- **Controllers**:
  - `PerformanceController.java`
    - POST `/api/performance` - Create review
    - GET `/api/performance/my` - Get user's reviews
    - GET `/api/performance/team` - Get team reviews
    - PATCH `/api/performance/{id}/feedback` - Provide feedback
  - `GoalController.java`
    - POST `/api/goals` - Create goal
    - GET `/api/goals/my` - Get user's goals
    - GET `/api/goals/team` - Get team goals
    - PATCH `/api/goals/{id}/status` - Update goal status

- **Service Layer**:
  - `PerformanceService.java` - Performance review logic
  - `GoalService.java` - Goal management

- **Entities**:
  - `PerformanceReview.java` - Review with ratings, feedback
  - `Goal.java` - Goal with priority, status, deadline

- **Enums**:
  - `ReviewStatus.java` - DRAFT, REVIEWED
  - `GoalStatus.java` - NOT_STARTED, IN_PROGRESS, COMPLETED
  - `GoalPriority.java` - HIGH, MEDIUM, LOW

- **DTOs**:
  - `ManagerPerformanceReviewRequest.java`
  - `ReviewFeedbackRequest.java`
  - `GoalRequest.java`

### P3 Microservices Responsibilities

#### performance-service (Port 8084)
- **Entities**: PerformanceReview, Goal, PerformanceNotification
- **DTOs**:
  - PerformanceReviewRequest, PerformanceReviewResponse
  - ReviewFeedbackRequest, TeamReviewResponse
  - GoalRequest, GoalResponse, GoalStatusUpdateRequest
  - TeamGoalResponse
- **Repository**: PerformanceReviewRepository, GoalRepository, PerformanceNotificationRepository
- **Service**: PerformanceService, GoalService, PerformanceNotificationService
- **Feign Clients**:
  - EmployeeServiceClient (to get employee info)
- **Controller**: PerformanceController, GoalController
  - POST `/api/performance` - Create performance review
  - GET `/api/performance/my` - Get user's reviews
  - GET `/api/performance/team` - Get team reviews
  - PATCH `/api/performance/{id}/feedback` - Provide feedback
  - POST `/api/goals` - Create goal
  - GET `/api/goals/my` - Get user's goals
  - GET `/api/goals/team` - Get team goals
  - PATCH `/api/goals/{id}/status` - Update goal status
  - PATCH `/api/goals/{id}/comment` - Add manager comment

#### Key Features to Implement
1. Performance review creation (manager/admin only)
2. Performance review workflow
3. Manager feedback with ratings
4. Goal CRUD operations
5. Goal status tracking
6. Goal priority management
7. Team views for managers
8. Embedded notifications for reviews and goals

---

## Krishna Babu - Admin, Login, Testing, Notification

### P2 Monolithic Responsibilities

#### Backend Components
- **Controllers**:
  - `AuthController.java` - Login and password reset endpoints
  - `AdminController.java`
    - CRUD for Departments
    - CRUD for Designations
    - CRUD for Holidays
    - CRUD for Announcements

- **Service Layer**:
  - `AuthService.java` - Login logic, password validation
  - `AdminService.java` - Master data CRUD
  - `NotificationService.java` - Notification operations

- **Entities**:
  - `Department.java`, `Designation.java`
  - `Holiday.java`, `Announcement.java`
  - `Notification.java`

- **DTOs**:
  - `LoginRequest.java`, `AuthResponse.java`
  - `NotificationSendRequest.java`

- **Testing**:
  - AuthControllerTest, AdminControllerTest
  - NotificationServiceTest, UserControllerTest

### P3 Microservices Responsibilities

#### admin-service (Port 8085)
- **Entities**: Department, Designation, Holiday, Announcement, AdminNotification
- **DTOs**:
  - DepartmentRequest, DepartmentResponse
  - DesignationRequest, DesignationResponse
  - HolidayRequest, HolidayResponse
  - AnnouncementRequest, AnnouncementResponse
  - AdminNotificationRequest, AdminNotificationResponse
- **Repository**: DepartmentRepository, DesignationRepository, HolidayRepository, AnnouncementRepository, AdminNotificationRepository
- **Service**: DepartmentService, DesignationService, HolidayService, AnnouncementService, AdminNotificationService
- **Controller**: AdminController, MasterDataController
  - GET/POST/DELETE `/api/admin/departments` - Department CRUD
  - GET/POST/DELETE `/api/admin/designations` - Designation CRUD
  - GET/POST/DELETE `/api/admin/holidays` - Holiday CRUD
  - GET/POST/PUT/DELETE `/api/admin/announcements` - Announcement CRUD
  - GET `/api/admin/notifications` - Get admin notifications
  - POST `/api/admin/notifications/send` - Send notification
  - PATCH `/api/admin/notifications/{id}/read` - Mark as read
  - GET `/api/internal/departments` - Internal endpoint
  - GET `/api/internal/designations` - Internal endpoint
  - GET `/api/internal/holidays` - Internal endpoint

#### Key Features to Implement
1. Department CRUD operations
2. Designation CRUD operations
3. Holiday management
4. Announcement management
5. Admin notification system
6. Internal endpoints for other services to fetch master data
7. Proper admin authorization

---

## Service Dependencies

```
                    ┌─────────────────┐
                    │   API Gateway   │
                    │    (8080)       │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│auth-service │     │employee-svc │     │admin-service│
│   (8081)    │     │   (8082)    │     │   (8085)    │
│  [Ganesh]   │     │   [Vamsi]   │     │[Krishna Babu]│
└─────────────┘     └─────────────┘     └─────────────┘
         │                   │                   │
         │                   │                   │
         │    ┌──────────────┴──────────────┐    │
         │    │                             │    │
         ▼    ▼                             ▼    ▼
┌─────────────────┐                 ┌─────────────────┐
│  leave-service  │                 │performance-svc  │
│    (8083)       │                 │    (8084)       │
│  [Sai Krishna]  │                 │ [Saideep Reddy] │
└─────────────────┘                 └─────────────────┘
```

### Dependency Matrix

| Service | Depends On |
|---------|------------|
| auth-service | None (independent) |
| employee-service | auth-service (user validation) |
| admin-service | auth-service (user validation) |
| leave-service | auth-service, employee-service |
| performance-service | auth-service, employee-service |

---

## Technology Stack (P3)

- **Framework**: Spring Boot 3.2
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Inter-service Communication**: OpenFeign
- **Resilience**: Resilience4j (Circuit Breaker)
- **Security**: JWT Authentication
- **Database**: H2 (per service, in-memory)
- **Build Tool**: Maven
- **Java Version**: 17

---

## Port Assignments

| Service | Port |
|---------|------|
| Config Server | 8888 |
| Discovery Server (Eureka) | 8761 |
| API Gateway | 8080 |
| auth-service | 8081 |
| employee-service | 8082 |
| leave-service | 8083 |
| performance-service | 8084 |
| admin-service | 8085 |
