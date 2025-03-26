# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Give execute permissions to mvnw
RUN chmod +x mvnw

# Build the application and create the JAR
RUN ./mvnw clean package -DskipTests

# Expose the application port
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "target/BlogBackend-0.0.1-SNAPSHOT.jar"]
