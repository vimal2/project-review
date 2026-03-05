# Music Service - API Reference

## Base URL
```
http://localhost:8084/api/catalog
```

## Catalog Endpoints

### 1. Get All Public Songs
```http
GET /api/catalog/songs?page=0&size=20
```

**Query Parameters:**
- `page` (optional, default: 0) - Page number
- `size` (optional, default: 20) - Page size

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "Song Title",
      "artistId": 1,
      "artistName": "Artist Name",
      "albumId": 1,
      "albumTitle": "Album Title",
      "duration": 240,
      "genre": "Rock",
      "coverImageUrl": "http://example.com/image.jpg",
      "playCount": 1000
    }
  ],
  "page": 0,
  "pageSize": 20,
  "totalElements": 100,
  "totalPages": 5
}
```

### 2. Get Song by ID
```http
GET /api/catalog/songs/{songId}
```

**Path Parameters:**
- `songId` - Song ID

**Response:**
```json
{
  "id": 1,
  "title": "Song Title",
  "artistId": 1,
  "artistName": "Artist Name",
  "albumId": 1,
  "albumTitle": "Album Title",
  "duration": 240,
  "genre": "Rock",
  "coverImageUrl": "http://example.com/image.jpg",
  "playCount": 1000
}
```

### 3. Get All Genres
```http
GET /api/catalog/genres
```

**Response:**
```json
[
  {
    "name": "Rock",
    "songCount": 50
  },
  {
    "name": "Pop",
    "songCount": 30
  }
]
```

### 4. Get Songs by Genre
```http
GET /api/catalog/genres/{genre}/songs?page=0&size=20
```

**Path Parameters:**
- `genre` - Genre name

**Query Parameters:**
- `page` (optional, default: 0) - Page number
- `size` (optional, default: 20) - Page size

**Response:** Same as Get All Public Songs

## Search Endpoints

### 5. Simple Search
```http
GET /api/catalog/search?q=query&page=0&size=20
```

**Query Parameters:**
- `q` (required) - Search query
- `page` (optional, default: 0) - Page number
- `size` (optional, default: 20) - Page size

**Response:**
```json
{
  "songs": [
    {
      "id": 1,
      "title": "Song Title",
      "artistId": 1,
      "artistName": "Artist Name",
      "albumId": 1,
      "albumTitle": "Album Title",
      "duration": 240,
      "genre": "Rock",
      "coverImageUrl": "http://example.com/image.jpg",
      "playCount": 1000
    }
  ],
  "totalResults": 10,
  "page": 0,
  "pageSize": 20
}
```

### 6. Advanced Search
```http
POST /api/catalog/search/advanced?page=0&size=20
Content-Type: application/json

{
  "query": "search term",
  "genre": "Rock",
  "artistName": "Artist Name"
}
```

**Query Parameters:**
- `page` (optional, default: 0) - Page number
- `size` (optional, default: 20) - Page size

**Request Body:**
```json
{
  "query": "search term",      // Required
  "genre": "Rock",              // Optional
  "artistName": "Artist Name"   // Optional
}
```

**Response:** Same as Simple Search

## Trending Endpoints

### 7. Get Trending Songs (Default)
```http
GET /api/catalog/trending?limit=20
```

**Query Parameters:**
- `limit` (optional, default: 20) - Number of songs to return

**Response:**
```json
{
  "songs": [
    {
      "id": 1,
      "title": "Song Title",
      "artistId": 1,
      "artistName": "Artist Name",
      "albumId": 1,
      "albumTitle": "Album Title",
      "duration": 240,
      "genre": "Rock",
      "coverImageUrl": "http://example.com/image.jpg",
      "playCount": 5000
    }
  ],
  "period": "WEEKLY"
}
```

### 8. Get Trending by Period
```http
GET /api/catalog/trending/{period}?limit=20
```

**Path Parameters:**
- `period` - Trending period (DAILY, WEEKLY, MONTHLY)

**Query Parameters:**
- `limit` (optional, default: 20) - Number of songs to return

**Response:** Same as Get Trending Songs

## Error Responses

### Service Unavailable (503)
```json
{
  "timestamp": "2026-03-05T11:00:00",
  "status": 503,
  "error": "Service Unavailable",
  "message": "Artist service is currently unavailable"
}
```

### Validation Error (400)
```json
{
  "timestamp": "2026-03-05T11:00:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "query": "Query is required"
  }
}
```

### External Service Error
```json
{
  "timestamp": "2026-03-05T11:00:00",
  "status": 500,
  "error": "External Service Error",
  "message": "Error communicating with artist service: ..."
}
```

## Notes

1. **No Authentication Required**: This is a public service. All endpoints are accessible without authentication.

2. **Pagination**: Most list endpoints support pagination via `page` and `size` query parameters.

3. **Caching**: Responses are cached to improve performance. Cache is automatically managed.

4. **Circuit Breaker**: If the artist service is unavailable, the circuit breaker will open and return 503 errors.

5. **Search**:
   - Simple search matches on song title, artist name, and album title
   - Advanced search supports additional filters for genre and artist name

6. **Trending**: Currently based on play count. Future versions will integrate with analytics service for real-time trending data based on the period.
