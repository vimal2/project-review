# Employer Service API Documentation

## Base URL
```
http://localhost:8083
```

## Authentication
All endpoints (except health check) require the `X-User-Id` header containing the authenticated user's ID.

```
X-User-Id: <userId>
```

---

## Endpoints

### 1. Health Check

**Endpoint:** `GET /api/employer/health`

**Description:** Check if the Employer Service is running and healthy.

**Headers:** None required

**Response:**
```json
{
  "success": true,
  "message": "Employer Service is running",
  "data": "OK",
  "timestamp": "2024-03-06T10:00:00"
}
```

**Status Codes:**
- `200 OK` - Service is healthy

---

### 2. Get Company Profile

**Endpoint:** `GET /api/employer/company-profile`

**Description:** Retrieve the company profile for the authenticated employer.

**Headers:**
- `X-User-Id` (required): The employer's user ID

**Request Example:**
```bash
curl -X GET http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123"
```

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Company profile retrieved successfully",
  "data": {
    "id": 1,
    "userId": 123,
    "companyName": "Tech Innovations Inc",
    "industry": "Information Technology",
    "companySize": "100-500",
    "companyDescription": "Leading provider of cloud-based solutions",
    "website": "https://techinnovations.com",
    "companyLocation": "San Francisco, CA",
    "createdAt": "2024-03-01T10:00:00",
    "updatedAt": "2024-03-06T15:30:00"
  },
  "timestamp": "2024-03-06T15:30:00"
}
```

**Error Response (404 Not Found):**
```json
{
  "success": false,
  "message": "Employer profile not found for user ID: 123",
  "data": null,
  "timestamp": "2024-03-06T15:30:00"
}
```

**Error Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "X-User-Id header is required and must be a positive number",
  "data": null,
  "timestamp": "2024-03-06T15:30:00"
}
```

**Status Codes:**
- `200 OK` - Profile retrieved successfully
- `400 Bad Request` - Invalid or missing X-User-Id header
- `404 Not Found` - Profile not found for the user

---

### 3. Update Company Profile

**Endpoint:** `PUT /api/employer/company-profile`

**Description:** Update or create the company profile for the authenticated employer.

**Headers:**
- `X-User-Id` (required): The employer's user ID
- `Content-Type: application/json`

**Request Body:**
```json
{
  "companyName": "Tech Innovations Inc",
  "industry": "Information Technology",
  "companySize": "100-500",
  "companyDescription": "Leading provider of cloud-based solutions and enterprise software",
  "website": "https://techinnovations.com",
  "companyLocation": "San Francisco, CA"
}
```

**Field Validations:**
- `companyName` (required): 2-200 characters
- `industry` (optional): Max 100 characters
- `companySize` (optional): Max 50 characters
- `companyDescription` (optional): Max 2000 characters
- `website` (optional): Max 255 characters
- `companyLocation` (optional): Max 255 characters

**Request Example:**
```bash
curl -X PUT http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123" \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "Tech Innovations Inc",
    "industry": "Information Technology",
    "companySize": "100-500",
    "companyDescription": "Leading provider of cloud-based solutions",
    "website": "https://techinnovations.com",
    "companyLocation": "San Francisco, CA"
  }'
```

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Company profile updated successfully",
  "data": {
    "id": 1,
    "userId": 123,
    "companyName": "Tech Innovations Inc",
    "industry": "Information Technology",
    "companySize": "100-500",
    "companyDescription": "Leading provider of cloud-based solutions",
    "website": "https://techinnovations.com",
    "companyLocation": "San Francisco, CA",
    "createdAt": "2024-03-01T10:00:00",
    "updatedAt": "2024-03-06T15:30:00"
  },
  "timestamp": "2024-03-06T15:30:00"
}
```

**Error Response (400 Bad Request - Validation):**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "companyName": "Company name must be between 2 and 200 characters"
  },
  "timestamp": "2024-03-06T15:30:00"
}
```

**Status Codes:**
- `200 OK` - Profile updated successfully
- `400 Bad Request` - Validation error or invalid request
- `404 Not Found` - User not found

---

### 4. Get Employer Statistics

**Endpoint:** `GET /api/employer/statistics`

**Description:** Retrieve aggregated statistics for the employer including job and application data from multiple services.

**Headers:**
- `X-User-Id` (required): The employer's user ID

**Request Example:**
```bash
curl -X GET http://localhost:8083/api/employer/statistics \
  -H "X-User-Id: 123"
```

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Statistics retrieved successfully",
  "data": {
    "totalJobsPosted": 15,
    "activeJobs": 10,
    "closedJobs": 5,
    "totalApplicationsReceived": 245,
    "applicationsByStatus": {
      "PENDING": 50,
      "REVIEWED": 100,
      "ACCEPTED": 45,
      "REJECTED": 50
    },
    "pendingApplications": 50,
    "reviewedApplications": 100,
    "acceptedApplications": 45,
    "rejectedApplications": 50
  },
  "timestamp": "2024-03-06T15:30:00"
}
```

**Error Response (404 Not Found):**
```json
{
  "success": false,
  "message": "Employer profile not found for user ID: 123",
  "data": null,
  "timestamp": "2024-03-06T15:30:00"
}
```

**Status Codes:**
- `200 OK` - Statistics retrieved successfully
- `400 Bad Request` - Invalid X-User-Id header
- `404 Not Found` - Employer profile not found
- `503 Service Unavailable` - External service unavailable

**Notes:**
- This endpoint aggregates data from Job Service and Application Service
- If external services are unavailable, default values (0) are returned
- The service uses circuit breaker pattern for resilience

---

## Error Response Format

All errors follow a consistent format:

```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2024-03-06T15:30:00"
}
```

### Common Error Status Codes

- `400 Bad Request` - Invalid input or validation error
- `403 Forbidden` - Access denied
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error
- `503 Service Unavailable` - External service unavailable

---

## Data Models

### EmployerCompanyProfileRequest
```json
{
  "companyName": "string (required, 2-200 chars)",
  "industry": "string (optional, max 100 chars)",
  "companySize": "string (optional, max 50 chars)",
  "companyDescription": "string (optional, max 2000 chars)",
  "website": "string (optional, max 255 chars)",
  "companyLocation": "string (optional, max 255 chars)"
}
```

### EmployerCompanyProfileResponse
```json
{
  "id": "number",
  "userId": "number",
  "companyName": "string",
  "industry": "string",
  "companySize": "string",
  "companyDescription": "string",
  "website": "string",
  "companyLocation": "string",
  "createdAt": "ISO 8601 datetime",
  "updatedAt": "ISO 8601 datetime"
}
```

### EmployerStatisticsResponse
```json
{
  "totalJobsPosted": "number",
  "activeJobs": "number",
  "closedJobs": "number",
  "totalApplicationsReceived": "number",
  "applicationsByStatus": {
    "PENDING": "number",
    "REVIEWED": "number",
    "ACCEPTED": "number",
    "REJECTED": "number"
  },
  "pendingApplications": "number",
  "reviewedApplications": "number",
  "acceptedApplications": "number",
  "rejectedApplications": "number"
}
```

---

## Company Size Options

Recommended values for `companySize` field:
- `1-10`
- `11-50`
- `51-100`
- `100-500`
- `500-1000`
- `1000-5000`
- `5000+`

---

## Example Use Cases

### Use Case 1: First-time Profile Creation
```bash
# Employer creates profile for the first time
curl -X PUT http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123" \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "My Startup Inc",
    "industry": "Technology",
    "companySize": "1-10",
    "companyDescription": "Innovative startup in AI/ML",
    "website": "https://mystartup.com",
    "companyLocation": "Remote"
  }'
```

### Use Case 2: Updating Existing Profile
```bash
# Employer updates company description
curl -X PUT http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123" \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "My Startup Inc",
    "industry": "Technology",
    "companySize": "11-50",
    "companyDescription": "We have grown! Now specializing in enterprise AI solutions",
    "website": "https://mystartup.com",
    "companyLocation": "New York, NY"
  }'
```

### Use Case 3: Viewing Profile
```bash
# Employer views their current profile
curl -X GET http://localhost:8083/api/employer/company-profile \
  -H "X-User-Id: 123"
```

### Use Case 4: Checking Statistics
```bash
# Employer checks their job and application statistics
curl -X GET http://localhost:8083/api/employer/statistics \
  -H "X-User-Id: 123"
```

---

## Integration with Other Services

### Auth Service
- Used to validate user existence and authentication
- Endpoint: `GET /api/auth/users/{userId}`

### Job Service
- Fetches employer's job postings and statistics
- Endpoints:
  - `GET /api/jobs/employer?employerId={userId}`
  - `GET /api/jobs/employer/statistics?employerId={userId}`

### Application Service
- Fetches applications for employer's jobs and statistics
- Endpoints:
  - `GET /api/applications/employer?employerId={userId}`
  - `GET /api/applications/employer/statistics?employerId={userId}`

---

## Rate Limiting & Timeouts

- Connection Timeout: 5000ms (5 seconds)
- Read Timeout: 5000ms (5 seconds)
- Circuit Breaker: Enabled for all Feign clients

---

## Testing with Postman

Import the included Postman collection: `Employer-Service-API.postman_collection.json`

The collection includes:
- All API endpoints
- Success scenarios
- Error scenarios
- Validation tests

---

## Security Considerations

1. **Input Sanitization**: All inputs are sanitized to prevent XSS attacks
2. **SQL Injection Protection**: Using JPA/Hibernate prepared statements
3. **Validation**: Jakarta Validation for request body validation
4. **Header-based Auth**: X-User-Id header for user identification
5. **HTTPS**: Use HTTPS in production environments

---

## Monitoring & Health

### Actuator Endpoints
- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics
- `/actuator/prometheus` - Prometheus metrics

---

## Support

For issues or questions, contact the RevHire development team.

**Module Owner:** Venkatesh
**Service:** Employer Service
**Version:** 1.0.0
