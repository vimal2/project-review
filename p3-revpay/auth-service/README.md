# RevPay Auth Service

Authentication and Authorization microservice for RevPay P3.

## Owner
Koushik

## Port
8081

## Package
com.revpay.auth

## Technology Stack
- Java 17
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Tokens)
- Lombok
- Maven

## Features
- User registration with role-based access (PERSONAL, BUSINESS, ADMIN)
- Secure login with JWT token generation
- Account lockout after 5 failed login attempts (15 minutes)
- Security questions for password recovery
- Transaction PIN management (setup, verify, reset)
- Password reset via security questions
- Token validation endpoint for internal microservice communication

## Database Schema

### Users Table
- id (Primary Key)
- username (Unique)
- email (Unique)
- password (Encrypted)
- fullName
- phoneNumber
- role (PERSONAL, BUSINESS, ADMIN)
- transactionPin (Encrypted)
- failedLoginAttempts
- lockoutUntil
- twoFactorCode
- twoFactorExpiry
- createdAt
- enabled

### Security Questions Table
- id (Primary Key)
- userId (Foreign Key)
- question
- answer

## API Endpoints

### Authentication Endpoints

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "phoneNumber": "1234567890",
  "role": "PERSONAL",
  "securityQuestions": [
    {
      "question": "What is your pet's name?",
      "answer": "fluffy"
    }
  ]
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "role": "PERSONAL"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "role": "PERSONAL"
}
```

#### Get Recovery Questions
```
GET /api/auth/recovery/questions?email=john@example.com

Response: 200 OK
[
  "What is your pet's name?"
]
```

#### Reset Password
```
POST /api/auth/recovery/reset
Content-Type: application/json

{
  "email": "john@example.com",
  "newPassword": "newPassword123",
  "answers": [
    {
      "question": "What is your pet's name?",
      "answer": "fluffy"
    }
  ]
}

Response: 200 OK
{
  "message": "Password reset successfully"
}
```

#### Validate Token (Internal)
```
GET /api/auth/validate
Authorization: Bearer <token>

Response: 200 OK
{
  "valid": true,
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "role": "PERSONAL"
}
```

### Security Endpoints (Protected)

#### Setup Transaction PIN
```
POST /api/v1/security/pin/setup
Authorization: Bearer <token>
Content-Type: application/json

{
  "pin": "1234"
}

Response: 200 OK
{
  "message": "Transaction PIN set successfully"
}
```

#### Verify Transaction PIN
```
POST /api/v1/security/pin/verify
Authorization: Bearer <token>
Content-Type: application/json

{
  "pin": "1234"
}

Response: 200 OK
{
  "valid": true
}
```

#### Reset Transaction PIN
```
POST /api/v1/security/pin/reset
Content-Type: application/json

{
  "email": "john@example.com",
  "newPin": "5678",
  "answers": [
    {
      "question": "What is your pet's name?",
      "answer": "fluffy"
    }
  ]
}

Response: 200 OK
{
  "message": "PIN reset successfully"
}
```

## Configuration

### Database Configuration
Update `application.yml` with your MySQL credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revpay_auth?createDatabaseIfNotExist=true
    username: root
    password: your_password
```

### JWT Configuration
Update JWT secret and expiration in `application.yml`:
```yaml
jwt:
  secret: your_secret_key
  expiration: 86400000 # 24 hours
```

## Running the Application

### Local Development
```bash
# Clone the repository
cd auth-service

# Run with Maven
mvn spring-boot:run

# Or build and run
mvn clean package
java -jar target/auth-service-1.0.0.jar
```

### Docker
```bash
# Build Docker image
docker build -t revpay-auth-service .

# Run container
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/revpay_auth \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  revpay-auth-service
```

### Docker Compose
```yaml
version: '3.8'
services:
  auth-service:
    build: .
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/revpay_auth?createDatabaseIfNotExist=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: password
    depends_on:
      - mysql

  mysql:
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: revpay_auth
```

## Default Test Users

The application loads default users on startup:

### Admin User
- Email: admin@revpay.com
- Password: admin123
- Role: ADMIN

### Personal User
- Email: john.doe@example.com
- Password: password123
- Role: PERSONAL
- PIN: 1234

### Business User
- Email: contact@acme.com
- Password: business123
- Role: BUSINESS
- PIN: 9999

## Security Features

1. **Password Encryption**: All passwords are encrypted using BCrypt
2. **JWT Authentication**: Stateless authentication using JWT tokens
3. **Account Lockout**: After 5 failed login attempts, account is locked for 15 minutes
4. **PIN Protection**: Transaction PINs are encrypted and validated separately
5. **Security Questions**: Used for password and PIN recovery
6. **Role-Based Access**: PERSONAL, BUSINESS, and ADMIN roles
7. **CORS Configuration**: Configured for frontend integration

## Error Handling

The service includes comprehensive error handling:
- `InvalidCredentialsException` (401): Invalid email/password or PIN
- `AccountLockedException` (423): Account locked due to failed attempts
- `DuplicateEmailException` (409): Email or username already exists
- `UsernameNotFoundException` (404): User not found
- `MethodArgumentNotValidException` (400): Validation errors

## Health Check

```
GET /actuator/health

Response: 200 OK
{
  "status": "UP"
}
```

## Testing

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=AuthServiceTest
```

## Future Enhancements

- Two-Factor Authentication (2FA) implementation
- OAuth2 integration
- Rate limiting
- Audit logging
- Email verification
- Password strength validation
- Refresh token mechanism

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

Copyright 2024 RevPay. All rights reserved.

## Contact

Owner: Koushik
Project: P3 RevPay
Service: Auth Service
