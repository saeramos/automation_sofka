@echo off
echo Setting up Automation Sofka project...

REM Check Java installation
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed. Please install Java JDK 17 or higher.
    exit /b 1
)

echo Java version:
java -version

REM Check if Gradle wrapper exists
if not exist "gradlew.bat" (
    echo Gradle wrapper not found. Creating...
    if exist "C:\Program Files\Gradle\gradle\bin\gradle.bat" (
        "C:\Program Files\Gradle\gradle\bin\gradle.bat" wrapper --gradle-version 8.5
    ) else (
        echo Error: Gradle is not installed. Please install Gradle or use the Gradle wrapper.
        exit /b 1
    )
)

REM Build project
echo Building project...
call gradlew.bat build -x test

echo Setup completed successfully!
echo You can now run tests with: gradlew.bat test

