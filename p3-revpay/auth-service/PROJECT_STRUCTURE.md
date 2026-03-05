# Auth Service Project Structure

## Project Details
- **Owner**: Koushik
- **Package**: com.revpay.auth
- **Port**: 8081
- **Database**: revpay_auth

## Directory Structure
```
auth-service/
├── src/
│   ├── main/
│   │   ├── java/com/revpay/auth/
│   │   │   ├── AuthServiceApplication.java
│   │   │   ├── config/
│   │   │   │   ├── DataLoader.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── SecurityController.java
│   │   │   ├── dto/
│   │   │   │   ├── AuthRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── ForgotPasswordRequest.java
│   │   │   │   ├── PinResetRequest.java
│   │   │   │   ├── PinSetupRequest.java
│   │   │   │   ├── PinVerifyRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── ResetPasswordRequest.java
│   │   │   │   ├── SecurityQuestionDto.java
│   │   │   │   └── UserValidationResponse.java
│   │   │   ├── entity/
│   │   │   │   ├── Role.java (enum)
│   │   │   │   ├── SecurityQuestion.java
│   │   │   │   └── User.java
│   │   │   ├── exception/
│   │   │   │   ├── AccountLockedException.java
│   │   │   │   ├── DuplicateEmailException.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── InvalidCredentialsException.java
│   │   │   ├── repository/
│   │   │   │   ├── SecurityQuestionRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       ├── AuthService.java
│   │   │       ├── JwtService.java
│   │   │       └── PinService.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/revpay/auth/
├── .gitignore
├── Dockerfile
├── pom.xml
└── README.md
```

## Component Summary

### Entities (3)
1. **User.java** - User entity with authentication fields
2. **SecurityQuestion.java** - Security questions for password recovery
3. **Role.java** - Enum (PERSONAL, BUSINESS, ADMIN)

### DTOs (10)
1. **RegisterRequest** - User registration payload
2. **AuthRequest** - Login credentials
3. **AuthResponse** - JWT token and user info response
4. **SecurityQuestionDto** - Security question Q&A
5. **ForgotPasswordRequest** - Password recovery initiation
6. **ResetPasswordRequest** - Password reset with security answers
7. **PinSetupRequest** - Transaction PIN setup
8. **PinVerifyRequest** - PIN verification
9. **PinResetRequest** - PIN reset with security answers
10. **UserValidationResponse** - Token validation response

### Repositories (2)
1. **UserRepository** - User data access
2. **SecurityQuestionRepository** - Security questions data access

### Services (3)
1. **AuthService** - Registration, login, password reset
2. **JwtService** - JWT token generation and validation
3. **PinService** - Transaction PIN management

### Controllers (2)
1. **AuthController** - Authentication endpoints (/api/auth/*)
2. **SecurityController** - Security endpoints (/api/v1/security/*)

### Configuration (3)
1. **SecurityConfig** - Spring Security configuration
2. **JwtAuthenticationFilter** - JWT token filter
3. **DataLoader** - Initial data loader with test users

### Exceptions (4)
1. **InvalidCredentialsException** - Invalid credentials
2. **AccountLockedException** - Account lockout
3. **DuplicateEmailException** - Duplicate email/username
4. **GlobalExceptionHandler** - Global exception handling

## API Endpoints

### Public Endpoints
- POST `/api/auth/register` - User registration
- POST `/api/auth/login` - User login
- GET `/api/auth/recovery/questions` - Get recovery questions
- POST `/api/auth/recovery/reset` - Reset password
- GET `/api/auth/validate` - Validate token (internal)

### Protected Endpoints (Requires Authentication)
- POST `/api/v1/security/pin/setup` - Setup transaction PIN
- POST `/api/v1/security/pin/verify` - Verify transaction PIN
- POST `/api/v1/security/pin/reset` - Reset transaction PIN

## Features Implemented
- User registration with validation
- JWT-based authentication
- Role-based access control (PERSONAL, BUSINESS, ADMIN)
- Account lockout after 5 failed login attempts (15 minutes)
- Security questions for password recovery
- Transaction PIN management
- Password reset functionality
- Token validation for internal services
- Comprehensive error handling
- CORS configuration
- Docker support
- Health check endpoints
- Default test users

## Technology Stack
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- MySQL 8.0
- JWT (jjwt 0.11.5)
- Lombok
- Maven 3.9+
- Java 17

## Build & Run

### Maven
```bash
mvn clean install
mvn spring-boot:run
```

### Docker
```bash
docker build -t revpay-auth-service .
docker run -p 8081:8081 revpay-auth-service
```

## Test Users
1. Admin: admin@revpay.com / admin123
2. Personal: john.doe@example.com / password123
3. Business: contact@acme.com / business123
