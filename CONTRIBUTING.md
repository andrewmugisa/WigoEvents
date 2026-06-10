# Contributing to WigoEvents

## Adding a new feature

Create a new package under `src/main/java/org/wigo/wigoevents/`:

```
src/main/java/org/wigo/wigoevents/
└── yourfeature/
    ├── YourEntity.java        ← @Entity
    ├── YourRepository.java    ← JpaRepository
    ├── YourService.java       ← business logic
    ├── YourController.java    ← REST endpoints
    ├── YourResponse.java      ← API response shape
    └── CreateYourDto.java     ← request body
```

Document the new endpoints in [API.md](API.md).

## Auth changes

Do **not** modify auth logic in this repo. Auth belongs in [`spring-auth`](https://github.com/andrewmugisa/Spring_auth).

After updating `spring-auth`:
1. Bump the `spring-auth` version in `pom.xml`
2. Run `./mvnw clean compile` to verify compatibility

## Running locally

```bash
cp env.example .env   # fill in your values
./mvnw spring-boot:run
```
