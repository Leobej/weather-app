# Use Java 23 JDK base image
FROM openjdk:23-jdk

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR into the container
COPY build/libs/app.jar app.jar

# Expose port used by Spring Boot app
EXPOSE 8085

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
