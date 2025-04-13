# 💈 Barber Shop API - Backend

A robust and secure backend for a Barber Shop Scheduling System built with **Spring Boot**, focusing on RESTful practices, security, scalability, and maintainability.

## Tecnologias Utilizadas
![Java](https://img.shields.io/badge/Java-100%25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-80%25-green)
![Docker](https://img.shields.io/badge/Docker-20%25-blue)
![Flyway](https://img.shields.io/badge/Flyway-15%25-lightblue)
![Swagger](https://img.shields.io/badge/Swagger-10%25-yellow)

### 📊 Linguagens mais usadas

![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=juliocbms&repo=Minhabarbearia&layout=compact&theme=transparent)

## 📌 Features

- ✅ **JWT Authentication**: Secure login and authorization using JSON Web Tokens
- 🔐 **Password Encryption**: Secured with Spring Security
- 🧬 **Flyway Migrations**: Database version control and migration management
- 🐳 **Docker Support**: Fully containerized for scalable deployment
- 📑 **Swagger UI**: Interactive API documentation

---

## 🛠 Technologies

- Java 17
- Spring Boot 3+
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- Swagger (springdoc-openapi)
- Docker / Docker Compose

---

## ⚙️ Getting Started

### Prerequisites

- Docker & Docker Compose installed

### Running with Docker

```bash
docker-compose up --build
```

The API will be available at: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html`

### API Routes

You can view and test all API routes via Swagger or Postman.

---

## 🔐 Security

- JWT token-based authentication and authorization
- Passwords encrypted with BCrypt
- Public routes: `/auth/**`
- Protected routes require `Authorization: Bearer <token>` header

---

## 🧪 Testing

You can run unit and integration tests with:

```bash
./mvnw test
```

---

## 📂 Project Structure

```bash
src
├── main
│   ├── java
│   │   └── com.example.barbershop
│   │       ├── controller
│   │       ├── dto
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       └── service
│   └── resources
│       └── application.yml
└── test
```

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
