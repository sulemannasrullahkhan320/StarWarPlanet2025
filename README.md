## Architecture

- **MVVM** architecture with a dedicated `PlanetsViewModel` for state management.
- **Repository Pattern** — `PlanetRepository` abstracts data sources and network logic.
- **Koin** — lightweight dependency injection framework for managing app components.
- **Jetpack Compose** — modern declarative UI toolkit, using **Navigation Compose** for in-app navigation.
- **Retrofit** + **OkHttp** — handle REST API calls efficiently.
- **Coil** — load and cache images seamlessly.
- **StateFlow** and sealed UI state classes ensure a unidirectional data flow and predictable state handling.

---