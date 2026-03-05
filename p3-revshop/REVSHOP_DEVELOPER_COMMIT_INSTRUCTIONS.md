# RevShop Developer Commit Instructions

## Product Service - Kavya and Jatin

### Kavya's Commits (Seller Features - Commits 1-5)

#### Commit 1: Initial product-service setup
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Kavya"
git config user.email "kavya@revature.com"

git add pom.xml
git add src/main/resources/application.yml
git add src/main/java/com/revshop/product/ProductServiceApplication.java

git commit -m "$(cat <<'EOF'
Initial product-service setup with dependencies and configuration

- Added pom.xml with Spring Boot, JPA, MySQL, validation dependencies
- Configured application.yml with database connection and server port
- Created ProductServiceApplication.java as main application class

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Add product and category entities
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Kavya"
git config user.email "kavya@revature.com"

git add src/main/java/com/revshop/product/entity/Product.java
git add src/main/java/com/revshop/product/entity/Category.java

git commit -m "$(cat <<'EOF'
Add Product and Category entity models

- Created Product.java with fields for product details, pricing, and inventory
- Created Category.java for product categorization
- Established ManyToOne relationship between Product and Category
- Added JPA annotations for database mapping

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Add DTOs for seller product management
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Kavya"
git config user.email "kavya@revature.com"

git add src/main/java/com/revshop/product/dto/ProductRequest.java
git add src/main/java/com/revshop/product/dto/ProductUpdateRequest.java
git add src/main/java/com/revshop/product/dto/ProductResponse.java
git add src/main/java/com/revshop/product/dto/CategoryRequest.java
git add src/main/java/com/revshop/product/dto/CategoryResponse.java
git add src/main/java/com/revshop/product/dto/ThresholdRequest.java
git add src/main/java/com/revshop/product/dto/SellerProductResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for seller product and category management

- Created ProductRequest for creating new products
- Created ProductUpdateRequest for updating existing products
- Created ProductResponse for returning product data
- Created CategoryRequest and CategoryResponse for category operations
- Created ThresholdRequest for inventory threshold management
- Created SellerProductResponse for seller-specific product views
- Added validation annotations to ensure data integrity

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Add product and category repositories
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Kavya"
git config user.email "kavya@revature.com"

git add src/main/java/com/revshop/product/repository/ProductRepository.java
git add src/main/java/com/revshop/product/repository/CategoryRepository.java

git commit -m "$(cat <<'EOF'
Add ProductRepository and CategoryRepository interfaces

- Created ProductRepository with custom queries for seller operations
- Created CategoryRepository for category data access
- Added query methods for finding products by seller and category
- Implemented search functionality for product filtering

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Implement seller service and controller
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Kavya"
git config user.email "kavya@revature.com"

git add src/main/java/com/revshop/product/service/ProductService.java
git add src/main/java/com/revshop/product/service/impl/ProductServiceImpl.java
git add src/main/java/com/revshop/product/controller/SellerProductController.java

git commit -m "$(cat <<'EOF'
Implement seller product service and controller

- Created ProductService interface defining seller operations
- Implemented ProductServiceImpl with business logic for:
  - Creating and updating products
  - Managing product inventory
  - Setting stock thresholds
  - Retrieving seller-specific products
- Created SellerProductController with endpoints for:
  - POST /api/products/seller - Create product
  - PUT /api/products/seller/{id} - Update product
  - GET /api/products/seller - Get all seller products
  - PATCH /api/products/seller/{id}/threshold - Update stock threshold

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

### Jatin's Commits (Buyer Features - Commits 6-9)

#### Commit 6: Add buyer-specific DTOs
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Jatin"
git config user.email "jatin@revature.com"

git add src/main/java/com/revshop/product/dto/ProductSearchResponse.java
git add src/main/java/com/revshop/product/dto/StockUpdateRequest.java
git add src/main/java/com/revshop/product/dto/ErrorResponse.java

git commit -m "$(cat <<'EOF'
Add DTOs for buyer operations and error handling

- Created ProductSearchResponse for product browsing and search results
- Created StockUpdateRequest for internal stock management
- Created ErrorResponse for standardized error responses
- Added necessary fields for buyer-facing product information

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 7: Implement buyer product controller
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Jatin"
git config user.email "jatin@revature.com"

git add src/main/java/com/revshop/product/controller/ProductController.java

git commit -m "$(cat <<'EOF'
Implement buyer product controller for browsing and search

- Created ProductController with buyer-facing endpoints:
  - GET /api/products - Browse all products
  - GET /api/products/{id} - Get product details
  - GET /api/products/search - Search products by keyword
  - GET /api/products/category/{categoryId} - Filter by category
- Implemented pagination and filtering support
- Added product availability checking

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add category and internal controllers
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Jatin"
git config user.email "jatin@revature.com"

git add src/main/java/com/revshop/product/controller/CategoryController.java
git add src/main/java/com/revshop/product/controller/InternalController.java

git commit -m "$(cat <<'EOF'
Add CategoryController and InternalController for service operations

- Created CategoryController with endpoints:
  - GET /api/categories - Get all categories
  - POST /api/categories - Create category
  - PUT /api/categories/{id} - Update category
  - DELETE /api/categories/{id} - Delete category
- Created InternalController for inter-service communication:
  - POST /internal/products/stock - Update product stock
  - GET /internal/products/{id} - Get product for internal use
- Implemented validation and error handling

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 9: Add configuration, data loader, and exception handling
```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/product-service

git config user.name "Jatin"
git config user.email "jatin@revature.com"

git add src/main/java/com/revshop/product/config/SecurityConfig.java
git add src/main/java/com/revshop/product/config/FeignConfig.java
git add src/main/java/com/revshop/product/config/DataLoader.java
git add src/main/java/com/revshop/product/exception/GlobalExceptionHandler.java
git add src/main/java/com/revshop/product/exception/ResourceNotFoundException.java
git add src/main/java/com/revshop/product/exception/UnauthorizedException.java
git add src/main/java/com/revshop/product/exception/InsufficientStockException.java

git commit -m "$(cat <<'EOF'
Add security config, data loader, and exception handling

- Created SecurityConfig for JWT authentication and authorization
- Created FeignConfig for inter-service communication setup
- Created DataLoader to initialize default categories on startup
- Implemented GlobalExceptionHandler for centralized error handling
- Created custom exceptions:
  - ResourceNotFoundException for missing resources
  - UnauthorizedException for access control
  - InsufficientStockException for inventory validation
- Added proper HTTP status codes and error messages

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

---

## Auth Service - Manjula

### Pre-requisites

```bash
# Configure Git with your identity
git config --global user.name "Manjula"
git config --global user.email "manjula@revature.com"

# Navigate to the project root
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop
```

### Manjula's Commits (Authentication Service - Commits 1-8)

#### Commit 1: Setup auth-service project structure

**What**: Create the basic project structure with pom.xml, application.yml, and main application class.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add pom.xml
git add src/main/resources/application.yml
git add src/main/java/com/revshop/auth/AuthServiceApplication.java

git commit -m "$(cat <<'EOF'
feat(auth): Setup auth-service project structure

- Add pom.xml with Spring Boot, Security, JPA, H2 dependencies
- Configure application.yml with port 8081, H2 database, Eureka client
- Create AuthServiceApplication.java main class
- Enable Eureka client registration

Service: auth-service
Port: 8081

Files:
- auth-service/pom.xml
- auth-service/src/main/resources/application.yml
- auth-service/src/main/java/com/revshop/auth/AuthServiceApplication.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 2: Add User entity and Role enum

**What**: Create User entity and Role enum for user management.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/entity/User.java
git add src/main/java/com/revshop/auth/entity/Role.java

git commit -m "$(cat <<'EOF'
feat(auth): Add User entity and Role enum

- Create User entity with userId, email, password, role fields
- Add Role enum with CUSTOMER, SELLER, ADMIN values
- Configure JPA annotations and relationships
- Add timestamps and active status fields

Entities:
- User: Core user authentication data
- Role enum: User role types

Files:
- auth-service/src/main/java/com/revshop/auth/entity/User.java
- auth-service/src/main/java/com/revshop/auth/entity/Role.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 3: Add DTOs for authentication operations

**What**: Create all DTOs for authentication operations.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/dto/RegisterRequest.java
git add src/main/java/com/revshop/auth/dto/LoginRequest.java
git add src/main/java/com/revshop/auth/dto/AuthResponse.java
git add src/main/java/com/revshop/auth/dto/ForgotPasswordRequest.java
git add src/main/java/com/revshop/auth/dto/ResetPasswordRequest.java
git add src/main/java/com/revshop/auth/dto/UserValidationResponse.java
git add src/main/java/com/revshop/auth/dto/ErrorResponse.java

git commit -m "$(cat <<'EOF'
feat(auth): Add DTOs for authentication operations

- Create RegisterRequest for new user registration
- Add LoginRequest for user login credentials
- Create AuthResponse for JWT token response
- Add ForgotPasswordRequest for password recovery
- Create ResetPasswordRequest for password reset
- Add UserValidationResponse for internal service validation
- Create ErrorResponse for consistent error format
- Include validation annotations (@NotBlank, @Email, etc.)

DTOs:
- RegisterRequest: email, password, firstName, lastName, role
- LoginRequest: email, password
- AuthResponse: accessToken, refreshToken, userId, email, role
- ForgotPasswordRequest: email
- ResetPasswordRequest: token, newPassword
- UserValidationResponse: userId, email, role, valid
- ErrorResponse: timestamp, status, error, message

Files:
- auth-service/src/main/java/com/revshop/auth/dto/RegisterRequest.java
- auth-service/src/main/java/com/revshop/auth/dto/LoginRequest.java
- auth-service/src/main/java/com/revshop/auth/dto/AuthResponse.java
- auth-service/src/main/java/com/revshop/auth/dto/ForgotPasswordRequest.java
- auth-service/src/main/java/com/revshop/auth/dto/ResetPasswordRequest.java
- auth-service/src/main/java/com/revshop/auth/dto/UserValidationResponse.java
- auth-service/src/main/java/com/revshop/auth/dto/ErrorResponse.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 4: Add UserRepository

**What**: Create JPA repository for user data access.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/repository/UserRepository.java

git commit -m "$(cat <<'EOF'
feat(auth): Add UserRepository for user data access

- Create UserRepository extending JpaRepository
- Add findByEmail method for authentication
- Add existsByEmail method for duplicate checking
- Add findByUserId method for user lookup
- Extend JpaRepository for CRUD operations

Repository:
- UserRepository: User data access with custom queries

Files:
- auth-service/src/main/java/com/revshop/auth/repository/UserRepository.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 5: Add service layer (AuthService interface and implementation)

**What**: Implement business logic for authentication, JWT generation, and user management.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/service/AuthService.java
git add src/main/java/com/revshop/auth/service/AuthServiceImpl.java

git commit -m "$(cat <<'EOF'
feat(auth): Add AuthService and AuthServiceImpl

- Create AuthService interface for authentication operations
- Implement AuthServiceImpl with login, register, logout
- Add JWT token generation and validation logic
- Implement BCrypt password hashing
- Add user validation endpoint for other services
- Implement forgot password and reset password functionality
- Add token expiration handling (24 hours for JWT)
- Implement role-based token claims

Services:
- AuthService: Authentication interface
- AuthServiceImpl: Core authentication logic implementation

Key Features:
- HMAC SHA256 token signing
- Role-based token claims
- Secure password hashing with BCrypt
- User validation for microservices
- Password recovery workflow

Files:
- auth-service/src/main/java/com/revshop/auth/service/AuthService.java
- auth-service/src/main/java/com/revshop/auth/service/AuthServiceImpl.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 6: Add AuthController

**What**: Create REST endpoints for authentication operations.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/controller/AuthController.java

git commit -m "$(cat <<'EOF'
feat(auth): Add AuthController with REST endpoints

- POST /api/auth/register - Register new user
- POST /api/auth/login - Authenticate user and return JWT
- POST /api/auth/logout - Logout and invalidate session
- POST /api/auth/forgot-password - Request password reset
- POST /api/auth/reset-password - Reset user password with token
- GET /api/auth/validate - Validate JWT token (internal endpoint)
- GET /api/auth/user/{userId} - Get user by ID (internal endpoint)

Controller:
- AuthController: All authentication endpoints
- Request/Response DTOs for all operations
- Proper HTTP status codes and error handling

Files:
- auth-service/src/main/java/com/revshop/auth/controller/AuthController.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 7: Add security configuration

**What**: Configure Spring Security, JWT service, and data loading.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/config/SecurityConfig.java
git add src/main/java/com/revshop/auth/config/JwtService.java
git add src/main/java/com/revshop/auth/config/DataLoader.java

git commit -m "$(cat <<'EOF'
feat(auth): Add SecurityConfig, JwtService, and DataLoader

- Create SecurityConfig with Spring Security filter chain
- Configure public endpoints (/api/auth/login, /api/auth/register)
- Add JwtService for JWT token generation and validation
- Implement BCryptPasswordEncoder bean
- Create DataLoader for initial user data seeding
- Configure CORS for frontend integration
- Add stateless session management

Configuration:
- SecurityConfig: Spring Security with stateless sessions
- JwtService: JWT token generation, validation, claims extraction
- DataLoader: Seeds default admin user

Default Admin User:
- Email: admin@revshop.com
- Password: admin123
- Role: ADMIN

JWT Configuration:
- Algorithm: HMAC SHA256
- Expiration: 24 hours
- Claims: userId, email, role

Files:
- auth-service/src/main/java/com/revshop/auth/config/SecurityConfig.java
- auth-service/src/main/java/com/revshop/auth/config/JwtService.java
- auth-service/src/main/java/com/revshop/auth/config/DataLoader.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

#### Commit 8: Add exception handling

**What**: Create custom exceptions and global exception handler.

```bash
cd /Users/vimalkrishnan/Workspace/revature/2353/review/p2/repos/p3-revshop/auth-service

git config user.name "Manjula"
git config user.email "manjula@revature.com"

git add src/main/java/com/revshop/auth/exception/GlobalExceptionHandler.java
git add src/main/java/com/revshop/auth/exception/InvalidCredentialsException.java
git add src/main/java/com/revshop/auth/exception/DuplicateEmailException.java
git add src/main/java/com/revshop/auth/exception/ResourceNotFoundException.java

git commit -m "$(cat <<'EOF'
feat(auth): Add exception handling with custom exceptions

- Create GlobalExceptionHandler with @RestControllerAdvice
- Add InvalidCredentialsException for login failures
- Create DuplicateEmailException for duplicate registration
- Add ResourceNotFoundException for user not found
- Implement proper HTTP status codes and error messages
- Use ErrorResponse DTO for consistent error format

Exceptions:
- InvalidCredentialsException: HTTP 401 (Unauthorized)
- DuplicateEmailException: HTTP 409 (Conflict)
- ResourceNotFoundException: HTTP 404 (Not Found)

Global Exception Handler:
- Catches all exceptions and returns consistent error responses
- Includes timestamp, status code, error type, and message
- Handles validation errors from @Valid annotations

Files:
- auth-service/src/main/java/com/revshop/auth/exception/GlobalExceptionHandler.java
- auth-service/src/main/java/com/revshop/auth/exception/InvalidCredentialsException.java
- auth-service/src/main/java/com/revshop/auth/exception/DuplicateEmailException.java
- auth-service/src/main/java/com/revshop/auth/exception/ResourceNotFoundException.java

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude Opus 4.5 <noreply@anthropic.com>
EOF
)"
```

**CHECKPOINT**: Manjula's auth-service is complete. Other services can now integrate with it for authentication.
