# Sports Events App

## Overview
This Android app, built using Jetpack Compose, displays sports events and allows users to manage their favorite events. It provides real-time updates on upcoming events and integrates a countdown timer for each event.

## Features
- View a list of sports events categorized by sport type.
- Mark events as favorites and manage them accordingly.
- Real-time countdown timer for upcoming events.
- Responsive UI with Jetpack Compose for modern Android app development.

## Technologies Used
- **Jetpack Compose:** Modern UI toolkit for building native Android UIs.
- **Kotlin:** Primary programming language for Android development.
- **Android Architecture Components:** Utilized for managing UI-related data in a lifecycle-conscious way.
- **Coroutine:** Used for asynchronous programming and managing background tasks.
- **Material Design Components:** UI components for implementing Material Design in Android apps.
- **Room Persistence Library:** Android's native SQLite database library for data storage.
- **MVVM Architecture:** Architecture pattern used for separating UI from business logic.

## Project Structure
The project is structured as follows:  
app/  
|-- api/  
|-- data/  
|-- model/            # Data models  
|-- repository/        # Repository for managing data  
|-- ui/  
|-- ui/SportsApp.kt   # Main UI class  
|-- viewmodel/         # ViewModels for UI logic  
|-- util/              # Utility classes  
|-- res/  
|-- AndroidManifest.xml  

## Getting Started
1. **Clone the repository:**

git clone https://gitlab.com/renato.oliveira2808/android.git

And go to Sports Events App project.

2. **Open in Android Studio:**
- Open Android Studio and select `Open an existing Android Studio project`.
- Navigate to the cloned directory and open it.

3. **Run the app:**
- Connect your Android device or start the emulator.
- Click `Run` in Android Studio to build and run the app.

## Contribution
Contributions are welcome! If you find any issues or have suggestions for improvement, please feel free to submit an issue or a pull request.


