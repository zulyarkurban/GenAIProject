# Use the base image
FROM mcr.microsoft.com/azure-buildpacks/java:17.0.9-debian-bullseye

# Set environment variables
ENV BP_JVM_VERSION=17
ENV BP_JDK_URL=https://msjavafiles.blob.core.windows.net/jdk/microsoft-jdk-17.0.9-linux-x64.tar.gz

# Install Maven
USER root
RUN rm -rf /var/lib/apt/lists/* && apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy application files and the build script
COPY . /app

# Ensure the build script is executable
RUN chmod +x build.sh

# Run the build command
RUN ./build.sh  # Replace with your actual build command