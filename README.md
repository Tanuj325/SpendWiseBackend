<div align="center">

# 💸 SpendWise Backend

### REST API for SpendWise — Built with Spring Boot

Secure backend powering the SpendWise personal finance tracker. Handles user authentication with JWT and expense analytics via a clean RESTful API.

[

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)

](https://spring.io/projects/spring-boot)
[

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

](https://www.oracle.com/java/)
[

![MongoDB](https://img.shields.io/badge/MongoDB-Database-47A248?style=for-the-badge&logo=mongodb&logoColor=white)

](https://www.mongodb.com/)
[

![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)

](https://www.docker.com/)

</div>

---

## 📱 About

**SpendWise Backend** is a RESTful API built with Spring Boot that serves as the backbone of the SpendWise mobile application. It handles user authentication with JWT, stores expense data in MongoDB, and exposes endpoints for expense analytics consumed by the React Native frontend.

> Frontend repo: [SpendWiseFrontend](https://github.com/Tanuj325/SpendWiseFrontend)

---

## ✨ Features

- 🔐 **JWT Authentication** — Stateless signup/login via Spring Security + JJWT
- 👤 **User Management** — Register, fetch by ID, fetch by email
- 💰 **Expense Tracking** — Add and retrieve expenses per user
- 📊 **Category Totals** — Total spending grouped by category
- 🗓️ **Monthly Report** — Category-wise and day-wise breakdown for any month/year
- 📈 **Last 6 Months Trend** — Month-wise spending for the last 6 months

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Framework | Spring Boot 3.5.4 |
| Language | Java 21 |
| Database | MongoDB |
| Security | Spring Security + JWT (JJWT 0.11.5) |
| ORM | Spring Data MongoDB |
| Validation | Spring Boot Validation |
| Build Tool | Maven |
| Containerization | Docker (multi-stage build) |
| Utilities | Lombok, dotenv-java 3.0.0 |

---

## 📁 Project Structure

```
SpendWiseBackend/
├── src/
│   └── main/
│       └── java/
│           └── org/example/
│               ├── config/         # Security & app configuration
│               ├── controller/     # REST controllers
│               ├── dto/            # Request DTOs (LoginRequest, SignupRequest)
│               ├── entity/         # MongoDB document classes
│               ├── repository/     # MongoDB repositories
│               ├── service/        # Business logic
│               ├── util/           # Utility classes (JWT etc.)
│               └── Application.java
├── Dockerfile
├── pom.xml
└── mvnw
```

---

## 🔌 API Endpoints

### Auth — `/auth`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/signup` | Register a new user |
| POST | `/auth/login` | Login and receive JWT token |

### Users — `/users`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/users` | Get all users |
| POST | `/users` | Create a user |
| GET | `/users/{id}` | Get user by ID |
| GET | `/users/email/{email}` | Get user by email |

### Expenses — `/expenses`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/expenses/{userId}` | Add a new expense |
| GET | `/expenses/{userId}` | Get all expenses for a user |
| GET | `/expenses/category/{userId}` | Get total spending per category |
| GET | `/expenses/report/{userId}?month=&year=` | Monthly report (category-wise + day-wise) |
| GET | `/expenses/monthly/{userId}` | Spending trend for last 6 months |

---

## 🚀 Getting Started

### Prerequisites

- **Java 21**
- **Maven 3.9+**
- **MongoDB** running locally or a MongoDB Atlas URI
- **Docker** (optional)

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/Tanuj325/SpendWiseBackend.git
cd SpendWiseBackend
```

### Environment Setup

Create a `.env` file in the root directory:

```env
MONGO_URI=mongodb://localhost:27017/spendwise
JWT_SECRET=your_jwt_secret_key
```

### Running Locally

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# OR build and run the JAR
./mvnw clean package -DskipTests
java -jar target/SpendWise-1.0-SNAPSHOT.jar
```

The server starts on **http://localhost:8080**

---

## 🐳 Running with Docker

```bash
# Build the Docker image
docker build -t spendwise-backend .

# Run the container
docker run -p 8080:8080 --env-file .env spendwise-backend
```

---

## 📦 Key Dependencies

```xml
spring-boot-starter-web
spring-boot-starter-data-mongodb
spring-boot-starter-security
spring-boot-starter-validation
jjwt-api / jjwt-impl / jjwt-jackson  →  0.11.5
lombok
dotenv-java  →  3.0.0
```

---

## 👤 Author

**Tanuj** — [@Tanuj325](https://github.com/Tanuj325)

---

## 📄 License

This project is for academic and educational purposes.
