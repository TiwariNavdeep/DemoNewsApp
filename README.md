# Demo NewsApp 📰

A high-performance Android News application built with **Jetpack Compose**, following **Clean Architecture** principles and the **MVVM** pattern.

## 🚀 Key Features

* **Offline-First Strategy**: Implements a local-cache-first approach. Data is fetched from the API, saved to a **Room Database**, and then served to the UI.
* If the network is unavailable, the app seamlessly serves cached news.
* **Dynamic Categories**: Integrated Tab-based navigation for General, Sports, Entertainment, and Technology.
* **Paging 3 Integration**: Efficiently handles large datasets with **Pagination (10 items per page)** for both the Home feed and Search results.
* **WebView Integration**: Custom implementation using `AndroidView` to read full articles within the app, featuring proper back-navigation handling and loading states.
* **Smart Search**: 
    * Search by keyword with paginated results.
    * Category chips that redirect and filter the main feed.

---

## 🏗️ Architecture & Tech Stack

The project follows **Clean Architecture** to ensure the code is modular, testable, and maintainable.

* **UI**: Jetpack Compose (100% declarative UI)
* **Navigation**: Navigation Compose (with URL Encoding for WebView routes)
* **Architecture**: MVVM (Model-View-ViewModel)
* **Local DB**: Room Persistence Library
* **Networking**: Retrofit & OkHttp
* **Pagination**: Paging 3 (RemoteMediator)
* **Asynchronous**: Kotlin Coroutines & Flow

---

## 🔄 App Flow

### 1. Home & News Feed
Upon launch, the app targets the "General" category. 
The **RemoteMediator** checks the local database; if the data is stale or missing,
it triggers a network request, updates the DB, and emits the new state to the UI via `Flow<PagingData>`.

### 2. Reading Articles
When a user taps **"Tap To Read More"** on a news card:
1.  The URL is encoded using `URLEncoder`.
2.  The `navController` navigates to the `WebView` route.
3.  The `WebViewScreen` handles the web lifecycle and allows "back" navigation within the browser history.

### 3. Search & Discovery
The Search screen allows users to query specific news.
Selecting a **Category Chip** updates the navigation state to return to the Home screen with the selected category active.
