# Use Eclipse Temurin JDK 21 for building
FROM eclipse-temurin:21-jdk-jammy AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies (cached layer)
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************** 
# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use JRE for runtime (smaller image)
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/fnc-bank-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
