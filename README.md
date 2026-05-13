# Runique

**An offline-first Android running tracker built with Jetpack Compose and Clean Architecture.**

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min%20SDK-25%20(Android%207.1)-brightgreen)
![Target SDK](https://img.shields.io/badge/Target%20SDK-35%20(Android%2015)-blue)
![Gradle](https://img.shields.io/badge/Gradle-8.10.0-02303A?logo=gradle)

---

## Overview

Runique is a GPS-powered running tracker that records real-time metrics, visualizes your route on Google Maps, and syncs your runs to the cloud — with full offline support. Authenticate with your account, track runs in the foreground (even with the screen off), review your run history, and explore aggregate statistics through an on-demand Analytics module.

---

## Features

### Run Tracking
- Real-time GPS tracking via Google Play Services Fused Location Provider
- Live metrics displayed during every run:
  - Elapsed time
  - Total distance (meters / km)
  - Current pace (min/km)
  - Average speed and max speed (km/h)
  - Total elevation gain (meters)
- Google Maps integration with a **speed-based color-coded polyline** (green → yellow → red as speed increases)
- Automatic map snapshot captured on run completion
- **Pause / Resume / Finish** run controls
- **Foreground service** keeps GPS tracking alive when the app is backgrounded or the screen is locked
- Deep link support: `runique://active_run`

### Offline-First Data Sync
- Runs are always saved to the local Room SQLite database first — zero data loss on network failure
- Three dedicated WorkManager workers handle background sync:
  - **CreateRunWorker** — syncs new runs to the API; retries up to 5 times with exponential backoff
  - **DeleteRunWorker** — confirms remote deletions; retries up to 5 times with exponential backoff
  - **FetchRunsWorker** — periodically pulls remote runs into the local database
- All sync workers are network-gated (only run when connected)
- Conflict-safe: if a run is deleted before it has been synced, it is removed from the pending-create queue instead of sending a delete request for a non-existent remote record

### Run History
- Chronological list of all completed runs (most recent first)
- Each run card displays: date & time, map thumbnail, distance, duration, average pace, and GPS start coordinates
- Delete any run with automatic background sync scheduling

### Analytics Dashboard *(Dynamic Feature — downloaded on demand)*
- Total distance run (all time)
- Total time spent running
- Fastest run ever recorded
- Average distance per run
- Average pace per run
- Computed entirely from local Room aggregation queries — no internet required
- Delivered as a **Play Feature Delivery** dynamic module, keeping the base APK smaller

### Authentication
- Email + password **registration** with real-time validation:
  - Minimum 9 characters
  - Must contain at least one uppercase letter, one lowercase letter, and one digit
- **Login** with JWT-based session (access token + refresh token)
- Automatic silent token refresh on 401 responses — no interruption to the user
- Tokens stored in **EncryptedSharedPreferences** (AndroidX Security Crypto)
- **Logout** clears all local session data and invalidates the remote session

---

## Architecture

Runique follows **Clean Architecture** with an **MVVM** presentation pattern. Each feature is split into three layers:

```
┌──────────────────────────────────────────┐
│  Presentation  (ViewModels + Composables) │
│     State ← ViewModel ← Action/Event     │
└────────────────────┬─────────────────────┘
                     │ interfaces only
┌────────────────────▼─────────────────────┐
│         Domain  (Pure Kotlin)             │
│  Models · Repository interfaces · Logic  │
└────────────────────┬─────────────────────┘
                     │ implements
┌────────────────────▼─────────────────────┐
│  Data  (Room · Ktor · WorkManager)        │
│  Repository impls · DAOs · HTTP · Workers │
└──────────────────────────────────────────┘
```

**Key design decisions:**
- Domain layer has **zero Android dependencies** — pure Kotlin models and interfaces
- All async work uses **Kotlin Coroutines** and **StateFlow / Flow**
- Functional error handling via a `Result<D, E>` sealed interface — no exceptions leaking across boundaries
- `OfflineFirstRunRepository` orchestrates the local-first / remote-sync strategy so no caller has to think about connectivity

---

## Module Structure

42 Gradle modules organized across four feature areas, each following the same domain / data / presentation split:

```
app/                          → Entry point, Koin DI wiring, navigation graph
│
├── core/
│   ├── domain/               → Shared models: Run, Location, AuthInfo, Result, DataError
│   ├── data/                 → OfflineFirstRunRepository, HttpClientFactory, EncryptedSessionStorage
│   ├── database/             → RunDatabase, RunDao, AnalyticsDao, RunPendingSyncDao
│   └── presentation/         → Design system (theme, colors, typography, reusable Compose components)
│
├── auth/
│   ├── domain/               → AuthRepository interface, UserDataValidator, PasswordValidationState
│   ├── data/                 → RemoteAuthRepository (Ktor), request/response DTOs
│   └── presentation/         → IntroScreen, LoginScreen, RegisterScreen
│
├── run/
│   ├── domain/               → RunningTracker, LocationDataCalculator, RunData
│   ├── data/                 → CreateRunWorker, DeleteRunWorker, SyncRunWorkerScheduler
│   ├── location/             → AndroidLocationObserver (FusedLocationProvider → Kotlin Flow)
│   ├── network/              → KtorRemoteRunDataSource, RunDto, RunMappers, CreateRunRequest
│   └── presentation/         → ActiveRunScreen, RunOverviewScreen, TrackerMap, PolylineColorCalculator,
│                               ActiveRunService (foreground service)
│
└── analytics/
    ├── domain/               → AnalyticsRepository interface, AnalyticsValues
    ├── data/                 → RoomAnalyticsRepository
    ├── presentation/         → AnalyticsDashboardScreen, AnalyticsDashboardViewModel
    └── analytics_feature/    → AnalyticsActivity (dynamic feature entry point)
```

**Custom Gradle Convention Plugins** (in `build-logic/`) keep module build files DRY:

| Plugin | Purpose |
|--------|---------|
| `runique-android-application` / `-compose` | App module configuration |
| `runique-android-library` / `-library-compose` | Android library modules |
| `runique-android-feature-ui` | Feature UI modules (includes Compose + Koin + Navigation) |
| `runique-android-dynamic-feature` | Dynamic delivery modules |
| `runique-android-room` | Room database setup with KSP |
| `runique-jvm-library` | Pure JVM / Kotlin modules |
| `runique-jvm-ktor` | JVM modules that use Ktor |

---

## Getting Started

### Prerequisites

- **Android Studio** Ladybug (2024.2) or newer
- **JDK 11** or newer
- A **Google Maps API key** with the *Maps SDK for Android* enabled ([Google Cloud Console](https://console.cloud.google.com/))
- An **API key** for the Runique backend

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/zeljkolucic/Runique.git
   cd Runique
   ```

2. **Configure secrets** — create or edit `local.properties` in the repository root:
   ```properties
   MAPS_API_KEY=your_google_maps_api_key_here
   API_KEY=your_runique_backend_api_key_here
   BASE_URL=https://runique.pl-coding.com:8080
   ```

3. **Open in Android Studio** and wait for the Gradle sync to complete.

4. **Run the app** on a physical device or an emulator that has **Google Play Services** installed.

> **Note:** GPS tracking requires a physical device for realistic results. If using an emulator, use the emulator's location controls to simulate movement.

### Required Permissions

The app requests the following permissions at runtime:

| Permission | Purpose |
|-----------|---------|
| `ACCESS_FINE_LOCATION` | GPS tracking during runs |
| `ACCESS_COARSE_LOCATION` | Fallback / network location |
| `POST_NOTIFICATIONS` | Foreground service notification (Android 13+) |

---

## API Reference

All endpoints are relative to `https://runique.pl-coding.com:8080`. Every request must include an `x-api-key` header. Authenticated endpoints require `Authorization: Bearer <accessToken>`.

### Authentication

| Method | Endpoint | Auth | Description |
|--------|---------|------|-------------|
| `POST` | `/register` | No | Create a new account |
| `POST` | `/login` | No | Log in; returns `accessToken`, `refreshToken`, `expirationTimestamp`, `userId` |
| `POST` | `/accessToken` | Refresh token | Exchange a refresh token for a new access token |
| `GET` | `/logout` | Yes | Invalidate the current session |

#### Register — `POST /register`
```json
{
  "email": "user@example.com",
  "password": "SecurePass1"
}
```

#### Login — `POST /login`
```json
// Request
{ "email": "user@example.com", "password": "SecurePass1" }

// Response
{
  "accessToken": "eyJ...",
  "refreshToken": "eyJ...",
  "accessTokenExpirationTimestamp": 1700000000,
  "userId": "abc123"
}
```

#### Refresh Token — `POST /accessToken`
```json
// Request
{ "refreshToken": "eyJ...", "userId": "abc123" }

// Response
{ "accessToken": "eyJ...", "expirationTimestamp": 1700003600 }
```

---

### Runs

| Method | Endpoint | Auth | Description |
|--------|---------|------|-------------|
| `GET` | `/runs` | Yes | Fetch all runs for the authenticated user |
| `POST` | `/run` | Yes | Upload a completed run with map screenshot |
| `DELETE` | `/run?id={runId}` | Yes | Delete a run by ID |

#### Get Runs — `GET /runs`
Returns a JSON array of run objects:
```json
[
  {
    "id": "run_abc123",
    "dateTimeUtc": "2024-09-15T08:30:00Z",
    "durationMillis": 3600000,
    "distanceMeters": 10000,
    "latitude": 52.2297,
    "longitude": 21.0122,
    "avgSpeedKmh": 10.0,
    "maxSpeedKmh": 12.5,
    "totalElevationMeters": 45,
    "mapPictureUrl": "https://..."
  }
]
```

#### Create Run — `POST /run`
Multipart form data with two parts:

| Part | Content-Type | Description |
|------|-------------|-------------|
| `RUN_DATA` | `text/plain` | JSON-serialized run object (see below) |
| `MAP_PICTURE` | `image/jpeg` | JPEG screenshot of the GPS route map |

```json
{
  "id": "local_uuid",
  "durationMillis": 3600000,
  "distanceMeters": 10000,
  "epochMillis": 1694767800000,
  "latitude": 52.2297,
  "longitude": 21.0122,
  "avgSpeedKmh": 10.0,
  "maxSpeedKmh": 12.5,
  "totalElevationMeters": 45
}
```

---

## Offline-First Sync

Runique uses a **local-first** strategy: every write goes to Room first; the network is treated as an eventually consistent replica.

```
User action
    │
    ▼
Room (local DB) ──────────────────────────────────────── Always succeeds
    │
    ▼ attempt remote call immediately
  Success? ──Yes──▶ Done
    │
    No (no network / error)
    │
    ▼
Enqueue WorkManager job (network-constrained)
    │
    ▼ when network returns
CreateRunWorker / DeleteRunWorker
    │
    ▼ up to 5 retries, exponential backoff (2 s initial)
  Success? ──Yes──▶ Remove from pending queue
    │
    No (5th failure)
    ▼
  Mark as failed (user can retry manually)

FetchRunsWorker (periodic)
  ── pulls remote runs into local DB on a schedule
  ── scheduled once, deduplication prevents multiple instances
```

