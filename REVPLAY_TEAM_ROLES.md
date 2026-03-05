# RevPlay Team Roles and Responsibilities

This document outlines the comprehensive roles and responsibilities for each team member across both the monolithic (P2) and microservices (P3) projects.

---

## Team Overview

| Developer | Primary Domain | P2 Components | P3 Service |
|-----------|---------------|---------------|------------|
| **Satya** | Authentication and Security | Auth, JWT, Security Config | auth-service (Port 8081) |
| **Satish** | User Profile, Favourites & Playlist | User, Playlist, Favorites | user-service (Port 8082) |
| **Srinivas** | Artist Profile, Music & Album Management | Artist, Song, Album | artist-service (Port 8083) |
| **Prithivirajan** | Music Discovery & Search | Song Catalog, Public Browse | music-service (Port 8084) |
| **Deeja** | Play Music & Recent History | Player, Listening History | player-service (Port 8085) |
| **Tarun** | Analytics and Insights | Analytics Dashboard | analytics-service (Port 8086) |

---

## Satya - Authentication and Security

### P2 Monolithic Responsibilities

#### Backend Components
- **Controller**: `AuthController.java`
  - POST `/api/auth/register` - User registration
  - POST `/api/auth/login` - User login

- **Service Layer**:
  - `AuthService.java` (interface)
  - `AuthServiceImpl.java` (implementation)
  - `CustomUserDetailsService.java` - Spring Security UserDetailsService

- **Security Configuration**:
  - `SecurityConfig.java` - Security filter chain, CORS, password encoder
  - `JwtTokenProvider.java` - JWT token generation and validation
  - `JwtAuthenticationFilter.java` - Request authentication filter
  - `CorsConfig.java` - CORS configuration

- **DTOs**:
  - `RegisterRequest.java`
  - `LoginRequest.java`
  - `AuthResponse.java`

- **Entity Contribution**:
  - `User.java` - User entity with role field
  - `Role.java` - Enum (USER, ARTIST)

#### Frontend Components (Angular)
- Login component
- Registration component
- Auth guard service
- JWT interceptor
- Auth service

### P3 Microservices Responsibilities

#### auth-service (Port 8081)
- **Entities**: User, RefreshToken
- **DTOs**: RegisterRequest, LoginRequest, AuthResponse, TokenRefreshRequest, TokenRefreshResponse
- **Repository**: UserRepository, RefreshTokenRepository
- **Service**: AuthService, JwtService, RefreshTokenService
- **Controller**: AuthController
  - POST `/api/auth/register` - Register new user
  - POST `/api/auth/login` - Login and get JWT
  - POST `/api/auth/refresh` - Refresh JWT token
  - POST `/api/auth/logout` - Logout (invalidate refresh token)
  - GET `/api/auth/validate` - Validate JWT token (internal)

#### Key Features to Implement
1. JWT token generation with claims (userId, email, role)
2. Refresh token mechanism
3. Password hashing with BCrypt
4. Token validation endpoint for other services
5. Role-based registration (USER/ARTIST)

---

## Satish - User Profile, Favourites & Playlist

### P2 Monolithic Responsibilities

#### Backend Components
- **Controllers**:
  - `UserController.java`
    - GET `/api/users/profile` - Get current user profile
    - PUT `/api/users/profile` - Update profile
    - GET `/api/users/stats` - Get user statistics
    - GET `/api/users/{username}` - Get user by username
  - `PlaylistController.java`
    - POST `/api/playlists` - Create playlist
    - GET `/api/playlists` - Get user's playlists
    - PUT `/api/playlists/{id}` - Update playlist
    - DELETE `/api/playlists/{id}` - Delete playlist
    - POST `/api/playlists/{id}/songs/{songId}` - Add song to playlist
    - DELETE `/api/playlists/{id}/songs/{songId}` - Remove song from playlist
  - `FavoriteController.java`
    - POST `/api/favorites/{songId}` - Add to favorites
    - DELETE `/api/favorites/{songId}` - Remove from favorites
    - GET `/api/favorites` - Get user's favorites

- **Service Layer**:
  - `UserService.java` / `UserServiceImpl.java`
  - `PlaylistService.java` / `PlaylistServiceImpl.java`
  - `FavoriteService.java` / `FavoriteServiceImpl.java`

- **Repositories**:
  - `UserRepository.java`
  - `PlaylistRepository.java`
  - `PlaylistSongRepository.java`
  - `FavoriteRepository.java`

- **Entities**:
  - `Playlist.java`
  - `PlaylistSong.java`
  - `Favorite.java`

- **DTOs**:
  - `UserProfileRequest.java`
  - `UserProfileResponse.java`
  - `UserStatsResponse.java`
  - `PlaylistRequest.java`
  - `PlaylistResponse.java`
  - `FavoriteResponse.java`

#### Frontend Components
- User profile page
- Profile edit component
- Playlist management UI
- Favorites page
- User statistics dashboard

### P3 Microservices Responsibilities

#### user-service (Port 8082)
- **Entities**: UserProfile, Playlist, PlaylistSong, Favorite
- **DTOs**:
  - UserProfileRequest, UserProfileResponse, UserStatsResponse
  - PlaylistRequest, PlaylistResponse, PlaylistSongRequest
  - FavoriteRequest, FavoriteResponse
- **Repositories**: UserProfileRepository, PlaylistRepository, PlaylistSongRepository, FavoriteRepository
- **Services**: UserProfileService, PlaylistService, FavoriteService
- **Feign Clients**:
  - ArtistServiceClient (to get song details for playlists/favorites)
  - PlayerServiceClient (to get listening stats)
- **Controllers**: UserProfileController, PlaylistController, FavoriteController

#### Key Features to Implement
1. User profile CRUD (linked to auth-service userId)
2. Playlist management with song ordering
3. Favorites management
4. User statistics aggregation
5. Integration with artist-service for song metadata

---

## Srinivas - Artist Profile, Music & Album Management

### P2 Monolithic Responsibilities

#### Backend Components
- **Controllers**:
  - `ArtistController.java`
    - GET `/api/artists/{artistId}` - Get artist profile
    - PUT `/api/artists/{artistId}` - Update artist profile
  - `SongController.java`
    - POST `/api/artists/{artistId}/songs/upload` - Upload song
    - GET `/api/artists/{artistId}/songs` - Get artist's songs
    - PUT `/api/artists/{artistId}/songs/{songId}` - Update song
    - DELETE `/api/artists/{artistId}/songs/{songId}` - Delete song
    - PUT `/api/artists/{artistId}/songs/{songId}/album/{albumId}` - Add song to album
    - PUT `/api/artists/{artistId}/songs/{songId}/visibility` - Update visibility
  - `AlbumController.java`
    - POST `/api/artists/{artistId}/albums` - Create album
    - GET `/api/artists/{artistId}/albums` - Get artist's albums
    - PUT `/api/artists/{artistId}/albums/{albumId}` - Update album
    - DELETE `/api/artists/{artistId}/albums/{albumId}` - Delete album

- **Service Layer**:
  - `ArtistService.java` / `ArtistServiceImpl.java`
  - `SongService.java` / `SongServiceImpl.java`
  - `AlbumService.java` / `AlbumServiceImpl.java`

- **Repositories**:
  - `ArtistRepository.java`
  - `SongRepository.java`
  - `AlbumRepository.java`

- **Entities**:
  - `Artist.java`
  - `Song.java`
  - `Album.java`
  - `Visibility.java` (enum)

- **DTOs**:
  - `ArtistProfileRequest.java`
  - `ArtistProfileResponse.java`
  - `SongUploadRequest.java`
  - `SongResponse.java`
  - `AlbumRequest.java`
  - `AlbumResponse.java`

#### Frontend Components
- Artist profile page
- Artist profile edit
- Song upload component
- Song management UI
- Album management UI
- Visibility toggle

### P3 Microservices Responsibilities

#### artist-service (Port 8083)
- **Entities**: Artist, Song, Album, Visibility (enum)
- **DTOs**:
  - ArtistProfileRequest, ArtistProfileResponse
  - SongUploadRequest, SongUpdateRequest, SongResponse
  - AlbumRequest, AlbumResponse
- **Repositories**: ArtistRepository, SongRepository, AlbumRepository
- **Services**: ArtistService, SongService, AlbumService
- **Controllers**: ArtistController, SongController, AlbumController
- **Internal Endpoints**:
  - GET `/api/internal/songs/public` - Get public songs (for music-service)
  - GET `/api/internal/songs/{songId}` - Get song by ID (for other services)

#### Key Features to Implement
1. Artist profile management (linked to auth-service userId with ARTIST role)
2. Song upload with metadata
3. Album creation and management
4. Song-album association
5. Visibility control (PUBLIC/UNLISTED)
6. Internal endpoints for cross-service communication

---

## Prithivirajan - Music Discovery & Search

### P2 Monolithic Responsibilities

#### Backend Components
- **Controller**: `SongCatalogController.java`
  - GET `/api/songs/public` - Get public songs (paginated)
  - GET `/api/songs/public/{songId}` - Get public song by ID
  - (Search functionality via query params)

- **Service Layer**:
  - Contributions to `SongService.java` for public song queries
  - Search/filter logic

- **Repository Contributions**:
  - `SongRepository.java` - findByVisibility(), search queries

#### Frontend Components
- Browse/Catalog page
- Search component
- Song discovery UI
- Genre filtering
- Search results page

### P3 Microservices Responsibilities

#### music-service (Port 8084)
- **DTOs**:
  - SongCatalogResponse, SongSearchRequest, SongSearchResponse
  - GenreResponse, TrendingSongsResponse
- **Services**: CatalogService, SearchService
- **Feign Clients**:
  - ArtistServiceClient (to fetch public songs)
- **Controllers**: CatalogController, SearchController
  - GET `/api/catalog/songs` - Browse public songs (paginated)
  - GET `/api/catalog/songs/{songId}` - Get song details
  - GET `/api/catalog/songs/search` - Search songs
  - GET `/api/catalog/genres` - Get available genres
  - GET `/api/catalog/trending` - Get trending songs

#### Key Features to Implement
1. Public song catalog with pagination
2. Full-text search across songs, artists, albums
3. Genre-based filtering
4. Trending songs (integration with analytics-service)
5. Song recommendations (future enhancement)
6. Caching for popular queries

---

## Deeja - Play Music & Recent History

### P2 Monolithic Responsibilities

#### Backend Components
- **Controllers**:
  - `PlayerController.java`
    - POST `/api/player/play` - Record play event
  - `ListeningHistoryController.java`
    - GET `/api/history/recent` - Get recent listening history
    - DELETE `/api/history` - Clear listening history

- **Service Layer**:
  - `ListeningHistoryService.java` / `ListeningHistoryServiceImpl.java`
  - recordPlay(), getRecentHistory(), clearHistory()

- **Repository**:
  - `ListeningHistoryRepository.java`
  - Custom queries for history retrieval

- **Entity**:
  - `ListeningHistory.java`

- **DTOs**:
  - `PlayRequest.java`
  - `ListeningHistoryResponse.java`

#### Frontend Components
- Music player component
- Now playing UI
- Recent history list
- Play controls
- Progress bar

### P3 Microservices Responsibilities

#### player-service (Port 8085)
- **Entities**: ListeningHistory
- **DTOs**:
  - PlayRequest, PlayResponse
  - ListeningHistoryResponse
  - NowPlayingResponse
- **Repositories**: ListeningHistoryRepository
- **Services**: PlayerService, ListeningHistoryService
- **Feign Clients**:
  - ArtistServiceClient (to get song metadata)
- **Controllers**: PlayerController, ListeningHistoryController
  - POST `/api/player/play` - Record play and return stream info
  - GET `/api/player/now-playing` - Get current playing song
  - GET `/api/history/recent` - Get recent history
  - DELETE `/api/history` - Clear history
  - GET `/api/internal/history/user/{userId}` - Get user history (for analytics)

#### Key Features to Implement
1. Play event recording with timestamp
2. Listening history with pagination
3. Recent plays tracking
4. Play count increment
5. Internal endpoints for analytics-service
6. Stream URL generation (if applicable)

---

## Tarun - Analytics and Insights

### P2 Monolithic Responsibilities

#### Backend Components
- **Controller**: `AnalyticsController.java`
  - GET `/api/artist/analytics/{artistId}/overview` - Artist overview
  - GET `/api/artist/analytics/{artistId}/songs` - Song performance
  - GET `/api/artist/analytics/{artistId}/top` - Top songs
  - GET `/api/artist/analytics/{artistId}/trends` - Listening trends
  - GET `/api/artist/analytics/{artistId}/top-listeners` - Top listeners

- **Service Layer**:
  - `AnalyticsService.java` / `AnalyticsServiceImpl.java`
  - getOverview(), getSongPerformance(), getTopSongs(), getListeningTrends(), getTopListeners()

- **Repository Contributions**:
  - `ListeningHistoryRepository.java` - Custom queries for:
    - Daily trends (date grouping)
    - Top listeners (user grouping)
    - Play counts per song

- **DTOs**:
  - `AnalyticsOverviewResponse.java`
  - `SongPerformanceResponse.java`
  - `ListeningTrendResponse.java`
  - `TopListenerResponse.java`

#### Frontend Components
- Analytics dashboard
- Charts and graphs
- Trend visualizations
- Top songs list
- Listener demographics

### P3 Microservices Responsibilities

#### analytics-service (Port 8086)
- **DTOs**:
  - AnalyticsOverviewResponse
  - SongPerformanceResponse
  - ListeningTrendResponse, DailyTrendData
  - TopListenerResponse
  - TopSongsResponse
- **Services**: AnalyticsService
- **Feign Clients**:
  - PlayerServiceClient (to get listening history data)
  - ArtistServiceClient (to get song/artist details)
  - UserServiceClient (to get user details for top listeners)
- **Controllers**: AnalyticsController
  - GET `/api/analytics/artist/{artistId}/overview` - Overview stats
  - GET `/api/analytics/artist/{artistId}/songs` - Song performance
  - GET `/api/analytics/artist/{artistId}/top` - Top songs
  - GET `/api/analytics/artist/{artistId}/trends` - Daily/weekly trends
  - GET `/api/analytics/artist/{artistId}/listeners` - Top listeners
  - GET `/api/analytics/platform` - Platform-wide analytics (admin)

#### Key Features to Implement
1. Artist analytics aggregation
2. Song performance metrics
3. Listening trends over time
4. Top listeners identification
5. Cross-service data aggregation
6. Caching for performance
7. Scheduled data refresh

---

## Service Dependencies

```
                    ┌─────────────────┐
                    │   API Gateway   │
                    │    (8080)       │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│auth-service │     │user-service │     │artist-service│
│   (8081)    │     │   (8082)    │     │   (8083)    │
│   [Satya]   │     │  [Satish]   │     │ [Srinivas]  │
└─────────────┘     └─────────────┘     └─────────────┘
                           │                   │
                           │      ┌────────────┘
                           ▼      ▼
                    ┌─────────────┐
                    │music-service│
                    │   (8084)    │
                    │[Prithivirajan]│
                    └─────────────┘
                           │
         ┌─────────────────┼─────────────────┐
         ▼                 │                 ▼
┌─────────────┐            │        ┌─────────────┐
│player-service│           │        │analytics-svc│
│   (8085)    │────────────┘        │   (8086)    │
│  [Deeja]    │                     │   [Tarun]   │
└─────────────┘                     └─────────────┘
```

### Dependency Matrix

| Service | Depends On |
|---------|------------|
| auth-service | None (independent) |
| user-service | auth-service (validate), artist-service (song details) |
| artist-service | auth-service (validate) |
| music-service | artist-service (public songs) |
| player-service | auth-service (validate), artist-service (song details) |
| analytics-service | player-service (history), artist-service (songs), user-service (users) |

---

## Technology Stack (P3)

- **Framework**: Spring Boot 3.2
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration**: Spring Cloud Config (optional)
- **Inter-service Communication**: OpenFeign
- **Resilience**: Resilience4j (Circuit Breaker)
- **Security**: JWT Authentication
- **Database**: H2 (per service, in-memory)
- **Build Tool**: Maven
- **Java Version**: 17

---

## Port Assignments

| Service | Port |
|---------|------|
| Config Server | 8888 |
| Discovery Server (Eureka) | 8761 |
| API Gateway | 8080 |
| auth-service | 8081 |
| user-service | 8082 |
| artist-service | 8083 |
| music-service | 8084 |
| player-service | 8085 |
| analytics-service | 8086 |
