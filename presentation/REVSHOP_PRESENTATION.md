# Project Presentation Deck - RevShop

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 6 members
**Template Version:** 1.0

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
+-----------------------------------------------------------------+
|                                                                 |
|                         RevShop                                 |
|              E-Commerce Platform                                |
|                                                                 |
|     Phase 1: Monolithic Application (AWS Deployment)            |
|     Phase 2: Microservices Architecture (Docker)                |
|                                                                 |
|     Team Members (Full Stack Developers):                       |
|     - Jatin - Buyer Product Management                          |
|     - Anusha - Checkout & Payment                               |
|     - Gotam - Order Management & Notification / Review          |
|     - Manjula - Login, Registration and Authentication          |
|     - Kavya - Seller Dashboard                                  |
|     - Pavan Kalyan - Cart Management                            |
|                                                                 |
|     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]               |
|                                                                 |
+-----------------------------------------------------------------+
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A comprehensive B2C e-commerce platform that brings together sellers who wish to manage their inventory and buyers looking for an intuitive shopping experience, following modularity, Separation of Concerns, and DRY principles.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | Buyer Registration & Authentication | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-02 | Product Browse, Search & Filter | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-03 | Cart Management & Checkout | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-04 | Seller Registration & Dashboard | Use Case 2 (14 pts) | Yes | Yes |
| FR-05 | Product Inventory Management | Use Case 2 (14 pts) | Yes | Yes |
| FR-06 | Order Management & Tracking | Use Case 2 (14 pts) | Yes | Yes |
| FR-07 | JWT Authentication & Password Recovery | Common Features (6 pts) | Yes | Yes |
| FR-08 | Product Reviews & Ratings | Common Features (6 pts) | Yes | Yes |
| FR-09 | Wishlist / Favorites | Common Features (6 pts) | Yes | Yes |
| FR-10 | In-App Notifications | Common Features | Yes | Yes |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Authentication | JWT with BCrypt |
| Security | Password Storage | BCrypt Hashing |
| Performance | API Response | < 500ms |
| Logging | Method Logging | AOP with Log4j2 |
| Testing | Unit Tests | 69 tests, 100% passing |
| Availability | Uptime | 99% |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL for production, H2 for testing |
| A-02 | AWS Free Tier resources accessible for Phase 1 |
| A-03 | Docker environment available for Phase 2 microservices |
| A-04 | Team has GitHub repository access |
| A-05 | Two user roles: BUYER and SELLER |
| A-06 | Dummy payment integration (COD, Credit Card simulation) |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Insufficient stock during checkout | High | Real-time stock validation, InsufficientStockException |
| Payment failures | High | PaymentFailedException, transaction rollback |
| Low stock alerts missed | Medium | Stock threshold alerts with notifications |
| Insufficient test coverage | High | 69 unit tests with JUnit 5 & Mockito |
| Price validation errors | Medium | Selling price <= MRP validation |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
+-----------------------------------------------------------------+
|                    Angular 16 Frontend                          |
|              (TypeScript, CSS3, Flexbox/Grid)                   |
+-----------------------------------------------------------------+
                              |
                    REST API (HTTP/JSON)
                              |
+-----------------------------------------------------------------+
|                   Spring Boot 3.x Backend                       |
|     +-------------------------------------------------------+   |
|     | Auth | Product | Cart | Order | Review | Notification |   |
|     +-------------------------------------------------------+   |
|     |              Spring Security + JWT                     |  |
|     +-------------------------------------------------------+   |
|     |         AOP Logging (Log4j2 + LoggingAspect)           |  |
|     +-------------------------------------------------------+   |
|     |              Spring Data JPA / Hibernate               |  |
|     +-------------------------------------------------------+   |
+-----------------------------------------------------------------+
                              |
+-----------------------------------------------------------------+
|                    MySQL 8 Database                             |
|  users | products | categories | carts | orders | reviews       |
+-----------------------------------------------------------------+
```

**Technologies:** Java 17+, Spring Boot 3.x, Spring Security, Spring Data JPA, JWT, BCrypt, MySQL 8, H2 (testing), Angular 16, Log4j2, AOP

---

### SLIDE 5: Phase 2 - Microservices Architecture (2 minutes)

```
+-----------------------------------------------------------------+
|                       Angular Frontend                          |
+-----------------------------------------------------------------+
                              |
+-----------------------------------------------------------------+
|                   API Gateway (Port 8080)                       |
|              (JWT Validation, Request Routing)                  |
+-----------------------------------------------------------------+
           |          |          |          |          |
     +----------+ +----------+ +----------+ +----------+ +----------+
     |   Auth   | | Product  | |   Cart   | |  Order   | | Payment  |
     | Service  | | Service  | | Service  | | Service  | | Service  |
     |  (8081)  | |  (8082)  | |  (8083)  | |  (8084)  | |  (8085)  |
     +----------+ +----------+ +----------+ +----------+ +----------+
           |                                                   |
     +----------+                                        +----------+
     |  Review  |                                        |Notification|
     | Service  |                                        | Service   |
     |  (8086)  |                                        |  (8087)   |
     +----------+                                        +----------+
                              |
+-----------------------------------------------------------------+
|               Discovery Server - Eureka (Port 8761)             |
+-----------------------------------------------------------------+
```

**Service Ports (Planned):**
| Service | Port | Owner |
|---------|------|-------|
| API Gateway | 8080 | Shared |
| Auth Service | 8081 | Manjula |
| Product Service | 8082 | Jatin (Buyer) / Kavya (Seller) |
| Cart Service | 8083 | Pavan Kalyan |
| Order Service | 8084 | Gotam |
| Payment Service | 8085 | Anusha |
| Review Service | 8086 | Gotam |
| Notification Service | 8087 | Gotam |
| Eureka Server | 8761 | Shared |

---

### SLIDE 6: ERD - Entity Relationship Diagram (1-2 minutes)

```
+------------------+          +------------------+
|      users       |          |    products      |
+------------------+          +------------------+
| PK id            |<-------->| PK id            |
| email (UK)       |    |     | name             |
| password (BCrypt)|    |     | description      |
| role (ENUM)      |    |     | price            |
| resetToken       |    |     | mrp              |
| fullName         |    |     | discountPercentage|
| createdAt        |    |     | quantity         |
+------------------+    |     | rating           |
       |                |     | FK category_id   |
       |                +---->| FK seller_id     |
       |                      | active           |
       v                      | stockThreshold   |
+------------------+          +------------------+
|      carts       |
+------------------+          +------------------+
| PK id            |          |   categories     |
| FK user_id (UK)  |          +------------------+
| createdAt        |          | PK id            |
+------------------+          | name             |
       |                      | description      |
       v                      +------------------+
+------------------+
|   cart_items     |          +------------------+
+------------------+          |     orders       |
| PK id            |          +------------------+
| FK cart_id       |          | PK id            |
| FK product_id    |          | FK buyer_id      |
| quantity         |          | status (ENUM)    |
| price            |          | totalAmount      |
+------------------+          | shippingAddress  |
                              | billingAddress   |
+------------------+          | name             |
|   order_items    |          | phoneNumber      |
+------------------+          | paymentMethod    |
| PK id            |          | paymentStatus    |
| FK order_id      |          | orderDate        |
| FK product_id    |          +------------------+
| quantity         |
| price            |          +------------------+
+------------------+          |    reviews       |
                              +------------------+
+------------------+          | PK id            |
|   favorites      |          | FK product_id    |
+------------------+          | FK user_id       |
| PK id            |          | rating (1-5)     |
| FK user_id       |          | comment          |
| FK product_id    |          | createdAt        |
| createdAt        |          +------------------+
+------------------+
                              +------------------+
                              |  notifications   |
                              +------------------+
                              | PK id            |
                              | FK user_id       |
                              | message          |
                              | isRead           |
                              | createdAt        |
                              +------------------+
```

**Enums:**
- **User Roles:** BUYER, SELLER
- **Order Status:** PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED

---

### SLIDE 7: Security Implementation (1-2 minutes)

#### Authentication Flow

```
+--------+     +----------+     +-------------+     +----------+
| Client | --> |  Login   | --> | Validate    | --> | Generate |
|        |     | Request  |     | Credentials |     | JWT      |
+--------+     +----------+     +-------------+     +----------+
                                      |                   |
                              BCrypt Verify          Token Response
                                      |                   |
                              +-------------+     +----------+
                              |   User DB   |     | Return   |
                              |             |     | Token    |
                              +-------------+     +----------+
```

#### Security Features

| Feature | Implementation |
|---------|---------------|
| Password Hashing | BCrypt |
| Token Type | JWT (JSON Web Tokens) |
| Authorization | Role-based (BUYER/SELLER) |
| Route Guards | Angular Route Guards |
| Password Recovery | Reset Token via Email |
| Input Validation | Jakarta Validation (@Valid) |

#### JWT Filter Flow

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null && jwtUtil.validateToken(token, username)) {
                // Set authentication in SecurityContext
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                        username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
```

---

### SLIDE 8: Testing Strategy (1-2 minutes)

#### Testing Approach

| Test Type | Framework | Coverage |
|-----------|-----------|----------|
| Unit Tests | JUnit 5, Mockito | Service layer (100% passing) |
| Integration Tests | Spring Boot Test | API endpoints |
| In-Memory DB | H2 Database | Test isolation |

#### Test Coverage (69 Unit Tests)

| Module | Test Class |
|--------|------------|
| Auth | AuthServiceImplTest |
| Products | ProductServiceImplTest |
| Cart | CartServiceImplTest |
| Orders | OrderServiceImplTest |
| Reviews | ReviewServiceImplTest |
| Favorites | FavoriteServiceImplTest |
| Seller Products | SellerProductServiceImplTest |
| Logging | LoggingAspectTest |

#### Test Execution

```bash
cd backend
mvn test
```

#### AOP Logging Testing

```java
@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @Test
    void logMethodEntry_ShouldLogBeforeMethod() {
        // Verify AOP intercepts and logs method entries
    }

    @Test
    void logMethodExit_ShouldLogAfterReturning() {
        // Verify AOP logs method return values
    }
}
```

---

### SLIDE 9: API Documentation (1 minute)

#### Key API Endpoints

**Authentication:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | User registration |
| POST | /api/auth/login | Login with JWT |
| POST | /api/auth/forgot-password | Password recovery |
| POST | /api/auth/reset-password | Reset password |

**Products (Buyer):**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | Get all products (paginated) |
| GET | /api/products/{id} | Get product by ID |
| GET | /api/products/search | Search products |
| GET | /api/products/category/{categoryId} | Products by category |

**Seller Products:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/seller/products | Add product |
| PUT | /api/seller/products/{id} | Update product |
| DELETE | /api/seller/products/{id} | Soft delete product |
| PUT | /api/seller/products/{id}/threshold | Set stock threshold |

**Cart:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/cart/{userId} | Get user cart |
| POST | /api/cart/{userId}/items | Add to cart |
| PUT | /api/cart/{userId}/items/{itemId} | Update quantity |
| DELETE | /api/cart/{userId}/items/{itemId} | Remove item |

**Orders:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/orders/checkout | Checkout cart |
| POST | /api/orders/payment | Process payment |
| GET | /api/orders/buyer/{buyerId} | Buyer's orders |
| GET | /api/orders/seller/{sellerId} | Seller's orders |

---

### SLIDE 10: Use Case 1 - Buyer Product Management (Jatin) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Product browsing and discovery
- Search and filter by category, keyword
- View product details, pricing, ratings
- Product recommendations

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
| Buyer  | --> | Browse      | --> | Filter/     | --> | View     |
|        |     | Products    |     | Search      |     | Details  |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                                          v
                                   +-------------+
                                   | Add to Cart |
                                   | or Wishlist |
                                   +-------------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| GET | /api/products/search?keyword= | Search products |
| GET | /api/products/category/{categoryId} | Filter by category |

#### Code Highlight (ProductServiceImpl.java)

```java
@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findByActiveTrue()
            .stream()
            .map(ProductMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(keyword)
            .stream()
            .map(ProductMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId)
            .stream()
            .map(ProductMapper::toDto)
            .collect(Collectors.toList());
    }
}
```

---

### SLIDE 11: Use Case 2 - Checkout & Payment (Anusha) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Multi-step checkout process
- Shipping and billing address collection
- Payment method selection (COD, Credit Card)
- Payment processing simulation
- Order confirmation

#### Checkout Flow

```
+--------+     +-------------+     +-------------+     +----------+
| Buyer  | --> | Review Cart | --> | Enter       | --> | Select   |
|        |     |             |     | Addresses   |     | Payment  |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+     +----------+
              | Process     | --> | Update      | --> | Send     |
              | Payment     |     | Inventory   |     | Confirm  |
              +-------------+     +-------------+     +----------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/orders/checkout | Checkout cart |
| POST | /api/orders/payment | Process payment |

#### Payment Methods Supported

| Method | Type | Processing |
|--------|------|-----------|
| COD | Cash on Delivery | Mark as PENDING |
| Credit Card | Simulated | Mark as PAID (dummy) |

#### Code Highlight (OrderServiceImpl.java - Checkout)

```java
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    @Transactional
    public OrderResponseDto checkout(CheckoutRequestDto request) {
        User buyer = userRepository.findById(request.getBuyerId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(request.getBuyerId())
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        // Validate stock availability
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException(
                    "Insufficient stock for: " + item.getProduct().getName());
            }
        }

        Order order = createOrderFromCart(buyer, cart, request);
        deductInventory(cart);
        cartService.clearCart(buyer.getId());
        notifySellerAndBuyer(order);

        return mapToResponseDto(orderRepository.save(order));
    }
}
```

---

### SLIDE 12: Use Case 3 - Order Management & Notification / Review (Gotam) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Order placement and tracking
- Order status management (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
- In-app notifications for buyers and sellers
- Product reviews (only for purchased products)
- Rating system (1-5 stars)

#### Order Status Flow

```
+----------+     +------------+     +---------+     +-----------+
| PENDING  | --> | PROCESSING | --> | SHIPPED | --> | DELIVERED |
+----------+     +------------+     +---------+     +-----------+
     |
     v
+-----------+
| CANCELLED |
+-----------+
```

#### API Endpoints Owned

**Orders:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/orders | Place order |
| GET | /api/orders/{orderId} | Get order by ID |
| GET | /api/orders/buyer/{buyerId} | Buyer's orders |
| GET | /api/orders/seller/{sellerId} | Seller's orders |
| PUT | /api/orders/{orderId}/status | Update status |
| PUT | /api/orders/{orderId}/cancel | Cancel order |

**Reviews:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/reviews | Add review |
| GET | /api/reviews/product/{productId} | Product reviews |
| GET | /api/reviews/user/{userId} | User's reviews |

**Notifications:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/notifications/{userId} | Get notifications |
| PUT | /api/notifications/{id}/read | Mark as read |

#### Code Highlight (ReviewServiceImpl.java)

```java
@Service
public class ReviewServiceImpl implements ReviewService {

    @Override
    public Review addReview(ReviewRequest request) {
        // Verify user has purchased this product
        boolean hasPurchased = orderRepository
            .existsByBuyerIdAndOrderItemsProductId(
                request.getUserId(), request.getProductId());

        if (!hasPurchased) {
            throw new ForbiddenException(
                "You can only review products you have purchased");
        }

        // Check for duplicate review
        if (reviewRepository.existsByUserIdAndProductId(
                request.getUserId(), request.getProductId())) {
            throw new DuplicateResourceException(
                "You have already reviewed this product");
        }

        Review review = new Review();
        review.setUser(userRepository.findById(request.getUserId()).orElseThrow());
        review.setProduct(productRepository.findById(request.getProductId()).orElseThrow());
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);
        updateProductRating(request.getProductId());
        return saved;
    }
}
```

---

### SLIDE 13: Use Case 4 - Login, Registration and Authentication (Manjula) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- User registration with role selection (BUYER/SELLER)
- Login with JWT token generation
- Password hashing with BCrypt
- Forgot password / Reset password flow
- Route guards for role-based access

#### Authentication Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | Register/   | --> | Validate &  | --> | Generate |
|        |     | Login       |     | Hash Pass   |     | JWT      |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+     +----------+
              | Store User  | --> | Return      | --> | Route to |
              | in Database |     | Auth Token  |     | Dashboard|
              +-------------+     +-------------+     +----------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | User registration |
| POST | /api/auth/login | Login with JWT |
| POST | /api/auth/forgot-password | Initiate password reset |
| POST | /api/auth/reset-password | Reset with token |

#### Code Highlight (AuthServiceImpl.java)

```java
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setFullName(request.getFullName());

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, user.getId(), user.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getRole().name());
    }
}
```

---

### SLIDE 14: Use Case 5 - Seller Dashboard (Kavya) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Product inventory management (Add, Edit, Delete)
- Stock quantity tracking
- Stock threshold alerts (low-stock notifications)
- Pricing management (MRP, selling price, discount)
- View orders for seller's products

#### Seller Dashboard Flow

```
+--------+     +-------------+     +-------------+     +----------+
| Seller | --> | View        | --> | Add/Edit    | --> | Set      |
|        |     | Products    |     | Product     |     | Threshold|
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+
              | View Orders | --> | Track Low   |
              | for Products|     | Stock Alerts|
              +-------------+     +-------------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/seller/products | Add new product |
| PUT | /api/seller/products/{id} | Update product |
| DELETE | /api/seller/products/{id} | Soft delete product |
| GET | /api/seller/products | Get seller's products |
| PUT | /api/seller/products/{id}/threshold | Set stock threshold |
| GET | /api/orders/seller/{sellerId} | View seller's orders |

#### Price Validation

| Rule | Validation |
|------|------------|
| Selling Price | Must be <= MRP |
| Stock Threshold | Default 5, customizable |
| Discount | Auto-calculated from MRP and price |

#### Code Highlight (SellerProductServiceImpl.java)

```java
@Service
public class SellerProductServiceImpl implements SellerProductService {

    @Override
    public Product addProduct(Long sellerId, SellerProductRequest request) {
        User seller = userRepository.findById(sellerId)
            .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        if (seller.getRole() != Role.SELLER) {
            throw new ForbiddenException("Only sellers can add products");
        }

        // Validate price <= MRP
        if (request.getPrice() > request.getMrp()) {
            throw new BadRequestException("Selling price cannot exceed MRP");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setMrp(request.getMrp());
        product.setDiscountPercentage(calculateDiscount(request.getMrp(), request.getPrice()));
        product.setQuantity(request.getQuantity());
        product.setStockThreshold(request.getStockThreshold() != null
            ? request.getStockThreshold() : 5);
        product.setSeller(seller);
        product.setCategory(categoryRepository.findById(request.getCategoryId()).orElse(null));
        product.setActive(true);

        return productRepository.save(product);
    }

    @Override
    public void setStockThreshold(Long sellerId, Long productId, ThresholdRequest request) {
        Product product = getProductAndVerifyOwnership(sellerId, productId);
        product.setStockThreshold(request.getThreshold());
        productRepository.save(product);

        // Check if current stock is below new threshold
        if (product.getQuantity() <= request.getThreshold()) {
            notificationService.sendLowStockAlert(sellerId, product);
        }
    }
}
```

---

### SLIDE 15: Use Case 6 - Cart Management (Pavan Kalyan) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Add products to cart
- Update cart item quantities
- Remove items from cart
- View cart with real-time totals
- Clear entire cart
- Stock validation during cart operations

#### Cart Flow

```
+--------+     +-------------+     +-------------+     +----------+
| Buyer  | --> | Add to Cart | --> | Update      | --> | View     |
|        |     |             |     | Quantity    |     | Cart     |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+
              | Remove Item | --> | Proceed to  |
              | / Clear All |     | Checkout    |
              +-------------+     +-------------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/cart/{userId} | Get user's cart |
| POST | /api/cart/{userId}/items | Add item to cart |
| PUT | /api/cart/{userId}/items/{itemId} | Update quantity |
| DELETE | /api/cart/{userId}/items/{itemId} | Remove item |
| DELETE | /api/cart/{userId} | Clear entire cart |

#### Cart Response Structure

```json
{
  "cartId": 1,
  "userId": 5,
  "items": [
    {
      "cartItemId": 10,
      "productId": 3,
      "productName": "Laptop",
      "quantity": 2,
      "price": 999.99,
      "subtotal": 1999.98
    }
  ],
  "totalItems": 2,
  "totalAmount": 1999.98
}
```

#### Code Highlight (CartServiceImpl.java)

```java
@Service
public class CartServiceImpl implements CartService {

    @Override
    @Transactional
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Validate stock availability
        if (product.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(
                "Requested quantity exceeds available stock");
        }

        Cart cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> createNewCart(user));

        // Check if product already in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(product.getId()))
            .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + request.getQuantity();
            if (newQuantity > product.getQuantity()) {
                throw new InsufficientStockException("Cannot add more than available stock");
            }
            item.setQuantity(newQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(product.getPrice());
            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
        return mapToResponse(cart);
    }
}
```

---

### SLIDE 16: AOP Logging Implementation (1-2 minutes)

#### Aspect-Oriented Programming for Logging

```java
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.revshop.controller..*(..))")
    public void logControllerMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering: {} with args: {}",
            joinPoint.getSignature().toShortString(),
            Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.revshop.service..*(..))",
                    returning = "result")
    public void logServiceMethodExit(JoinPoint joinPoint, Object result) {
        logger.debug("Exiting: {} with result: {}",
            joinPoint.getSignature().toShortString(),
            result);
    }

    @AfterThrowing(pointcut = "execution(* com.revshop..*(..))",
                   throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in: {} - {}",
            joinPoint.getSignature().toShortString(),
            exception.getMessage());
    }
}
```

#### Log4j2 Configuration Highlights

| Feature | Configuration |
|---------|--------------|
| Console Output | Color-coded by level |
| File Logging | backend/logs directory |
| Rolling Policy | Daily rotation, 10MB max size |
| Pattern | Timestamp, Level, Class, Message |

---

### SLIDE 17: Deployment Architecture (1-2 minutes)

#### Phase 1: AWS Deployment

```
+---------------------------+
|    AWS Cloud              |
|  +---------------------+  |
|  |    EC2 Instance     |  |
|  |  +--------------+   |  |
|  |  | Spring Boot  |   |  |
|  |  | Application  |   |  |
|  |  +--------------+   |  |
|  |         |           |  |
|  |  +--------------+   |  |
|  |  |    MySQL     |   |  |
|  |  |   Database   |   |  |
|  |  +--------------+   |  |
|  +---------------------+  |
+---------------------------+
```

#### Phase 2: Docker Deployment (Planned)

```
+--------------------------------------------------+
|              Docker Compose                       |
|  +------------+  +------------+  +------------+  |
|  |  Eureka    |  |    API     |  |  Angular   |  |
|  |  Server    |  |  Gateway   |  |  Frontend  |  |
|  +------------+  +------------+  +------------+  |
|                                                   |
|  +--------+ +--------+ +--------+ +--------+     |
|  |  Auth  | |Product | |  Cart  | | Order  |     |
|  +--------+ +--------+ +--------+ +--------+     |
|                                                   |
|  +--------+ +--------+                            |
|  | Review | |Notific.|                            |
|  +--------+ +--------+                            |
+--------------------------------------------------+
```

---

### SLIDE 18: Demo Walkthrough (2-3 minutes)

#### Demo Scenario

**Step 1: Registration & Login**
- Register as Seller with business details
- Register as Buyer
- Login and receive JWT token

**Step 2: Seller Flow**
- Add products with pricing (MRP, selling price)
- Set stock thresholds
- View products in inventory

**Step 3: Buyer Flow**
- Browse products by category
- Search products by keyword
- Add products to cart
- View cart totals

**Step 4: Checkout & Order**
- Enter shipping/billing address
- Select payment method (COD/Credit Card)
- Complete checkout
- View order confirmation

**Step 5: Reviews & Notifications**
- Submit review for purchased product
- View notifications
- Seller views order

---

### SLIDE 19: Challenges & Solutions (1-2 minutes)

| Challenge | Solution |
|-----------|----------|
| Stock validation during checkout | InsufficientStockException with real-time validation |
| Price validation (price <= MRP) | Backend validation with BadRequestException |
| Review only for purchased products | Order history verification before review submission |
| Logging without polluting business logic | AOP with Log4j2 for clean separation |
| JWT token management | Angular interceptors for automatic token attachment |
| Low stock alerts | Stock threshold configuration with notification triggers |

---

### SLIDE 20: Key Learnings (1 minute)

| Area | Learning |
|------|----------|
| AOP | Clean logging without business logic pollution |
| Testing | H2 in-memory database for test isolation |
| Security | JWT with role-based route guards |
| Validation | Jakarta Validation for request validation |
| Error Handling | GlobalExceptionHandler for consistent responses |
| Soft Delete | Active flag for product deletion recovery |

---

### SLIDE 21: Future Enhancements (1 minute)

| Enhancement | Description |
|-------------|-------------|
| Real Payment Gateway | Stripe/PayPal integration |
| Product Images | Image upload and storage (S3) |
| Email Notifications | Order confirmation emails |
| Advanced Search | Elasticsearch for full-text search |
| Recommendation Engine | AI-powered product recommendations |
| Mobile App | React Native mobile application |
| Analytics Dashboard | Sales and inventory analytics |
| Discount Coupons | Coupon code system |

---

### SLIDE 22: Repository & Resources (30 seconds)

#### GitHub Repositories

| Phase | Repository |
|-------|------------|
| Phase 1 (Monolithic) | https://github.com/Divident1/RevShop_P2 |
| Phase 2 (Microservices) | https://github.com/Anusha-Reddy920/RevShop_P3 |

#### Technology Documentation

- [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- [Angular 16](https://angular.io/)
- [Log4j2](https://logging.apache.org/log4j/2.x/)
- [JWT](https://jwt.io/)

---

### SLIDE 23: Team Contributions Summary

| Team Member | Phase 1 Contribution | Phase 2 Service(s) |
|-------------|---------------------|-------------------|
| Jatin | Product browsing, search, filter | Product Service (Buyer) |
| Anusha | Checkout process, payment | Payment Service |
| Gotam | Orders, Reviews, Notifications | Order Service, Review Service, Notification Service |
| Manjula | Auth, Registration, JWT Security | Auth Service |
| Kavya | Seller dashboard, inventory | Product Service (Seller) |
| Pavan Kalyan | Cart management | Cart Service |

---

### SLIDE 24: Q&A (5-10 minutes)

```
+-----------------------------------------------------------------+
|                                                                 |
|                     Questions & Answers                         |
|                                                                 |
|     Thank you for your attention!                               |
|                                                                 |
|     Contact:                                                    |
|     - GitHub: github.com/Divident1                              |
|     - GitHub: github.com/Anusha-Reddy920                        |
|                                                                 |
+-----------------------------------------------------------------+
```

---

## PRESENTATION NOTES

### Time Allocation

| Section | Duration |
|---------|----------|
| Introduction & Overview (Slides 1-3) | 3 minutes |
| Architecture (Slides 4-6) | 5 minutes |
| Security & Testing (Slides 7-8) | 3 minutes |
| API Documentation (Slide 9) | 1 minute |
| Team Member Use Cases (Slides 10-15) | 12 minutes |
| AOP & Deployment (Slides 16-17) | 3 minutes |
| Demo (Slide 18) | 3 minutes |
| Wrap-up (Slides 19-24) | 4 minutes |
| **Total** | **34 minutes** |

### Demo Preparation Checklist

- [ ] MySQL database created (revshop_p2)
- [ ] Backend server started on port 8080
- [ ] Frontend running on port 4200
- [ ] DataSeeder populated test data
- [ ] Test accounts ready (seller@revature.com, buyer@revature.com)
- [ ] Sample products and categories created

### Default Test Users

| Role | Email | Password |
|------|-------|----------|
| SELLER | seller@revature.com | password |
| BUYER | buyer@revature.com | password |

---

*RevShop Team - 2024*
