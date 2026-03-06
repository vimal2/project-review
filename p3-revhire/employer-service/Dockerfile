# Multi-stage Docker build for Employer Service

# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user
RUN addgroup -g 1001 spring && adduser -u 1001 -G spring -s /bin/sh -D spring

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Expose the application port
EXPOSE 8083

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8083/api/employer/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Optional: Add JVM options for containerized environment
# ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
