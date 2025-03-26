# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the application inside the container
RUN ./mvnw clean package -DskipTests

# Copy the JAR file from the target directory to the container
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
