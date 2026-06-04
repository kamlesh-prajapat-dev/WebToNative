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

## 🔔 Notification Flow

The app handles notifications to improve user engagement.
1.  **Permission**: Requests `POST_NOTIFICATIONS` permission on Android 13+.
2.  **Implementation**: Utilizes a `NotificationHelper` class to manage channels and display local notifications.
3.  **Trigger**: Notifications can be triggered by system events or received via Firebase Cloud Messaging (FCM).

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
*   **UI Polish**: Designing a premium-looking Image Carousel without relying on static assets, solved using dynamic gradients and Canvas.

---

## 🌟 Future Improvements

*   **Biometric Lock**: Add an option to lock the app using fingerprint or face ID.
*   **Offline Support**: Cache recently visited pages for offline viewing.
*   **Multi-Tab Support**: Allow users to open and switch between multiple websites.
*   **Custom Themes**: Let users choose accent colors and dark/light mode preferences for their wrapped apps.

---

Developed with ❤️ by **Orufy**
