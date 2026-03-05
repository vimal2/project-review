# RevShop Microservices Infrastructure - Summary

## Project Information
- **Location:** `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop`
- **Owner:** Gotam (Order Management & Notification / Review)
- **Spring Boot Version:** 3.2.0
- **Spring Cloud Version:** 2023.0.0
- **Java Version:** 17

## Infrastructure Components Created

### 1. Root-Level Files
- **pom.xml** - Parent Maven POM with all module declarations
- **.gitignore** - Java/Maven gitignore configuration
- **README.md** - Comprehensive project documentation
- **docker-compose.yml** - Docker orchestration for all services
- **INFRASTRUCTURE_SUMMARY.md** - This file

### 2. Config Server (Port 8888)
**Location:** `config-server/`
- ConfigServerApplication.java
- pom.xml (spring-cloud-config-server dependency)
- application.yml (native profile configuration)
- Dockerfile

### 3. Discovery Server (Port 8761)
**Location:** `discovery-server/`
- DiscoveryServerApplication.java
- pom.xml (spring-cloud-starter-netflix-eureka-server dependency)
- application.yml (Eureka configuration)
- Dockerfile

### 4. API Gateway (Port 8080)
**Location:** `api-gateway/`
- ApiGatewayApplication.java
- **filter/JwtAuthenticationFilter.java** - JWT validation and user header injection
- **config/RouteConfig.java** - Route definitions for all services
- **config/CorsConfig.java** - CORS configuration
- pom.xml (gateway, eureka-client, jwt dependencies)
- application.yml
- Dockerfile

### 5. Auth Service (Port 8081)
**Location:** `auth-service/`
- AuthServiceApplication.java
- Complete authentication implementation (already existed)
- pom.xml with Eureka client
- application.yml
- Dockerfile

### 6. Product Service (Port 8082)
**Location:** `product-service/`
- ProductServiceApplication.java
- Product catalog and seller operations (already existed)
- pom.xml with Eureka client
- application.yml
- Dockerfile

### 7. Cart Service (Port 8083)
**Location:** `cart-service/`
- CartServiceApplication.java
- Cart and favorites management (already existed)
- pom.xml with Eureka client
- application.yml
- Dockerfile

### 8. Checkout Service (Port 8084)
**Location:** `checkout-service/`
- CheckoutServiceApplication.java
- Checkout and payment processing (already existed)
- pom.xml with Eureka client
- application.yml
- Dockerfile

### 9. Order Service (Port 8085)
**Location:** `order-service/`
- **OrderServiceApplication.java** ✓ CREATED
- OrderController.java (already existed)
- **ReviewController.java** (already existed)
- **NotificationController.java** (already existed)
- pom.xml ✓ UPDATED (added Eureka client and PostgreSQL driver)
- **application.yml** ✓ CREATED
- **Dockerfile** ✓ CREATED

## API Gateway Routes

All requests go through API Gateway (http://localhost:8080):

| Path | Target Service | Port | Auth Required |
|------|---------------|------|---------------|
| `/api/auth/**` | auth-service | 8081 | No |
| `/api/products/**` | product-service | 8082 | Yes |
| `/api/seller/**` | product-service | 8082 | Yes |
| `/api/cart/**` | cart-service | 8083 | Yes |
| `/api/favorites/**` | cart-service | 8083 | Yes |
| `/api/checkout/**` | checkout-service | 8084 | Yes |
| `/api/payment/**` | checkout-service | 8084 | Yes |
| `/api/orders/**` | order-service | 8085 | Yes |
| `/api/reviews/**` | order-service | 8085 | Yes |
| `/api/notifications/**` | order-service | 8085 | Yes |

## JWT Authentication Flow

1. Client authenticates via `/api/auth/login` (no JWT required)
2. Auth service returns JWT token with claims:
   - `sub`: user email
   - `role`: BUYER or SELLER
   - `userId`: user ID
3. Client includes JWT in Authorization header: `Bearer <token>`
4. API Gateway validates JWT and extracts claims
5. Gateway adds headers to downstream requests:
   - `X-User-Id`: User ID
   - `X-User-Email`: User email
   - `X-User-Role`: User role
6. Backend services read user info from headers (no JWT parsing needed)

## Service Dependencies

### Technology Stack
- **Framework:** Spring Boot 3.2.0
- **Cloud:** Spring Cloud 2023.0.0
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Security:** JWT (jjwt 0.12.3)
- **Database:** PostgreSQL (primary), H2 (cart-service), MySQL (checkout-service)
- **Inter-service Communication:** OpenFeign
- **Build Tool:** Maven
- **Containerization:** Docker

### Maven Dependencies by Service

**Config Server:**
- spring-cloud-config-server

**Discovery Server:**
- spring-cloud-starter-netflix-eureka-server

**API Gateway:**
- spring-cloud-starter-gateway
- spring-cloud-starter-netflix-eureka-client
- jjwt-api, jjwt-impl, jjwt-jackson

**Auth Service:**
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-cloud-starter-netflix-eureka-client
- jjwt-api, jjwt-impl, jjwt-jackson
- postgresql

**Product Service:**
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-cloud-starter-netflix-eureka-client
- postgresql

**Cart Service:**
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign
- h2

**Checkout Service:**
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign
- mysql-connector-j

**Order Service:**
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-cloud-starter-netflix-eureka-client ✓ ADDED
- spring-cloud-starter-openfeign
- postgresql ✓ ADDED (replaced MySQL)
- jjwt-api, jjwt-impl, jjwt-jackson

## Running the Infrastructure

### Using Docker Compose (Recommended)

```bash
# Build all services
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop
mvn clean package -DskipTests

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f [service-name]

# Stop all services
docker-compose down
```

### Running Locally (Development)

Start services in this order:

```bash
# 1. Config Server (8888)
cd config-server && mvn spring-boot:run

# 2. Discovery Server (8761)
cd discovery-server && mvn spring-boot:run

# 3. API Gateway (8080)
cd api-gateway && mvn spring-boot:run

# 4. Business Services (any order)
cd auth-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd cart-service && mvn spring-boot:run
cd checkout-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
```

## Service URLs

- **Eureka Dashboard:** http://localhost:8761
- **API Gateway:** http://localhost:8080
- **Config Server:** http://localhost:8888

## Database Configuration

### PostgreSQL (Shared)
- **Services:** auth-service, product-service, order-service
- **Host:** localhost:5432
- **Database:** revshop
- **Username:** revshop
- **Password:** revshop123

### H2 (In-Memory)
- **Service:** cart-service
- **Console:** http://localhost:8083/h2-console

### MySQL
- **Service:** checkout-service
- **Host:** localhost:3306
- **Database:** revshop_checkout

## File Structure

```
p3-revshop/
├── pom.xml                    # Parent POM
├── .gitignore                 # Git ignore rules
├── README.md                  # Project documentation
├── docker-compose.yml         # Docker orchestration
├── INFRASTRUCTURE_SUMMARY.md  # This file
├── config-server/
│   ├── src/main/java/com/revshop/configserver/
│   │   └── ConfigServerApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── pom.xml
│   └── Dockerfile
├── discovery-server/
│   ├── src/main/java/com/revshop/discoveryserver/
│   │   └── DiscoveryServerApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── pom.xml
│   └── Dockerfile
├── api-gateway/
│   ├── src/main/java/com/revshop/apigateway/
│   │   ├── ApiGatewayApplication.java
│   │   ├── config/
│   │   │   ├── RouteConfig.java
│   │   │   └── CorsConfig.java
│   │   └── filter/
│   │       └── JwtAuthenticationFilter.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── pom.xml
│   └── Dockerfile
├── auth-service/
│   └── [Complete auth implementation]
├── product-service/
│   └── [Complete product implementation]
├── cart-service/
│   └── [Complete cart implementation]
├── checkout-service/
│   └── [Complete checkout implementation]
└── order-service/
    ├── src/main/java/com/revshop/order/
    │   ├── OrderServiceApplication.java ✓ CREATED
    │   ├── controller/
    │   │   ├── OrderController.java
    │   │   ├── ReviewController.java
    │   │   └── NotificationController.java
    │   ├── service/
    │   ├── repository/
    │   ├── entity/
    │   ├── dto/
    │   ├── config/
    │   └── util/
    ├── src/main/resources/
    │   └── application.yml ✓ CREATED
    ├── pom.xml ✓ UPDATED
    └── Dockerfile ✓ CREATED
```

## Key Features Implemented

### API Gateway
✓ JWT authentication filter
✓ User information extraction (userId, email, role)
✓ Request header injection for downstream services
✓ Route configuration for all microservices
✓ CORS configuration for frontend (http://localhost:3000)
✓ Eureka integration

### Service Discovery
✓ All services registered with Eureka
✓ Load balancing support
✓ Dynamic service discovery

### Centralized Configuration
✓ Config server with native profile
✓ Ready for Git-based configuration

### Docker Support
✓ Individual Dockerfiles for all services
✓ Docker Compose orchestration
✓ Health checks configured
✓ PostgreSQL container included

## Next Steps

1. **Database Setup:**
   - Create PostgreSQL database `revshop`
   - Create MySQL database `revshop_checkout` (or use PostgreSQL)

2. **Testing:**
   - Test each service individually
   - Test service discovery registration
   - Test API Gateway routing
   - Test JWT authentication flow

3. **Configuration:**
   - Update JWT secret key (currently hardcoded)
   - Configure externalized secrets
   - Set up Git repository for config server

4. **Enhancement:**
   - Add circuit breakers (Resilience4j)
   - Add distributed tracing (Zipkin/Sleuth)
   - Add API documentation (Swagger/OpenAPI)
   - Add monitoring (Actuator, Prometheus)

## Owner Information

**Project Owner:** Gotam
**Responsibility:** Order Management & Notification / Review Services
**Order Service Features:**
- Order creation and management
- Order status tracking
- Product review system
- Notification management
- Seller order tracking
