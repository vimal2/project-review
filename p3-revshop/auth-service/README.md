# Auth Service - RevShop P3

Authentication and Authorization microservice for the RevShop e-commerce platform.

## Overview

The Auth Service handles user registration, login, JWT token generation, password reset, and token validation for the entire RevShop microservices ecosystem.

**Port:** 8081
**Owner:** Manjula (Login, Registration and Authentication)
**Package:** com.revshop.auth

## Features

- User Registration (BUYER and SELLER roles)
- User Login with JWT token generation
- Password Reset functionality
- JWT Token Validation (for API Gateway)
- User retrieval by ID (internal endpoint)
- BCrypt password encryption
- H2 in-memory database
- Eureka service discovery integration
- Sample user data seeding

## Technology Stack

- **Java:** 17
- **Spring Boot:** 3.2.0
- **Spring Cloud:** 2023.0.0
- **Spring Security:** BCrypt password encoding
- **JWT:** io.jsonwebtoken 0.11.5 (24-hour token expiration)
- **Database:** H2 (in-memory)
- **Service Discovery:** Eureka Client
- **Build Tool:** Maven

## Project Structure

```
auth-service/
├── src/main/java/com/revshop/auth/
│   ├── entity/
│   │   ├── User.java
│   │   └── Role.java
│   ├── dto/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   ├── ForgotPasswordRequest.java
│   │   ├── ResetPasswordRequest.java
│   │   ├── UserValidationResponse.java
│   │   └── ErrorResponse.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── service/
│   │   ├── AuthService.java
│   │   └── impl/
│   │       └── AuthServiceImpl.java
│   ├── controller/
│   │   └── AuthController.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── JwtService.java
│   │   └── DataLoader.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── InvalidCredentialsException.java
│   │   ├── DuplicateEmailException.java
│   │   └── ResourceNotFoundException.java
│   └── AuthServiceApplication.java
├── src/main/resources/
│   └── application.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## API Endpoints

### Public Endpoints

#### 1. Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "BUYER",
  "businessName": null
}
```

**Response:** 200 OK
```json
"User registered successfully"
```

#### 2. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:** 200 OK
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "BUYER"
}
```

#### 3. Forgot Password
```http
POST /api/auth/forgot-password
Content-Type: application/json

{
  "email": "john@example.com"
}
```

**Response:** 200 OK
```json
"Password reset token generated successfully. Check your email."
```

#### 4. Reset Password
```http
POST /api/auth/reset-password
Content-Type: application/json

{
  "email": "john@example.com",
  "token": "reset-token-uuid",
  "newPassword": "newpassword123"
}
```

**Response:** 200 OK
```json
"Password reset successful"
```

### Internal Endpoints (for API Gateway and other services)

#### 5. Validate Token
```http
GET /api/auth/validate
Authorization: Bearer <jwt-token>
```

**Response:** 200 OK
```json
{
  "userId": 1,
  "email": "john@example.com",
  "role": "BUYER",
  "valid": true
}
```

#### 6. Get User by ID
```http
GET /api/auth/user/{userId}
```

**Response:** 200 OK
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "BUYER",
  "businessName": null,
  "active": true,
  "createdAt": "2024-03-01T10:00:00"
}
```

## Entity Models

### User
```java
- id: Long (Primary Key)
- name: String
- email: String (Unique)
- password: String (BCrypt encoded)
- role: Role (BUYER/SELLER)
- businessName: String (for SELLER only)
- resetToken: String
- active: Boolean
- createdAt: LocalDateTime
```

### Role (Enum)
- BUYER
- SELLER

## Configuration

### application.yml
```yaml
server:
  port: 8081

spring:
  application:
    name: auth-service
  datasource:
    url: jdbc:h2:mem:authdb
  h2:
    console:
      enabled: true
      path: /h2-console

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Running the Service

### Prerequisites
- Java 17
- Maven 3.6+
- Eureka Server running on port 8761 (optional for standalone testing)

### Run Locally
```bash
cd auth-service
mvn clean install
mvn spring-boot:run
```

The service will start on **http://localhost:8081**

### Access H2 Console
- URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:authdb
- Username: sa
- Password: (empty)

### Run with Docker
```bash
# Build Docker image
docker build -t auth-service:1.0 .

# Run container
docker run -p 8081:8081 auth-service:1.0
```

## Sample Users

The service pre-loads sample users on startup:

| Email | Password | Role | Business Name |
|-------|----------|------|---------------|
| buyer@revshop.com | password123 | BUYER | - |
| seller@revshop.com | password123 | SELLER | Jane's Electronics |
| alice@example.com | password123 | BUYER | - |
| bob@example.com | password123 | SELLER | Bob's Store |

## Security

- **Password Encryption:** BCrypt with default strength (10 rounds)
- **JWT Token:** HS256 algorithm
- **Token Expiration:** 24 hours
- **Secret Key:** Stored in JwtService (use environment variable in production)

## Error Handling

The service returns standardized error responses:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password",
  "timestamp": "2024-03-01T10:00:00"
}
```

**HTTP Status Codes:**
- 200: Success
- 400: Bad Request
- 401: Unauthorized (Invalid credentials)
- 404: Not Found (User not found)
- 409: Conflict (Duplicate email)
- 422: Unprocessable Entity (Validation error)
- 500: Internal Server Error

## Testing

### Using cURL

**Register:**
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "role": "BUYER"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "buyer@revshop.com",
    "password": "password123"
  }'
```

**Validate Token:**
```bash
curl -X GET http://localhost:8081/api/auth/validate \
  -H "Authorization: Bearer <your-jwt-token>"
```

## Integration with Other Services

### API Gateway Integration
The API Gateway should call `/api/auth/validate` to validate JWT tokens before routing requests to other microservices.

### User ID Reference
Other microservices can use the `userId` from the JWT token to reference users. They can call `/api/auth/user/{userId}` to get full user details if needed.

## Future Enhancements

- Email service integration for password reset
- Refresh token mechanism
- OAuth2 integration
- Two-factor authentication (2FA)
- User profile management
- Account activation via email
- Role-based access control (RBAC) refinements

## Support

For issues or questions, contact:
- **Owner:** Manjula
- **Team:** Revature Batch 2353
- **Project:** P3 RevShop Microservices

## License

Proprietary - Revature Training Project
