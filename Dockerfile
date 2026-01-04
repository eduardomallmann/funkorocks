# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the production image
FROM openjdk:21-jre-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Create a non-root user
RUN addgroup --system --gid 1001 spring && \
    adduser --system spring --uid 1001 --shell /bin/bash

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx256m -Xms128m"

# Expose the port
EXPOSE 8080

# Healthcheck with proper endpoints
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl --fail http://localhost:8080/actuator/health/liveness || exit 1

# Readiness check
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl --fail http://localhost:8080/actuator/health/readiness || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
