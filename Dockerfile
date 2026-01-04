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

# Create a non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Add a healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl --fail http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
