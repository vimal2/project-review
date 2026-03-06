# Project Presentation Deck Template

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 4-6 members
**Template Version:** 1.0

---

## How to Use This Template

1. Replace all `[PLACEHOLDER]` values with project-specific content
2. Delete sections marked `(if applicable)` if not relevant
3. Adjust slide numbers based on team size (4-6 members)
4. Each team member should fill in their individual contribution slide

### Placeholder Legend

| Placeholder | Description | Example |
|-------------|-------------|---------|
| `[PROJECT_NAME]` | Your project name | RevShop, Password Manager |
| `[USE_CASE_1]` | Primary feature/use case | Vault Management, Order Processing |
| `[USE_CASE_2]` | Secondary feature/use case | Password Generator, Cart Management |
| `[ENTITY_1]` | Main database entity | VaultEntry, Product, Order |
| `[ENTITY_2]` | Secondary entity | Category, CartItem |
| `[MEMBER_X]` | Team member name | John, Sarah |
| `[PORT_X]` | Service port number | 8081, 8082 |

---

## Evaluation Criteria (100 Points Total)

| Category | Criteria | Points | Presentation Focus |
|----------|----------|--------|-------------------|
| **Core Features** | Main Use Case 1 | 20 | Primary demo + architecture |
| | Use Case 2 | 14 | Secondary demo + implementation |
| | Common Features | 6 | Auth, CRUD, validations |
| **Technical Standards** | Code Organization | 10 | Layered architecture, patterns |
| | DB & Security | 15 | ERD, encryption, JWT, validations |
| | UX | 10 | UI/UX design, responsiveness |
| **Testing & Documentation** | Testing | 15 | Unit tests, integration tests |
| | Logging | 5 | Application logging, audit trail |
| | Deliverables | 5 | README, docs, deployment |
| **Total** | | **100** | |

---

## Time Allocation (~26 slides)

| Section | Time | Slides |
|---------|------|--------|
| Intro, Team, Effort | 3 min | 1-6 |
| Core Features Demo | 6-8 min | 7-11 |
| Technical Standards | 5-6 min | 12-14 |
| Testing & Docs | 3-4 min | 15-17 |
| Deployment | 2 min | 18-19 |
| **Individual Contributions** | **4-6 min** | 20-25 |
| Q&A | 5-10 min | 26 |

**Note:** Individual contribution slides ensure each team member speaks and demonstrates their specific work.

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                    [PROJECT_NAME]                           │
│                                                             │
│     Phase 1: Monolithic Application (AWS Deployment)        │
│     Phase 2: Microservices Architecture (Local)             │
│                                                             │
│     Team Members (Full Stack Developers):                   │
│     • [MEMBER_1] - [FEATURE_1] + Tech Lead                  │
│     • [MEMBER_2] - [FEATURE_2] + DevOps                     │
│     • [MEMBER_3] - [FEATURE_3]                              │
│     • [MEMBER_4] - [FEATURE_4]                              │
│     • [MEMBER_5] - [FEATURE_5] (if 5-6 member team)         │
│     • [MEMBER_6] - [FEATURE_6] (if 6 member team)           │
│                                                             │
│     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** [ONE_LINE_DESCRIPTION]

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | [USE_CASE_1_FEATURE_1] | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-02 | [USE_CASE_1_FEATURE_2] | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-03 | [USE_CASE_2_FEATURE_1] | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-04 | [USE_CASE_2_FEATURE_2] | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-05 | User Authentication (Login/Register) | Common Features (6 pts) | ✓ | ✓ |
| FR-06 | CRUD Operations for [ENTITY_1] | Common Features (6 pts) | ✓ | ✓ |
| FR-07 | Input Validation & Error Handling | Common Features (6 pts) | ✓ | ✓ |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Data Encryption | [ENCRYPTION_TYPE] |
| Security | Authentication | JWT Tokens |
| Performance | API Response | < 500ms |
| Availability | Uptime (P1) | 99% |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL database available (local + AWS RDS) |
| A-02 | AWS Free Tier resources accessible |
| A-03 | Modern browser support (Chrome, Firefox, Edge) |
| A-04 | Team has GitHub repository access |
| A-05 | [PROJECT_SPECIFIC_ASSUMPTION_1] |
| A-06 | [PROJECT_SPECIFIC_ASSUMPTION_2] |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Limited microservices experience | Medium | Pair programming, documentation |
| AWS deployment issues | High | Practice deployments, local fallback |
| Insufficient test coverage | High | TDD approach, code reviews |
| [PROJECT_SPECIFIC_RISK_1] | [IMPACT] | [MITIGATION] |
| [PROJECT_SPECIFIC_RISK_2] | [IMPACT] | [MITIGATION] |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
┌────────────────────────────────────────────────────────────┐
│                    ANGULAR FRONTEND                         │
│              (Components, Services, Guards)                 │
└─────────────────────────┬──────────────────────────────────┘
                          │ HTTP/REST
┌─────────────────────────┴──────────────────────────────────┐
│                  SPRING BOOT MONOLITH                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ Controllers │→ │  Services   │→ │    Repositories     │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Security: JWT Filter → Authentication → Authorization│   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────┬──────────────────────────────────┘
                          │ JDBC
┌─────────────────────────┴──────────────────────────────────┐
│                      MySQL DATABASE                         │
└────────────────────────────────────────────────────────────┘
```

#### Phase 2: Microservices Architecture

```
┌────────────────────────────────────────────────────────────┐
│                    ANGULAR FRONTEND                         │
└─────────────────────────┬──────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────┐
│                API GATEWAY (Port [GATEWAY_PORT])            │
│            JWT Validation + Request Routing                 │
└────────┬────────────────┬────────────────┬─────────────────┘
         │                │                │
    ┌────┴────┐     ┌─────┴─────┐    ┌─────┴─────┐
    │Auth Svc │     │[SERVICE_1]│    │[SERVICE_2]│
    │ [PORT_1]│     │  [PORT_2] │    │  [PORT_3] │
    └────┬────┘     └─────┬─────┘    └─────┬─────┘
         │                │                │
         └────────────────┼────────────────┘
                          │
              ┌───────────┴───────────┐
              │    Eureka Server      │
              │    Port [EUREKA_PORT] │
              └───────────────────────┘
```

---

### SLIDE 5: Project Team & Responsibilities (30 seconds)

#### Team Structure: Full Stack Contributors

| Member | Feature Ownership | Additional Responsibility | Key Deliverables |
|--------|-------------------|---------------------------|------------------|
| [MEMBER_1] | [USE_CASE_1] - Module A | **Tech Lead** (Architecture, Code Reviews) | [DELIVERABLE_1] |
| [MEMBER_2] | [USE_CASE_1] - Module B | **DevOps** (CI/CD, AWS Deployment) | [DELIVERABLE_2] |
| [MEMBER_3] | [USE_CASE_2] - Module A | - | [DELIVERABLE_3] |
| [MEMBER_4] | [USE_CASE_2] - Module B | - | [DELIVERABLE_4] |
| [MEMBER_5] | Common Features (Auth) | - | [DELIVERABLE_5] |
| [MEMBER_6] | Common Features (UI/UX) | - | [DELIVERABLE_6] |

**Note:** All team members contribute as **Full Stack Developers** across frontend (Angular) and backend (Spring Boot). Additional responsibilities are shared among 1-2 members.

#### 4-Member Team Configuration

| Member | Feature Ownership | Additional Responsibility |
|--------|-------------------|---------------------------|
| [MEMBER_1] | [USE_CASE_1] Complete | **Tech Lead** |
| [MEMBER_2] | [USE_CASE_2] Complete | **DevOps** |
| [MEMBER_3] | Authentication & Security | - |
| [MEMBER_4] | Common Features & UI | - |

#### 6-Member Team Configuration

| Member | Feature Ownership | Additional Responsibility |
|--------|-------------------|---------------------------|
| [MEMBER_1] | [USE_CASE_1] - Create/Read | **Tech Lead** |
| [MEMBER_2] | [USE_CASE_1] - Update/Delete | **DevOps** |
| [MEMBER_3] | [USE_CASE_2] - Primary Flow | - |
| [MEMBER_4] | [USE_CASE_2] - Secondary Flow | - |
| [MEMBER_5] | Auth & Session Management | - |
| [MEMBER_6] | Common UI & Error Handling | - |

---

### SLIDE 6: Effort Estimate & Actuals (1 minute)

#### Phase 1: Monolithic Application

| Feature/Module | Owner | Estimated (hrs) | Actual (hrs) | Variance | Notes |
|----------------|-------|-----------------|--------------|----------|-------|
| Project Setup & Config | [MEMBER] | [X] | | | |
| Database Design & Entities | [MEMBER] | [X] | | | |
| [USE_CASE_1] - Backend | [MEMBER] | [X] | | | |
| [USE_CASE_1] - Frontend | [MEMBER] | [X] | | | |
| [USE_CASE_2] - Backend | [MEMBER] | [X] | | | |
| [USE_CASE_2] - Frontend | [MEMBER] | [X] | | | |
| Authentication (Backend) | [MEMBER] | [X] | | | |
| Authentication (Frontend) | [MEMBER] | [X] | | | |
| Common Features | [MEMBER] | [X] | | | |
| Testing | All | [X] | | | |
| CI/CD & AWS Deployment | [MEMBER] | [X] | | | |
| **Total Phase 1** | | **[TOTAL]** | | | |

#### Phase 2: Microservices Migration

| Feature/Module | Owner | Estimated (hrs) | Actual (hrs) | Variance | Notes |
|----------------|-------|-----------------|--------------|----------|-------|
| Eureka Server Setup | [MEMBER] | [X] | | | |
| API Gateway | [MEMBER] | [X] | | | |
| Auth Service Extraction | [MEMBER] | [X] | | | |
| [SERVICE_1] Extraction | [MEMBER] | [X] | | | |
| [SERVICE_2] Extraction | [MEMBER] | [X] | | | |
| Service Communication (Feign) | All | [X] | | | |
| Frontend API Updates | [MEMBER] | [X] | | | |
| Integration Testing | All | [X] | | | |
| **Total Phase 2** | | **[TOTAL]** | | | |

#### Effort Summary by Member

| Member | Phase 1 (hrs) | Phase 2 (hrs) | Total (hrs) | % Contribution |
|--------|---------------|---------------|-------------|----------------|
| [MEMBER_1] | [X] | [X] | [X] | [X]% |
| [MEMBER_2] | [X] | [X] | [X] | [X]% |
| [MEMBER_3] | [X] | [X] | [X] | [X]% |
| [MEMBER_4] | [X] | [X] | [X] | [X]% |
| [MEMBER_5] | [X] | [X] | [X] | [X]% |
| [MEMBER_6] | [X] | [X] | [X] | [X]% |
| **Total** | **[X]** | **[X]** | **[X]** | **100%** |

---

## CORE FEATURES SECTION (40 Points)

---

### SLIDE 7: Main Use Case 1 - Design (20 Points) (2 minutes)

**Use Case:** [USE_CASE_1_NAME]

#### User Story

> As a [USER_TYPE], I want to [ACTION] so that [BENEFIT].

#### Feature Flow

```
┌─────────┐     ┌─────────┐     ┌─────────┐     ┌─────────┐
│  User   │────►│ [STEP_1]│────►│ [STEP_2]│────►│ [RESULT]│
│  Action │     │         │     │         │     │         │
└─────────┘     └─────────┘     └─────────┘     └─────────┘
```

#### API Endpoints for Use Case 1

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/[RESOURCE_1] | Create [ENTITY_1] |
| GET | /api/[RESOURCE_1] | List all [ENTITY_1]s |
| GET | /api/[RESOURCE_1]/{id} | Get [ENTITY_1] details |
| PUT | /api/[RESOURCE_1]/{id} | Update [ENTITY_1] |
| DELETE | /api/[RESOURCE_1]/{id} | Delete [ENTITY_1] |
| [METHOD] | [ENDPOINT] | [DESCRIPTION] |

#### Implementation Highlights

- [KEY_TECHNICAL_DECISION_1]
- [KEY_TECHNICAL_DECISION_2]
- [SECURITY_CONSIDERATION]

---

### SLIDE 8: Main Use Case 1 - DEMO (3-4 minutes)

**Live Demo Script:**

| Step | Action | Expected Result | Points Demonstrated |
|------|--------|-----------------|---------------------|
| 1 | Navigate to [FEATURE_PAGE] | Page loads correctly | UI/UX |
| 2 | Create new [ENTITY_1] | Form validation works | Validation |
| 3 | Submit form | Success message, data saved | CRUD Create |
| 4 | View created [ENTITY_1] | Details displayed correctly | CRUD Read |
| 5 | Edit [ENTITY_1] | Update successful | CRUD Update |
| 6 | Delete [ENTITY_1] | Confirmation, item removed | CRUD Delete |
| 7 | Show backend logs | Request/response logged | Logging |

**Backup:** [Prepare screenshots/video recording in case of demo failure]

---

### SLIDE 9: Use Case 2 - Design (14 Points) (1-2 minutes)

**Use Case:** [USE_CASE_2_NAME]

#### User Story

> As a [USER_TYPE], I want to [ACTION] so that [BENEFIT].

#### Feature Components

```
┌─────────────────────────────────────────────────────────┐
│                    [USE_CASE_2] FLOW                     │
│                                                          │
│   ┌──────────┐    ┌──────────┐    ┌──────────┐          │
│   │[COMPONENT│───►│ [SERVICE]│───►│   [API]  │          │
│   │    A]    │    │  Logic   │    │ Endpoint │          │
│   └──────────┘    └──────────┘    └──────────┘          │
│                                                          │
│   Key Features:                                          │
│   • [FEATURE_A]                                          │
│   • [FEATURE_B]                                          │
│   • [FEATURE_C]                                          │
└─────────────────────────────────────────────────────────┘
```

#### API Endpoints for Use Case 2

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/[RESOURCE_2] | [ACTION_DESCRIPTION] |
| GET | /api/[RESOURCE_2]/search | Search/filter [ENTITY_2]s |
| [METHOD] | [ENDPOINT] | [DESCRIPTION] |

---

### SLIDE 10: Use Case 2 - DEMO (2-3 minutes)

**Live Demo Script:**

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Access [USE_CASE_2] feature | Feature loads correctly |
| 2 | Perform [PRIMARY_ACTION] | Action completes successfully |
| 3 | Verify result | Expected outcome visible |
| 4 | Show edge case handling | Error handling works |
| 5 | [ADDITIONAL_STEP] | [EXPECTED_RESULT] |

---

### SLIDE 11: Common Features (6 Points) (1 minute)

#### Authentication Flow

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│  Login   │───►│  Validate│───►│ Generate │───►│  Return  │
│  Request │    │  Creds   │    │   JWT    │    │  Token   │
└──────────┘    └──────────┘    └──────────┘    └──────────┘
```

#### Common Features Implemented

| Feature | Implementation | Status |
|---------|---------------|--------|
| User Registration | Spring Security + BCrypt | ✓ |
| User Login | JWT Authentication | ✓ |
| Session Management | Token-based ([EXPIRY_TIME] expiry) | ✓ |
| Input Validation | Bean Validation (@Valid) | ✓ |
| Error Handling | GlobalExceptionHandler | ✓ |
| CORS Configuration | Allowed origins configured | ✓ |
| [ADDITIONAL_FEATURE] | [IMPLEMENTATION] | ✓ |

**Quick Demo:** Login → Access protected route → Logout

---

## TECHNICAL STANDARDS SECTION (35 Points)

---

### SLIDE 12: Code Organization (10 Points) (2 minutes)

#### Backend Package Structure

```
com.[COMPANY].[PROJECT]/
├── controller/          # REST Controllers (API endpoints)
│   ├── AuthController.java
│   ├── [UseCase1]Controller.java
│   └── [UseCase2]Controller.java
├── service/             # Business Logic Layer
│   ├── [Service]Interface.java
│   └── impl/
│       └── [Service]Impl.java
├── repository/          # Data Access Layer (JPA)
│   └── [Entity]Repository.java
├── entity/              # JPA Entities
│   └── [Entity].java
├── dto/                 # Data Transfer Objects
│   ├── [Entity]Request.java
│   └── [Entity]Response.java
├── config/              # Configuration Classes
│   ├── SecurityConfig.java
│   └── CorsConfig.java
├── security/            # Security Components
│   ├── JwtUtil.java
│   └── JwtAuthenticationFilter.java
└── exception/           # Exception Handling
    ├── GlobalExceptionHandler.java
    └── [Custom]Exception.java
```

#### Frontend Structure

```
src/app/
├── core/                # Core module (singleton services)
├── shared/              # Shared components, pipes, directives
├── features/            # Feature modules
│   ├── auth/            # Login, Register
│   ├── [use-case-1]/    # Primary feature
│   └── [use-case-2]/    # Secondary feature
├── services/            # API services
├── guards/              # Route guards
├── interceptors/        # HTTP interceptors
└── models/              # TypeScript interfaces
```

#### Design Patterns Used

| Pattern | Where Used | Benefit |
|---------|-----------|---------|
| Repository Pattern | Data access layer | Abstraction, testability |
| Service Layer | Business logic | Separation of concerns |
| DTO Pattern | API communication | Decouple entities from API |
| Dependency Injection | Throughout | Loose coupling |
| Interceptor Pattern | HTTP requests | Cross-cutting concerns |
| [ADDITIONAL_PATTERN] | [WHERE_USED] | [BENEFIT] |

---

### SLIDE 13: Database & Security (15 Points) (2-3 minutes)

#### Entity Relationship Diagram (ERD)

```
┌─────────────────┐          ┌─────────────────────┐
│      USER       │          │    [ENTITY_1]       │
├─────────────────┤          ├─────────────────────┤
│ PK id (BIGINT)  │──────┐   │ PK id (BIGINT)      │
│    username     │      │   │ FK user_id          │◄─┐
│    email        │      │   │    [FIELD_1]        │  │
│    password     │      └──►│    [FIELD_2]        │  │
│    role         │          │    [FIELD_3]        │  │
│    created_at   │          │    created_at       │  │
│    updated_at   │          │    updated_at       │  │
└─────────────────┘          └─────────────────────┘  │
        │                                             │
        │ 1:N                                         │
        ▼                                             │
┌─────────────────┐          ┌─────────────────────┐  │
│   [ENTITY_2]    │          │    [ENTITY_3]       │  │
├─────────────────┤          ├─────────────────────┤  │
│ PK id           │          │ PK id               │  │
│ FK user_id      │          │ FK [entity_1]_id    │──┘
│    [FIELD_1]    │          │    [FIELD_1]        │
│    [FIELD_2]    │          │    [FIELD_2]        │
│    timestamp    │          │    created_at       │
└─────────────────┘          └─────────────────────┘
```

#### Security Implementation

| Security Layer | Implementation | Details |
|----------------|---------------|---------|
| **Authentication** | JWT + Spring Security | Stateless, [EXPIRY_TIME] expiry |
| **Password Storage** | BCrypt | Salt rounds: 10 |
| **Data Encryption** | [ENCRYPTION_ALGO] | [ENCRYPTION_DETAILS] |
| **Input Validation** | Bean Validation | @NotNull, @Size, @Email |
| **SQL Injection** | JPA/Hibernate | Parameterized queries |
| **XSS Protection** | Angular sanitization | Built-in protection |
| **CORS** | Configured origins | localhost:4200, [AWS_DOMAIN] |

#### Security Flow

```
Request → CORS Filter → JWT Filter → Authentication
    → Authorization → Controller → Response
```

---

### SLIDE 14: UX Design (10 Points) (1-2 minutes)

#### UI/UX Highlights

| Aspect | Implementation |
|--------|---------------|
| **Responsive Design** | CSS Flexbox/Grid, Mobile-first |
| **Navigation** | Intuitive menu, breadcrumbs |
| **Forms** | Inline validation, clear error messages |
| **Feedback** | Loading spinners, success/error toasts |
| **Accessibility** | ARIA labels, keyboard navigation |
| [ADDITIONAL_UX] | [IMPLEMENTATION] |

#### Key Screens

```
┌─────────────────────────────────────────────────────────┐
│  [Screenshot: Login Page]    [Screenshot: Dashboard]    │
│                                                         │
│  • Clean login form          • Clear data visualization │
│  • Validation messages       • Easy navigation          │
│  • Remember me option        • Action buttons visible   │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│  [Screenshot: Use Case 1]    [Screenshot: Use Case 2]   │
│                                                         │
│  • Intuitive workflow        • Search/filter options    │
│  • Clear CTAs                • Responsive tables        │
│  • Confirmation dialogs      • Pagination               │
└─────────────────────────────────────────────────────────┘
```

#### User Flow Optimization

- Minimal clicks to complete tasks
- Clear visual hierarchy
- Consistent design language
- Helpful error messages with recovery suggestions

---

## TESTING & DOCUMENTATION SECTION (25 Points)

---

### SLIDE 15: Testing (15 Points) (2 minutes)

#### Test Coverage Summary

| Test Type | Framework | Coverage | Status |
|-----------|-----------|----------|--------|
| Unit Tests (Backend) | JUnit 5 + Mockito | Service layer | ✓ |
| Unit Tests (Frontend) | Jasmine + Karma | Components | ✓ |
| Integration Tests | Spring Boot Test | API endpoints | ✓ |
| E2E Tests | [FRAMEWORK] | Critical flows | [STATUS] |

#### Sample Test Cases

**Backend Unit Test Example:**
```java
@Test
void create[Entity]_WithValidData_ReturnsCreated() {
    // Arrange
    [Entity]Request request = new [Entity]Request("[VALUE_1]", "[VALUE_2]");
    when(repository.save(any())).thenReturn(mock[Entity]);

    // Act
    [Entity]Response response = service.create(request);

    // Assert
    assertNotNull(response);
    assertEquals("[EXPECTED_VALUE]", response.get[Field]());
    verify(repository, times(1)).save(any());
}
```

**Test Results:**
```
Tests run: [X], Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

#### Testing Strategy

| Layer | What We Test | Tools |
|-------|-------------|-------|
| Service | Business logic, edge cases | JUnit, Mockito |
| Controller | Request/response mapping | MockMvc |
| Repository | Query methods | @DataJpaTest |
| Frontend | Component behavior | Jasmine |

---

### SLIDE 16: Logging (5 Points) (1 minute)

#### Logging Implementation

| Log Level | Usage | Example |
|-----------|-------|---------|
| INFO | Normal operations | User login, CRUD operations |
| DEBUG | Development details | Request/response data |
| WARN | Potential issues | Invalid input attempts |
| ERROR | Failures | Exceptions, failed operations |

#### Log Configuration (application.properties)
```properties
logging.level.root=INFO
logging.level.com.[COMPANY].[PROJECT]=DEBUG
logging.level.org.springframework.security=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/application.log
```

#### Audit Logging

```
┌─────────────────────────────────────────────────────────┐
│ AUDIT_LOG Table                                         │
├─────────────────────────────────────────────────────────┤
│ [TIMESTAMP] | [ACTION_1]    | [USER_EMAIL]              │
│ [TIMESTAMP] | [ACTION_2]    | [ENTITY_ID]: [VALUE]      │
│ [TIMESTAMP] | [ACTION_3]    | [ENTITY_ID]: [VALUE]      │
│ [TIMESTAMP] | [ACTION_4]    | [ENTITY_ID]: [VALUE]      │
└─────────────────────────────────────────────────────────┘
```

#### Log Demo

- Show application startup logs
- Demonstrate request logging
- Show audit trail for user actions

---

### SLIDE 17: Deliverables (5 Points) (1 minute)

#### Project Deliverables Checklist

| Deliverable | Location | Status |
|-------------|----------|--------|
| **Source Code** | GitHub Repository | ✓ |
| **README.md** | Root directory | ✓ |
| **API Documentation** | /docs/api.md or Swagger | ✓ |
| **ERD Diagram** | /docs/erd.md | ✓ |
| **Architecture Diagram** | /docs/architecture.md | ✓ |
| **Setup Instructions** | README.md | ✓ |
| **CI/CD Pipeline** | .github/workflows/ | ✓ |
| **Test Reports** | Generated on build | ✓ |

#### Repository Structure

```
[PROJECT_NAME]/
├── README.md                 # Project overview, setup instructions
├── .github/
│   └── workflows/
│       ├── ci.yml            # Build & test on PR
│       └── deploy.yml        # Deploy to AWS
├── frontend/
│   └── README.md             # Frontend-specific docs
├── backend/
│   └── README.md             # Backend-specific docs
├── docs/
│   ├── architecture.md       # System architecture
│   ├── api-documentation.md  # API endpoints
│   └── erd.md                # Database design
└── docker-compose.yml        # Local setup (Phase 2)
```

---

### SLIDE 18: Deployment Architecture (2 minutes)

#### Phase 1: AWS Deployment with GitHub Actions

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
│  │  │  │Spring Boot │  │◄────►│  Production Database   │  │  │
│  │  │  │ + Angular  │  │ JDBC │  (Private Subnet)      │  │  │
│  │  │  │  (nginx)   │  │      │                        │  │  │
│  │  │  └────────────┘  │      └────────────────────────┘  │  │
│  │  └──────────────────┘                                   │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

#### Phase 2: Local Microservices

```
┌──────────────────────────────────────────────────────────────┐
│                   LOCAL DEVELOPMENT                           │
│                                                               │
│  Angular ([FE_PORT]) ──► API Gateway ([GW_PORT]) ──┬──► Auth ([PORT_1])     │
│                                          ├──► [Service1] ([PORT_2])  │
│                                          └──► [Service2] ([PORT_3])  │
│                                                    │          │
│                          Eureka Server ([EUREKA_PORT]) ◄────┘          │
│                                    │                          │
│                          MySQL ([DB_PORT]) ◄────────────────────   │
└──────────────────────────────────────────────────────────────┘
```

---

### SLIDE 19: Development Methodology (1 minute)

#### Git Workflow

```
main ─────────────────────────────────────────────►
  │                                    ▲
  └──► develop ────────────────────────┤
         │        ▲        ▲           │
         │        │        │           │
         └──► feature/[use-case-1] ────┘
         └──► feature/[use-case-2] ────┘
         └──► bugfix/[issue-number] ───┘
```

#### Development Workflow

| Phase | Activity | Tools |
|-------|----------|-------|
| Planning | User stories, task breakdown | GitHub Issues |
| Development | Feature branches, commits | Git, IDE |
| Review | Pull requests, code review | GitHub PRs |
| Testing | Automated tests | JUnit, Jasmine |
| Deployment | CI/CD pipeline | GitHub Actions |

#### Local Environment Setup

```bash
# Prerequisites: Java 17, Maven, Node 18+, MySQL 8

# Backend
cd backend && mvn spring-boot:run    # Port [BACKEND_PORT]

# Frontend
cd frontend && npm install && npm start  # Port [FRONTEND_PORT]
```

---

## INDIVIDUAL CONTRIBUTIONS SECTION (Each member: 1-2 minutes)

---

### SLIDE 20-25: Individual Member Contributions

**Instructions:** Each team member presents their own slide (1-2 min each). This demonstrates individual understanding and contribution.

---

#### SLIDE 20: [MEMBER_1_NAME] - Contributions

**Feature Ownership:** [FEATURE_NAME]
**Additional Role:** Tech Lead

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | [CONTROLLERS_SERVICES] | [FILE_1].java, [FILE_2].java |
| Frontend | [COMPONENTS_SERVICES] | [FILE_1].ts, [FILE_2].ts |
| Database | [TABLES_DESIGNED] | [TABLE_NAME] table |
| Testing | [TESTS_WRITTEN] | [TEST_FILE].java |

##### Code Highlight

```java
// [DESCRIPTION_OF_CODE]
[CODE_SNIPPET_YOU_ARE_PROUD_OF]
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

#### SLIDE 21: [MEMBER_2_NAME] - Contributions

**Feature Ownership:** [FEATURE_NAME]
**Additional Role:** DevOps (CI/CD)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | [CONTROLLERS_SERVICES] | [FILE_1].java, [FILE_2].java |
| Frontend | [COMPONENTS] | [FILE_1].ts, [FILE_2].ts |
| DevOps | [CI_CD_PIPELINE] | .github/workflows/ci.yml, deploy.yml |
| AWS | [INFRASTRUCTURE] | EC2 setup, RDS configuration |

##### Code/Config Highlight

```yaml
# [DESCRIPTION_OF_CONFIG]
[CONFIG_SNIPPET_YOU_CONFIGURED]
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [DEVOPS_SKILL_GAINED]
- [CLOUD_DEPLOYMENT_UNDERSTANDING]

---

#### SLIDE 22: [MEMBER_3_NAME] - Contributions

**Feature Ownership:** [FEATURE_NAME]

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | [WHAT_YOU_BUILT] | [FILES] |
| Frontend | [WHAT_YOU_BUILT] | [FILES] |
| Testing | [TESTS_WRITTEN] | [FILES] |

##### Code Highlight

```[LANGUAGE]
// [DESCRIPTION_OF_CODE]
[CODE_SNIPPET_YOU_ARE_PROUD_OF]
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

#### SLIDE 23: [MEMBER_4_NAME] - Contributions

**Feature Ownership:** [FEATURE_NAME]

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | [WHAT_YOU_BUILT] | [FILES] |
| Frontend | [WHAT_YOU_BUILT] | [FILES] |
| Security | [SECURITY_COMPONENTS] | [FILES] |

##### Code Highlight

```[LANGUAGE]
// [DESCRIPTION_OF_CODE]
[CODE_SNIPPET_YOU_ARE_PROUD_OF]
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

#### SLIDE 24: [MEMBER_5_NAME] - Contributions (if 5+ member team)

**Feature Ownership:** [FEATURE_NAME]

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | [WHAT_YOU_BUILT] | [FILES] |
| Frontend | [WHAT_YOU_BUILT] | [FILES] |

##### Code Highlight

```[LANGUAGE]
// [DESCRIPTION_OF_CODE]
[CODE_SNIPPET]
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

#### SLIDE 25: [MEMBER_6_NAME] - Contributions (if 6 member team)

**Feature Ownership:** [FEATURE_NAME]

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | [WHAT_YOU_BUILT] | [FILES] |
| Frontend | [WHAT_YOU_BUILT] | [FILES] |

##### Code Highlight

```[LANGUAGE]
// [DESCRIPTION_OF_CODE]
[CODE_SNIPPET]
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 26: Team Challenges & Collective Learnings (1 minute)

#### Team-Level Challenges

| Challenge | Impact | How We Solved It Together |
|-----------|--------|---------------------------|
| Coordinating parallel development | Merge conflicts, integration issues | Daily standups, clear branch naming |
| Consistent coding standards | Code review delays | Established coding guidelines early |
| Service integration (Phase 2) | API contract mismatches | API-first design, shared DTOs |
| [TEAM_CHALLENGE_1] | [IMPACT] | [SOLUTION] |
| [TEAM_CHALLENGE_2] | [IMPACT] | [SOLUTION] |

#### Collective Learnings

| Area | What We Learned as a Team |
|------|---------------------------|
| **Technical** | Full-stack development, microservices patterns |
| **Collaboration** | Git workflow, code reviews, communication |
| **Problem Solving** | Debugging distributed systems, research skills |
| **Professional** | Meeting deadlines, documentation importance |

---

### SLIDE 27: Q&A (5-10 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                       QUESTIONS?                            │
│                                                             │
│                                                             │
│     GitHub: [REPOSITORY_URL]                                │
│                                                             │
│     Live Demo: [AWS_URL]                                    │
│                                                             │
│     Team Contact: [TEAM_EMAIL_OR_SLACK]                     │
│                                                             │
│     Individual GitHub Contributions:                        │
│     • [MEMBER_1]: [X commits, Y PRs]                        │
│     • [MEMBER_2]: [X commits, Y PRs]                        │
│     • [MEMBER_3]: [X commits, Y PRs]                        │
│     • [MEMBER_4]: [X commits, Y PRs]                        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Q&A Tips:**
- Any team member can answer questions about their feature area
- Be honest about limitations and future improvements
- Reference specific code/commits when explaining technical decisions

---

## REVIEWER SECTION

---

### Scoring Checklist

#### Core Features (40 Points)

**Main Use Case 1 (20 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Feature fully functional | 8 | [ ] |
| All CRUD operations work | 4 | [ ] |
| Edge cases handled | 4 | [ ] |
| Demo successful | 4 | [ ] |

**Use Case 2 (14 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Feature implemented | 6 | [ ] |
| Integration with Use Case 1 | 4 | [ ] |
| Demo successful | 4 | [ ] |

**Common Features (6 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Authentication working | 2 | [ ] |
| Validation implemented | 2 | [ ] |
| Error handling | 2 | [ ] |

---

#### Technical Standards (35 Points)

**Code Organization (10 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Clear package structure | 3 | [ ] |
| Layered architecture | 3 | [ ] |
| Design patterns used | 2 | [ ] |
| Clean code practices | 2 | [ ] |

**DB & Security (15 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Proper ERD design | 4 | [ ] |
| Relationships correct | 3 | [ ] |
| JWT implementation | 4 | [ ] |
| Data encryption | 2 | [ ] |
| Input validation | 2 | [ ] |

**UX (10 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Intuitive navigation | 3 | [ ] |
| Form validation feedback | 3 | [ ] |
| Responsive design | 2 | [ ] |
| Loading/error states | 2 | [ ] |

---

#### Testing & Documentation (25 Points)

**Testing (15 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Unit tests present | 5 | [ ] |
| Integration tests | 5 | [ ] |
| Tests passing | 3 | [ ] |
| Reasonable coverage | 2 | [ ] |

**Logging (5 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| Application logging | 2 | [ ] |
| Audit trail | 2 | [ ] |
| Log levels appropriate | 1 | [ ] |

**Deliverables (5 pts)**

| Criteria | Points | Achieved |
|----------|--------|----------|
| README complete | 2 | [ ] |
| API documentation | 2 | [ ] |
| CI/CD pipeline | 1 | [ ] |

---

### Individual Contribution Assessment

**Purpose:** Verify each team member contributed meaningfully as a full-stack developer.

#### Per-Member Evaluation

| Member | Frontend Work | Backend Work | Full Stack? | Challenges Explained | Score (1-5) |
|--------|--------------|--------------|-------------|---------------------|-------------|
| [MEMBER_1] | [ ] | [ ] | [ ] | [ ] | |
| [MEMBER_2] | [ ] | [ ] | [ ] | [ ] | |
| [MEMBER_3] | [ ] | [ ] | [ ] | [ ] | |
| [MEMBER_4] | [ ] | [ ] | [ ] | [ ] | |
| [MEMBER_5] | [ ] | [ ] | [ ] | [ ] | |
| [MEMBER_6] | [ ] | [ ] | [ ] | [ ] | |

#### Individual Contribution Criteria

| Criteria | What to Look For |
|----------|------------------|
| **Full Stack Evidence** | Member shows both Angular and Spring Boot code |
| **Feature Ownership** | Clear end-to-end ownership of a feature |
| **Code Understanding** | Can explain their code during Q&A |
| **Problem Solving** | Describes real challenges and thoughtful solutions |
| **Git Contribution** | Visible commits in repository |
| **Technical Depth** | Code highlight shows understanding, not just copy-paste |

#### Red Flags

- Member cannot explain their own code
- All commits from one or two members only
- Generic challenges (not specific to their work)
- Unable to answer questions about their feature area

---

## PRESENTATION TIPS

### 1. Time Management

- Practice the full presentation multiple times
- Have a backup plan if demo fails (screenshots/video)
- Individual slides: strictly 1-2 minutes each
- Leave buffer time for Q&A

### 2. Demo Preparation

- Test all features before presentation
- Have sample data ready
- Clear browser cache, use incognito mode
- Have AWS deployment running (Phase 1)
- Have all microservices running locally (Phase 2)

### 3. Team Coordination

- Assign speakers for each section
- Smooth transitions between speakers
- Each member presents their own contribution slide
- All team members should be ready for Q&A on their feature

### 4. Individual Contribution Slides

- **Be Specific:** Show actual file names, not generic descriptions
- **Show Code:** Pick 1 meaningful code snippet you're proud of
- **Real Challenges:** Describe actual problems you faced (not generic)
- **Own Your Work:** Be ready to explain any line of code you show
- **Practice:** Each member should practice their 1-2 minute section

### 5. Technical Setup

- Test screen sharing
- Have code editor ready for questions
- Backend and frontend running before presentation
- Terminal ready to show logs if asked

### 6. Q&A Preparation

- Anticipate common questions per feature area
- Each member should be able to answer questions about:
  - Their specific code/feature
  - Design decisions they made
  - How they tested their feature
- Be honest about limitations and future improvements

### 7. Git Contribution Evidence

- Be ready to show git log/blame for your contributions
- Know your commit count and key PRs
- Can demonstrate you wrote the code you're presenting

---

## QUICK REFERENCE: Placeholder Summary

| Placeholder | Replace With |
|-------------|--------------|
| `[PROJECT_NAME]` | Your project name |
| `[USE_CASE_1]` | Primary feature name |
| `[USE_CASE_2]` | Secondary feature name |
| `[ENTITY_1]`, `[ENTITY_2]` | Database entity names |
| `[MEMBER_1]` - `[MEMBER_6]` | Team member names |
| `[FEATURE_1]` - `[FEATURE_6]` | Feature each member owns |
| `[PORT_1]` - `[PORT_X]` | Service port numbers |
| `[RESOURCE_1]`, `[RESOURCE_2]` | API resource paths |
| `[COMPANY]` | Package namespace |
| `[ENCRYPTION_TYPE]` | e.g., AES-256-GCM |
| `[EXPIRY_TIME]` | e.g., 24 hours |
| `[CHALLENGE_X]` | Specific challenge faced |
| `[SOLUTION_X]` | How you solved it |
| `[LEARNING_X]` | What you learned |
