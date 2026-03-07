# Project Presentation Deck - RevConnect

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 6 members
**Template Version:** 1.0

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
+-----------------------------------------------------------------+
|                                                                 |
|                        RevConnect                               |
|              Social Media Platform                              |
|                                                                 |
|     Phase 1: Monolithic Application (AWS Deployment)            |
|     Phase 2: Microservices Architecture (Docker)                |
|                                                                 |
|     Team Members (Full Stack Developers):                       |
|     - Gopala Krishna - User Profile                             |
|     - Vasanth - Posts and Feed                                  |
|     - Sandeep - Network Management                              |
|     - Srinivas Rao - Authentication                             |
|     - Siva Sai - Likes & Comments                               |
|     - Laxmi Narayana - Notifications                            |
|                                                                 |
|     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]               |
|                                                                 |
+-----------------------------------------------------------------+
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A professional full-stack social media application providing a platform for personal users, businesses, and content creators to connect, share posts, engage through comments and likes, and receive personalized feeds and notifications.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | User Registration & Authentication | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-02 | User Profile with Bio, Location, Website | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-03 | Create/Edit/Delete Text Posts with Hashtags | Main Use Case 1 (20 pts) | Yes | Yes |
| FR-04 | Business/Creator Profiles with Analytics | Use Case 2 (14 pts) | Yes | Yes |
| FR-05 | Post Scheduling & Promotional Content | Use Case 2 (14 pts) | Yes | Yes |
| FR-06 | Product/Service Tagging & Call-to-Action | Use Case 2 (14 pts) | Yes | Yes |
| FR-07 | Connection Requests & Follow System | Common Features (6 pts) | Yes | Yes |
| FR-08 | Likes & Comments on Posts | Common Features (6 pts) | Yes | Yes |
| FR-09 | Notifications & Personalized Feed | Common Features (6 pts) | Yes | Yes |
| FR-10 | Privacy Settings (Public/Private) | Common Features | Yes | Yes |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Authentication | JWT with BCrypt |
| Security | Password Storage | BCrypt (strength 12) |
| Security | Algorithm | HS512 |
| Performance | API Response | < 500ms |
| Resilience | Circuit Breaker | Resilience4j |
| Availability | Uptime | 99% |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL 8 database for production, H2 for development (Phase 2) |
| A-02 | AWS Free Tier resources accessible for Phase 1 deployment |
| A-03 | Docker environment available for Phase 2 microservices |
| A-04 | Team has GitHub repository access |
| A-05 | Three user roles: PERSONAL, CREATOR, BUSINESS |
| A-06 | Hibernate auto-creates tables via ddl-auto=update |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Inter-service communication failures | High | Circuit breakers with Resilience4j, retry logic |
| Service discovery failures | High | Eureka health checks, 30s intervals, 5 retries |
| Insufficient test coverage | High | Unit tests with JUnit 4 & Mockito |
| AWS deployment issues | Medium | Docker Compose local fallback |
| SQL injection vulnerabilities | High | JPA/Hibernate parameterized queries |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
+-----------------------------------------------------------------+
|                    Angular 17 Frontend                          |
|              (TypeScript, Bootstrap 5, Node.js 18+)             |
+-----------------------------------------------------------------+
                              |
                    REST API (HTTP/JSON)
                              |
+-----------------------------------------------------------------+
|                   Spring Boot 3.2 Backend                       |
|     +-------------------------------------------------------+   |
|     |  Auth  | User | Post | Comment | Like | Network | ... |   |
|     +-------------------------------------------------------+   |
|     |              Spring Security + JWT                     |  |
|     +-------------------------------------------------------+   |
|     |              Spring Data JPA / Hibernate               |  |
|     +-------------------------------------------------------+   |
+-----------------------------------------------------------------+
                              |
+-----------------------------------------------------------------+
|                    MySQL 8 Database                             |
|     users | posts | comments | likes | connections | follows    |
+-----------------------------------------------------------------+
```

**Technologies:** Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA, JWT (JJWT 0.11.5), BCrypt, MySQL 8, Angular 17, Bootstrap 5

---

### SLIDE 5: Phase 2 - Microservices Architecture (2 minutes)

```
+-----------------------------------------------------------------+
|                       Angular Frontend                          |
+-----------------------------------------------------------------+
                              |
+-----------------------------------------------------------------+
|                   API Gateway (Port 8080)                       |
|              (JWT Validation, Request Routing)                  |
+-----------------------------------------------------------------+
           |          |          |          |          |
     +----------+ +----------+ +----------+ +----------+ +----------+
     |   Auth   | |   User   | |   Post   | | Network  | |Interaction|
     | Service  | | Service  | | Service  | | Service  | | Service  |
     |  (8081)  | |  (8082)  | |  (8083)  | |  (8084)  | |  (8085)  |
     +----------+ +----------+ +----------+ +----------+ +----------+
           |                                                   |
     +----------+                                        +----------+
     |Notification|                                      | Config   |
     | Service   |                                       | Server   |
     |  (8086)   |                                       |  (8888)  |
     +----------+                                        +----------+
                              |
+-----------------------------------------------------------------+
|               Discovery Server - Eureka (Port 8761)             |
+-----------------------------------------------------------------+
```

**Service Ports:**
| Service | Port | Owner |
|---------|------|-------|
| Config Server | 8888 | Shared |
| Discovery Server | 8761 | Shared |
| API Gateway | 8080 | Shared |
| Auth Service | 8081 | Srinivas Rao |
| User Service | 8082 | Gopala Krishna |
| Post Service | 8083 | Vasanth |
| Network Service | 8084 | Sandeep |
| Interaction Service | 8085 | Siva Sai |
| Notification Service | 8086 | Laxmi Narayana |

---

### SLIDE 6: ERD - Entity Relationship Diagram (1-2 minutes)

```
+------------------+          +------------------+
|      users       |          |      posts       |
+------------------+          +------------------+
| PK id            |<-------->| PK id            |
| username (UK)    |    |     | FK user_id       |
| email (UK)       |    |     | content          |
| password (BCrypt)|    |     | type (ENUM)      |
| role (ENUM)      |    |     | hashtags         |
| first_name       |    |     | view_count       |
| last_name        |    |     | pinned           |
| bio              |    |     | scheduled_at     |
| profile_picture  |    |     | created_at       |
| location         |    |     +------------------+
| website          |
| privacy (ENUM)   |          +------------------+
| business_name    |          |     comments     |
| category         |          +------------------+
| created_at       |          | PK id            |
+------------------+          | FK post_id       |
       |                      | FK user_id       |
       |                      | content          |
       |                      | created_at       |
       v                      +------------------+
+------------------+
|   connections    |          +------------------+
+------------------+          |      likes       |
| PK id            |          +------------------+
| FK requester_id  |          | PK id            |
| FK addressee_id  |          | FK post_id       |
| status (ENUM)    |          | FK user_id       |
| created_at       |          | created_at       |
+------------------+          +------------------+

+------------------+          +------------------+
|     follows      |          |  notifications   |
+------------------+          +------------------+
| PK id            |          | PK id            |
| FK follower_id   |          | FK recipient_id  |
| FK following_id  |          | FK sender_id     |
| created_at       |          | type (ENUM)      |
+------------------+          | reference_id     |
                              | message          |
                              | is_read          |
                              | created_at       |
                              +------------------+
```

**Enums:**
- **User Roles:** PERSONAL, CREATOR, BUSINESS
- **Privacy:** PUBLIC, PRIVATE
- **Post Types:** TEXT, IMAGE, PROMOTIONAL, REPOST, ANNOUNCEMENT
- **Connection Status:** PENDING, ACCEPTED, REJECTED
- **Notification Types:** CONNECTION_REQUEST, CONNECTION_ACCEPTED, NEW_FOLLOWER, POST_LIKED, POST_COMMENTED, POST_SHARED, POST_MENTIONED

---

### SLIDE 7: Security Implementation (1-2 minutes)

#### Authentication Flow

```
+--------+     +----------+     +-------------+     +----------+
| Client | --> |  Login   | --> | Validate    | --> | Generate |
|        |     | Request  |     | Credentials |     | JWT      |
+--------+     +----------+     +-------------+     +----------+
                                      |                   |
                              BCrypt Verify          HS512 Sign
                                      |                   |
                              +-------------+     +----------+
                              |   User DB   |     | Return   |
                              |             |     | Token    |
                              +-------------+     +----------+
```

#### Security Features

| Feature | Implementation |
|---------|---------------|
| Password Hashing | BCrypt (strength 12) |
| Token Algorithm | HS512 |
| Token Library | JJWT 0.11.5 |
| Authorization | Spring Security on all endpoints |
| Input Validation | @Valid + Bean Validation |
| SQL Injection Prevention | JPA/Hibernate parameterized queries |
| XSS Prevention | Spring response encoding |
| CORS | Configured for Angular origin only |

#### Phase 2 Gateway Security

```
Request --> API Gateway --> Validate JWT --> Route to Service
                |
                v
           Extract Claims
           Forward User Context
```

---

### SLIDE 8: Testing Strategy (1-2 minutes)

#### Testing Approach

| Test Type | Framework | Coverage |
|-----------|-----------|----------|
| Unit Tests | JUnit 4, Mockito | Service & Controller layers |
| Integration Tests | Spring Boot Test | API endpoints |
| Frontend Tests | Angular Testing | Components & Services |

#### Test Coverage by Module

| Module | Test Files |
|--------|------------|
| Auth | AuthControllerTest, AuthServiceTest |
| User | UserControllerTest, UserServiceTest |
| Post | PostControllerTest, PostServiceTest |
| Comment | CommentControllerTest, CommentServiceTest |
| Like | LikeControllerTest, LikeServiceTest |
| Network | NetworkControllerTest, NetworkServiceTest |
| Notification | NotificationControllerTest, NotificationServiceTest |
| Feed | FeedControllerTest, FeedServiceTest |

#### Test Execution

```bash
cd backend
mvn test
```

---

### SLIDE 9: API Documentation (1 minute)

#### Key API Endpoints (27+ Total)

**Authentication:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | User registration |
| POST | /api/auth/login | Login with JWT response |

**User Profile:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/users/me | Current user profile |
| GET | /api/users/{id} | Get user by ID |
| PUT | /api/users/{id} | Update profile |
| GET | /api/users/search?q= | Search users |

**Posts:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/posts | Create post |
| GET | /api/posts/{id} | Get post |
| PUT | /api/posts/{id} | Update post |
| DELETE | /api/posts/{id} | Delete post |
| GET | /api/posts/trending | Trending posts |
| GET | /api/posts/{id}/analytics | Post analytics |

**Network:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/network/connect/{userId} | Send connection |
| PUT | /api/network/connections/{id}/accept | Accept connection |
| POST | /api/network/follow/{userId} | Follow user |
| GET | /api/network/followers/{userId} | Get followers |

---

### SLIDE 10: Use Case 1 - User Profile Management (Gopala Krishna) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- User profile CRUD operations
- Profile customization (bio, location, website, profile picture)
- Business/Creator profile enhancements
- User search functionality

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | View/Edit   | --> | Validate    | --> | Update   |
|        |     | Profile     |     | Input       |     | Database |
+--------+     +-------------+     +-------------+     +----------+
                     |                                       |
                     v                                       v
              +-------------+                         +----------+
              | Display     |<------------------------| Success  |
              | Updated     |                         | Response |
              +-------------+                         +----------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/users/me | Get current user profile |
| GET | /api/users/{id} | Get user by ID |
| PUT | /api/users/{id} | Update user profile |
| GET | /api/users/search?q= | Search users |

#### Code Highlight (UserService.java)

```java
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBio(request.getBio());
        user.setLocation(request.getLocation());
        user.setWebsite(request.getWebsite());
        user.setProfilePicture(request.getProfilePicture());

        return mapToResponse(userRepository.save(user));
    }
}
```

---

### SLIDE 11: Use Case 2 - Posts and Feed (Vasanth) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Post creation with multiple types (TEXT, IMAGE, PROMOTIONAL, REPOST, ANNOUNCEMENT)
- Hashtag support for content discovery
- Post scheduling functionality
- Personalized feed generation
- Post analytics for creators/businesses

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | Create Post | --> | Extract     | --> | Save to  |
|        |     | with Tags   |     | Hashtags    |     | Database |
+--------+     +-------------+     +-------------+     +----------+
                     |                                       |
                     v                                       v
              +-------------+                         +----------+
              | Notify      |<------------------------| Generate |
              | Followers   |                         | Feed     |
              +-------------+                         +----------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/posts | Create new post |
| GET | /api/posts/{id} | Get post by ID |
| PUT | /api/posts/{id} | Update post |
| DELETE | /api/posts/{id} | Delete post |
| GET | /api/posts/user/{userId} | Get user's posts |
| GET | /api/posts/trending | Get trending posts |
| GET | /api/posts/search?hashtag= | Search by hashtag |
| GET | /api/posts/{id}/analytics | Post analytics |
| GET | /api/feed | Personalized feed |

#### Code Highlight (PostService.java)

```java
@Service
public class PostService {

    public PostResponse createPost(Long userId, CreatePostRequest request) {
        Post post = Post.builder()
            .user(userRepository.findById(userId).orElseThrow())
            .content(request.getContent())
            .type(request.getType())
            .hashtags(extractHashtags(request.getContent()))
            .callToActionLabel(request.getCallToActionLabel())
            .callToActionUrl(request.getCallToActionUrl())
            .build();

        Post saved = postRepository.save(post);
        notifyFollowers(userId, saved);
        return mapToResponse(saved);
    }
}
```

---

### SLIDE 12: Use Case 3 - Network Management (Sandeep) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Connection request system (send, accept, reject)
- Follow/Unfollow functionality
- Connections list management
- Followers/Following management
- Network relationship status tracking

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
| User A | --> | Send        | --> | Create      | --> | Notify   |
|        |     | Connection  |     | PENDING     |     | User B   |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                                          v
+--------+     +-------------+     +-------------+
| User B | --> | Accept/     | --> | Update      |
|        |     | Reject      |     | Status      |
+--------+     +-------------+     +-------------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/network/connect/{userId} | Send connection request |
| PUT | /api/network/connections/{id}/accept | Accept request |
| PUT | /api/network/connections/{id}/reject | Reject request |
| DELETE | /api/network/connect/{userId} | Remove connection |
| GET | /api/network/connections | Get my connections |
| GET | /api/network/requests/received | Pending requests |
| GET | /api/network/requests/sent | Sent requests |
| POST | /api/network/follow/{userId} | Follow user |
| DELETE | /api/network/follow/{userId} | Unfollow user |
| GET | /api/network/followers/{userId} | Get followers |
| GET | /api/network/following/{userId} | Get following |

#### Code Highlight (NetworkService.java)

```java
@Service
public class NetworkService {

    public ConnectionResponse sendConnectionRequest(Long requesterId, Long addresseeId) {
        if (connectionExists(requesterId, addresseeId)) {
            throw new DuplicateResourceException("Connection already exists");
        }

        Connection connection = Connection.builder()
            .requester(userRepository.findById(requesterId).orElseThrow())
            .addressee(userRepository.findById(addresseeId).orElseThrow())
            .status(ConnectionStatus.PENDING)
            .build();

        Connection saved = connectionRepository.save(connection);
        notificationService.createNotification(addresseeId, requesterId,
            NotificationType.CONNECTION_REQUEST, null);

        return mapToResponse(saved);
    }
}
```

---

### SLIDE 13: Use Case 4 - Authentication (Srinivas Rao) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- User registration with validation
- Login with JWT token generation
- JWT token validation and parsing
- Password hashing with BCrypt
- Security configuration

#### Authentication Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | Register/   | --> | Validate    | --> | Hash     |
|        |     | Login       |     | Input       |     | Password |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                     +--------------------+
                     |
                     v
              +-------------+     +-------------+     +----------+
              | Generate    | --> | Return      | --> | Store    |
              | JWT Token   |     | Response    |     | Client   |
              +-------------+     +-------------+     +----------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | User registration |
| POST | /api/auth/login | Login with JWT |

#### Code Highlight (AuthService.java & JwtTokenProvider.java)

```java
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        User user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(UserRole.PERSONAL)
            .build();

        userRepository.save(user);
        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token, mapToUserResponse(user));
    }
}

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .claim("email", user.getEmail())
            .claim("role", user.getRole().name())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}
```

---

### SLIDE 14: Use Case 5 - Likes & Comments (Siva Sai) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Like/Unlike posts
- Create/Delete comments
- Retrieve comments for posts
- Trigger notifications for engagement
- Unique constraint enforcement for likes

#### Use Case Flow

```
+--------+     +-------------+     +-------------+     +----------+
|  User  | --> | Like/       | --> | Check       | --> | Save     |
|        |     | Comment     |     | Duplicate   |     | to DB    |
+--------+     +-------------+     +-------------+     +----------+
                                          |
                                          v
                                   +-------------+
                                   | Notify Post |
                                   | Owner       |
                                   +-------------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/posts/{id}/like | Like a post |
| DELETE | /api/posts/{id}/like | Unlike a post |
| POST | /api/posts/{id}/comments | Add comment |
| GET | /api/posts/{id}/comments | Get comments |
| DELETE | /api/comments/{id} | Delete comment |

#### Code Highlight (LikeService.java & CommentService.java)

```java
@Service
public class LikeService {

    public void likePost(Long userId, Long postId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new DuplicateResourceException("Already liked");
        }

        Like like = Like.builder()
            .user(userRepository.findById(userId).orElseThrow())
            .post(postRepository.findById(postId).orElseThrow())
            .build();

        likeRepository.save(like);

        Post post = postRepository.findById(postId).orElseThrow();
        notificationService.createNotification(post.getUser().getId(),
            userId, NotificationType.POST_LIKED, postId);
    }
}

@Service
public class CommentService {

    public CommentResponse addComment(Long userId, Long postId, String content) {
        Comment comment = Comment.builder()
            .user(userRepository.findById(userId).orElseThrow())
            .post(postRepository.findById(postId).orElseThrow())
            .content(content)
            .build();

        Comment saved = commentRepository.save(comment);
        notificationService.createNotification(saved.getPost().getUser().getId(),
            userId, NotificationType.POST_COMMENTED, postId);

        return mapToResponse(saved);
    }
}
```

---

### SLIDE 15: Use Case 6 - Notifications (Laxmi Narayana) (2 minutes)

#### Feature Overview

**Primary Responsibilities:**
- Create notifications for various events
- Retrieve user notifications
- Mark notifications as read (individual/all)
- Unread count tracking
- Notification categorization by type

#### Notification Types

| Type | Trigger |
|------|---------|
| CONNECTION_REQUEST | User sends connection request |
| CONNECTION_ACCEPTED | User accepts connection |
| NEW_FOLLOWER | User starts following |
| POST_LIKED | User likes a post |
| POST_COMMENTED | User comments on post |
| POST_SHARED | User shares a post |
| POST_MENTIONED | User mentioned in post |

#### Use Case Flow

```
+----------+     +-------------+     +-------------+     +----------+
| Event    | --> | Create      | --> | Store       | --> | User     |
| Trigger  |     | Notification|     | in DB       |     | Notified |
+----------+     +-------------+     +-------------+     +----------+
                                           |
                                           v
                                    +-------------+
                                    | Mark Read   |
                                    | on View     |
                                    +-------------+
```

#### API Endpoints Owned

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/notifications | Get all notifications |
| GET | /api/notifications/unread-count | Get unread count |
| PUT | /api/notifications/{id}/read | Mark as read |
| PUT | /api/notifications/read-all | Mark all as read |

#### Code Highlight (NotificationService.java)

```java
@Service
public class NotificationService {

    public void createNotification(Long recipientId, Long senderId,
            NotificationType type, Long referenceId) {

        if (recipientId.equals(senderId)) return; // Don't notify self

        Notification notification = Notification.builder()
            .recipient(userRepository.findById(recipientId).orElseThrow())
            .sender(userRepository.findById(senderId).orElseThrow())
            .type(type)
            .referenceId(referenceId)
            .message(generateMessage(type, senderId))
            .isRead(false)
            .build();

        notificationRepository.save(notification);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByRecipientIdAndIsReadFalse(userId);
    }

    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadByRecipientId(userId);
    }
}
```

---

### SLIDE 16: Phase 2 - Microservices Deep Dive (2 minutes)

#### Service Architecture Details

| Service | Port | Database | Key Dependencies |
|---------|------|----------|------------------|
| Config Server | 8888 | None | Spring Cloud Config |
| Discovery Server | 8761 | None | Eureka Server |
| API Gateway | 8080 | None | Spring Cloud Gateway, JWT |
| Auth Service | 8081 | H2 (dev) | Spring Security, JWT |
| User Service | 8082 | H2 (dev) | Spring Data JPA, OpenFeign |
| Post Service | 8083 | H2 (dev) | Spring Data JPA, OpenFeign |
| Network Service | 8084 | H2 (dev) | Spring Data JPA, OpenFeign |
| Interaction Service | 8085 | H2 (dev) | Spring Data JPA, OpenFeign |
| Notification Service | 8086 | H2 (dev) | Spring Data JPA, OpenFeign |

#### Inter-Service Communication

```
+----------------+     OpenFeign     +----------------+
| Post Service   | <--------------> | User Service   |
|                |                  |                |
+----------------+                  +----------------+
        |                                   |
        |          +----------------+       |
        +--------> | Notification   | <-----+
                   | Service        |
                   +----------------+
```

#### Docker Compose Startup Sequence

```
1. Config Server (8888) - starts first
       |
       v
2. Discovery Server (8761) - depends on Config Server health
       |
       v
3. All Services (8080-8086) - depend on Discovery Server health
```

---

### SLIDE 17: Circuit Breaker & Resilience (1 minute)

#### Resilience4j Implementation

```java
@Service
public class PostService {

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    public UserDTO getUserDetails(Long userId) {
        return userServiceClient.getUser(userId);
    }

    public UserDTO getUserFallback(Long userId, Exception ex) {
        return UserDTO.builder()
            .id(userId)
            .username("Unknown User")
            .build();
    }
}
```

#### Health Check Configuration

| Property | Value |
|----------|-------|
| Interval | 30 seconds |
| Timeout | 10 seconds |
| Retries | 5 |
| Endpoint | /actuator/health |

---

### SLIDE 18: Deployment Architecture (1-2 minutes)

#### Phase 1: AWS Deployment

```
+---------------------------+
|    AWS Cloud              |
|  +---------------------+  |
|  |    EC2 Instance     |  |
|  |  +--------------+   |  |
|  |  | Spring Boot  |   |  |
|  |  | Application  |   |  |
|  |  +--------------+   |  |
|  |         |           |  |
|  |  +--------------+   |  |
|  |  |    MySQL     |   |  |
|  |  |   Database   |   |  |
|  |  +--------------+   |  |
|  +---------------------+  |
+---------------------------+
```

#### Phase 2: Docker Deployment

```
+--------------------------------------------------+
|              Docker Compose                       |
|  +------------+  +------------+  +------------+  |
|  |  Config    |  | Discovery  |  |    API     |  |
|  |  Server    |  |  Server    |  |  Gateway   |  |
|  +------------+  +------------+  +------------+  |
|                                                   |
|  +--------+ +--------+ +--------+ +--------+     |
|  |  Auth  | |  User  | |  Post  | |Network |     |
|  +--------+ +--------+ +--------+ +--------+     |
|                                                   |
|  +------------+  +------------+                   |
|  |Interaction |  |Notification|                   |
|  +------------+  +------------+                   |
|                                                   |
|  Network: revconnect-network (bridge)             |
+--------------------------------------------------+
```

#### Docker Commands

```bash
# Build all services
docker-compose build

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

---

### SLIDE 19: Demo Walkthrough (2-3 minutes)

#### Demo Scenario

**Step 1: User Registration & Authentication**
- Register new user with email, username, password
- Login and receive JWT token
- View profile

**Step 2: Content Creation**
- Create post with hashtags
- View post in feed
- Check post analytics (creator account)

**Step 3: Social Networking**
- Search for users
- Send connection request
- Accept connection request
- Follow a user

**Step 4: Engagement**
- Like a post
- Add comment
- View notifications
- Mark notifications as read

**Step 5: Microservices Demo (Phase 2)**
- Show Eureka dashboard (http://localhost:8761)
- Demonstrate service discovery
- Show circuit breaker in action

---

### SLIDE 20: Challenges & Solutions (1-2 minutes)

| Challenge | Solution |
|-----------|----------|
| JWT token validation across services | API Gateway centralized validation with claims forwarding |
| Service-to-service authentication | OpenFeign clients with JWT propagation |
| Database consistency across services | Each service owns its domain data, eventual consistency |
| Circular dependency notifications | Event-driven notification creation |
| Connection request race conditions | Unique constraint on (requester_id, addressee_id) |
| Feed performance with many connections | Indexed queries on user_id and created_at |

---

### SLIDE 21: Key Learnings (1 minute)

| Area | Learning |
|------|----------|
| Microservices | Service boundaries should align with business domains |
| Security | JWT with short expiry and proper secret management |
| Testing | Mock external dependencies in unit tests |
| DevOps | Docker Compose simplifies multi-service development |
| Database | H2 for development speeds up iteration |
| Resilience | Circuit breakers prevent cascade failures |

---

### SLIDE 22: Future Enhancements (1 minute)

| Enhancement | Description |
|-------------|-------------|
| Direct Messaging | Real-time chat between connections |
| Image/Media Posts | Upload and display images in posts |
| Post Scheduling | Business accounts schedule content |
| Advanced Analytics | Engagement metrics dashboard |
| Email Notifications | External notification delivery |
| OAuth2 Integration | Social login (Google, GitHub) |
| Elasticsearch | Full-text search for posts and users |
| Redis Caching | Performance optimization |

---

### SLIDE 23: Repository & Resources (30 seconds)

#### GitHub Repositories

| Phase | Repository |
|-------|------------|
| Phase 1 (Monolithic) | https://github.com/sandeepakula2002/RevConnect |
| Phase 2 (Microservices) | https://github.com/sandeepakula2002/RevConnect-P3 |

#### Technology Documentation

- [Spring Boot 3.2](https://spring.io/projects/spring-boot)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Angular 17](https://angular.io/)
- [Docker Compose](https://docs.docker.com/compose/)

---

### SLIDE 24: Team Contributions Summary

| Team Member | Phase 1 Contribution | Phase 2 Service |
|-------------|---------------------|-----------------|
| Gopala Krishna | User module (frontend + backend) | User Service (8082) |
| Vasanth | Post, Feed modules (frontend + backend) | Post Service (8083) |
| Sandeep | Network module (connections, follows) | Network Service (8084) |
| Srinivas Rao | Auth module, Security config, JWT | Auth Service (8081) |
| Siva Sai | Like, Comment modules | Interaction Service (8085) |
| Laxmi Narayana | Notification module | Notification Service (8086) |

---

### SLIDE 25: Q&A (5-10 minutes)

```
+-----------------------------------------------------------------+
|                                                                 |
|                     Questions & Answers                         |
|                                                                 |
|     Thank you for your attention!                               |
|                                                                 |
|     Contact:                                                    |
|     - GitHub: github.com/sandeepakula2002                       |
|                                                                 |
+-----------------------------------------------------------------+
```

---

## PRESENTATION NOTES

### Time Allocation

| Section | Duration |
|---------|----------|
| Introduction & Overview (Slides 1-3) | 3 minutes |
| Architecture (Slides 4-6) | 5 minutes |
| Security & Testing (Slides 7-8) | 3 minutes |
| API Documentation (Slide 9) | 1 minute |
| Team Member Use Cases (Slides 10-15) | 12 minutes |
| Microservices Deep Dive (Slides 16-17) | 3 minutes |
| Deployment & Demo (Slides 18-19) | 4 minutes |
| Wrap-up (Slides 20-25) | 4 minutes |
| **Total** | **35 minutes** |

### Demo Preparation Checklist

- [ ] MySQL database running with sample data
- [ ] Backend server started on port 8080
- [ ] Frontend running on port 4200
- [ ] Test accounts created (personal, creator, business)
- [ ] Sample posts with hashtags
- [ ] Docker Compose environment ready (Phase 2)
- [ ] Eureka dashboard accessible

---

*RevConnect Team - 2024*
