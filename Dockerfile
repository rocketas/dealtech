# In order to test run:
# docker build -t [name] . ==> this creates the docker image. You can specificy platform with --platform linux/amd64
# docker run --rm -p 8080:8080 [name] ==> verifies that the app can run
#### To push to Google Artifact Registry
# docker tag  [docker-tag] us-central1-docker.pkg.dev/dealtech-471809/dealtech-docker-repo/dealtech-image-test-v0:latest ==
# docker push us-central1-docker.pkg.dev/dealtech-471809/dealtech-docker-repo/dealtech-image-test-v0:latest
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set the working directory for the build
WORKDIR /workspace

# Copy project files into the build environment
COPY . .
RUN chmod +x ./gradlew

# Run the build. The .jar is created inside this stage at /workspace/build/libs/
RUN ./gradlew build --no-daemon

# --- STAGE 2: THE FINAL "APP" ---
# Start fresh with a clean, lightweight JRE image
FROM eclipse-temurin:21-jre-alpine

# Set the working directory for the final application
WORKDIR /app

# This is the magic step!
# Copy ONLY the .jar file FROM the 'builder' stage INTO this new stage.
COPY --from=builder /workspace/app/build/libs/*.jar /app/application.jar

# Expose the port and run the app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]