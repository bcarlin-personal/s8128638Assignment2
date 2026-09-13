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