# MovieDemo

## About

MovieDemo is an Android application built with Jetpack Compose that allows users to browse and discover movies from The Movie Database (TMDb) API. It showcases modern Android development practices, focusing on a clean architecture, testability, and a delightful user experience.

## Features

* **Movie Discovery:** Browse trending and popular movies with ease.
* **Detailed Information:** View comprehensive details for each movie, including cast, crew, reviews, and trailers.
* **Offline Support:** Cache movie data for offline access.
* **Search Functionality:** Easily find movies by title.
* **Favorites:** Mark movies as favorites for quick access later.

## Technologies

This project leverages a variety of cutting-edge technologies and libraries, including:

* **Jetpack Compose:** A modern toolkit for building native Android UIs with a declarative approach.
* **Kotlin Coroutines:** Simplifies asynchronous programming, enabling efficient handling of background tasks like network requests.
* **Kotlin Flow:** Provides reactive streams for observing data changes and seamlessly updating the UI.
* **Hilt:** A dependency injection library for Android that promotes testability and modularity.
* **Retrofit:** A type-safe HTTP client for Android and Java that simplifies interactions with REST APIs.
* **Room:** A persistence library that provides an abstraction layer over SQLite, making it easier to work with databases.
* **Coil:** An image loading library that efficiently loads and displays images in the UI.
* **Timber:** A logging library that simplifies debugging and tracking application behavior.
* **Data Paginator** - helps handling pagination data

## Architecture

Movie Mania follows a **MVVM (Model-View-ViewModel)** architecture pattern, promoting a clear separation of concerns and making the codebase maintainable and testable.

**Here's a breakdown of the architecture layers:**

1. **UI Layer (Jetpack Compose):**
   * **Composable Functions:** Responsible for defining the UI elements and their behavior.
   * **ViewModels:** Expose data and state to the UI and handle user interactions.

2. **Data Layer:**
   * **Repository:** Acts as a single source of truth for movie data, abstracting access to both local and remote data sources.
   * **Data Sources:**
      * **Remote Data Source (TMDB API):** Fetches movie data from the TMDb API using Retrofit.
      * **Local Data Source (Room):** Caches movie data locally using the Room database for offline access.

3. **Domain Layer:**
   * **Use Cases:** Encapsulate business logic and orchestrate the flow of data between the UI and data layers.

**Architecture Diagram:**

## Components

* **MovieListViewModel:** Manages the state and logic for displaying a list of movies, including pagination and handling loading and error states.
* **MovieDetailViewModel:** Fetches and displays detailed information about a specific movie.
* **TMDBRepository:** Provides access to movie data from both the TMDb API and the local database.
* **TMDBApi:** Defines the API endpoints for interacting with the TMDb API using Retrofit.
* **MovieDatabase:** Defines the Room database schema for storing movie data locally.

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or a physical device.

## Testing

The project includes a comprehensive suite of unit and UI tests:

* **Unit Tests:** Verify the logic of individual components in isolation.
* **UI Tests:** Simulate user interactions and assert the behavior of the UI.


## Contributing

Contributions are welcome! Please follow the standard GitHub flow for submitting pull requests.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
