# Business Service - RevPay P3

## Overview
The Business Service is a microservice that manages business profiles, invoicing, business loans, and bank accounts for the RevPay payment platform. This service is part of the P3 microservices architecture.

## Owner
- **Name:** Nikhil
- **Port:** 8085
- **Package:** com.revpay.business

## Features

### Business Profile Management
- Create and update business profiles
- Submit business verification documents
- Update verification status (NOT_SUBMITTED, PENDING_VERIFICATION, VERIFIED, REJECTED)

### Invoice Management
- Create invoices with line items
- Track invoice status (SENT, PAID, OVERDUE)
- Process invoice payments through wallet integration
- Automatic overdue invoice detection
- Generate unique invoice numbers

### Business Loan Management
- Apply for business loans
- Track loan applications (SUBMITTED, APPROVED, REJECTED, ADDITIONAL_DOCUMENTS_REQUIRED)
- Approve/reject loan applications
- Generate repayment schedules
- Track loan repayments (PENDING, PAID, OVERDUE)

### Bank Account Management
- Add business bank accounts
- Encrypt sensitive account information
- Set default bank accounts
- Manage multiple bank accounts

### Analytics
- Invoice analytics (total, paid, pending, overdue)
- Revenue tracking (total, pending, overdue)
- Loan analytics (total loans, active loans, outstanding balance)

## Technology Stack
- **Java Version:** 17
- **Spring Boot Version:** 3.1.5
- **Database:** MySQL
- **ORM:** JPA/Hibernate
- **Security:** Spring Security
- **Feign Clients:** OpenFeign for inter-service communication
- **Build Tool:** Maven

## Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Spring Cloud OpenFeign
- MySQL Connector
- Lombok
- JWT (io.jsonwebtoken)

## API Endpoints

### Business Profile
- `GET /api/business/profile/user/{userId}` - Get business profile by user ID
- `POST /api/business/profile/user/{userId}` - Create or update business profile
- `PUT /api/business/profile/{profileId}/status` - Update verification status

### Invoices
- `POST /api/business/invoices/user/{userId}` - Create invoice
- `GET /api/business/invoices/user/{userId}` - Get all invoices for user
- `GET /api/business/invoices/number/{invoiceNumber}` - Get invoice by number
- `POST /api/business/invoices/{invoiceNumber}/pay` - Pay invoice

### Loans
- `POST /api/business/loans/user/{userId}` - Apply for loan
- `GET /api/business/loans/user/{userId}` - Get all loans for user
- `GET /api/business/loans/{loanId}` - Get loan by ID
- `PUT /api/business/loans/{loanId}/approve` - Approve loan
- `PUT /api/business/loans/{loanId}/reject` - Reject loan

### Bank Accounts
- `POST /api/business/bank-accounts/user/{userId}` - Add bank account
- `GET /api/business/bank-accounts/user/{userId}` - Get all bank accounts for user
- `DELETE /api/business/bank-accounts/{accountId}/user/{userId}` - Delete bank account
- `PUT /api/business/bank-accounts/{accountId}/user/{userId}/default` - Set default account

### Analytics
- `GET /api/business/analytics/user/{userId}` - Get business analytics

## Database Schema

### Tables
- `business_profiles` - Business profile information
- `invoices` - Invoice records
- `invoice_items` - Line items for invoices
- `business_loans` - Business loan applications
- `loan_repayments` - Loan repayment schedule
- `business_bank_accounts` - Business bank account information

## External Service Integration

### Wallet Service
- URL: http://localhost:8082
- Used for processing invoice payments (debit/credit operations)

### Notification Service
- URL: http://localhost:8083
- Used for sending notifications about business events

## Configuration

### Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/revpay_business
    username: root
    password: root
```

### Service Ports
- Business Service: 8085
- Wallet Service: 8082
- Notification Service: 8083

## Build and Run

### Prerequisites
- Java 17
- Maven 3.6+
- MySQL 8.0+

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

Or run the JAR:
```bash
java -jar target/business-service-0.0.1-SNAPSHOT.jar
```

### Docker Build
```bash
docker build -t business-service:latest .
```

### Docker Run
```bash
docker run -p 8085:8085 business-service:latest
```

## Security Features
- Bank account number and routing number encryption
- AES encryption for sensitive data
- CORS configuration for cross-origin requests
- Stateless session management

## Error Handling
- Global exception handler for consistent error responses
- Custom exceptions: ResourceNotFoundException, BusinessException, UnauthorizedException
- Validation error handling
- Timestamp and detailed error information in responses

## Future Enhancements
- JWT authentication integration
- Rate limiting for API endpoints
- Advanced analytics with date range filters
- Invoice templates and PDF generation
- Automated loan repayment processing
- Multi-currency support
- Payment reminders and notifications
- Business credit scoring

## Contact
For questions or issues, please contact the development team.
