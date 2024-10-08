# First stage: Build the application using Maven
FROM maven:3.8.1-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build the application
COPY . .
RUN mvn clean package

# Second stage: Create the final image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=build /app/target/GenAIProject-1.0-SNAPSHOT.jar app.jar

# Set environment variable for Heroku's dynamic port
ENV PORT=${PORT}

# Run the jar file
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]