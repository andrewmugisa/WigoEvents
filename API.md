# WigoEvents API Reference

Full request/response documentation for every endpoint.

---

## Auth endpoints — `/auth/**`

> These are provided by `spring-auth`. No code in this repo.

---

### POST /auth/signup

Register a new account.

**Request**
```json
{
  "name": "Test User",
  "email": "user@example.com",
  "password": "Test123!"
}
```

**200 OK**
```json
{
  "message": "Registration successful. Please check your email to verify your account."
}
```

**400 — email already registered**
```json
{
  "timestamp": "2026-06-10T03:47:29.051Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Email is already registered"
}
```

**400 — validation errors**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": {
    "email": "Invalid email format",
    "password": "Password must be at least 6 characters",
    "name": "Name is required"
  }
}
```

---

### POST /auth/login

Login and receive a JWT token.

**Request**
```json
{
  "email": "user@example.com",
  "password": "Test123!"
}
```

**200 OK**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "expiresIn": 3600000
}
```

**401 — wrong password**
```json
{
  "timestamp": "...",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password"
}
```

**403 — account not verified**
```json
{
  "timestamp": "...",
  "status": 403,
  "error": "Forbidden",
  "message": "Account not verified. Please check your email"
}
```

---

### POST /auth/verify

Verify account using 6-digit code from email.

**Request**
```json
{
  "email": "user@example.com",
  "verificationCode": "482910"
}
```

**200 OK**
```json
{
  "message": "Account verified successfully. You can now log in."
}
```

**400 — wrong code**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Verification code does not match"
}
```

**409 — already verified**
```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "Account is already verified"
}
```

**409 — code expired**
```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "Verification code has expired. Please request a new one"
}
```

---

### POST /auth/resend

Resend verification code to email.

**Request**
```json
{
  "email": "user@example.com"
}
```

**200 OK**
```json
{
  "message": "Verification code resent. Please check your email."
}
```

**409 — already verified**
```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "Account is already verified"
}
```

---

### POST /auth/logout

Blacklist the current token server-side. Token is immediately invalid even before expiry.

**Headers**
```
Authorization: Bearer <token>
```

**200 OK**
```json
{
  "message": "Logged out successfully."
}
```

**401 — no/invalid token**
```json
{
  "timestamp": "...",
  "status": 401,
  "error": "Unauthorized",
  "message": "..."
}
```

---

## User endpoints — `/users/**`

> All require `Authorization: Bearer <token>` header unless noted.

---

### GET /users/me

Get the currently authenticated user.

**Headers**
```
Authorization: Bearer <token>
```

**200 OK**
```json
{
  "userId": 1,
  "name": "Test User",
  "email": "user@example.com",
  "username": "testuser_4821",
  "enabled": true,
  "createdAt": "2026-05-10T05:25:46.778627Z"
}
```

**401 — missing or invalid token**
```json
{
  "timestamp": "...",
  "status": 401,
  "error": "Unauthorized",
  "message": "..."
}
```

---

### GET /users/

Get all registered users.

**Headers**
```
Authorization: Bearer <token>
```

**200 OK**
```json
[
  {
    "userId": 1,
    "name": "Test User",
    "email": "user@example.com",
    "username": "testuser_4821",
    "enabled": true,
    "createdAt": "2026-05-10T05:25:46.778627Z"
  }
]
```

---

### PATCH /users/me/username

Update the display username of the current user.

**Headers**
```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request**
```json
{
  "username": "mynewusername"
}
```

**Rules** — username is lowercased, spaces and special characters stripped, minimum 3 characters.

**200 OK**
```json
{
  "userId": 1,
  "name": "Test User",
  "email": "user@example.com",
  "username": "mynewusername",
  "enabled": true,
  "createdAt": "2026-05-10T05:25:46.778627Z"
}
```

**400 — username taken**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Username is already taken"
}
```

**400 — too short**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Username must be at least 3 characters"
}
```

---

### DELETE /users/me

Permanently delete the current user's account. Cannot be undone.

**Headers**
```
Authorization: Bearer <token>
```

**200 OK**
```json
{
  "message": "Account deleted successfully."
}
```

**401 — missing or invalid token**
```json
{
  "timestamp": "...",
  "status": 401,
  "error": "Unauthorized",
  "message": "..."
}
```

---

## Common error responses

| Status | Meaning |
|---|---|
| 400 | Bad request — validation failed or business rule violated |
| 401 | Unauthorized — missing, invalid, or expired token |
| 403 | Forbidden — authenticated but action not allowed (e.g. unverified account) |
| 404 | Not found |
| 409 | Conflict — state mismatch (e.g. already verified, duplicate) |
| 500 | Unexpected server error |
