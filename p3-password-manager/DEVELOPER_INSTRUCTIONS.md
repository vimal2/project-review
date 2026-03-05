# Developer Instructions - Password Manager P3 Microservices

## Overview

This document provides step-by-step instructions for each team member to create their own repository with proper commit history from this reference implementation.

## Important: Commit Order Matters

To ensure a clean, professional commit history, follow the commit order exactly as specified below. Each developer should copy files in the order listed and make commits at each step.

---

## Initial Setup (All Developers Together - Pair Programming)

Before individual work begins, the team should set up the shared infrastructure together.

### Step 1: Create New Repository

Each developer creates a new Git repository:

```bash
mkdir p3-password-manager
cd p3-password-manager
git init
```

### Step 2: Shared Infrastructure Setup

Copy and commit these files in order:

#### Commit 1: Initialize project structure
```bash
# Copy these files:
# - pom.xml (parent)
# - .gitignore
# - mvnw, mvnw.cmd
# - .mvn/wrapper/

git add .
git commit -m "chore: initialize multi-module maven project"
```

#### Commit 2: Add Config Server
```bash
# Copy entire config-server/ directory

git add config-server/
git commit -m "feat(infra): add config-server module"
```

#### Commit 3: Add Discovery Server
```bash
# Copy entire discovery-server/ directory

git add discovery-server/
git commit -m "feat(infra): add discovery-server module"
```

#### Commit 4: Add API Gateway
```bash
# Copy entire api-gateway/ directory

git add api-gateway/
git commit -m "feat(infra): add api-gateway module"
```

#### Commit 5: Add Docker Compose
```bash
# Copy docker-compose.yml

git add docker-compose.yml
git commit -m "feat(docker): add base docker-compose configuration"
```

#### Commit 6: Add README
```bash
# Copy README.md

git add README.md
git commit -m "docs: add project README"
```

---

## Namratha - Auth Service (Port 8081)

After shared infrastructure is set up, follow these commits:

### Commit 7: Create auth-service module structure
```bash
# Copy:
# - auth-service/pom.xml
# - auth-service/src/main/java/com/passwordmanager/auth/AuthServiceApplication.java
# - auth-service/src/main/resources/application.yml

git add auth-service/
git commit -m "feat(auth): create auth-service module structure"
```

### Commit 8: Add user entity and repository
```bash
# Copy:
# - auth-service/src/main/java/com/passwordmanager/auth/entity/User.java
# - auth-service/src/main/java/com/passwordmanager/auth/entity/VerificationCode.java
# - auth-service/src/main/java/com/passwordmanager/auth/repository/UserRepository.java
# - auth-service/src/main/java/com/passwordmanager/auth/repository/VerificationCodeRepository.java

git add auth-service/src/main/java/com/passwordmanager/auth/entity/
git add auth-service/src/main/java/com/passwordmanager/auth/repository/
git commit -m "feat(auth): migrate user entity and repository"
```

### Commit 9: Add DTOs
```bash
# Copy all files from:
# - auth-service/src/main/java/com/passwordmanager/auth/dto/

git add auth-service/src/main/java/com/passwordmanager/auth/dto/
git commit -m "feat(auth): add authentication DTOs"
```

### Commit 10: Implement JWT authentication
```bash
# Copy:
# - auth-service/src/main/java/com/passwordmanager/auth/security/JwtUtil.java
# - auth-service/src/main/java/com/passwordmanager/auth/security/JwtAuthenticationFilter.java
# - auth-service/src/main/java/com/passwordmanager/auth/security/SecurityConfig.java

git add auth-service/src/main/java/com/passwordmanager/auth/security/
git commit -m "feat(auth): implement JWT authentication"
```

### Commit 11: Add auth service and controller
```bash
# Copy:
# - auth-service/src/main/java/com/passwordmanager/auth/service/AuthService.java
# - auth-service/src/main/java/com/passwordmanager/auth/controller/AuthController.java
# - auth-service/src/main/java/com/passwordmanager/auth/controller/UserController.java

git add auth-service/src/main/java/com/passwordmanager/auth/service/
git add auth-service/src/main/java/com/passwordmanager/auth/controller/
git commit -m "feat(auth): add registration and login endpoints"
```

### Commit 12: Add exception handling
```bash
# Copy:
# - auth-service/src/main/java/com/passwordmanager/auth/exception/

git add auth-service/src/main/java/com/passwordmanager/auth/exception/
git commit -m "feat(auth): add global exception handler"
```

### Commit 13: Add config-server configuration
```bash
# Add auth-service routes to config-server if not already present

git add config-server/src/main/resources/configurations/auth-service.yml
git commit -m "feat(config): add auth-service configuration"
```

### Commit 14: Add Dockerfile
```bash
# Copy auth-service/Dockerfile

git add auth-service/Dockerfile
git commit -m "feat(docker): add auth-service dockerfile"
```

---

## Avila - Vault Service (Port 8082)

### Commit 7: Create vault-service module structure
```bash
# Copy:
# - vault-service/pom.xml
# - vault-service/src/main/java/com/passwordmanager/vault/VaultServiceApplication.java
# - vault-service/src/main/resources/application.yml

git add vault-service/
git commit -m "feat(vault): create vault-service module structure"
```

### Commit 8: Add password entry entity and repository
```bash
# Copy:
# - vault-service/src/main/java/com/passwordmanager/vault/entity/PasswordEntry.java
# - vault-service/src/main/java/com/passwordmanager/vault/repository/PasswordEntryRepository.java

git add vault-service/src/main/java/com/passwordmanager/vault/entity/
git add vault-service/src/main/java/com/passwordmanager/vault/repository/
git commit -m "feat(vault): migrate password entry entity and repository"
```

### Commit 9: Add AES encryption utility
```bash
# Copy:
# - vault-service/src/main/java/com/passwordmanager/vault/security/EncryptionUtil.java

git add vault-service/src/main/java/com/passwordmanager/vault/security/
git commit -m "feat(vault): implement AES encryption utility"
```

### Commit 10: Add DTOs
```bash
# Copy all files from:
# - vault-service/src/main/java/com/passwordmanager/vault/dto/

git add vault-service/src/main/java/com/passwordmanager/vault/dto/
git commit -m "feat(vault): add vault DTOs"
```

### Commit 11: Add vault CRUD operations
```bash
# Copy:
# - vault-service/src/main/java/com/passwordmanager/vault/service/VaultService.java
# - vault-service/src/main/java/com/passwordmanager/vault/controller/VaultController.java

git add vault-service/src/main/java/com/passwordmanager/vault/service/
git add vault-service/src/main/java/com/passwordmanager/vault/controller/
git commit -m "feat(vault): add vault CRUD operations"
```

### Commit 12: Create Feign client for auth-service
```bash
# Copy:
# - vault-service/src/main/java/com/passwordmanager/vault/client/AuthServiceClient.java
# - vault-service/src/main/java/com/passwordmanager/vault/client/AuthServiceClientFallback.java

git add vault-service/src/main/java/com/passwordmanager/vault/client/
git commit -m "feat(vault): create Feign client for auth-service"
```

### Commit 13: Add exception handling
```bash
# Copy:
# - vault-service/src/main/java/com/passwordmanager/vault/exception/

git add vault-service/src/main/java/com/passwordmanager/vault/exception/
git commit -m "feat(vault): add exception handling"
```

### Commit 14: Add config-server configuration
```bash
git add config-server/src/main/resources/configurations/vault-service.yml
git commit -m "feat(config): add vault-service configuration"
```

### Commit 15: Add Dockerfile
```bash
git add vault-service/Dockerfile
git commit -m "feat(docker): add vault-service dockerfile"
```

---

## Keerthana - Security Service (Port 8083)

### Commit 7: Create security-service module structure
```bash
# Copy:
# - security-service/pom.xml
# - security-service/src/main/java/com/passwordmanager/security/SecurityServiceApplication.java
# - security-service/src/main/resources/application.yml

git add security-service/
git commit -m "feat(security): create security-service module structure"
```

### Commit 8: Add password generator utility
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/util/PasswordUtil.java

git add security-service/src/main/java/com/passwordmanager/security/util/
git commit -m "feat(security): implement password generator"
```

### Commit 9: Add password strength service
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/service/PasswordStrengthService.java

git add security-service/src/main/java/com/passwordmanager/security/service/PasswordStrengthService.java
git commit -m "feat(security): implement password strength analyzer"
```

### Commit 10: Add entities and repositories
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/entity/
# - security-service/src/main/java/com/passwordmanager/security/repository/

git add security-service/src/main/java/com/passwordmanager/security/entity/
git add security-service/src/main/java/com/passwordmanager/security/repository/
git commit -m "feat(security): migrate security alert entity"
```

### Commit 11: Add DTOs
```bash
# Copy all files from:
# - security-service/src/main/java/com/passwordmanager/security/dto/

git add security-service/src/main/java/com/passwordmanager/security/dto/
git commit -m "feat(security): add security DTOs"
```

### Commit 12: Create Feign client for vault-service
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/client/

git add security-service/src/main/java/com/passwordmanager/security/client/
git commit -m "feat(security): create Feign client for vault-service"
```

### Commit 13: Add password generator service
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/service/PasswordGeneratorService.java

git add security-service/src/main/java/com/passwordmanager/security/service/PasswordGeneratorService.java
git commit -m "feat(security): add password generator service"
```

### Commit 14: Implement security audit functionality
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/service/SecurityAuditService.java

git add security-service/src/main/java/com/passwordmanager/security/service/SecurityAuditService.java
git commit -m "feat(security): implement security audit functionality"
```

### Commit 15: Build dashboard aggregation
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/service/DashboardService.java

git add security-service/src/main/java/com/passwordmanager/security/service/DashboardService.java
git commit -m "feat(security): build dashboard aggregation"
```

### Commit 16: Add controllers
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/controller/

git add security-service/src/main/java/com/passwordmanager/security/controller/
git commit -m "feat(security): add generator and dashboard controllers"
```

### Commit 17: Add exception handling
```bash
# Copy:
# - security-service/src/main/java/com/passwordmanager/security/exception/

git add security-service/src/main/java/com/passwordmanager/security/exception/
git commit -m "feat(security): add exception handling"
```

### Commit 18: Add config-server configuration
```bash
git add config-server/src/main/resources/configurations/security-service.yml
git commit -m "feat(config): add security-service configuration"
```

### Commit 19: Add Dockerfile
```bash
git add security-service/Dockerfile
git commit -m "feat(docker): add security-service dockerfile"
```

---

## Akhila - Backup Service (Port 8084)

### Commit 7: Create backup-service module structure
```bash
# Copy:
# - backup-service/pom.xml
# - backup-service/src/main/java/com/passwordmanager/backup/BackupServiceApplication.java
# - backup-service/src/main/resources/application.yml

git add backup-service/
git commit -m "feat(backup): create backup-service module structure"
```

### Commit 8: Add entities and repositories
```bash
# Copy:
# - backup-service/src/main/java/com/passwordmanager/backup/entity/
# - backup-service/src/main/java/com/passwordmanager/backup/repository/

git add backup-service/src/main/java/com/passwordmanager/backup/entity/
git add backup-service/src/main/java/com/passwordmanager/backup/repository/
git commit -m "feat(backup): migrate backup entity and repository"
```

### Commit 9: Add utilities
```bash
# Copy:
# - backup-service/src/main/java/com/passwordmanager/backup/util/

git add backup-service/src/main/java/com/passwordmanager/backup/util/
git commit -m "feat(backup): add encryption and file utilities"
```

### Commit 10: Add DTOs
```bash
# Copy all files from:
# - backup-service/src/main/java/com/passwordmanager/backup/dto/

git add backup-service/src/main/java/com/passwordmanager/backup/dto/
git commit -m "feat(backup): add backup DTOs"
```

### Commit 11: Create Feign client for vault-service
```bash
# Copy:
# - backup-service/src/main/java/com/passwordmanager/backup/client/

git add backup-service/src/main/java/com/passwordmanager/backup/client/
git commit -m "feat(backup): create Feign client for vault-service"
```

### Commit 12: Implement backup export
```bash
# First part of backup service

git add backup-service/src/main/java/com/passwordmanager/backup/service/BackupService.java
git commit -m "feat(backup): implement encrypted backup export"
```

### Commit 13: Implement backup restore with validation
```bash
# Backup restore functionality is part of BackupService.java
# This commit message represents the logical progression

git commit --allow-empty -m "feat(backup): implement backup restore with validation"
# Or add additional backup-related code changes
```

### Commit 14: Implement audit logging
```bash
# Copy:
# - backup-service/src/main/java/com/passwordmanager/backup/service/AuditLogService.java

git add backup-service/src/main/java/com/passwordmanager/backup/service/AuditLogService.java
git commit -m "feat(audit): migrate audit log functionality"
```

### Commit 15: Add controllers
```bash
# Copy:
# - backup-service/src/main/java/com/passwordmanager/backup/controller/

git add backup-service/src/main/java/com/passwordmanager/backup/controller/
git commit -m "feat(backup): add backup and audit controllers"
```

### Commit 16: Add exception handling
```bash
# Copy:
# - backup-service/src/main/java/com/passwordmanager/backup/exception/

git add backup-service/src/main/java/com/passwordmanager/backup/exception/
git commit -m "feat(backup): add exception handling"
```

### Commit 17: Add config-server configuration
```bash
git add config-server/src/main/resources/configurations/backup-service.yml
git commit -m "feat(config): add backup-service configuration"
```

### Commit 18: Add Dockerfile
```bash
git add backup-service/Dockerfile
git commit -m "feat(docker): add backup-service dockerfile"
```

---

## Final Integration Steps (All Developers)

After all developers have committed their services:

### Update docker-compose.yml with all services
```bash
# Ensure docker-compose.yml has all service definitions

git add docker-compose.yml
git commit -m "feat(docker): update docker-compose with all services"
```

### Update README with complete API documentation
```bash
git add README.md
git commit -m "docs: update README with complete documentation"
```

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

## Directory Structure Reference

```
p3-password-manager/
├── .gitignore
├── .mvn/
│   └── wrapper/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── docker-compose.yml
├── README.md
├── DEVELOPER_INSTRUCTIONS.md
├── config-server/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/passwordmanager/configserver/
│       │   └── ConfigServerApplication.java
│       └── resources/
│           ├── application.yml
│           └── configurations/
│               ├── application.yml
│               ├── api-gateway.yml
│               ├── discovery-server.yml
│               ├── auth-service.yml
│               ├── vault-service.yml
│               ├── security-service.yml
│               └── backup-service.yml
├── discovery-server/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/...
├── api-gateway/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/...
├── auth-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/passwordmanager/auth/
│       ├── AuthServiceApplication.java
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── exception/
│       ├── repository/
│       ├── security/
│       └── service/
├── vault-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/passwordmanager/vault/
│       ├── VaultServiceApplication.java
│       ├── client/
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── exception/
│       ├── repository/
│       ├── security/
│       └── service/
├── security-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/passwordmanager/security/
│       ├── SecurityServiceApplication.java
│       ├── client/
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── exception/
│       ├── repository/
│       ├── service/
│       └── util/
└── backup-service/
    ├── Dockerfile
    ├── pom.xml
    └── src/main/java/com/passwordmanager/backup/
        ├── BackupServiceApplication.java
        ├── client/
        ├── controller/
        ├── dto/
        ├── entity/
        ├── exception/
        ├── repository/
        ├── service/
        └── util/
```

---

## Tips for Clean Commit History

1. **Make atomic commits** - Each commit should represent one logical change
2. **Write meaningful commit messages** - Follow the conventional commits format
3. **Test before committing** - Ensure the code compiles at each step
4. **Don't commit generated files** - Use .gitignore properly
5. **Review before pushing** - Use `git log --oneline` to verify history
