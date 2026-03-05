# Notification Service - RevPay

## Overview
The Notification Service is a microservice for the RevPay platform that manages user notifications, notification preferences, and provides internal APIs for other services to create notifications.

**Owner:** Dileep
**Port:** 8083
**Package:** com.revpay.notification

## Features
- User notification management (TRANSACTIONS, REQUESTS, ALERTS)
- Notification preferences per user
- Mark notifications as read
- Unread notification count
- Internal API for service-to-service notification creation
- JWT-based authentication
- RESTful API endpoints

## Technology Stack
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- Spring Security with JWT
- MySQL Database
- Lombok
- Maven

## Project Structure
```
notification-service/
├── src/
│   ├── main/
│   │   ├── java/com/revpay/notification/
│   │   │   ├── config/           # Configuration classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── DataLoader.java
│   │   │   ├── controller/       # REST controllers
│   │   │   │   ├── NotificationController.java
│   │   │   │   └── InternalController.java
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── NotificationRequest.java
│   │   │   │   ├── NotificationResponse.java
│   │   │   │   ├── NotificationPreferenceResponse.java
│   │   │   │   ├── UpdateNotificationPreferenceRequest.java
│   │   │   │   ├── UnreadCountResponse.java
│   │   │   │   └── ErrorResponse.java
│   │   │   ├── entity/           # JPA entities
│   │   │   │   ├── Notification.java
│   │   │   │   ├── NotificationCategory.java
│   │   │   │   └── NotificationPreference.java
│   │   │   ├── exception/        # Custom exceptions
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/       # Spring Data repositories
│   │   │   │   ├── NotificationRepository.java
│   │   │   │   └── NotificationPreferenceRepository.java
│   │   │   ├── service/          # Business logic
│   │   │   │   ├── NotificationService.java
│   │   │   │   ├── NotificationServiceImpl.java
│   │   │   │   ├── NotificationPreferenceService.java
│   │   │   │   └── NotificationPreferenceServiceImpl.java
│   │   │   ├── util/             # Utility classes
│   │   │   │   └── JwtUtil.java
│   │   │   └── NotificationServiceApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── Dockerfile
├── pom.xml
└── README.md
```

## Database Schema

### Notifications Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| user_id | BIGINT | User identifier |
| category | VARCHAR(50) | TRANSACTIONS, REQUESTS, ALERTS |
| type | VARCHAR(100) | Notification type |
| title | VARCHAR(255) | Notification title |
| message | TEXT | Notification message |
| amount | DECIMAL(19,2) | Transaction amount (optional) |
| counterparty | VARCHAR(255) | Other party name (optional) |
| event_status | VARCHAR(50) | Event status |
| navigation_target | VARCHAR(255) | Frontend route to navigate |
| event_time | TIMESTAMP | Time of the event |
| metadata_json | TEXT | Additional metadata in JSON |
| is_read | BOOLEAN | Read status |
| created_at | TIMESTAMP | Creation timestamp |

### Notification Preferences Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| user_id | BIGINT | User identifier (unique) |
| transactions_enabled | BOOLEAN | Enable transaction notifications |
| requests_enabled | BOOLEAN | Enable request notifications |
| alerts_enabled | BOOLEAN | Enable alert notifications |
| low_balance_threshold | DECIMAL(19,2) | Low balance alert threshold |

## API Endpoints

### User Notification Endpoints (Authenticated)

#### 1. Get Notifications
```
GET /api/v1/notifications?category={CATEGORY}&unreadOnly={true|false}
Authorization: Bearer <JWT_TOKEN>
```

**Query Parameters:**
- `category` (optional): TRANSACTIONS, REQUESTS, or ALERTS
- `unreadOnly` (optional): true to get only unread notifications

**Response:**
```json
[
  {
    "id": 1,
    "userId": 1,
    "category": "TRANSACTIONS",
    "type": "PAYMENT_RECEIVED",
    "title": "Payment Received",
    "message": "You received $50.00 from John Doe",
    "amount": 50.00,
    "counterparty": "John Doe",
    "eventStatus": "COMPLETED",
    "navigationTarget": "/transactions/123",
    "eventTime": "2024-01-15T10:30:00",
    "metadataJson": null,
    "isRead": false,
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

#### 2. Get Unread Count
```
GET /api/v1/notifications/unread-count
Authorization: Bearer <JWT_TOKEN>
```

**Response:**
```json
{
  "count": 5
}
```

#### 3. Mark Notification as Read
```
PATCH /api/v1/notifications/{id}/read
Authorization: Bearer <JWT_TOKEN>
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "category": "TRANSACTIONS",
  "type": "PAYMENT_RECEIVED",
  "title": "Payment Received",
  "message": "You received $50.00 from John Doe",
  "amount": 50.00,
  "counterparty": "John Doe",
  "eventStatus": "COMPLETED",
  "navigationTarget": "/transactions/123",
  "eventTime": "2024-01-15T10:30:00",
  "metadataJson": null,
  "isRead": true,
  "createdAt": "2024-01-15T10:30:00"
}
```

#### 4. Mark All Notifications as Read
```
PATCH /api/v1/notifications/read-all
Authorization: Bearer <JWT_TOKEN>
```

**Response:** 200 OK

#### 5. Get Notification Preferences
```
GET /api/v1/notifications/preferences
Authorization: Bearer <JWT_TOKEN>
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "transactionsEnabled": true,
  "requestsEnabled": true,
  "alertsEnabled": true,
  "lowBalanceThreshold": 100.00
}
```

#### 6. Update Notification Preferences
```
PUT /api/v1/notifications/preferences
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "transactionsEnabled": true,
  "requestsEnabled": false,
  "alertsEnabled": true,
  "lowBalanceThreshold": 150.00
}
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "transactionsEnabled": true,
  "requestsEnabled": false,
  "alertsEnabled": true,
  "lowBalanceThreshold": 150.00
}
```

### Internal Endpoints (No Authentication Required)

#### Create Notification
```
POST /api/internal/notifications
Content-Type: application/json

{
  "userId": 1,
  "category": "TRANSACTIONS",
  "type": "PAYMENT_RECEIVED",
  "title": "Payment Received",
  "message": "You received $50.00 from John Doe",
  "amount": 50.00,
  "counterparty": "John Doe",
  "eventStatus": "COMPLETED",
  "navigationTarget": "/transactions/123",
  "eventTime": "2024-01-15T10:30:00",
  "metadataJson": "{\"transactionId\": 123}"
}
```

**Response:** 201 CREATED
```json
{
  "id": 1,
  "userId": 1,
  "category": "TRANSACTIONS",
  "type": "PAYMENT_RECEIVED",
  "title": "Payment Received",
  "message": "You received $50.00 from John Doe",
  "amount": 50.00,
  "counterparty": "John Doe",
  "eventStatus": "COMPLETED",
  "navigationTarget": "/transactions/123",
  "eventTime": "2024-01-15T10:30:00",
  "metadataJson": "{\"transactionId\": 123}",
  "isRead": false,
  "createdAt": "2024-01-15T10:30:00"
}
```

## Configuration

### application.yml
```yaml
spring:
  application:
    name: notification-service
  datasource:
    url: jdbc:mysql://localhost:3306/revpay_notifications?createDatabaseIfNotExist=true
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8083

jwt:
  secret: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
  expiration: 86400000
```

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Local Development

1. **Clone the repository**
```bash
cd /path/to/p3-revpay/notification-service
```

2. **Configure the database**
Update `src/main/resources/application.yml` with your MySQL credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revpay_notifications?createDatabaseIfNotExist=true
    username: your_username
    password: your_password
```

3. **Build the application**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```

The service will start on http://localhost:8083

### Using Docker

1. **Build Docker image**
```bash
docker build -t notification-service:latest .
```

2. **Run Docker container**
```bash
docker run -p 8083:8083 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/revpay_notifications \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  notification-service:latest
```

### Using Docker Compose
```yaml
version: '3.8'
services:
  notification-service:
    build: .
    ports:
      - "8083:8083"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/revpay_notifications
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
    depends_on:
      - mysql

  mysql:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=revpay_notifications
    ports:
      - "3306:3306"
```

## Testing

### Run Tests
```bash
mvn test
```

### Sample cURL Commands

**Get Notifications:**
```bash
curl -X GET "http://localhost:8083/api/v1/notifications" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Create Notification (Internal):**
```bash
curl -X POST "http://localhost:8083/api/internal/notifications" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "category": "TRANSACTIONS",
    "type": "PAYMENT_RECEIVED",
    "title": "Payment Received",
    "message": "You received $50.00 from John Doe",
    "amount": 50.00,
    "counterparty": "John Doe",
    "eventStatus": "COMPLETED",
    "navigationTarget": "/transactions/123"
  }'
```

## Integration with Other Services

Other microservices can create notifications by calling the internal API:

```java
// Example from Transaction Service
RestTemplate restTemplate = new RestTemplate();
String url = "http://notification-service:8083/api/internal/notifications";

NotificationRequest request = NotificationRequest.builder()
    .userId(userId)
    .category(NotificationCategory.TRANSACTIONS)
    .type("PAYMENT_SENT")
    .title("Payment Sent")
    .message("You sent $" + amount + " to " + recipientName)
    .amount(amount)
    .counterparty(recipientName)
    .eventStatus("COMPLETED")
    .navigationTarget("/transactions/" + transactionId)
    .eventTime(LocalDateTime.now())
    .build();

restTemplate.postForEntity(url, request, NotificationResponse.class);
```

## Security

- JWT-based authentication for user endpoints
- Internal endpoints are not authenticated (service-to-service communication)
- CORS configured for frontend applications
- Password encoding with BCrypt
- Stateless session management

## Error Handling

The service provides consistent error responses:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Notification not found with ID: 123",
  "path": "/api/v1/notifications/123"
}
```

## Monitoring and Health

Spring Boot Actuator endpoints are available at `/actuator/*`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is part of the RevPay platform - Revature Project.

## Contact

**Owner:** Dileep
**Project:** P3 RevPay
**Service:** Notification Service
