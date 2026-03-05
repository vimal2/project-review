# REVSHOP PROJECT - TEAM ROLES AND RESPONSIBILITIES

## Document Overview

This document provides a comprehensive breakdown of team member responsibilities across both the P2 (Monolithic) and P3 (Microservices) versions of the RevShop E-commerce platform. Each team member owns specific functional domains and is responsible for both backend services and API development.

**Project Information:**
- **P2 Repository:** `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/RevShop_P2`
- **P3 Repository:** `/Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop`
- **Technology Stack:** Spring Boot, Spring Cloud, MySQL, PostgreSQL, H2, JWT, Eureka, API Gateway
- **Architecture:** Monolith (P2) → Microservices (P3)

---

## Team Overview Table

| Developer | Primary Domain | P2 Components | P3 Service(s) | P3 Port(s) |
|-----------|---------------|---------------|---------------|------------|
| **Manjula** | Login, Registration & Authentication | AuthController, AuthService, User Entity | auth-service | 8081 |
| **Kavya** | Seller Dashboard | SellerProductController, ProductController (seller endpoints), SellerProductService | product-service (seller operations) | 8082 |
| **Jatin** | Buyer Product Management | ProductController (buyer endpoints), ProductService | product-service (buyer operations) | 8082 |
| **Pavan Kalyan** | Cart & Favorites Management | CartController, FavoriteController, CartService, FavoriteService | cart-service | 8083 |
| **Anusha** | Checkout & Payment | OrderController (checkout/payment), CheckoutService, PaymentService | checkout-service | 8084 |
| **Gotam** | Order, Notification, Review & Infrastructure | OrderController, ReviewController, NotificationController, Order/Review/Notification Services | order-service + Infrastructure | 8085 |

---

## Port Assignments (P3 Microservices)

| Service | Port | Database | Purpose |
|---------|------|----------|---------|
| **discovery-server** | 8761 | N/A | Eureka Service Registry |
| **api-gateway** | 8080 | N/A | API Gateway (Route requests) |
| **auth-service** | 8081 | H2 (in-memory) | User authentication & JWT |
| **product-service** | 8082 | MySQL | Product catalog & seller dashboard |
| **cart-service** | 8083 | H2 (in-memory) | Shopping cart & favorites |
| **checkout-service** | 8084 | MySQL | Checkout sessions & payments |
| **order-service** | 8085 | PostgreSQL | Orders, reviews & notifications |
| **config-server** | 8888 | N/A | Centralized configuration |

---

## Technology Stack

### Backend Technologies
- **Framework:** Spring Boot 3.x
- **Language:** Java 17+
- **Build Tool:** Maven
- **ORM:** Hibernate/JPA
- **Security:** Spring Security + JWT
- **Validation:** Jakarta Validation

### Microservices Infrastructure (P3)
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Circuit Breaker:** Resilience4j
- **Service Communication:** OpenFeign
- **Configuration:** Spring Cloud Config Server

### Databases
- **MySQL:** Product Service, Checkout Service
- **PostgreSQL:** Order Service
- **H2 (In-Memory):** Auth Service, Cart Service

### Testing & Quality
- **Unit Testing:** JUnit 5, Mockito
- **API Testing:** RestAssured (recommended)
- **Code Coverage:** JaCoCo

### DevOps & Deployment
- **Containerization:** Docker
- **Orchestration:** Docker Compose
- **Version Control:** Git

---

# DEVELOPER-SPECIFIC RESPONSIBILITIES

---

## 1. MANJULA - Authentication & User Management

### Primary Domain
**Login, Registration, and Authentication**

### P2 Monolithic Responsibilities

#### Controllers
- **AuthController** (`/api/auth`)
  ```java
  POST   /api/auth/register          // User registration
  POST   /api/auth/login             // User login (returns JWT)
  POST   /api/auth/forgot-password   // Initiate password reset
  POST   /api/auth/reset-password    // Reset password with token
  ```

#### Services
- **AuthService** (Interface)
  - `register(RegisterRequest request): String`
  - `loginWithToken(LoginRequest request): String`
  - `forgotPassword(ForgotPasswordRequest request): void`
  - `resetPassword(ResetPasswordRequest request): String`

- **AuthServiceImpl** (Implementation)
  - User registration logic
  - Password encryption (BCrypt)
  - JWT token generation
  - Password reset token management
  - User validation

#### Entities
- **User**
  ```java
  - userId: Long (Primary Key)
  - email: String (Unique)
  - password: String (Encrypted)
  - firstName: String
  - lastName: String
  - role: Role (BUYER/SELLER)
  - resetToken: String
  - createdAt: LocalDateTime
  ```

- **Role** (Enum)
  ```java
  BUYER, SELLER
  ```

#### Repositories
- **UserRepository** (JpaRepository)
  - `findByEmail(String email): Optional<User>`
  - `existsByEmail(String email): boolean`
  - `findByResetToken(String token): Optional<User>`

#### DTOs
- `RegisterRequest` - User registration data
- `LoginRequest` - Login credentials
- `ForgotPasswordRequest` - Email for password reset
- `ResetPasswordRequest` - Token and new password

#### Key Features to Implement (P2)
1. User registration with validation
2. Email uniqueness check
3. Password encryption using BCrypt
4. JWT token generation on login
5. Password reset workflow
6. Role-based access (BUYER/SELLER)
7. Input validation for all endpoints
8. Custom exception handling

---

### P3 Microservices Responsibilities

#### Service Details
- **Service Name:** auth-service
- **Port:** 8081
- **Base Path:** `/api/auth`
- **Database:** H2 (in-memory)

#### Entities
- **User**
  ```java
  package com.revshop.auth.entity

  - userId: Long (Primary Key)
  - email: String (Unique, Indexed)
  - password: String (Encrypted)
  - firstName: String
  - lastName: String
  - role: Role (BUYER/SELLER)
  - resetToken: String
  - tokenExpiry: LocalDateTime
  - createdAt: LocalDateTime
  - updatedAt: LocalDateTime
  ```

- **Role** (Enum)
  ```java
  BUYER, SELLER
  ```

#### DTOs
```
com.revshop.auth.dto/
├── RegisterRequest.java          // User registration
├── LoginRequest.java             // Login credentials
├── AuthResponse.java             // JWT token response
├── ForgotPasswordRequest.java    // Password reset request
├── ResetPasswordRequest.java     // Reset password with token
├── UserValidationResponse.java   // Token validation response
└── ErrorResponse.java            // Error handling
```

#### Repositories
- **UserRepository** (JpaRepository)
  ```java
  findByEmail(String email): Optional<User>
  existsByEmail(String email): boolean
  findByResetToken(String token): Optional<User>
  ```

#### Services
- **AuthService** (Interface)
- **AuthServiceImpl** (Implementation)
  ```java
  register(RegisterRequest): String
  login(LoginRequest): AuthResponse
  forgotPassword(ForgotPasswordRequest): String
  resetPassword(ResetPasswordRequest): String
  validateToken(String token): UserValidationResponse
  getUserById(Long userId): User
  ```

#### Controllers
- **AuthController** (`/api/auth`)
  ```java
  POST   /api/auth/register              // User registration
  POST   /api/auth/login                 // User login (returns JWT)
  POST   /api/auth/forgot-password       // Password reset request
  POST   /api/auth/reset-password        // Reset password
  GET    /api/auth/validate              // Validate JWT (internal)
  GET    /api/auth/user/{userId}         // Get user by ID (internal)
  ```

#### Key Features to Implement (P3)
1. **JWT Token Generation & Validation**
   - Create utility class for JWT operations
   - Token expiration handling (e.g., 24 hours)
   - Token refresh mechanism (optional)

2. **User Registration**
   - Email validation and uniqueness check
   - Password strength validation
   - BCrypt password encryption
   - Automatic role assignment

3. **User Authentication**
   - Login with email/password
   - JWT token generation on successful login
   - Return user details with token

4. **Password Reset Flow**
   - Generate secure reset token
   - Store token with expiry time
   - Validate token before reset
   - Update password and clear token

5. **Token Validation Endpoint**
   - Internal endpoint for API Gateway
   - Validate JWT and extract user info
   - Return user ID and role

6. **Service Registration**
   - Register with Eureka Discovery Server
   - Health check endpoints
   - Actuator configuration

7. **Error Handling**
   - Invalid credentials
   - Duplicate email
   - Expired reset token
   - Invalid JWT token

#### Configuration (application.yml)
```yaml
server:
  port: 8081

spring:
  application:
    name: auth-service
  datasource:
    url: jdbc:h2:mem:authdb
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### Package Structure
```
com.revshop.auth/
├── controller/
│   └── AuthController.java
├── service/
│   ├── AuthService.java
│   └── impl/
│       └── AuthServiceImpl.java
├── repository/
│   └── UserRepository.java
├── entity/
│   ├── User.java
│   └── Role.java
├── dto/
│   ├── RegisterRequest.java
│   ├── LoginRequest.java
│   ├── AuthResponse.java
│   ├── ForgotPasswordRequest.java
│   ├── ResetPasswordRequest.java
│   └── UserValidationResponse.java
├── util/
│   └── JwtUtil.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── CustomExceptions.java
└── AuthServiceApplication.java
```

---

## 2. KAVYA - Seller Dashboard

### Primary Domain
**Seller Product Management Dashboard**

### P2 Monolithic Responsibilities

#### Controllers
- **SellerProductController** (`/api/seller/products`)
  ```java
  POST   /api/seller/products                 // Add new product
  PUT    /api/seller/products/{id}            // Update product
  DELETE /api/seller/products/{id}            // Delete product
  GET    /api/seller/products/{sellerId}      // Get seller's products
  ```

- **ProductController** (`/api/products`) - Seller endpoints
  ```java
  POST   /api/products                        // Add product
  PUT    /api/products/{id}                   // Update product
  DELETE /api/products/{id}                   // Delete product
  PUT    /api/products/{id}/threshold         // Set stock threshold
  GET    /api/products                        // Get all products
  ```

#### Services
- **SellerProductService** (Interface)
  - `addProduct(SellerProductRequest): String`
  - `updateProduct(Long id, SellerProductRequest): String`
  - `deleteProduct(Long id): String`
  - `getSellerProducts(Long sellerId): List<ProductDto>`

- **ProductService** (Interface) - Seller methods
  - `addProduct(ProductRequest): Product`
  - `updateProduct(Long id, ProductUpdateRequest): Product`
  - `deleteProduct(Long id, Long sellerId): void`
  - `setStockThreshold(Long id, ThresholdRequest): Product`
  - `getAllProducts(): List<Product>`

#### Entities
- **Product**
  ```java
  - productId: Long
  - name: String
  - description: String
  - price: Double
  - stockQuantity: Integer
  - stockThreshold: Integer
  - sellerId: Long
  - categoryId: Long
  - imageUrl: String
  - createdAt: LocalDateTime
  - updatedAt: LocalDateTime
  ```

- **Category**
  ```java
  - categoryId: Long
  - name: String
  - description: String
  ```

#### Repositories
- **ProductRepository** (JpaRepository)
  - `findBySellerId(Long sellerId): List<Product>`
  - `findByCategory(Category category): List<Product>`
  - `existsByIdAndSellerId(Long id, Long sellerId): boolean`

- **CategoryRepository** (JpaRepository)
  - `findByName(String name): Optional<Category>`

#### DTOs
- `ProductRequest` - Add product request
- `ProductUpdateRequest` - Update product details
- `SellerProductRequest` - Seller-specific product request
- `ProductDto` - Product response
- `ThresholdRequest` - Set stock alert threshold

#### Key Features to Implement (P2)
1. Add new products with validation
2. Update product details (name, price, stock, etc.)
3. Delete products (only own products)
4. View all products by seller
5. Set stock threshold for low inventory alerts
6. Manage product categories
7. Upload product images
8. Track inventory levels

---

### P3 Microservices Responsibilities

#### Service Details
- **Service Name:** product-service
- **Port:** 8082
- **Base Path:** `/api/seller/products`
- **Database:** MySQL (revshop_products)

#### Entities
```
com.revshop.product.entity/
├── Product.java
└── Category.java
```

**Product Entity:**
```java
- productId: Long (Primary Key)
- name: String (Not null)
- description: String
- price: BigDecimal (Not null, min: 0)
- stockQuantity: Integer (Not null, min: 0)
- stockThreshold: Integer (Default: 10)
- sellerId: Long (Not null, Indexed)
- categoryId: Long
- imageUrl: String
- isActive: Boolean (Default: true)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

**Category Entity:**
```java
- categoryId: Long (Primary Key)
- name: String (Unique)
- description: String
```

#### DTOs
```
com.revshop.product.dto/
├── ProductRequest.java           // Add product
├── ProductUpdateRequest.java     // Update product
├── ProductResponse.java          // Product details
├── SellerProductResponse.java    // Seller view of product
├── ThresholdRequest.java         // Stock threshold
├── CategoryRequest.java          // Add/update category
├── CategoryResponse.java         // Category details
├── StockUpdateRequest.java       // Update stock
└── ErrorResponse.java            // Error handling
```

#### Repositories
- **ProductRepository** (JpaRepository)
  ```java
  findBySellerId(Long sellerId): List<Product>
  findBySellerIdAndIsActive(Long sellerId, boolean active): List<Product>
  existsByProductIdAndSellerId(Long productId, Long sellerId): boolean
  findByStockQuantityLessThanStockThreshold(): List<Product>
  ```

- **CategoryRepository** (JpaRepository)
  ```java
  findByName(String name): Optional<Category>
  existsByName(String name): boolean
  ```

#### Services
- **ProductService** (Interface)
- **ProductServiceImpl** (Implementation)
  ```java
  // Seller operations
  addProduct(ProductRequest, Long sellerId): ProductResponse
  updateProduct(Long id, ProductUpdateRequest, Long sellerId): ProductResponse
  deleteProduct(Long id, Long sellerId): void
  getSellerProducts(Long sellerId): List<SellerProductResponse>
  setStockThreshold(Long id, ThresholdRequest, Long sellerId): ProductResponse
  updateStock(Long id, StockUpdateRequest): void

  // Buyer operations (shared with Jatin)
  getAllProducts(): List<ProductResponse>
  getProductById(Long id): ProductResponse
  getProductsByCategory(Long categoryId, Pageable): Page<ProductResponse>
  searchProducts(String keyword): List<ProductResponse>
  ```

#### Controllers

**1. SellerProductController** (`/api/seller/products`)
```java
POST   /api/seller/products              // Add product
PUT    /api/seller/products/{id}         // Update product
DELETE /api/seller/products/{id}         // Delete product
GET    /api/seller/products              // Get seller's products
PUT    /api/seller/products/{id}/threshold  // Set stock threshold
```

**2. CategoryController** (`/api/categories`)
```java
POST   /api/categories                   // Add category (admin)
GET    /api/categories                   // Get all categories
GET    /api/categories/{id}              // Get category by ID
```

**3. InternalController** (`/api/internal/products`)
```java
GET    /api/internal/products/{id}       // Get product (internal)
PUT    /api/internal/products/{id}/stock // Update stock (internal)
POST   /api/internal/products/validate   // Validate products (internal)
```

#### Key Features to Implement (P3)
1. **Product Management**
   - Add new products with validation
   - Update product details (price, description, stock)
   - Soft delete products (isActive flag)
   - View all seller's products
   - Filter products by status (active/inactive)

2. **Inventory Management**
   - Set stock threshold for alerts
   - Get low stock products
   - Update stock quantity
   - Track stock history (optional)

3. **Category Management**
   - Create new categories
   - List all categories
   - Assign products to categories

4. **Authorization**
   - Extract seller ID from JWT (X-User-Id header)
   - Verify product ownership before updates
   - Prevent unauthorized access

5. **Internal Endpoints**
   - Provide product details to other services
   - Update stock after order placement
   - Validate product availability

6. **Service Integration**
   - Register with Eureka
   - Health checks
   - Circuit breaker configuration

7. **Low Stock Notifications**
   - Track products below threshold
   - Send alerts to order-service (async)

#### Configuration (application.yml)
```yaml
server:
  port: 8082

spring:
  application:
    name: product-service
  datasource:
    url: jdbc:mysql://localhost:3306/revshop_products
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### Package Structure
```
com.revshop.product/
├── controller/
│   ├── SellerProductController.java
│   ├── ProductController.java
│   ├── CategoryController.java
│   └── InternalController.java
├── service/
│   ├── ProductService.java
│   └── impl/
│       └── ProductServiceImpl.java
├── repository/
│   ├── ProductRepository.java
│   └── CategoryRepository.java
├── entity/
│   ├── Product.java
│   └── Category.java
├── dto/
│   └── (11 DTO files)
├── exception/
│   └── GlobalExceptionHandler.java
└── ProductServiceApplication.java
```

---

## 3. JATIN - Buyer Product Management

### Primary Domain
**Buyer Product Browsing, Search, and Viewing**

### P2 Monolithic Responsibilities

#### Controllers
- **ProductController** (`/api/products`) - Buyer endpoints
  ```java
  GET    /api/products/category/{categoryId}   // Browse by category
  GET    /api/products/search?keyword=         // Search products
  GET    /api/products/details/{id}            // Product details
  ```

#### Services
- **ProductService** (Interface) - Buyer methods
  - `getProductsByCategory(Long categoryId, int page, int size): Page<ProductDto>`
  - `searchProducts(String keyword): List<ProductDto>`
  - `getProductDetails(Long id): ProductDto`

#### Entities
- **Product** (shared with Kavya)
- **Category** (shared with Kavya)

#### Repositories
- **ProductRepository** (JpaRepository)
  - `findByCategoryId(Long categoryId, Pageable): Page<Product>`
  - `findByNameContainingIgnoreCase(String keyword): List<Product>`
  - `findByDescriptionContainingIgnoreCase(String keyword): List<Product>`
  - Custom search queries

#### DTOs
- `ProductDto` - Product display for buyers
- `ProductSearchResponse` - Search results

#### Key Features to Implement (P2)
1. Browse products by category with pagination
2. Search products by keyword (name/description)
3. View product details (name, price, stock, images)
4. Filter products (price range, availability)
5. Sort products (price, name, newest)
6. Display product ratings/reviews
7. Show stock availability status

---

### P3 Microservices Responsibilities

#### Service Details
- **Service Name:** product-service (shared with Kavya)
- **Port:** 8082
- **Base Path:** `/api/products`
- **Database:** MySQL (revshop_products)

#### Entities
Same as Kavya (shared Product and Category entities)

#### DTOs
```
com.revshop.product.dto/
├── ProductResponse.java           // Product details for buyers
├── ProductSearchResponse.java     // Search results
└── CategoryResponse.java          // Category details
```

#### Services
- **ProductService** (Interface) - Buyer methods
  ```java
  getAllProducts(): List<ProductResponse>
  getProductById(Long id): ProductResponse
  getProductsByCategory(Long categoryId, Pageable): Page<ProductResponse>
  searchProducts(String keyword): List<ProductSearchResponse>
  getProductDetails(Long id): ProductResponse
  getCategories(): List<CategoryResponse>
  ```

#### Controllers
- **ProductController** (`/api/products`)
  ```java
  GET    /api/products                      // Get all products
  GET    /api/products/{id}                 // Product details
  GET    /api/products/category/{categoryId} // Browse by category
  GET    /api/products/search?keyword=      // Search products
  ```

#### Key Features to Implement (P3)
1. **Product Browsing**
   - Get all active products
   - Browse products by category
   - Pagination support (page, size)
   - Filter out inactive products

2. **Product Search**
   - Search by product name
   - Search by description
   - Search by category
   - Return relevant results

3. **Product Details**
   - Get complete product information
   - Include category details
   - Show stock availability
   - Display seller information (optional)

4. **Category Browsing**
   - List all categories
   - Get products by category with pagination
   - Sort options (price, name, date)

5. **Caching (Optional)**
   - Cache frequently accessed products
   - Cache category listings
   - Improve performance

6. **Service Integration**
   - Provide data to cart-service
   - Provide data to checkout-service
   - Internal endpoints for other services

#### Collaboration with Kavya
- **Shared Service:** Both use product-service
- **Kavya:** Manages seller endpoints (add, update, delete)
- **Jatin:** Manages buyer endpoints (browse, search, view)
- **Shared Repository:** ProductRepository
- **Shared Entity:** Product, Category

#### Package Structure
```
com.revshop.product/
├── controller/
│   ├── ProductController.java (Jatin's responsibility)
│   └── SellerProductController.java (Kavya's responsibility)
├── service/
│   ├── ProductService.java (Shared)
│   └── impl/
│       └── ProductServiceImpl.java (Shared)
└── (Other packages shared with Kavya)
```

---

## 4. PAVAN KALYAN - Cart & Favorites Management

### Primary Domain
**Shopping Cart and Favorites (Wishlist)**

### P2 Monolithic Responsibilities

#### Controllers

**1. CartController** (`/api/cart`)
```java
POST   /api/cart/{userId}/items             // Add to cart
PUT    /api/cart/{userId}/items/{cartItemId} // Update quantity
DELETE /api/cart/{userId}/items/{cartItemId} // Remove from cart
GET    /api/cart/{userId}                   // View cart
DELETE /api/cart/{userId}                   // Clear cart
```

**2. FavoriteController** (`/api/favorites`)
```java
POST   /api/favorites/{userId}              // Add to favorites
DELETE /api/favorites/{userId}/{favoriteId} // Remove from favorites
GET    /api/favorites/{userId}              // View favorites
```

#### Services

**CartService** (Interface)
- `addToCart(Long userId, AddToCartRequest): CartResponse`
- `updateCartItemQuantity(Long userId, Long cartItemId, UpdateCartRequest): CartResponse`
- `removeFromCart(Long userId, Long cartItemId): CartResponse`
- `getCart(Long userId): CartResponse`
- `clearCart(Long userId): void`

**FavoriteService** (Interface)
- `addToFavorites(Long userId, FavoriteRequest): String`
- `removeFromFavorites(Long userId, Long favoriteId): String`
- `getFavorites(Long userId): List<ProductDto>`

#### Entities

**Cart**
```java
- cartId: Long (Primary Key)
- userId: Long (Unique)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
- cartItems: List<CartItem> (One-to-Many)
```

**CartItem**
```java
- cartItemId: Long (Primary Key)
- cart: Cart (Many-to-One)
- productId: Long
- quantity: Integer
- price: Double
- addedAt: LocalDateTime
```

**Favorite**
```java
- favoriteId: Long (Primary Key)
- userId: Long
- productId: Long
- addedAt: LocalDateTime
```

#### Repositories

**CartRepository** (JpaRepository)
- `findByUserId(Long userId): Optional<Cart>`
- `existsByUserId(Long userId): boolean`

**CartItemRepository** (JpaRepository)
- `findByCartAndProductId(Cart cart, Long productId): Optional<CartItem>`
- `deleteByCartAndCartItemId(Cart cart, Long cartItemId): void`

**FavoriteRepository** (JpaRepository)
- `findByUserId(Long userId): List<Favorite>`
- `existsByUserIdAndProductId(Long userId, Long productId): boolean`
- `deleteByUserIdAndFavoriteId(Long userId, Long favoriteId): void`

#### DTOs
- `AddToCartRequest` - Add product to cart
- `UpdateCartRequest` - Update cart item quantity
- `CartResponse` - Cart details with items
- `CartItemResponse` - Cart item details
- `FavoriteRequest` - Add to favorites

#### Key Features to Implement (P2)
1. Add products to cart with quantity
2. Update cart item quantities
3. Remove items from cart
4. View cart with total price calculation
5. Clear entire cart
6. Add products to favorites/wishlist
7. Remove from favorites
8. View all favorite products
9. Prevent duplicate cart items (increase quantity)
10. Validate product availability before adding

---

### P3 Microservices Responsibilities

#### Service Details
- **Service Name:** cart-service
- **Port:** 8083
- **Base Path:** `/api/cart`, `/api/favorites`
- **Database:** H2 (in-memory)

#### Entities
```
com.revshop.cart.model/
├── Cart.java
├── CartItem.java
└── Favorite.java
```

**Cart Entity:**
```java
- cartId: Long (Primary Key)
- userId: Long (Unique, Indexed)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
- items: List<CartItem> (One-to-Many, Cascade)
```

**CartItem Entity:**
```java
- cartItemId: Long (Primary Key)
- cart: Cart (Many-to-One)
- productId: Long (Not null)
- productName: String
- productPrice: BigDecimal
- quantity: Integer (min: 1)
- addedAt: LocalDateTime
```

**Favorite Entity:**
```java
- favoriteId: Long (Primary Key)
- userId: Long (Indexed)
- productId: Long (Indexed)
- productName: String
- productPrice: BigDecimal
- imageUrl: String
- addedAt: LocalDateTime
```

#### DTOs
```
com.revshop.cart.dto/
├── AddToCartRequest.java         // Add to cart
├── UpdateCartRequest.java        // Update quantity
├── CartResponse.java             // Cart with items
├── CartItemResponse.java         // Cart item details
├── FavoriteRequest.java          // Add to favorites
├── FavoriteResponse.java         // Favorite item
├── ProductDto.java               // Product info from product-service
└── ErrorResponse.java            // Error handling
```

#### Repositories

**CartRepository** (JpaRepository)
```java
findByUserId(Long userId): Optional<Cart>
existsByUserId(Long userId): boolean
deleteByUserId(Long userId): void
```

**CartItemRepository** (JpaRepository)
```java
findByCartAndProductId(Cart cart, Long productId): Optional<CartItem>
findByCart(Cart cart): List<CartItem>
deleteByCartAndCartItemId(Cart cart, Long cartItemId): void
```

**FavoriteRepository** (JpaRepository)
```java
findByUserId(Long userId): List<Favorite>
existsByUserIdAndProductId(Long userId, Long productId): boolean
deleteByUserIdAndFavoriteId(Long userId, Long favoriteId): void
findByUserIdAndProductId(Long userId, Long productId): Optional<Favorite>
```

#### Services

**CartService** (Interface)
```java
addToCart(Long userId, AddToCartRequest): CartResponse
updateCartItem(Long userId, Long itemId, UpdateCartRequest): CartResponse
removeFromCart(Long userId, Long itemId): CartResponse
getCart(Long userId): CartResponse
clearCart(Long userId): void
calculateTotal(Long userId): BigDecimal
```

**FavoriteService** (Interface)
```java
addToFavorites(Long userId, FavoriteRequest): FavoriteResponse
removeFromFavorites(Long userId, Long favoriteId): void
getFavorites(Long userId): List<FavoriteResponse>
isFavorite(Long userId, Long productId): boolean
```

#### Controllers

**1. CartController** (`/api/cart`)
```java
POST   /api/cart/items                   // Add to cart
PUT    /api/cart/items/{itemId}          // Update quantity
DELETE /api/cart/items/{itemId}          // Remove item
GET    /api/cart                         // Get cart
DELETE /api/cart                         // Clear cart
```

**2. FavoriteController** (`/api/favorites`)
```java
POST   /api/favorites                    // Add to favorites
DELETE /api/favorites/{favoriteId}       // Remove from favorites
GET    /api/favorites                    // Get all favorites
GET    /api/favorites/check/{productId}  // Check if favorited
```

**3. InternalController** (`/api/internal/cart`)
```java
GET    /api/internal/cart/{userId}       // Get cart (internal)
DELETE /api/internal/cart/{userId}       // Clear cart (internal)
```

#### Feign Clients
```
com.revshop.cart.client/
└── ProductServiceClient.java
```

**ProductServiceClient:**
```java
@FeignClient(name = "product-service")
interface ProductServiceClient {
    @GetMapping("/api/internal/products/{id}")
    ProductDto getProduct(@PathVariable Long id);

    @PostMapping("/api/internal/products/validate")
    Map<Long, ProductDto> validateProducts(@RequestBody List<Long> productIds);
}
```

#### Key Features to Implement (P3)
1. **Cart Management**
   - Add products to cart (fetch details from product-service)
   - Update cart item quantities
   - Remove items from cart
   - Get cart with total calculation
   - Clear entire cart
   - Auto-create cart for new users

2. **Cart Validation**
   - Validate product exists via Feign client
   - Check product stock availability
   - Update product details in cart
   - Handle duplicate products (increase quantity)

3. **Favorites/Wishlist**
   - Add products to favorites
   - Remove from favorites
   - View all favorite products
   - Check if product is favorited
   - Prevent duplicate favorites

4. **User Context**
   - Extract user ID from JWT (X-User-Id header)
   - Ensure users access only their cart/favorites
   - Session management

5. **Internal Endpoints**
   - Provide cart data to checkout-service
   - Clear cart after successful checkout
   - Internal cart operations

6. **Service Communication**
   - Feign client to product-service
   - Circuit breaker configuration (Resilience4j)
   - Fallback methods for service failures
   - Timeout handling

7. **Price Calculation**
   - Calculate cart subtotal
   - Calculate total items count
   - Store price snapshot at add time

8. **Error Handling**
   - Product not found
   - Insufficient stock
   - Invalid quantity
   - Service unavailable

#### Configuration (application.yml)
```yaml
server:
  port: 8083

spring:
  application:
    name: cart-service
  datasource:
    url: jdbc:h2:mem:cartdb

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
  circuitbreaker:
    enabled: true

resilience4j:
  circuitbreaker:
    instances:
      product-service:
        slidingWindowSize: 10
        failureRateThreshold: 50
```

#### Package Structure
```
com.revshop.cart/
├── controller/
│   ├── CartController.java
│   ├── FavoriteController.java
│   └── InternalController.java
├── service/
│   ├── CartService.java
│   ├── FavoriteService.java
│   └── impl/
│       ├── CartServiceImpl.java
│       └── FavoriteServiceImpl.java
├── repository/
│   ├── CartRepository.java
│   ├── CartItemRepository.java
│   └── FavoriteRepository.java
├── model/
│   ├── Cart.java
│   ├── CartItem.java
│   └── Favorite.java
├── dto/
│   └── (8 DTO files)
├── client/
│   └── ProductServiceClient.java
├── exception/
│   └── GlobalExceptionHandler.java
└── CartServiceApplication.java
```

---

## 5. ANUSHA - Checkout & Payment

### Primary Domain
**Checkout Process and Payment Handling**

### P2 Monolithic Responsibilities

#### Controllers
- **OrderController** (`/api/orders`) - Checkout endpoints
  ```java
  POST   /api/orders/checkout               // Initiate checkout
  POST   /api/orders/payment                // Process payment
  ```

#### Services
- **OrderService** (Interface) - Checkout methods
  - `checkout(CheckoutRequestDto): OrderResponseDto`
  - `processPayment(PaymentRequestDto): OrderResponseDto`

#### DTOs
- `CheckoutRequestDto` - Checkout request with cart items
- `PaymentRequestDto` - Payment details
- `OrderResponseDto` - Order confirmation

#### Key Features to Implement (P2)
1. Initiate checkout from cart
2. Validate cart items and stock
3. Calculate total price
4. Apply discounts/coupons (optional)
5. Process payment (simulation)
6. Create order after successful payment
7. Update inventory after checkout
8. Clear cart after checkout

---

### P3 Microservices Responsibilities

#### Service Details
- **Service Name:** checkout-service
- **Port:** 8084
- **Base Path:** `/api/checkout`, `/api/payment`
- **Database:** MySQL (revshop_checkout)

#### Entities
```
com.revshop.checkout.entity/
├── CheckoutSession.java
├── PaymentTransaction.java
├── CheckoutStatus.java (Enum)
├── PaymentStatus.java (Enum)
└── PaymentMethod.java (Enum)
```

**CheckoutSession Entity:**
```java
- sessionId: Long (Primary Key)
- userId: Long (Not null, Indexed)
- cartId: Long
- items: JSON/Text (Cart items snapshot)
- subtotal: BigDecimal
- tax: BigDecimal
- shippingCost: BigDecimal
- totalAmount: BigDecimal
- shippingAddress: String
- billingAddress: String
- status: CheckoutStatus (INITIATED, ADDRESS_ADDED, PAYMENT_PENDING, COMPLETED, CANCELLED)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
- expiresAt: LocalDateTime
```

**PaymentTransaction Entity:**
```java
- transactionId: Long (Primary Key)
- sessionId: Long (Foreign Key)
- userId: Long
- amount: BigDecimal
- paymentMethod: PaymentMethod (CARD, UPI, WALLET, COD)
- status: PaymentStatus (PENDING, SUCCESS, FAILED, REFUNDED)
- transactionReference: String (Unique)
- paymentGateway: String
- createdAt: LocalDateTime
- completedAt: LocalDateTime
```

**Enums:**
```java
CheckoutStatus: INITIATED, ADDRESS_ADDED, PAYMENT_PENDING, COMPLETED, CANCELLED
PaymentStatus: PENDING, SUCCESS, FAILED, REFUNDED
PaymentMethod: CARD, UPI, WALLET, COD
```

#### DTOs
```
com.revshop.checkout.dto/
├── InitiateCheckoutRequest.java   // Start checkout
├── AddressRequest.java            // Shipping/billing address
├── CheckoutResponse.java          // Checkout session details
├── PaymentRequest.java            // Payment details
├── PaymentResponse.java           // Payment result
├── CartDto.java                   // Cart from cart-service
├── CreateOrderRequest.java        // Order creation request
└── ErrorResponse.java             // Error handling
```

#### Repositories

**CheckoutSessionRepository** (JpaRepository)
```java
findBySessionIdAndUserId(Long sessionId, Long userId): Optional<CheckoutSession>
findByUserId(Long userId): List<CheckoutSession>
findByStatus(CheckoutStatus status): List<CheckoutSession>
deleteByExpiresAtBefore(LocalDateTime dateTime): void
```

**PaymentTransactionRepository** (JpaRepository)
```java
findBySessionId(Long sessionId): Optional<PaymentTransaction>
findByTransactionReference(String reference): Optional<PaymentTransaction>
findByUserId(Long userId): List<PaymentTransaction>
```

#### Services

**CheckoutService** (Interface)
```java
initiateCheckout(InitiateCheckoutRequest): CheckoutResponse
addAddress(Long sessionId, Long userId, AddressRequest): CheckoutResponse
getCheckoutSession(Long sessionId, Long userId): CheckoutResponse
cancelCheckout(Long sessionId, Long userId): void
validateCheckoutSession(Long sessionId): boolean
```

**PaymentService** (Interface)
```java
processPayment(Long sessionId, PaymentRequest): PaymentResponse
verifyPayment(String transactionReference): PaymentStatus
refundPayment(Long transactionId): PaymentResponse
```

#### Controllers

**1. CheckoutController** (`/api/checkout`)
```java
POST   /api/checkout/initiate              // Initiate checkout
PUT    /api/checkout/{sessionId}/address   // Add shipping address
GET    /api/checkout/{sessionId}           // Get checkout session
DELETE /api/checkout/{sessionId}           // Cancel checkout
```

**2. PaymentController** (`/api/payment`)
```java
POST   /api/payment/{sessionId}/process    // Process payment
GET    /api/payment/{transactionId}        // Get payment status
POST   /api/payment/{transactionId}/verify // Verify payment
POST   /api/payment/{transactionId}/refund // Refund payment
```

#### Feign Clients
```
com.revshop.checkout.client/
├── CartServiceClient.java
├── ProductServiceClient.java
└── OrderServiceClient.java
```

**CartServiceClient:**
```java
@FeignClient(name = "cart-service")
interface CartServiceClient {
    @GetMapping("/api/internal/cart/{userId}")
    CartDto getCart(@PathVariable Long userId);

    @DeleteMapping("/api/internal/cart/{userId}")
    void clearCart(@PathVariable Long userId);
}
```

**ProductServiceClient:**
```java
@FeignClient(name = "product-service")
interface ProductServiceClient {
    @PostMapping("/api/internal/products/validate")
    Map<Long, ProductDto> validateProducts(@RequestBody List<Long> productIds);

    @PutMapping("/api/internal/products/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestBody StockUpdateRequest request);
}
```

**OrderServiceClient:**
```java
@FeignClient(name = "order-service")
interface OrderServiceClient {
    @PostMapping("/api/orders")
    OrderResponse createOrder(@RequestBody CreateOrderRequest request);
}
```

#### Key Features to Implement (P3)
1. **Checkout Initiation**
   - Fetch cart from cart-service via Feign
   - Validate all products exist and have stock
   - Create checkout session
   - Calculate subtotal, tax, shipping
   - Set session expiry (30 minutes)

2. **Address Management**
   - Add shipping address to session
   - Add billing address (optional)
   - Validate address format
   - Update session status

3. **Payment Processing**
   - Validate checkout session
   - Create payment transaction
   - Simulate payment gateway call
   - Update payment status
   - Handle payment failures

4. **Order Creation**
   - After successful payment
   - Call order-service to create order
   - Update product stock via product-service
   - Clear cart via cart-service
   - Mark session as completed

5. **Session Management**
   - Track session status
   - Handle session expiry
   - Cleanup expired sessions (scheduled job)
   - Prevent double payments

6. **Service Orchestration**
   - Coordinate between cart, product, and order services
   - Use circuit breakers for resilience
   - Implement fallback mechanisms
   - Transaction management (Saga pattern)

7. **Rollback Handling**
   - If order creation fails, refund payment
   - If stock update fails, cancel order
   - Restore cart if needed
   - Error recovery mechanisms

8. **Payment Methods**
   - Support multiple payment methods (CARD, UPI, WALLET, COD)
   - Payment gateway integration (simulation)
   - Payment verification
   - Refund processing

#### Configuration (application.yml)
```yaml
server:
  port: 8084

spring:
  application:
    name: checkout-service
  datasource:
    url: jdbc:mysql://localhost:3306/revshop_checkout

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
  circuitbreaker:
    enabled: true

resilience4j:
  circuitbreaker:
    instances:
      cartService:
        slidingWindowSize: 10
        failureRateThreshold: 50
      productService:
        slidingWindowSize: 10
        failureRateThreshold: 50
      orderService:
        slidingWindowSize: 10
        failureRateThreshold: 50

checkout:
  session:
    expiration-minutes: 30
```

#### Package Structure
```
com.revshop.checkout/
├── controller/
│   ├── CheckoutController.java
│   └── PaymentController.java
├── service/
│   ├── CheckoutService.java
│   ├── PaymentService.java
│   └── impl/
│       ├── CheckoutServiceImpl.java
│       └── PaymentServiceImpl.java
├── repository/
│   ├── CheckoutSessionRepository.java
│   └── PaymentTransactionRepository.java
├── entity/
│   ├── CheckoutSession.java
│   ├── PaymentTransaction.java
│   ├── CheckoutStatus.java
│   ├── PaymentStatus.java
│   └── PaymentMethod.java
├── dto/
│   └── (8 DTO files)
├── client/
│   ├── CartServiceClient.java
│   ├── ProductServiceClient.java
│   └── OrderServiceClient.java
├── exception/
│   └── GlobalExceptionHandler.java
└── CheckoutServiceApplication.java
```

---

## 6. GOTAM - Order Management, Notifications, Reviews & Infrastructure

### Primary Domain
**Order Management, Notifications, Reviews, and Microservices Infrastructure**

### P2 Monolithic Responsibilities

#### Controllers

**1. OrderController** (`/api/orders`)
```java
POST   /api/orders                         // Place order
GET    /api/orders/buyer/{buyerId}         // Buyer's orders
GET    /api/orders/seller/{sellerId}       // Seller's orders
GET    /api/orders/{orderId}               // Order details
PUT    /api/orders/{orderId}/status        // Update order status
PUT    /api/orders/{orderId}/cancel        // Cancel order
```

**2. ReviewController** (`/api/reviews`)
```java
POST   /api/reviews/{productId}            // Add review
GET    /api/reviews/{productId}            // Get product reviews
GET    /api/reviews/user/{userId}          // Get user's reviews
DELETE /api/reviews/{reviewId}             // Delete review
```

**3. NotificationController** (`/api/notifications`)
```java
GET    /api/notifications/{userId}         // Get user notifications
PUT    /api/notifications/{id}/read        // Mark as read
DELETE /api/notifications/{id}             // Delete notification
```

#### Services

**OrderService** (Interface)
```java
placeOrder(OrderRequest): OrderResponse
getOrdersByBuyer(Long buyerId): List<OrderResponse>
getOrdersBySeller(Long sellerId): List<OrderResponse>
getOrderById(Long orderId): OrderResponse
updateOrderStatus(Long orderId, OrderStatus status): OrderResponse
cancelOrder(Long orderId): void
```

**ReviewService** (Interface)
```java
addReview(Long productId, ReviewRequest): String
getProductReviews(Long productId): List<ReviewDto>
getUserReviews(Long userId): List<ReviewDto>
deleteReview(Long reviewId): void
```

**NotificationService** (Interface)
```java
createNotification(Long userId, String message): void
getUserNotifications(Long userId): List<NotificationDto>
markAsRead(Long notificationId): void
deleteNotification(Long notificationId): void
```

#### Entities

**Order**
```java
- orderId: Long
- buyerId: Long
- sellerId: Long
- totalAmount: BigDecimal
- orderStatus: OrderStatus
- orderDate: LocalDateTime
- shippingAddress: String
- items: List<OrderItem>
```

**OrderItem**
```java
- orderItemId: Long
- order: Order
- productId: Long
- productName: String
- quantity: Integer
- price: BigDecimal
```

**Review**
```java
- reviewId: Long
- productId: Long
- userId: Long
- rating: Integer (1-5)
- comment: String
- createdAt: LocalDateTime
```

**Notification**
```java
- notificationId: Long
- userId: Long
- message: String
- type: NotificationType
- isRead: Boolean
- createdAt: LocalDateTime
```

#### Repositories
- **OrderRepository**
- **OrderItemRepository**
- **ReviewRepository**
- **NotificationRepository**

#### Key Features to Implement (P2)
1. Place orders from cart
2. Track order status
3. View buyer/seller orders
4. Cancel orders
5. Add product reviews
6. View product reviews
7. Send notifications (order placed, status updates)
8. Manage user notifications

---

### P3 Microservices Responsibilities

#### Service Details
- **Service Name:** order-service
- **Port:** 8085
- **Base Path:** `/api/orders`, `/api/reviews`, `/api/notifications`
- **Database:** PostgreSQL (revshop)

#### Entities
```
com.revshop.order.entity/
├── Order.java
├── OrderItem.java
├── OrderStatus.java (Enum)
├── Review.java
├── Notification.java
└── NotificationType.java (Enum)
```

**Order Entity:**
```java
- orderId: Long (Primary Key)
- buyerId: Long (Not null, Indexed)
- sellerId: Long (Indexed)
- totalAmount: BigDecimal
- orderStatus: OrderStatus (PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- orderDate: LocalDateTime
- shippingAddress: String
- billingAddress: String
- paymentId: String
- items: List<OrderItem> (One-to-Many, Cascade)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

**OrderItem Entity:**
```java
- orderItemId: Long (Primary Key)
- order: Order (Many-to-One)
- productId: Long (Not null)
- productName: String
- quantity: Integer
- price: BigDecimal
- sellerId: Long
```

**Review Entity:**
```java
- reviewId: Long (Primary Key)
- productId: Long (Indexed)
- userId: Long (Indexed)
- orderId: Long
- rating: Integer (1-5, Not null)
- comment: String
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

**Notification Entity:**
```java
- notificationId: Long (Primary Key)
- userId: Long (Indexed)
- message: String
- type: NotificationType (ORDER_PLACED, ORDER_SHIPPED, etc.)
- isRead: Boolean (Default: false)
- createdAt: LocalDateTime
```

**Enums:**
```java
OrderStatus: PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
NotificationType: ORDER_PLACED, ORDER_SHIPPED, ORDER_DELIVERED, ORDER_CANCELLED,
                  REVIEW_ADDED, LOW_STOCK_ALERT, PAYMENT_SUCCESS
```

#### DTOs
```
com.revshop.order.dto/
├── CreateOrderRequest.java        // Create order
├── OrderResponse.java             // Order details
├── OrderItemResponse.java         // Order item details
├── UpdateOrderStatusRequest.java  // Update status
├── ReviewRequest.java             // Add review
├── ReviewResponse.java            // Review details
├── ProductReviewsResponse.java    // Product reviews summary
└── NotificationResponse.java      // Notification details
```

#### Repositories

**OrderRepository** (JpaRepository)
```java
findByBuyerId(Long buyerId): List<Order>
findBySellerId(Long sellerId): List<Order>
findByBuyerIdOrderByOrderDateDesc(Long buyerId): List<Order>
findByOrderStatus(OrderStatus status): List<Order>
```

**OrderItemRepository** (JpaRepository)
```java
findByOrder(Order order): List<OrderItem>
findByProductId(Long productId): List<OrderItem>
```

**ReviewRepository** (JpaRepository)
```java
findByProductId(Long productId): List<Review>
findByUserId(Long userId): List<Review>
findByProductIdOrderByCreatedAtDesc(Long productId): List<Review>
existsByUserIdAndProductId(Long userId, Long productId): boolean
```

**NotificationRepository** (JpaRepository)
```java
findByUserId(Long userId): List<Notification>
findByUserIdAndIsRead(Long userId, boolean isRead): List<Notification>
findByUserIdOrderByCreatedAtDesc(Long userId): List<Notification>
```

#### Services

**OrderService** (Interface)
```java
createOrder(CreateOrderRequest): OrderResponse
getOrderById(Long orderId): OrderResponse
getOrdersByBuyer(Long buyerId): List<OrderResponse>
getOrdersBySeller(Long sellerId): List<OrderResponse>
updateOrderStatus(Long orderId, OrderStatus status): OrderResponse
cancelOrder(Long orderId, Long userId): void
```

**ReviewService** (Interface)
```java
addReview(ReviewRequest): ReviewResponse
getProductReviews(Long productId): List<ReviewResponse>
getUserReviews(Long userId): List<ReviewResponse>
deleteReview(Long reviewId, Long userId): void
getProductAverageRating(Long productId): Double
```

**NotificationService** (Interface)
```java
createNotification(Long userId, String message, NotificationType type): void
getUserNotifications(Long userId): List<NotificationResponse>
markAsRead(Long notificationId): void
deleteNotification(Long notificationId): void
sendOrderNotification(Order order, String event): void
```

#### Controllers

**1. OrderController** (`/api/orders`)
```java
POST   /api/orders                      // Create order
GET    /api/orders/{orderId}            // Get order details
GET    /api/orders/my                   // Get my orders (buyer)
GET    /api/orders/seller               // Get seller orders
PUT    /api/orders/{orderId}/status     // Update order status
PUT    /api/orders/{orderId}/cancel     // Cancel order
```

**2. ReviewController** (`/api/reviews`)
```java
POST   /api/reviews                     // Add review
GET    /api/reviews/product/{productId} // Get product reviews
GET    /api/reviews/my                  // Get my reviews
DELETE /api/reviews/{reviewId}          // Delete review
```

**3. NotificationController** (`/api/notifications`)
```java
GET    /api/notifications               // Get my notifications
PUT    /api/notifications/{id}/read     // Mark as read
DELETE /api/notifications/{id}          // Delete notification
POST   /api/notifications/read-all      // Mark all as read
```

#### Feign Clients
```
com.revshop.order.client/
└── ProductServiceClient.java
```

**ProductServiceClient:**
```java
@FeignClient(name = "product-service")
interface ProductServiceClient {
    @GetMapping("/api/internal/products/{id}")
    ProductDto getProduct(@PathVariable Long id);

    @PutMapping("/api/internal/products/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestBody StockUpdateRequest request);
}
```

#### Key Features to Implement (P3)

**Order Management:**
1. Create order from checkout-service request
2. Store order items with product details
3. Track order status through lifecycle
4. Update order status (seller operations)
5. Cancel orders with validation
6. View buyer/seller order history
7. Order search and filtering

**Review Management:**
1. Add product reviews with ratings (1-5)
2. View product reviews with pagination
3. Calculate average product rating
4. View user's review history
5. Delete own reviews
6. Prevent duplicate reviews (optional)
7. Review validation (order verification)

**Notification Management:**
1. Create notifications for order events
2. Send order placed notifications
3. Send order status update notifications
4. Send review notifications
5. Mark notifications as read
6. Delete notifications
7. Get unread count

**Service Integration:**
1. Receive order creation from checkout-service
2. Validate products via product-service
3. Update product stock after order
4. Send notifications asynchronously
5. Handle service failures gracefully

---

### Infrastructure Responsibilities (P3)

As the infrastructure owner, Gotam is also responsible for setting up and maintaining the microservices infrastructure components.

#### 1. Eureka Discovery Server

**Service Details:**
- **Port:** 8761
- **Purpose:** Service registration and discovery
- **Dashboard:** http://localhost:8761

**Configuration:**
```yaml
server:
  port: 8761

spring:
  application:
    name: discovery-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

**Responsibilities:**
- Set up Eureka Server
- Configure service registration
- Monitor registered services
- Handle service health checks
- Dashboard monitoring

---

#### 2. API Gateway

**Service Details:**
- **Port:** 8080
- **Purpose:** Single entry point for all client requests
- **Technology:** Spring Cloud Gateway

**Configuration:**
```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      globalcors:
        cors-configurations:
          '[/**]':
            allowed-origins: "http://localhost:3000"
            allowed-methods: [GET, POST, PUT, DELETE, OPTIONS]
```

**Routing Configuration:**
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/api/auth/**

        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/api/products/**, /api/seller/products/**, /api/categories/**

        - id: cart-service
          uri: lb://cart-service
          predicates:
            - Path=/api/cart/**, /api/favorites/**

        - id: checkout-service
          uri: lb://checkout-service
          predicates:
            - Path=/api/checkout/**, /api/payment/**

        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**, /api/reviews/**, /api/notifications/**
```

**Responsibilities:**
- Configure routing to all services
- Implement CORS policies
- JWT authentication filter
- Rate limiting (optional)
- Request logging
- Error handling
- Load balancing

---

#### 3. Config Server (Optional)

**Service Details:**
- **Port:** 8888
- **Purpose:** Centralized configuration management

**Responsibilities:**
- Set up Config Server
- Manage environment-specific configs
- Configure Git repository for configs
- Refresh configurations dynamically

---

#### 4. Docker & Docker Compose

**Docker Compose Configuration:**
```yaml
version: '3.8'

services:
  discovery-server:
    build: ./discovery-server
    ports:
      - "8761:8761"

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - discovery-server

  auth-service:
    build: ./auth-service
    ports:
      - "8081:8081"
    depends_on:
      - discovery-server

  product-service:
    build: ./product-service
    ports:
      - "8082:8082"
    depends_on:
      - discovery-server
      - mysql

  cart-service:
    build: ./cart-service
    ports:
      - "8083:8083"
    depends_on:
      - discovery-server

  checkout-service:
    build: ./checkout-service
    ports:
      - "8084:8084"
    depends_on:
      - discovery-server
      - mysql

  order-service:
    build: ./order-service
    ports:
      - "8085:8085"
    depends_on:
      - discovery-server
      - postgres

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: revshop_products
    ports:
      - "3306:3306"

  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: revshop
      POSTGRES_USER: revshop
      POSTGRES_PASSWORD: revshop123
    ports:
      - "5432:5432"
```

**Responsibilities:**
- Create Dockerfiles for each service
- Configure Docker Compose
- Set up database containers
- Network configuration
- Volume management
- Container orchestration

---

#### 5. Monitoring & Logging

**Actuator Configuration:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
```

**Responsibilities:**
- Configure Spring Actuator
- Set up health checks for all services
- Implement centralized logging (optional: ELK stack)
- Monitor service performance
- Track service dependencies
- Alert configuration

---

#### Package Structure (order-service)
```
com.revshop.order/
├── controller/
│   ├── OrderController.java
│   ├── ReviewController.java
│   └── NotificationController.java
├── service/
│   ├── OrderService.java
│   ├── ReviewService.java
│   ├── NotificationService.java
│   └── impl/
│       ├── OrderServiceImpl.java
│       ├── ReviewServiceImpl.java
│       └── NotificationServiceImpl.java
├── repository/
│   ├── OrderRepository.java
│   ├── OrderItemRepository.java
│   ├── ReviewRepository.java
│   └── NotificationRepository.java
├── entity/
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   ├── Review.java
│   ├── Notification.java
│   └── NotificationType.java
├── dto/
│   └── (8 DTO files)
├── client/
│   └── ProductServiceClient.java
├── util/
│   └── JwtUtil.java
├── exception/
│   └── GlobalExceptionHandler.java
└── OrderServiceApplication.java
```

---

## Service Dependencies Diagram (ASCII)

```
                              ┌──────────────────┐
                              │   API GATEWAY    │
                              │   Port: 8080     │
                              └────────┬─────────┘
                                       │
                    ┌──────────────────┼──────────────────┐
                    │                  │                  │
          ┌─────────▼────────┐  ┌──────▼───────┐  ┌──────▼──────┐
          │  EUREKA SERVER   │  │  CONFIG      │  │   FRONTEND  │
          │  Port: 8761      │  │  SERVER      │  │   CLIENT    │
          └─────────┬────────┘  │  Port: 8888  │  └─────────────┘
                    │           └──────────────┘
        ┌───────────┼───────────────────┐
        │           │                   │
        │  All services register here   │
        │           │                   │
┌───────▼──────┬────▼────────┬─────────▼────────┬──────────────┬─────────────┐
│              │             │                  │              │             │
│  auth-       │  product-   │   cart-          │  checkout-   │   order-    │
│  service     │  service    │   service        │  service     │   service   │
│  :8081       │  :8082      │   :8083          │  :8084       │   :8085     │
│              │             │                  │              │             │
│  [H2 DB]     │  [MySQL]    │   [H2 DB]        │  [MySQL]     │  [Postgres] │
└──────────────┴─────┬───────┴────────┬─────────┴──────┬───────┴──────┬──────┘
                     │                │                │              │
                     │    ┌───────────┘                │              │
                     │    │                            │              │
         ┌───────────┘    │      ┌─────────────────────┘              │
         │                │      │                                    │
         │         Feign  │      │  Feign                      Feign  │
         │         Client │      │  Clients                    Client │
         │                │      │                                    │
         │                ▼      ▼                                    ▼
         │           ┌─────────────┐                          ┌──────────┐
         │           │  PRODUCT-   │◄─────────────────────────┤  ORDER-  │
         │           │  SERVICE    │                          │  SERVICE │
         └──────────►│             │                          └──────────┘
                     └─────────────┘

Legend:
  ──► Feign Client Call (Synchronous)
  ──┼ Service Registration (Eureka)
```

---

## Service Dependency Matrix

| Service | Depends On | Used By | Communication Method |
|---------|-----------|---------|---------------------|
| **discovery-server** | None | All services | Service Registration |
| **api-gateway** | discovery-server | Frontend | HTTP Routing |
| **config-server** | None | All services (optional) | Configuration Fetch |
| **auth-service** | discovery-server | api-gateway | JWT Validation |
| **product-service** | discovery-server | cart-service, checkout-service, order-service | Feign Client |
| **cart-service** | discovery-server, product-service | checkout-service | Feign Client |
| **checkout-service** | discovery-server, cart-service, product-service, order-service | None | Feign Client (Orchestrator) |
| **order-service** | discovery-server, product-service | checkout-service | Feign Client |

---

## Detailed Feign Client Dependencies

### cart-service → product-service
**Purpose:** Validate products when adding to cart
```java
@FeignClient(name = "product-service")
interface ProductServiceClient {
    @GetMapping("/api/internal/products/{id}")
    ProductDto getProduct(@PathVariable Long id);
}
```

### checkout-service → cart-service
**Purpose:** Fetch cart items for checkout
```java
@FeignClient(name = "cart-service")
interface CartServiceClient {
    @GetMapping("/api/internal/cart/{userId}")
    CartDto getCart(@PathVariable Long userId);

    @DeleteMapping("/api/internal/cart/{userId}")
    void clearCart(@PathVariable Long userId);
}
```

### checkout-service → product-service
**Purpose:** Validate products and update stock
```java
@FeignClient(name = "product-service")
interface ProductServiceClient {
    @PostMapping("/api/internal/products/validate")
    Map<Long, ProductDto> validateProducts(@RequestBody List<Long> productIds);

    @PutMapping("/api/internal/products/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestBody StockUpdateRequest request);
}
```

### checkout-service → order-service
**Purpose:** Create order after successful payment
```java
@FeignClient(name = "order-service")
interface OrderServiceClient {
    @PostMapping("/api/orders")
    OrderResponse createOrder(@RequestBody CreateOrderRequest request);
}
```

### order-service → product-service
**Purpose:** Get product details and update stock
```java
@FeignClient(name = "product-service")
interface ProductServiceClient {
    @GetMapping("/api/internal/products/{id}")
    ProductDto getProduct(@PathVariable Long id);
}
```

---

## Testing Responsibilities

### Unit Testing
Each developer is responsible for:
- Service layer unit tests (Mockito)
- Repository layer tests
- Controller layer tests (MockMvc)
- Minimum 80% code coverage

### Integration Testing
- Test Feign client interactions
- Test database operations
- Test API endpoints

### E2E Testing (Gotam - Infrastructure)
- Test complete user flows
- Test service-to-service communication
- Test API Gateway routing
- Test circuit breakers and fallbacks

---

## Development Workflow

### 1. Local Development
Each developer should:
1. Run discovery-server (Gotam sets this up)
2. Run their own service(s)
3. Run dependent services or use mocks
4. Test endpoints via Postman/Swagger

### 2. Integration Testing
1. Run all services via Docker Compose
2. Test inter-service communication
3. Verify database connections
4. Test via API Gateway

### 3. Code Review
- Peer review before merging
- Check for code quality
- Verify tests pass
- Review API documentation

---

## Git Branching Strategy

```
main
├── develop
│   ├── feature/auth-service-login (Manjula)
│   ├── feature/product-seller-dashboard (Kavya)
│   ├── feature/product-buyer-browse (Jatin)
│   ├── feature/cart-management (Pavan Kalyan)
│   ├── feature/checkout-payment (Anusha)
│   └── feature/order-review-notification (Gotam)
```

---

## Common Utilities (Shared by All)

### JWT Utility
```java
com.revshop.common.util.JwtUtil
- generateToken(User user): String
- validateToken(String token): boolean
- extractUserId(String token): Long
- extractRole(String token): String
```

### Exception Handling
```java
com.revshop.common.exception.GlobalExceptionHandler
- handleNotFoundException
- handleValidationException
- handleServiceUnavailableException
- handleUnauthorizedException
```

### Common DTOs
```java
com.revshop.common.dto.ErrorResponse
com.revshop.common.dto.SuccessResponse
com.revshop.common.dto.PageResponse
```

---

## API Documentation

Each developer should:
1. Document all API endpoints
2. Use Swagger/OpenAPI annotations
3. Provide request/response examples
4. Document error codes
5. Update README with API details

---

## Environment Variables

### auth-service (Manjula)
```properties
JWT_SECRET=your_secret_key
JWT_EXPIRATION=86400000
```

### product-service (Kavya & Jatin)
```properties
DB_URL=jdbc:mysql://localhost:3306/revshop_products
DB_USERNAME=root
DB_PASSWORD=root
```

### cart-service (Pavan Kalyan)
```properties
PRODUCT_SERVICE_URL=http://localhost:8082
```

### checkout-service (Anusha)
```properties
CART_SERVICE_URL=http://localhost:8083
PRODUCT_SERVICE_URL=http://localhost:8082
ORDER_SERVICE_URL=http://localhost:8085
PAYMENT_GATEWAY_URL=https://api.payment-gateway.com
```

### order-service (Gotam)
```properties
DB_URL=jdbc:postgresql://localhost:5432/revshop
DB_USERNAME=revshop
DB_PASSWORD=revshop123
PRODUCT_SERVICE_URL=http://localhost:8082
```

---

## Deployment Checklist

### Before Deployment
- [ ] All unit tests pass
- [ ] Integration tests pass
- [ ] Code reviewed and approved
- [ ] API documentation updated
- [ ] Environment variables configured
- [ ] Database migrations ready
- [ ] Docker images built
- [ ] Health checks configured

### Deployment Order (P3)
1. discovery-server (Gotam)
2. config-server (Gotam) - Optional
3. auth-service (Manjula)
4. product-service (Kavya & Jatin)
5. cart-service (Pavan Kalyan)
6. order-service (Gotam)
7. checkout-service (Anusha)
8. api-gateway (Gotam)

---

## Support & Collaboration

### Communication Channels
- **Daily Standups:** Share progress and blockers
- **Code Reviews:** Review each other's code
- **Pair Programming:** For complex integrations
- **Documentation:** Maintain shared documentation

### Inter-team Dependencies
- **Kavya & Jatin:** Share product-service development
- **Anusha:** Depends on cart, product, and order services
- **Pavan Kalyan:** Integrates with product-service
- **Gotam:** Supports all teams with infrastructure

---

## Quick Reference

### Service URLs (Local Development)
```
Eureka Dashboard:  http://localhost:8761
API Gateway:       http://localhost:8080
Auth Service:      http://localhost:8081
Product Service:   http://localhost:8082
Cart Service:      http://localhost:8083
Checkout Service:  http://localhost:8084
Order Service:     http://localhost:8085
```

### Database Connections
```
MySQL (Product, Checkout):  localhost:3306
PostgreSQL (Order):         localhost:5432
H2 Console (Auth, Cart):    http://localhost:{port}/h2-console
```

### Common Maven Commands
```bash
# Build service
mvn clean package

# Run tests
mvn test

# Skip tests
mvn clean package -DskipTests

# Run Spring Boot app
mvn spring-boot:run
```

### Docker Commands
```bash
# Build all images
docker-compose build

# Start all services
docker-compose up

# Start in detached mode
docker-compose up -d

# Stop all services
docker-compose down

# View logs
docker-compose logs -f [service-name]
```

---

## Document Version
- **Version:** 1.0
- **Last Updated:** 2026-03-05
- **Maintained By:** Gotam (Infrastructure Lead)

---

## Appendix: Complete Package Structure Overview

```
p3-revshop/
├── discovery-server/          (Gotam)
├── api-gateway/              (Gotam)
├── config-server/            (Gotam - Optional)
├── auth-service/             (Manjula)
│   └── com.revshop.auth/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       └── util/
├── product-service/          (Kavya & Jatin)
│   └── com.revshop.product/
│       ├── controller/
│       │   ├── SellerProductController.java  (Kavya)
│       │   ├── ProductController.java        (Jatin)
│       │   └── CategoryController.java       (Kavya)
│       ├── service/
│       ├── repository/
│       ├── entity/
│       └── dto/
├── cart-service/             (Pavan Kalyan)
│   └── com.revshop.cart/
│       ├── controller/
│       │   ├── CartController.java
│       │   └── FavoriteController.java
│       ├── service/
│       ├── repository/
│       ├── model/
│       ├── dto/
│       └── client/
├── checkout-service/         (Anusha)
│   └── com.revshop.checkout/
│       ├── controller/
│       │   ├── CheckoutController.java
│       │   └── PaymentController.java
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       └── client/
├── order-service/            (Gotam)
│   └── com.revshop.order/
│       ├── controller/
│       │   ├── OrderController.java
│       │   ├── ReviewController.java
│       │   └── NotificationController.java
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       └── client/
├── docker-compose.yml        (Gotam)
└── README.md                 (All team members contribute)
```

---

**End of Document**
