#!/bin/bash

echo "Setting up Automation Sofka project..."

# Check Java installation
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java JDK 17 or higher."
    exit 1
fi

echo "Java version:"
java -version

# Check if Gradle wrapper exists
if [ ! -f "./gradlew" ]; then
    echo "Gradle wrapper not found. Creating..."
    if command -v gradle &> /dev/null; then
        gradle wrapper --gradle-version 8.5
    else
        echo "Error: Gradle is not installed. Please install Gradle or use the Gradle wrapper."
        exit 1
    fi
fi

# Make gradlew executable
chmod +x gradlew

# Build project
echo "Building project..."
./gradlew build -x test

echo "Setup completed successfully!"
echo "You can now run tests with: ./gradlew test"

