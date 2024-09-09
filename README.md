# Workout Tracker

Workout Tracker is a Spring Boot application that allows users to manage their workouts, exercises, and track their fitness progress.

## Features

- User authentication and registration
- Create, update, and delete workouts
- Add exercises to workouts
- Schedule workouts
- Generate workout reports
- RESTful API for easy integration

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL
- Flyway for database migrations
- Lombok for reducing boilerplate code
- Swagger/OpenAPI for API documentation

## Project Structure

The project follows a standard Spring Boot application structure:

- `entity`: Contains JPA entities (User, Workout, Exercise)
- `repository`: Contains Spring Data JPA repositories
- `service`: Contains business logic
- `controller`: Contains REST API endpoints
- `dto`: Contains Data Transfer Objects for API requests and responses

## Setup and Installation

1. Clone the repository
2. Ensure you have Java 17 and Maven installed
3. Set up a MySQL database and update the `application.properties` file with your database credentials
4. Run the following command to start the application:

```
mvn spring-boot:run
```

## API Endpoints

- POST /api/workouts: Create a new workout
- PUT /api/workouts/{id}: Update an existing workout
- DELETE /api/workouts/{id}: Delete a workout
- GET /api/workouts: List all workouts for the current user
- GET /api/workouts/report: Generate a workout report

## Database Schema

The database schema is managed using Flyway migrations. The initial schema is defined in:

## Testing

To run the tests, use the following command:

```
mvn test
```

## API Documentation

API documentation is available using Swagger UI. After starting the application, visit:

```
http://localhost:8081/swagger-ui.html
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the [MIT License](LICENSE).


#### Source Challenge - [Workout Tracker](https://roadmap.sh/projects/fitness-workout-tracker)