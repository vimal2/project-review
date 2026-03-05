# RevPlay P3 - Developer Instructions

This document provides step-by-step commit instructions for each developer to contribute to the microservices project.

## Project Setup

Before starting, ensure the shared infrastructure is committed first (this can be done by any team member):

```bash
git init
git add pom.xml .gitignore README.md docker-compose.yml
git add config-server/ discovery-server/ api-gateway/
git commit -m "feat: add shared infrastructure (config-server, discovery-server, api-gateway)"
```

---

## Developer 1: Satya - Auth Service (Port 8081)

### Commit Order

**Commit 1: Base structure**
```bash
git add auth-service/pom.xml
git add auth-service/src/main/resources/application.yml
git add auth-service/src/main/java/com/revplay/auth/AuthServiceApplication.java
git commit -m "feat(auth): add auth-service module with base configuration"
```

**Commit 2: Entity classes**
```bash
git add auth-service/src/main/java/com/revplay/auth/entity/
git commit -m "feat(auth): add User, Role, and RefreshToken entities"
```

**Commit 3: DTOs**
```bash
git add auth-service/src/main/java/com/revplay/auth/dto/
git commit -m "feat(auth): add authentication DTOs (LoginRequest, RegisterRequest, AuthResponse)"
```

**Commit 4: Repository layer**
```bash
git add auth-service/src/main/java/com/revplay/auth/repository/
git commit -m "feat(auth): add UserRepository and RefreshTokenRepository"
```

**Commit 5: Security configuration**
```bash
git add auth-service/src/main/java/com/revplay/auth/config/
git commit -m "feat(auth): add security configuration and CORS setup"
```

**Commit 6: Service layer**
```bash
git add auth-service/src/main/java/com/revplay/auth/service/
git commit -m "feat(auth): add AuthService, JwtService, and RefreshTokenService"
```

**Commit 7: Exception handling**
```bash
git add auth-service/src/main/java/com/revplay/auth/exception/
git commit -m "feat(auth): add exception handling with GlobalExceptionHandler"
```

**Commit 8: Controller and Docker**
```bash
git add auth-service/src/main/java/com/revplay/auth/controller/
git add auth-service/Dockerfile
git commit -m "feat(auth): add AuthController with login/register/refresh endpoints"
```

---

## Developer 2: Satish - User Service (Port 8082)

### Commit Order

**Commit 1: Base structure**
```bash
git add user-service/pom.xml
git add user-service/src/main/resources/application.yml
git add user-service/src/main/java/com/revplay/user/UserServiceApplication.java
git commit -m "feat(user): add user-service module with base configuration"
```

**Commit 2: Entity classes**
```bash
git add user-service/src/main/java/com/revplay/user/entity/
git commit -m "feat(user): add UserProfile, Playlist, PlaylistSong, and Favorite entities"
```

**Commit 3: DTOs**
```bash
git add user-service/src/main/java/com/revplay/user/dto/
git commit -m "feat(user): add UserProfile, Playlist, and Favorite DTOs"
```

**Commit 4: Feign clients**
```bash
git add user-service/src/main/java/com/revplay/user/client/
git commit -m "feat(user): add Feign client for artist-service"
```

**Commit 5: Repository layer**
```bash
git add user-service/src/main/java/com/revplay/user/repository/
git commit -m "feat(user): add repositories for UserProfile, Playlist, and Favorite"
```

**Commit 6: Configuration**
```bash
git add user-service/src/main/java/com/revplay/user/config/
git commit -m "feat(user): add security and Feign configuration"
```

**Commit 7: Exception handling**
```bash
git add user-service/src/main/java/com/revplay/user/exception/
git commit -m "feat(user): add exception handling"
```

**Commit 8: Service layer**
```bash
git add user-service/src/main/java/com/revplay/user/service/
git commit -m "feat(user): add UserProfileService, PlaylistService, and FavoriteService"
```

**Commit 9: Controllers and Docker**
```bash
git add user-service/src/main/java/com/revplay/user/controller/
git add user-service/Dockerfile
git commit -m "feat(user): add controllers for profile, playlist, and favorites"
```

---

## Developer 3: Srinivas - Artist Service (Port 8083)

### Commit Order

**Commit 1: Base structure**
```bash
git add artist-service/pom.xml
git add artist-service/src/main/resources/application.yml
git add artist-service/src/main/java/com/revplay/artist/ArtistServiceApplication.java
git commit -m "feat(artist): add artist-service module with base configuration"
```

**Commit 2: Entity classes**
```bash
git add artist-service/src/main/java/com/revplay/artist/entity/
git commit -m "feat(artist): add Artist, Song, Album, and Visibility entities"
```

**Commit 3: DTOs**
```bash
git add artist-service/src/main/java/com/revplay/artist/dto/
git commit -m "feat(artist): add Artist, Song, and Album DTOs"
```

**Commit 4: Repository layer**
```bash
git add artist-service/src/main/java/com/revplay/artist/repository/
git commit -m "feat(artist): add ArtistRepository, SongRepository, and AlbumRepository"
```

**Commit 5: Configuration**
```bash
git add artist-service/src/main/java/com/revplay/artist/config/
git commit -m "feat(artist): add security configuration"
```

**Commit 6: Exception handling**
```bash
git add artist-service/src/main/java/com/revplay/artist/exception/
git commit -m "feat(artist): add exception handling with UnauthorizedException"
```

**Commit 7: Service layer**
```bash
git add artist-service/src/main/java/com/revplay/artist/service/
git commit -m "feat(artist): add ArtistService, SongService, and AlbumService"
```

**Commit 8: Controllers and Docker**
```bash
git add artist-service/src/main/java/com/revplay/artist/controller/
git add artist-service/Dockerfile
git commit -m "feat(artist): add controllers for artist profile, songs, albums, and internal APIs"
```

---

## Developer 4: Prithivirajan - Music Service (Port 8084)

### Commit Order

**Commit 1: Base structure**
```bash
git add music-service/pom.xml
git add music-service/src/main/resources/application.yml
git add music-service/src/main/java/com/revplay/music/MusicServiceApplication.java
git commit -m "feat(music): add music-service module with base configuration"
```

**Commit 2: DTOs**
```bash
git add music-service/src/main/java/com/revplay/music/dto/
git commit -m "feat(music): add SongCatalog, Search, Genre, and Trending DTOs"
```

**Commit 3: Feign clients**
```bash
git add music-service/src/main/java/com/revplay/music/client/
git commit -m "feat(music): add Feign client for artist-service"
```

**Commit 4: Configuration**
```bash
git add music-service/src/main/java/com/revplay/music/config/
git commit -m "feat(music): add Feign and cache configuration"
```

**Commit 5: Exception handling**
```bash
git add music-service/src/main/java/com/revplay/music/exception/
git commit -m "feat(music): add exception handling"
```

**Commit 6: Service layer**
```bash
git add music-service/src/main/java/com/revplay/music/service/
git commit -m "feat(music): add CatalogService, SearchService, and TrendingService"
```

**Commit 7: Controllers and Docker**
```bash
git add music-service/src/main/java/com/revplay/music/controller/
git add music-service/Dockerfile
git commit -m "feat(music): add CatalogController, SearchController, and TrendingController"
```

---

## Developer 5: Deeja - Player Service (Port 8085)

### Commit Order

**Commit 1: Base structure**
```bash
git add player-service/pom.xml
git add player-service/src/main/resources/application.yml
git add player-service/src/main/java/com/revplay/player/PlayerServiceApplication.java
git commit -m "feat(player): add player-service module with base configuration"
```

**Commit 2: Entity classes**
```bash
git add player-service/src/main/java/com/revplay/player/entity/
git commit -m "feat(player): add ListeningHistory entity"
```

**Commit 3: DTOs**
```bash
git add player-service/src/main/java/com/revplay/player/dto/
git commit -m "feat(player): add Play, ListeningHistory, and NowPlaying DTOs"
```

**Commit 4: Feign clients**
```bash
git add player-service/src/main/java/com/revplay/player/client/
git commit -m "feat(player): add Feign client for artist-service"
```

**Commit 5: Repository layer**
```bash
git add player-service/src/main/java/com/revplay/player/repository/
git commit -m "feat(player): add ListeningHistoryRepository with custom queries"
```

**Commit 6: Configuration**
```bash
git add player-service/src/main/java/com/revplay/player/config/
git commit -m "feat(player): add security and Feign configuration"
```

**Commit 7: Exception handling**
```bash
git add player-service/src/main/java/com/revplay/player/exception/
git commit -m "feat(player): add exception handling"
```

**Commit 8: Service layer**
```bash
git add player-service/src/main/java/com/revplay/player/service/
git commit -m "feat(player): add PlayerService and ListeningHistoryService"
```

**Commit 9: Controllers and Docker**
```bash
git add player-service/src/main/java/com/revplay/player/controller/
git add player-service/Dockerfile
git commit -m "feat(player): add PlayerController, ListeningHistoryController, and InternalController"
```

---

## Developer 6: Tarun - Analytics Service (Port 8086)

### Commit Order

**Commit 1: Base structure**
```bash
git add analytics-service/pom.xml
git add analytics-service/src/main/resources/application.yml
git add analytics-service/src/main/java/com/revplay/analytics/AnalyticsServiceApplication.java
git commit -m "feat(analytics): add analytics-service module with base configuration"
```

**Commit 2: DTOs**
```bash
git add analytics-service/src/main/java/com/revplay/analytics/dto/
git commit -m "feat(analytics): add analytics DTOs (Overview, SongPerformance, Trends, TopListeners)"
```

**Commit 3: Feign clients**
```bash
git add analytics-service/src/main/java/com/revplay/analytics/client/
git commit -m "feat(analytics): add Feign clients for player, artist, and user services"
```

**Commit 4: Configuration**
```bash
git add analytics-service/src/main/java/com/revplay/analytics/config/
git commit -m "feat(analytics): add security, Feign, and cache configuration"
```

**Commit 5: Exception handling**
```bash
git add analytics-service/src/main/java/com/revplay/analytics/exception/
git commit -m "feat(analytics): add exception handling"
```

**Commit 6: Service layer**
```bash
git add analytics-service/src/main/java/com/revplay/analytics/service/
git commit -m "feat(analytics): add AnalyticsService and AggregationService"
```

**Commit 7: Controller and Docker**
```bash
git add analytics-service/src/main/java/com/revplay/analytics/controller/
git add analytics-service/Dockerfile
git commit -m "feat(analytics): add AnalyticsController with artist analytics endpoints"
```

---

## Commit Message Guidelines

Follow conventional commits format:
- `feat(scope): description` - New feature
- `fix(scope): description` - Bug fix
- `docs(scope): description` - Documentation
- `refactor(scope): description` - Code refactoring
- `test(scope): description` - Adding tests

Scope should be the service name: `auth`, `user`, `artist`, `music`, `player`, `analytics`

---

## Service Dependencies

When committing, be aware of service dependencies:

| Service | Depends On |
|---------|-----------|
| auth-service | None (independent) |
| user-service | artist-service (Feign client) |
| artist-service | None (independent) |
| music-service | artist-service (Feign client) |
| player-service | artist-service (Feign client) |
| analytics-service | player-service, artist-service, user-service (Feign clients) |

**Recommended commit order across team:**
1. Satya (auth) - Can start immediately
2. Srinivas (artist) - Can start immediately
3. Satish (user) - After artist-service structure is committed
4. Prithivirajan (music) - After artist-service structure is committed
5. Deeja (player) - After artist-service structure is committed
6. Tarun (analytics) - After player-service and artist-service are committed

---

## Testing Your Service

```bash
# Build the service
cd <service-directory>
mvn clean package -DskipTests

# Run the service
mvn spring-boot:run

# Test endpoints (example for auth-service)
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password123","firstName":"Test","lastName":"User","role":"USER"}'
```

---

## Starting the Full Stack

```bash
# 1. Start Discovery Server first
cd discovery-server && mvn spring-boot:run

# 2. Start API Gateway
cd api-gateway && mvn spring-boot:run

# 3. Start business services (in any order)
cd auth-service && mvn spring-boot:run
cd artist-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd music-service && mvn spring-boot:run
cd player-service && mvn spring-boot:run
cd analytics-service && mvn spring-boot:run
```

Check Eureka Dashboard: http://localhost:8761

---

## Verification Checklist

Before pushing, verify:
- [ ] Service starts without errors
- [ ] Registers with Eureka (check http://localhost:8761)
- [ ] API endpoints respond correctly
- [ ] No hardcoded values (use application.yml)
- [ ] Proper error handling in place
- [ ] All validation annotations working

---

## Port Quick Reference

| Service | Port |
|---------|------|
| Config Server | 8888 |
| Discovery Server (Eureka) | 8761 |
| API Gateway | 8080 |
| auth-service (Satya) | 8081 |
| user-service (Satish) | 8082 |
| artist-service (Srinivas) | 8083 |
| music-service (Prithivirajan) | 8084 |
| player-service (Deeja) | 8085 |
| analytics-service (Tarun) | 8086 |
