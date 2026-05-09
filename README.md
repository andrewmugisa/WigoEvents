# WiGO_EVENTS


---

# WiGO EVENTS API Documentation

This document describes the authentication and user endpoints for the MyDay application.

---

## **Authentication Endpoints**

### **1. Signup**

**POST `/auth/signup`**

Registers a new user.

**Request Body (JSON):**

```json
{
  "email": "user@example.com",
  "username": "username",
  "password": "password123"
}
```

**Response:**

* `200 OK` — Returns the created user.

**Notes:**

* No authentication required.
* Email and username must be unique.

---

### **2. Login**

**POST `/auth/login`**

Authenticates a registered user. Returns a JWT token.

**Request Body (JSON):**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK):**

```json
{
  "token": "jwt-token-string",
  "expiresIn": 3600000
}
```

**Notes:**

* Include the token as `Bearer` in Authorization header for protected endpoints.
* Token expires after the duration specified in `expiresIn` (milliseconds).

---

### **3. Verify Email**

**POST `/auth/verify`**

Verifies a newly registered user's email using a token.

**Request Body (JSON):**

```json
{
  "token": "verification-token"
}
```

**Response:**

* `200 OK` — `"Account verified Successfully"`
* `400 Bad Request` — Error message if token is invalid or expired

**Notes:**

* Public endpoint, no authentication required.
* Use the **Resend** endpoint if token is lost or expired.

---

### **4. Resend Verification Token**

**POST `/auth/resend?email=<user-email>`**

Resends the verification token to the specified email.

**Request Parameters:**

* `email` (string) — The email to resend the token to

**Response:**

* `200 OK` — `"Verification code resend Successfully"`
* `400 Bad Request` — Error message if resending fails

**Notes:**

* Public endpoint, no authentication required.

---

## **User Endpoints**

### **1. Get Authenticated User**

**GET `/users/me`**

Retrieves the currently authenticated user's profile.

**Authentication:**

* Required — Include JWT Bearer token in Authorization header

**Response (200 OK):**

```json
{
  "id": 1,
  "username": "username",
  "email": "user@example.com",
  "status": "ACTIVE"
}
```

**Notes:**

* Returns the data of the logged-in user only.

---

### **2. Get All Users**

**GET `/users/`**

Returns a list of all registered users.

**Authentication:**

* Required — JWT Bearer token

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "username": "username",
    "email": "user@example.com",
    "status": "ACTIVE"
  },
  ...
]
```

---

