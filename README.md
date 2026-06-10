# WigoEvents

A Spring Boot application for event management, built on top of the [`spring-auth`](https://github.com/andrewmugisa/Spring_auth) shared authentication library.

---

## Architecture

Auth is handled entirely by the `spring-auth` library — this app owns only business logic, its own database tables, and the glue code that connects the two.

```
WigoEvents
├── user/          ← user management feature
├── events/        ← (coming soon)
├── bookings/      ← (coming soon)
└── adapter/       ← glue between this app and spring-auth
```

Adding a new feature = add a new package. Each package owns its own entity, repository, service, controller, DTOs, and responses. Nothing is shared across features except through explicit imports.

---

## Stack

- Java 17 · Spring Boot 4.0.6
- PostgreSQL · Spring Data JPA · Hibernate
- JWT authentication via `spring-auth` library
- Lombok · Springdoc OpenAPI (Swagger UI)
- Dotenv (`spring-dotenv`) for `.env` support

---

## API endpoints

### Auth — provided by `spring-auth` (no code needed in this repo)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/auth/signup` | Public | Register with name, email, password |
| POST | `/auth/login` | Public | Login, returns JWT token |
| POST | `/auth/verify` | Public | Verify account with 6-digit email code |
| POST | `/auth/resend` | Public | Resend verification code |
| POST | `/auth/logout` | Bearer | Blacklist token server-side |

### User — owned by this app

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/users/me` | Bearer | Get current authenticated user |
| GET | `/users/` | Bearer | Get all users |
| PATCH | `/users/me/username` | Bearer | Update display username |
| DELETE | `/users/me` | Bearer | Permanently delete account |

---

## Error response format

All errors follow this consistent shape (from `spring-auth`'s `GlobalExceptionHandler`):

```json
{
  "timestamp": "2026-06-10T03:47:29.051Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Email is already registered"
}
```

Validation errors include a `details` field:

```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": {
    "email": "Invalid email format",
    "password": "Password must be at least 6 characters"
  }
}
```

---

## Setup

### Prerequisites
- Java 17+
- PostgreSQL running locally
- `spring-auth` installed to local Maven repo (see below)

### Step 1 — Install the auth library

```bash
cd ~/IdeaProjects/Spring_auth
./mvnw clean install
```

### Step 2 — Create a `.env` file

Copy `env.example` to `.env` and fill in your values:

```bash
cp env.example .env
```

```properties
# Database
PSQL_DATABASE=wigoEventsDB
PSQL_USER=postgres
PSQL_PASSWORD=your-password

# JWT
JWT_SECRET_KEY=your-base64-secret-key
JWT_EXPIRATION_MS=3600000

# Mail (Gmail SMTP)
MAIL_SENDER_SERVER=smtp.gmail.com
MAIL_SENDER_PORT=587
MAIL_USE_TLS=true
SUPPORT_EMAIL=your-email@gmail.com
SUPPORT_EMAIL_APP_PASSWORD=your-app-password

# CORS
LOCALHOST_URL=http://127.0.0.1:5500
LOCAL_TEST_URL=http://localhost:3000
```

### Step 3 — Run

```bash
./mvnw spring-boot:run
```

App starts on `http://localhost:8080`. Swagger UI at `http://localhost:8080/swagger-ui/index.html`.

---

## How auth is wired in

The `spring-auth` library needs two things from this app:

**`adapter/AuthUserRepositoryAdapter.java`** — bridges the JPA `UserRepository` to the library's `AuthUserRepository` interface:

```java
@Repository
public class AuthUserRepositoryAdapter implements AuthUserRepository {
    // delegates findByEmail, findByUsername, findByVerificationCode, save
    // to the JPA UserRepository
}
```

**`adapter/UserEntityFactory.java`** — tells the library how to create a new user:

```java
@Component
public class UserEntityFactory implements AuthUserFactory {
    @Override
    public AuthUser create(String username, String email, String password, String name) {
        return new UserEntity(username, email, password, name);
    }
}
```

The `@SpringBootApplication` on `WigoEventsApplication` excludes Spring Boot's default security auto-configs to prevent a filter chain conflict with the library's own `SecurityConfiguration`.

---

## Adding a new feature

Create a new package, e.g. `events/`:

```
src/main/java/org/wigo/wigoevents/events/
├── EventEntity.java       ← @Entity, your DB table
├── EventRepository.java   ← JpaRepository
├── EventService.java      ← business logic
├── EventController.java   ← REST endpoints
├── EventResponse.java     ← what the API returns
└── CreateEventDto.java    ← what the API accepts
```

That's it. No changes to `spring-auth`, no changes to `adapter/`, no changes to `user/`.

---

## Multi-device login

This app uses stateless JWT. The server does not track active sessions — logging in on multiple devices issues independent tokens, all valid simultaneously until they expire. This is intentional. Single-session enforcement would require a `sessions` table and is not currently implemented.

---

## Project structure

```
src/main/java/org/wigo/wigoevents/
├── WigoEventsApplication.java     ← main class, excludes default security auto-configs
├── user/
│   ├── UserEntity.java            ← extends AuthUser, add app-specific fields here
│   ├── UserRepository.java        ← JPA repo
│   ├── UserService.java           ← updateUsername, deleteUser, allUsers
│   ├── UserController.java        ← GET/PATCH/DELETE /users/*
│   ├── UserResponse.java          ← API response shape
│   └── UpdateUsernameDto.java     ← PATCH /users/me/username request body
└── adapter/
    ├── AuthUserRepositoryAdapter.java  ← bridges UserRepository → AuthUserRepository
    └── UserEntityFactory.java          ← tells spring-auth how to create UserEntity
```
