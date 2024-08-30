# AccountController

## Overview

The `AccountController` is a REST controller in the Spring framework responsible for managing user accounts, including registration, login, account updates, password updates, and account deletion. This controller ensures secure operations through session validation.

## Prerequisites

- [Java](https://www.oracle.com/java/technologies/downloads/#jdk17-windows) (version 11 or higher)
- [Maven](https://maven.apache.org/)
- [Spring Boot](https://spring.io/projects/spring-boot) (version 3.3.3 or higher)

## Endpoints Overview

| **Method** | **Endpoint**                    | **Description**                    | **Request Body**     | **Headers**   |
|------------|---------------------------------|------------------------------------|----------------------|---------------|
| `POST`     | `/users/register`               | Register a new account             | `AccountDTO`         | None          |
| `POST`     | `/users/login`                  | Login to an account                | `AccountDTO`         | None          |
| `PATCH`    | `/users/{id}/password`          | Update account password            | `PasswordUpdateRequest` | `Session-ID` |
| `PUT`      | `/users/{id}`                   | Update account information         | `AccountDTO`         | `Session-ID`  |
| `GET`      | `/users`                        | Retrieve all accounts              | None                 | `Session-ID`  |
| `GET`      | `/users/{id}`                   | Retrieve account by ID             | None                 | `Session-ID`  |
| `DELETE`   | `/users/{id}`                   | Delete an account                  | None                 | `Session-ID`  |

## Detailed Endpoints

### 1. Register a New Account

- **Endpoint:** `POST /users/register`
- **Description:** Registers a new user account.
- **Request Body:** 
  ```json
  {
    "fullName": "john_doe",
    "password": "password123",
    "email": "john@example.com"
  }
  
  Successful API Response
    ```json
  {
    "data": {
        "accountId": 1,
        "fullName": "john_doe",
        "email": "john@example.com",
        "password": "$2a$10$uA/2BK9fAifG0ZlUNTwo2enuXWMQ1ZCxbbOl.AH/vbEyELS6OCwEq"
    },
    "message": "Account created successfully",
    "status": "success"
}
  
  
### 2. Login to that Account

- **Endpoint:** `POST /users/login`
- **Description:** logs in to user account.
- **Request Body:** 

  ```json
   {
    "password": "password123",
    "email": "john@example.com"
  }

## API Response

The API response for a successful login 
```json
{
    "data": "b8c314bb-b47e-402a-b33c-23fb1ba8abc2",
    "message": "Login successful",
    "status": "success"
}

This will generate a sample Session-ID which we will pass as header for the below request :

### 3. update user details ( Get the user id from the response hit while registering with register api)

- **Endpoint:** `PUT /users/1`
- **Header:** Session-ID from login success response data
- **Description:** Updates user details.
- **Request Body:** 
  ```json
  {
    "username": "john smith",
    "password": "password1234",
  }
  
### 4. update password 

- **Endpoint:** `PATCH /users/1`
- **Description:** Updates user details.
- **Header:** Session-ID from login success response data
- **Request Body:** 
  ```json
 {
      "newPassword": "password123456"
 }
  
 ### 5. Get Account by Id 

- **Endpoint:** `PATCH /users/1?&Session-ID=78a8730d-3f6b-4e3d-a80a-e2be957c2402`
- **Header:** Session-ID from login success response data
- **Description:** Updates user details.
- **Request Body:** 
  ```json
 {
      "newPassword": "password123456"
 } 
 
  ### 6. Get Account by Id 

- **Endpoint:** `PATCH /users/1?&Session-ID=78a8730d-3f6b-4e3d-a80a-e2be957c2402`
- **Header:** Session-ID from login success response data
- **Description:** Updates user details.
- **Request Body:** 
  ```json
 {
      "newPassword": "password123456"
 } 
  
   ### 6. Delete Account by Id 

- **Endpoint:** `DELETE /users/1`
- **Header:** Session-ID from login success response data
- **Description:** Updates user details.

   ### 6. Get all Users 

- **Endpoint:** `DELETE /users/1`
- **Header:** Session-ID from login success response data
- **Description:** Updates user details.
  
  
