# Base Image
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/*.jar /app/servicediscovery.jar

# Run the service
ENTRYPOINT ["java", "-jar", "/app/servicediscovery.jar"]
