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
