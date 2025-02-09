# Project Overview


This project is built using Kotlin and follows the principles of Clean Architecture. The main goal of Clean Architecture is to separate the code into layers, making it easier to maintain, test, and scale. The project uses Gradle as the build system.  

## Features:
1. Constant Venues update based on location. *GetLocationsUseCase* returns flows of locations. And it emits a new location every 10 seconds.
2. Venues list: venues are reloaded for a new location. Every time *GetLocationsUseCase* emits a new location, *GetVenuesUseCase* requests new venues.
3. Favourites: users can save their favourites venues by clicking on hearth icon next to the venue name. The favourites data is saved locally in SharedPreferences.
4. Accessibility: Check the accessibility section below


## Clean Architecture
The project is divided into 3 layers:  

* Data Layer: Responsible for data management. Package: **com.github.murzagalin.restaurants.data** This layer implements the repository interfaces defined in the domain layer and handles data sources such as *FavoritesStorage* storage, and network *VenuesApi*.  
* Domain Layer: Contains the business logic of the application. Package: **com.github.murzagalin.restaurants.domain**. This layer is independent of any other layers and frameworks. It includes use cases, entities, and repository interfaces.
* Presentation(UI) Layer: Manages the UI and user interactions. Package: **com.github.murzagalin.restaurants.ui**. This layer includes ViewModels, UI mappers, UI components. This layer interacts with the domain layer to display data to the user.  

Each layer has their own models. And there are 2 mappers. One is in Data Layer (API models -> Domain models), another one is in UI Layer (Domain models -> UI models)

## Test coverage
* I added Unit tests for most of the classes. Please check the test coverage.
* There are no tests for storages and activities and DI. I didn't add any Android SDK related tests, to keep it simple. Also having roboelectric tests make testing more expensive in terms of maintenance cost.

## Error handling
I handled 2 types of errors:
1. Undefined errors
2. Network errors

Each of them has a dedicated error screen.

## Accessibility

I implemented simple accessibility:
1. Every row is merged and read at once as "{venue name} {favourite state}" so for users it sounds like "McDonald's Favourte" or "McDonald's Not Favourte"
2. Favourites buttons are separated, so users can still click on them
3. TalkBack also reads a title which is a page_title from the response

## Trade-offs
1. Using **SharedPreferences** vs. **Database** vs. **java Files** vs. **Remote storage**. This project aims for simplicity, so I didn't add any complicated storage and got away with just using **SharedPreferences**. If we need more robust implementation, **java Files** or **Database** would be better. If we want to keep the data when users have multiple devices, it is better to use a **Remote Storage**
2. Hardcoding the list of locations in **GetLocationsUseCase** vs. moving it one level lower to a **DataSource**. I wanted to keep the code as simple as possible, so I hardcoded the values directly in the UseCase. This was done just for code readability and simplicity.
