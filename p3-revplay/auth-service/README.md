# Auth Service - RevPlay

**Owner**: Satya
**Port**: 8081
**Service Name**: auth-service

## Overview

The Auth Service handles authentication and security for the RevPlay platform. It provides JWT-based authentication with refresh token support, user registration, and login functionality.

## Technologies

- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- H2 Database (In-Memory)
- Eureka Client (Service Discovery)
- JWT (io.jsonwebtoken 0.12.3)
- Jakarta Validation

## Features

- User registration with role-based access (USER, ARTIST)
- JWT-based authentication
- Refresh token mechanism
- Password encryption using BCrypt
- Token validation endpoint for internal services
- Logout functionality (invalidate refresh tokens)
- Global exception handling
- CORS support

## Project Structure

```
auth-service/
├── src/main/java/com/revplay/auth/
│   ├── AuthServiceApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── WebConfig.java
│   ├── controller/
│   │   └── AuthController.java
│   ├── dto/
│   │   ├── AuthResponse.java
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── TokenRefreshRequest.java
│   │   └── TokenRefreshResponse.java
│   ├── entity/
│   │   ├── RefreshToken.java
│   │   ├── Role.java
│   │   └── User.java
│   ├── exception/
│   │   ├── AuthException.java
│   │   └── GlobalExceptionHandler.java
│   ├── repository/
│   │   ├── RefreshTokenRepository.java
│   │   └── UserRepository.java
│   └── service/
│       ├── AuthService.java
│       ├── AuthServiceImpl.java
│       ├── JwtService.java
│       └── RefreshTokenService.java
├── src/main/resources/
│   └── application.yml
├── Dockerfile
└── pom.xml
```

## API Endpoints

### 1. Register User
**POST** `/api/auth/register`

Request Body:
```json
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER"
}
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "tokenType": "Bearer",
  "userId": 1,
  "email": "user@example.com",
  "role": "USER"
}
```

### 2. Login
**POST** `/api/auth/login`

Request Body:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "tokenType": "Bearer",
  "userId": 1,
  "email": "user@example.com",
  "role": "USER"
}
```

### 3. Refresh Token
**POST** `/api/auth/refresh`

Request Body:
```json
{
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "tokenType": "Bearer"
}
```

### 4. Logout
**POST** `/api/auth/logout`

Request Body:
```json
{
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

Response:
```json
{
  "message": "Logout successful"
}
```

### 5. Validate Token (Internal)
**GET** `/api/auth/validate?token={jwt_token}`

Response:
```json
{
  "valid": true,
  "userId": 1,
  "email": "user@example.com"
}
```

## Configuration

### Application Properties (application.yml)

- **Server Port**: 8081
- **Database**: H2 in-memory database
- **H2 Console**: Available at `http://localhost:8081/h2-console`
- **Eureka Server**: `http://localhost:8761/eureka/`
- **JWT Expiration**: 24 hours (86400000 ms)
- **Refresh Token Expiration**: 7 days (604800000 ms)

## Database Schema

### Users Table
- `id` (BIGINT, Primary Key)
- `email` (VARCHAR, Unique, Not Null)
- `password` (VARCHAR, Not Null)
- `first_name` (VARCHAR, Not Null)
- `last_name` (VARCHAR, Not Null)
- `role` (VARCHAR, Not Null)
- `created_at` (TIMESTAMP, Not Null)
- `updated_at` (TIMESTAMP, Not Null)

### Refresh Tokens Table
- `id` (BIGINT, Primary Key)
- `token` (VARCHAR, Unique, Not Null)
- `user_id` (BIGINT, Not Null)
- `expiry_date` (TIMESTAMP, Not Null)

## Running the Service

### Prerequisites
- Java 17
- Maven 3.9+
- Eureka Server running on port 8761

### Using Maven
```bash
mvn clean install
mvn spring-boot:run
```

### Using Docker
```bash
docker build -t revplay-auth-service .
docker run -p 8081:8081 revplay-auth-service
```

## Security

- Passwords are hashed using BCrypt
- JWT tokens are signed using HS256 algorithm
- Refresh tokens are stored in the database and validated on each use
- CSRF protection is disabled (stateless API)
- CORS is enabled for all origins

## Service Discovery

This service registers with Eureka Server as `auth-service` and can be discovered by other microservices in the RevPlay ecosystem.

## Error Handling

The service implements global exception handling with standardized error responses:

```json
{
  "timestamp": "2024-03-05T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password",
  "path": "/api/auth/login"
}
```

## Health Check

The service exposes Spring Boot Actuator endpoints for health monitoring.

## Development Notes

- No Lombok used - Manual builder pattern implemented
- Jakarta validation annotations used for request validation
- All entities and DTOs include builder pattern for easy object creation
- Service uses constructor-based dependency injection
