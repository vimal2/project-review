# Project Presentation Deck - RevPlay

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 6 members
**Template Version:** 1.0

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                        RevPlay                              │
│              Music Streaming Platform                       │
│                                                             │
│     Phase 1: Monolithic Application (AWS Deployment)        │
│     Phase 2: Microservices Architecture (Docker)            │
│                                                             │
│     Team Members (Full Stack Developers):                   │
│     • Prithivirajan - Music Discovery & Search              │
│     • Tarun - Analytics and Insights                        │
│     • Satish - User Profile, Favourites & Playlist          │
│     • Satya - Authentication and Access Control             │
│     • Srinivas - Artist Profile, Music & Album Management   │
│     • Deeja - Play Music & Recent History                   │
│                                                             │
│     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A comprehensive music streaming platform enabling users to discover, browse, and play music with role-based access control supporting both regular users and artists with profile management, analytics, playlists, and listening history features.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | Browse Public Songs Catalog | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-02 | Search & Filter Songs | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-03 | Play Music & Track History | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-04 | Artist Profile & Song Upload | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-05 | Album Management | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-06 | Artist Analytics & Insights | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-07 | User Registration & JWT Auth | Common Features (6 pts) | ✓ | ✓ |
| FR-08 | User Profile Management | Common Features (6 pts) | ✓ | ✓ |
| FR-09 | Playlist Creation & Management | Common Features (6 pts) | ✓ | ✓ |
| FR-10 | Favorites Management | Common Features | ✓ | ✓ |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Authentication | JWT with Refresh Tokens |
| Security | Password Storage | BCrypt Hashing |
| Performance | API Response | < 500ms |
| Resilience | Circuit Breaker | Resilience4j |
| Caching | Performance | Caffeine/In-Memory Cache |
| Availability | Uptime (P1) | 99% |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | H2 in-memory database for development (MySQL for production) |
| A-02 | AWS Free Tier resources accessible for Phase 1 |
| A-03 | Docker environment available for Phase 2 microservices |
| A-04 | Team has GitHub repository access |
| A-05 | Two user roles: USER (listeners) and ARTIST (content creators) |
| A-06 | Music files hosted externally (URLs stored in database) |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Inter-service communication failures | High | Circuit breakers with Resilience4j |
| Expensive analytics computations | Medium | Caffeine caching with 5-min TTL |
| Service discovery failures | High | Eureka health checks, retry logic |
| Insufficient test coverage | High | Unit tests, integration tests |
| AWS deployment issues | High | Docker Compose local fallback |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
┌────────────────────────────────────────────────────────────┐
│                  ANGULAR FRONTEND                           │
│              (Components, Services, Guards)                 │
│                    Port: 4200                               │
│                                                             │
│  Modules: auth, music/browse, user, artist, player, history│
└─────────────────────────┬──────────────────────────────────┘
                          │ HTTP/REST + JWT
┌─────────────────────────┴──────────────────────────────────┐
│               SPRING BOOT MONOLITH (Port 8080)              │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                    CONTROLLERS                       │   │
│  │  Auth, User, Artist, Song, Album, Player, History   │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                     SERVICES                         │   │
│  │  AuthService, UserService, ArtistService,           │   │
│  │  SongService, PlaylistService, PlayerService        │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                   REPOSITORIES                       │   │
│  │  JPA Repositories for all entities                  │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │     Security: JWT + Spring Security (USER/ARTIST)    │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────┬──────────────────────────────────┘
                          │ JDBC/JPA
┌─────────────────────────┴──────────────────────────────────┐
│                    MySQL DATABASE                           │
└────────────────────────────────────────────────────────────┘
```

#### Phase 2: Microservices Architecture

```
┌────────────────────────────────────────────────────────────┐
│                    ANGULAR FRONTEND                         │
└─────────────────────────┬──────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────┐
│               API GATEWAY (Port 8080)                       │
│         JWT Validation + Request Routing + CORS             │
│     X-User-Id, X-User-Role Header Injection                 │
└────────┬──────────┬──────────┬──────────┬──────────────────┘
         │          │          │          │
    ┌────┴────┐ ┌───┴───┐ ┌────┴────┐ ┌───┴────┐
    │  Auth   │ │ User  │ │ Artist  │ │ Music  │
    │ Service │ │Service│ │ Service │ │Service │
    │  8081   │ │ 8082  │ │  8083   │ │  8084  │
    │ [Satya] │ │[Satish]│ │[Srinivas]│[Prithivi]│
    └─────────┘ └───┬───┘ └────┬────┘ └────────┘
                    │          │
                    │     ┌────┴────────────┐
                    │     │                 │
               ┌────┴────┐           ┌──────┴─────┐
               │ Player  │           │ Analytics  │
               │ Service │           │  Service   │
               │  8085   │───────────│   8086     │
               │ [Deeja] │           │  [Tarun]   │
               └─────────┘           └────────────┘

         ┌─────────────────────┬─────────────────────┐
         │                     │                     │
┌────────┴────────┐  ┌─────────┴─────────┐  ┌───────┴───────┐
│ Discovery Server│  │   Config Server   │  │  H2 Database  │
│  Eureka (8761)  │  │      (8888)       │  │  (per service)│
└─────────────────┘  └───────────────────┘  └───────────────┘
```

#### Service Communication Flow

```
User Request → API Gateway → JWT Validation
    → Extract User Info → Add Headers (X-User-Id, X-User-Role)
    → Route to Service → Feign Client calls (if needed)
    → Response with Circuit Breaker protection
```

---

## CORE FEATURES SECTION (40 Points)

---

### SLIDE 5: Main Use Case 1 - Music Discovery & Playback Design (20 Points) (2 minutes)

**Use Case:** Music Discovery, Search & Playback

#### User Stories

> As a **User**, I want to browse public songs so that I can discover new music.

> As a **User**, I want to search and filter songs by title, artist, or genre so that I can find specific music.

> As a **User**, I want to play songs and track my listening history so that I can revisit music I enjoyed.

#### Feature Flow

```
┌─────────────┐   ┌──────────────┐   ┌───────────────┐   ┌─────────────┐
│   Browse    │──►│   Search/    │──►│   Select      │──►│    Play     │
│   Catalog   │   │   Filter     │   │    Song       │   │    Music    │
└─────────────┘   └──────────────┘   └───────────────┘   └─────────────┘
                                                                │
                                                                ▼
┌─────────────────────────────────────────────────────────────────────┐
│                      PLAY EVENT TRACKING                             │
│                                                                      │
│   ┌─────────────┐   ┌──────────────┐   ┌───────────────────────────┐│
│   │  Record     │──►│   Update     │──►│  Available in History    ││
│   │  Play Event │   │  Play Count  │   │  & Analytics             ││
│   └─────────────┘   └──────────────┘   └───────────────────────────┘│
└─────────────────────────────────────────────────────────────────────┘
```

#### API Endpoints for Music Discovery & Playback

| Method | Endpoint | Description | Service |
|--------|----------|-------------|---------|
| GET | /api/catalog/songs | Browse public songs (paginated) | Music |
| GET | /api/catalog/songs/{songId} | Get song details | Music |
| GET | /api/catalog/search?q={query} | Search songs | Music |
| GET | /api/catalog/genres | Get available genres | Music |
| GET | /api/catalog/trending | Get trending songs | Music |
| GET | /api/catalog/trending/{period} | Trending by period (daily/weekly/monthly) | Music |
| POST | /api/player/play | Record play event | Player |
| GET | /api/player/now-playing | Get currently playing | Player |
| GET | /api/history/recent | Get recent history | Player |
| GET | /api/history/stats | Get listening statistics | Player |
| DELETE | /api/history | Clear history | Player |

#### Implementation Highlights

- **Database-Free Music Service:** Aggregates data from Artist Service via Feign client
- **Circuit Breaker Pattern:** Resilience4j protects against Artist Service failures
- **In-Memory Caching:** Songs, genres, search results, trending cached
- **Play Count Tracking:** Player Service increments count in Artist Service
- **Listening Duration:** Track how long users listen to each song

---

### SLIDE 6: Main Use Case 1 - Music Discovery & Playback DEMO (3-4 minutes)

**Live Demo Script:**

| Step | Action | Expected Result | Points Demonstrated |
|------|--------|-----------------|---------------------|
| 1 | Login as User | Dashboard loads | Authentication |
| 2 | Browse Songs | Paginated song list displayed | Catalog API |
| 3 | Filter by Genre | Songs filtered by selected genre | Genre Filtering |
| 4 | Search for Song | Search results displayed | Search Feature |
| 5 | View Trending | Trending songs by period | Trending API |
| 6 | Click Play | Song plays, play event recorded | Player Service |
| 7 | Check Now Playing | Current song info displayed | Now Playing API |
| 8 | View History | Recent songs in history | History Tracking |
| 9 | View Stats | Total plays, listening time | Stats API |
| 10 | Clear History | History cleared successfully | History Management |

**Backup:** Screenshots/video recording prepared for demo failure scenarios

---

### SLIDE 7: Use Case 2 - Artist Management Design (14 Points) (1-2 minutes)

**Use Case:** Artist Profile, Music Upload & Analytics

#### User Stories

> As an **Artist**, I want to create my profile so that listeners can discover me.

> As an **Artist**, I want to upload songs and organize them into albums so that I can share my music.

> As an **Artist**, I want to view analytics so that I can understand my audience.

#### Feature Components

```
┌─────────────────────────────────────────────────────────────────┐
│                  ARTIST PROFILE MANAGEMENT                       │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │    Create     │───►│    Update     │───►│    Verified    │  │
│   │    Profile    │    │  Stage Name   │    │    Badge       │  │
│   │  (ARTIST role)│    │   Bio, Image  │    │   (future)     │  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      SONG MANAGEMENT                             │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │    Upload     │───►│   Associate   │───►│   Visibility   │  │
│   │     Song      │    │  with Album   │    │ PUBLIC/UNLISTED│  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
│                                                                  │
│   Song Fields: title, duration, genre, fileUrl, coverImageUrl   │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                     ALBUM MANAGEMENT                             │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │    Create     │───►│  Add Songs    │───►│    Release     │  │
│   │    Album      │    │   to Album    │    │     Date       │  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                   ANALYTICS & INSIGHTS                           │
│                                                                  │
│   ┌───────────────┐    ┌───────────────┐    ┌────────────────┐  │
│   │   Overview    │    │  Top Songs    │    │   Listening    │  │
│   │  (plays,      │    │  Performance  │    │    Trends      │  │
│   │  listeners)   │    │   Metrics     │    │  (daily/weekly)│  │
│   └───────────────┘    └───────────────┘    └────────────────┘  │
│                              │                                   │
│                              ▼                                   │
│                    ┌────────────────┐                           │
│                    │  Top Listeners │                           │
│                    │   (Top Fans)   │                           │
│                    └────────────────┘                           │
└─────────────────────────────────────────────────────────────────┘
```

#### API Endpoints for Artist Management

| Method | Endpoint | Description | Service |
|--------|----------|-------------|---------|
| GET | /api/artists/profile | Get current artist profile | Artist |
| POST | /api/artists/profile | Create artist profile | Artist |
| PUT | /api/artists/profile | Update artist profile | Artist |
| POST | /api/artists/songs | Upload song | Artist |
| GET | /api/artists/songs | Get artist's songs | Artist |
| PUT | /api/artists/songs/{songId} | Update song | Artist |
| DELETE | /api/artists/songs/{songId} | Delete song | Artist |
| POST | /api/artists/albums | Create album | Artist |
| GET | /api/artists/albums | Get artist's albums | Artist |
| GET | /api/analytics/artist/{id}/overview | Analytics overview | Analytics |
| GET | /api/analytics/artist/{id}/top | Top songs | Analytics |
| GET | /api/analytics/artist/{id}/trends | Listening trends | Analytics |
| GET | /api/analytics/artist/{id}/listeners | Top listeners | Analytics |

---

### SLIDE 8: Use Case 2 - Artist Management DEMO (2-3 minutes)

**Live Demo Script:**

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Login as Artist | Artist dashboard displayed |
| 2 | Create Artist Profile | Enter stage name, bio, image URL |
| 3 | Upload Song | Fill song details, set visibility |
| 4 | Create Album | Enter album details, release date |
| 5 | Add Song to Album | Associate song with album |
| 6 | View My Songs | List of uploaded songs |
| 7 | Update Song Visibility | Toggle PUBLIC/UNLISTED |
| 8 | View Analytics Overview | Total plays, unique listeners |
| 9 | View Top Songs | Song performance metrics |
| 10 | View Listening Trends | Daily/weekly trend chart data |

---

### SLIDE 9: Common Features (6 Points) (1 minute)

#### Authentication Flow

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   Register   │───►│    Login     │───►│   Generate   │───►│   Access     │
│  (USER or    │    │  (Validate)  │    │   JWT +      │    │  Protected   │
│   ARTIST)    │    │              │    │  Refresh     │    │  Resources   │
└──────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
                                        ┌──────────────┐
                                        │   Refresh    │
                                        │    Token     │
                                        │  (7 days)    │
                                        └──────────────┘
```

#### Common Features Implemented

| Feature | Implementation | Status |
|---------|---------------|--------|
| User Registration | Spring Security + BCrypt | ✓ |
| JWT Authentication | Access + Refresh tokens | ✓ |
| Role-Based Access | USER, ARTIST roles | ✓ |
| Token Refresh | 7-day refresh token | ✓ |
| User Profiles | Create, read, update | ✓ |
| Playlists | Create, add/remove songs | ✓ |
| Favorites | Add/remove songs | ✓ |
| Input Validation | Jakarta Validation | ✓ |
| Error Handling | GlobalExceptionHandler | ✓ |
| CORS Support | Configured for frontend | ✓ |

#### Playlist & Favorites Features

```
┌─────────────────────────────────────────────────────────────────┐
│                    PLAYLIST MANAGEMENT                           │
│                                                                  │
│   • Create named playlists                                       │
│   • Add songs to playlist                                        │
│   • Remove songs from playlist                                   │
│   • Update playlist name/description                             │
│   • Delete playlist                                              │
│   • View playlist with enriched song data                        │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    FAVORITES MANAGEMENT                          │
│                                                                  │
│   • Add song to favorites                                        │
│   • Remove song from favorites                                   │
│   • Check if song is favorited                                   │
│   • View all favorites with song details                         │
└─────────────────────────────────────────────────────────────────┘
```

**Quick Demo:** Register → Login → Create Playlist → Add Favorites

---

## TECHNICAL STANDARDS SECTION (35 Points)

---

### SLIDE 10: Code Organization (10 Points) (2 minutes)

#### Backend Package Structure (Phase 1 - Monolith)

```
com.revplay/
├── controller/              # REST Controllers
│   ├── AuthController.java
│   ├── UserController.java
│   ├── ArtistController.java
│   ├── SongController.java
│   ├── AlbumController.java
│   ├── PlaylistController.java
│   └── PlayerController.java
├── service/                 # Business Logic Layer
├── repository/              # Data Access Layer
├── model/                   # JPA Entities
├── dto/                     # Data Transfer Objects
├── config/                  # Configuration Classes
│   └── SecurityConfig.java
└── exception/               # Exception Handling
```

#### Phase 2 Microservices Structure

```
p3-revplay/
├── config-server/           # Centralized config (Port 8888)
├── discovery-server/        # Eureka registry (Port 8761)
├── api-gateway/             # Request routing (Port 8080)
│   └── JwtAuthenticationFilter.java
├── auth-service/            # Authentication (Port 8081) [Satya]
│   ├── controller/AuthController.java
│   ├── service/JwtService.java
│   └── service/RefreshTokenService.java
├── user-service/            # User management (Port 8082) [Satish]
│   ├── controller/UserController.java
│   ├── controller/PlaylistController.java
│   └── controller/FavoriteController.java
├── artist-service/          # Artist & songs (Port 8083) [Srinivas]
│   ├── controller/ArtistController.java
│   ├── controller/SongController.java
│   ├── controller/AlbumController.java
│   └── controller/InternalController.java
├── music-service/           # Discovery (Port 8084) [Prithivirajan]
│   ├── controller/CatalogController.java
│   ├── client/ArtistServiceClient.java
│   └── (NO DATABASE - aggregation only)
├── player-service/          # Playback (Port 8085) [Deeja]
│   ├── controller/PlayerController.java
│   ├── controller/HistoryController.java
│   └── controller/InternalController.java
├── analytics-service/       # Analytics (Port 8086) [Tarun]
│   ├── controller/AnalyticsController.java
│   └── (NO DATABASE - aggregation with caching)
├── docker-compose.yml       # Container orchestration
└── pom.xml                  # Parent POM
```

#### Frontend Module Structure

```
frontend/src/app/modules/
├── auth/                    # Login, Register
├── music/browse/            # Song discovery
├── user/                    # Profile, Favorites, Playlists, Dashboard
├── artist/                  # Artist-specific features
├── player/                  # Music playback
└── history/                 # Listening history
```

#### Design Patterns Used

| Pattern | Where Used | Benefit |
|---------|-----------|---------|
| Repository Pattern | Data access layer | Abstraction, testability |
| Service Layer | Business logic | Separation of concerns |
| Builder Pattern | All DTOs and entities | Clean object construction (no Lombok) |
| Feign Clients | Inter-service communication | Declarative REST calls |
| Circuit Breaker | All Feign calls | Resilience against failures |
| Service Discovery | Eureka | Dynamic service location |
| API Gateway | Single entry point | Routing, authentication |
| Aggregation | Music & Analytics services | No database, data from other services |

---

### SLIDE 11: Database & Security (15 Points) (2-3 minutes)

#### Entity Relationship Diagram (ERD)

```
┌─────────────────┐          ┌─────────────────────┐
│      USERS      │          │      ARTISTS        │
├─────────────────┤          ├─────────────────────┤
│ PK id           │          │ PK id               │
│    email (UK)   │◄────────►│ FK user_id (UK)     │
│    password     │   1:1    │    stage_name       │
│    firstName    │          │    bio              │
│    lastName     │          │    profile_image_url│
│    role         │          │    verified         │
│    created_at   │          │    created_at       │
└────────┬────────┘          └──────────┬──────────┘
         │                              │
         │ 1:N                          │ 1:N
         ▼                              ▼
┌─────────────────┐          ┌─────────────────────┐
│ REFRESH_TOKENS  │          │       SONGS         │
├─────────────────┤          ├─────────────────────┤
│ PK id           │          │ PK id               │
│ FK user_id      │          │ FK artist_id        │
│    token        │          │ FK album_id (null)  │
│    expiry_date  │          │    title            │
└─────────────────┘          │    duration         │
                             │    genre            │
┌─────────────────┐          │    file_url         │
│   USER_PROFILES │          │    cover_image_url  │
├─────────────────┤          │    visibility       │
│ PK id           │          │    play_count       │
│ FK user_id (UK) │          └──────────┬──────────┘
│    displayName  │                     │
│    avatarUrl    │                     │
└────────┬────────┘          ┌──────────┴──────────┐
         │                   │                     │
         │ 1:N               ▼                     ▼
         ▼          ┌─────────────────┐   ┌───────────────────┐
┌─────────────────┐ │     ALBUMS      │   │ LISTENING_HISTORY │
│    PLAYLISTS    │ ├─────────────────┤   ├───────────────────┤
├─────────────────┤ │ PK id           │   │ PK id             │
│ PK id           │ │ FK artist_id    │   │ FK user_id        │
│ FK user_id      │ │    title        │   │ FK song_id        │
│    name         │ │    description  │   │    played_at      │
│    description  │ │    cover_image  │   │    duration       │
│    created_at   │ │    release_date │   └───────────────────┘
└────────┬────────┘ └─────────────────┘
         │
         │ N:M
         ▼
┌─────────────────┐          ┌─────────────────────┐
│ PLAYLIST_SONGS  │          │     FAVORITES       │
├─────────────────┤          ├─────────────────────┤
│ FK playlist_id  │          │ PK id               │
│ FK song_id      │          │ FK user_id          │
│    position     │          │ FK song_id          │
└─────────────────┘          │    added_at         │
                             └─────────────────────┘
```

#### Security Implementation

| Security Layer | Implementation | Details |
|----------------|---------------|---------|
| **Authentication** | JWT + Spring Security | HS256 algorithm |
| **Access Token** | JWT | 24-hour expiry |
| **Refresh Token** | Database-stored | 7-day expiry |
| **Password Storage** | BCrypt | Secure hashing |
| **Role-Based Access** | USER, ARTIST | @PreAuthorize |
| **Header Injection** | API Gateway | X-User-Id, X-User-Role |
| **Input Validation** | Jakarta Validation | @NotBlank, @Size, @Email |
| **CSRF** | Disabled | Stateless API |
| **CORS** | Enabled | All origins (dev) |

#### Security Flow (Phase 2)

```
Request → API Gateway → JWT Filter → Validate Token
    → Extract Claims → Add Headers (X-User-Id, X-User-Role)
    → Route to Service → Service validates role if needed → Response
```

---

### SLIDE 12: UX Design (10 Points) (1-2 minutes)

#### UI/UX Highlights

| Aspect | Implementation |
|--------|---------------|
| **Responsive Design** | Angular responsive components |
| **Navigation** | Role-based menus (User/Artist) |
| **Music Player** | Integrated playback controls |
| **Search** | Real-time search with filters |
| **Grid Layout** | Playlist and song displays |
| **Trending** | Period-based trending sections |

#### Key Screens

```
┌─────────────────────────────────────────────────────────────────┐
│  [Login Page]                    [Browse Songs]                 │
│                                                                 │
│  • Email/password form           • Song grid with covers        │
│  • Role selection (User/Artist)  • Genre filter dropdown        │
│  • Register link                 • Search bar                   │
│  • Forgot password               • Trending section             │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Music Player]                  [User Dashboard]               │
│                                                                 │
│  • Album art display             • Recently played              │
│  • Play/pause/skip controls      • Playlists overview           │
│  • Progress bar                  • Favorites count              │
│  • Volume control                • Listening stats              │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Artist Dashboard]              [Analytics]                    │
│                                                                 │
│  • Profile overview              • Total plays chart            │
│  • Upload song button            • Top songs list               │
│  • My songs list                 • Listener trends              │
│  • My albums list                • Top fans                     │
└─────────────────────────────────────────────────────────────────┘
```

#### User Flow Optimization

- Role-based dashboard on login
- One-click add to favorites
- Drag-drop playlist management
- Real-time search results
- Seamless music playback

---

## TESTING & DOCUMENTATION SECTION (25 Points)

---

### SLIDE 13: Testing (15 Points) (2 minutes)

#### Test Coverage Summary

| Test Type | Framework | Coverage | Status |
|-----------|-----------|----------|--------|
| Unit Tests (Backend) | JUnit + Mockito | Service layer | ✓ |
| Integration Tests | Spring Boot Test | API endpoints | [INFO NOT AVAILABLE] |
| Frontend Tests | Jasmine + Karma | Components | [INFO NOT AVAILABLE] |
| Circuit Breaker Tests | WireMock | Feign clients | Recommended |

#### Test Classes by Service (Phase 2)

| Service | Test Focus |
|---------|-----------|
| Auth Service | Registration, login, token refresh, validation |
| User Service | Profile CRUD, playlist operations, favorites |
| Artist Service | Profile, song upload, album management |
| Music Service | Catalog browsing, search, Feign client mocking |
| Player Service | Play events, history tracking, stats |
| Analytics Service | Aggregation logic, caching, authorization |

#### Sample Test Case

```java
@Test
void playMusic_RecordsEventAndIncrementsPlayCount() {
    // Arrange
    Long userId = 1L;
    Long songId = 100L;
    PlayRequest request = PlayRequest.builder().songId(songId).build();

    SongResponse mockSong = SongResponse.builder()
        .id(songId)
        .title("Test Song")
        .artistName("Test Artist")
        .build();

    when(artistServiceClient.getSongById(songId)).thenReturn(mockSong);
    when(historyRepository.save(any())).thenReturn(new ListeningHistory());

    // Act
    PlayResponse response = playerService.play(userId, request);

    // Assert
    assertNotNull(response);
    assertEquals("Test Song", response.getTitle());
    verify(artistServiceClient).incrementPlayCount(songId);
    verify(historyRepository).save(any(ListeningHistory.class));
}

@Test
void getAnalyticsOverview_ForNonOwner_ThrowsForbidden() {
    // Arrange
    Long artistId = 1L;
    Long requestingUserId = 2L;  // Different user

    // Act & Assert
    assertThrows(UnauthorizedException.class, () -> {
        analyticsService.getOverview(artistId, requestingUserId, "ARTIST");
    });
}
```

#### Manual Test Scenarios

| # | Scenario | Expected Result |
|---|----------|-----------------|
| 1 | Register as USER | Account created, JWT returned |
| 2 | Register as ARTIST | Account created, can create artist profile |
| 3 | Browse songs catalog | Paginated song list displayed |
| 4 | Search songs | Matching results returned |
| 5 | Play song | Play event recorded, count incremented |
| 6 | Create playlist | Playlist created, can add songs |
| 7 | Add to favorites | Song added to favorites list |
| 8 | Upload song (Artist) | Song uploaded, visible in catalog |
| 9 | View analytics (Artist) | Overview stats displayed |
| 10 | Refresh expired token | New access token issued |

---

### SLIDE 14: Logging (5 Points) (1 minute)

#### Logging Implementation

| Log Level | Usage | Example |
|-----------|-------|---------|
| INFO | Normal operations | User login, song played |
| DEBUG | Development details | Request/response payloads |
| WARN | Potential issues | Circuit breaker open, cache miss |
| ERROR | Failures | Service unavailable, validation error |

#### Caching Strategy (Analytics Service)

```
┌─────────────────────────────────────────────────────────────────┐
│                   CAFFEINE CACHE CONFIGURATION                   │
│                                                                  │
│   • Max Size: 1000 entries                                       │
│   • TTL: 5 minutes                                               │
│                                                                  │
│   Cached Operations:                                             │
│   • Artist overview statistics                                   │
│   • Song performance metrics                                     │
│   • Top songs by period                                          │
│   • Listening trends                                             │
│   • Top listeners                                                │
│   • Daily stats aggregation                                      │
│   • Completion rates                                             │
│   • Unique listener counts                                       │
└─────────────────────────────────────────────────────────────────┘
```

#### Circuit Breaker Configuration

```
┌─────────────────────────────────────────────────────────────────┐
│                RESILIENCE4J CIRCUIT BREAKER                      │
│                                                                  │
│   • Sliding Window Size: 10 calls                                │
│   • Failure Rate Threshold: 50%                                  │
│   • Wait Duration (Open): 5-30 seconds (varies by service)       │
│   • Half-Open Permitted Calls: 3                                 │
│   • Timeout Duration: 4-5 seconds                                │
└─────────────────────────────────────────────────────────────────┘
```

---

### SLIDE 15: Deliverables (5 Points) (1 minute)

#### Project Deliverables Checklist

| Deliverable | Location | Status |
|-------------|----------|--------|
| **Source Code (P1)** | GitHub: RevplayP2 | ✓ |
| **Source Code (P2)** | GitHub: RevplayP3 | ✓ |
| **README.md** | Root directory (both repos) | ✓ |
| **Service READMEs** | Each microservice folder | ✓ |
| **API Reference** | music-service/API_REFERENCE.md | ✓ |
| **Docker Compose** | docker-compose.yml | ✓ |
| **Architecture Diagram** | README.md (ASCII art) | ✓ |

#### Repository Structure

```
RevplayP2/                          p3-revplay/
├── backend/                        ├── README.md
│   ├── pom.xml                     ├── docker-compose.yml
│   └── src/main/java/              ├── pom.xml
└── frontend/                       ├── config-server/
    ├── package.json                ├── discovery-server/
    ├── angular.json                ├── api-gateway/
    └── src/app/                    ├── auth-service/
        └── modules/                │   └── README.md
            ├── auth/               ├── user-service/
            ├── music/browse/       │   └── README.md
            ├── user/               ├── artist-service/
            ├── artist/             │   └── README.md
            ├── player/             ├── music-service/
            └── history/            │   ├── README.md
                                    │   └── API_REFERENCE.md
                                    ├── player-service/
                                    │   ├── README.md
                                    │   └── SERVICE_SUMMARY.md
                                    └── analytics-service/
                                        └── README.md
```

---

### SLIDE 16: Deployment Architecture (2 minutes)

#### Phase 1: AWS Deployment

```
┌──────────────────────────────────────────────────────────────┐
│                        GITHUB                                 │
│  ┌──────────┐     ┌────────────────────────────────────┐     │
│  │  Push to │────►│         GitHub Actions              │     │
│  │   main   │     │  Build → Test → Deploy to AWS       │     │
│  └──────────┘     └─────────────────┬──────────────────┘     │
└─────────────────────────────────────┼────────────────────────┘
                                      │
                                      ▼
┌──────────────────────────────────────────────────────────────┐
│                         AWS CLOUD                             │
│  ┌────────────────────────────────────────────────────────┐  │
│  │                    VPC                                  │  │
│  │  ┌──────────────────┐      ┌────────────────────────┐  │  │
│  │  │    EC2 Instance  │      │    RDS (MySQL)         │  │  │
│  │  │  ┌────────────┐  │      │                        │  │  │
│  │  │  │Spring Boot │  │◄────►│  revplay_db            │  │  │
│  │  │  │   :8080    │  │ JDBC │  (Private Subnet)      │  │  │
│  │  │  └────────────┘  │      │                        │  │  │
│  │  │  ┌────────────┐  │      └────────────────────────┘  │  │
│  │  │  │   nginx    │  │                                   │  │
│  │  │  │  (Angular) │  │                                   │  │
│  │  │  │    :80     │  │                                   │  │
│  │  │  └────────────┘  │                                   │  │
│  │  └──────────────────┘                                   │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

#### Phase 2: Docker Compose Deployment

```
┌──────────────────────────────────────────────────────────────┐
│                   DOCKER COMPOSE                              │
│                                                               │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                 revplay-network (bridge)                 │ │
│  │                                                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │ │
│  │  │config-server│  │ discovery   │  │   api-gateway   │  │ │
│  │  │    :8888    │  │   :8761     │  │     :8080       │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘  │ │
│  │                                                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │ │
│  │  │auth-service │  │user-service │  │ artist-service  │  │ │
│  │  │    :8081    │  │    :8082    │  │     :8083       │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘  │ │
│  │                                                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │ │
│  │  │music-service│  │player-service│  │analytics-service│ │ │
│  │  │    :8084    │  │    :8085    │  │     :8086       │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘  │ │
│  │                                                          │ │
│  └─────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────┘
```

#### Docker Compose Commands

```bash
# Build all services
mvn clean package -DskipTests

# Start all services
docker-compose up --build

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

---

### SLIDE 17: Development Methodology (1 minute)

#### Git Workflow

```
main ─────────────────────────────────────────────►
  │                                    ▲
  └──► develop ────────────────────────┤
         │        ▲        ▲           │
         │        │        │           │
         └──► feature/auth-service─────┘
         └──► feature/user-service─────┘
         └──► feature/artist-service───┘
         └──► feature/music-service────┘
         └──► feature/player-service───┘
         └──► feature/analytics-service┘
```

#### Development Workflow

| Phase | Activity | Tools |
|-------|----------|-------|
| Planning | User stories, task breakdown | GitHub Issues |
| Development | Feature branches, commits | Git, IDE |
| Review | Pull requests, code review | GitHub PRs |
| Testing | Unit tests, manual testing | JUnit, Mockito |
| Deployment | CI/CD pipeline, Docker | GitHub Actions, AWS |

#### Local Environment Setup

```bash
# Prerequisites: Java 17, Maven, Node.js 18+

# Phase 1 Setup
cd RevplayP2

# Backend (Port 8080)
cd backend && mvn spring-boot:run

# Frontend (Port 4200)
cd frontend && npm install && npm start

# Phase 2 Setup
cd p3-revplay

# Build all
mvn clean package -DskipTests

# Start with Docker
docker-compose up --build

# Or start manually in order:
# 1. config-server → 2. discovery-server → 3. api-gateway → 4. business services
```

---

## INDIVIDUAL CONTRIBUTIONS SECTION (Each member: 1-2 minutes)

---

### SLIDE 18: Prithivirajan - Contributions

**Feature Ownership:** Music Discovery & Search
**Service:** music-service (Port 8084)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Catalog Controller | CatalogController.java |
| Backend | Search Service | CatalogService.java |
| Backend | Artist Service Client | ArtistServiceClient.java (Feign) |
| Backend | Caching Implementation | CacheConfig.java |
| Frontend | Browse Module | music/browse/ components |
| Integration | Circuit Breaker | Resilience4j configuration |

##### Code Highlight

```java
// Database-free Music Service - Aggregates from Artist Service
@Service
public class CatalogService {

    @Autowired
    private ArtistServiceClient artistServiceClient;

    @Cacheable(value = "songs", key = "'page:' + #page + ':size:' + #size")
    public Page<SongResponse> browseSongs(int page, int size) {
        return artistServiceClient.getPublicSongs(page, size, "createdAt,desc");
    }

    @Cacheable(value = "search", key = "#query + ':' + #page + ':' + #size")
    public Page<SongResponse> searchSongs(String query, int page, int size) {
        // Fetch all public songs and filter in-memory
        List<SongResponse> allSongs = artistServiceClient.getAllPublicSongs();

        List<SongResponse> filtered = allSongs.stream()
            .filter(song ->
                song.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                song.getArtistName().toLowerCase().contains(query.toLowerCase()) ||
                (song.getAlbumTitle() != null &&
                 song.getAlbumTitle().toLowerCase().contains(query.toLowerCase()))
            )
            .collect(Collectors.toList());

        return toPage(filtered, page, size);
    }

    @Cacheable(value = "trending", key = "#period")
    public List<SongResponse> getTrending(String period) {
        // Fetch songs sorted by play_count from artist service
        return artistServiceClient.getTrendingSongs(period);
    }
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [TECHNICAL_SKILL_GAINED]
- [PROBLEM_SOLVING_APPROACH]
- [COLLABORATION_INSIGHT]

---

### SLIDE 19: Tarun - Contributions

**Feature Ownership:** Analytics and Insights
**Service:** analytics-service (Port 8086)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Analytics Controller | AnalyticsController.java |
| Backend | Aggregation Service | AnalyticsService.java |
| Backend | Feign Clients | PlayerServiceClient, ArtistServiceClient, UserServiceClient |
| Backend | Caching with Caffeine | CacheConfig.java |
| Frontend | Analytics Dashboard | artist/analytics/ components |
| Security | Artist-only authorization | Role validation |

##### Code Highlight

```java
// Analytics Service - No Database, Pure Aggregation with Caching
@Service
public class AnalyticsService {

    @Cacheable(value = "overview", key = "#artistId + ':' + #period")
    public ArtistOverviewResponse getOverview(Long artistId, Long userId, String role) {
        // Authorization check
        if (!"ARTIST".equals(role) || !artistId.equals(userId)) {
            throw new UnauthorizedException("Artists can only view their own analytics");
        }

        // Fetch artist's songs
        List<SongResponse> songs = artistServiceClient.getArtistSongs(artistId);
        List<Long> songIds = songs.stream().map(SongResponse::getId).toList();

        // Fetch play history for all songs
        Map<Long, List<ListeningHistory>> historyBySong = songIds.stream()
            .collect(Collectors.toMap(
                id -> id,
                id -> playerServiceClient.getSongHistory(id)
            ));

        // Calculate metrics
        long totalPlays = historyBySong.values().stream()
            .mapToLong(List::size).sum();

        long uniqueListeners = historyBySong.values().stream()
            .flatMap(List::stream)
            .map(ListeningHistory::getUserId)
            .distinct()
            .count();

        double totalMinutes = historyBySong.values().stream()
            .flatMap(List::stream)
            .mapToInt(ListeningHistory::getDuration)
            .sum() / 60.0;

        SongResponse topSong = songs.stream()
            .max(Comparator.comparing(s ->
                historyBySong.get(s.getId()).size()))
            .orElse(null);

        return ArtistOverviewResponse.builder()
            .totalPlays(totalPlays)
            .uniqueListeners(uniqueListeners)
            .totalListeningTimeMinutes(totalMinutes)
            .topSongId(topSong != null ? topSong.getId() : null)
            .topSongTitle(topSong != null ? topSong.getTitle() : null)
            .period(period)
            .build();
    }
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 20: Satish - Contributions

**Feature Ownership:** User Profile, Favourites & Playlist
**Service:** user-service (Port 8082)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | User Controller | UserController.java |
| Backend | Playlist Controller | PlaylistController.java |
| Backend | Favorite Controller | FavoriteController.java |
| Backend | Artist Service Client | ArtistServiceClient.java (Feign) |
| Frontend | User Module | user/ (profile, favorites, playlists, dashboard) |
| Database | User Profile, Playlist, Favorite entities | JPA entities |

##### Code Highlight

```java
// Playlist Service with Song Enrichment
@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private ArtistServiceClient artistServiceClient;

    public PlaylistResponse getPlaylistWithSongs(Long playlistId, Long userId) {
        Playlist playlist = playlistRepository.findById(playlistId)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        if (!playlist.getUserId().equals(userId)) {
            throw new UnauthorizedException("Not your playlist");
        }

        // Get song IDs from playlist
        List<Long> songIds = playlist.getSongIds();

        // Fetch song details from Artist Service
        List<SongResponse> songs = artistServiceClient.getSongsByIds(songIds);

        return PlaylistResponse.builder()
            .id(playlist.getId())
            .name(playlist.getName())
            .description(playlist.getDescription())
            .songs(songs)
            .songCount(songs.size())
            .createdAt(playlist.getCreatedAt())
            .build();
    }

    @Transactional
    public void addSongToPlaylist(Long playlistId, Long songId, Long userId) {
        Playlist playlist = playlistRepository.findById(playlistId)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        if (!playlist.getUserId().equals(userId)) {
            throw new UnauthorizedException("Not your playlist");
        }

        // Verify song exists
        artistServiceClient.getSongById(songId);

        playlist.addSong(songId);
        playlistRepository.save(playlist);
    }
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 21: Satya - Contributions

**Feature Ownership:** Authentication and Access Control
**Service:** auth-service (Port 8081)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Auth Controller | AuthController.java |
| Backend | JWT Service | JwtService.java |
| Backend | Refresh Token Service | RefreshTokenService.java |
| Backend | Security Configuration | SecurityConfig.java |
| Frontend | Auth Module | auth/ (login, register) |
| Database | User, RefreshToken entities | JPA entities |

##### Code Highlight

```java
// JWT Authentication with Refresh Token
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already registered");
        }

        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .role(Role.valueOf(request.getRole()))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        user = userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getToken())
            .tokenType("Bearer")
            .userId(user.getId())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        RefreshToken refreshToken = refreshTokenService
            .findByToken(request.getRefreshToken())
            .orElseThrow(() -> new AuthException("Invalid refresh token"));

        refreshTokenService.verifyExpiration(refreshToken);

        User user = userRepository.findById(refreshToken.getUserId())
            .orElseThrow(() -> new AuthException("User not found"));

        String newAccessToken = jwtService.generateToken(user);

        return TokenRefreshResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(request.getRefreshToken())
            .tokenType("Bearer")
            .build();
    }
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 22: Srinivas - Contributions

**Feature Ownership:** Artist Profile, Music & Album Management
**Service:** artist-service (Port 8083)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Artist Controller | ArtistController.java |
| Backend | Song Controller | SongController.java |
| Backend | Album Controller | AlbumController.java |
| Backend | Internal Controller | InternalController.java (for other services) |
| Frontend | Artist Module | artist/ components |
| Database | Artist, Song, Album entities | JPA entities |

##### Code Highlight

```java
// Artist Service - Song Management with Visibility Control
@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Transactional
    public SongResponse uploadSong(SongUploadRequest request, Long userId) {
        Artist artist = artistRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        // Validate album belongs to artist if provided
        if (request.getAlbumId() != null) {
            Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));

            if (!album.getArtistId().equals(artist.getId())) {
                throw new UnauthorizedException("Album does not belong to you");
            }
        }

        Song song = Song.builder()
            .artistId(artist.getId())
            .albumId(request.getAlbumId())
            .title(request.getTitle())
            .duration(request.getDuration())
            .genre(request.getGenre())
            .fileUrl(request.getFileUrl())
            .coverImageUrl(request.getCoverImageUrl())
            .visibility(Visibility.valueOf(request.getVisibility()))
            .playCount(0L)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        song = songRepository.save(song);

        return toResponse(song, artist);
    }

    // Internal API for other services
    @Transactional
    public void incrementPlayCount(Long songId) {
        Song song = songRepository.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        song.setPlayCount(song.getPlayCount() + 1);
        songRepository.save(song);
    }

    public List<SongResponse> getPublicSongs() {
        return songRepository.findByVisibility(Visibility.PUBLIC).stream()
            .map(this::toResponseWithArtist)
            .collect(Collectors.toList());
    }
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 23: Deeja - Contributions

**Feature Ownership:** Play Music & Recent History
**Service:** player-service (Port 8085)

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Player Controller | PlayerController.java |
| Backend | History Controller | HistoryController.java |
| Backend | Internal Controller | InternalController.java (for Analytics) |
| Backend | Artist Service Client | ArtistServiceClient.java (Feign) |
| Frontend | Player Module, History Module | player/, history/ components |
| Database | ListeningHistory entity | JPA entity |

##### Code Highlight

```java
// Player Service - Play Events with Duration Tracking
@Service
public class PlayerService {

    @Autowired
    private ListeningHistoryRepository historyRepository;

    @Autowired
    private ArtistServiceClient artistServiceClient;

    @Transactional
    public PlayResponse play(Long userId, PlayRequest request) {
        // Fetch song details from Artist Service
        SongResponse song = artistServiceClient.getSongById(request.getSongId());

        // Record play event
        ListeningHistory history = ListeningHistory.builder()
            .userId(userId)
            .songId(request.getSongId())
            .playedAt(LocalDateTime.now())
            .duration(0)  // Updated later when playback ends
            .build();

        history = historyRepository.save(history);

        // Increment play count in Artist Service
        try {
            artistServiceClient.incrementPlayCount(request.getSongId());
        } catch (Exception e) {
            // Log but don't fail - play count is not critical
            log.warn("Failed to increment play count for song {}", request.getSongId());
        }

        return PlayResponse.builder()
            .historyId(history.getId())
            .songId(song.getId())
            .title(song.getTitle())
            .artistName(song.getArtistName())
            .albumTitle(song.getAlbumTitle())
            .fileUrl(song.getFileUrl())
            .coverImageUrl(song.getCoverImageUrl())
            .duration(song.getDuration())
            .build();
    }

    @Transactional
    public void updateDuration(Long historyId, Long userId, int duration) {
        ListeningHistory history = historyRepository.findById(historyId)
            .orElseThrow(() -> new ResourceNotFoundException("History entry not found"));

        if (!history.getUserId().equals(userId)) {
            throw new UnauthorizedException("Not your history entry");
        }

        history.setDuration(duration);
        historyRepository.save(history);
    }

    public UserHistoryStatsResponse getStats(Long userId) {
        List<ListeningHistory> history = historyRepository.findByUserId(userId);

        long totalPlays = history.size();
        long totalSeconds = history.stream()
            .mapToInt(ListeningHistory::getDuration)
            .sum();
        long uniqueSongs = history.stream()
            .map(ListeningHistory::getSongId)
            .distinct()
            .count();

        return UserHistoryStatsResponse.builder()
            .totalSongsPlayed(totalPlays)
            .totalListeningTimeSeconds(totalSeconds)
            .uniqueSongsPlayed(uniqueSongs)
            .build();
    }
}
```

##### Challenges I Faced & How I Solved Them

| Challenge | Impact | My Solution |
|-----------|--------|-------------|
| [CHALLENGE_1] | [IMPACT_1] | [SOLUTION_1] |
| [CHALLENGE_2] | [IMPACT_2] | [SOLUTION_2] |

##### Key Learnings

- [LEARNING_1]
- [LEARNING_2]

---

### SLIDE 24: Team Challenges & Collective Learnings (1 minute)

#### Team-Level Challenges

| Challenge | Impact | How We Solved It Together |
|-----------|--------|---------------------------|
| Coordinating 6 microservices | Integration complexity | Clear API contracts, internal endpoints |
| Database-free services (Music, Analytics) | Data aggregation challenges | Feign clients, caching strategies |
| Circuit breaker configuration | Tuning for reliability | Shared Resilience4j config, testing |
| JWT header propagation | Auth across services | API Gateway header injection |
| [TEAM_CHALLENGE_1] | [IMPACT] | [SOLUTION] |

#### Collective Learnings

| Area | What We Learned as a Team |
|------|---------------------------|
| **Technical** | Microservices patterns, Feign clients, Circuit breakers, Caching |
| **Architecture** | Service discovery, API Gateway, Aggregation services |
| **Security** | JWT with refresh tokens, Role-based access |
| **Collaboration** | Git workflow, API contract design, Service ownership |
| **DevOps** | Docker Compose, Health checks, Service dependencies |

---

### SLIDE 25: Q&A (5-10 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                       QUESTIONS?                            │
│                                                             │
│                                                             │
│     GitHub Phase 1:                                         │
│     https://github.com/Satishvarma17/RevplayP2              │
│                                                             │
│     GitHub Phase 2:                                         │
│     https://github.com/taruneshwar28/RevplayP3              │
│                                                             │
│     Live Demo: [AWS_URL]                                    │
│                                                             │
│     Individual GitHub Contributions:                        │
│     • Prithivirajan: [X commits, Y PRs]                     │
│     • Tarun: [X commits, Y PRs]                             │
│     • Satish: [X commits, Y PRs]                            │
│     • Satya: [X commits, Y PRs]                             │
│     • Srinivas: [X commits, Y PRs]                          │
│     • Deeja: [X commits, Y PRs]                             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Q&A Tips:**
- Any team member can answer questions about their feature area
- Be ready to explain database-free services (Music, Analytics)
- Demonstrate circuit breaker behavior
- Show Eureka dashboard for service discovery

---

## NOTES FOR PRESENTERS

### Information Not Available (To Be Filled)

The following information could not be determined from the provided repositories and should be filled in by the team:

1. **Batch ID and Presentation Date** - Slide 1
2. **Individual Challenges & Solutions** - Slides 18-23 (each team member should fill their specific challenges)
3. **Individual Key Learnings** - Slides 18-23
4. **Exact Git Commit Counts** - Slide 25
5. **AWS Deployment URL** - Slide 25
6. **Frontend Test Coverage Details** - Slide 13
7. **Additional Team Challenges** - Slide 24

### Demo Preparation Checklist

- [ ] Phase 1: Backend running on port 8080
- [ ] Phase 1: Frontend running on port 4200
- [ ] Phase 2: Docker Compose services running
- [ ] Eureka dashboard accessible at :8761
- [ ] H2 Console accessible for each service
- [ ] Test users created (USER and ARTIST roles)
- [ ] Sample songs uploaded
- [ ] AWS deployment active (if applicable)
- [ ] Backup screenshots/video prepared

### Phase 2 Microservices Ports Reference

| Service | Port | Owner | Database |
|---------|------|-------|----------|
| Config Server | 8888 | Shared | None |
| Discovery Server | 8761 | Shared | None |
| API Gateway | 8080 | Shared | None |
| Auth Service | 8081 | Satya | H2 (authdb) |
| User Service | 8082 | Satish | H2 (userdb) |
| Artist Service | 8083 | Srinivas | H2 (artistdb) |
| Music Service | 8084 | Prithivirajan | None (aggregation) |
| Player Service | 8085 | Deeja | H2 (playerdb) |
| Analytics Service | 8086 | Tarun | None (aggregation) |

### Special Notes

- **Music Service & Analytics Service** have NO database - they aggregate data from other services
- All services use **manual Builder pattern** (no Lombok)
- **Caffeine caching** is used for expensive aggregations
- **Circuit breakers** protect all Feign client calls

### Presentation Tips

1. Each member should practice their 1-2 minute contribution section
2. Be ready to explain aggregation services without databases
3. Show Eureka dashboard for service discovery demonstration
4. Demonstrate circuit breaker with WireMock or by stopping a service
5. Know your commit count and key PRs
