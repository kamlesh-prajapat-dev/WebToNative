# WebToNative

WebToNative is a powerful Android application built with Jetpack Compose that allows users to seamlessly convert any website URL into a native mobile app experience. It features a modern UI, Firebase authentication, local history tracking, and a robust WebView implementation.

## 🚀 Project Setup Steps

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/your-username/WebToNative.git
    cd WebToNative
    ```
2.  **Open in Android Studio**:
    *   Open Android Studio (Ladybug or newer recommended).
    *   Select **Open** and navigate to the project directory.
3.  **Sync Gradle**:
    *   Wait for the project to index and sync Gradle dependencies.
4.  **Firebase Configuration**:
    *   Place your `google-services.json` file in the `app/` directory (see [Firebase Setup](#-firebase-setup)).
    *   Open `gradle.properties` in the project root and add your Firebase Web Client ID:
        ```properties
        WEB_CLIENT_ID="YOUR_WEB_CLIENT_TOKEN_HERE"
        ```
5.  **Build and Run**:
    *   Select an emulator or physical device and click the **Run** button.

---

## 🔥 Firebase Setup

The project uses Firebase for Authentication (Google Sign-In) and potentially for Cloud Messaging.

1.  **Create a Firebase Project**: Go to the [Firebase Console](https://console.firebase.google.com/).
2.  **Add Android App**: Use the package name `com.example.webtonative`.
3.  **SHA-1 Fingerprint**:
    *   Generate your SHA-1 key using `./gradlew signingReport`.
    *   Add the SHA-1 to your Firebase project settings to enable Google Sign-In.
4.  **Download Config**: Download `google-services.json` and move it to `app/src/`.
5.  **Enable Services**:
    *   Enable **Authentication** and activate the **Google** provider.
    *   In the Google Cloud Console, go to **Project > APIs & Services > Credentials > OAuth 2.0 Client IDs**. Under **Web SDK configuration**, copy the **Web client ID**. This is the token you need for your `gradle.properties` file.

---

## 🏗 Architecture Explanation

WebToNative follows **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)** pattern to ensure scalability and maintainability.

*   **Presentation Layer**: Built with **Jetpack Compose** for a reactive UI. ViewModels use `StateFlow` to expose UI state.
*   **Domain Layer**: Contains business logic, models, and repository interfaces.
*   **Data Layer**: Handles data sourcing from **Firebase (Remote)** and **Room Database (Local)**.
*   **Dependency Injection**: Powered by **Hilt** for clean decoupled components.

---

## 📊 Database Schema (Room)

We use Room to store the user's browsing history locally.

### `HistoryItem` Entity
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | `Int` | Primary Key (Auto-generated) |
| `url` | `String` | The website URL |
| `title` | `String` | Page title captured from WebView |
| `visitCount` | `Int` | Number of times visited |
| `lastVisitTime` | `Long` | Timestamp of the last visit (Indexed for fast sorting) |
| `iconInitial` | `String` | Initial letter for UI avatar |

---

## 🔔 Notification Flow (Welcome Back)

The app implements a local "Welcome Back" notification system with specific business rules to enhance user engagement.

### Features:
- **Title**: "Welcome Back"
- **Message**: "Thanks for opening the app"
- **Business Rules**:
  - **Daily Frequency**: Shown only once per day using Room database to track the last notification time.
  - **Foreground Detection**: Uses `ProcessLifecycleOwner` to ensure notifications are NOT shown when the app is already open/active.
  - **Login Safety**: Logic ensures it doesn't fire immediately after a login session.
- **Technical Stack**:
  - **WorkManager**: `PeriodicWorkRequestBuilder` ensures a reliable 24-hour check cycle.
  - **Notification Channel**: Custom `welcome_channel` for modern Android compatibility.
  - **Permissions**: Fully supports Android 13+ `POST_NOTIFICATIONS` runtime requests.

---

## 🌐 WebView Lifecycle Handling

Managing the WebView lifecycle correctly is crucial for performance and preventing memory leaks.

*   **`remember` & `AndroidView`**: The `WebView` instance is remembered across recompositions.
*   **State Management**: `WebViewClient` and `WebChromeClient` are used to sync the loading state and page titles with the `ViewModel`.
*   **Back Navigation**: A `BackHandler` is implemented to allow users to navigate back through the website's history before exiting the `WebViewScreen`.
*   **Loading States**: Custom `Loader` components are displayed during `onPageStarted` and dismissed in `onPageFinished`.

---

## 🚧 Challenges Faced

*   **State Persistence**: Keeping the WebView state consistent during configuration changes (like screen rotation) in a Compose-first environment.
*   **Deep Linking**: Correctly routing incoming URLs to the WebView while maintaining the app's navigation stack.
*   **Local Notification Stability**: Implementing a reliable "Welcome Back" notification system using `WorkManager` and `ProcessLifecycleOwner`. The primary challenge was ensuring the notification only triggers once daily and remains inactive while the app is in the foreground, which proved difficult to stabilize across different Android versions.

---

## 🌟 Future Improvements

*   **Network Connectivity**: Implement more robust handling for network state changes to provide a smoother offline-to-online transition.
*   **Code Quality**: Refactor existing modules to further enhance code readability, maintainability, and testing coverage.
*   **Notification Reliability**: Continue refining the local notification logic to ensure 100% consistent behavior across all OEM-specific power management settings.
*   **Offline Support**: Cache recently visited pages for offline viewing.
*   **Custom Themes**: Let users choose accent colors and dark/light mode preferences for their wrapped apps.

---

Developed with ❤️ by **Orufy**
