# Artist Service - RevPlay

**Owner:** Srinivas
**Port:** 8083
**Service Name:** artist-service

## Overview

The Artist Service is responsible for managing artist profiles, music uploads, and album management in the RevPlay platform. It provides APIs for artists to create profiles, upload songs, organize albums, and track their music analytics.

## Features

- **Artist Profile Management**: Create and manage artist profiles with stage names, bios, and profile images
- **Song Upload & Management**: Upload, update, and manage individual songs
- **Album Management**: Create and organize songs into albums
- **Visibility Control**: Control song visibility (PUBLIC/UNLISTED)
- **Play Count Tracking**: Track song play counts for analytics
- **Internal APIs**: Provide song data to other microservices

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Cloud**: 2023.0.0
- **Database**: H2 (in-memory)
- **Service Discovery**: Eureka Client
- **Build Tool**: Maven

## Database Schema

### Artist Table
- `id` (Long, Primary Key)
- `user_id` (Long, Unique, Not Null) - References auth-service user
- `stage_name` (String, Not Null)
- `bio` (String, 1000 chars)
- `profile_image_url` (String)
- `verified` (Boolean, Default: false)
- `created_at` (LocalDateTime)
- `updated_at` (LocalDateTime)

### Song Table
- `id` (Long, Primary Key)
- `artist_id` (Long, Not Null)
- `album_id` (Long, Nullable)
- `title` (String, Not Null)
- `duration` (Integer, Not Null) - in seconds
- `genre` (String, Not Null)
- `file_url` (String, Not Null)
- `cover_image_url` (String)
- `visibility` (Enum: PUBLIC/UNLISTED)
- `play_count` (Long, Default: 0)
- `created_at` (LocalDateTime)
- `updated_at` (LocalDateTime)

### Album Table
- `id` (Long, Primary Key)
- `artist_id` (Long, Not Null)
- `title` (String, Not Null)
- `description` (String, 1000 chars)
- `cover_image_url` (String)
- `release_date` (LocalDate, Not Null)
- `created_at` (LocalDateTime)
- `updated_at` (LocalDateTime)

## API Endpoints

### Artist Profile Management

#### Get Current Artist Profile
```
GET /api/artists/profile
Headers: X-User-Id (required)
Response: ArtistProfileResponse
```

#### Create Artist Profile
```
POST /api/artists/profile
Headers: X-User-Id, X-User-Role (must be ARTIST)
Body: ArtistProfileRequest
Response: ArtistProfileResponse (201 Created)
```

#### Update Artist Profile
```
PUT /api/artists/profile
Headers: X-User-Id
Body: ArtistProfileRequest
Response: ArtistProfileResponse
```

#### Get Artist by ID (Public)
```
GET /api/artists/{artistId}
Response: ArtistProfileResponse
```

### Song Management

#### Upload Song
```
POST /api/artists/songs
Headers: X-User-Id
Body: SongUploadRequest
Response: SongResponse (201 Created)
```

#### Get Current Artist's Songs
```
GET /api/artists/songs
Headers: X-User-Id
Response: List<SongResponse>
```

#### Get Song Details
```
GET /api/artists/songs/{songId}
Response: SongResponse
```

#### Update Song
```
PUT /api/artists/songs/{songId}
Headers: X-User-Id
Body: SongUpdateRequest
Response: SongResponse
```

#### Delete Song
```
DELETE /api/artists/songs/{songId}
Headers: X-User-Id
Response: 204 No Content
```

#### Update Song Visibility
```
PUT /api/artists/songs/{songId}/visibility
Headers: X-User-Id
Query Params: visibility (PUBLIC/UNLISTED)
Response: SongResponse
```

### Album Management

#### Create Album
```
POST /api/artists/albums
Headers: X-User-Id
Body: AlbumRequest
Response: AlbumResponse (201 Created)
```

#### Get Current Artist's Albums
```
GET /api/artists/albums
Headers: X-User-Id
Response: List<AlbumResponse>
```

#### Get Album with Songs
```
GET /api/artists/albums/{albumId}
Response: AlbumResponse
```

#### Update Album
```
PUT /api/artists/albums/{albumId}
Headers: X-User-Id
Body: AlbumRequest
Response: AlbumResponse
```

#### Delete Album
```
DELETE /api/artists/albums/{albumId}
Headers: X-User-Id
Response: 204 No Content
```

### Internal APIs (for other microservices)

#### Get Song by ID
```
GET /api/internal/songs/{songId}
Response: SongResponse
```

#### Get Songs by IDs (Batch)
```
POST /api/internal/songs/batch
Body: List<Long> (song IDs)
Response: List<SongResponse>
```

#### Get Public Songs (Paginated)
```
GET /api/internal/songs/public
Query Params: page, size, sort
Response: Page<SongResponse>
```

#### Increment Play Count
```
POST /api/internal/songs/{songId}/play
Response: 200 OK
```

## DTOs

### Request DTOs
- `ArtistProfileRequest`: stageName, bio, profileImageUrl
- `SongUploadRequest`: title, duration, genre, fileUrl, coverImageUrl, visibility, albumId
- `SongUpdateRequest`: title, genre, coverImageUrl, visibility, albumId
- `AlbumRequest`: title, description, coverImageUrl, releaseDate

### Response DTOs
- `ArtistProfileResponse`: id, userId, stageName, bio, profileImageUrl, verified, songCount, albumCount, totalPlays, createdAt
- `SongResponse`: id, title, artistId, artistName, albumId, albumTitle, duration, genre, fileUrl, coverImageUrl, visibility, playCount, createdAt
- `AlbumResponse`: id, title, description, coverImageUrl, releaseDate, songCount, songs, createdAt

## Security

- All authenticated endpoints require `X-User-Id` header
- Artist profile creation requires `X-User-Role: ARTIST`
- Only song/album owners can update or delete their content
- Public endpoints: `/api/artists/{artistId}`, `/api/internal/**`

## Running the Service

### Prerequisites
- Java 17
- Maven 3.9+
- Eureka Server running on port 8761

### Local Development
```bash
mvn spring-boot:run
```

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/artist-service-1.0.0.jar
```

### Docker Build
```bash
docker build -t artist-service:latest .
```

### Docker Run
```bash
docker run -p 8083:8083 artist-service:latest
```

## Configuration

### Application Configuration (application.yml)
- **Server Port**: 8083
- **Database**: H2 in-memory (artistdb)
- **H2 Console**: Enabled at `/h2-console`
- **Eureka**: Registers with `http://localhost:8761/eureka/`

### Environment Variables
You can override configuration using environment variables:
- `SERVER_PORT`: Server port (default: 8083)
- `EUREKA_URI`: Eureka server URL

## Service Integration

### Required Headers from API Gateway
- `X-User-Id`: User ID from authentication
- `X-User-Role`: User role (ARTIST required for profile creation)

### Services that consume Artist Service
- **Streaming Service**: Fetches song data for playback
- **Playlist Service**: Retrieves song information for playlists
- **Analytics Service**: Tracks play counts and artist statistics

## Exception Handling

The service provides comprehensive error handling:
- `ResourceNotFoundException`: 404 - Resource not found
- `UnauthorizedException`: 403 - Unauthorized access
- `IllegalStateException`: 400 - Invalid state (e.g., profile already exists)
- `MethodArgumentNotValidException`: 400 - Validation errors
- `Exception`: 500 - Internal server error

## Validation

All request DTOs use Jakarta Validation annotations:
- `@NotBlank`: Required string fields
- `@NotNull`: Required fields
- `@Size`: String length constraints
- `@Min`: Minimum numeric values

## Business Rules

1. **Artist Profile**: User must have ARTIST role to create artist profile
2. **One Profile per User**: Each user can have only one artist profile
3. **Song Ownership**: Artists can only manage their own songs
4. **Album Ownership**: Artists can only manage their own albums
5. **Song-Album Association**: Songs can only be added to albums owned by the same artist
6. **Album Deletion**: When an album is deleted, songs are unlinked (albumId set to null)

## Database Access

H2 Console is available at: `http://localhost:8083/h2-console`
- **JDBC URL**: `jdbc:h2:mem:artistdb`
- **Username**: `sa`
- **Password**: (empty)

## Monitoring & Health

Spring Boot Actuator endpoints (if enabled):
- Health: `http://localhost:8083/actuator/health`
- Info: `http://localhost:8083/actuator/info`

## Project Structure

```
artist-service/
├── src/main/java/com/revplay/artist/
│   ├── entity/          # JPA entities
│   ├── dto/             # Data Transfer Objects
│   ├── repository/      # Spring Data repositories
│   ├── service/         # Business logic
│   ├── controller/      # REST controllers
│   ├── config/          # Configuration classes
│   ├── exception/       # Exception handling
│   └── ArtistServiceApplication.java
├── src/main/resources/
│   └── application.yml
├── pom.xml
├── Dockerfile
└── README.md
```

## Design Patterns

- **Builder Pattern**: All entities and DTOs use manual builder pattern (no Lombok)
- **Repository Pattern**: Spring Data JPA repositories
- **Service Layer Pattern**: Business logic separated from controllers
- **DTO Pattern**: Request/Response objects separate from entities
- **Global Exception Handling**: Centralized error handling

## Future Enhancements

- PostgreSQL database for production
- S3 integration for file storage
- Audio file validation
- Artist verification workflow
- Song genre taxonomy
- Collaborative albums (multiple artists)
- Song lyrics support
- Music licensing information
