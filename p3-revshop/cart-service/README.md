# Cart Service - RevShop

## Overview
The Cart Service is a microservice responsible for managing shopping carts and user favorites in the RevShop e-commerce platform. It provides comprehensive functionality for cart operations, favorite product management, and integrates with the product-service for real-time product data and stock validation.

**Owner:** Pavan Kalyan
**Port:** 8083
**Package:** com.revshop.cart

## Features

### Cart Management
- Add products to cart with quantity validation
- Update cart item quantities
- Remove items from cart
- View complete cart with totals
- Clear entire cart
- Auto-create cart for new users
- Real-time stock validation via product-service
- Circuit breaker pattern for resilience

### Favorites Management
- Add products to favorites
- Remove products from favorites
- View all favorite products
- Check if a product is favorited
- Prevent duplicate favorites
- Store product snapshot for quick access

### Internal APIs
- Expose cart data to checkout-service
- Allow order-service to clear cart after successful checkout

## Technology Stack

- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** H2 (in-memory) / MySQL (production)
- **Service Discovery:** Netflix Eureka Client
- **API Client:** OpenFeign
- **Resilience:** Resilience4j Circuit Breaker
- **Monitoring:** Spring Boot Actuator, Micrometer Prometheus
- **Security:** Spring Security
- **Build Tool:** Maven

## Architecture

### Entities

#### Cart
- `id` - Primary key
- `userId` - User identifier (unique)
- `items` - List of cart items
- `createdAt` - Timestamp
- `updatedAt` - Timestamp

#### CartItem
- `id` - Primary key
- `cartId` - Foreign key to Cart
- `productId` - Product identifier
- `productName` - Product name snapshot
- `productPrice` - Product price snapshot
- `quantity` - Item quantity
- `createdAt` - Timestamp

#### Favorite
- `id` - Primary key
- `userId` - User identifier
- `productId` - Product identifier
- `productName` - Product name snapshot
- `productPrice` - Product price snapshot
- `productImage` - Product image URL
- `addedAt` - Timestamp
- **Unique Constraint:** (userId, productId)

### API Endpoints

#### Cart Endpoints

**Add to Cart**
```
POST /api/cart/items
Headers: X-User-Id: {userId}
Body: {
  "productId": 1,
  "quantity": 2
}
```

**Update Cart Item**
```
PUT /api/cart/items/{itemId}
Headers: X-User-Id: {userId}
Body: {
  "quantity": 3
}
```

**Remove from Cart**
```
DELETE /api/cart/items/{itemId}
Headers: X-User-Id: {userId}
```

**Get Cart**
```
GET /api/cart
Headers: X-User-Id: {userId}
```

**Clear Cart**
```
DELETE /api/cart
Headers: X-User-Id: {userId}
```

#### Favorite Endpoints

**Add to Favorites**
```
POST /api/favorites/{productId}
Headers: X-User-Id: {userId}
```

**Remove from Favorites**
```
DELETE /api/favorites/{productId}
Headers: X-User-Id: {userId}
```

**Get All Favorites**
```
GET /api/favorites
Headers: X-User-Id: {userId}
```

**Check if Favorited**
```
GET /api/favorites/{productId}/check
Headers: X-User-Id: {userId}
Response: { "isFavorite": true/false }
```

#### Internal Endpoints (for inter-service communication)

**Get Cart by User ID**
```
GET /api/internal/cart/{userId}
```

**Clear Cart by User ID**
```
DELETE /api/internal/cart/{userId}
```

## Configuration

### Application Properties (application.yml)

```yaml
server:
  port: 8083

spring:
  application:
    name: cart-service
  datasource:
    url: jdbc:h2:mem:cartdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

resilience4j:
  circuitbreaker:
    instances:
      product-service:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        failureRateThreshold: 50
```

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker (optional)

### Local Development

1. **Build the project:**
```bash
mvn clean install
```

2. **Run the application:**
```bash
mvn spring-boot:run
```

3. **Access the service:**
- Service URL: http://localhost:8083
- H2 Console: http://localhost:8083/h2-console
- Actuator Health: http://localhost:8083/actuator/health

### Docker Deployment

1. **Build Docker image:**
```bash
docker build -t revshop/cart-service:latest .
```

2. **Run container:**
```bash
docker run -p 8083:8083 \
  -e EUREKA_URI=http://discovery-server:8761/eureka \
  revshop/cart-service:latest
```

## Dependencies

### External Services
- **product-service:** For product details and stock validation
- **discovery-server:** For service registration and discovery
- **api-gateway:** For routing and authentication (optional)

### Feign Clients
- `ProductServiceClient` - Communicates with product-service
  - Get product details
  - Validate stock availability
  - Circuit breaker with fallback methods

## Error Handling

The service implements comprehensive error handling:

- **404 Not Found:** Cart, cart item, or favorite not found
- **400 Bad Request:** Insufficient stock, invalid quantity
- **409 Conflict:** Duplicate favorite
- **422 Unprocessable Entity:** Validation errors
- **500 Internal Server Error:** Unexpected errors

All errors return a consistent ErrorResponse format:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Cart not found for user: 123",
  "timestamp": "2024-03-05T10:30:00"
}
```

## Security

- Spring Security enabled with basic authentication
- X-User-Id header for user identification
- Internal endpoints accessible without authentication
- H2 console accessible for development

## Monitoring and Health

### Actuator Endpoints
- `/actuator/health` - Health check
- `/actuator/info` - Service information
- `/actuator/metrics` - Service metrics
- `/actuator/prometheus` - Prometheus metrics

### Circuit Breaker
- Monitors product-service calls
- Automatic fallback on failures
- Health indicators exposed via actuator

## Sample Data

The service includes a DataLoader that populates sample data on startup:
- 2 sample carts with items
- 3 sample favorites
- For testing and demonstration purposes

## Testing

Run tests with:
```bash
mvn test
```

## Development Team

**Owner:** Pavan Kalyan
**Module:** Cart Management
**Responsibilities:**
- Shopping cart functionality
- Favorites management
- Product service integration
- Stock validation
- Circuit breaker implementation

## Future Enhancements

- Redis cache for cart data
- Cart abandonment tracking
- Cart expiration policy
- Favorite product recommendations
- Shopping cart sharing
- Save for later functionality
- Price change notifications for favorites

## Support

For issues or questions, contact the development team or create an issue in the project repository.

## License

Copyright 2024 RevShop. All rights reserved.
