# Ktor Banking API

## Overview
This project is a simple banking application built with **Ktor**, using **Exposed** as the database ORM and **H2** as the in-memory database. The API supports basic banking operations such as account creation, deposits, withdrawals, and money transfers.

## Features
- Create a new bank account
- Get account details by ID
- Deposit money into an account
- Withdraw money from an account
- Transfer money between accounts

## Technologies Used
- **Ktor** (Backend framework)
- **Kotlin** (Programming language)
- **Exposed** (Database ORM)
- **H2** (In-memory database)
- **Ktor Serialization** (JSON support)
- **Netty** (Server engine)

## Project Structure
```
/src/main/kotlin/com/mehmetgenc
  /dto        -> Data Transfer Objects (DTOs)
  /model      -> Database models
  /repository -> Database operations (CRUD)
  /service    -> Business logic
  /routes     -> API endpoints
  /config     -> Ktor configuration (Database, Serialization, Routing, etc.)
  Application.kt -> Main Ktor application file
```

## Installation
### Prerequisites
- JDK 17+
- Kotlin 1.9+
- Gradle

### Clone the Repository
```sh
git clone https://github.com/mehmetgencv/ktor-banking-api.git
cd ktor-banking-api
```

### Build and Run the Project
```sh
gradle build
```

Run the application:
```sh
gradle run
```

The server will start on `http://localhost:8080`.

## API Endpoints

### Create Account
**POST** `/accounts`
```json
{
  "name": "John Doe",
  "initialBalance": 5000.0
}
```
**Response:**
```json
1 // Account ID
```

### Get Account by ID
**GET** `/accounts/{id}`

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "balance": 5000.0
}
```

### Deposit Money
**PUT** `/accounts/deposit`
```json
{
  "id": 1,
  "amount": 1000.0
}
```
**Response:**
```json
"Deposit successful"
```

### Withdraw Money
**PUT** `/accounts/withdraw`
```json
{
  "id": 1,
  "amount": 2000.0
}
```
**Response:**
```json
"Withdrawal successful"
```

### Transfer Money
**POST** `/accounts/transfer`
```json
{
  "fromId": 1,
  "toId": 2,
  "amount": 1500.0
}
```
**Response:**
```json
"Transfer successful"
```

## License
This project is licensed under the MIT License.

