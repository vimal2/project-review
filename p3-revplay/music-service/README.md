# Music Service

## Overview
The Music Service is part of the RevPlay microservices architecture. It provides music discovery and search capabilities by aggregating data from the Artist Service.

**Owner:** Prithivirajan
**Port:** 8084
**Service Type:** PUBLIC (No authentication required)

## Features
- Browse public songs catalog with pagination
- Search songs by title, artist, or album
- Filter songs by genre
- View available genres with song counts
- Get trending songs (by period: daily, weekly, monthly)
- Advanced search with multiple filters

## Architecture
This service is **database-free** and fetches all data from the Artist Service via Feign client. It implements:
- Circuit breaker pattern with Resilience4j
- Simple in-memory caching for frequently accessed data
- Service discovery with Eureka

## API Endpoints

### Catalog Endpoints
- `GET /api/catalog/songs` - Browse all public songs (paginated)
- `GET /api/catalog/songs/{songId}` - Get song details by ID
- `GET /api/catalog/genres` - Get all available genres with song counts
- `GET /api/catalog/genres/{genre}/songs` - Get songs by genre (paginated)

### Search Endpoints
- `GET /api/catalog/search?q={query}` - Search songs by title, artist, or album
- `POST /api/catalog/search/advanced` - Advanced search with filters

### Trending Endpoints
- `GET /api/catalog/trending` - Get trending songs (default: weekly)
- `GET /api/catalog/trending/{period}` - Get trending by period (daily/weekly/monthly)

## Dependencies
- Artist Service (via Feign client)
- Eureka Discovery Server

## Technologies
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Spring Cloud OpenFeign
- Resilience4j (Circuit Breaker)
- Spring Cache
- Eureka Client
- Java 17

## Configuration
Key configurations in `application.yml`:
- Server port: 8084
- Eureka server: http://localhost:8761/eureka/
- Feign timeouts: 5000ms
- Circuit breaker settings configured via Resilience4j

## Running the Service

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Eureka Discovery Server running on port 8761
- Artist Service running on its configured port

### Local Development
```bash
mvn spring-boot:run
```

### Build
```bash
mvn clean package
```

### Docker Build
```bash
docker build -t music-service:1.0.0 .
```

### Docker Run
```bash
docker run -p 8084:8084 \
  -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://discovery-server:8761/eureka/ \
  music-service:1.0.0
```

## Caching Strategy
The service implements in-memory caching for:
- **songs**: Individual song details and paginated song lists
- **genres**: Genre list with counts
- **search**: Search results
- **trending**: Trending songs by period

Cache eviction is handled automatically by the simple cache manager.

## Circuit Breaker Configuration
- Failure rate threshold: 50%
- Wait duration in open state: 30 seconds
- Sliding window size: 10 calls
- Minimum number of calls: 5

## Future Enhancements
- Integration with Analytics Service for real-time trending data
- Elasticsearch integration for advanced full-text search
- Distributed caching with Redis
- Personalized recommendations based on user listening history

## Health Check
```
GET http://localhost:8084/actuator/health
```

## Monitoring Endpoints
- `/actuator/health` - Service health status
- `/actuator/info` - Service information
- `/actuator/metrics` - Service metrics
- `/actuator/circuitbreakers` - Circuit breaker status
