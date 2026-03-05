# Player Service - RevPlay

**Owner:** Deeja
**Port:** 8085
**Purpose:** Handles Play Music & Recent History

## Overview

The Player Service is a core microservice in the RevPlay music streaming platform. It manages music playback events and maintains listening history for users. This service tracks when users play songs, how long they listen, and provides analytics on listening patterns.

## Features

- Record play events when users start playing songs
- Track listening duration for each play session
- Maintain comprehensive listening history per user
- Provide listening statistics and analytics
- Support for internal analytics queries
- Integration with Artist Service for song metadata and play count tracking

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** - Database operations
- **H2 Database** - In-memory database
- **Spring Cloud Netflix Eureka Client** - Service discovery
- **OpenFeign** - Inter-service communication
- **Resilience4j** - Circuit breaker for fault tolerance
- **Jakarta Validation** - Request validation
- **Spring Security** - Basic security configuration

## Architecture

### Package Structure

```
com.revplay.player/
├── client/              # Feign clients for external services
├── config/              # Configuration classes
├── controller/          # REST endpoints
├── dto/                 # Data Transfer Objects
├── entity/              # JPA entities
├── exception/           # Custom exceptions and handlers
├── repository/          # Data access layer
└── service/             # Business logic
```

### Database Schema

#### ListeningHistory Table
- `id` (Long) - Primary key
- `user_id` (Long) - User identifier
- `song_id` (Long) - Song identifier
- `played_at` (LocalDateTime) - When the song was played
- `duration` (Integer) - How long the user listened (in seconds)

## API Endpoints

### Player Controller (`/api/player`)

#### Play a Song
```http
POST /api/player/play
Headers: X-User-Id: {userId}
Body: {
  "songId": 1
}
Response: PlayResponse with song details
```

#### Update Play Duration
```http
PUT /api/player/play/{historyId}/duration
Headers: X-User-Id: {userId}
Body: {
  "duration": 180
}
Response: Success message
```

#### Get Now Playing
```http
GET /api/player/now-playing
Headers: X-User-Id: {userId}
Response: NowPlayingResponse (most recent play)
```

### Listening History Controller (`/api/history`)

#### Get Recent History
```http
GET /api/history/recent?page=0&size=20
Headers: X-User-Id: {userId}
Response: Paginated ListeningHistoryResponse
```

#### Clear History
```http
DELETE /api/history
Headers: X-User-Id: {userId}
Response: Success message
```

#### Get Listening Statistics
```http
GET /api/history/stats
Headers: X-User-Id: {userId}
Response: UserHistoryStatsResponse
```

#### Get Top Songs
```http
GET /api/history/top-songs?limit=10
Headers: X-User-Id: {userId}
Response: List of top played songs with play counts
```

### Internal Controller (`/api/internal/history`)

For use by analytics-service and other internal services:

#### Get User History
```http
GET /api/internal/history/user/{userId}
Response: List of ListeningHistory entities
```

#### Get Song Play History
```http
GET /api/internal/history/song/{songId}
Response: List of ListeningHistory for a specific song
```

#### Get Artist Listeners
```http
GET /api/internal/history/artist/{artistId}/listeners
Body: List of song IDs
Response: Map of song IDs to listener counts
```

#### Get Play Trends
```http
GET /api/internal/history/trends?startDate={date}&endDate={date}
Response: Daily play count trends
```

## DTOs

### PlayRequest
- `songId` (Long, required) - Song to play

### PlayResponse
- `songId` (Long) - Song ID
- `title` (String) - Song title
- `artistName` (String) - Artist name
- `albumTitle` (String) - Album title
- `fileUrl` (String) - Audio file URL
- `coverImageUrl` (String) - Album cover image URL
- `duration` (Integer) - Song duration in seconds

### ListeningHistoryResponse
- `id` (Long) - History entry ID
- `songId` (Long) - Song ID
- `songTitle` (String) - Song title
- `artistName` (String) - Artist name
- `albumTitle` (String) - Album title
- `coverImageUrl` (String) - Album cover
- `playedAt` (LocalDateTime) - When played
- `listenedDuration` (Integer) - Duration listened

### UserHistoryStatsResponse
- `totalSongsPlayed` (Long) - Total play count
- `totalListeningTimeSeconds` (Long) - Total listening time
- `uniqueSongsPlayed` (Long) - Number of unique songs
- `topGenres` (List<String>) - Top genres (future enhancement)

### NowPlayingResponse
- `songId` (Long) - Currently playing song ID
- `title` (String) - Song title
- `artistName` (String) - Artist name
- `albumTitle` (String) - Album title
- `coverImageUrl` (String) - Album cover
- `startedAt` (LocalDateTime) - When playback started

## Service Dependencies

### Artist Service Integration
The Player Service integrates with the Artist Service via Feign client:

- **GET /api/internal/songs/{songId}** - Retrieves song details
- **POST /api/internal/songs/{songId}/play** - Increments play count

Circuit breaker pattern is applied to handle artist-service failures gracefully.

## Configuration

### Application Properties (application.yml)

```yaml
server.port: 8085
spring.application.name: player-service
spring.datasource.url: jdbc:h2:mem:playerdb
eureka.client.service-url.defaultZone: http://localhost:8761/eureka/
```

### Circuit Breaker Configuration

The service uses Resilience4j circuit breaker for the Artist Service:
- Sliding window size: 10
- Failure rate threshold: 50%
- Wait duration in open state: 5 seconds

## Running the Service

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Eureka Discovery Server running on port 8761
- Artist Service available

### Local Development

1. Build the service:
```bash
mvn clean package
```

2. Run the service:
```bash
java -jar target/player-service-1.0.0.jar
```

3. Access H2 Console:
```
http://localhost:8085/h2-console
JDBC URL: jdbc:h2:mem:playerdb
Username: sa
Password: (empty)
```

### Docker

Build and run with Docker:

```bash
# Build image
docker build -t player-service:1.0.0 .

# Run container
docker run -p 8085:8085 \
  -e EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/ \
  player-service:1.0.0
```

## Health Check

The service exposes actuator endpoints:

```http
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
```

## Security

The service uses X-User-Id header for user identification. In a production environment, this should be replaced with proper JWT token validation and integration with the authentication service.

## Error Handling

Global exception handling provides consistent error responses:

- `ResourceNotFoundException` - 404 Not Found
- `MethodArgumentNotValidException` - 400 Bad Request (validation errors)
- Generic exceptions - 500 Internal Server Error

All error responses include:
- `timestamp` - When the error occurred
- `message` - Error description
- `status` - HTTP status code
- `errors` - Validation errors (if applicable)

## Future Enhancements

1. **Genre Analytics** - Track and analyze top genres for users
2. **Playlist Integration** - Track plays from specific playlists
3. **Real-time Now Playing** - WebSocket support for real-time updates
4. **Offline Play Sync** - Sync offline listening history when back online
5. **Recommendations** - Use listening history for personalized recommendations
6. **Export History** - Allow users to export their listening data
7. **Privacy Controls** - Allow users to opt-out of history tracking

## Owner Contact

**Service Owner:** Deeja
For questions or issues related to this service, please contact the service owner.
