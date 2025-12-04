# Base image
FROM openjdk:21-jdk-slim

# Build arguments
ARG JAR_FILE=build/libs/*.jar

# Copy the application JAR
COPY ${JAR_FILE} app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
