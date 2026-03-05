# Cart Service - Project Structure

## Overview
Complete microservice for Cart and Favorites management in RevShop P3.

**Location:** `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/cart-service`
**Port:** 8083
**Owner:** Pavan Kalyan
**Package:** com.revshop.cart

## Directory Structure

```
cart-service/
├── pom.xml                                    # Maven configuration with all dependencies
├── Dockerfile                                  # Docker containerization
├── .gitignore                                  # Git ignore rules
├── README.md                                   # Comprehensive documentation
├── PROJECT_STRUCTURE.md                        # This file
└── src/
    ├── main/
    │   ├── java/com/revshop/cart/
    │   │   ├── CartServiceApplication.java     # Main application entry point
    │   │   ├── model/                          # Entity classes
    │   │   │   ├── Cart.java                   # Cart entity with userId, items, timestamps
    │   │   │   ├── CartItem.java               # Cart item with product details, quantity
    │   │   │   └── Favorite.java               # Favorite entity with product snapshot
    │   │   ├── dto/                            # Data Transfer Objects
    │   │   │   ├── AddToCartRequest.java       # Request to add item to cart
    │   │   │   ├── UpdateCartRequest.java      # Request to update cart item quantity
    │   │   │   ├── CartResponse.java           # Cart response with items and totals
    │   │   │   ├── CartItemResponse.java       # Individual cart item response
    │   │   │   ├── FavoriteRequest.java        # Request to add favorite
    │   │   │   ├── FavoriteResponse.java       # Favorite response with product info
    │   │   │   ├── ProductDto.java             # Product data from product-service
    │   │   │   └── ErrorResponse.java          # Standardized error response
    │   │   ├── repository/                     # JPA Repositories
    │   │   │   ├── CartRepository.java         # findByUserId, existsByUserId
    │   │   │   ├── CartItemRepository.java     # findByCartId, findByCartIdAndProductId
    │   │   │   └── FavoriteRepository.java     # findByUserId, existsByUserIdAndProductId
    │   │   ├── service/                        # Service interfaces
    │   │   │   ├── CartService.java            # Cart service interface
    │   │   │   ├── FavoriteService.java        # Favorite service interface
    │   │   │   └── impl/                       # Service implementations
    │   │   │       ├── CartServiceImpl.java    # Cart business logic with stock validation
    │   │   │       └── FavoriteServiceImpl.java # Favorite business logic
    │   │   ├── controller/                     # REST Controllers
    │   │   │   ├── CartController.java         # Cart endpoints with X-User-Id header
    │   │   │   ├── FavoriteController.java     # Favorite endpoints with X-User-Id header
    │   │   │   └── InternalController.java     # Internal APIs for other services
    │   │   ├── client/                         # Feign Clients
    │   │   │   └── ProductServiceClient.java   # Product service integration with circuit breaker
    │   │   ├── config/                         # Configuration classes
    │   │   │   ├── SecurityConfig.java         # Spring Security configuration
    │   │   │   ├── FeignConfig.java            # Feign client configuration
    │   │   │   └── DataLoader.java             # Sample data loader
    │   │   └── exception/                      # Exception handling
    │   │       ├── ResourceNotFoundException.java
    │   │       ├── InsufficientStockException.java
    │   │       ├── DuplicateResourceException.java
    │   │       └── GlobalExceptionHandler.java # Centralized exception handling
    │   └── resources/
    │       └── application.yml                 # Application configuration
    └── test/
        └── java/com/revshop/cart/              # Test directory structure

```

## File Count Summary

- **Total Java Files:** 30
- **Configuration Files:** 2 (pom.xml, application.yml)
- **Documentation:** 2 (README.md, PROJECT_STRUCTURE.md)
- **Docker:** 1 (Dockerfile)
- **Git:** 1 (.gitignore)

## Entity Details

### Cart (carts table)
- id (PK)
- userId (unique)
- createdAt
- updatedAt
- Relationship: One-to-Many with CartItem

### CartItem (cart_items table)
- id (PK)
- cartId (FK)
- productId
- productName
- productPrice
- quantity
- createdAt
- Relationship: Many-to-One with Cart

### Favorite (favorites table)
- id (PK)
- userId
- productId
- productName
- productPrice
- productImage
- addedAt
- Unique Constraint: (userId, productId)

## API Endpoints Summary

### Cart Endpoints
- `POST /api/cart/items` - Add to cart
- `PUT /api/cart/items/{itemId}` - Update quantity
- `DELETE /api/cart/items/{itemId}` - Remove item
- `GET /api/cart` - View cart
- `DELETE /api/cart` - Clear cart

### Favorite Endpoints
- `POST /api/favorites/{productId}` - Add favorite
- `DELETE /api/favorites/{productId}` - Remove favorite
- `GET /api/favorites` - Get all favorites
- `GET /api/favorites/{productId}/check` - Check if favorited

### Internal Endpoints
- `GET /api/internal/cart/{userId}` - Get cart for checkout
- `DELETE /api/internal/cart/{userId}` - Clear cart after order

## Key Features

1. **User Identification:** X-User-Id header for all user-facing endpoints
2. **Stock Validation:** Real-time validation via product-service
3. **Circuit Breaker:** Resilience4j for product-service calls
4. **Auto-create Cart:** Automatically creates cart on first use
5. **Duplicate Prevention:** Unique constraint on favorites
6. **Product Snapshots:** Stores product details for performance
7. **Comprehensive Error Handling:** Standardized error responses
8. **Sample Data:** DataLoader for testing
9. **Monitoring:** Actuator endpoints with health checks
10. **Containerization:** Docker support

## Dependencies

### Spring Boot Starters
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-actuator
- spring-boot-starter-security

### Database
- H2 (development)
- MySQL Connector (production)

### Microservices
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign
- spring-cloud-starter-circuitbreaker-resilience4j

### Monitoring
- micrometer-registry-prometheus

## Building and Running

### Local Development
```bash
mvn clean install
mvn spring-boot:run
```

### Docker
```bash
docker build -t revshop/cart-service:latest .
docker run -p 8083:8083 revshop/cart-service:latest
```

## Integration Points

### Upstream Dependencies
- **product-service:** Product details and stock validation

### Downstream Consumers
- **checkout-service:** Cart retrieval and clearing
- **api-gateway:** Request routing
- **discovery-server:** Service registration

## Testing

Access the service:
- Service: http://localhost:8083
- H2 Console: http://localhost:8083/h2-console
- Health Check: http://localhost:8083/actuator/health

Sample cURL commands:
```bash
# Add to cart
curl -X POST http://localhost:8083/api/cart/items \
  -H "X-User-Id: 1" \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'

# Get cart
curl -X GET http://localhost:8083/api/cart \
  -H "X-User-Id: 1"

# Add favorite
curl -X POST http://localhost:8083/api/favorites/1 \
  -H "X-User-Id: 1"
```

## Notes

- All user-facing endpoints require X-User-Id header
- Internal endpoints are open for inter-service communication
- Circuit breaker protects against product-service failures
- Sample data is loaded on startup for testing
- H2 console is enabled for development

