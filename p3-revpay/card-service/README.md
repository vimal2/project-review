# Card Service

RevPay Card Management Microservice - Handles card storage, encryption, and management operations.

## Owner
**Vivek**

## Service Information
- **Port:** 8082
- **Package:** com.revpay.card
- **Database:** MySQL (revpay_card_service)

## Features

### Card Management
- Add new payment cards (credit/debit)
- Retrieve user's cards
- Delete cards
- Set default card

### Security
- AES encryption for card numbers and CVV
- JWT-based authentication
- Secure card data storage
- Only last 4 digits exposed in responses

### Integrations
- **Notification Service** (Port 8083) - Sends notifications for card operations

## Technology Stack
- Java 17
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- Spring Cloud OpenFeign
- MySQL
- Lombok
- JWT (jjwt)

## Architecture

### Entities
- **Card** - Card entity with encrypted sensitive data
- **CardType** - Enum (VISA, MASTERCARD, AMEX, DISCOVER, OTHER)
- **PaymentMethodType** - Enum (DEBIT, CREDIT)

### DTOs
- **AddCardRequest** - Request to add new card
- **CardResponse** - Card response with masked data
- **ErrorResponse** - Standard error response

### Key Components
- **CardService** - Business logic for card operations
- **EncryptionService** - AES encryption/decryption
- **NotificationServiceClient** - Feign client for notifications
- **CardRepository** - Data access layer

## API Endpoints

### Card Operations

#### Add Card
```
POST /api/v1/cards
Headers: X-User-Id: {userId}, Authorization: Bearer {token}
Body:
{
  "cardHolderName": "John Doe",
  "cardNumber": "4111111111111111",
  "expiryDate": "12/25",
  "cvv": "123",
  "cardType": "VISA",
  "paymentMethodType": "DEBIT"
}
Response: 201 Created
```

#### Get User Cards
```
GET /api/v1/cards
Headers: X-User-Id: {userId}, Authorization: Bearer {token}
Response: 200 OK
[
  {
    "id": 1,
    "cardHolderName": "John Doe",
    "lastFourDigits": "1111",
    "expiryDate": "12/25",
    "cardType": "VISA",
    "paymentMethodType": "DEBIT",
    "isDefault": true
  }
]
```

#### Delete Card
```
DELETE /api/v1/cards/{id}
Headers: X-User-Id: {userId}, Authorization: Bearer {token}
Response: 200 OK
{
  "message": "Card deleted successfully"
}
```

#### Set Default Card
```
PATCH /api/v1/cards/{id}/default
Headers: X-User-Id: {userId}, Authorization: Bearer {token}
Response: 200 OK
{
  "id": 1,
  "cardHolderName": "John Doe",
  "lastFourDigits": "1111",
  "expiryDate": "12/25",
  "cardType": "VISA",
  "paymentMethodType": "DEBIT",
  "isDefault": true
}
```

## Configuration

### Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revpay_card_service
    username: root
    password: root
```

### Security Configuration
```yaml
jwt:
  secret: {your-secret-key}

app:
  security:
    encryption-key: {your-encryption-key}
```

### External Services
```yaml
notification:
  service:
    url: http://localhost:8083
```

## Setup and Installation

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Local Development

1. **Clone the repository**
```bash
cd /path/to/p3-revpay/card-service
```

2. **Configure MySQL**
```bash
mysql -u root -p
CREATE DATABASE revpay_card_service;
```

3. **Update application.yml**
Update database credentials in `src/main/resources/application.yml`

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The service will start on port 8082.

### Docker Deployment

1. **Build Docker image**
```bash
docker build -t card-service:latest .
```

2. **Run container**
```bash
docker run -p 8082:8082 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/revpay_card_service \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  card-service:latest
```

## Exception Handling

### Custom Exceptions
- **ResourceNotFoundException** - Card not found (404)
- **CardLimitExceededException** - Maximum cards limit exceeded (400)
- **IllegalArgumentException** - Invalid input data (400)

### Global Exception Handler
Provides consistent error responses across all endpoints with timestamp, status, error type, and message.

## Security Features

### Data Encryption
- Card numbers encrypted using AES
- CVV encrypted using AES
- Only last 4 digits stored in plain text

### Authentication
- JWT token validation
- User ID extraction from headers
- Request authorization checks

### CORS Configuration
- Configured for frontend origins
- Supports credentials
- Allows all standard HTTP methods

## Validation

### Card Number
- Must be 13-19 digits
- Validated with regex pattern

### Expiry Date
- Must be in MM/YY format
- Validated with regex pattern

### CVV
- Must be 3-4 digits
- Validated with regex pattern

## Business Rules

1. **Maximum Cards:** Users can have up to 10 cards
2. **Default Card:** First card is automatically set as default
3. **Delete Default:** When default card is deleted, next card becomes default
4. **User Isolation:** Users can only access their own cards

## Monitoring

### Actuator Endpoints
- `/actuator/health` - Health check
- `/actuator/info` - Application info
- `/actuator/metrics` - Metrics

### Logging
- DEBUG level for application code
- SQL logging enabled
- Security audit logging

## Testing

### Run Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify
```

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check credentials in application.yml
   - Ensure database exists

2. **Port Already in Use**
   - Change port in application.yml
   - Kill process using port 8082

3. **Encryption Errors**
   - Verify encryption key is configured
   - Check key length (must be valid AES key)

## Contributing

1. Create feature branch
2. Make changes
3. Write tests
4. Submit pull request

## License
Proprietary - RevPay Project

## Contact
**Owner:** Vivek
**Service:** Card Service
**Port:** 8082
