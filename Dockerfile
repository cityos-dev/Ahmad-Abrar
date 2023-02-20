# Use a base image that has Java and Maven installed
FROM maven:3.8.4-openjdk-17-slim as build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY ["pom.xml", "."]

# Download the dependencies
RUN mvn dependency:go-offline

# Copy the application source code
COPY ["src/", "./src/"]

# Build the application
RUN mvn package -DskipTests

# Create a new image with Java installed
FROM openjdk:17-slim
  
# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose the default port
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]
