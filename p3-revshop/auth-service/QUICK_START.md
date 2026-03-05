# Auth Service - Quick Start Guide

## Build and Run

### Option 1: Maven (Local Development)
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

# Build
mvn clean install

# Run
mvn spring-boot:run
```

### Option 2: Docker
```bash
# Build image
docker build -t revshop/auth-service:1.0 .

# Run container
docker run -p 8081:8081 revshop/auth-service:1.0
```

## Access Points

- **API Base URL:** http://localhost:8081/api/auth
- **H2 Console:** http://localhost:8081/h2-console
  - JDBC URL: `jdbc:h2:mem:authdb`
  - Username: `sa`
  - Password: (empty)

## Pre-loaded Test Users

| Email | Password | Role |
|-------|----------|------|
| buyer@revshop.com | password123 | BUYER |
| seller@revshop.com | password123 | SELLER |
| alice@example.com | password123 | BUYER |
| bob@example.com | password123 | SELLER |

## API Testing

### 1. Register New User
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New User",
    "email": "newuser@example.com",
    "password": "password123",
    "role": "BUYER"
  }'
```

Expected: `200 OK` - "User registered successfully"

### 2. Register Seller with Business Name
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New Seller",
    "email": "newseller@example.com",
    "password": "password123",
    "role": "SELLER",
    "businessName": "My Business"
  }'
```

### 3. Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "buyer@revshop.com",
    "password": "password123"
  }'
```

Expected Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 1,
  "name": "John Buyer",
  "email": "buyer@revshop.com",
  "role": "BUYER"
}
```

### 4. Validate Token (Save token from login response)
```bash
TOKEN="<your-jwt-token-here>"

curl -X GET http://localhost:8081/api/auth/validate \
  -H "Authorization: Bearer $TOKEN"
```

Expected Response:
```json
{
  "userId": 1,
  "email": "buyer@revshop.com",
  "role": "BUYER",
  "valid": true
}
```

### 5. Get User by ID
```bash
curl -X GET http://localhost:8081/api/auth/user/1
```

### 6. Forgot Password
```bash
curl -X POST http://localhost:8081/api/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "buyer@revshop.com"
  }'
```

### 7. Reset Password
```bash
# First, check the H2 console to get the reset token generated
# Then use it in this request:

curl -X POST http://localhost:8081/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "buyer@revshop.com",
    "token": "the-reset-token-from-db",
    "newPassword": "newpassword123"
  }'
```

## Testing Error Cases

### Invalid Credentials
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "buyer@revshop.com",
    "password": "wrongpassword"
  }'
```

Expected: `401 Unauthorized`
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password",
  "timestamp": "2024-03-05T21:07:00"
}
```

### Duplicate Email
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Duplicate",
    "email": "buyer@revshop.com",
    "password": "password123",
    "role": "BUYER"
  }'
```

Expected: `409 Conflict`
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Email already exists: buyer@revshop.com",
  "timestamp": "2024-03-05T21:07:00"
}
```

### Validation Error
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "A",
    "email": "invalid-email",
    "password": "123",
    "role": "BUYER"
  }'
```

Expected: `422 Unprocessable Entity`

## Postman Collection (Import Ready)

Create a new collection in Postman with these requests:

1. **Register** - POST `{{baseUrl}}/register`
2. **Login** - POST `{{baseUrl}}/login`
3. **Validate Token** - GET `{{baseUrl}}/validate` (with Bearer token)
4. **Get User** - GET `{{baseUrl}}/user/1`
5. **Forgot Password** - POST `{{baseUrl}}/forgot-password`
6. **Reset Password** - POST `{{baseUrl}}/reset-password`

Environment variables:
- `baseUrl`: `http://localhost:8081/api/auth`
- `token`: (set from login response)

## Integration with API Gateway

When integrating with an API Gateway:

1. **Token Validation**: Call `GET /api/auth/validate` with the Authorization header
2. **Extract User Info**: Parse the response to get userId, email, and role
3. **Pass to Services**: Include userId in headers for downstream services

Example Gateway Flow:
```
Client Request → Gateway → Validate Token (Auth Service) → Forward to Service
```

## Monitoring

Check service health:
```bash
curl http://localhost:8081/actuator/health
```

Check Eureka registration:
```bash
curl http://localhost:8761/eureka/apps/AUTH-SERVICE
```

## Common Issues

1. **Port 8081 already in use**
   - Change port in `application.yml` or stop the conflicting service

2. **Eureka connection failed**
   - Ensure Eureka server is running on port 8761
   - Or disable Eureka: `eureka.client.enabled=false`

3. **JWT token expired**
   - Tokens expire after 24 hours
   - Login again to get a new token

## Next Steps

1. Start the service
2. Test all endpoints using cURL or Postman
3. Verify H2 database content
4. Check Eureka registration (if Eureka is running)
5. Integrate with API Gateway

## Support

For questions or issues:
- Check README.md for detailed documentation
- Review application logs for error details
- Verify all dependencies are properly installed
