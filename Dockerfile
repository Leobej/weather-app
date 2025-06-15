# Use Java 21 JDK image
FROM openjdk:23-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR from host to container
COPY build/libs/*.jar app.jar

# Expose port 8085
EXPOSE 8085

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
