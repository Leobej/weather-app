# Base image with JDK 23
FROM openjdk:23-jdk

# Working directory inside the container
WORKDIR /app

# Copy only the final JAR (no source, no build system)
COPY build/libs/*.jar app.jar

# Expose port used by Spring Boot
EXPOSE 8085

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
