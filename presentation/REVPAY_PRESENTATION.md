# Project Presentation Deck - RevPay

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
│                        RevPay                               │
│              Digital Payments Platform                      │
│                                                             │
│     Phase 1: Monolithic Application (AWS Deployment)        │
│     Phase 2: Microservices Architecture (Docker)            │
│                                                             │
│     Team Members (Full Stack Developers):                   │
│     • Nikhil - Business Module                              │
│     • Vivek - Cards Management                              │
│     • Dileep - Notification System                          │
│     • Koushik - Login/Registration                          │
│     • Sri Charani - Payments & Wallets (Transactions)       │
│                                                             │
│     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A comprehensive digital payments platform supporting wallet transfers, money requests, transaction PIN authorization, card management, business workflows, and notification management for personal and business users.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | Wallet Operations (Balance, Add, Withdraw, Send) | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-02 | Money Request Lifecycle (Create, Accept, Reject) | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-03 | Business Profile & Verification | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-04 | Invoice & Loan Management | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-05 | User Registration & JWT Authentication | Common Features (6 pts) | ✓ | ✓ |
| FR-06 | Transaction PIN (4-digit) Verification | Common Features (6 pts) | ✓ | ✓ |
| FR-07 | Card Management (Add, Delete, Default) | Common Features (6 pts) | ✓ | ✓ |
| FR-08 | Multi-Category Notifications | Common Features | ✓ | ✓ |
| FR-09 | Role-Based Access (PERSONAL/BUSINESS/ADMIN) | Common Features | ✓ | ✓ |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Card Data | AES Encryption |
| Security | Authentication | JWT Tokens |
| Security | Password Storage | BCrypt Hashing |
| Security | Transaction Authorization | 4-digit PIN |
| Performance | API Response | < 500ms |
| Availability | Uptime (P1) | 99% |
| Resilience | Account Lockout | 5 failed attempts → 15 min lock |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL database available (local + AWS RDS) |
| A-02 | AWS Free Tier resources accessible for Phase 1 |
| A-03 | Docker environment available for Phase 2 microservices |
| A-04 | Team has GitHub repository access |
| A-05 | Users have valid email for registration |
| A-06 | Security questions configured for password recovery |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Card encryption key management | Critical | Secure key storage, AES encryption |
| Inter-service communication failures | High | Eureka discovery, health checks |
| Transaction PIN brute force | High | Account lockout after 5 attempts |
| Insufficient test coverage | High | Unit tests, integration tests |
| AWS deployment issues | High | Docker Compose local fallback |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
┌────────────────────────────────────────────────────────────┐
│                  ANGULAR FRONTEND                           │
│              (Components, Services, Guards)                 │
│                    Port: 4200                               │
└─────────────────────────┬──────────────────────────────────┘
                          │ HTTP/REST + JWT
┌─────────────────────────┴──────────────────────────────────┐
│               SPRING BOOT 3.1.5 MONOLITH (Port 8080)        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                    CONTROLLERS                       │   │
│  │  Auth, Wallet, Request, Notification, Business,     │   │
│  │  Cards, Dashboard, Security, Payment                │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                     SERVICES                         │   │
│  │  UserService, WalletService, RequestService,        │   │
│  │  NotificationService, BusinessService, CardService   │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                   REPOSITORIES                       │   │
│  │  JPA Repositories for all entities                  │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │   Security: JWT + Spring Security + PIN Verification │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────┬──────────────────────────────────┘
                          │ JDBC/JPA
┌─────────────────────────┴──────────────────────────────────┐
│                    MySQL DATABASE                           │
│                     (revpay_dev)                            │
└────────────────────────────────────────────────────────────┘
```

#### Phase 2: Microservices Architecture

```
┌────────────────────────────────────────────────────────────┐
│              ANGULAR / REACT / VUE FRONTEND                 │
└─────────────────────────┬──────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────┐
│               API GATEWAY (Port 8080)                       │
│         JWT Validation + Request Routing + CORS             │
│     X-User-Id, X-User-Email, X-User-Role Header Injection   │
└────────┬──────────┬──────────┬──────────┬──────────────────┘
         │          │          │          │
    ┌────┴────┐ ┌───┴───┐ ┌────┴────┐ ┌───┴────┐ ┌────────┐
    │  Auth   │ │ Card  │ │ Notif.  │ │ Wallet │ │Business│
    │ Service │ │Service│ │ Service │ │Service │ │Service │
    │  8081   │ │ 8082  │ │  8083   │ │  8084  │ │  8085  │
    └────┬────┘ └───┬───┘ └────┬────┘ └───┬────┘ └───┬────┘
         │          │          │          │          │
         └──────────┴──────────┴──────────┴──────────┘
                               │
         ┌─────────────────────┼─────────────────────┐
         │                     │                     │
┌────────┴────────┐  ┌─────────┴─────────┐  ┌───────┴───────┐
│ Discovery Server│  │   Config Server   │  │    MySQL      │
│  Eureka (8761)  │  │      (8888)       │  │    (3306)     │
└─────────────────┘  └───────────────────┘  └───────────────┘
```

#### API Gateway Routes (Phase 2)

| Route | Target Service | Auth Required |
|-------|---------------|---------------|
| `/api/auth/**` | auth-service | No |
| `/api/v1/security/**` | auth-service | Yes |
| `/api/v1/cards/**` | card-service | Yes |
| `/api/v1/notifications/**` | notification-service | Yes |
| `/api/v1/wallet/**` | wallet-service | Yes |
| `/api/v1/transactions/**` | wallet-service | Yes |
| `/api/v1/requests/**` | wallet-service | Yes |
| `/api/v1/business/**` | business-service | Yes |

---

## CORE FEATURES SECTION (40 Points)

---

### SLIDE 5: Main Use Case 1 - Payments & Wallet Design (20 Points) (2 minutes)

**Use Case:** Digital Wallet & Payment Operations

#### User Stories

> As a **User**, I want to add funds to my wallet so that I can make payments and transfers.

> As a **User**, I want to send money to other users and track my transactions.

> As a **User**, I want to create and respond to money requests so that I can request payments from others.

#### Feature Flow

```
┌─────────────┐   ┌──────────────┐   ┌───────────────┐   ┌─────────────┐
│    Login    │──►│ View Balance │──►│  Add Funds /  │──►│ Transaction │
│   + PIN     │   │  Dashboard   │   │  Send Money   │   │  Complete   │
└─────────────┘   └──────────────┘   └───────────────┘   └─────────────┘
                                            │
                                            ▼
                                     ┌─────────────┐
                                     │ Notification│
                                     │    Sent     │
                                     └─────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                   MONEY REQUEST LIFECYCLE                        │
│                                                                  │
│   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────────────┐ │
│   │ CREATE  │──►│ PENDING │──►│ ACCEPT/ │──►│ TRANSFER FUNDS  │ │
│   │ REQUEST │   │         │   │ REJECT  │   │ (if accepted)   │ │
│   └─────────┘   └─────────┘   └─────────┘   └─────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

#### API Endpoints for Wallet & Payments

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/v1/wallet/balance | Get wallet balance |
| POST | /api/v1/wallet/add-funds | Add funds to wallet |
| POST | /api/v1/wallet/withdraw | Withdraw funds |
| POST | /api/v1/transactions/send | Send money to user |
| GET | /api/v1/transactions | Get transaction history |
| POST | /api/v1/requests/create | Create money request |
| GET | /api/v1/requests | List all money requests |
| POST | /api/v1/requests/{id}/respond | Accept/reject request |

#### Implementation Highlights

- **Transaction PIN:** 4-digit PIN required for sensitive operations
- **Atomic Transactions:** Spring @Transactional ensures data integrity
- **BigDecimal Precision:** All monetary values use BigDecimal
- **Transaction Types:** SEND, RECEIVE, REQUEST, DEPOSIT, WITHDRAWAL, PAYMENT
- **Request Status:** PENDING, ACCEPTED, DECLINED, CANCELLED

---

### SLIDE 6: Main Use Case 1 - Wallet & Payments DEMO (3-4 minutes)

**Live Demo Script:**

| Step | Action | Expected Result | Points Demonstrated |
|------|--------|-----------------|---------------------|
| 1 | Login with credentials | Dashboard loads with wallet balance | Authentication |
| 2 | View Wallet Balance | Current balance displayed | Wallet API |
| 3 | Add Funds to Wallet | Balance increases | Deposit Transaction |
| 4 | Send Money to User | Enter recipient, amount, PIN | PIN Verification |
| 5 | Verify Transaction | Success message, balance updated | Transaction Processing |
| 6 | Check Recipient Balance | Recipient balance increased | Peer-to-Peer Transfer |
| 7 | Create Money Request | Enter payer email, amount, note | Request Creation |
| 8 | Login as Payer | See pending request notification | Notification System |
| 9 | Accept Request | Confirm with PIN, funds transferred | Request Lifecycle |
| 10 | View Transaction History | All transactions listed | Audit Trail |

**Backup:** Screenshots/video recording prepared for demo failure scenarios

---

### SLIDE 7: Use Case 2 - Business Module Design (14 Points) (1-2 minutes)

**Use Case:** Business Profile, Invoice & Loan Management

#### User Stories

> As a **Business User**, I want to create and verify my business profile so that I can access business features.

> As a **Business User**, I want to create and send invoices to clients and track payments.

> As a **Business User**, I want to apply for business loans and track repayment schedules.

#### Feature Components

```
┌─────────────────────────────────────────────────────────────────┐
│                  BUSINESS PROFILE MANAGEMENT                     │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │    Create     │───►│    Submit     │───►│   VERIFIED/    │  │
│   │    Profile    │    │   Documents   │    │   REJECTED     │  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
│                                                                  │
│   Status Flow: NOT_SUBMITTED → PENDING_VERIFICATION → VERIFIED   │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                     INVOICE MANAGEMENT                           │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │    Create     │───►│     SENT      │───►│  PAID/OVERDUE  │  │
│   │   Invoice     │    │               │    │                │  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
│                                                                  │
│   Features: Line items, Auto invoice numbers, Payment tracking   │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      LOAN MANAGEMENT                             │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │    Apply      │───►│   SUBMITTED   │───►│   APPROVED/    │  │
│   │   for Loan    │    │               │    │   REJECTED     │  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
│                              │                      │            │
│                              ▼                      ▼            │
│                 ┌───────────────────────┐  ┌────────────────┐   │
│                 │ ADDITIONAL_DOCS_REQ   │  │  Repayment     │   │
│                 │                       │  │  Schedule      │   │
│                 └───────────────────────┘  └────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

#### API Endpoints for Business Module

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/business/profile/user/{userId} | Get business profile |
| POST | /api/business/profile/user/{userId} | Create/update profile |
| PUT | /api/business/profile/{id}/status | Update verification status |
| POST | /api/business/invoices/user/{userId} | Create invoice |
| GET | /api/business/invoices/user/{userId} | List invoices |
| POST | /api/business/invoices/{number}/pay | Pay invoice |
| POST | /api/business/loans/user/{userId} | Apply for loan |
| PUT | /api/business/loans/{id}/approve | Approve loan |
| GET | /api/business/analytics/user/{userId} | Get business analytics |

---

### SLIDE 8: Use Case 2 - Business Module DEMO (2-3 minutes)

**Live Demo Script:**

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Login as Business User | Business dashboard displayed |
| 2 | Create Business Profile | Enter business details |
| 3 | Submit for Verification | Status: PENDING_VERIFICATION |
| 4 | Create Invoice | Add line items, recipient |
| 5 | View Invoice List | Invoice with unique number |
| 6 | Apply for Business Loan | Enter amount, term, purpose |
| 7 | View Loan Status | Status: SUBMITTED |
| 8 | (Admin) Approve Loan | Repayment schedule generated |
| 9 | View Business Analytics | Total invoices, revenue, loans |
| 10 | Add Bank Account | Encrypted storage, set default |

---

### SLIDE 9: Common Features (6 Points) (1 minute)

#### Authentication Flow

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   Register   │───►│    Login     │───►│   Generate   │───►│   Access     │
│  (Security   │    │  (Validate)  │    │    JWT       │    │  Protected   │
│  Questions)  │    │              │    │              │    │  Resources   │
└──────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
                           │
                           ▼ (5 failed attempts)
                    ┌──────────────┐
                    │   Account    │
                    │   Locked     │
                    │  (15 mins)   │
                    └──────────────┘
```

#### Common Features Implemented

| Feature | Implementation | Status |
|---------|---------------|--------|
| User Registration | Spring Security + BCrypt | ✓ |
| JWT Authentication | Stateless token-based | ✓ |
| Role-Based Access | PERSONAL, BUSINESS, ADMIN | ✓ |
| Account Lockout | 5 failed attempts → 15 min lock | ✓ |
| Security Questions | Password/PIN recovery | ✓ |
| Transaction PIN | 4-digit encrypted PIN | ✓ |
| Card Management | AES encrypted, last 4 digits exposed | ✓ |
| Notifications | TRANSACTIONS, REQUESTS, ALERTS | ✓ |
| Notification Preferences | Per-user settings, low balance threshold | ✓ |
| Input Validation | Bean Validation (@Valid) | ✓ |
| Error Handling | GlobalExceptionHandler | ✓ |

#### Card Management Features

```
┌─────────────────────────────────────────────────────────────────┐
│                    CARD MANAGEMENT                               │
│                                                                  │
│   • Add Cards (VISA, MASTERCARD, AMEX, DISCOVER)                │
│   • Card Types: DEBIT, CREDIT                                    │
│   • AES Encryption for card number and CVV                       │
│   • Only last 4 digits exposed in responses                      │
│   • Maximum 10 cards per user                                    │
│   • Set default card                                             │
│   • Automatic default assignment                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Quick Demo:** Register → Login → Setup PIN → Add Card → View Notifications

---

## TECHNICAL STANDARDS SECTION (35 Points)

---

### SLIDE 10: Code Organization (10 Points) (2 minutes)

#### Backend Package Structure (Phase 1 - Monolith)

```
com.revpay/
├── controller/              # REST Controllers (14 controllers)
│   ├── AuthController.java
│   ├── WalletController.java
│   ├── RequestController.java
│   ├── NotificationController.java
│   ├── BusinessController.java
│   ├── CardController.java
│   ├── DashboardController.java
│   └── SecurityController.java
├── service/                 # Business Logic Layer (17 services)
│   ├── UserService.java
│   ├── WalletService.java
│   ├── TransactionService.java
│   ├── RequestService.java
│   ├── NotificationService.java
│   ├── BusinessService.java
│   └── CardService.java
├── repository/              # Data Access Layer (13 repositories)
├── model/                   # JPA Entities (24 entities)
├── dto/                     # Data Transfer Objects (33 DTOs)
├── config/                  # Configuration Classes
│   ├── SecurityConfig.java
│   └── CorsConfig.java
├── exception/               # Exception Handling
│   └── GlobalExceptionHandler.java
└── RevpayApplication.java
```

#### Phase 2 Microservices Structure

```
p3-revpay/
├── config-server/           # Centralized config (Port 8888)
├── discovery-server/        # Eureka registry (Port 8761)
├── api-gateway/             # Request routing (Port 8080)
│   └── filter/
│       └── JwtAuthenticationFilter.java
├── auth-service/            # Authentication (Port 8081)
│   ├── controller/
│   ├── service/
│   ├── repository/
│   └── entity/
├── card-service/            # Card management (Port 8082)
│   ├── EncryptionService.java
│   └── NotificationServiceClient.java (Feign)
├── notification-service/    # Notifications (Port 8083)
│   ├── InternalController.java
│   └── NotificationController.java
├── wallet-service/          # Wallet & transactions (Port 8084)
│   ├── WalletController.java
│   ├── TransactionController.java
│   └── RequestController.java
├── business-service/        # Business operations (Port 8085)
│   ├── invoice/
│   ├── loan/
│   └── analytics/
├── docker-compose.yml       # Container orchestration
└── pom.xml                  # Parent POM
```

#### Frontend Structure

```
src/app/
├── core/                    # Core module (singleton services)
├── shared/                  # Shared components
├── features/                # Feature modules
│   ├── auth/                # Login, Register
│   ├── dashboard/           # Dashboard
│   ├── wallet/              # Wallet operations
│   ├── requests/            # Money requests
│   ├── notifications/       # Notification center
│   ├── cards/               # Card management
│   └── business/            # Business module
├── services/                # API services
├── guards/                  # Route guards
└── models/                  # TypeScript interfaces
```

#### Design Patterns Used

| Pattern | Where Used | Benefit |
|---------|-----------|---------|
| Repository Pattern | Data access layer | Abstraction, testability |
| Service Layer | Business logic | Separation of concerns |
| DTO Pattern | API communication | Decouple entities from API |
| Feign Clients | Inter-service communication | Declarative REST calls |
| Service Discovery | Eureka (Phase 2) | Dynamic service location |
| API Gateway | Single entry point (Phase 2) | Routing, authentication |
| Config Server | Centralized config (Phase 2) | Environment management |

---

### SLIDE 11: Database & Security (15 Points) (2-3 minutes)

#### Entity Relationship Diagram (ERD)

```
┌─────────────────┐          ┌─────────────────────┐
│      USERS      │          │      WALLETS        │
├─────────────────┤          ├─────────────────────┤
│ PK id           │◄────────►│ PK id               │
│    username(UK) │   1:1    │ FK user_id (UK)     │
│    email (UK)   │          │    balance          │
│    password     │          │    currency         │
│    fullName     │          │    created_at       │
│    phoneNumber  │          └─────────────────────┘
│    role         │
│    transactionPin│         ┌─────────────────────┐
│    failedAttempts│         │   TRANSACTIONS      │
│    lockoutUntil │          ├─────────────────────┤
│    enabled      │          │ PK id               │
└────────┬────────┘          │ FK sender_wallet_id │
         │                   │ FK receiver_wallet_id│
         │ 1:N               │    amount           │
         ▼                   │    type             │
┌─────────────────────┐      │    status           │
│ SECURITY_QUESTIONS  │      │    description      │
├─────────────────────┤      │    timestamp        │
│ PK id               │      └─────────────────────┘
│ FK user_id          │
│    question         │      ┌─────────────────────┐
│    answer           │      │   MONEY_REQUESTS    │
└─────────────────────┘      ├─────────────────────┤
                             │ PK id               │
┌─────────────────────┐      │ FK requester_id     │
│       CARDS         │      │ FK payer_id         │
├─────────────────────┤      │    amount           │
│ PK id               │      │    note             │
│ FK user_id          │      │    status           │
│    cardHolderName   │      │    created_at       │
│    cardNumber (ENC) │      └─────────────────────┘
│    lastFourDigits   │
│    expiryDate       │      ┌─────────────────────┐
│    cvv (ENC)        │      │   NOTIFICATIONS     │
│    cardType         │      ├─────────────────────┤
│    paymentMethodType│      │ PK id               │
│    isDefault        │      │ FK user_id          │
└─────────────────────┘      │    category         │
                             │    type             │
┌─────────────────────┐      │    title, message   │
│ BUSINESS_PROFILES   │      │    amount           │
├─────────────────────┤      │    counterparty     │
│ PK id               │      │    is_read          │
│ FK user_id          │      │    event_time       │
│    businessName     │      └─────────────────────┘
│    taxId            │
│    verificationStatus│     ┌─────────────────────┐
└─────────────────────┘      │     INVOICES        │
         │                   ├─────────────────────┤
         │ 1:N               │ PK id               │
         ▼                   │ FK business_id      │
┌─────────────────────┐      │    invoiceNumber    │
│  BUSINESS_LOANS     │      │    status           │
├─────────────────────┤      │    totalAmount      │
│ PK id               │      └─────────────────────┘
│ FK business_id      │
│    amount           │
│    status           │
│    interestRate     │
└─────────────────────┘
```

#### Security Implementation

| Security Layer | Implementation | Details |
|----------------|---------------|---------|
| **Authentication** | JWT + Spring Security | Stateless, 24h expiry |
| **Password Storage** | BCrypt | Encrypted hashing |
| **Transaction PIN** | 4-digit encrypted | Required for sensitive ops |
| **Card Encryption** | AES | Card number, CVV encrypted |
| **Account Lockout** | 5 failed attempts | 15-minute lockout |
| **Role-Based Access** | PERSONAL, BUSINESS, ADMIN | @PreAuthorize |
| **Input Validation** | Bean Validation | @NotNull, @Size, @Email |
| **SQL Injection** | JPA/Hibernate | Parameterized queries |
| **CORS** | Configured origins | localhost:4200, 3000, 5173 |

#### Security Flow (Phase 2)

```
Request → API Gateway → JWT Filter → Extract User Info
    → Add Headers (X-User-Id, X-User-Email, X-User-Role)
    → Route to Microservice → Process Request → Response
```

---

### SLIDE 12: UX Design (10 Points) (1-2 minutes)

#### UI/UX Highlights

| Aspect | Implementation |
|--------|---------------|
| **Responsive Design** | Angular responsive components |
| **Dashboard** | Wallet balance, recent transactions, pending requests |
| **Forms** | Inline validation, clear error messages |
| **PIN Entry** | 4-digit masked input |
| **Notifications** | Badge count, category filtering |
| **Card Display** | Masked card numbers (****1234) |
| **Transaction History** | Searchable, filterable list |

#### Key Screens

```
┌─────────────────────────────────────────────────────────────────┐
│  [Login Page]                    [Dashboard]                    │
│                                                                 │
│  • Email/password form           • Wallet balance widget        │
│  • Account lockout message       • Quick actions (Send, Add)    │
│  • Forgot password link          • Recent transactions          │
│  • Register option               • Pending requests count       │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Send Money]                    [Money Requests]               │
│                                                                 │
│  • Recipient lookup              • Incoming/outgoing tabs       │
│  • Amount input                  • Accept/Reject buttons        │
│  • PIN verification modal        • Request details              │
│  • Confirmation screen           • Create request form          │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Cards Management]              [Notifications]                │
│                                                                 │
│  • Add card form                 • Category tabs                │
│  • Card list with masked numbers • Unread badge                 │
│  • Set default option            • Mark read/all read           │
│  • Delete confirmation           • Preference settings          │
└─────────────────────────────────────────────────────────────────┘
```

#### User Flow Optimization

- One-click send money with recent recipients
- PIN modal with auto-focus
- Real-time balance updates
- Push notification badges
- Clear transaction status indicators

---

## TESTING & DOCUMENTATION SECTION (25 Points)

---

### SLIDE 13: Testing (15 Points) (2 minutes)

#### Test Coverage Summary

| Test Type | Framework | Coverage | Status |
|-----------|-----------|----------|--------|
| Unit Tests (Backend) | JUnit + Mockito | Service layer | ✓ |
| Integration Tests | Spring Boot Test | API endpoints | [INFO NOT AVAILABLE] |
| Frontend Tests | Jasmine + Karma | Components | [INFO NOT AVAILABLE] |
| Manual Testing | Functional Scenarios | All features | ✓ |

#### Test Classes by Service (Phase 2)

| Service | Test Classes |
|---------|-------------|
| Auth Service | AuthServiceTest, SecurityServiceTest |
| Card Service | CardServiceTest, EncryptionServiceTest |
| Notification Service | NotificationServiceTest, PreferenceServiceTest |
| Wallet Service | WalletServiceTest, TransactionServiceTest, RequestServiceTest |
| Business Service | ProfileServiceTest, InvoiceServiceTest, LoanServiceTest |

#### Sample Test Case

```java
@Test
void sendMoney_WithValidPinAndSufficientBalance_TransfersSuccessfully() {
    // Arrange
    Long senderId = 1L;
    Long receiverId = 2L;
    BigDecimal amount = new BigDecimal("100.00");

    Wallet senderWallet = Wallet.builder()
        .userId(senderId)
        .balance(new BigDecimal("500.00"))
        .build();

    Wallet receiverWallet = Wallet.builder()
        .userId(receiverId)
        .balance(new BigDecimal("200.00"))
        .build();

    when(walletRepository.findByUserId(senderId)).thenReturn(Optional.of(senderWallet));
    when(walletRepository.findByUserId(receiverId)).thenReturn(Optional.of(receiverWallet));

    // Act
    TransactionResponse response = walletService.sendMoney(senderId, receiverId, amount);

    // Assert
    assertNotNull(response);
    assertEquals(new BigDecimal("400.00"), senderWallet.getBalance());
    assertEquals(new BigDecimal("300.00"), receiverWallet.getBalance());
    verify(notificationService, times(2)).createNotification(any());
}

@Test
void verifyPin_WithInvalidPin_ThrowsInvalidCredentialsException() {
    // Arrange
    String email = "test@example.com";
    String invalidPin = "9999";

    User user = User.builder()
        .email(email)
        .transactionPin(passwordEncoder.encode("1234"))
        .build();

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    // Act & Assert
    assertThrows(InvalidCredentialsException.class, () -> {
        securityService.verifyPin(email, invalidPin);
    });
}
```

#### Manual Test Scenarios

| # | Scenario | Expected Result |
|---|----------|-----------------|
| 1 | Register with valid data | Account created, JWT returned |
| 2 | Login with valid credentials | JWT token, dashboard access |
| 3 | Login with invalid credentials (5x) | Account locked 15 mins |
| 4 | Send money with valid PIN | Transaction completed |
| 5 | Send money with invalid PIN | Error: Invalid PIN |
| 6 | Send money with insufficient balance | Error: Insufficient funds |
| 7 | Create money request | Request sent, payer notified |
| 8 | Accept money request | Funds transferred |
| 9 | Add card with valid data | Card saved, encrypted |
| 10 | Business loan application | Loan status: SUBMITTED |

---

### SLIDE 14: Logging (5 Points) (1 minute)

#### Logging Implementation

| Log Level | Usage | Example |
|-----------|-------|---------|
| INFO | Normal operations | User login, transaction completed |
| DEBUG | Development details | Request/response payloads |
| WARN | Potential issues | Failed login attempt, low balance |
| ERROR | Failures | Transaction failure, service unavailable |

#### Audit Logging

```
┌─────────────────────────────────────────────────────────────────┐
│                    NOTIFICATION-BASED AUDIT                      │
│                                                                  │
│   All significant actions create notifications:                 │
│   • TRANSACTIONS: Payment sent/received                         │
│   • REQUESTS: Money request created/accepted/rejected           │
│   • ALERTS: Low balance, account locked, card added             │
│                                                                  │
│   Each notification includes:                                    │
│   • Timestamp                                                    │
│   • User ID                                                      │
│   • Action type                                                  │
│   • Amount (if applicable)                                       │
│   • Counterparty                                                 │
│   • Status                                                       │
│   • Navigation target                                            │
└─────────────────────────────────────────────────────────────────┘
```

#### Health Monitoring (Phase 2)

```bash
# Check service health
curl http://localhost:8080/actuator/health

# View API Gateway routes
curl http://localhost:8080/actuator/gateway/routes

# Eureka dashboard
http://localhost:8761
```

---

### SLIDE 15: Deliverables (5 Points) (1 minute)

#### Project Deliverables Checklist

| Deliverable | Location | Status |
|-------------|----------|--------|
| **Source Code (P1)** | GitHub: revpay-ph2 | ✓ |
| **Source Code (P2)** | GitHub: revpay_p3 | ✓ |
| **README.md** | Root directory (both repos) | ✓ |
| **Service READMEs** | Each microservice folder | ✓ |
| **Docker Compose** | docker-compose.yml | ✓ |
| **API Documentation** | README.md with sample payloads | ✓ |
| **Setup Instructions** | README.md | ✓ |

#### Repository Structure

```
revpay-ph2/                         p3-revpay/
├── README.md                       ├── README.md
├── backend/                        ├── docker-compose.yml
│   ├── pom.xml                     ├── pom.xml
│   ├── src/main/java/              ├── config-server/
│   └── src/main/resources/         ├── discovery-server/
└── frontend/                       ├── api-gateway/
    ├── package.json                ├── auth-service/
    ├── angular.json                │   └── README.md
    └── src/app/                    ├── card-service/
                                    │   └── README.md
                                    ├── notification-service/
                                    │   └── README.md
                                    ├── wallet-service/
                                    │   └── README.md
                                    └── business-service/
                                        └── README.md
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
│  │  │  │Spring Boot │  │◄────►│  revpay_dev            │  │  │
│  │  │  │   :8080    │  │ JDBC │  (Private Subnet)      │  │  │
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

#### Phase 2: Docker Compose Deployment

```
┌──────────────────────────────────────────────────────────────┐
│                   DOCKER COMPOSE                              │
│                                                               │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                 revpay-network (bridge)                  │ │
│  │                                                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │ │
│  │  │ mysql-db    │  │config-server│  │discovery-server │  │ │
│  │  │   :3306     │  │   :8888     │  │     :8761       │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘  │ │
│  │                                                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │ │
│  │  │ api-gateway │  │auth-service │  │  card-service   │  │ │
│  │  │    :8080    │  │   :8081     │  │     :8082       │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘  │ │
│  │                                                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │ │
│  │  │notification │  │wallet-service│  │business-service │  │ │
│  │  │   :8083     │  │   :8084     │  │     :8085       │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘  │ │
│  │                                                          │ │
│  └─────────────────────────────────────────────────────────┘ │
│                                                               │
│  Volume: mysql-data                                           │
└──────────────────────────────────────────────────────────────┘
```

#### Docker Compose Commands

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Rebuild specific service
docker-compose up -d --build wallet-service
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
         └──► feature/wallet-service───┘
         └──► feature/card-service─────┘
         └──► feature/notification─────┘
         └──► feature/auth-service─────┘
         └──► feature/business-service─┘
```

#### Development Workflow

| Phase | Activity | Tools |
|-------|----------|-------|
| Planning | User stories, task breakdown | GitHub Issues |
| Development | Feature branches, commits | Git, IDE |
| Review | Pull requests, code review | GitHub PRs |
| Testing | Unit tests, manual testing | JUnit, Mockito |
| Deployment | CI/CD pipeline, Docker | GitHub Actions, AWS |

#### Local Environment Setup

```bash
# Prerequisites: Java 17, Maven, Node.js 18+, MySQL 8

# Phase 1 Setup
cd revpay-ph2

# Database (auto-created)
# Backend (Port 8080)
cd backend && mvn spring-boot:run

# Frontend (Port 4200)
cd frontend && npm install && npm start

# Phase 2 Setup
cd p3-revpay

# Start all services with Docker
docker-compose up -d

# Or start individually
cd config-server && mvn spring-boot:run
cd discovery-server && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
# ... repeat for other services
```

#### Default Test Credentials

| Role | Email | Password | PIN |
|------|-------|----------|-----|
| Admin | admin@revpay.com | admin123 | - |
| Personal | john.doe@example.com | password123 | 1234 |
| Business | contact@acme.com | business123 | 9999 |

---

## INDIVIDUAL CONTRIBUTIONS SECTION (Each member: 1-2 minutes)

---

### SLIDE 18: Nikhil - Contributions

**Feature Ownership:** Business Module
**Service:** business-service (Port 8085)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Business Controller, Business Service | BusinessController.java, BusinessService.java |
| Backend | Invoice & Loan Management | InvoiceService.java, LoanService.java |
| Backend | Bank Account Management | BankAccountService.java, EncryptionService.java |
| Backend | Analytics Service | AnalyticsService.java |
| Frontend | Business Profile, Invoice, Loan Components | business/ module |
| Database | business_profiles, invoices, business_loans tables | Entity design |

##### Code Highlight

```java
// Invoice Payment with Wallet Integration
@Transactional
public Invoice payInvoice(String invoiceNumber, Long payerId) {
    Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

    if (invoice.getStatus() == InvoiceStatus.PAID) {
        throw new BusinessException("Invoice already paid");
    }

    // Debit payer's wallet
    walletServiceClient.debitWallet(payerId, invoice.getTotalAmount());

    // Credit business wallet
    BusinessProfile business = invoice.getBusinessProfile();
    walletServiceClient.creditWallet(business.getUserId(), invoice.getTotalAmount());

    invoice.setStatus(InvoiceStatus.PAID);
    invoice.setPaidAt(LocalDateTime.now());

    // Send notifications
    notificationServiceClient.sendNotification(
        NotificationRequest.builder()
            .userId(business.getUserId())
            .category("TRANSACTIONS")
            .type("INVOICE_PAID")
            .title("Invoice Paid")
            .message("Invoice " + invoiceNumber + " has been paid")
            .amount(invoice.getTotalAmount())
            .build()
    );

    return invoiceRepository.save(invoice);
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

### SLIDE 19: Vivek - Contributions

**Feature Ownership:** Cards Management
**Service:** card-service (Port 8082)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Card Controller, Card Service | CardController.java, CardService.java |
| Backend | AES Encryption Service | EncryptionService.java |
| Backend | Notification Integration | NotificationServiceClient.java (Feign) |
| Frontend | Card List, Add Card Form | cards/ module |
| Database | cards table with encrypted fields | Card.java entity |
| Security | Card number/CVV encryption | AES implementation |

##### Code Highlight

```java
// AES Encryption for Card Data
@Service
public class EncryptionService {

    private static final String ALGORITHM = "AES";

    @Value("${app.security.encryption-key}")
    private String encryptionKey;

    public String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(
                Base64.getDecoder().decode(encryptedData));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}

// Card Response with masked data
public CardResponse toResponse(Card card) {
    return CardResponse.builder()
        .id(card.getId())
        .cardHolderName(card.getCardHolderName())
        .lastFourDigits(card.getLastFourDigits())  // Only expose last 4
        .expiryDate(card.getExpiryDate())
        .cardType(card.getCardType())
        .paymentMethodType(card.getPaymentMethodType())
        .isDefault(card.getIsDefault())
        .build();
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

### SLIDE 20: Dileep - Contributions

**Feature Ownership:** Notification System
**Service:** notification-service (Port 8083)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Notification Controller | NotificationController.java |
| Backend | Internal API for service-to-service | InternalController.java |
| Backend | Notification Preferences | NotificationPreferenceService.java |
| Frontend | Notification Center, Preferences | notifications/ module |
| Database | notifications, notification_preferences tables | Entity design |
| Integration | Internal API for other services | POST /api/internal/notifications |

##### Code Highlight

```java
// Internal API for creating notifications from other services
@RestController
@RequestMapping("/api/internal/notifications")
public class InternalController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestBody @Valid NotificationRequest request) {

        // Check user preferences before creating
        NotificationPreference prefs = preferenceService
            .getOrCreatePreferences(request.getUserId());

        boolean shouldCreate = switch (request.getCategory()) {
            case TRANSACTIONS -> prefs.isTransactionsEnabled();
            case REQUESTS -> prefs.isRequestsEnabled();
            case ALERTS -> prefs.isAlertsEnabled();
        };

        if (!shouldCreate) {
            return ResponseEntity.ok().build();  // User disabled this category
        }

        Notification notification = Notification.builder()
            .userId(request.getUserId())
            .category(request.getCategory())
            .type(request.getType())
            .title(request.getTitle())
            .message(request.getMessage())
            .amount(request.getAmount())
            .counterparty(request.getCounterparty())
            .eventStatus(request.getEventStatus())
            .navigationTarget(request.getNavigationTarget())
            .eventTime(request.getEventTime() != null ?
                request.getEventTime() : LocalDateTime.now())
            .isRead(false)
            .createdAt(LocalDateTime.now())
            .build();

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(toResponse(notificationService.save(notification)));
    }
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

### SLIDE 21: Koushik - Contributions

**Feature Ownership:** Login/Registration
**Service:** auth-service (Port 8081)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Auth Controller, Auth Service | AuthController.java, AuthService.java |
| Backend | Security Controller (PIN) | SecurityController.java, PinService.java |
| Backend | JWT Utility | JwtUtil.java |
| Backend | Account Lockout Logic | LoginAttemptService.java |
| Frontend | Login, Register, Forgot Password | auth/ module |
| Database | users, security_questions tables | User.java, SecurityQuestion.java |
| Security | Spring Security Configuration | SecurityConfig.java |

##### Code Highlight

```java
// Login with Account Lockout Protection
public AuthResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

    // Check if account is locked
    if (user.getLockoutUntil() != null &&
        user.getLockoutUntil().isAfter(LocalDateTime.now())) {
        long minutesRemaining = ChronoUnit.MINUTES.between(
            LocalDateTime.now(), user.getLockoutUntil());
        throw new AccountLockedException(
            "Account locked. Try again in " + minutesRemaining + " minutes");
    }

    // Validate password
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

        if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
            user.setLockoutUntil(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION));
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
            throw new AccountLockedException(
                "Account locked due to too many failed attempts");
        }

        userRepository.save(user);
        throw new InvalidCredentialsException("Invalid credentials");
    }

    // Reset failed attempts on successful login
    user.setFailedLoginAttempts(0);
    user.setLockoutUntil(null);
    userRepository.save(user);

    // Generate JWT token
    String token = jwtUtil.generateToken(user);

    return AuthResponse.builder()
        .token(token)
        .userId(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole().name())
        .build();
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

### SLIDE 22: Sri Charani - Contributions

**Feature Ownership:** Payments & Wallets (Transactions)
**Service:** wallet-service (Port 8084)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Wallet Controller, Wallet Service | WalletController.java, WalletService.java |
| Backend | Transaction Controller, Service | TransactionController.java, TransactionService.java |
| Backend | Money Request Controller, Service | RequestController.java, RequestService.java |
| Backend | Feign Clients for Auth, Card, Notification | Service clients |
| Frontend | Wallet Dashboard, Send Money, Requests | wallet/, transactions/, requests/ modules |
| Database | wallets, transactions, money_requests tables | Entity design |

##### Code Highlight

```java
// Send Money with PIN Verification and Notifications
@Transactional
public TransactionResponse sendMoney(SendMoneyRequest request, Long senderId) {
    // Verify PIN
    PinVerifyResponse pinResponse = authServiceClient.verifyPin(
        PinVerifyRequest.builder().pin(request.getPin()).build(),
        senderId
    );

    if (!pinResponse.isValid()) {
        throw new InvalidCredentialsException("Invalid transaction PIN");
    }

    // Get wallets
    Wallet senderWallet = walletRepository.findByUserId(senderId)
        .orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));

    Wallet receiverWallet = walletRepository.findByUserId(request.getReceiverId())
        .orElseThrow(() -> new ResourceNotFoundException("Receiver wallet not found"));

    // Check balance
    if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
        throw new InsufficientFundsException("Insufficient balance");
    }

    // Transfer funds
    senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));
    receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

    walletRepository.save(senderWallet);
    walletRepository.save(receiverWallet);

    // Create transaction records
    Transaction senderTx = createTransaction(
        senderWallet, receiverWallet, request.getAmount(),
        TransactionType.SEND, request.getDescription()
    );

    Transaction receiverTx = createTransaction(
        receiverWallet, senderWallet, request.getAmount(),
        TransactionType.RECEIVE, request.getDescription()
    );

    // Send notifications (async, don't fail transaction)
    try {
        notificationServiceClient.createNotification(buildSendNotification(senderTx));
        notificationServiceClient.createNotification(buildReceiveNotification(receiverTx));
    } catch (Exception e) {
        log.warn("Failed to send notification", e);
    }

    return toResponse(senderTx);
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
| Coordinating 5 microservices | Integration complexity | Clear API contracts, Feign clients |
| JWT token consistency | Auth failures across services | Shared JWT secret, API Gateway validation |
| Transaction atomicity | Data inconsistency risk | Spring @Transactional, careful error handling |
| Feign client failures | Cascading failures | Async notifications, graceful degradation |
| [TEAM_CHALLENGE_1] | [IMPACT] | [SOLUTION] |

#### Collective Learnings

| Area | What We Learned as a Team |
|------|---------------------------|
| **Technical** | Full-stack development, microservices patterns, AES encryption |
| **Architecture** | Service discovery, API Gateway, inter-service communication |
| **Security** | JWT implementation, PIN verification, account lockout |
| **Collaboration** | Git workflow, code reviews, API contract design |
| **DevOps** | Docker Compose, health checks, service dependencies |

---

### SLIDE 24: Q&A (5-10 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                       QUESTIONS?                            │
│                                                             │
│                                                             │
│     GitHub Phase 1:                                         │
│     https://github.com/dileep-kumar-ch/revpay-ph2           │
│                                                             │
│     GitHub Phase 2:                                         │
│     https://github.com/vivekgonuguntla/revpay_p3            │
│                                                             │
│     Live Demo: [AWS_URL]                                    │
│                                                             │
│     Individual GitHub Contributions:                        │
│     • Nikhil: [X commits, Y PRs]                            │
│     • Vivek: [X commits, Y PRs]                             │
│     • Dileep: [X commits, Y PRs]                            │
│     • Koushik: [X commits, Y PRs]                           │
│     • Sri Charani: [X commits, Y PRs]                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Q&A Tips:**
- Any team member can answer questions about their feature area
- Be ready to explain transaction PIN flow and account lockout
- Reference specific code/commits when explaining technical decisions
- Be ready to demonstrate inter-service communication (Feign clients)

---

## NOTES FOR PRESENTERS

### Information Not Available (To Be Filled)

The following information could not be determined from the provided repositories and should be filled in by the team:

1. **Batch ID and Presentation Date** - Slide 1
2. **Individual Challenges & Solutions** - Slides 18-22 (each team member should fill their specific challenges)
3. **Individual Key Learnings** - Slides 18-22
4. **Exact Git Commit Counts** - Slide 24
5. **AWS Deployment URL** - Slide 24
6. **Frontend Test Coverage Details** - Slide 13
7. **Additional Team Challenges** - Slide 23

### Demo Preparation Checklist

- [ ] MySQL database running
- [ ] Phase 1: Backend running on port 8080
- [ ] Phase 1: Frontend running on port 4200
- [ ] Phase 2: Docker Compose services running
- [ ] Eureka dashboard accessible at :8761
- [ ] Test users seeded (admin, personal, business)
- [ ] AWS deployment active (if applicable)
- [ ] Backup screenshots/video prepared

### Phase 2 Microservices Ports Reference

| Service | Port | Owner | Purpose |
|---------|------|-------|---------|
| Config Server | 8888 | - | Centralized configuration |
| Discovery Server | 8761 | - | Eureka service registry |
| API Gateway | 8080 | - | Request routing, JWT validation |
| Auth Service | 8081 | Koushik | Authentication, PIN |
| Card Service | 8082 | Vivek | Card management |
| Notification Service | 8083 | Dileep | Notifications |
| Wallet Service | 8084 | Sri Charani | Wallet, transactions, requests |
| Business Service | 8085 | Nikhil | Business operations |

### Presentation Tips

1. Each member should practice their 1-2 minute contribution section
2. Prepare to explain PIN verification and account lockout
3. Be ready to show Eureka dashboard for service discovery
4. Demonstrate inter-service communication with Feign clients
5. Know your commit count and key PRs
