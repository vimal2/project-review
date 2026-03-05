# Checkout Service

## Overview
The Checkout Service is a microservice for the RevShop P3 e-commerce platform that handles checkout sessions and payment processing. It manages the complete checkout flow from session initiation to payment completion and order creation.

**Owner:** Anusha (Checkout and Payment)
**Port:** 8084
**Package:** com.revshop.checkout

## Features

- Checkout session management
- Address collection and validation
- Payment processing (COD, Credit Card, Debit Card, UPI)
- Integration with Cart, Product, and Order services
- Session expiration handling (30 minutes)
- Circuit breaker pattern for resilience
- Transaction tracking and status management

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Spring Cloud (OpenFeign, Resilience4j)
- Spring Data JPA
- MySQL Database
- Lombok
- Maven

## Architecture

### Entities

1. **CheckoutSession**
   - `id`: Primary key
   - `userId`: User identifier
   - `cartSnapshot`: JSON snapshot of cart at checkout time
   - `shippingAddress`: Shipping address
   - `billingAddress`: Billing address
   - `contactName`: Customer name
   - `phoneNumber`: Contact number
   - `paymentMethod`: Selected payment method
   - `paymentStatus`: Payment status
   - `totalAmount`: Total checkout amount
   - `status`: Checkout session status
   - `createdAt`: Creation timestamp
   - `expiresAt`: Expiration timestamp (30 minutes from creation)

2. **PaymentTransaction**
   - `id`: Primary key
   - `checkoutSessionId`: Reference to checkout session
   - `orderId`: Reference to created order
   - `amount`: Transaction amount
   - `paymentMethod`: Payment method used
   - `transactionId`: Unique transaction identifier
   - `status`: Transaction status
   - `createdAt`: Creation timestamp
   - `errorMessage`: Error details if failed

### Enums

- **PaymentMethod**: COD, CREDIT_CARD, DEBIT_CARD, UPI
- **PaymentStatus**: PENDING, COMPLETED, FAILED, REFUNDED
- **CheckoutStatus**: INITIATED, ADDRESS_ADDED, PAYMENT_PENDING, COMPLETED, EXPIRED, CANCELLED

### Services

1. **CheckoutService**
   - Initiate checkout session
   - Add shipping/billing address
   - Retrieve checkout session
   - Cancel checkout session

2. **PaymentService**
   - Process payments
   - Validate payment details
   - Create orders via Order Service
   - Clear cart after successful payment
   - Transaction status tracking

## API Endpoints

### Checkout Endpoints

#### 1. Initiate Checkout
```
POST /api/checkout/initiate
```
**Request Body:**
```json
{
  "userId": 1,
  "totalAmount": 299.99
}
```
**Response:** CheckoutResponse with session details

#### 2. Add Address
```
PUT /api/checkout/{sessionId}/address?userId={userId}
```
**Request Body:**
```json
{
  "shippingAddress": "123 Main St, City, State, ZIP",
  "billingAddress": "123 Main St, City, State, ZIP",
  "name": "John Doe",
  "phone": "1234567890"
}
```
**Response:** Updated CheckoutResponse

#### 3. Get Checkout Session
```
GET /api/checkout/{sessionId}?userId={userId}
```
**Response:** CheckoutResponse with session details

#### 4. Cancel Checkout
```
DELETE /api/checkout/{sessionId}?userId={userId}
```
**Response:** 204 No Content

### Payment Endpoints

#### 1. Process Payment
```
POST /api/payment/process
```
**Request Body:**
```json
{
  "checkoutSessionId": 1,
  "paymentMethod": "CREDIT_CARD",
  "cardDetails": {
    "cardNumber": "4111111111111111",
    "cardHolderName": "John Doe",
    "expiryDate": "12/25",
    "cvv": "123"
  }
}
```
**Note:** `cardDetails` is optional for COD payments

**Response:**
```json
{
  "transactionId": "TXN-uuid",
  "status": "COMPLETED",
  "orderId": 123,
  "message": "Payment completed successfully"
}
```

#### 2. Get Transaction Status
```
GET /api/payment/{transactionId}
```
**Response:** PaymentTransaction details

## Service Integration

### Cart Service (Port 8082)
- `GET /api/cart/{userId}` - Fetch user's cart
- `DELETE /api/cart/{userId}/clear` - Clear cart after successful payment

### Product Service (Port 8081)
- `GET /api/products/{productId}/stock` - Check product stock
- `PUT /api/products/{productId}/stock` - Update product stock

### Order Service (Port 8083)
- `POST /api/orders` - Create order after successful payment

## Circuit Breaker Configuration

The service uses Resilience4j circuit breakers for all external service calls:

- **cartService**: Protects calls to Cart Service
- **productService**: Protects calls to Product Service
- **orderService**: Protects calls to Order Service

Configuration:
- Sliding window size: 10 requests
- Failure rate threshold: 50%
- Wait duration in open state: 10 seconds
- Minimum number of calls: 5

## Database Configuration

### MySQL Setup

1. Create database:
```sql
CREATE DATABASE revshop_checkout;
```

2. Configure environment variables:
```bash
export DB_URL=jdbc:mysql://localhost:3306/revshop_checkout
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

### Tables

The application automatically creates the following tables:
- `checkout_sessions`
- `payment_transactions`

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Running instances of Cart, Product, and Order services

### Local Development

1. Clone the repository
2. Configure database connection in `application.yml` or via environment variables
3. Build the application:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The service will start on port 8084.

### Docker Deployment

1. Build Docker image:
```bash
docker build -t checkout-service:1.0.0 .
```

2. Run container:
```bash
docker run -p 8084:8084 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/revshop_checkout \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=password \
  -e CART_SERVICE_URL=http://host.docker.internal:8082 \
  -e PRODUCT_SERVICE_URL=http://host.docker.internal:8081 \
  -e ORDER_SERVICE_URL=http://host.docker.internal:8083 \
  checkout-service:1.0.0
```

## Configuration

### Application Properties

Key configuration properties in `application.yml`:

```yaml
server:
  port: 8084

services:
  cart:
    url: http://localhost:8082
  product:
    url: http://localhost:8081
  order:
    url: http://localhost:8083

checkout:
  session:
    expiration-minutes: 30
```

## Error Handling

The service provides comprehensive error handling with appropriate HTTP status codes:

- `404 NOT_FOUND` - Checkout session or transaction not found
- `410 GONE` - Checkout session expired
- `402 PAYMENT_REQUIRED` - Payment failed
- `400 BAD_REQUEST` - Invalid request parameters
- `422 UNPROCESSABLE_ENTITY` - Validation errors
- `500 INTERNAL_SERVER_ERROR` - Unexpected errors

## Health Check

The service exposes health check endpoints via Spring Actuator:

```
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
GET /actuator/circuitbreakers
```

## Checkout Flow

1. **Initiate Checkout**
   - User initiates checkout
   - Service fetches cart from Cart Service
   - Creates checkout session with cart snapshot
   - Returns session ID

2. **Add Address**
   - User provides shipping and billing address
   - Service validates and updates session
   - Session status updated to ADDRESS_ADDED

3. **Process Payment**
   - User selects payment method and provides details
   - Service validates payment
   - Processes payment based on method (COD/Card)
   - Creates order via Order Service
   - Clears user's cart
   - Updates session status to COMPLETED

4. **Session Expiration**
   - Sessions automatically expire after 30 minutes
   - Expired sessions cannot be used for payment
   - Status updated to EXPIRED

## Payment Methods

### Cash on Delivery (COD)
- No additional validation required
- Order created immediately
- Payment collected on delivery

### Card Payments (Credit/Debit)
- Requires card details
- Basic validation performed
- In production, integrate with payment gateway

### UPI
- UPI ID or QR code based payment
- Integration point for UPI payment processors

## Security

- CSRF protection disabled for stateless REST API
- Stateless session management
- All endpoints currently permit all access (add authentication as needed)
- Actuator endpoints accessible for monitoring

## Future Enhancements

- JWT-based authentication
- Enhanced payment gateway integration
- Payment retry mechanism
- Webhook support for payment status updates
- Email notifications
- Order confirmation emails
- Scheduled cleanup of expired sessions
- Advanced fraud detection
- Multi-currency support
- Discount and coupon code support

## Testing

Run tests with:
```bash
mvn test
```

## Contributing

1. Follow the existing code structure
2. Write unit tests for new features
3. Update API documentation
4. Follow Java naming conventions
5. Add appropriate logging

## Contact

**Owner:** Anusha
**Service:** Checkout and Payment
**Port:** 8084

## License

Copyright 2024 RevShop. All rights reserved.
