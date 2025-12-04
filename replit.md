# Connect API - Spring Boot Backend

## Overview
Spring Boot REST API that manages events data for Portland locations using PostgreSQL database. The API provides complete social features including user authentication (JWT-based), friend management, event attendance tracking, and favorites.

## Project Architecture

### Technology Stack
- **Framework**: Spring Boot 3.1.5
- **Database**: PostgreSQL (Neon-backed via Replit)
- **ORM**: Spring Data JPA with Hibernate
- **Migrations**: Flyway
- **Security**: Spring Security with JWT authentication
- **Build**: Gradle 8.4

### Directory Structure
```
src/main/java/com/connect/
├── config/             # Configuration classes (Security, CORS)
├── controller/         # REST controllers (Auth, Event, User, Friends, Favorite)
├── dto/                # Data Transfer Objects
├── exception/          # Global exception handler
├── model/              # JPA entities (Event, User, Friendship, EventAttendee, Favorite)
├── repository/         # JPA repositories
├── security/           # JWT service and authentication filter
└── service/            # Business logic services
```

### Database Schema
The database is managed separately in the `connect-db` repository. This API does not touch the database schema - migrations are handled externally.

**Entities:**
- `events` - Event information (title, category, date, location, attendees)
- `users` - User accounts (email, password hash, profile info)
- `friendships` - Friend relationships (requester, addressee, status)
- `event_attendees` - Event attendance tracking
- `favorites` - User's favorited events

## API Endpoints (32 total)

### Authentication (`/auth`)
- `POST /auth/signup` - Register new user
- `POST /auth/login` - Login and get JWT token
- `POST /auth/logout` - Logout (invalidate token client-side)
- `GET /auth/me` - Get current authenticated user

### Users (`/users`)
- `GET /users/profile` - Get own profile
- `PUT /users/profile` - Update own profile
- `GET /users/{id}` - Get user by ID

### Events (`/events`)
- `GET /events` - List all events
- `GET /events/{id}` - Get event by ID
- `POST /events` - Create new event
- `PUT /events/{id}` - Update event
- `DELETE /events/{id}` - Delete event
- `POST /events/{id}/join` - Join event (authenticated)
- `POST /events/{id}/leave` - Leave event (authenticated)
- `GET /events/my` - Get events user has joined
- `GET /events/created` - Get events user created
- `GET /events/friends` - Get events friends are attending
- `GET /events/{id}/joined` - Check if user joined event
- `GET /events/search` - Search events by location/category

### Friends (`/friends`)
- `GET /friends` - List accepted friends
- `POST /friends/request/{userId}` - Send friend request
- `GET /friends/requests/pending` - Get pending friend requests
- `GET /friends/requests/sent` - Get sent friend requests
- `POST /friends/accept/{friendshipId}` - Accept friend request
- `POST /friends/reject/{friendshipId}` - Reject friend request
- `DELETE /friends/{friendId}` - Remove friend

### Favorites (`/favorites`)
- `GET /favorites` - Get user's favorites
- `POST /favorites/{eventId}` - Add event to favorites
- `DELETE /favorites/{eventId}` - Remove from favorites
- `GET /favorites/{eventId}/check` - Check if event is favorited

## Configuration

### Environment Variables
- `PGHOST` - PostgreSQL host (default: 127.0.0.1)
- `PGPORT` - PostgreSQL port (default: 5432)
- `PGDATABASE` - Database name (default: connect_db)
- `PGUSER` - Database user (default: runner)
- `PGPASSWORD` - Database password (default: empty)
- `JWT_SECRET` - Secret key for JWT signing (auto-generated if not set)

### CORS
Currently configured to allow all origins (`*`) for development. Will be configured with specific frontend URL for production.

## Running the API

### Development
```bash
./gradlew bootRun
```
The API runs on port 8080 with context path `/api`.

### Testing
```bash
./gradlew test
```

## Recent Changes (December 2024)
- Added complete authentication system with Spring Security and JWT
- Created 5 new entities: User, Friendship, EventAttendee, Favorite
- Built 5 controllers with 32 total endpoints
- Added global exception handler for better error responses
- Configured CORS to allow all origins temporarily
- Fixed PGHOST to use 127.0.0.1 for local database connection

## User Preferences
- Database migrations are managed separately in `connect-db` repository - NEVER touch the database schema
- CORS set to allow all origins temporarily, will configure specific frontend URL later
- JWT-based authentication with BCrypt password hashing for security

## 3-Repo Structure
This project is part of a 3-repository structure:
1. **connect-api** (this repo) - Spring Boot backend API
2. **connect-db** - Database migrations and schema management
3. **connect-frontend** - React/mobile frontend application
