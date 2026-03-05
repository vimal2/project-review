# RevPay Developer Commit Instructions

## Overview
This document provides step-by-step commit instructions for the RevPay microservices project. The strategy uses a single shared mono-repository with feature-based commits (5-10 per developer).

**Repository Path:** `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revpay`

---

## Team Commit Order

1. **Koushik** - Infrastructure (6 commits) + auth-service (8 commits) = **14 commits total**
2. **Vivek** - card-service (8 commits)
3. **Dileep** - notification-service (8 commits)
4. **Sri Charani** - wallet-service (8 commits)
5. **Nikhil** - business-service (8 commits)

---

## Pre-Requisites (All Developers)

Before starting, each developer must configure their Git identity:

```bash
# Set your name and email
git config user.name "Your Full Name"
git config user.email "your.email@example.com"

# Verify configuration
git config user.name
git config user.email

# Navigate to repository
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revpay

# Verify you're in the correct repository
pwd
```

---

## Developer 1: Koushik (Infrastructure + Auth Service)

### Phase 1: Infrastructure Setup (6 commits)

#### Commit 1: Initialize root project structure and parent POM

```bash
git add pom.xml
git add .gitignore
git add README.md
git add docker-compose.yml

git commit -m "$(cat <<'EOF'
Initialize RevPay microservices project with parent POM

- Add parent POM with Spring Boot 3.2.0 and Spring Cloud 2023.0.0
- Configure Maven multi-module project structure
- Add .gitignore for Java/Maven projects
- Add docker-compose.yml for local development
- Add project README with architecture overview

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Setup Config Server

```bash
git add config-server/pom.xml
git add config-server/src/main/resources/application.yml
git add config-server/src/main/java/com/revpay/configserver/ConfigServerApplication.java

git commit -m "$(cat <<'EOF'
Add Config Server for centralized configuration management

- Configure Spring Cloud Config Server
- Add application.yml with server configuration
- Setup ConfigServerApplication main class
- Enable @EnableConfigServer annotation

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Setup Discovery Server (Eureka)

```bash
git add discovery-server/pom.xml
git add discovery-server/src/main/resources/application.yml
git add discovery-server/src/main/java/com/revpay/discoveryserver/DiscoveryServerApplication.java

git commit -m "$(cat <<'EOF'
Add Eureka Discovery Server for service registration

- Configure Netflix Eureka Server
- Add application.yml with Eureka configuration
- Setup DiscoveryServerApplication main class
- Enable @EnableEurekaServer annotation
- Configure self-preservation and renewal thresholds

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Setup API Gateway - Core Configuration

```bash
git add api-gateway/pom.xml
git add api-gateway/src/main/resources/application.yml
git add api-gateway/src/main/java/com/revpay/gateway/ApiGatewayApplication.java

git commit -m "$(cat <<'EOF'
Add API Gateway with Spring Cloud Gateway

- Configure Spring Cloud Gateway dependencies
- Add application.yml with gateway routes configuration
- Setup ApiGatewayApplication main class
- Configure routes for all microservices
- Add Eureka client integration

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Add API Gateway filters and CORS configuration

```bash
git add api-gateway/src/main/java/com/revpay/gateway/filter/JwtAuthenticationFilter.java
git add api-gateway/src/main/java/com/revpay/gateway/config/CorsConfig.java

git commit -m "$(cat <<'EOF'
Add JWT authentication filter and CORS configuration to API Gateway

- Implement JwtAuthenticationFilter for token validation
- Add CorsConfig for cross-origin resource sharing
- Configure allowed origins, methods, and headers
- Add request/response logging in filter

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 6: Add API Gateway route configuration

```bash
git add api-gateway/src/main/java/com/revpay/gateway/config/RouteConfig.java

git commit -m "$(cat <<'EOF'
Add programmatic route configuration for API Gateway

- Implement RouteConfig for dynamic route management
- Configure predicates and filters for each service
- Add custom gateway filter factories
- Setup load balancing configuration

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

### Phase 2: Auth Service (8 commits)

#### Commit 7: Initialize Auth Service with basic setup

```bash
git add auth-service/pom.xml
git add auth-service/src/main/resources/application.yml
git add auth-service/src/main/java/com/revpay/auth/AuthServiceApplication.java

git commit -m "$(cat <<'EOF'
Initialize Auth Service with Spring Boot configuration

- Add auth-service module with Spring Security dependencies
- Configure application.yml with database and JWT settings
- Setup AuthServiceApplication main class
- Add Eureka client configuration
- Configure PostgreSQL data source

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add Auth Service domain entities and enums

```bash
git add auth-service/src/main/java/com/revpay/auth/entity/User.java
git add auth-service/src/main/java/com/revpay/auth/entity/Role.java
git add auth-service/src/main/java/com/revpay/auth/entity/SecurityQuestion.java

git commit -m "$(cat <<'EOF'
Add User, Role, and SecurityQuestion entities for authentication

- Create User entity with JPA annotations
- Add Role enum (CUSTOMER, BUSINESS, ADMIN)
- Create SecurityQuestion entity for password recovery
- Configure entity relationships and constraints
- Add account locking and PIN management fields

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 9: Add Auth Service DTOs

```bash
git add auth-service/src/main/java/com/revpay/auth/dto/AuthRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/AuthResponse.java
git add auth-service/src/main/java/com/revpay/auth/dto/RegisterRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/ForgotPasswordRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/ResetPasswordRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/PinSetupRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/PinVerifyRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/PinResetRequest.java
git add auth-service/src/main/java/com/revpay/auth/dto/SecurityQuestionDto.java
git add auth-service/src/main/java/com/revpay/auth/dto/UserValidationResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for authentication and user management

- Create AuthRequest and AuthResponse for login
- Add RegisterRequest for user registration
- Implement password recovery DTOs (ForgotPasswordRequest, ResetPasswordRequest)
- Add PIN management DTOs (PinSetupRequest, PinVerifyRequest, PinResetRequest)
- Create SecurityQuestionDto for security questions
- Add UserValidationResponse for inter-service communication

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 10: Add Auth Service repositories

```bash
git add auth-service/src/main/java/com/revpay/auth/repository/UserRepository.java
git add auth-service/src/main/java/com/revpay/auth/repository/SecurityQuestionRepository.java

git commit -m "$(cat <<'EOF'
Add JPA repositories for User and SecurityQuestion entities

- Create UserRepository with custom query methods
- Add SecurityQuestionRepository for security questions
- Implement findByEmail, findByEmailAndIsActiveTrue queries
- Add existsByEmail for duplicate checking
- Configure repository annotations

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 11: Add Auth Service business logic

```bash
git add auth-service/src/main/java/com/revpay/auth/service/JwtService.java
git add auth-service/src/main/java/com/revpay/auth/service/AuthService.java
git add auth-service/src/main/java/com/revpay/auth/service/PinService.java

git commit -m "$(cat <<'EOF'
Implement authentication business logic services

- Add JwtService for JWT token generation and validation
- Implement AuthService with login, register, and password reset
- Create PinService for PIN setup, verification, and reset
- Add account locking mechanism after failed attempts
- Implement security question validation
- Add BCrypt password encoding

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 12: Add Auth Service REST controllers

```bash
git add auth-service/src/main/java/com/revpay/auth/controller/AuthController.java
git add auth-service/src/main/java/com/revpay/auth/controller/SecurityController.java

git commit -m "$(cat <<'EOF'
Add REST controllers for authentication endpoints

- Create AuthController with login, register, and password management
- Add SecurityController for PIN and security operations
- Implement /auth/login, /auth/register endpoints
- Add /auth/forgot-password and /auth/reset-password endpoints
- Implement PIN management endpoints
- Add user validation endpoint for inter-service calls

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 13: Add Auth Service security configuration

```bash
git add auth-service/src/main/java/com/revpay/auth/config/SecurityConfig.java
git add auth-service/src/main/java/com/revpay/auth/config/JwtAuthenticationFilter.java

git commit -m "$(cat <<'EOF'
Configure Spring Security for Auth Service

- Implement SecurityConfig with JWT authentication
- Add JwtAuthenticationFilter for request authentication
- Configure public and protected endpoints
- Setup password encoder bean
- Add CORS configuration
- Configure security filter chain

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 14: Add Auth Service exception handling and data loader

```bash
git add auth-service/src/main/java/com/revpay/auth/exception/GlobalExceptionHandler.java
git add auth-service/src/main/java/com/revpay/auth/exception/InvalidCredentialsException.java
git add auth-service/src/main/java/com/revpay/auth/exception/DuplicateEmailException.java
git add auth-service/src/main/java/com/revpay/auth/exception/AccountLockedException.java
git add auth-service/src/main/java/com/revpay/auth/config/DataLoader.java

git commit -m "$(cat <<'EOF'
Add exception handling and initial data loader

- Implement GlobalExceptionHandler for centralized error handling
- Create custom exceptions (InvalidCredentialsException, DuplicateEmailException, AccountLockedException)
- Add DataLoader to populate security questions on startup
- Configure exception response formats
- Add validation error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

## Developer 2: Vivek (Card Service)

### Pre-work: Wait for Koushik to complete all infrastructure and auth-service commits

#### Commit 1: Initialize Card Service with basic setup

```bash
git add card-service/pom.xml
git add card-service/src/main/resources/application.yml
git add card-service/src/main/java/com/revpay/card/CardServiceApplication.java
git add card-service/src/test/java/com/revpay/card/CardServiceApplicationTests.java

git commit -m "$(cat <<'EOF'
Initialize Card Service with Spring Boot configuration

- Add card-service module with required dependencies
- Configure application.yml with database settings
- Setup CardServiceApplication main class
- Add Eureka client configuration
- Configure PostgreSQL data source
- Add basic test class

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Add Card Service domain entities and enums

```bash
git add card-service/src/main/java/com/revpay/card/entity/Card.java
git add card-service/src/main/java/com/revpay/card/entity/CardType.java
git add card-service/src/main/java/com/revpay/card/entity/PaymentMethodType.java

git commit -m "$(cat <<'EOF'
Add Card entity and related enums

- Create Card entity with JPA annotations
- Add CardType enum (CREDIT, DEBIT)
- Add PaymentMethodType enum (CREDIT_CARD, DEBIT_CARD, BANK_ACCOUNT)
- Configure encrypted card number storage
- Add card validation fields (expiry, CVV)
- Setup user association with userId field

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Add Card Service DTOs

```bash
git add card-service/src/main/java/com/revpay/card/dto/AddCardRequest.java
git add card-service/src/main/java/com/revpay/card/dto/CardResponse.java
git add card-service/src/main/java/com/revpay/card/dto/ErrorResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for card operations

- Create AddCardRequest for adding new cards
- Add CardResponse with masked card numbers
- Create ErrorResponse for error handling
- Add validation annotations
- Include timestamp and error details

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Add Card Service repository

```bash
git add card-service/src/main/java/com/revpay/card/repository/CardRepository.java

git commit -m "$(cat <<'EOF'
Add CardRepository for data access

- Create CardRepository extending JpaRepository
- Add custom query methods for user cards
- Implement findByUserId method
- Add countByUserId for card limit checking
- Add findByUserIdAndIsDefaultTrue for default card

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Add Card Service business logic

```bash
git add card-service/src/main/java/com/revpay/card/service/CardService.java
git add card-service/src/main/java/com/revpay/card/service/CardServiceImpl.java
git add card-service/src/main/java/com/revpay/card/service/EncryptionService.java

git commit -m "$(cat <<'EOF'
Implement card management business logic

- Add CardService interface with core operations
- Implement CardServiceImpl with card CRUD operations
- Create EncryptionService for card number encryption/decryption
- Add card limit validation (max 3 cards per user)
- Implement default card management
- Add card masking for security

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 6: Add Card Service REST controller

```bash
git add card-service/src/main/java/com/revpay/card/controller/CardController.java

git commit -m "$(cat <<'EOF'
Add REST controller for card operations

- Create CardController with card management endpoints
- Implement POST /cards for adding cards
- Add GET /cards for retrieving user cards
- Implement DELETE /cards/{id} for card removal
- Add PUT /cards/{id}/default for setting default card
- Extract userId from JWT token

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 7: Add Card Service security and Feign configuration

```bash
git add card-service/src/main/java/com/revpay/card/config/SecurityConfig.java
git add card-service/src/main/java/com/revpay/card/config/JwtAuthenticationFilter.java
git add card-service/src/main/java/com/revpay/card/config/JwtService.java
git add card-service/src/main/java/com/revpay/card/config/FeignConfig.java
git add card-service/src/main/java/com/revpay/card/client/NotificationServiceClient.java

git commit -m "$(cat <<'EOF'
Configure security and Feign clients for Card Service

- Implement SecurityConfig with JWT authentication
- Add JwtAuthenticationFilter for token validation
- Create JwtService for token processing
- Configure FeignConfig for inter-service communication
- Add NotificationServiceClient for sending notifications
- Setup request interceptor for JWT propagation

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add Card Service exception handling

```bash
git add card-service/src/main/java/com/revpay/card/exception/GlobalExceptionHandler.java
git add card-service/src/main/java/com/revpay/card/exception/ResourceNotFoundException.java
git add card-service/src/main/java/com/revpay/card/exception/CardLimitExceededException.java

git commit -m "$(cat <<'EOF'
Add exception handling for Card Service

- Implement GlobalExceptionHandler for error responses
- Create ResourceNotFoundException for missing cards
- Add CardLimitExceededException for card limit validation
- Configure exception response formats
- Add validation error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

## Developer 3: Dileep (Notification Service)

### Pre-work: Wait for Vivek to complete card-service commits

#### Commit 1: Initialize Notification Service with basic setup

```bash
git add notification-service/pom.xml
git add notification-service/src/main/resources/application.yml
git add notification-service/src/main/java/com/revpay/notification/NotificationServiceApplication.java

git commit -m "$(cat <<'EOF'
Initialize Notification Service with Spring Boot configuration

- Add notification-service module with required dependencies
- Configure application.yml with database settings
- Setup NotificationServiceApplication main class
- Add Eureka client configuration
- Configure PostgreSQL data source

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Add Notification Service domain entities and enums

```bash
git add notification-service/src/main/java/com/revpay/notification/entity/Notification.java
git add notification-service/src/main/java/com/revpay/notification/entity/NotificationCategory.java
git add notification-service/src/main/java/com/revpay/notification/entity/NotificationPreference.java

git commit -m "$(cat <<'EOF'
Add Notification and NotificationPreference entities

- Create Notification entity with JPA annotations
- Add NotificationCategory enum (TRANSACTION, SECURITY, MARKETING, SYSTEM)
- Create NotificationPreference entity for user preferences
- Configure entity relationships
- Add read/unread status tracking
- Setup user notification preferences

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Add Notification Service DTOs

```bash
git add notification-service/src/main/java/com/revpay/notification/dto/NotificationRequest.java
git add notification-service/src/main/java/com/revpay/notification/dto/NotificationResponse.java
git add notification-service/src/main/java/com/revpay/notification/dto/NotificationPreferenceResponse.java
git add notification-service/src/main/java/com/revpay/notification/dto/UpdateNotificationPreferenceRequest.java
git add notification-service/src/main/java/com/revpay/notification/dto/UnreadCountResponse.java
git add notification-service/src/main/java/com/revpay/notification/dto/ErrorResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for notification operations

- Create NotificationRequest for sending notifications
- Add NotificationResponse with notification details
- Create NotificationPreferenceResponse for user preferences
- Add UpdateNotificationPreferenceRequest for preference updates
- Create UnreadCountResponse for unread notification count
- Add ErrorResponse for error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Add Notification Service repositories

```bash
git add notification-service/src/main/java/com/revpay/notification/repository/NotificationRepository.java
git add notification-service/src/main/java/com/revpay/notification/repository/NotificationPreferenceRepository.java

git commit -m "$(cat <<'EOF'
Add repositories for Notification and NotificationPreference

- Create NotificationRepository with custom queries
- Add NotificationPreferenceRepository for preference management
- Implement findByUserIdOrderByCreatedAtDesc
- Add countByUserIdAndIsReadFalse for unread count
- Implement preference lookup by userId

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Add Notification Service business logic

```bash
git add notification-service/src/main/java/com/revpay/notification/service/NotificationService.java
git add notification-service/src/main/java/com/revpay/notification/service/NotificationServiceImpl.java
git add notification-service/src/main/java/com/revpay/notification/service/NotificationPreferenceService.java
git add notification-service/src/main/java/com/revpay/notification/service/NotificationPreferenceServiceImpl.java

git commit -m "$(cat <<'EOF'
Implement notification management business logic

- Add NotificationService interface with core operations
- Implement NotificationServiceImpl for notification CRUD
- Create NotificationPreferenceService for preference management
- Add notification sending with category filtering
- Implement mark as read functionality
- Add unread count calculation
- Create preference update logic

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 6: Add Notification Service REST controllers

```bash
git add notification-service/src/main/java/com/revpay/notification/controller/NotificationController.java
git add notification-service/src/main/java/com/revpay/notification/controller/InternalController.java

git commit -m "$(cat <<'EOF'
Add REST controllers for notification operations

- Create NotificationController for user-facing endpoints
- Add InternalController for inter-service communication
- Implement GET /notifications for retrieving notifications
- Add PUT /notifications/{id}/read for marking as read
- Implement POST /internal/notifications for service-to-service calls
- Add GET /notifications/unread-count endpoint
- Create preference management endpoints

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 7: Add Notification Service security configuration

```bash
git add notification-service/src/main/java/com/revpay/notification/config/SecurityConfig.java
git add notification-service/src/main/java/com/revpay/notification/config/JwtAuthenticationFilter.java
git add notification-service/src/main/java/com/revpay/notification/util/JwtUtil.java

git commit -m "$(cat <<'EOF'
Configure security for Notification Service

- Implement SecurityConfig with JWT authentication
- Add JwtAuthenticationFilter for token validation
- Create JwtUtil for token processing
- Configure public and protected endpoints
- Add CORS configuration
- Setup security filter chain

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add Notification Service exception handling and data loader

```bash
git add notification-service/src/main/java/com/revpay/notification/exception/GlobalExceptionHandler.java
git add notification-service/src/main/java/com/revpay/notification/exception/ResourceNotFoundException.java
git add notification-service/src/main/java/com/revpay/notification/config/DataLoader.java

git commit -m "$(cat <<'EOF'
Add exception handling and data initialization

- Implement GlobalExceptionHandler for error responses
- Create ResourceNotFoundException for missing notifications
- Add DataLoader to initialize default notification preferences
- Configure exception response formats
- Setup default preferences for all categories

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

## Developer 4: Sri Charani (Wallet Service)

### Pre-work: Wait for Dileep to complete notification-service commits

#### Commit 1: Initialize Wallet Service with basic setup

```bash
git add wallet-service/pom.xml
git add wallet-service/src/main/resources/application.yml
git add wallet-service/src/main/java/com/revpay/wallet/WalletServiceApplication.java
git add wallet-service/src/test/java/com/revpay/wallet/WalletServiceApplicationTests.java

git commit -m "$(cat <<'EOF'
Initialize Wallet Service with Spring Boot configuration

- Add wallet-service module with required dependencies
- Configure application.yml with database settings
- Setup WalletServiceApplication main class
- Add Eureka client configuration
- Configure PostgreSQL data source
- Add basic test class

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Add Wallet Service domain entities and enums

```bash
git add wallet-service/src/main/java/com/revpay/wallet/entity/Wallet.java
git add wallet-service/src/main/java/com/revpay/wallet/entity/Transaction.java
git add wallet-service/src/main/java/com/revpay/wallet/entity/TransactionType.java
git add wallet-service/src/main/java/com/revpay/wallet/entity/TransactionStatus.java
git add wallet-service/src/main/java/com/revpay/wallet/entity/MoneyRequest.java
git add wallet-service/src/main/java/com/revpay/wallet/entity/RequestStatus.java

git commit -m "$(cat <<'EOF'
Add Wallet, Transaction, and MoneyRequest entities

- Create Wallet entity with balance tracking
- Add Transaction entity for transaction history
- Create MoneyRequest entity for money requests
- Add TransactionType enum (CREDIT, DEBIT, TRANSFER, REQUEST_PAYMENT)
- Add TransactionStatus enum (PENDING, COMPLETED, FAILED, CANCELLED)
- Add RequestStatus enum (PENDING, ACCEPTED, REJECTED, CANCELLED)
- Configure entity relationships and constraints

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Add Wallet Service DTOs

```bash
git add wallet-service/src/main/java/com/revpay/wallet/dto/WalletResponse.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/WalletOperationRequest.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/SendMoneyRequest.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/TransactionResponse.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/TransactionHistoryResponse.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/CreateMoneyRequestDto.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/MoneyRequestResponse.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/RespondToRequestDto.java
git add wallet-service/src/main/java/com/revpay/wallet/dto/ErrorResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for wallet and transaction operations

- Create WalletResponse for wallet details
- Add WalletOperationRequest for deposit/withdrawal
- Create SendMoneyRequest for money transfers
- Add TransactionResponse and TransactionHistoryResponse
- Create CreateMoneyRequestDto for requesting money
- Add MoneyRequestResponse for request details
- Create RespondToRequestDto for accepting/rejecting requests
- Add ErrorResponse for error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Add Wallet Service repositories

```bash
git add wallet-service/src/main/java/com/revpay/wallet/repository/WalletRepository.java
git add wallet-service/src/main/java/com/revpay/wallet/repository/TransactionRepository.java
git add wallet-service/src/main/java/com/revpay/wallet/repository/MoneyRequestRepository.java

git commit -m "$(cat <<'EOF'
Add repositories for Wallet, Transaction, and MoneyRequest

- Create WalletRepository with custom queries
- Add TransactionRepository for transaction history
- Create MoneyRequestRepository for money requests
- Implement findByUserId methods
- Add transaction history queries with pagination
- Implement money request queries by status

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Add Wallet Service business logic

```bash
git add wallet-service/src/main/java/com/revpay/wallet/service/WalletService.java
git add wallet-service/src/main/java/com/revpay/wallet/service/TransactionService.java
git add wallet-service/src/main/java/com/revpay/wallet/service/MoneyRequestService.java

git commit -m "$(cat <<'EOF'
Implement wallet management business logic

- Add WalletService with wallet operations
- Implement TransactionService for transaction processing
- Create MoneyRequestService for money request handling
- Add deposit and withdrawal with PIN verification
- Implement peer-to-peer money transfer
- Add transaction history retrieval
- Create money request creation and response logic
- Add balance validation and concurrent access handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 6: Add Wallet Service REST controllers

```bash
git add wallet-service/src/main/java/com/revpay/wallet/controller/WalletController.java
git add wallet-service/src/main/java/com/revpay/wallet/controller/TransactionController.java
git add wallet-service/src/main/java/com/revpay/wallet/controller/MoneyRequestController.java

git commit -m "$(cat <<'EOF'
Add REST controllers for wallet operations

- Create WalletController for wallet management
- Add TransactionController for transaction operations
- Create MoneyRequestController for money requests
- Implement GET /wallet for wallet details
- Add POST /wallet/deposit and POST /wallet/withdraw
- Implement POST /wallet/send for money transfers
- Add GET /transactions for transaction history
- Create POST /money-requests for requesting money
- Add PUT /money-requests/{id}/respond for accepting/rejecting

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 7: Add Wallet Service security and Feign configuration

```bash
git add wallet-service/src/main/java/com/revpay/wallet/config/SecurityConfig.java
git add wallet-service/src/main/java/com/revpay/wallet/config/FeignConfig.java
git add wallet-service/src/main/java/com/revpay/wallet/client/AuthServiceClient.java
git add wallet-service/src/main/java/com/revpay/wallet/client/CardServiceClient.java
git add wallet-service/src/main/java/com/revpay/wallet/client/NotificationServiceClient.java

git commit -m "$(cat <<'EOF'
Configure security and Feign clients for Wallet Service

- Implement SecurityConfig with JWT authentication
- Configure FeignConfig for inter-service communication
- Add AuthServiceClient for PIN verification
- Create CardServiceClient for card operations
- Add NotificationServiceClient for transaction notifications
- Setup request interceptor for JWT propagation

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add Wallet Service exception handling

```bash
git add wallet-service/src/main/java/com/revpay/wallet/exception/GlobalExceptionHandler.java
git add wallet-service/src/main/java/com/revpay/wallet/exception/InsufficientFundsException.java
git add wallet-service/src/main/java/com/revpay/wallet/exception/InvalidPinException.java

git commit -m "$(cat <<'EOF'
Add exception handling for Wallet Service

- Implement GlobalExceptionHandler for error responses
- Create InsufficientFundsException for balance validation
- Add InvalidPinException for PIN verification failures
- Configure exception response formats
- Add validation error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

## Developer 5: Nikhil (Business Service)

### Pre-work: Wait for Sri Charani to complete wallet-service commits

#### Commit 1: Initialize Business Service with basic setup

```bash
git add business-service/pom.xml
git add business-service/src/main/resources/application.yml
git add business-service/src/main/java/com/revpay/business/BusinessServiceApplication.java

git commit -m "$(cat <<'EOF'
Initialize Business Service with Spring Boot configuration

- Add business-service module with required dependencies
- Configure application.yml with database settings
- Setup BusinessServiceApplication main class
- Add Eureka client configuration
- Configure PostgreSQL data source

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Add Business Service domain entities and enums

```bash
git add business-service/src/main/java/com/revpay/business/entity/BusinessProfile.java
git add business-service/src/main/java/com/revpay/business/entity/BusinessBankAccount.java
git add business-service/src/main/java/com/revpay/business/entity/Invoice.java
git add business-service/src/main/java/com/revpay/business/entity/InvoiceItem.java
git add business-service/src/main/java/com/revpay/business/entity/BusinessLoan.java
git add business-service/src/main/java/com/revpay/business/entity/LoanRepayment.java
git add business-service/src/main/java/com/revpay/business/entity/BusinessVerificationStatus.java
git add business-service/src/main/java/com/revpay/business/entity/InvoiceStatus.java
git add business-service/src/main/java/com/revpay/business/entity/LoanStatus.java
git add business-service/src/main/java/com/revpay/business/entity/RepaymentStatus.java

git commit -m "$(cat <<'EOF'
Add Business Service entities and enums

- Create BusinessProfile entity for business accounts
- Add BusinessBankAccount for bank account linking
- Create Invoice and InvoiceItem entities
- Add BusinessLoan and LoanRepayment entities
- Create BusinessVerificationStatus enum (PENDING, VERIFIED, REJECTED)
- Add InvoiceStatus enum (DRAFT, SENT, PAID, CANCELLED, OVERDUE)
- Create LoanStatus enum (APPLIED, APPROVED, DISBURSED, CLOSED, REJECTED)
- Add RepaymentStatus enum (SCHEDULED, PAID, OVERDUE, FAILED)

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Add Business Service DTOs

```bash
git add business-service/src/main/java/com/revpay/business/dto/BusinessProfileResponse.java
git add business-service/src/main/java/com/revpay/business/dto/BusinessVerificationRequest.java
git add business-service/src/main/java/com/revpay/business/dto/BankAccountRequest.java
git add business-service/src/main/java/com/revpay/business/dto/BankAccountResponse.java
git add business-service/src/main/java/com/revpay/business/dto/InvoiceRequest.java
git add business-service/src/main/java/com/revpay/business/dto/InvoiceResponse.java
git add business-service/src/main/java/com/revpay/business/dto/InvoiceItemRequest.java
git add business-service/src/main/java/com/revpay/business/dto/InvoiceItemResponse.java
git add business-service/src/main/java/com/revpay/business/dto/InvoicePaymentRequest.java
git add business-service/src/main/java/com/revpay/business/dto/LoanApplicationRequest.java
git add business-service/src/main/java/com/revpay/business/dto/LoanResponse.java
git add business-service/src/main/java/com/revpay/business/dto/LoanRepaymentResponse.java
git add business-service/src/main/java/com/revpay/business/dto/BusinessAnalyticsResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for business operations

- Create BusinessProfileResponse and BusinessVerificationRequest
- Add BankAccountRequest and BankAccountResponse
- Create InvoiceRequest, InvoiceResponse, and related DTOs
- Add InvoicePaymentRequest for invoice payments
- Create LoanApplicationRequest and LoanResponse
- Add LoanRepaymentResponse for loan tracking
- Create BusinessAnalyticsResponse for business metrics

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Add Business Service repositories

```bash
git add business-service/src/main/java/com/revpay/business/repository/BusinessProfileRepository.java
git add business-service/src/main/java/com/revpay/business/repository/BusinessBankAccountRepository.java
git add business-service/src/main/java/com/revpay/business/repository/InvoiceRepository.java
git add business-service/src/main/java/com/revpay/business/repository/BusinessLoanRepository.java
git add business-service/src/main/java/com/revpay/business/repository/LoanRepaymentRepository.java

git commit -m "$(cat <<'EOF'
Add repositories for Business Service entities

- Create BusinessProfileRepository with custom queries
- Add BusinessBankAccountRepository for account management
- Create InvoiceRepository with invoice queries
- Add BusinessLoanRepository for loan operations
- Create LoanRepaymentRepository for repayment tracking
- Implement queries by userId and status

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Add Business Service business logic

```bash
git add business-service/src/main/java/com/revpay/business/service/BusinessProfileService.java
git add business-service/src/main/java/com/revpay/business/service/BankAccountService.java
git add business-service/src/main/java/com/revpay/business/service/InvoiceService.java
git add business-service/src/main/java/com/revpay/business/service/LoanService.java
git add business-service/src/main/java/com/revpay/business/service/AnalyticsService.java

git commit -m "$(cat <<'EOF'
Implement business management services

- Add BusinessProfileService for profile management
- Implement BankAccountService for account operations
- Create InvoiceService for invoice generation and payment
- Add LoanService for loan application and repayment
- Implement AnalyticsService for business metrics
- Add invoice generation with automatic calculations
- Create loan eligibility checking
- Implement analytics aggregation

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 6: Add Business Service REST controllers

```bash
git add business-service/src/main/java/com/revpay/business/controller/BusinessProfileController.java
git add business-service/src/main/java/com/revpay/business/controller/BankAccountController.java
git add business-service/src/main/java/com/revpay/business/controller/InvoiceController.java
git add business-service/src/main/java/com/revpay/business/controller/LoanController.java
git add business-service/src/main/java/com/revpay/business/controller/AnalyticsController.java

git commit -m "$(cat <<'EOF'
Add REST controllers for business operations

- Create BusinessProfileController for profile management
- Add BankAccountController for account operations
- Implement InvoiceController for invoice CRUD
- Create LoanController for loan applications
- Add AnalyticsController for business metrics
- Implement POST /business/profile for profile creation
- Add GET /business/invoices and POST /business/invoices
- Create POST /business/loans and GET /business/loans
- Add GET /business/analytics for business insights

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 7: Add Business Service security and Feign configuration

```bash
git add business-service/src/main/java/com/revpay/business/config/SecurityConfig.java
git add business-service/src/main/java/com/revpay/business/config/FeignConfig.java
git add business-service/src/main/java/com/revpay/business/client/WalletServiceClient.java
git add business-service/src/main/java/com/revpay/business/client/NotificationServiceClient.java

git commit -m "$(cat <<'EOF'
Configure security and Feign clients for Business Service

- Implement SecurityConfig with JWT authentication
- Configure FeignConfig for inter-service communication
- Add WalletServiceClient for payment processing
- Create NotificationServiceClient for business notifications
- Setup request interceptor for JWT propagation

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add Business Service exception handling

```bash
git add business-service/src/main/java/com/revpay/business/exception/GlobalExceptionHandler.java
git add business-service/src/main/java/com/revpay/business/exception/ResourceNotFoundException.java
git add business-service/src/main/java/com/revpay/business/exception/BusinessException.java
git add business-service/src/main/java/com/revpay/business/exception/UnauthorizedException.java

git commit -m "$(cat <<'EOF'
Add exception handling for Business Service

- Implement GlobalExceptionHandler for error responses
- Create ResourceNotFoundException for missing resources
- Add BusinessException for business logic errors
- Create UnauthorizedException for access control
- Configure exception response formats
- Add validation error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

## Verification Commands

After all commits are complete, verify the repository state:

```bash
# Check total number of commits
git log --oneline | wc -l

# View commit history
git log --oneline --graph --all

# Check commit authors
git shortlog -sn

# Verify all files are tracked
git status

# Check branch status
git branch -v
```

---

## Common Git Commands Reference

```bash
# Check current status
git status

# View uncommitted changes
git diff

# View commit history
git log --oneline -n 10

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes - DANGEROUS!)
git reset --hard HEAD~1

# View specific commit
git show <commit-hash>

# Check who made changes to a file
git blame <file-path>
```

---

## Notes

1. **Sequential Commits**: Each developer must wait for the previous developer to complete their commits before starting.

2. **Commit Messages**: All commit messages follow the format:
   - Brief summary line
   - Blank line
   - Detailed bullet points of changes
   - Blank line
   - Claude Code attribution

3. **File Paths**: All file paths are absolute and based on the repository root.

4. **HEREDOC Format**: All commit messages use HEREDOC format to preserve formatting and handle multi-line messages properly.

5. **Testing**: After each developer completes their commits, test the build:
   ```bash
   mvn clean install -DskipTests
   ```

6. **Database Setup**: Ensure PostgreSQL is running locally before testing the services.

7. **Port Conflicts**: Each service runs on a different port (configured in application.yml).

8. **Service Dependencies**: Services depend on each other in this order:
   - Config Server & Discovery Server (infrastructure)
   - API Gateway (routing)
   - Auth Service (authentication)
   - Card Service (requires Auth)
   - Notification Service (standalone)
   - Wallet Service (requires Auth, Card, Notification)
   - Business Service (requires Wallet, Notification)

---

## Troubleshooting

### Issue: Commit message contains special characters
**Solution**: Use HEREDOC format as shown in examples (already included in all commands)

### Issue: File not found during git add
**Solution**:
```bash
# Check if file exists
ls -la <file-path>

# Check current directory
pwd

# Navigate to repository root
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revpay
```

### Issue: Merge conflicts
**Solution**: This shouldn't happen with sequential commits, but if it does:
```bash
# Check conflicting files
git status

# Resolve conflicts in editor, then:
git add <resolved-files>
git commit -m "Resolve merge conflicts"
```

### Issue: Wrong author in commit
**Solution**: Fix before pushing:
```bash
# Amend last commit with correct author
git commit --amend --author="Correct Name <correct.email@example.com>"
```

---

## End of Document

**Total Expected Commits**: 14 (Koushik) + 8 (Vivek) + 8 (Dileep) + 8 (Sri Charani) + 8 (Nikhil) = **46 commits**
