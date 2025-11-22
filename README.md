# Connect API - Spring Boot

Backend API for Connect community events platform using Spring Boot, PostgreSQL, and Flyway.

## Setup

1. Set environment variables:
```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/connect_db
export DB_USER=postgres
export DB_PASSWORD=yourpassword
```

2. Build the project:
```bash
./gradlew build
```

3. Run the application:
```bash
./gradlew bootRun
```

The API will be available at `http://localhost:3001/api`

## API Endpoints

- `GET /api/events` - Get all events
- `GET /api/events/{id}` - Get event by ID
- `POST /api/events` - Create new event
- `PUT /api/events/{id}` - Update event
- `DELETE /api/events/{id}` - Delete event
- `POST /api/events/{id}/join` - Join an event

## Database

PostgreSQL with Flyway migrations. Migrations are automatically applied on startup.
