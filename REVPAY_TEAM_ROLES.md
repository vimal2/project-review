# RevPay Team Roles and Responsibilities

## Project Overview

**RevPay** is a comprehensive digital payment platform that evolved from a monolithic architecture (P2) to a distributed microservices architecture (P3). This document outlines team member responsibilities across both phases.

---

## Team Overview

| Developer | Module | Service | Port | P2 Monolith | P3 Microservice |
|-----------|--------|---------|------|-------------|-----------------|
| **Koushik** | Authentication & Infrastructure | auth-service | 8081 | Login, Registration, Security | Auth Service + Infrastructure Setup |
| **Vivek** | Card Management | card-service | 8082 | Card Operations | Card Service |
| **Dileep** | Notifications | notification-service | 8083 | Notification System | Notification Service |
| **Sri Charani** | Payments & Wallets | wallet-service | 8084 | Transactions, Wallet, Money Requests | Wallet & Transaction Service |
| **Nikhil** | Business Operations | business-service | 8085 | Business Profiles, Invoices, Loans | Business Service |

---

## Technology Stack

### P2 - Monolithic Architecture
- **Backend**: Spring Boot 3.1.5, Spring Security, Spring Data JPA
- **Frontend**: Angular, TypeScript, RxJS
- **Database**: MySQL 8.0 (Single Database: `revpay_dev`)
- **Authentication**: JWT (JSON Web Tokens)
- **Build Tool**: Maven
- **API Architecture**: RESTful Monolith

### P3 - Microservices Architecture
- **Backend**: Spring Boot 3.2.0, Spring Cloud 2023.0.0
- **Service Discovery**: Eureka Server (Port 8761)
- **API Gateway**: Spring Cloud Gateway (Port 8080)
- **Config Server**: Spring Cloud Config (Port 8888)
- **Database**: MySQL 8.0 (Separate databases per service)
- **Authentication**: JWT with Gateway-level validation
- **Inter-Service Communication**: OpenFeign
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

---

## Developer Responsibilities

### 1. Koushik - Authentication & Infrastructure

#### P2 Responsibilities (Monolithic Backend)

**Controllers:**
- `AuthController.java` - `/api/auth`
  - POST `/register` - User registration
  - POST `/login` - User authentication
  - GET `/recovery/questions` - Retrieve security questions
  - POST `/recovery/reset` - Password reset

- `SecurityController.java` - `/api/v1/security`
  - POST `/pin/setup` - Setup transaction PIN
  - POST `/pin/verify` - Verify transaction PIN
  - POST `/pin/reset` - Reset transaction PIN

**Services:**
- `AuthService.java` - Authentication logic, user registration, password recovery
- `JwtService.java` - JWT token generation and validation
- `EncryptionService.java` - Password and PIN encryption/decryption

**Entities:**
- `User.java` - User entity with authentication details
- `Role.java` - User roles (PERSONAL, BUSINESS, ADMIN)
- `SecurityQuestion.java` - Security questions for password recovery

**DTOs:**
- `RegisterRequest.java`, `AuthRequest.java`, `AuthResponse.java`
- `SecurityQuestionDto.java`

---

#### P3 Responsibilities (Auth Microservice)

**Service**: `auth-service` (Port: 8081)
**Database**: `revpay_auth`

**Controllers:**
- `AuthController.java` - `/api/auth`
  - POST `/register` - User registration
  - POST `/login` - User authentication
  - GET `/recovery/questions` - Security question retrieval
  - POST `/recovery/reset` - Password reset
  - GET `/validate` - Token validation endpoint for other services

- `SecurityController.java` - `/api/v1/security`
  - POST `/pin/setup` - Transaction PIN setup
  - POST `/pin/verify` - PIN verification
  - POST `/pin/reset` - PIN reset

**Services:**
- `AuthService.java` - Core authentication and user management
- `JwtService.java` - JWT token operations
- `PinService.java` - Transaction PIN management

**Entities:**
- `User.java` - User authentication entity
- `Role.java` - Role enumeration
- `SecurityQuestion.java` - Security questions entity

**DTOs:**
- `RegisterRequest.java`, `AuthRequest.java`, `AuthResponse.java`
- `PinSetupRequest.java`, `PinVerifyRequest.java`, `PinResetRequest.java`
- `ForgotPasswordRequest.java`, `ResetPasswordRequest.java`
- `UserValidationResponse.java`
- `SecurityQuestionDto.java`

**Configuration:**
- `JwtAuthenticationFilter.java` - JWT filter for request authentication
- `SecurityConfig.java` - Security configuration
- `DataLoader.java` - Initial data loading

**Infrastructure Responsibilities:**
- Setup and configure Eureka Discovery Server (Port 8761)
- Setup and configure Config Server (Port 8888)
- Setup and configure API Gateway (Port 8080)
- Configure Docker Compose for all services
- Manage service registration and discovery
- Implement gateway routing and JWT validation

---

### 2. Vivek - Card Management

#### P2 Responsibilities (Monolithic Backend)

**Controllers:**
- `CardController.java` - `/api/v1/cards`
  - POST `/` - Add new card
  - GET `/` - Get all user cards
  - DELETE `/{id}` - Delete card
  - PATCH `/{id}/default` - Set default card

**Services:**
- `CardService.java` - Card management operations
- `EncryptionService.java` - Card number encryption (shared service)

**Entities:**
- `Card.java` - Card entity with encrypted card numbers

**DTOs:**
- `AddCardRequest.java` - Card addition request
- `CardResponse.java` - Card response (masked card number)

---

#### P3 Responsibilities (Card Microservice)

**Service**: `card-service` (Port: 8082)
**Database**: `revpay_card_service`

**Controllers:**
- `CardController.java` - `/api/v1/cards`
  - POST `/` - Add new card
  - GET `/` - Retrieve user cards
  - DELETE `/{id}` - Remove card
  - PATCH `/{id}/default` - Set default payment card

**Services:**
- `CardService.java` (Interface) - Card operations contract
- `CardServiceImpl.java` - Implementation of card operations
- `EncryptionService.java` - Card data encryption

**Entities:**
- `Card.java` - Card entity
- `CardType.java` - Enum (CREDIT, DEBIT)
- `PaymentMethodType.java` - Payment method types

**DTOs:**
- `AddCardRequest.java` - Add card request DTO
- `CardResponse.java` - Card response with masked details
- `ErrorResponse.java` - Error handling DTO

**Feign Clients:**
- `NotificationServiceClient.java` - Integration with notification service

**Configuration:**
- `JwtAuthenticationFilter.java` - JWT validation
- `JwtService.java` - JWT parsing
- `SecurityConfig.java` - Security configuration
- `FeignConfig.java` - Feign client configuration

**Exception Handling:**
- `CardLimitExceededException.java`
- `ResourceNotFoundException.java`
- `GlobalExceptionHandler.java`

---

### 3. Dileep - Notifications

#### P2 Responsibilities (Monolithic Backend)

**Controllers:**
- `NotificationController.java` - `/api/v1/notifications`
  - GET `/` - Get notifications (with filters)
  - GET `/unread-count` - Get unread notification count
  - PATCH `/{id}/read` - Mark single notification as read
  - PATCH `/read-all` - Mark all notifications as read
  - GET `/preferences` - Get notification preferences
  - PUT `/preferences` - Update notification preferences

**Services:**
- `NotificationService.java` - Notification CRUD operations
- `NotificationEventPublisher.java` - Event publishing interface
- `LoggingNotificationEventPublisher.java` - Event publisher implementation

**Entities:**
- `Notification.java` - Notification entity
- `NotificationPreference.java` - User notification preferences
- `NotificationCategory.java` - Enum (REQUESTS, TRANSACTIONS, ALERTS, SYSTEM)

**DTOs:**
- `NotificationResponse.java` - Notification details
- `NotificationEvent.java` - Event data structure
- `NotificationPreferenceResponse.java`
- `UpdateNotificationPreferenceRequest.java`

---

#### P3 Responsibilities (Notification Microservice)

**Service**: `notification-service` (Port: 8083)
**Database**: `revpay_notifications`

**Controllers:**
- `NotificationController.java` - `/api/v1/notifications`
  - GET `/` - Fetch notifications with filtering
  - GET `/unread-count` - Get unread count
  - PATCH `/{id}/read` - Mark as read
  - PATCH `/read-all` - Mark all as read
  - GET `/preferences` - Retrieve notification preferences
  - PUT `/preferences` - Update preferences

- `InternalController.java` - Internal API for other services
  - POST `/internal/notifications/send` - Send notification (called by other services)

**Services:**
- `NotificationService.java` (Interface)
- `NotificationServiceImpl.java` - Notification management implementation
- `NotificationPreferenceService.java` (Interface)
- `NotificationPreferenceServiceImpl.java` - Preference management

**Entities:**
- `Notification.java` - Notification entity
- `NotificationPreference.java` - User preferences
- `NotificationCategory.java` - Category enumeration

**DTOs:**
- `NotificationRequest.java` - Internal notification creation
- `NotificationResponse.java` - Notification data
- `UnreadCountResponse.java` - Unread count wrapper
- `NotificationPreferenceResponse.java`
- `UpdateNotificationPreferenceRequest.java`
- `ErrorResponse.java`

**Configuration:**
- `JwtAuthenticationFilter.java` - JWT validation
- `SecurityConfig.java` - Security setup
- `DataLoader.java` - Default data initialization

**Utilities:**
- `JwtUtil.java` - JWT extraction and parsing

**Exception Handling:**
- `ResourceNotFoundException.java`
- `GlobalExceptionHandler.java`

---

### 4. Sri Charani - Payments & Wallets (Transactions)

#### P2 Responsibilities (Monolithic Backend)

**Controllers:**
- `WalletController.java` - `/api/v1/wallet`
  - GET `/balance` - Get wallet balance
  - POST `/add-funds` - Add money to wallet
  - POST `/withdraw` - Withdraw from wallet

- `TransactionController.java` - `/api/v1/transactions`
  - GET `/` - Get transaction history
  - POST `/send` - Send money to another user

- `MoneyRequestController.java` - `/api/v1/requests`
  - POST `/create` - Create money request
  - GET `/` - Get all money requests
  - POST `/{requestId}/respond` - Accept/reject money request

**Services:**
- `WalletService.java` - Wallet operations
- `TransactionService.java` - Transaction processing
- `MoneyRequestService.java` - Money request lifecycle management

**Entities:**
- `Wallet.java` - User wallet entity
- `Transaction.java` - Transaction records
- `TransactionType.java` - Enum (SEND, RECEIVE, ADD_FUNDS, WITHDRAW)
- `TransactionStatus.java` - Enum (PENDING, SUCCESS, FAILED)
- `MoneyRequest.java` - Money request entity
- `RequestStatus.java` - Enum (PENDING, ACCEPTED, REJECTED)

**DTOs:**
- `WalletResponse.java`, `WalletOperationRequest.java`
- `TransactionResponse.java`
- `CreateMoneyRequestDto.java`, `MoneyRequestResponse.java`

---

#### P3 Responsibilities (Wallet Microservice)

**Service**: `wallet-service` (Port: 8084)
**Database**: `revpay_wallet`

**Controllers:**
- `WalletController.java` - `/api/v1/wallet`
  - GET `/balance` - Retrieve wallet balance
  - POST `/add-funds` - Add funds to wallet
  - POST `/withdraw` - Withdraw funds

- `TransactionController.java` - `/api/v1/transactions`
  - GET `/` - Transaction history
  - POST `/send` - Send money transaction

- `MoneyRequestController.java` - `/api/v1/requests`
  - POST `/create` - Create money request
  - GET `/` - List money requests
  - POST `/{requestId}/respond` - Respond to request

**Services:**
- `WalletService.java` - Wallet management
- `TransactionService.java` - Transaction processing
- `MoneyRequestService.java` - Money request operations

**Entities:**
- `Wallet.java` - Wallet entity
- `Transaction.java` - Transaction record
- `TransactionType.java` - Transaction types
- `TransactionStatus.java` - Transaction statuses
- `MoneyRequest.java` - Money request entity
- `RequestStatus.java` - Request statuses

**DTOs:**
- `WalletResponse.java`, `WalletOperationRequest.java`
- `SendMoneyRequest.java`
- `TransactionResponse.java`, `TransactionHistoryResponse.java`
- `CreateMoneyRequestDto.java`, `RespondToRequestDto.java`
- `MoneyRequestResponse.java`
- `ErrorResponse.java`

**Feign Clients:**
- `AuthServiceClient.java` - User validation
- `CardServiceClient.java` - Card verification for fund additions
- `NotificationServiceClient.java` - Send transaction notifications

**Configuration:**
- `SecurityConfig.java` - Security configuration
- `FeignConfig.java` - Feign client setup

**Exception Handling:**
- `InsufficientFundsException.java`
- `InvalidPinException.java`
- `GlobalExceptionHandler.java`

---

### 5. Nikhil - Business Operations

#### P2 Responsibilities (Monolithic Backend)

**Controllers:**
- `BusinessProfileController.java` - `/api/v1/business/profile`
  - POST `/` - Create/update business profile
  - GET `/` - Get business profile
  - PATCH `/verification` - Update verification status

- `BusinessInvoiceController.java` - `/api/v1/business/invoices`
  - POST `/` - Create invoice
  - GET `/` - List invoices
  - GET `/{id}` - Get invoice details
  - POST `/{id}/pay` - Pay invoice

- `BusinessLoanController.java` - `/api/v1/business/loans`
  - POST `/apply` - Apply for business loan
  - GET `/` - List loans
  - POST `/{id}/decision` - Approve/reject loan

- `BusinessAnalyticsController.java` - `/api/v1/business/analytics`
  - GET `/` - Get business analytics

- `BusinessPaymentMethodController.java` - `/api/v1/business/payment-methods`
  - POST `/bank-accounts` - Add bank account
  - GET `/bank-accounts` - List bank accounts

**Services:**
- `BusinessProfileService.java` - Business profile management
- `BusinessInvoiceService.java` - Invoice operations
- `BusinessLoanService.java` - Loan processing
- `BusinessAnalyticsService.java` - Analytics generation
- `BusinessPaymentMethodService.java` - Payment method management

**Entities:**
- `BusinessBankAccount.java` - Business bank accounts
- `BusinessLoan.java` - Loan entity
- `BusinessVerificationStatus.java` - Enum (PENDING, VERIFIED, REJECTED)
- `Invoice.java`, `InvoiceItem.java` - Invoice entities
- `InvoiceStatus.java` - Enum (DRAFT, SENT, PAID, CANCELLED)
- `LoanStatus.java` - Enum (PENDING, APPROVED, REJECTED, ACTIVE)
- `LoanRepayment.java` - Loan repayment records
- `RepaymentStatus.java` - Repayment status enum

**DTOs:**
- `BusinessProfileResponse.java`
- `BusinessVerificationDecisionRequest.java`, `BusinessVerificationUpdateRequest.java`
- `CreateInvoiceRequest.java`, `InvoiceResponse.java`, `InvoiceItemRequest.java`, `InvoiceItemResponse.java`
- `InvoicePaymentRequest.java`
- `LoanApplicationRequest.java`, `LoanResponse.java`, `LoanDecisionRequest.java`
- `BusinessAnalyticsResponse.java`
- `BankAccountRequest.java`, `BankAccountResponse.java`

---

#### P3 Responsibilities (Business Microservice)

**Service**: `business-service` (Port: 8085)
**Database**: `revpay_business`

**Controllers:**
- `BusinessProfileController.java` - `/api/business/profile`
  - GET `/user/{userId}` - Get profile by user ID
  - POST `/user/{userId}` - Create/update profile
  - PUT `/{profileId}/status` - Update verification status

- `InvoiceController.java` - `/api/business/invoices`
  - POST `/` - Create invoice
  - GET `/user/{userId}` - Get user invoices
  - GET `/{id}` - Get invoice by ID
  - POST `/{id}/pay` - Pay invoice
  - DELETE `/{id}` - Delete invoice

- `LoanController.java` - `/api/business/loans`
  - POST `/apply` - Apply for loan
  - GET `/user/{userId}` - Get user loans
  - GET `/{id}` - Get loan details
  - POST `/{id}/repay` - Make loan repayment

- `AnalyticsController.java` - `/api/business/analytics`
  - GET `/user/{userId}` - Get business analytics

- `BankAccountController.java` - `/api/business/bank-accounts`
  - POST `/` - Add bank account
  - GET `/user/{userId}` - Get user bank accounts
  - DELETE `/{id}` - Remove bank account

**Services:**
- `BusinessProfileService.java` - Profile management
- `InvoiceService.java` - Invoice operations
- `LoanService.java` - Loan processing
- `AnalyticsService.java` - Analytics generation
- `BankAccountService.java` - Bank account management

**Entities:**
- `BusinessProfile.java` - Business profile
- `BusinessVerificationStatus.java` - Verification status enum
- `Invoice.java`, `InvoiceItem.java` - Invoice entities
- `InvoiceStatus.java` - Invoice status enum
- `BusinessLoan.java` - Loan entity
- `LoanStatus.java` - Loan status enum
- `LoanRepayment.java` - Repayment entity
- `RepaymentStatus.java` - Repayment status enum
- `BusinessBankAccount.java` - Bank account entity

**DTOs:**
- `BusinessProfileResponse.java`, `BusinessVerificationRequest.java`
- `InvoiceRequest.java`, `InvoiceResponse.java`
- `InvoiceItemRequest.java`, `InvoiceItemResponse.java`
- `InvoicePaymentRequest.java`
- `LoanApplicationRequest.java`, `LoanResponse.java`
- `LoanRepaymentResponse.java`
- `BusinessAnalyticsResponse.java`
- `BankAccountRequest.java`, `BankAccountResponse.java`

**Feign Clients:**
- `WalletServiceClient.java` - Integration for invoice payments
- `NotificationServiceClient.java` - Send business notifications

**Configuration:**
- `SecurityConfig.java` - Security configuration
- `FeignConfig.java` - Feign client setup

**Exception Handling:**
- `BusinessException.java`
- `ResourceNotFoundException.java`
- `UnauthorizedException.java`
- `GlobalExceptionHandler.java`

---

## Service Dependencies Diagram

```
                                    ┌────────────────┐
                                    │                │
                                    │   API Gateway  │
                                    │   (Port 8080)  │
                                    │                │
                                    └───────┬────────┘
                                            │
                    ┌───────────────────────┼───────────────────────┐
                    │                       │                       │
         ┌──────────▼─────────┐  ┌─────────▼──────────┐  ┌────────▼─────────┐
         │                    │  │                     │  │                  │
         │  Discovery Server  │  │   Config Server     │  │  MySQL Databases │
         │  (Eureka - 8761)   │  │   (Port 8888)       │  │                  │
         │                    │  │                     │  │                  │
         └──────────┬─────────┘  └─────────────────────┘  └──────────────────┘
                    │
    ┌───────────────┼───────────────┬───────────────┬───────────────┐
    │               │               │               │               │
┌───▼────┐    ┌─────▼──────┐  ┌────▼─────┐   ┌─────▼──────┐  ┌────▼─────────┐
│        │    │            │  │          │   │            │  │              │
│  Auth  │◄───┤   Wallet   │  │   Card   │   │Notification│  │   Business   │
│ (8081) │    │  (8084)    │  │  (8082)  │   │   (8083)   │  │    (8085)    │
│        │    │            │  │          │   │            │  │              │
└────────┘    └────┬───────┘  └────┬─────┘   └─────▲──────┘  └──────┬───────┘
                   │               │               │                │
                   │               │               │                │
                   └───────────────┴───────────────┴────────────────┘
                            (Feign Client Calls)


Service Communication:
━━━━━━━━━━━━━━━━━━━━

Wallet Service → Auth Service (User validation)
Wallet Service → Card Service (Card verification for add funds)
Wallet Service → Notification Service (Transaction notifications)

Card Service → Notification Service (Card operation notifications)

Business Service → Wallet Service (Invoice payments)
Business Service → Notification Service (Business notifications)

All Services → Discovery Server (Service registration)
All Services → Config Server (Configuration)
API Gateway → All Services (Request routing with JWT validation)
```

---

## Port Assignments

| Component | Port | Description | Database |
|-----------|------|-------------|----------|
| **Config Server** | 8888 | Centralized configuration management | N/A |
| **Discovery Server** | 8761 | Eureka service registry | N/A |
| **API Gateway** | 8080 | Single entry point, JWT validation, routing | N/A |
| **Auth Service** | 8081 | Authentication, registration, security | `revpay_auth` |
| **Card Service** | 8082 | Card management operations | `revpay_card_service` |
| **Notification Service** | 8083 | Notification delivery and preferences | `revpay_notifications` |
| **Wallet Service** | 8084 | Wallet, transactions, money requests | `revpay_wallet` |
| **Business Service** | 8085 | Business profiles, invoices, loans | `revpay_business` |

---

## API Gateway Routing Configuration

| Route Pattern | Target Service | Authentication |
|---------------|----------------|----------------|
| `/api/auth/**` | auth-service | Public |
| `/api/v1/security/**` | auth-service | Required |
| `/api/v1/cards/**` | card-service | Required |
| `/api/v1/notifications/**` | notification-service | Required |
| `/api/v1/wallet/**` | wallet-service | Required |
| `/api/v1/transactions/**` | wallet-service | Required |
| `/api/v1/requests/**` | wallet-service | Required |
| `/api/business/**` | business-service | Required |

---

## Database Schema Distribution (P3)

### revpay_auth (Auth Service - Koushik)
- users
- roles
- security_questions

### revpay_card_service (Card Service - Vivek)
- cards

### revpay_notifications (Notification Service - Dileep)
- notifications
- notification_preferences

### revpay_wallet (Wallet Service - Sri Charani)
- wallets
- transactions
- money_requests

### revpay_business (Business Service - Nikhil)
- business_profiles
- business_bank_accounts
- invoices
- invoice_items
- business_loans
- loan_repayments

---

## Key Architectural Changes: P2 → P3

### Monolith to Microservices
- **P2**: Single Spring Boot application with shared database
- **P3**: Independent microservices with dedicated databases

### Authentication
- **P2**: Session-based authentication with Spring Security
- **P3**: JWT validation at API Gateway with header propagation (X-User-Id, X-User-Email, X-User-Role)

### Service Discovery
- **P2**: Direct HTTP calls (not applicable)
- **P3**: Eureka-based service discovery

### Configuration
- **P2**: Local application.properties
- **P3**: Centralized Spring Cloud Config Server

### Inter-Service Communication
- **P2**: Direct method calls (monolith)
- **P3**: OpenFeign REST clients

### Deployment
- **P2**: Single WAR/JAR deployment
- **P3**: Docker Compose orchestration with multiple containers

---

## Testing Strategy

### P2 Testing
- Unit tests for services
- Integration tests for controllers
- End-to-end tests through Angular frontend

### P3 Testing
- Unit tests per microservice
- Contract testing between services
- API Gateway integration tests
- Service discovery tests
- Feign client tests

---

## Development Workflow

### Local Development Setup
1. Start MySQL
2. Start Config Server (8888)
3. Start Discovery Server (8761)
4. Start API Gateway (8080)
5. Start Business Services (8081-8085)

### Docker Development Setup
```bash
cd p3-revpay
docker-compose up -d
```

---

## Common Development Tasks

### Adding New Endpoint
1. Create DTO classes
2. Add repository method (if needed)
3. Implement service logic
4. Create controller endpoint
5. Update API documentation

### Adding New Microservice
1. Create new Maven module
2. Add Eureka client dependency
3. Configure application.yml (service name, port, database)
4. Register routes in API Gateway
5. Add to docker-compose.yml
6. Update parent pom.xml

---

## Security Considerations

### JWT Configuration
- **Secret**: 256-bit shared secret across all services
- **Expiration**: 24 hours (86400000 ms)
- **Header Format**: `Authorization: Bearer <token>`

### Encryption
- **Card Numbers**: AES encryption in card-service
- **Passwords**: BCrypt hashing in auth-service
- **PINs**: Encrypted storage with verification

### CORS Configuration
API Gateway allows:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)
- `http://localhost:5173` (Vite)

---

## Monitoring and Health Checks

All services expose Spring Boot Actuator endpoints:
- `/actuator/health` - Service health status
- `/actuator/info` - Service information
- `/actuator/metrics` - Service metrics

Discovery Server Dashboard: `http://localhost:8761`

---

## Troubleshooting Guide

### Service Not Registering
- Check Eureka server is running (8761)
- Verify `eureka.client.service-url.defaultZone` in application.yml
- Check network connectivity

### Database Connection Failures
- Verify MySQL is running
- Check database credentials in application.yml
- Ensure database exists (or `createDatabaseIfNotExist=true`)

### JWT Authentication Issues
- Verify JWT secret matches across services
- Check token expiration
- Validate Authorization header format

### Feign Client Errors
- Check target service is registered with Eureka
- Verify service names in Feign client annotations
- Check network connectivity between services

---

## Project Repositories

- **P2 Monolith**: `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/revpay-ph2`
- **P3 Microservices**: `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revpay`

---

## Documentation References

- P2 README: `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/revpay-ph2/README.md`
- P3 README: `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revpay/README.md`
- Docker Compose: `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revpay/docker-compose.yml`

---

**Document Version**: 1.0
**Last Updated**: March 5, 2026
**Maintained By**: Koushik (Infrastructure Lead)
