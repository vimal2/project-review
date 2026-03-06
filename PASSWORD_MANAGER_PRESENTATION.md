# Project Presentation Deck - Password Manager

## Full Stack Application Development - Phase 1 & Phase 2

**Duration:** 20-25 minutes presentation + 5-10 minutes Q&A
**Team Size:** 4 members
**Template Version:** 1.0

---

## SLIDE DECK CONTENT

---

### SLIDE 1: Title Slide (30 seconds)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                     Password Manager                        │
│           Secure Credential Storage & Management            │
│                                                             │
│     Phase 1: Monolithic Application (AWS Deployment)        │
│     Phase 2: Microservices Architecture (Local/Docker)      │
│                                                             │
│     Team Members (Full Stack Developers):                   │
│     • Namratha - Register & Login, Chrome Extension,        │
│                  Security                                   │
│     • Avila - Vault Management, Chrome Extension            │
│     • Keerthana - Password Generator, Security Report,      │
│                   AWS Deployment                            │
│     • Akhila - Backup Operations                            │
│                                                             │
│     Batch: [BATCH_ID] | Date: [PRESENTATION_DATE]           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### SLIDE 2: Project Overview & Requirements (1-2 minutes)

**Project Purpose:** A secure password management application that allows users to safely store, manage, and generate strong passwords for their various online accounts with encryption, multi-factor authentication, and browser integration.

#### Functional Requirements

| # | Requirement | Use Case Category | Phase 1 | Phase 2 |
|---|-------------|-------------------|---------|---------|
| FR-01 | Vault Entry CRUD Operations | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-02 | Password Search, Filter, Favorites | Main Use Case 1 (20 pts) | ✓ | ✓ |
| FR-03 | Password Generation with Custom Parameters | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-04 | Security Report (Weak/Reused/Old Passwords) | Use Case 2 (14 pts) | ✓ | ✓ |
| FR-05 | User Registration & Authentication | Common Features (6 pts) | ✓ | ✓ |
| FR-06 | Master Password Protection | Common Features (6 pts) | ✓ | ✓ |
| FR-07 | Chrome Extension Autofill | Common Features (6 pts) | ✓ | ✓ |
| FR-08 | Encrypted Backup & Restore | Common Features | ✓ | ✓ |
| FR-09 | Audit Logging | Common Features | ✓ | ✓ |

#### Non-Functional Requirements

| Category | Requirement | Target |
|----------|-------------|--------|
| Security | Password Encryption | AES Encryption at Rest |
| Security | Authentication | Multi-Factor with OTP |
| Security | Master Password | Required for Sensitive Operations |
| Performance | API Response | < 500ms |
| Availability | Uptime (P1) | 99% |
| Tracing | Distributed Tracing | Zipkin (Phase 2) |

---

### SLIDE 3: Assumptions & Risks (1 minute)

#### Assumptions

| # | Assumption |
|---|------------|
| A-01 | MySQL database available (local + AWS RDS) |
| A-02 | AWS Free Tier resources accessible for Phase 1 |
| A-03 | Chrome browser for extension testing |
| A-04 | Team has GitHub repository access |
| A-05 | Docker environment available for Phase 2 microservices |
| A-06 | Users will set a strong master password |

#### Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| Encryption key management | Critical | Secure key storage, rotation policies |
| Chrome extension security | High | Content Security Policy, limited permissions |
| Microservices complexity | Medium | Docker Compose orchestration, health checks |
| Insufficient test coverage | High | Unit tests, integration tests with Mockito |
| AWS deployment issues | High | Practice deployments, local Docker fallback |

---

### SLIDE 4: Solution Architecture Overview (2 minutes)

#### Phase 1: Monolithic Architecture

```
┌────────────────────────────────────────────────────────────┐
│                  ANGULAR FRONTEND                           │
│              (Components, Services, Guards)                 │
│                    Port: 4200                               │
└─────────────────────────┬──────────────────────────────────┘
                          │ HTTP/REST
┌─────────────────────────┴──────────────────────────────────┐
│                  CHROME EXTENSION MVP                       │
│       (popup.js, content.js, background.js)                 │
│              Domain-based Credential Matching               │
└─────────────────────────┬──────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────┐
│               SPRING BOOT MONOLITH (Port 8084)              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ Controllers │→ │  Services   │→ │ JPA Repositories    │ │
│  │ Auth, Vault │  │ Vault, Gen  │  │                     │ │
│  │ Generator   │  │ Backup      │  │                     │ │
│  │ Backup,Audit│  │ Dashboard   │  │                     │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │         UTILITIES: EncryptionUtil + FileUtil         │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │   Security: Spring Security + MFA (OTP Simulation)   │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────┬──────────────────────────────────┘
                          │ JDBC/JPA
┌─────────────────────────┴──────────────────────────────────┐
│                    MySQL DATABASE                           │
│                      (vault_db)                             │
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
│           Request Routing + Load Balancing                  │
└────────┬──────────┬──────────┬──────────┬──────────────────┘
         │          │          │          │
    ┌────┴────┐ ┌───┴───┐ ┌────┴────┐ ┌───┴────┐
    │  Auth   │ │ Vault │ │Security │ │ Backup │
    │ Service │ │Service│ │ Service │ │Service │
    │  8081   │ │ 8082  │ │  8083   │ │  8084  │
    └────┬────┘ └───┬───┘ └────┬────┘ └───┬────┘
         │          │          │          │
         └──────────┴──────────┴──────────┘
                          │
         ┌────────────────┼────────────────┐
         │                │                │
┌────────┴────────┐ ┌─────┴─────┐ ┌────────┴────────┐
│ Discovery Server│ │  Config   │ │     Zipkin      │
│  Eureka (8761)  │ │  Server   │ │  Tracing (9411) │
│                 │ │  (8888)   │ │                 │
└─────────────────┘ └───────────┘ └─────────────────┘
                          │
              ┌───────────┴───────────┐
              │   MySQL Database(s)   │
              └───────────────────────┘
```

#### Service Dependencies (Phase 2)

```
Config Server (8888) ─── starts first
       │
       ▼
Discovery Server (8761) ─── depends on config
       │
       ▼
API Gateway (8080) ─── registers with Eureka
       │
       ├──► Auth Service (8081)
       │           │
       │           ▼
       ├──► Vault Service (8082) ─── depends on Auth
       │           │
       │           ▼
       ├──► Security Service (8083) ─── depends on Vault
       │           │
       │           ▼
       └──► Backup Service (8084) ─── depends on Vault
```

---

## CORE FEATURES SECTION (40 Points)

---

### SLIDE 5: Main Use Case 1 - Vault Management Design (20 Points) (2 minutes)

**Use Case:** Secure Password Vault Management

#### User Stories

> As a **User**, I want to securely store my account credentials so that I can access them when needed with master password protection.

> As a **User**, I want to search, filter, and organize my vault entries so that I can quickly find the credentials I need.

> As a **User**, I want the Chrome extension to autofill my credentials so that I don't have to manually copy-paste passwords.

#### Feature Flow

```
┌─────────────┐   ┌──────────────┐   ┌───────────────┐   ┌─────────────┐
│    Login    │──►│ Master Pass  │──►│ Vault Access  │──►│ CRUD Entry  │
│   (MFA)     │   │ Verification │   │  Granted      │   │ Operations  │
└─────────────┘   └──────────────┘   └───────────────┘   └─────────────┘
                                            │
                                            ▼
                                     ┌─────────────┐
                                     │ Chrome Ext  │
                                     │  Autofill   │
                                     └─────────────┘
```

#### API Endpoints for Vault Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/vault | Create new vault entry |
| GET | /api/vault | List all vault entries |
| GET | /api/vault/{id} | Get entry details (requires master password) |
| PUT | /api/vault/{id} | Update vault entry |
| DELETE | /api/vault/{id} | Delete vault entry |
| GET | /api/vault/search?q={query} | Search vault entries |
| GET | /api/vault/by-domain?domain={domain} | Get credentials for Chrome extension |
| POST | /api/vault/{id}/favorite | Toggle favorite status |

#### Implementation Highlights

- **Encryption at Rest:** All passwords encrypted using AES before database storage
- **Master Password:** Required to view actual password values
- **Domain Matching:** Chrome extension matches credentials by website domain
- **Favorites & Categories:** Organize vault entries for quick access
- **Search & Filter:** Full-text search across titles, usernames, websites

---

### SLIDE 6: Main Use Case 1 - Vault Management DEMO (3-4 minutes)

**Live Demo Script:**

| Step | Action | Expected Result | Points Demonstrated |
|------|--------|-----------------|---------------------|
| 1 | Login with MFA | Dashboard loads with vault summary | Authentication, MFA |
| 2 | View Dashboard | Statistics: total entries, weak passwords | Dashboard API |
| 3 | Add New Vault Entry | Form: title, username, password, website, category | CRUD Create |
| 4 | Use Password Generator | Generate strong password with options | Generator Integration |
| 5 | Save Entry | Success message, entry appears in list | Validation, Persistence |
| 6 | Search Vault | Find entry by title/username | Search Feature |
| 7 | Mark as Favorite | Star icon toggles | Favorites Feature |
| 8 | View Password | Enter master password, password revealed | Security Verification |
| 9 | Chrome Extension Demo | Visit demo login page, autofill credentials | Extension Integration |
| 10 | Edit & Delete Entry | Update fields, delete with confirmation | CRUD Update/Delete |

**Backup:** Screenshots/video recording prepared for demo failure scenarios

---

### SLIDE 7: Use Case 2 - Password Generator & Security Report Design (14 Points) (1-2 minutes)

**Use Case:** Password Generation & Security Analysis

#### User Stories

> As a **User**, I want to generate strong, random passwords with customizable parameters so that my accounts are secure.

> As a **User**, I want to see a security report identifying weak, reused, and old passwords so that I can improve my security posture.

#### Feature Components

```
┌─────────────────────────────────────────────────────────────────┐
│                  PASSWORD GENERATOR FLOW                         │
│                                                                  │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│   │  Configure   │───►│   Generate   │───►│  Copy/Use in     │  │
│   │  Parameters  │    │   Password   │    │  Vault Entry     │  │
│   └──────────────┘    └──────────────┘    └──────────────────┘  │
│                                                                  │
│   Parameters:                                                    │
│   • Length (8-128 characters)                                    │
│   • Include Uppercase (A-Z)                                      │
│   • Include Lowercase (a-z)                                      │
│   • Include Numbers (0-9)                                        │
│   • Include Special Characters (!@#$%...)                        │
│   • Batch Generation (multiple passwords)                        │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    SECURITY REPORT FLOW                          │
│                                                                  │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│   │  Analyze     │───►│  Categorize  │───►│  Action Items    │  │
│   │  All Vault   │    │  Weak/Reused │    │  & Suggestions   │  │
│   └──────────────┘    └──────────────┘    └──────────────────┘  │
│                                                                  │
│   Security Checks:                                               │
│   • Weak Passwords (short, common patterns)                      │
│   • Reused Passwords (same password multiple sites)              │
│   • Old Passwords (not changed in X days)                        │
│   • Strength Score (0-100)                                       │
└─────────────────────────────────────────────────────────────────┘
```

#### API Endpoints for Generator & Security

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/generator/generate | Generate password with parameters |
| POST | /api/generator/strength | Check password strength |
| GET | /api/dashboard | Get vault statistics |
| GET | /api/dashboard/security-report | Get security analysis |
| GET | /api/dashboard/weak-passwords | List weak passwords |
| GET | /api/dashboard/reused-passwords | List reused passwords |

---

### SLIDE 8: Use Case 2 - Generator & Security DEMO (2-3 minutes)

**Live Demo Script:**

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Open Password Generator | Generator UI with options |
| 2 | Set Length to 16 | Length slider/input updates |
| 3 | Enable all character types | Checkboxes selected |
| 4 | Generate Password | Strong random password displayed |
| 5 | Copy to Clipboard | Copied notification |
| 6 | Check Strength | Strength meter shows "Strong" |
| 7 | Open Security Report | Dashboard with analysis |
| 8 | View Weak Passwords | List with suggestions to improve |
| 9 | View Reused Passwords | Grouped by password hash |
| 10 | Click to Update | Navigate to vault entry for update |

---

### SLIDE 9: Common Features (6 Points) (1 minute)

#### Authentication Flow

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│  Register    │───►│    Login     │───►│   MFA/OTP    │───►│   Session    │
│  (email,pass)│    │  (validate)  │    │ Verification │    │   Created    │
└──────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
```

#### Common Features Implemented

| Feature | Implementation | Status |
|---------|---------------|--------|
| User Registration | Spring Security + Email validation | ✓ |
| User Login | Session-based authentication | ✓ |
| Multi-Factor Auth | OTP simulation (dev mode) | ✓ |
| Master Password | Required for password reveal | ✓ |
| Chrome Extension | Domain-based autofill | ✓ |
| Encrypted Backup | AES encrypted export/import | ✓ |
| Backup Restore | Upload and decrypt backup file | ✓ |
| Audit Logging | All sensitive actions logged | ✓ |
| Input Validation | Bean Validation (@Valid) | ✓ |
| Error Handling | GlobalExceptionHandler | ✓ |

#### Chrome Extension Integration

```
┌─────────────────────────────────────────────────────────────────┐
│                    CHROME EXTENSION FLOW                         │
│                                                                  │
│  1. User visits login page (e.g., facebook.com)                 │
│  2. Extension extracts domain from current URL                  │
│  3. Calls GET /api/vault/by-domain?domain=facebook.com          │
│  4. User clicks "Fill" in popup                                 │
│  5. content.js injects credentials into form fields             │
└─────────────────────────────────────────────────────────────────┘
```

**Quick Demo:** Register → Login with OTP → View Vault → Use Chrome Extension

---

## TECHNICAL STANDARDS SECTION (35 Points)

---

### SLIDE 10: Code Organization (10 Points) (2 minutes)

#### Backend Package Structure (Phase 1 - Monolith)

```
com.passwordmanager/
├── controller/              # REST Controllers
│   ├── AuthController.java
│   ├── VaultController.java
│   ├── GeneratorController.java
│   ├── BackupController.java
│   ├── DashboardController.java
│   └── AuditController.java
├── service/                 # Business Logic Layer
│   ├── AuthService.java
│   ├── VaultService.java
│   ├── GeneratorService.java
│   ├── BackupServiceImpl.java
│   ├── DashboardServiceImpl.java
│   └── AuditServiceImpl.java
├── repository/              # Data Access Layer (JPA)
│   ├── UserRepository.java
│   ├── VaultEntryRepository.java
│   ├── BackupFileRepository.java
│   └── AuditLogRepository.java
├── entity/                  # JPA Entities
│   ├── User.java
│   ├── VaultEntry.java
│   ├── BackupFile.java
│   └── AuditLog.java
├── dto/                     # Data Transfer Objects
│   ├── request/
│   └── response/
├── config/                  # Configuration Classes
│   └── SecurityConfig.java
├── util/                    # Utility Classes
│   ├── EncryptionUtil.java
│   └── FileUtil.java
└── exception/               # Exception Handling
    └── GlobalExceptionHandler.java
```

#### Phase 2 Microservices Structure

```
RevPasswordManager-p3/
├── api-gateway/             # Request routing (Port 8080)
│   └── src/main/java/
├── config-server/           # Centralized config (Port 8888)
│   └── src/main/java/
├── discovery-server/        # Eureka registry (Port 8761)
│   └── src/main/java/
├── auth-service/            # Authentication (Port 8081)
│   └── src/main/java/
├── vault-service/           # Vault CRUD (Port 8082)
│   └── src/main/java/
├── security-service/        # Generator/Reports (Port 8083)
│   └── src/main/java/
├── backup-service/          # Backup operations (Port 8084)
│   └── src/main/java/
├── docker-compose.yml       # Container orchestration
└── pom.xml                  # Parent POM
```

#### Frontend Structure

```
src/app/
├── core/                    # Core module (singleton services)
├── shared/                  # Shared components
├── features/                # Feature modules
│   ├── auth/                # Login, Register
│   ├── vault/               # Vault management
│   ├── generator/           # Password generator
│   ├── dashboard/           # Dashboard & reports
│   └── backup/              # Backup management
├── services/                # API services
├── guards/                  # Route guards
└── models/                  # TypeScript interfaces
```

#### Chrome Extension Structure

```
chrome-extension-mvp/
├── manifest.json            # Extension config & permissions
├── popup.html               # Extension popup UI
├── popup.js                 # Popup interaction logic
├── background.js            # Service worker
├── content.js               # Form injection script
├── demo-login.html          # Local test page
└── README.md                # Documentation
```

#### Design Patterns Used

| Pattern | Where Used | Benefit |
|---------|-----------|---------|
| Repository Pattern | Data access layer | Abstraction, testability |
| Service Layer | Business logic | Separation of concerns |
| DTO Pattern | API communication | Decouple entities from API |
| Utility Pattern | EncryptionUtil, FileUtil | Reusable security functions |
| Service Discovery | Eureka (Phase 2) | Dynamic service location |
| API Gateway | Single entry point (Phase 2) | Routing, load balancing |
| Config Server | Centralized config (Phase 2) | Environment management |

---

### SLIDE 11: Database & Security (15 Points) (2-3 minutes)

#### Entity Relationship Diagram (ERD)

```
┌─────────────────┐
│      USER       │
├─────────────────┤
│ PK id (BIGINT)  │
│    email (UK)   │
│    password     │
│    name         │
│    phone        │
│    mfa_enabled  │
│    created_at   │
└────────┬────────┘
         │
         │ 1:N
         ▼
┌─────────────────────┐
│    VAULT_ENTRY      │
├─────────────────────┤
│ PK id (BIGINT)      │
│ FK user_id          │
│    title            │
│    username         │
│    password (ENC)   │──── Encrypted with AES
│    website          │
│    category         │
│    is_favorite      │
│    created_at       │
│    updated_at       │
└─────────────────────┘

┌─────────────────────┐          ┌─────────────────────┐
│     AUDIT_LOG       │          │    BACKUP_FILE      │
├─────────────────────┤          ├─────────────────────┤
│ PK id (BIGINT)      │          │ PK id (BIGINT)      │
│ FK user_id          │          │ FK user_id          │
│    action           │          │    file_name        │
│    entity_type      │          │    encrypted_content│
│    entity_id        │          │    checksum         │
│    ip_address       │          │    created_at       │
│    status           │          │                     │
│    timestamp        │          │                     │
└─────────────────────┘          └─────────────────────┘
```

#### Security Implementation

| Security Layer | Implementation | Details |
|----------------|---------------|---------|
| **Authentication** | Spring Security | Session-based auth |
| **Multi-Factor** | OTP Verification | Simulated in dev mode |
| **Password Storage** | BCrypt | User passwords hashed |
| **Vault Encryption** | AES | Vault entries encrypted at rest |
| **Master Password** | Re-verification | Required for password reveal |
| **Backup Encryption** | AES + Checksum | Integrity verification |
| **Input Validation** | Bean Validation | @NotNull, @Size, @Email |
| **SQL Injection** | JPA/Hibernate | Parameterized queries |
| **Audit Trail** | AuditService | All sensitive actions logged |

#### Security Flow

```
Request → CORS Filter → Security Filter → Authentication
    → Master Password Check (if needed) → Controller
    → AuditService.log() → Response
```

#### Encryption Utilities

```java
// EncryptionUtil - Key functions
encrypt(plainText, key)    → Encrypted string
decrypt(cipherText, key)   → Original string
generateKey()              → Secure random key
hash(password)             → BCrypt hash
```

---

### SLIDE 12: UX Design (10 Points) (1-2 minutes)

#### UI/UX Highlights

| Aspect | Implementation |
|--------|---------------|
| **Responsive Design** | Angular Material / Bootstrap components |
| **Password Visibility** | Eye icon toggle for password fields |
| **Strength Indicator** | Visual meter for password strength |
| **Copy to Clipboard** | One-click copy with feedback |
| **Search** | Real-time search filtering |
| **Favorites** | Quick access star toggle |
| **Categories** | Organize by account type |
| **Chrome Extension** | Minimal popup, one-click fill |

#### Key Screens

```
┌─────────────────────────────────────────────────────────────────┐
│  [Login Page]                    [Dashboard]                    │
│                                                                 │
│  • Email/password form           • Total vault entries count    │
│  • MFA code input                • Weak passwords warning       │
│  • Register link                 • Security score overview      │
│  • Forgot password               • Quick actions                │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Vault List]                    [Password Generator]           │
│                                                                 │
│  • Search bar                    • Length slider (8-128)        │
│  • Category filter               • Character type toggles       │
│  • Favorites section             • Generated password display   │
│  • Entry cards with actions      • Strength indicator           │
│  • Add new button                • Copy button                  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│  [Chrome Extension Popup]                                       │
│                                                                 │
│  • Current domain display        • Matching credentials list    │
│  • "Fill" button per entry       • Link to full web app         │
│  • Login status indicator                                       │
└─────────────────────────────────────────────────────────────────┘
```

#### User Flow Optimization

- One-click password generation and copy
- Master password cached for session (configurable timeout)
- Keyboard shortcuts for common actions
- Clear visual feedback for all operations
- Confirmation dialogs for destructive actions

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
| Manual Testing | Functional Scenarios | All features | ✓ |

#### Test Classes Structure

```
backend/src/test/java/
├── service/
│   ├── VaultServiceTest.java
│   ├── GeneratorServiceTest.java
│   ├── BackupServiceTest.java
│   └── AuditServiceTest.java
├── controller/
│   ├── VaultControllerTest.java
│   ├── GeneratorControllerTest.java
│   └── BackupControllerTest.java
└── util/
    └── EncryptionUtilTest.java
```

#### Sample Test Case

```java
@Test
void generatePassword_WithAllOptions_ReturnsStrongPassword() {
    // Arrange
    GeneratorRequest request = GeneratorRequest.builder()
        .length(16)
        .includeUppercase(true)
        .includeLowercase(true)
        .includeNumbers(true)
        .includeSpecial(true)
        .build();

    // Act
    String password = generatorService.generate(request);

    // Assert
    assertNotNull(password);
    assertEquals(16, password.length());
    assertTrue(containsUppercase(password));
    assertTrue(containsLowercase(password));
    assertTrue(containsNumber(password));
    assertTrue(containsSpecial(password));
}

@Test
void encryptAndDecrypt_RoundTrip_ReturnsOriginal() {
    // Arrange
    String original = "MySecretPassword123!";
    String key = EncryptionUtil.generateKey();

    // Act
    String encrypted = EncryptionUtil.encrypt(original, key);
    String decrypted = EncryptionUtil.decrypt(encrypted, key);

    // Assert
    assertNotEquals(original, encrypted);
    assertEquals(original, decrypted);
}
```

#### Manual Test Scenarios

| # | Scenario | Expected Result |
|---|----------|-----------------|
| 1 | Register new user | Account created, login enabled |
| 2 | Login with MFA | OTP verified, dashboard displayed |
| 3 | Add vault entry | Entry saved, appears in list |
| 4 | Generate password | Random password with selected criteria |
| 5 | View password (master required) | Password revealed after verification |
| 6 | Chrome extension autofill | Credentials filled in login form |
| 7 | Create encrypted backup | Download file with checksum |
| 8 | Restore from backup | Entries restored successfully |
| 9 | View audit log | All actions logged with timestamps |
| 10 | Security report | Weak/reused passwords identified |

---

### SLIDE 14: Logging (5 Points) (1 minute)

#### Logging Implementation

| Log Level | Usage | Example |
|-----------|-------|---------|
| INFO | Normal operations | User login, vault entry created |
| DEBUG | Development details | Encryption/decryption steps |
| WARN | Potential issues | Failed login attempt, weak password |
| ERROR | Failures | Encryption failure, database error |

#### Audit Logging Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    AUDIT LOG SYSTEM                              │
│                                                                  │
│   User Action → Controller → AuditService.log() → Database     │
│                                                                  │
│   Logged Information:                                            │
│   • Action type (LOGIN, VIEW_PASSWORD, CREATE_ENTRY, etc.)      │
│   • User ID                                                      │
│   • Entity type and ID                                           │
│   • IP address                                                   │
│   • Timestamp                                                    │
│   • Status (SUCCESS/FAILURE)                                     │
└─────────────────────────────────────────────────────────────────┘
```

#### Audit Log Table

```
┌─────────────────────────────────────────────────────────────────┐
│ AUDIT_LOG                                                        │
├─────────────────────────────────────────────────────────────────┤
│ 2024-03-06 10:15:23 | LOGIN           | user@email.com | SUCCESS│
│ 2024-03-06 10:16:45 | CREATE_ENTRY    | VaultEntry:42  | SUCCESS│
│ 2024-03-06 10:17:30 | VIEW_PASSWORD   | VaultEntry:42  | SUCCESS│
│ 2024-03-06 10:18:00 | GENERATE_PASSWD | length:16      | SUCCESS│
│ 2024-03-06 10:20:15 | CREATE_BACKUP   | backup_v1.enc  | SUCCESS│
└─────────────────────────────────────────────────────────────────┘
```

#### Log Demo Points

- Show audit log list in UI
- Filter by action type
- Filter by date range
- Show security-related events

---

### SLIDE 15: Deliverables (5 Points) (1 minute)

#### Project Deliverables Checklist

| Deliverable | Location | Status |
|-------------|----------|--------|
| **Source Code** | GitHub Repository | ✓ |
| **README.md** | Root directory | ✓ |
| **ERD Documentation** | backend/docs/ERD.md | ✓ |
| **Architecture Diagram** | backend/docs/ARCHITECTURE.md | ✓ |
| **Chrome Extension** | chrome-extension-mvp/ | ✓ |
| **Docker Compose (P2)** | docker-compose.yml | ✓ |
| **Setup Instructions** | README.md | ✓ |

#### Repository Structure

```
password-manager-p2/                    RevPasswordManager-p3/
├── README.md                           ├── README.md
├── backend/                            ├── api-gateway/
│   ├── pom.xml                         ├── config-server/
│   ├── src/main/java/                  ├── discovery-server/
│   ├── src/main/resources/             ├── auth-service/
│   ├── src/test/java/                  ├── vault-service/
│   └── docs/                           ├── security-service/
│       ├── ERD.md                      ├── backup-service/
│       └── ARCHITECTURE.md             ├── docker-compose.yml
├── frontend/                           └── pom.xml
│   ├── package.json
│   ├── angular.json
│   └── src/app/
└── chrome-extension-mvp/
    ├── manifest.json
    ├── popup.html/js
    ├── content.js
    └── background.js
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
│  │  │  │Spring Boot │  │◄────►│  vault_db              │  │  │
│  │  │  │   :8084    │  │ JDBC │  (Private Subnet)      │  │  │
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
│  ┌────────────────────────────────────────────────────────┐  │
│  │              password-manager-network                   │  │
│  │                                                         │  │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐ │  │
│  │  │config-server│  │ discovery   │  │   api-gateway   │ │  │
│  │  │    :8888    │  │   :8761     │  │     :8080       │ │  │
│  │  └──────┬──────┘  └──────┬──────┘  └────────┬────────┘ │  │
│  │         │                │                   │          │  │
│  │         └────────────────┼───────────────────┘          │  │
│  │                          │                              │  │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐ │  │
│  │  │auth-service │  │vault-service│  │security-service │ │  │
│  │  │    :8081    │  │    :8082    │  │     :8083       │ │  │
│  │  └─────────────┘  └─────────────┘  └─────────────────┘ │  │
│  │                                                         │  │
│  │  ┌─────────────┐  ┌─────────────┐                      │  │
│  │  │backup-service│  │   zipkin    │                     │  │
│  │  │    :8084    │  │    :9411    │                      │  │
│  │  └─────────────┘  └─────────────┘                      │  │
│  │                                                         │  │
│  └────────────────────────────────────────────────────────┘  │
│                          │                                    │
│                    MySQL :3306                                │
└──────────────────────────────────────────────────────────────┘
```

#### Docker Compose Commands

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Rebuild specific service
docker-compose up -d --build vault-service
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
         └──► feature/vault-management─┘
         └──► feature/password-generator
         └──► feature/chrome-extension─┘
         └──► feature/backup-restore───┘
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
# Prerequisites: Java 17, Maven, Node.js 18+, MySQL 8

# Database Setup
mysql -u root -p
CREATE DATABASE vault_db;

# Set environment variables
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=vault_db
export DB_USER=root
export DB_PASSWORD=root

# Backend (Port 8084)
cd backend && mvn spring-boot:run

# Frontend (Port 4200)
cd frontend && npm install && npm start

# Chrome Extension
# Load unpacked extension from chrome-extension-mvp/ in chrome://extensions
```

---

## INDIVIDUAL CONTRIBUTIONS SECTION (Each member: 1-2 minutes)

---

### SLIDE 18: Namratha - Contributions

**Feature Ownership:** Register & Login, Chrome Extension, Security

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Auth Controller, Auth Service | AuthController.java, AuthService.java |
| Backend | MFA/OTP Implementation | OtpService.java, SecurityConfig.java |
| Frontend | Login Component, Register Component | auth/ module |
| Extension | Chrome Extension Core | popup.js, background.js |
| Security | Spring Security Configuration | SecurityConfig.java |

##### Code Highlight

```java
// MFA Verification Flow
public boolean verifyOtp(String email, String otpCode) {
    OtpRecord record = otpRepository.findByEmail(email)
        .orElseThrow(() -> new InvalidOtpException("OTP not found"));

    if (record.isExpired()) {
        otpRepository.delete(record);
        throw new InvalidOtpException("OTP has expired");
    }

    if (!record.getCode().equals(otpCode)) {
        record.incrementAttempts();
        if (record.getAttempts() >= MAX_ATTEMPTS) {
            otpRepository.delete(record);
            throw new InvalidOtpException("Too many failed attempts");
        }
        throw new InvalidOtpException("Invalid OTP code");
    }

    otpRepository.delete(record);
    return true;
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

### SLIDE 19: Avila - Contributions

**Feature Ownership:** Vault Management, Chrome Extension

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Vault Controller, Vault Service | VaultController.java, VaultService.java |
| Backend | Vault Entry Repository | VaultEntryRepository.java |
| Frontend | Vault List, Vault Entry Form | vault/ module components |
| Extension | Content Script, Domain Matching | content.js, popup.js |
| Database | VaultEntry Entity | VaultEntry.java |

##### Code Highlight

```java
// Domain-based credential matching for Chrome Extension
@GetMapping("/by-domain")
public ResponseEntity<List<VaultEntryResponse>> getByDomain(
        @RequestParam String domain,
        @AuthenticationPrincipal UserDetails user) {

    // Extract base domain (e.g., "facebook.com" from "www.facebook.com")
    String baseDomain = extractBaseDomain(domain);

    List<VaultEntry> entries = vaultRepository
        .findByUserIdAndWebsiteContaining(
            getUserId(user),
            baseDomain
        );

    return ResponseEntity.ok(
        entries.stream()
            .map(this::toResponse)
            .collect(Collectors.toList())
    );
}
```

```javascript
// content.js - Form field injection
function fillCredentials(username, password) {
    const usernameFields = document.querySelectorAll(
        'input[type="text"], input[type="email"], input[name*="user"], input[name*="email"]'
    );
    const passwordFields = document.querySelectorAll('input[type="password"]');

    if (usernameFields.length > 0) {
        usernameFields[0].value = username;
        usernameFields[0].dispatchEvent(new Event('input', { bubbles: true }));
    }
    if (passwordFields.length > 0) {
        passwordFields[0].value = password;
        passwordFields[0].dispatchEvent(new Event('input', { bubbles: true }));
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

### SLIDE 20: Keerthana - Contributions

**Feature Ownership:** Password Generator, Security Report, AWS Deployment

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Generator Controller, Dashboard Controller | GeneratorController.java, DashboardController.java |
| Backend | Generator Service, Dashboard Service | GeneratorService.java, DashboardServiceImpl.java |
| Frontend | Generator Component, Security Report | generator/, dashboard/ modules |
| DevOps | AWS EC2, RDS Configuration | AWS infrastructure setup |
| DevOps | CI/CD Pipeline | [GitHub Actions if applicable] |

##### Code Highlight

```java
// Password Generator with configurable options
public String generate(GeneratorRequest request) {
    StringBuilder chars = new StringBuilder();

    if (request.isIncludeLowercase()) chars.append(LOWERCASE);
    if (request.isIncludeUppercase()) chars.append(UPPERCASE);
    if (request.isIncludeNumbers()) chars.append(NUMBERS);
    if (request.isIncludeSpecial()) chars.append(SPECIAL);

    if (chars.length() == 0) {
        throw new IllegalArgumentException("At least one character type required");
    }

    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < request.getLength(); i++) {
        int index = random.nextInt(chars.length());
        password.append(chars.charAt(index));
    }

    return password.toString();
}

// Security Report - Identify weak passwords
public SecurityReport generateReport(Long userId) {
    List<VaultEntry> entries = vaultRepository.findByUserId(userId);

    List<VaultEntry> weak = entries.stream()
        .filter(e -> calculateStrength(e.getPassword()) < 50)
        .collect(Collectors.toList());

    Map<String, List<VaultEntry>> reused = entries.stream()
        .collect(Collectors.groupingBy(
            e -> hash(e.getPassword())
        ))
        .entrySet().stream()
        .filter(e -> e.getValue().size() > 1)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return new SecurityReport(weak, reused, calculateOverallScore(entries));
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

### SLIDE 21: Akhila - Contributions

**Feature Ownership:** Backup Operations

##### My Contributions

| Layer | What I Built | Files/Components |
|-------|--------------|------------------|
| Backend | Backup Controller, Backup Service | BackupController.java, BackupServiceImpl.java |
| Backend | Audit Controller, Audit Service | AuditController.java, AuditServiceImpl.java |
| Backend | Encryption Utilities | EncryptionUtil.java, FileUtil.java |
| Frontend | Backup/Restore Component, Audit Log Viewer | backup/, audit/ modules |
| Database | BackupFile, AuditLog Entities | BackupFile.java, AuditLog.java |

##### Code Highlight

```java
// Encrypted Backup Creation with Checksum
public BackupResponse createBackup(Long userId) {
    List<VaultEntry> entries = vaultRepository.findByUserId(userId);

    // Serialize entries to JSON
    String jsonContent = objectMapper.writeValueAsString(entries);

    // Encrypt the content
    String encryptionKey = getOrCreateUserKey(userId);
    String encryptedContent = EncryptionUtil.encrypt(jsonContent, encryptionKey);

    // Generate checksum for integrity verification
    String checksum = generateChecksum(encryptedContent);

    // Save backup record
    BackupFile backup = BackupFile.builder()
        .userId(userId)
        .fileName("backup_" + Instant.now().toEpochMilli() + ".enc")
        .encryptedContent(encryptedContent)
        .checksum(checksum)
        .createdAt(LocalDateTime.now())
        .build();

    backupRepository.save(backup);

    // Log the action
    auditService.log(userId, "CREATE_BACKUP", "BackupFile", backup.getId(), "SUCCESS");

    return new BackupResponse(backup.getId(), backup.getFileName(), checksum);
}

// Restore from Backup with Integrity Check
public RestoreResponse restoreBackup(Long userId, Long backupId) {
    BackupFile backup = backupRepository.findById(backupId)
        .orElseThrow(() -> new NotFoundException("Backup not found"));

    // Verify checksum
    String currentChecksum = generateChecksum(backup.getEncryptedContent());
    if (!currentChecksum.equals(backup.getChecksum())) {
        throw new IntegrityException("Backup file has been tampered with");
    }

    // Decrypt and restore
    String encryptionKey = getOrCreateUserKey(userId);
    String jsonContent = EncryptionUtil.decrypt(backup.getEncryptedContent(), encryptionKey);

    List<VaultEntry> entries = objectMapper.readValue(jsonContent,
        new TypeReference<List<VaultEntry>>() {});

    // Import entries
    int imported = importEntries(userId, entries);

    auditService.log(userId, "RESTORE_BACKUP", "BackupFile", backupId, "SUCCESS");

    return new RestoreResponse(imported, "Restore completed successfully");
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

### SLIDE 22: Team Challenges & Collective Learnings (1 minute)

#### Team-Level Challenges

| Challenge | Impact | How We Solved It Together |
|-----------|--------|---------------------------|
| Encryption key management | Security risk if mishandled | Centralized EncryptionUtil, secure key storage |
| Chrome extension CORS issues | Extension couldn't call backend | Proper CORS configuration, extension manifest |
| Microservices inter-service communication | Service discovery complexity | Eureka registry, API Gateway routing |
| Master password session handling | UX vs Security tradeoff | Configurable timeout, secure session storage |
| [TEAM_CHALLENGE_1] | [IMPACT] | [SOLUTION] |

#### Collective Learnings

| Area | What We Learned as a Team |
|------|---------------------------|
| **Technical** | Full-stack development, encryption best practices, Chrome extension APIs |
| **Architecture** | Monolith to microservices transition, service discovery patterns |
| **Security** | MFA implementation, secure password storage, audit logging |
| **Collaboration** | Git workflow, code reviews, API contract design |
| **DevOps** | Docker containerization, AWS deployment, CI/CD pipelines |

---

### SLIDE 23: Q&A (5-10 minutes)

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                       QUESTIONS?                            │
│                                                             │
│                                                             │
│     GitHub Phase 1:                                         │
│     https://github.com/Avila416/password-manager-p2         │
│                                                             │
│     GitHub Phase 2:                                         │
│     https://github.com/Avila416/RevPasswordManager-p3       │
│                                                             │
│     Live Demo: [AWS_URL]                                    │
│                                                             │
│     Individual GitHub Contributions:                        │
│     • Namratha: [X commits, Y PRs]                          │
│     • Avila: [X commits, Y PRs]                             │
│     • Keerthana: [X commits, Y PRs]                         │
│     • Akhila: [X commits, Y PRs]                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Q&A Tips:**
- Any team member can answer questions about their feature area
- Be honest about limitations (planned features not yet implemented)
- Reference specific code/commits when explaining technical decisions
- Be ready to explain encryption approach and security measures

---

## NOTES FOR PRESENTERS

### Information Not Available (To Be Filled)

The following information could not be determined from the provided repositories and should be filled in by the team:

1. **Batch ID and Presentation Date** - Slide 1
2. **Individual Challenges & Solutions** - Slides 18-21 (each team member should fill their specific challenges)
3. **Individual Key Learnings** - Slides 18-21
4. **Exact Git Commit Counts** - Slide 23
5. **AWS Deployment URL** - Slide 23
6. **Frontend Test Coverage Details** - Slide 13
7. **Additional Team Challenges** - Slide 22

### Planned but Not Yet Implemented Features

Based on the README documentation:
- Security question recovery mechanism
- User profile management interface
- Note attachments for vault entries
- File-based backup import functionality (upload interface)

### Demo Preparation Checklist

- [ ] Database seeded with test vault entries
- [ ] Backend running on port 8084
- [ ] Frontend running on port 4200
- [ ] Chrome extension loaded in browser
- [ ] Demo login page (demo-login.html) accessible
- [ ] AWS deployment active (if applicable)
- [ ] Docker Compose services running (Phase 2)
- [ ] Zipkin tracing UI accessible at :9411 (Phase 2)
- [ ] Backup screenshots/video prepared

### Phase 2 Microservices Ports Reference

| Service | Port | Purpose |
|---------|------|---------|
| Config Server | 8888 | Centralized configuration |
| Discovery Server | 8761 | Eureka service registry |
| API Gateway | 8080 | Request routing |
| Auth Service | 8081 | Authentication |
| Vault Service | 8082 | Vault CRUD operations |
| Security Service | 8083 | Generator, reports |
| Backup Service | 8084 | Backup operations |
| Zipkin | 9411 | Distributed tracing |

### Presentation Tips

1. Each member should practice their 1-2 minute contribution section
2. Prepare to explain encryption approach in detail
3. Have Chrome extension demo ready with test credentials
4. Be ready to show Zipkin traces for Phase 2
5. Know your commit count and key PRs
