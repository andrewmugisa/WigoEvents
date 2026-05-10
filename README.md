---

## Auth Layer — Manual Test Guide (Final)

---

### 1. Signup — happy path

`POST http://localhost:8080/auth/signup`

```json
{
  "email": "wigo.inc@gmail.com",
  "password": "123456",
  "name": "Test User"
}
```

**Expected 200**
```json
{
  "userId": 1,
  "username": "testuser_4821",
  "name": "Test User",
  "email": "wigo.inc@gmail.com",
  "enabled": false,
  "createdAt": "2026-05-10T00:00:00Z"
}
```
✅ No `password`, `verificationCode`, `verificationCodeExpiration` in response
✅ `username` is auto-generated from name
✅ `name` is populated
✅ `createdAt` is populated
✅ `enabled` is `false`

---

### 2. Signup — validation errors

`POST http://localhost:8080/auth/signup`

```json
{
  "email": "not-an-email",
  "password": "123",
  "name": ""
}
```

**Expected 400**
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

### 3. Signup — duplicate email

`POST http://localhost:8080/auth/signup` *(same email as test 1)*

```json
{
  "email": "wigo.inc@gmail.com",
  "password": "123456",
  "name": "Test Two"
}
```

**Expected 400**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Email is already registered"
}
```

---

### 4. Signup — name that generates a taken username

*Simulate collision by registering two users with the same name. The second should get a different auto-generated suffix.*

First signup:
```json
{ "email": "user1@gmail.com", "password": "123456", "name": "John Doe" }
```
→ gets username e.g. `johndoe`

Second signup:
```json
{ "email": "user2@gmail.com", "password": "123456", "name": "John Doe" }
```

**Expected 200** — username will be `johndoe_XXXX` (different suffix, not a conflict error)
✅ Uniqueness handled server-side transparently

---

### 5. Login before verification

`POST http://localhost:8080/auth/login`

```json
{
  "email": "wigo.inc@gmail.com",
  "password": "123456"
}
```

**Expected 403**
```json
{
  "timestamp": "...",
  "status": 403,
  "error": "Forbidden",
  "message": "Account not verified. Please check your email"
}
```

---

### 6. Verify — wrong email

`POST http://localhost:8080/auth/verify`

```json
{
  "email": "wrong@gmail.com",
  "verificationCode": "123456"
}
```

**Expected 400**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "User not found"
}
```

---

### 7. Verify — wrong code

`POST http://localhost:8080/auth/verify`

```json
{
  "email": "wigo.inc@gmail.com",
  "verificationCode": "000000"
}
```

**Expected 400**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Verification code does not match"
}
```

---

### 8. Verify — happy path

`POST http://localhost:8080/auth/verify`
*(use the code from the email sent in test 1)*

```json
{
  "email": "wigo.inc@gmail.com",
  "verificationCode": "XXXXXX"
}
```

**Expected 200**
```json
{
  "message": "Account verified successfully"
}
```

---

### 9. Verify — already verified

`POST http://localhost:8080/auth/verify` *(repeat test 8)*

```json
{
  "email": "wigo.inc@gmail.com",
  "verificationCode": "XXXXXX"
}
```

**Expected 409**
```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "Account is already verified"
}
```

---

### 10. Resend — user not found

`POST http://localhost:8080/auth/resend`

```json
{
  "email": "nobody@gmail.com"
}
```

**Expected 400**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "User not found"
}
```

---

### 11. Resend — already verified

`POST http://localhost:8080/auth/resend`

```json
{
  "email": "wigo.inc@gmail.com"
}
```

**Expected 409**
```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "Account is already verified"
}
```

---

### 12. Resend — happy path

*Sign up a fresh account, do not verify it, then:*

`POST http://localhost:8080/auth/resend`

```json
{
  "email": "fresh@gmail.com"
}
```

**Expected 200**
```json
{
  "message": "Verification code resent successfully"
}
```
✅ New code arrives in email
✅ Old code no longer works (test 7 with old code should now fail)

---

### 13. Login — wrong password

`POST http://localhost:8080/auth/login`

```json
{
  "email": "wigo.inc@gmail.com",
  "password": "wrongpassword"
}
```

**Expected 401**
```json
{
  "timestamp": "...",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password"
}
```

---

### 14. Login — happy path

`POST http://localhost:8080/auth/login`

```json
{
  "email": "wigo.inc@gmail.com",
  "password": "123456"
}
```

**Expected 200**
```json
{
  "token": "eyJ...",
  "expiresIn": 3600000
}
```
✅ Save the token for tests 15–17

---

### 15. Get current user — happy path

`GET http://localhost:8080/users/me`
`Authorization: Bearer <token from test 14>`

**Expected 200**
```json
{
  "userId": 1,
  "username": "testuser_4821",
  "name": "Test User",
  "email": "wigo.inc@gmail.com",
  "enabled": true,
  "createdAt": "2026-05-10T00:00:00Z"
}
```
✅ No sensitive fields

---

### 16. Get current user — no token

`GET http://localhost:8080/users/me`
*(no Authorization header)*

**Expected 401**
```json
{
  "timestamp": "...",
  "status": 401,
  "error": "Unauthorized",
  "message": "..."
}
```

---

### 17. Get current user — invalid token

`GET http://localhost:8080/users/me`
`Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.fake.token`

**Expected 401**

---

### 18. Update username — happy path

`PATCH http://localhost:8080/users/me/username`
`Authorization: Bearer <token from test 14>`

```json
{
  "username": "mynewusername"
}
```

**Expected 200**
```json
{
  "userId": 1,
  "username": "mynewusername",
  "name": "Test User",
  "email": "wigo.inc@gmail.com",
  "enabled": true,
  "createdAt": "2026-05-10T00:00:00Z"
}
```

---

### 19. Update username — already taken

`PATCH http://localhost:8080/users/me/username`
`Authorization: Bearer <token from test 14>`

```json
{
  "username": "mynewusername"
}
```

**Expected 400**
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Username is already taken"
}
```

---

### 20. Update username — no token

`PATCH http://localhost:8080/users/me/username`
*(no Authorization header)*

**Expected 401**

---

### 21. Expired verification code

*Temporarily set expiry to 1 minute for testing:*

```java
private static final int VERIFICATION_EXPIRY_MINUTES = 1;
```

Sign up → wait 1 minute → try to verify:

**Expected 409**
```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "Verification code has expired. Please request a new one"
}
```

---

### Pass criteria summary

| # | Endpoint | Scenario | Expected status |
|---|---|---|---|
| 1 | POST /auth/signup | Happy path | 200 |
| 2 | POST /auth/signup | Validation errors | 400 |
| 3 | POST /auth/signup | Duplicate email | 400 |
| 4 | POST /auth/signup | Duplicate name collision | 200 (different suffix) |
| 5 | POST /auth/login | Unverified account | 403 |
| 6 | POST /auth/verify | Wrong email | 400 |
| 7 | POST /auth/verify | Wrong code | 400 |
| 8 | POST /auth/verify | Happy path | 200 |
| 9 | POST /auth/verify | Already verified | 409 |
| 10 | POST /auth/resend | User not found | 400 |
| 11 | POST /auth/resend | Already verified | 409 |
| 12 | POST /auth/resend | Happy path | 200 |
| 13 | POST /auth/login | Wrong password | 401 |
| 14 | POST /auth/login | Happy path | 200 |
| 15 | GET /users/me | Valid token | 200 |
| 16 | GET /users/me | No token | 401 |
| 17 | GET /users/me | Invalid token | 401 |
| 18 | PATCH /users/me/username | Happy path | 200 |
| 19 | PATCH /users/me/username | Username taken | 400 |
| 20 | PATCH /users/me/username | No token | 401 |
| 21 | POST /auth/verify | Expired code | 409 |
