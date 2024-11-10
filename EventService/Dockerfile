# --------------------
# BUILD STAGE
# --------------------
FROM gradle:8.10-jdk23-alpine AS builder

# Set the working directory and copy project files
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the project without running tests
RUN gradle build -x test

# --------------------
# PACKAGE STAGE
# --------------------
FROM openjdk:23-jdk

# Create an application directory
RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/EventService.jar

# Set environment variables if needed (example for MongoDB credentials)
ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password

# Expose the appropriate port (adjust as needed for each service)
EXPOSE 8092

# Run the application
ENTRYPOINT ["java", "-jar", "/app/EventService.jar"]
