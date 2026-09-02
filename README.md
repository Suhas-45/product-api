# Product API

A RESTful Product Management API built using Spring Boot, Spring Data JPA, PostgreSQL, Spring Security, JWT, and Swagger/OpenAPI.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL 18
- Spring Security
- JWT Authentication
- Maven
- JUnit 5 / Mockito
- Swagger / OpenAPI
- Docker / Docker Compose

## Architecture

Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
PostgreSQL

Spring Security with JWT protects the API endpoints.

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/auth/register` | Register user |
| POST | `/api/v1/auth/login` | Login and obtain JWT |
| POST | `/api/v1/auth/refresh` | Refresh access token |

### Products

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/products` | Get paginated products |
| GET | `/api/v1/products/{id}` | Get product by ID |
| POST | `/api/v1/products` | Create product |
| PUT | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |
| GET | `/api/v1/products/{id}/items` | Get product items |
| POST | `/api/v1/products/{id}/items` | Create item |

## Pagination

Example:

GET `/api/v1/products?page=0&size=10&sort=productName,asc`

## Security

The application uses:

- JWT access tokens
- Refresh tokens
- Refresh token rotation
- BCrypt password hashing
- Role-based authorization

Roles:

- USER
- ADMIN

Users can read products, while ADMIN users can create, update, and delete products.

## Validation

Jakarta Bean Validation is used for request validation.

Examples:

- Product name is required
- Username is required
- Password must contain at least 6 characters
- Item quantity must be at least 1

## Database

The application uses PostgreSQL.

Main tables:

- `product`
- `item`
- `users`
- `refresh_token`

Database indexes are added for frequently queried columns.

## Running Locally

Set the required environment variables:

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your-password"
$env:JWT_SECRET="your-secret-key"