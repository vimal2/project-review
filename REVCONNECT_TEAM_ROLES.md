# RevConnect - Team Roles & Responsibilities

## Project Overview

**Project Name:** RevConnect (Social Media Platform)
**Team Size:** 6 Developers
**P2 (Monolithic):** Spring Boot 3.x, MySQL, JWT Authentication, Angular 17
**P3 (Microservices):** Spring Cloud, Eureka, API Gateway, Feign Clients

---

## Team Members & Focus Areas

| Developer | P2 Focus Area | P3 Microservice Ownership |
|-----------|---------------|---------------------------|
| **Srinivas Rao** | Authentication & Security | Auth Service |
| **Gopala Krishna** | User Profile Management | User Service |
| **Vasanth** | Posts, Feed & Content | Post Service |
| **Sandeep** | Network (Connections & Follows) | Network Service |
| **Siva Sai** | Likes & Comments | Interaction Service |
| **Yakkanti** | Notifications | Notification Service |

---

## P2 (Monolithic) - Roles & Responsibilities

### Srinivas Rao - Authentication & Security Lead

**Focus Areas:** User Registration, Login, JWT Security, Password Management

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | AuthController | `auth/controller/AuthController.java` |
| Service | AuthService | `auth/service/AuthService.java` |
| DTO | AuthDtos | `auth/dto/AuthDtos.java` |
| Security | JwtTokenProvider | `security/JwtTokenProvider.java` |
| Security | SecurityConfig | `security/SecurityConfig.java` |
| Security | JwtAuthenticationFilter | `security/JwtAuthenticationFilter.java` |
| Security | UserDetailsServiceImpl | `security/UserDetailsServiceImpl.java` |

**API Endpoints Developed:**
- `POST /auth/register` - User registration with validation
- `POST /auth/login` - Login with JWT token generation
- `POST /auth/forgot-password` - Password reset functionality

**Key Contributions:**
- Implemented JWT token generation (HS512 algorithm)
- Configured Spring Security filter chain
- BCrypt password hashing (strength 12)
- Set up CORS for Angular frontend (localhost:4200)
- Input validation with Bean Validation
- Support for login with username OR email

---

### Gopala Krishna - User Profile Lead

**Focus Areas:** User Profile CRUD, Search, Profile Settings

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | UserController | `user/controller/UserController.java` |
| Service | UserService | `user/service/UserService.java` |
| Entity | User | `user/model/User.java` |
| Repository | UserRepository | `user/repository/UserRepository.java` |
| DTO | UserDtos | `user/dto/UserDtos.java` |
| Enum | UserRole | `user/model/UserRole.java` |
| Enum | PrivacyType | `user/model/PrivacyType.java` |

**API Endpoints Developed:**
- `GET /users/me` - Get authenticated user profile
- `GET /users/{id}` - Get user profile by ID
- `PUT /users/{id}` - Update profile (bio, location, website, etc.)
- `GET /users/search?q=` - Search users by name/username

**Key Contributions:**
- User entity with PERSONAL/CREATOR/BUSINESS roles
- Profile fields: firstName, lastName, bio, profilePicture, location
- Business profile support: businessName, category, contactEmail, businessHours
- Privacy settings (PUBLIC/PRIVATE)
- Full-text search functionality
- Profile response with relationship status (isFollowing, isConnected)

---

### Vasanth - Posts & Feed Lead

**Focus Areas:** Post CRUD, Feed Algorithm, Trending, Analytics, Hashtags

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | PostController | `post/controller/PostController.java` |
| Controller | FeedController | `feed/controller/FeedController.java` |
| Service | PostService | `post/service/PostService.java` |
| Service | FeedService | `feed/service/FeedService.java` |
| Entity | Post | `post/model/Post.java` |
| Repository | PostRepository | `post/repository/PostRepository.java` |
| DTO | PostDtos | `post/dto/PostDtos.java` |
| Enum | PostType | `post/model/PostType.java` |

**API Endpoints Developed:**
- `POST /posts` - Create new post (text, image, promotional, repost)
- `GET /posts/{id}` - Get single post (increments view count)
- `PUT /posts/{id}` - Update post content/hashtags/pinning
- `DELETE /posts/{id}` - Delete post with authorization
- `GET /posts/user/{userId}` - Get user's posts (paginated)
- `GET /posts/trending` - Trending posts (last 24h by views)
- `GET /posts/search?hashtag=` - Search by hashtag
- `GET /posts/{id}/analytics` - Post analytics (engagement rate)
- `GET /feed?page=0&size=10` - Personalized feed

**Key Contributions:**
- Post types: TEXT, IMAGE, PROMOTIONAL, REPOST, ANNOUNCEMENT
- Repost functionality with originalPost reference
- Call-to-action for promotional posts
- Post scheduling (scheduledAt, published flags)
- View count tracking
- Hashtag support and search
- Pinned posts functionality
- Feed algorithm: own + connections + following posts
- Post analytics: views, likes, comments, engagement rate

---

### Sandeep - Network Management Lead

**Focus Areas:** Connections (Friend Requests), Following System, Suggestions

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | NetworkController | `network/controller/NetworkController.java` |
| Service | NetworkService | `network/service/NetworkService.java` |
| Entity | Connection | `network/model/Connection.java` |
| Entity | Follow | `network/model/Follow.java` |
| Repository | ConnectionRepository | `network/repository/ConnectionRepository.java` |
| Repository | FollowRepository | `network/repository/FollowRepository.java` |
| Enum | ConnectionStatus | `network/model/ConnectionStatus.java` |

**API Endpoints Developed:**

**Connections:**
- `POST /network/connect/{userId}` - Send connection request
- `PUT /network/connections/{id}/accept` - Accept connection
- `PUT /network/connections/{id}/reject` - Reject connection
- `DELETE /network/connect/{userId}` - Remove connection
- `GET /network/connections` - List accepted connections
- `GET /network/requests/received` - Pending requests received
- `GET /network/requests/sent` - Pending requests sent
- `GET /network/suggestions?limit=10` - Connection suggestions

**Follows:**
- `POST /network/follow/{userId}` - Follow user
- `DELETE /network/follow/{userId}` - Unfollow user
- `GET /network/followers/{userId}` - Get followers
- `GET /network/following/{userId}` - Get following

**Key Contributions:**
- Bidirectional connection system (requester/addressee)
- Connection status workflow: PENDING → ACCEPTED/REJECTED
- Unidirectional follow system (follower/following)
- Connection suggestions algorithm (excludes connected/pending)
- Follower/following counts for profiles
- Connected user IDs for feed algorithm

---

### Siva Sai - Likes & Comments Lead

**Focus Areas:** Like System, Comment System, Engagement

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | LikeController | `like/controller/LikeController.java` |
| Controller | CommentController | `comment/controller/CommentController.java` |
| Service | LikeService | `like/service/LikeService.java` |
| Service | CommentService | `comment/service/CommentService.java` |
| Entity | Like | `like/model/Like.java` |
| Entity | Comment | `comment/model/Comment.java` |
| Repository | LikeRepository | `like/repository/LikeRepository.java` |
| Repository | CommentRepository | `comment/repository/CommentRepository.java` |

**API Endpoints Developed:**

**Likes:**
- `POST /posts/{postId}/like` - Like a post
- `DELETE /posts/{postId}/like` - Unlike a post

**Comments:**
- `POST /posts/{postId}/comments` - Add comment
- `GET /posts/{postId}/comments?page=0&size=20` - Get comments (paginated)
- `DELETE /comments/{id}` - Delete comment with authorization

**Key Contributions:**
- Like entity with unique constraint (post_id, user_id)
- Like/unlike toggle functionality
- Like count tracking
- Comment system with pagination (oldest first)
- Comment authorization (only author can delete)
- Notification triggers for likes and comments
- Like/comment counts for post responses

---

### Yakkanti - Notifications Lead

**Focus Areas:** Notification System, Real-time Updates, Read Status

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | NotificationController | `notification/controller/NotificationController.java` |
| Service | NotificationService | `notification/service/NotificationService.java` |
| Entity | Notification | `notification/model/Notification.java` |
| Repository | NotificationRepository | `notification/repository/NotificationRepository.java` |
| Enum | NotificationType | `notification/model/NotificationType.java` |

**API Endpoints Developed:**
- `GET /notifications?page=0&size=20` - Get paginated notifications
- `GET /notifications/unread-count` - Get unread count
- `PUT /notifications/{id}/read` - Mark as read
- `PUT /notifications/read-all` - Mark all as read

**Key Contributions:**
- Notification types: CONNECTION_REQUEST, CONNECTION_ACCEPTED, NEW_FOLLOWER, POST_LIKED, POST_COMMENTED, POST_SHARED, POST_MENTIONED
- Notification triggers from other services
- Unread count badge support
- Mark single/all as read functionality
- Self-action exclusion (no notification for own actions)

---

## P3 (Microservices) - Roles & Responsibilities

### Architecture Overview

```
                                    +-------------------+
                                    |   Config Server   |
                                    |    (Port 8888)    |
                                    +--------+----------+
                                             |
                    +------------------------+------------------------+
                    |                        |                        |
                    v                        v                        v
         +------------------+     +------------------+     +------------------+
         | Discovery Server |     |   API Gateway    |     |     Zipkin       |
         |  (Eureka 8761)   |     |   (Port 8080)    |     |   (Port 9411)    |
         +--------+---------+     +--------+---------+     +------------------+
                  |                        |
     +------------+------------+-----------+------------+-------------+
     |            |            |           |            |             |
     v            v            v           v            v             v
+----------+ +----------+ +----------+ +----------+ +-----------+ +------------+
|   Auth   | |   User   | |   Post   | | Network  | |Interaction| |Notification|
| Service  | | Service  | | Service  | | Service  | |  Service  | |  Service   |
| (8081)   | | (8082)   | | (8083)   | | (8084)   | |  (8085)   | |  (8086)    |
| Srinivas | | Gopala   | | Vasanth  | | Sandeep  | |  Siva Sai | |  Yakkanti  |
+----------+ +----------+ +----------+ +----------+ +-----------+ +------------+
```

### Shared Infrastructure (All 6 Developers)

Each developer contributes equally to the shared infrastructure components:

| Component | Module | Contributions Per Developer |
|-----------|--------|----------------------------|
| **Config Server** | `config-server` | Each adds their service config file |
| **Discovery Server** | `discovery-server` | Shared setup (pair programming) |
| **API Gateway** | `api-gateway` | Each adds routes for their service |
| **Docker Compose** | `docker-compose.yml` | Each adds their service definition |
| **Parent POM** | `pom.xml` | Each adds their module |

---

### Srinivas Rao - Auth Service (Port 8081)

**Service Module:** `auth-service`

**Responsibilities:**
1. Create `auth-service` Maven module
2. Migrate authentication components from P2
3. Implement user registration and login
4. Provide JWT token generation/validation
5. Expose user validation endpoint for other services

**Module Structure:**
```
auth-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/revconnect/auth/
    ├── AuthServiceApplication.java
    ├── controller/
    │   └── AuthController.java
    ├── service/
    │   └── AuthService.java
    ├── security/
    │   ├── SecurityConfig.java
    │   ├── JwtTokenProvider.java
    │   └── JwtAuthenticationFilter.java
    ├── dto/
    │   ├── RegisterRequest.java
    │   ├── LoginRequest.java
    │   └── AuthResponse.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/forgot-password`
- `GET /api/auth/validate` (for Feign clients)
- `GET /api/auth/user/{username}` (for Feign clients)

**Commits to Make:**
1. `feat(auth): create auth-service module structure`
2. `feat(auth): implement JWT token provider`
3. `feat(auth): add security configuration`
4. `feat(auth): implement registration endpoint`
5. `feat(auth): implement login endpoint`
6. `feat(auth): add password reset functionality`
7. `feat(auth): expose validation endpoint for Feign clients`
8. `feat(config): add auth-service configuration`
9. `feat(gateway): add auth-service routes`
10. `feat(docker): add auth-service to docker-compose`

---

### Gopala Krishna - User Service (Port 8082)

**Service Module:** `user-service`

**Responsibilities:**
1. Create `user-service` Maven module
2. Migrate user profile components from P2
3. Implement profile CRUD operations
4. Create Feign client to Auth Service
5. Expose user endpoints for other services

**Module Structure:**
```
user-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/revconnect/user/
    ├── UserServiceApplication.java
    ├── controller/
    │   └── UserController.java
    ├── service/
    │   └── UserService.java
    ├── entity/
    │   ├── User.java
    │   ├── UserRole.java
    │   └── PrivacyType.java
    ├── repository/
    │   └── UserRepository.java
    ├── client/
    │   ├── AuthServiceClient.java
    │   └── NetworkServiceClient.java
    ├── dto/
    │   ├── UserResponse.java
    │   └── ProfileUpdateRequest.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `GET /api/users/me`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `GET /api/users/search?q=`
- `GET /api/users/exists/{id}` (for Feign clients)
- `GET /api/users/by-username/{username}` (for Feign clients)

**Feign Clients:**
```java
@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @GetMapping("/api/auth/user/{username}")
    UserDto getUserByUsername(@PathVariable String username);
}

@FeignClient(name = "network-service")
public interface NetworkServiceClient {
    @GetMapping("/api/network/stats/{userId}")
    NetworkStatsDto getNetworkStats(@PathVariable Long userId);
}
```

**Commits to Make:**
1. `feat(user): create user-service module structure`
2. `feat(user): migrate user entity and repository`
3. `feat(user): implement user profile endpoints`
4. `feat(user): create Feign client for auth-service`
5. `feat(user): create Feign client for network-service`
6. `feat(user): add search functionality`
7. `feat(user): expose user endpoints for Feign clients`
8. `feat(config): add user-service configuration`
9. `feat(gateway): add user-service routes`
10. `feat(docker): add user-service to docker-compose`

---

### Vasanth - Post Service (Port 8083)

**Service Module:** `post-service`

**Responsibilities:**
1. Create `post-service` Maven module
2. Migrate post and feed components from P2
3. Implement post CRUD operations
4. Implement feed algorithm
5. Create Feign clients to User, Network, Interaction services

**Module Structure:**
```
post-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/revconnect/post/
    ├── PostServiceApplication.java
    ├── controller/
    │   ├── PostController.java
    │   └── FeedController.java
    ├── service/
    │   ├── PostService.java
    │   └── FeedService.java
    ├── entity/
    │   ├── Post.java
    │   └── PostType.java
    ├── repository/
    │   └── PostRepository.java
    ├── client/
    │   ├── UserServiceClient.java
    │   ├── NetworkServiceClient.java
    │   └── InteractionServiceClient.java
    ├── dto/
    │   ├── CreatePostRequest.java
    │   ├── UpdatePostRequest.java
    │   ├── PostResponse.java
    │   └── PostAnalyticsResponse.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `POST /api/posts`
- `GET /api/posts/{id}`
- `PUT /api/posts/{id}`
- `DELETE /api/posts/{id}`
- `GET /api/posts/user/{userId}`
- `GET /api/posts/trending`
- `GET /api/posts/search?hashtag=`
- `GET /api/posts/{id}/analytics`
- `GET /api/feed?page=0&size=10`
- `GET /api/posts/by-ids` (for Feign clients)

**Feign Clients:**
```java
@FeignClient(name = "network-service")
public interface NetworkServiceClient {
    @GetMapping("/api/network/connected-ids/{userId}")
    List<Long> getConnectedUserIds(@PathVariable Long userId);

    @GetMapping("/api/network/following-ids/{userId}")
    List<Long> getFollowingIds(@PathVariable Long userId);
}

@FeignClient(name = "interaction-service")
public interface InteractionServiceClient {
    @GetMapping("/api/interactions/post/{postId}/stats")
    InteractionStatsDto getPostStats(@PathVariable Long postId);
}
```

**Commits to Make:**
1. `feat(post): create post-service module structure`
2. `feat(post): migrate post entity and repository`
3. `feat(post): implement post CRUD operations`
4. `feat(post): add hashtag and search functionality`
5. `feat(post): implement trending posts`
6. `feat(post): add post analytics`
7. `feat(post): create Feign clients for network-service`
8. `feat(post): create Feign client for interaction-service`
9. `feat(feed): implement feed algorithm`
10. `feat(config): add post-service configuration`
11. `feat(gateway): add post-service routes`
12. `feat(docker): add post-service to docker-compose`

---

### Sandeep - Network Service (Port 8084)

**Service Module:** `network-service`

**Responsibilities:**
1. Create `network-service` Maven module
2. Migrate connection and follow components from P2
3. Implement connection workflow
4. Implement follow system
5. Expose network data endpoints for other services
6. Trigger notifications via Feign client

**Module Structure:**
```
network-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/revconnect/network/
    ├── NetworkServiceApplication.java
    ├── controller/
    │   └── NetworkController.java
    ├── service/
    │   └── NetworkService.java
    ├── entity/
    │   ├── Connection.java
    │   ├── Follow.java
    │   └── ConnectionStatus.java
    ├── repository/
    │   ├── ConnectionRepository.java
    │   └── FollowRepository.java
    ├── client/
    │   ├── UserServiceClient.java
    │   └── NotificationServiceClient.java
    ├── dto/
    │   ├── ConnectionResponse.java
    │   ├── FollowResponse.java
    │   └── NetworkStatsDto.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**

**Connections:**
- `POST /api/network/connect/{userId}`
- `PUT /api/network/connections/{id}/accept`
- `PUT /api/network/connections/{id}/reject`
- `DELETE /api/network/connect/{userId}`
- `GET /api/network/connections`
- `GET /api/network/requests/received`
- `GET /api/network/requests/sent`
- `GET /api/network/suggestions`

**Follows:**
- `POST /api/network/follow/{userId}`
- `DELETE /api/network/follow/{userId}`
- `GET /api/network/followers/{userId}`
- `GET /api/network/following/{userId}`

**Feign Client Endpoints:**
- `GET /api/network/connected-ids/{userId}`
- `GET /api/network/following-ids/{userId}`
- `GET /api/network/stats/{userId}`
- `GET /api/network/is-connected/{userId1}/{userId2}`
- `GET /api/network/is-following/{followerId}/{followingId}`

**Commits to Make:**
1. `feat(network): create network-service module structure`
2. `feat(network): migrate connection entity and repository`
3. `feat(network): migrate follow entity and repository`
4. `feat(network): implement connection request workflow`
5. `feat(network): implement accept/reject functionality`
6. `feat(network): implement follow/unfollow`
7. `feat(network): add connection suggestions`
8. `feat(network): create Feign client for user-service`
9. `feat(network): create Feign client for notification-service`
10. `feat(network): expose network data endpoints for Feign clients`
11. `feat(config): add network-service configuration`
12. `feat(gateway): add network-service routes`
13. `feat(docker): add network-service to docker-compose`

---

### Siva Sai - Interaction Service (Port 8085)

**Service Module:** `interaction-service`

**Responsibilities:**
1. Create `interaction-service` Maven module
2. Migrate like and comment components from P2
3. Combine likes and comments into single service
4. Trigger notifications via Feign client
5. Expose interaction stats for other services

**Module Structure:**
```
interaction-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/revconnect/interaction/
    ├── InteractionServiceApplication.java
    ├── controller/
    │   ├── LikeController.java
    │   └── CommentController.java
    ├── service/
    │   ├── LikeService.java
    │   └── CommentService.java
    ├── entity/
    │   ├── Like.java
    │   └── Comment.java
    ├── repository/
    │   ├── LikeRepository.java
    │   └── CommentRepository.java
    ├── client/
    │   ├── PostServiceClient.java
    │   ├── UserServiceClient.java
    │   └── NotificationServiceClient.java
    ├── dto/
    │   ├── LikeResponse.java
    │   ├── CommentRequest.java
    │   ├── CommentResponse.java
    │   └── InteractionStatsDto.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**

**Likes:**
- `POST /api/posts/{postId}/like`
- `DELETE /api/posts/{postId}/like`
- `GET /api/posts/{postId}/likes/count`
- `GET /api/posts/{postId}/liked` (check if current user liked)

**Comments:**
- `POST /api/posts/{postId}/comments`
- `GET /api/posts/{postId}/comments?page=0&size=20`
- `DELETE /api/comments/{id}`
- `GET /api/posts/{postId}/comments/count`

**Feign Client Endpoints:**
- `GET /api/interactions/post/{postId}/stats` (likeCount, commentCount, isLiked)

**Commits to Make:**
1. `feat(interaction): create interaction-service module structure`
2. `feat(interaction): migrate like entity and repository`
3. `feat(interaction): migrate comment entity and repository`
4. `feat(interaction): implement like/unlike functionality`
5. `feat(interaction): implement comment CRUD`
6. `feat(interaction): create Feign client for post-service`
7. `feat(interaction): create Feign client for user-service`
8. `feat(interaction): create Feign client for notification-service`
9. `feat(interaction): expose interaction stats for Feign clients`
10. `feat(config): add interaction-service configuration`
11. `feat(gateway): add interaction-service routes`
12. `feat(docker): add interaction-service to docker-compose`

---

### Yakkanti - Notification Service (Port 8086)

**Service Module:** `notification-service`

**Responsibilities:**
1. Create `notification-service` Maven module
2. Migrate notification components from P2
3. Expose notification trigger endpoints for other services
4. Implement notification query and management

**Module Structure:**
```
notification-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/revconnect/notification/
    ├── NotificationServiceApplication.java
    ├── controller/
    │   └── NotificationController.java
    ├── service/
    │   └── NotificationService.java
    ├── entity/
    │   ├── Notification.java
    │   └── NotificationType.java
    ├── repository/
    │   └── NotificationRepository.java
    ├── client/
    │   └── UserServiceClient.java
    ├── dto/
    │   ├── NotificationResponse.java
    │   └── CreateNotificationRequest.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**

**User-facing:**
- `GET /api/notifications?page=0&size=20`
- `GET /api/notifications/unread-count`
- `PUT /api/notifications/{id}/read`
- `PUT /api/notifications/read-all`

**Feign Client Endpoints (called by other services):**
- `POST /api/notifications/connection-request`
- `POST /api/notifications/connection-accepted`
- `POST /api/notifications/new-follower`
- `POST /api/notifications/post-liked`
- `POST /api/notifications/post-commented`
- `POST /api/notifications/post-shared`

**Commits to Make:**
1. `feat(notification): create notification-service module structure`
2. `feat(notification): migrate notification entity and repository`
3. `feat(notification): implement notification query endpoints`
4. `feat(notification): implement mark as read functionality`
5. `feat(notification): add unread count endpoint`
6. `feat(notification): expose trigger endpoints for Feign clients`
7. `feat(notification): create Feign client for user-service`
8. `feat(config): add notification-service configuration`
9. `feat(gateway): add notification-service routes`
10. `feat(docker): add notification-service to docker-compose`

---

## API Gateway Routes Configuration

Each developer adds their service routes to `api-gateway.yml`:

```yaml
spring:
  cloud:
    gateway:
      routes:
        # Srinivas Rao's routes
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/api/auth/**

        # Gopala Krishna's routes
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**

        # Vasanth's routes
        - id: post-service
          uri: lb://post-service
          predicates:
            - Path=/api/posts/**, /api/feed/**

        # Sandeep's routes
        - id: network-service
          uri: lb://network-service
          predicates:
            - Path=/api/network/**

        # Siva Sai's routes (likes/comments under posts path)
        - id: interaction-service-likes
          uri: lb://interaction-service
          predicates:
            - Path=/api/posts/*/like, /api/posts/*/likes/**
        - id: interaction-service-comments
          uri: lb://interaction-service
          predicates:
            - Path=/api/posts/*/comments, /api/posts/*/comments/**, /api/comments/**

        # Yakkanti's routes
        - id: notification-service
          uri: lb://notification-service
          predicates:
            - Path=/api/notifications/**
```

---

## Service Communication Matrix

| From Service | To Service | Purpose | Feign Client Owner |
|--------------|------------|---------|-------------------|
| user-service | auth-service | Validate JWT, get user by username | Gopala Krishna |
| user-service | network-service | Get follower/following counts | Gopala Krishna |
| post-service | user-service | Get post author info | Vasanth |
| post-service | network-service | Get connected/following IDs for feed | Vasanth |
| post-service | interaction-service | Get like/comment counts | Vasanth |
| network-service | user-service | Get user info for connections | Sandeep |
| network-service | notification-service | Trigger connection notifications | Sandeep |
| interaction-service | post-service | Verify post exists | Siva Sai |
| interaction-service | user-service | Get user info | Siva Sai |
| interaction-service | notification-service | Trigger like/comment notifications | Siva Sai |
| notification-service | user-service | Get user info for notifications | Yakkanti |

---

## Commit Message Guidelines

All commits should follow conventional commits format:

```
<type>(<scope>): <description>

Types:
- feat: New feature
- fix: Bug fix
- docs: Documentation
- test: Tests
- chore: Maintenance
- refactor: Code restructuring

Scopes:
- auth: Auth service (Srinivas Rao)
- user: User service (Gopala Krishna)
- post: Post service (Vasanth)
- feed: Feed functionality (Vasanth)
- network: Network service (Sandeep)
- interaction: Interaction service (Siva Sai)
- notification: Notification service (Yakkanti)
- infra: Infrastructure (config, discovery, gateway)
- docker: Docker/containerization
- config: Configuration files
- gateway: API Gateway
```

**Examples:**
```
feat(auth): implement JWT token generation
feat(post): add hashtag search functionality
fix(network): correct connection status workflow
test(interaction): add unit tests for like service
docs(readme): update API documentation
```

---

## Testing Responsibilities

| Developer | Unit Test Scope | Integration Test Scope |
|-----------|-----------------|----------------------|
| Srinivas Rao | AuthService, JwtTokenProvider | Auth + Gateway integration |
| Gopala Krishna | UserService | User + Auth + Network Feign clients |
| Vasanth | PostService, FeedService | Post + Network + Interaction Feign clients |
| Sandeep | NetworkService | Network + User + Notification Feign clients |
| Siva Sai | LikeService, CommentService | Interaction + Post + Notification Feign clients |
| Yakkanti | NotificationService | Notification + User Feign clients |

---

## Summary

### P2 Contribution Summary

| Developer | Controllers | Services | Entities | Endpoints |
|-----------|-------------|----------|----------|-----------|
| Srinivas Rao | 1 | 1 | 0 (uses User) | 3 |
| Gopala Krishna | 1 | 1 | 1 + 2 enums | 4 |
| Vasanth | 2 | 2 | 1 + 1 enum | 9 |
| Sandeep | 1 | 1 | 2 + 1 enum | 12 |
| Siva Sai | 2 | 2 | 2 | 5 |
| Yakkanti | 1 | 1 | 1 + 1 enum | 4 |

### P3 Module Ownership

| Developer | Service | Port | Dependencies |
|-----------|---------|------|--------------|
| Srinivas Rao | auth-service | 8081 | None (base service) |
| Gopala Krishna | user-service | 8082 | auth-service, network-service |
| Vasanth | post-service | 8083 | user-service, network-service, interaction-service |
| Sandeep | network-service | 8084 | user-service, notification-service |
| Siva Sai | interaction-service | 8085 | post-service, user-service, notification-service |
| Yakkanti | notification-service | 8086 | user-service |

---

## Next Steps

1. **Set up Git repository** for P3 project
2. **Phase 1**: Infrastructure setup (pair programming)
3. **Phase 2**: Individual service development (parallel)
4. **Phase 3**: Integration and testing (all hands)
5. **Documentation** and demo preparation
