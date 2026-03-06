# Job Service API Documentation

## Base URL
```
http://localhost:8084
```

## Public Endpoints

### 1. Get All Open Jobs
Get a list of all jobs with status OPEN.

**Endpoint:** `GET /api/jobs`

**Response:**
```json
{
  "success": true,
  "message": "Jobs retrieved successfully",
  "data": [
    {
      "id": 1,
      "employerId": 1,
      "companyName": "TechCorp Solutions",
      "title": "Senior Java Developer",
      "description": "We are looking for an experienced Java developer...",
      "skills": "Java, Spring Boot, Microservices, MySQL",
      "education": "Bachelor's degree in Computer Science",
      "maxExperienceYears": 5,
      "location": "New York, NY",
      "minSalary": 100000.00,
      "maxSalary": 150000.00,
      "jobType": "Full-time",
      "openings": 2,
      "applicationDeadline": "2024-12-31",
      "status": "OPEN",
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    }
  ]
}
```

### 2. Get Job By ID
Get details of a specific job posting.

**Endpoint:** `GET /api/jobs/{jobId}`

**Path Parameters:**
- `jobId` (Long) - Job posting ID

**Response:**
```json
{
  "success": true,
  "message": "Job retrieved successfully",
  "data": {
    "id": 1,
    "employerId": 1,
    "companyName": "TechCorp Solutions",
    "title": "Senior Java Developer",
    "description": "We are looking for an experienced Java developer...",
    "skills": "Java, Spring Boot, Microservices, MySQL",
    "education": "Bachelor's degree in Computer Science",
    "maxExperienceYears": 5,
    "location": "New York, NY",
    "minSalary": 100000.00,
    "maxSalary": 150000.00,
    "jobType": "Full-time",
    "openings": 2,
    "applicationDeadline": "2024-12-31",
    "status": "OPEN",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
}
```

## Job Seeker Endpoints

### 3. Search Jobs
Search for jobs with various filters.

**Endpoint:** `GET /api/jobseeker/jobs/search`

**Headers:**
- `X-User-Id` (optional) - Job seeker user ID

**Query Parameters:**
- `title` (String, optional) - Filter by job title (partial match)
- `location` (String, optional) - Filter by location (partial match)
- `companyName` (String, optional) - Filter by company name (partial match)
- `jobType` (String, optional) - Filter by job type (exact match)
- `maxExperienceYears` (Integer, optional) - Filter by maximum experience required
- `minSalary` (BigDecimal, optional) - Filter by minimum salary
- `maxSalary` (BigDecimal, optional) - Filter by maximum salary
- `datePostedAfter` (LocalDate, optional) - Filter by jobs posted after this date (YYYY-MM-DD)

**Example Request:**
```
GET /api/jobseeker/jobs/search?title=Java&location=New York&minSalary=80000
```

**Response:**
```json
{
  "success": true,
  "message": "Jobs retrieved successfully",
  "data": [
    {
      "id": 1,
      "employerId": 1,
      "companyName": "TechCorp Solutions",
      "title": "Senior Java Developer",
      ...
    }
  ]
}
```

## Employer Endpoints

All employer endpoints require the `X-User-Id` header containing the employer's user ID.

### 4. Get Employer's Jobs
Get all job postings created by the employer.

**Endpoint:** `GET /api/employer/jobs`

**Headers:**
- `X-User-Id` (required) - Employer user ID

**Response:**
```json
{
  "success": true,
  "message": "Jobs retrieved successfully",
  "data": [
    {
      "id": 1,
      "employerId": 1,
      "companyName": "TechCorp Solutions",
      "title": "Senior Java Developer",
      ...
    }
  ]
}
```

### 5. Create Job
Create a new job posting.

**Endpoint:** `POST /api/employer/jobs`

**Headers:**
- `X-User-Id` (required) - Employer user ID
- `Content-Type: application/json`

**Request Body:**
```json
{
  "title": "Senior Java Developer",
  "description": "We are looking for an experienced Java developer to join our team.",
  "skills": "Java, Spring Boot, Microservices, MySQL, Docker",
  "education": "Bachelor's degree in Computer Science",
  "maxExperienceYears": 5,
  "location": "New York, NY",
  "minSalary": 100000.00,
  "maxSalary": 150000.00,
  "jobType": "Full-time",
  "openings": 2,
  "applicationDeadline": "2024-12-31"
}
```

**Validation Rules:**
- `title`: Required, 3-200 characters
- `description`: Required, 10-5000 characters
- `skills`: Optional, max 2000 characters
- `education`: Optional, max 200 characters
- `maxExperienceYears`: Optional, 0-50
- `location`: Required, max 200 characters
- `minSalary`: Optional, must be > 0
- `maxSalary`: Optional, must be > 0 and >= minSalary
- `jobType`: Optional, max 50 characters
- `openings`: Required, 1-1000
- `applicationDeadline`: Optional, must be future date

**Response:**
```json
{
  "success": true,
  "message": "Job created successfully",
  "data": {
    "id": 1,
    "employerId": 1,
    "companyName": "TechCorp Solutions",
    "title": "Senior Java Developer",
    "description": "We are looking for an experienced Java developer...",
    "status": "OPEN",
    ...
  }
}
```

### 6. Update Job
Update an existing job posting.

**Endpoint:** `PUT /api/employer/jobs/{jobId}`

**Headers:**
- `X-User-Id` (required) - Employer user ID
- `Content-Type: application/json`

**Path Parameters:**
- `jobId` (Long) - Job posting ID

**Request Body:**
Same as Create Job endpoint

**Response:**
```json
{
  "success": true,
  "message": "Job updated successfully",
  "data": {
    "id": 1,
    "employerId": 1,
    ...
  }
}
```

### 7. Delete Job
Delete a job posting.

**Endpoint:** `DELETE /api/employer/jobs/{jobId}`

**Headers:**
- `X-User-Id` (required) - Employer user ID

**Path Parameters:**
- `jobId` (Long) - Job posting ID

**Response:**
```json
{
  "success": true,
  "message": "Job deleted successfully",
  "data": null
}
```

### 8. Close Job
Close a job posting (no longer accepting applications).

**Endpoint:** `PATCH /api/employer/jobs/{jobId}/close`

**Headers:**
- `X-User-Id` (required) - Employer user ID

**Path Parameters:**
- `jobId` (Long) - Job posting ID

**Response:**
```json
{
  "success": true,
  "message": "Job closed successfully",
  "data": {
    "id": 1,
    "status": "CLOSED",
    ...
  }
}
```

### 9. Reopen Job
Reopen a closed job posting.

**Endpoint:** `PATCH /api/employer/jobs/{jobId}/reopen`

**Headers:**
- `X-User-Id` (required) - Employer user ID

**Path Parameters:**
- `jobId` (Long) - Job posting ID

**Response:**
```json
{
  "success": true,
  "message": "Job reopened successfully",
  "data": {
    "id": 1,
    "status": "OPEN",
    ...
  }
}
```

### 10. Mark Job as Filled
Mark a job posting as filled.

**Endpoint:** `PATCH /api/employer/jobs/{jobId}/fill`

**Headers:**
- `X-User-Id` (required) - Employer user ID

**Path Parameters:**
- `jobId` (Long) - Job posting ID

**Response:**
```json
{
  "success": true,
  "message": "Job marked as filled successfully",
  "data": {
    "id": 1,
    "status": "FILLED",
    ...
  }
}
```

### 11. Get Job Statistics
Get statistics about employer's job postings.

**Endpoint:** `GET /api/employer/jobs/statistics`

**Headers:**
- `X-User-Id` (required) - Employer user ID

**Response:**
```json
{
  "success": true,
  "message": "Statistics retrieved successfully",
  "data": {
    "totalJobs": 10,
    "openJobs": 5,
    "closedJobs": 2,
    "filledJobs": 3
  }
}
```

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "title": "Title is required",
    "minSalary": "Min salary must be greater than 0"
  }
}
```

### 403 Forbidden
```json
{
  "success": false,
  "message": "You are not authorized to update this job",
  "data": null
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Job not found with ID: 123",
  "data": null
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "An unexpected error occurred: ...",
  "data": null
}
```

## Job Status Flow

```
OPEN --> CLOSED --> OPEN (can reopen)
  |
  +--> FILLED (final state, cannot reopen)
```

## Business Rules

1. Only the employer who created a job can update, delete, close, reopen, or fill it
2. Min salary cannot be greater than max salary
3. Application deadline must be in the future
4. Cannot reopen a filled job
5. Openings must be between 1 and 1000
6. Company name is automatically fetched from the Employer Service
7. All user inputs are sanitized to prevent XSS and SQL injection

## Inter-Service Dependencies

1. **Employer Service**: Fetches company name when creating a job
2. **Notification Service**: Sends notifications when a job is created
3. **Auth Service**: Can validate user authentication (optional)
