# TripMate – AI-Powered Smart Travel Planner

TripMate is a premium, production-ready Android travel planner application built with Kotlin and Jetpack Compose. It is designed to assist travelers in generating dynamic, day-wise itineraries, navigating destinations using free offline-friendly maps, tracking travel budgets and expenses, maintaining a personal travel journal, and receiving live weather insights—all integrated with Google's Gemini AI.

The codebase adheres strictly to **MVVM Clean Architecture**, offline-first principles, and modern Android development practices.

---

## Key Features

### 🤖 AI Travel Assistant
* Powered by the **Google Gemini API** (`gemini-flash-latest`).
* Generates structured, day-wise travel plans based on custom user prompts (e.g., duration, budget, target destination).
* Provides smart recommendations for attractions, dining, and scenic views based on budget.
* Configured in [GenerativeAIService.kt](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/data/remote/GenerativeAIService.kt).

### 🗺️ Interactive & Offline-Friendly Maps
* Employs **OpenStreetMap** with **OSMDroid** for free, high-performance mapping.
* Avoids paid Google Maps API billing.
* Supports displaying the traveler's current location, custom destination markers, interactive zoom, and favorite locations.
* Map logic is implemented in [OsmMapView.kt](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/ui/map/OsmMapView.kt) and rendered on the [NavigationScreen.kt](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/ui/map/NavigationScreen.kt).

### 📅 Smart Trip Planner
* Facilitates structured travel organizing.
* Users can view generated trips, browse day-wise plans, and check location-specific schedules.
* Features localized storage of itinerary days in Room Database.

### 💰 Travel Expense Tracker
* Locally logs travel costs with categorization (Food, Transport, Lodging, Shopping, Miscellaneous).
* Highlights budget analytics, expense indicators, and statistics to keep trips cost-efficient.

### ✍️ Travel Journal
* A digital diary for logging notes, thoughts, and trip memories.
* Securely persisted offline using Room DB.

### ☀️ Weather Insights
* Integrates with the **OpenWeather API** to fetch real-time forecasts.
* Shows temperature, wind speed, humidity, and rainfall forecasts to ensure travelers plan appropriately.

---

## Project Tech Stack

| Component | Technology | Description |
|---|---|---|
| **Language** | Kotlin | Modern, expressive, and safe language for Android. |
| **UI Framework** | Jetpack Compose | Declarative UI toolkit with Material 3 styling. |
| **Architecture** | MVVM + Clean Architecture | Separates concerns cleanly between UI, Business Logic, and Data. |
| **Local Database** | Room Database | Offline-first SQL wrapper supporting foreign keys and cascade deletes. |
| **Networking** | Retrofit | Type-safe HTTP client for Weather API communications. |
| **Image Loading** | Coil Compose | Lightweight image loading library for Compose. |
| **Map Rendering** | OSMDroid | Open-source library for interacting with OpenStreetMap tiles. |
| **AI Integration** | Google Generative AI | Gemini Android SDK. |

---

## Architectural Flow

The application follows an offline-first architecture pattern:

```
    [ Jetpack Compose UI Screens ] 
                 │
                 ▼
          [ ViewModels ] (Manages State and Actions)
                 │
                 ▼
       [ Repository Layer ] (Single source of truth)
          ╱          ╲
         ▼            ▼
   [ Room DB ]   [ Remote APIs ]
  (Offline Data) (Gemini / Weather API)
```

### Main Directories & Packages
* **`data/`**: Core data models, API clients, local database configuration, and repositories.
  * [local/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/data/local): Room Entities, DAOs, and the database class.
  * [remote/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/data/remote): Retrofit endpoints and the Gemini API Service.
  * [repository/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/data/repository): Repositories orchestrating networking and DB storage.
* **`navigation/`**: Manages app screen routing definitions.
  * See [Screen.kt](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/navigation/Screen.kt).
* **`ui/`**: Jetpack Compose presentation layer.
  * [home/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/ui/home): Home dashboard screen and state.
  * [timeline/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/ui/timeline): Itinerary details screen.
  * [map/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/ui/map): OSM mapping screens and views.
  * [splash/](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/java/com/tripmate/ai/ui/splash): Startup logo entry screen.

---

## App Screens Detailed

1. **Splash Screen**: Initiates loading sequences and transitions into the Dashboard.
2. **Onboarding Screen**: Features app introductions and walks users through initial setup.
3. **Home Dashboard**: Displays quick navigation, recent/upcoming trips, climate status, and the AI prompt input box.
4. **Interactive Timeline Screen**: Displays day-by-day itineraries, place detail cards, and navigation buttons.
5. **Maps Screen**: Displays place routes, user geolocation pins, and custom landmarks.
6. **Expense Tracker Screen**: Log expenses, show category aggregations, and compute remaining budget stats.
7. **Journal Screen**: View and write notes regarding completed trips.
8. **Favorites Screen**: Highlights starred locations and bookmark views.

---

## Setup & Running the Application

### 1. Prerequisites
Ensure you have the latest version of **Android Studio (Ladybug or newer)** installed.

### 2. Configure API Keys
To protect sensitive credentials, API keys are loaded via the project's local Gradle properties and injected into `BuildConfig`.
1. Create a `local.properties` file in the root folder of the project.
2. Add your Google Gemini and OpenWeather keys:
   ```properties
   GEMINI_API_KEY=your_gemini_api_key_here
   OPENWEATHER_API_KEY=your_openweather_api_key_here
   ```

### 3. Build and Run
1. Open Android Studio and select **Open Project**, selecting the root `Trip-Mate` directory.
2. Allow Gradle Sync to download dependencies.
3. Choose a device (physical phone or Android emulator) and click the **Run** button or execute:
   ```bash
   ./gradlew installDebug
   ```

---

## Permissions Setup

The application requests the following permissions in [AndroidManifest.xml](file:///c:/Users/nvsai/Desktop/anti%20gravity%20project/Trip-Mate/app/src/main/AndroidManifest.xml):
* `android.permission.INTERNET`: Used for map download, weather, and AI queries.
* `android.permission.ACCESS_FINE_LOCATION` & `android.permission.ACCESS_COARSE_LOCATION`: Used to display the user's current position on the map.
* `android.permission.POST_NOTIFICATIONS`: Prompts itinerary notifications and expense entry reminders.

---

## Security & Best Practices
* **No hardcoded credentials**: API Keys are read strictly from `local.properties` (which is excluded from Git) and compiled safely into `BuildConfig`.
* **Asynchronous processing**: Network and Room DB requests are handled via Kotlin Coroutines on `Dispatchers.IO` to ensure smooth UI performance.
* **Cascade Deletion**: Database integrity is maintained using SQL foreign keys, so removing a trip automatically cleans up associated itinerary days, places, and records.