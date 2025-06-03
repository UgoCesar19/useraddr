# User Address Backend

This is the backend of the User Address Management system, developed using **Spring Boot**, **Java**, and **JPA (Hibernate)** with **JWT-based authentication**.

It provides RESTful APIs for:

- User management
- Address management
- Authentication and authorization

This APIs are used in [this](https://github.com/UgoCesar19/usuaddr-front) Angular application.

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security with JWT
- Spring Data JPA (Hibernate)
- H2 (for development) or PostgreSQL (for production)
- Maven
- Lombok

---

## Requirements

- Java 17 or higher
- Maven
- Optional: PostgreSQL (for production profile)

---

## Installation & Running the Application

### 1. Clone the Repository

```bash
git clone https://github.com/UgoCesar19/useraddr.git
cd useraddr
```

### 2. Configure the Database

You can choose to run using:

#### a. **H2 In-Memory Database (default)**
Nothing to configure. Just run the application.

#### b. **PostgreSQL Database**

Create a docker container for the database:

```bash
docker run --name useraddr-postgres -e POSTGRES_DB=useraddr_db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres:15
```

### 3. Build and Run

```bash
./mvnw spring-boot:run
```

The app will be available at:  
📍 `http://localhost:8080`

---

## API Endpoints

### Authentication

- `POST /autenticar` — login and receive JWT
- `POST /atualizar-autenticacao` - revalidate JWT

### Users

- `POST /usuarios` — register new user
- `GET /usuarios` — list all users (Admin only)
- `PUT /usuarios/{id}` — update user
- `DELETE /usuarios/{id}` — delete user

### Addresses

- `GET /enderecos` — list addresses (admin sees all, user sees own)
- `POST /enderecos/{usuarioId}` — create address
- `PUT /enderecos/{enderecoId}` — update address
- `DELETE /enderecos/{enderecoId}` — delete address

---

## Authentication & Roles

- JWT-based authentication
- Two roles:
  - **ROLE_ADMIN**
  - **ROLE_NORMAL**

JWT must be sent in the `Authorization` header:

```
Authorization: Bearer <your_token>
```

---

## Author

Developed by [@UgoCesar19](https://github.com/UgoCesar19)

Next steps:
- Full test coverage.
