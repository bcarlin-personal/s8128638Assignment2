# s8128638Assignment2 

An Android application built for NIT3213 demonstrating MVVM architecture, Dependency Injection via Hilt, Retrofit API integration, and Unit Testing.

## Features
- **Login Screen**: Authenticates user against the Footscray endpoint using student credentials.
- **Dashboard Screen**: Retrieves and displays entity summaries in a RecyclerView using the keypass token.
- **Details Screen**: Displays complete entity information, including detailed descriptions.

## Tech Stack & Architecture
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Networking**: Retrofit 2 + Gson Converter
- **UI Components**: ViewBinding, RecyclerView, CardView
- **Testing**: JUnit4, Mockito, Kotlinx Coroutines Test, InstantTaskExecutorRule

## Dependencies
All dependencies are declared in `app/build.gradle.kts` and resolved automatically on Gradle sync. Key libraries:
- **Hilt** (`com.google.dagger:hilt-android`) — dependency injection; requires the `kotlin-kapt` and `com.google.dagger.hilt.android` plugins, both already configured in the Gradle files.
- **Retrofit 2 + Gson Converter** — network calls and JSON deserialization.
- **OkHttp Logging Interceptor** — logs raw request/response bodies for debugging.
- **JUnit4, Mockito, Kotlinx Coroutines Test** — unit testing. 
- No manual dependency installation is required — running Gradle sync (step 4 below) downloads everything.

## How to Build & Run
1. Open Android Studio.
2. Select **File > Open** and select the project root directory.
3. Ensure JDK 17 is selected under **Settings > Build, Execution, Deployment > Build Tools > Gradle**.
4. Sync Gradle files by clicking **Sync Project with Gradle Files**.
5. Select an Android Emulator or physical device running **Android 7.0 (API 24)** or higher.
6. Click **Run 'app'** (`Shift + F10`).

## API Endpoints Used
- `POST https://nit3213apinew.onrender.com/footscray/auth`
- `GET https://nit3213apinew.onrender.com/dashboard/{keypass}`

## Requirements & Notes
- **Internet connection required**: The app authenticates against a live external API hosted on Render, so the emulator or device must have network access. The `INTERNET` permission is already declared in `AndroidManifest.xml`.
- **Login credentials**: Use your student ID (without the leading "s") as the username and your first name (case-sensitive) as the password, per the NIT3213 assignment specification.
- **Class location**: This build targets the `/footscray/` endpoint. Students in other locations would change the endpoint path in `ApiService.kt`.

## Running Unit Tests
Run all tests via: **right-click the `com.example.s8128638assignment2` test package > Run Tests**, or from the terminal:
```
./gradlew testDebugUnitTest
```