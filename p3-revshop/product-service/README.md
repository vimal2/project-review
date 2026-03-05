# Product Service

Product microservice for RevShop P3 - Manages products, categories, and inventory.

## Overview

**Port:** 8082
**Owners:**
- Kavya - Seller Dashboard (Product Management)
- Jatin - Buyer Product Management (Product Browsing & Search)

## Features

### Seller Operations (Kavya)
- Add new products
- Update product details
- Soft delete products
- Set stock threshold alerts
- View all seller products with low stock indicators

### Buyer Operations (Jatin)
- Browse all active products (paginated)
- View product details
- Browse products by category (paginated)
- Search products by keyword
- View all categories

### Internal Operations
- Get product details (for other services)
- Update product stock (for order service)

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Spring Data JPA
- MySQL Database
- Spring Cloud Netflix Eureka (Service Discovery)
- Spring Cloud OpenFeign (Inter-service Communication)
- Lombok
- Docker

## API Endpoints

### Buyer Endpoints (Jatin)

#### Get All Products (Paginated)
```
GET /api/products?page=0&size=10
```

#### Get Product Details
```
GET /api/products/{id}
```

#### Get Products by Category (Paginated)
```
GET /api/products/category/{categoryId}?page=0&size=10
```

#### Search Products
```
GET /api/products/search?keyword=phone
```

### Seller Endpoints (Kavya)

**Note:** All seller endpoints require `X-User-Id` header from gateway.

#### Add Product
```
POST /api/seller/products
Headers: X-User-Id: {sellerId}
Body: {
  "name": "Product Name",
  "description": "Product Description",
  "price": 99.99,
  "mrp": 149.99,
  "quantity": 50,
  "imageUrl": "https://example.com/image.jpg",
  "categoryId": 1
}
```

#### Update Product
```
PUT /api/seller/products/{id}
Headers: X-User-Id: {sellerId}
Body: {
  "name": "Updated Name",
  "description": "Updated Description",
  "price": 89.99,
  "mrp": 149.99,
  "quantity": 45,
  "imageUrl": "https://example.com/image.jpg",
  "categoryId": 1,
  "active": true
}
```

#### Delete Product (Soft Delete)
```
DELETE /api/seller/products/{id}
Headers: X-User-Id: {sellerId}
```

#### Get Seller Products
```
GET /api/seller/products
Headers: X-User-Id: {sellerId}
```

#### Set Stock Threshold
```
PUT /api/seller/products/{id}/threshold
Headers: X-User-Id: {sellerId}
Body: {
  "threshold": 10
}
```

### Category Endpoints

#### Get All Categories
```
GET /api/categories
```

#### Add Category (Admin)
```
POST /api/categories
Headers: X-User-Role: ADMIN
Body: {
  "name": "Category Name",
  "description": "Category Description"
}
```

### Internal Endpoints (Service-to-Service)

**Note:** These should be secured at the gateway level to prevent external access.

#### Get Product by ID (Internal)
```
GET /api/internal/products/{id}
```

#### Update Stock (Internal)
```
PUT /api/internal/products/{id}/stock
Body: {
  "quantity": -5  // negative to decrease, positive to increase
}
```

## Database Schema

### Products Table
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT(2000),
    price DOUBLE NOT NULL,
    mrp DOUBLE NOT NULL,
    discount_percentage DOUBLE,
    quantity INT NOT NULL,
    rating DOUBLE DEFAULT 0.0,
    image_url VARCHAR(500),
    active BOOLEAN DEFAULT TRUE,
    stock_threshold INT DEFAULT 5,
    seller_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

### Categories Table
```sql
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500)
);
```

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Service port | 8082 |
| `DB_URL` | MySQL connection URL | jdbc:mysql://localhost:3306/revshop_products |
| `DB_USERNAME` | Database username | root |
| `DB_PASSWORD` | Database password | root |
| `EUREKA_SERVER` | Eureka server URL | http://localhost:8761/eureka/ |

## Running the Service

### Local Development

1. **Prerequisites:**
   - Java 17
   - Maven 3.6+
   - MySQL 8.0+

2. **Setup Database:**
```bash
mysql -u root -p
CREATE DATABASE revshop_products;
```

3. **Run the Application:**
```bash
mvn spring-boot:run
```

Or with custom environment variables:
```bash
export DB_URL=jdbc:mysql://localhost:3306/revshop_products
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
mvn spring-boot:run
```

### Docker

1. **Build Docker Image:**
```bash
docker build -t product-service:1.0.0 .
```

2. **Run Container:**
```bash
docker run -p 8082:8082 \
  -e DB_URL=jdbc:mysql://mysql:3306/revshop_products \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=root \
  -e EUREKA_SERVER=http://eureka-server:8761/eureka/ \
  product-service:1.0.0
```

### Docker Compose

```yaml
product-service:
  build: ./product-service
  ports:
    - "8082:8082"
  environment:
    - DB_URL=jdbc:mysql://mysql:3306/revshop_products
    - DB_USERNAME=root
    - DB_PASSWORD=root
    - EUREKA_SERVER=http://eureka-server:8761/eureka/
  depends_on:
    - mysql
    - eureka-server
```

## Security

- Authentication and authorization are handled at the API Gateway level
- This service trusts the `X-User-Id` and `X-User-Role` headers from the gateway
- Seller endpoints verify product ownership using the `X-User-Id` header
- Internal endpoints should be secured at the gateway to prevent external access

## Data Initialization

The service automatically seeds the database with:
- 8 product categories (Electronics, Fashion, Home & Kitchen, Books, Sports, Beauty, Toys, Automotive)
- 6 sample products for testing

This runs only once when the database is empty.

## Business Rules

1. **Discount Calculation:** Automatically calculated as `((MRP - Price) / MRP) * 100`
2. **Stock Threshold:** Default is 5 units; sellers can customize per product
3. **Low Stock Indicator:** `lowStock` flag is set when `quantity <= stockThreshold`
4. **Soft Delete:** Products are never truly deleted; `active` flag is set to false
5. **Active Products Only:** Buyer endpoints only return products where `active = true`

## Error Handling

The service returns standardized error responses:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: 123",
  "timestamp": "2026-03-05T12:00:00"
}
```

Common HTTP status codes:
- `200 OK` - Success
- `201 Created` - Resource created
- `204 No Content` - Success with no response body
- `400 Bad Request` - Invalid request or insufficient stock
- `403 Forbidden` - Unauthorized operation
- `404 Not Found` - Resource not found
- `422 Unprocessable Entity` - Validation errors
- `500 Internal Server Error` - Server error

## Health Check

```
GET /actuator/health
```

## Monitoring

Actuator endpoints available at:
- `/actuator/health` - Health status
- `/actuator/info` - Service information
- `/actuator/metrics` - Service metrics

## Dependencies

This service depends on:
- MySQL Database
- Eureka Service Discovery Server

This service is used by:
- Order Service (to get product details and update stock)
- API Gateway (to route requests)

## Team Responsibilities

### Kavya - Seller Dashboard
- Product CRUD operations
- Stock management
- Threshold alerts
- Seller product listing

### Jatin - Buyer Product Management
- Product browsing with pagination
- Product search
- Category filtering
- Product details view

## Future Enhancements

- Product reviews integration
- Image upload to cloud storage
- Advanced search with filters (price range, rating)
- Product recommendations
- Inventory alerts via notification service
- Bulk product upload for sellers
- Product variants (size, color)
- SEO-friendly product URLs
