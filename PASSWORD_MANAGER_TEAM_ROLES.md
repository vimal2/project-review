# Password Manager - Team Roles & Responsibilities

## Project Overview

**Project Name:** Password Manager
**Team Size:** 4 Developers
**P2 (Monolithic):** Spring Boot 3.2.5, MySQL, JWT Authentication
**P3 (Microservices):** Spring Cloud, Eureka, API Gateway, Feign Clients

---

## Team Members & Focus Areas

| Developer | P2 Focus Area | P3 Microservice Ownership |
|-----------|---------------|---------------------------|
| **Namratha** | Register & Login, Chrome Extension, JWT Security | Auth Service |
| **Avila** | Vault Operations, Chrome Extension | Vault Service |
| **Keerthana** | Password Generation & Security Reports | Security Service |
| **Akhila** | Backup Operations | Backup Service + Audit Service |

---

## P2 (Monolithic) - Roles & Responsibilities

### Namratha - Authentication & Security Lead

**Focus Areas:** User Registration, Login, JWT Security Implementation, Chrome Extension Backend Integration

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | AuthenticationController | `controller/AuthenticationController.java` |
| Service | AuthService (Interface) | `service/AuthService.java` |
| Service | AuthServiceImpl | `service/AuthServiceImpl.java` |
| Entity | User | `entity/User.java` |
| Entity | VerificationCode | `entity/VerificationCode.java` |
| Security | SecurityConfig | `security/SecurityConfig.java` |
| Security | JwtUtil | `security/JwtUtil.java` |
| Security | JwtFilter | `security/JwtFilter.java` |
| Security | CustomUserDetailsService | `security/CustomUserDetailsService.java` |
| Validator | AuthValidator | `validator/AuthValidator.java` |
| Validator | MasterPasswordValidator | `validator/MasterPasswordValidator.java` |
| DTO | RegisterRequestDTO | `dto/RegisterRequestDTO.java` |
| DTO | LoginRequestDTO | `dto/LoginRequestDTO.java` |
| DTO | AuthResponseDTO | `dto/AuthResponseDTO.java` |
| Repository | UserRepository | `repository/UserRepository.java` |
| Repository | VerificationCodeRepository | `repository/VerificationCodeRepository.java` |

**API Endpoints Developed:**
- `POST /auth/register` - User registration
- `POST /auth/login` - User login with JWT token generation
- `POST /auth/logout` - Token blacklisting
- `GET /auth/account` - Get authenticated user profile
- `POST /auth/master-password/setup` - Initialize master password
- `PUT /auth/master-password/change` - Update master password
- `POST /auth/password/forgot/request` - Request password reset
- `POST /auth/password/forgot/reset` - Reset password
- `PUT /auth/2fa/status` - Enable/disable 2FA
- `POST /auth/2fa/request` - Request OTP
- `POST /auth/2fa/verify` - Verify OTP

**Key Contributions:**
- Implemented JWT token generation and validation
- Configured Spring Security filter chain
- Set up CORS for Chrome Extension support
- Implemented two-factor authentication flow
- Created token blacklisting for logout functionality

---

### Avila - Vault Operations Lead

**Focus Areas:** Password Vault CRUD, Password Encryption/Decryption, Chrome Extension Backend Integration

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | VaultController | `controller/VaultController.java` |
| Service | VaultService | `service/VaultService.java` |
| Entity | PasswordEntry | `entity/PasswordEntry.java` |
| Entity | VaultEntry | `entity/VaultEntry.java` |
| Security | EncryptionUtil | `security/EncryptionUtil.java` |
| DTO | PasswordEntryRequestDTO | `dto/PasswordEntryRequestDTO.java` |
| DTO | PasswordEntryResponseDTO | `dto/PasswordEntryResponseDTO.java` |
| DTO | VaultSearchRequest | `dto/VaultSearchRequest.java` |
| Repository | PasswordEntryRepository | `repository/PasswordEntryRepository.java` |
| Repository | VaultEntryRepository | `repository/VaultEntryRepository.java` |

**API Endpoints Developed:**
- `POST /api/vault` - Add new password entry
- `GET /api/vault` - Retrieve all password entries
- `GET /api/vault/{id}` - Get single entry (requires master password)
- `PUT /api/vault/{id}` - Update password entry
- `DELETE /api/vault/{id}` - Delete entry (requires master password)
- `POST /api/vault/{id}/verify` - Verify master password and reveal
- `PUT /api/vault/{id}/favorite` - Mark entry as favorite
- `GET /api/vault/favorites` - Retrieve favorite entries
- `GET /api/vault/by-domain` - Filter by domain name
- `POST /api/vault/search` - Search with filters and sorting

**Key Contributions:**
- Implemented AES-256-GCM encryption for password storage
- Created search and filter functionality
- Built domain-based password lookup for Chrome Extension
- Implemented favorites system
- Master password verification for sensitive operations

---

### Keerthana - Security & Reporting Lead

**Focus Areas:** Password Generation, Password Strength Analysis, Security Audit Reports, Dashboard Metrics

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | GeneratorController | `controller/GeneratorController.java` |
| Controller | DashboardController | `controller/DashboardController.java` |
| Service | PasswordGeneratorService | `service/PasswordGeneratorService.java` |
| Service | PasswordStrengthService | `service/PasswordStrengthService.java` |
| Service | DashboardService (Interface) | `service/DashboardService.java` |
| Service | DashboardServiceImpl | `service/impl/DashboardServiceImpl.java` |
| Entity | SecurityAlert | `entity/SecurityAlert.java` |
| Entity | AuditReport | `entity/AuditReport.java` |
| DTO | GeneratePasswordRequest | `dto/GeneratePasswordRequest.java` |
| DTO | PasswordStrengthResponse | `dto/PasswordStrengthResponse.java` |
| DTO | DashboardResponse | `dto/DashboardResponse.java` |
| DTO | SecurityAuditResponse | `dto/SecurityAuditResponse.java` |
| Repository | SecurityAlertRepository | `repository/SecurityAlertRepository.java` |
| Repository | AuditReportRepository | `repository/AuditReportRepository.java` |

**API Endpoints Developed:**
- `POST /api/generator/generate` - Generate random passwords
- `GET /api/generator/audit` - Generate security audit report
- `GET /api/generator/audit/alerts` - List security alerts
- `GET /api/generator/audit/passwords-analysis` - Analyze stored passwords
- `DELETE /api/generator/audit/history` - Clear audit history
- `GET /api/dashboard` - Get dashboard summary metrics

**Key Contributions:**
- Built configurable password generator (length, special chars, numbers)
- Implemented password strength scoring algorithm (WEAK/MEDIUM/STRONG/VERY_STRONG)
- Created security audit system detecting:
  - Weak passwords
  - Reused passwords
  - Old/stale passwords
- Built dashboard with aggregated security metrics
- Alert severity classification (LOW/MEDIUM/HIGH)

---

### Akhila - Backup & Audit Lead

**Focus Areas:** Backup/Restore Operations, Audit Logging, Data Export/Import

**Components Owned:**

| Layer | Component | File Path |
|-------|-----------|-----------|
| Controller | BackupController | `controller/BackupController.java` |
| Controller | AuditController | `controller/AuditController.java` |
| Service | BackupService (Interface) | `service/BackupService.java` |
| Service | BackupServiceImpl | `service/impl/BackupServiceImpl.java` |
| Service | AuditService | `service/AuditService.java` |
| Entity | BackupFile | `entity/BackupFile.java` |
| Entity | AuditLog | `entity/AuditLog.java` |
| DTO | BackupExportResponse | `dto/BackupExportResponse.java` |
| DTO | BackupRestoreRequest | `dto/BackupRestoreRequest.java` |
| DTO | AuditLogResponse | `dto/AuditLogResponse.java` |
| Repository | BackupFileRepository | `repository/BackupFileRepository.java` |
| Repository | AuditLogRepository | `repository/AuditLogRepository.java` |
| Util | FileUtil | `util/FileUtil.java` |
| Util | AuditActions | `util/AuditActions.java` |

**API Endpoints Developed:**
- `GET /api/backup/export` - Export encrypted vault as JSON
- `POST /api/backup/restore` - Restore from backup file
- `PUT /api/backup/update` - Update existing backup
- `PATCH /api/backup/validate` - Validate backup integrity (checksum)
- `DELETE /api/backup/delete` - Remove backup record
- `GET /api/backup/latest` - Get latest backup metadata
- `GET /api/audit` - List audit logs with filters (action, status, ip)

**Key Contributions:**
- Implemented encrypted backup export (AES-256-GCM + Base64)
- Built backup restore with integrity validation
- Created checksum verification system
- Implemented comprehensive audit logging
- Built audit log filtering by action, status, and IP address
- Tracked all user actions for compliance

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
     +------------+------------+-----------+------------+
     |            |            |                        |
     v            v            v                        v
+----------+ +----------+ +----------+           +------------+
|   Auth   | |  Vault   | | Security |           |   Backup   |
| Service  | | Service  | | Service  |           |  Service   |
| (8081)   | | (8082)   | | (8083)   |           |  (8084)    |
| Namratha | |  Avila   | | Keerthana|           |  Akhila    |
+----------+ +----------+ +----------+           +------------+
```

### Shared Infrastructure (All 4 Developers)

Each developer contributes equally to the shared infrastructure components:

| Component | Module | Contributions Per Developer |
|-----------|--------|----------------------------|
| **Config Server** | `config-server` | Each adds their service config file |
| **Discovery Server** | `discovery-server` | Shared setup (pair programming) |
| **API Gateway** | `api-gateway` | Each adds routes for their service |
| **Docker Compose** | `docker-compose.yml` | Each adds their service definition |
| **Parent POM** | `pom.xml` | Each adds their module |

---

### Namratha - Auth Service (Port 8081)

**Service Module:** `auth-service`

**Responsibilities:**
1. Create `auth-service` Maven module
2. Migrate authentication components from P2
3. Implement user registration and login
4. Provide JWT token generation/validation
5. Expose user validation endpoint for other services
6. Create Feign client interface for other services to consume

**Module Structure:**
```
auth-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/passwordmanager/auth/
    ├── AuthServiceApplication.java
    ├── controller/
    │   └── AuthController.java
    ├── service/
    │   ├── AuthService.java
    │   └── UserService.java
    ├── entity/
    │   ├── User.java
    │   └── VerificationCode.java
    ├── repository/
    │   ├── UserRepository.java
    │   └── VerificationCodeRepository.java
    ├── security/
    │   ├── SecurityConfig.java
    │   ├── JwtUtil.java
    │   └── JwtFilter.java
    ├── dto/
    │   ├── LoginRequest.java
    │   ├── RegisterRequest.java
    │   ├── AuthResponse.java
    │   └── UserDto.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout`
- `GET /api/auth/me`
- `POST /api/auth/master-password/setup`
- `PUT /api/auth/master-password/change`
- `POST /api/auth/2fa/*`
- `GET /api/users/{id}` (for Feign clients)
- `GET /api/users/exists/{id}` (for Feign clients)

**Infrastructure Contributions:**
- Create `auth-service.yml` in config-server/configurations/
- Add auth-service routes to API Gateway config
- Add auth-service to docker-compose.yml
- Add auth-service module to parent pom.xml

**Commits to Make:**
1. `feat(auth): create auth-service module structure`
2. `feat(auth): migrate user entity and repository`
3. `feat(auth): implement JWT authentication`
4. `feat(auth): add registration and login endpoints`
5. `feat(auth): add 2FA functionality`
6. `feat(auth): expose user endpoints for Feign clients`
7. `feat(config): add auth-service configuration`
8. `feat(gateway): add auth-service routes`
9. `feat(docker): add auth-service to docker-compose`
10. `test(auth): add unit tests for auth service`

---

### Avila - Vault Service (Port 8082)

**Service Module:** `vault-service`

**Responsibilities:**
1. Create `vault-service` Maven module
2. Migrate vault/password entry components from P2
3. Implement password encryption/decryption
4. Create Feign client to Auth Service for user validation
5. Implement circuit breaker for Auth Service calls

**Module Structure:**
```
vault-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/passwordmanager/vault/
    ├── VaultServiceApplication.java
    ├── controller/
    │   └── VaultController.java
    ├── service/
    │   └── VaultService.java
    ├── entity/
    │   └── PasswordEntry.java
    ├── repository/
    │   └── PasswordEntryRepository.java
    ├── client/
    │   ├── AuthServiceClient.java
    │   └── AuthServiceClientFallback.java
    ├── security/
    │   └── EncryptionUtil.java
    ├── dto/
    │   ├── PasswordEntryRequest.java
    │   ├── PasswordEntryResponse.java
    │   └── UserDto.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `POST /api/vault`
- `GET /api/vault`
- `GET /api/vault/{id}`
- `PUT /api/vault/{id}`
- `DELETE /api/vault/{id}`
- `POST /api/vault/{id}/verify`
- `PUT /api/vault/{id}/favorite`
- `GET /api/vault/favorites`
- `GET /api/vault/by-domain`
- `POST /api/vault/search`

**Feign Client (to Auth Service):**
```java
@FeignClient(name = "auth-service", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {
    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable Long id);

    @GetMapping("/api/users/exists/{id}")
    boolean userExists(@PathVariable Long id);
}
```

**Infrastructure Contributions:**
- Create `vault-service.yml` in config-server/configurations/
- Add vault-service routes to API Gateway config
- Add vault-service to docker-compose.yml
- Add vault-service module to parent pom.xml

**Commits to Make:**
1. `feat(vault): create vault-service module structure`
2. `feat(vault): migrate password entry entity and repository`
3. `feat(vault): implement AES encryption utility`
4. `feat(vault): add vault CRUD operations`
5. `feat(vault): create Feign client for auth-service`
6. `feat(vault): implement circuit breaker with fallback`
7. `feat(vault): add search and filter functionality`
8. `feat(config): add vault-service configuration`
9. `feat(gateway): add vault-service routes`
10. `feat(docker): add vault-service to docker-compose`
11. `test(vault): add unit tests for vault service`

---

### Keerthana - Security Service (Port 8083)

**Service Module:** `security-service`

**Responsibilities:**
1. Create `security-service` Maven module
2. Migrate password generator and strength analyzer
3. Implement security audit functionality
4. Create Feign client to Vault Service for password analysis
5. Build dashboard aggregation from multiple services

**Module Structure:**
```
security-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/passwordmanager/security/
    ├── SecurityServiceApplication.java
    ├── controller/
    │   ├── GeneratorController.java
    │   └── DashboardController.java
    ├── service/
    │   ├── PasswordGeneratorService.java
    │   ├── PasswordStrengthService.java
    │   ├── SecurityAuditService.java
    │   └── DashboardService.java
    ├── entity/
    │   ├── SecurityAlert.java
    │   └── AuditReport.java
    ├── repository/
    │   ├── SecurityAlertRepository.java
    │   └── AuditReportRepository.java
    ├── client/
    │   ├── VaultServiceClient.java
    │   └── VaultServiceClientFallback.java
    ├── dto/
    │   ├── GeneratePasswordRequest.java
    │   ├── PasswordStrengthResponse.java
    │   ├── SecurityAuditResponse.java
    │   └── DashboardResponse.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `POST /api/generator/generate`
- `POST /api/generator/strength` (analyze password strength)
- `GET /api/security/audit`
- `GET /api/security/alerts`
- `GET /api/security/passwords-analysis`
- `DELETE /api/security/audit/history`
- `GET /api/dashboard`

**Feign Client (to Vault Service):**
```java
@FeignClient(name = "vault-service", fallback = VaultServiceClientFallback.class)
public interface VaultServiceClient {
    @GetMapping("/api/vault/user/{userId}")
    List<PasswordEntryDto> getUserPasswords(@PathVariable Long userId);

    @GetMapping("/api/vault/count/user/{userId}")
    long getPasswordCount(@PathVariable Long userId);
}
```

**Infrastructure Contributions:**
- Create `security-service.yml` in config-server/configurations/
- Add security-service routes to API Gateway config
- Add security-service to docker-compose.yml
- Add security-service module to parent pom.xml

**Commits to Make:**
1. `feat(security): create security-service module structure`
2. `feat(security): implement password generator`
3. `feat(security): implement password strength analyzer`
4. `feat(security): migrate security alert entity`
5. `feat(security): create Feign client for vault-service`
6. `feat(security): implement security audit functionality`
7. `feat(security): build dashboard aggregation`
8. `feat(config): add security-service configuration`
9. `feat(gateway): add security-service routes`
10. `feat(docker): add security-service to docker-compose`
11. `test(security): add unit tests for security service`

---

### Akhila - Backup Service (Port 8084)

**Service Module:** `backup-service`

**Responsibilities:**
1. Create `backup-service` Maven module
2. Migrate backup/restore functionality
3. Implement audit logging for all services
4. Create Feign clients to Vault Service for backup data
5. Expose audit logging endpoint for other services

**Module Structure:**
```
backup-service/
├── pom.xml
├── Dockerfile
└── src/main/java/com/passwordmanager/backup/
    ├── BackupServiceApplication.java
    ├── controller/
    │   ├── BackupController.java
    │   └── AuditController.java
    ├── service/
    │   ├── BackupService.java
    │   └── AuditLogService.java
    ├── entity/
    │   ├── BackupFile.java
    │   └── AuditLog.java
    ├── repository/
    │   ├── BackupFileRepository.java
    │   └── AuditLogRepository.java
    ├── client/
    │   ├── VaultServiceClient.java
    │   └── VaultServiceClientFallback.java
    ├── dto/
    │   ├── BackupExportResponse.java
    │   ├── BackupRestoreRequest.java
    │   ├── AuditLogRequest.java
    │   └── AuditLogResponse.java
    └── exception/
        └── GlobalExceptionHandler.java
```

**API Endpoints:**
- `GET /api/backup/export`
- `POST /api/backup/restore`
- `PUT /api/backup/update`
- `PATCH /api/backup/validate`
- `DELETE /api/backup/delete`
- `GET /api/backup/latest`
- `GET /api/audit`
- `POST /api/audit/log` (for other services to log actions)

**Feign Client (to Vault Service):**
```java
@FeignClient(name = "vault-service", fallback = VaultServiceClientFallback.class)
public interface VaultServiceClient {
    @GetMapping("/api/vault/user/{userId}/export")
    List<PasswordEntryDto> exportUserVault(@PathVariable Long userId);
}
```

**Infrastructure Contributions:**
- Create `backup-service.yml` in config-server/configurations/
- Add backup-service routes to API Gateway config
- Add backup-service to docker-compose.yml
- Add backup-service module to parent pom.xml

**Commits to Make:**
1. `feat(backup): create backup-service module structure`
2. `feat(backup): migrate backup entity and repository`
3. `feat(backup): implement encrypted backup export`
4. `feat(backup): implement backup restore with validation`
5. `feat(backup): create Feign client for vault-service`
6. `feat(backup): implement checksum verification`
7. `feat(audit): migrate audit log functionality`
8. `feat(audit): expose audit logging endpoint for services`
9. `feat(config): add backup-service configuration`
10. `feat(gateway): add backup-service routes`
11. `feat(docker): add backup-service to docker-compose`
12. `test(backup): add unit tests for backup service`

---

## Shared Infrastructure Setup

### Phase 1: Project Foundation (All 4 Developers - Pair Programming)

**Deliverables:**
1. Create parent `pom.xml` with multi-module structure
2. Set up `config-server` module (copy from microservices-demo)
3. Set up `discovery-server` module (copy from microservices-demo)
4. Set up `api-gateway` module (copy from microservices-demo)
5. Create `docker-compose.yml` with base services
6. Create `.gitignore` and project documentation

**Suggested Pairing:**
- Namratha + Keerthana: Config Server + Discovery Server
- Avila + Akhila: API Gateway + Docker Compose

**Shared Commits:**
1. `chore: initialize multi-module maven project`
2. `feat(infra): add config-server module`
3. `feat(infra): add discovery-server module`
4. `feat(infra): add api-gateway module`
5. `feat(docker): add base docker-compose configuration`
6. `docs: add project README`

### Phase 2: Individual Service Development (Parallel Work)

Each developer creates their service module independently:
- Namratha: auth-service
- Avila: vault-service
- Keerthana: security-service
- Akhila: backup-service

### Phase 3: Integration & Testing (All 4 Developers)

**Deliverables:**
1. Integration testing between services
2. Feign client testing with circuit breakers
3. End-to-end API Gateway routing tests
4. Docker Compose full stack testing
5. Documentation updates

---

## API Gateway Routes Configuration

Each developer adds their service routes to `api-gateway.yml`:

```yaml
spring:
  cloud:
    gateway:
      routes:
        # Namratha's routes
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/api/auth/**, /api/users/**

        # Avila's routes
        - id: vault-service
          uri: lb://vault-service
          predicates:
            - Path=/api/vault/**

        # Keerthana's routes
        - id: security-service
          uri: lb://security-service
          predicates:
            - Path=/api/generator/**, /api/security/**, /api/dashboard/**

        # Akhila's routes
        - id: backup-service
          uri: lb://backup-service
          predicates:
            - Path=/api/backup/**, /api/audit/**
```

---

## Service Communication Matrix

| From Service | To Service | Purpose | Feign Client Owner |
|--------------|------------|---------|-------------------|
| vault-service | auth-service | Validate user exists | Avila |
| security-service | vault-service | Analyze user passwords | Keerthana |
| security-service | auth-service | Get user count | Keerthana |
| backup-service | vault-service | Export vault data | Akhila |
| backup-service | auth-service | Validate user | Akhila |

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
- auth: Auth service
- vault: Vault service
- security: Security service
- backup: Backup service
- audit: Audit functionality
- infra: Infrastructure (config, discovery, gateway)
- docker: Docker/containerization
- config: Configuration files
- gateway: API Gateway
```

**Examples:**
```
feat(auth): implement JWT token generation
feat(vault): add AES-256 encryption for passwords
fix(security): correct password strength scoring algorithm
test(backup): add unit tests for checksum validation
docs(readme): update API documentation
```

---

## Testing Responsibilities

| Developer | Unit Test Scope | Integration Test Scope |
|-----------|-----------------|----------------------|
| Namratha | AuthService, JwtUtil | Auth + Gateway integration |
| Avila | VaultService, EncryptionUtil | Vault + Auth Feign client |
| Keerthana | GeneratorService, StrengthService | Security + Vault Feign client |
| Akhila | BackupService, AuditService | Backup + Vault Feign client |

---

## Summary

### P2 Contribution Summary

| Developer | Controllers | Services | Entities | Endpoints |
|-----------|-------------|----------|----------|-----------|
| Namratha | 1 | 3 | 2 | 11 |
| Avila | 1 | 1 | 2 | 10 |
| Keerthana | 2 | 4 | 2 | 6 |
| Akhila | 2 | 2 | 2 | 7 |

### P3 Module Ownership

| Developer | Service | Port | Dependencies |
|-----------|---------|------|--------------|
| Namratha | auth-service | 8081 | None (base service) |
| Avila | vault-service | 8082 | auth-service |
| Keerthana | security-service | 8083 | vault-service, auth-service |
| Akhila | backup-service | 8084 | vault-service, auth-service |

---

## Next Steps

1. **Set up Git repository** for P3 project
2. **Phase 1**: Infrastructure setup (pair programming, 2-3 days)
3. **Phase 2**: Individual service development (parallel, 1-2 weeks)
4. **Phase 3**: Integration and testing (all hands, 3-4 days)
5. **Documentation** and demo preparation
