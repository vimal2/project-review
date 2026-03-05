# Player Service - API Testing Guide

## Quick Start

1. **Start the service:**
   ```bash
   mvn spring-boot:run
   ```

2. **Service will be available at:** `http://localhost:8085`

3. **H2 Console:** `http://localhost:8085/h2-console`
   - JDBC URL: `jdbc:h2:mem:playerdb`
   - Username: `sa`
   - Password: (empty)

## API Test Examples

All requests require the `X-User-Id` header.

### 1. Play a Song

**Request:**
```bash
curl -X POST http://localhost:8085/api/player/play \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "songId": 1
  }'
```

**Expected Response:**
```json
{
  "songId": 1,
  "title": "Song Title",
  "artistName": "Artist Name",
  "albumTitle": "Album Title",
  "fileUrl": "http://example.com/song.mp3",
  "coverImageUrl": "http://example.com/cover.jpg",
  "duration": 240
}
```

### 2. Update Play Duration

**Request:**
```bash
curl -X PUT http://localhost:8085/api/player/play/1/duration \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "duration": 180
  }'
```

**Expected Response:**
```json
{
  "message": "Duration updated successfully"
}
```

### 3. Get Now Playing

**Request:**
```bash
curl -X GET http://localhost:8085/api/player/now-playing \
  -H "X-User-Id: 1"
```

**Expected Response:**
```json
{
  "songId": 1,
  "title": "Currently Playing",
  "artistName": "Artist",
  "albumTitle": "Album",
  "coverImageUrl": null,
  "startedAt": "2026-03-05T16:45:00"
}
```

### 4. Get Recent Listening History

**Request:**
```bash
curl -X GET "http://localhost:8085/api/history/recent?page=0&size=20" \
  -H "X-User-Id: 1"
```

**Expected Response:**
```json
{
  "content": [
    {
      "id": 1,
      "songId": 1,
      "songTitle": "Song Title",
      "artistName": "Artist Name",
      "albumTitle": "Album Title",
      "coverImageUrl": "http://example.com/cover.jpg",
      "playedAt": "2026-03-05T16:45:00",
      "listenedDuration": 180
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 5. Get Listening Statistics

**Request:**
```bash
curl -X GET http://localhost:8085/api/history/stats \
  -H "X-User-Id: 1"
```

**Expected Response:**
```json
{
  "totalSongsPlayed": 10,
  "totalListeningTimeSeconds": 2400,
  "uniqueSongsPlayed": 5,
  "topGenres": []
}
```

### 6. Get Top Songs

**Request:**
```bash
curl -X GET "http://localhost:8085/api/history/top-songs?limit=10" \
  -H "X-User-Id: 1"
```

**Expected Response:**
```json
[
  {
    "songId": 1,
    "playCount": 5,
    "title": "Song Title",
    "artistName": "Artist Name",
    "albumTitle": "Album Title"
  }
]
```

### 7. Clear Listening History

**Request:**
```bash
curl -X DELETE http://localhost:8085/api/history \
  -H "X-User-Id: 1"
```

**Expected Response:**
```json
{
  "message": "Listening history cleared successfully"
}
```

## Internal API Endpoints (for analytics-service)

### 8. Get User History (Internal)

**Request:**
```bash
curl -X GET http://localhost:8085/api/internal/history/user/1
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "userId": 1,
    "songId": 1,
    "playedAt": "2026-03-05T16:45:00",
    "duration": 180
  }
]
```

### 9. Get Song Play History (Internal)

**Request:**
```bash
curl -X GET http://localhost:8085/api/internal/history/song/1
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "userId": 1,
    "songId": 1,
    "playedAt": "2026-03-05T16:45:00",
    "duration": 180
  }
]
```

### 10. Get Play Trends (Internal)

**Request:**
```bash
curl -X GET "http://localhost:8085/api/internal/history/trends?startDate=2026-03-01T00:00:00&endDate=2026-03-05T23:59:59"
```

**Expected Response:**
```json
[
  {
    "date": "2026-03-05",
    "playCount": 10
  }
]
```

### 11. Get Artist Listeners (Internal)

**Request:**
```bash
curl -X GET http://localhost:8085/api/internal/history/artist/1/listeners \
  -H "Content-Type: application/json" \
  -d '[1, 2, 3]'
```

**Expected Response:**
```json
{
  "1": 5,
  "2": 3,
  "3": 2
}
```

## Health Check

**Request:**
```bash
curl -X GET http://localhost:8085/actuator/health
```

**Expected Response:**
```json
{
  "status": "UP"
}
```

## Error Responses

### Resource Not Found (404)
```json
{
  "timestamp": "2026-03-05T16:45:00",
  "message": "Song not found or artist-service unavailable: 999",
  "status": 404
}
```

### Validation Error (400)
```json
{
  "timestamp": "2026-03-05T16:45:00",
  "message": "Validation failed",
  "errors": {
    "songId": "Song ID is required"
  },
  "status": 400
}
```

### Internal Server Error (500)
```json
{
  "timestamp": "2026-03-05T16:45:00",
  "message": "An error occurred: Error details...",
  "status": 500
}
```

## Testing Workflow

### Scenario 1: User Plays a Song
1. Call POST /api/player/play with songId
2. Service records play event with duration 0
3. Service calls artist-service to get song details
4. Service calls artist-service to increment play count
5. Returns song details to client
6. Later, call PUT /api/player/play/{id}/duration to update actual listen time

### Scenario 2: View Listening History
1. Call GET /api/history/recent to get paginated history
2. Each history entry includes song details fetched from artist-service
3. If artist-service is down, returns history with "Unknown" for song details

### Scenario 3: Get User Statistics
1. Call GET /api/history/stats
2. Returns total plays, listening time, unique songs
3. Call GET /api/history/top-songs for most played songs

### Scenario 4: Analytics Service Integration
1. Analytics service calls GET /api/internal/history/user/{userId}
2. Gets raw listening history data
3. Analytics service calls GET /api/internal/history/trends
4. Gets daily aggregated play counts

## Testing with Postman

Import the following as a Postman Collection:

```json
{
  "info": {
    "name": "Player Service API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Play Song",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "X-User-Id",
            "value": "1"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\"songId\": 1}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "http://localhost:8085/api/player/play",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8085",
          "path": ["api", "player", "play"]
        }
      }
    }
  ]
}
```

## Notes

- All public endpoints require X-User-Id header
- Internal endpoints don't require authentication (should be protected by API Gateway in production)
- Artist Service must be running and registered with Eureka for full functionality
- Circuit breaker activates after 50% failure rate over 10 requests
- H2 database is in-memory and resets on service restart
