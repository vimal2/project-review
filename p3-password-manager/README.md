# Password Manager - Microservices Architecture (P3)

A microservices-based password manager application built with Spring Boot and Spring Cloud.

## Architecture Overview

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

## Services

| Service | Port | Owner | Description |
|---------|------|-------|-------------|
| Config Server | 8888 | Shared | Centralized configuration management |
| Discovery Server | 8761 | Shared | Service discovery (Eureka) |
| API Gateway | 8080 | Shared | Single entry point for all services |
| Auth Service | 8081 | Namratha | Authentication, JWT, User management |
| Vault Service | 8082 | Avila | Password vault CRUD, encryption |
| Security Service | 8083 | Keerthana | Password generation, security audits |
| Backup Service | 8084 | Akhila | Backup/restore, audit logging |

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (for containerized deployment)

## Building the Project

```bash
# Build all modules
./mvnw clean package -DskipTests

# Build specific module
./mvnw clean package -pl auth-service -DskipTests
```

## Running Locally

### Start Order (Important)

1. **Config Server** (wait for startup)
2. **Discovery Server** (wait for startup)
3. **API Gateway**
4. **Auth Service**
5. **Vault Service**
6. **Security Service**
7. **Backup Service**

### Using Maven

```bash
# Terminal 1 - Config Server
cd config-server && ../mvnw spring-boot:run

# Terminal 2 - Discovery Server
cd discovery-server && ../mvnw spring-boot:run

# Terminal 3 - API Gateway
cd api-gateway && ../mvnw spring-boot:run

# Terminal 4 - Auth Service
cd auth-service && ../mvnw spring-boot:run

# Terminal 5 - Vault Service
cd vault-service && ../mvnw spring-boot:run

# Terminal 6 - Security Service
cd security-service && ../mvnw spring-boot:run

# Terminal 7 - Backup Service
cd backup-service && ../mvnw spring-boot:run
```

### Using Docker Compose

```bash
# Build and start all services
docker-compose up --build

# Stop all services
docker-compose down
```

## API Endpoints

All requests go through the API Gateway at `http://localhost:8080`

### Auth Service (Namratha)
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout
- `GET /api/auth/me` - Get current user
- `POST /api/auth/master-password/setup` - Setup master password
- `PUT /api/auth/master-password/change` - Change master password

### Vault Service (Avila)
- `POST /api/vault` - Add password entry
- `GET /api/vault` - List all entries
- `GET /api/vault/{id}` - Get entry by ID
- `PUT /api/vault/{id}` - Update entry
- `DELETE /api/vault/{id}` - Delete entry
- `PUT /api/vault/{id}/favorite` - Toggle favorite
- `GET /api/vault/favorites` - Get favorites
- `POST /api/vault/search` - Search entries

### Security Service (Keerthana)
- `POST /api/generator/generate` - Generate passwords
- `GET /api/generator/audit` - Security audit
- `GET /api/generator/audit/alerts` - Security alerts
- `GET /api/generator/audit/passwords-analysis` - Analyze passwords
- `GET /api/dashboard` - Dashboard metrics

### Backup Service (Akhila)
- `GET /api/backup/export` - Export backup
- `POST /api/backup/restore` - Restore backup
- `PATCH /api/backup/validate` - Validate backup
- `DELETE /api/backup/delete` - Delete backup
- `GET /api/backup/latest` - Latest backup info
- `GET /api/audit` - Audit logs
- `POST /api/audit/log` - Log action

## Monitoring

- **Eureka Dashboard**: http://localhost:8761
- **Zipkin Dashboard**: http://localhost:9411
- **H2 Console**: http://localhost:{port}/h2-console

## Team Members

| Name | Service | Responsibilities |
|------|---------|-----------------|
| Namratha | Auth Service | Authentication, JWT, 2FA |
| Avila | Vault Service | Password CRUD, Encryption |
| Keerthana | Security Service | Password Generation, Audits |
| Akhila | Backup Service | Backup/Restore, Audit Logs |
