# 🛒 E-Commerce Backend (Spring Boot)

A production-ready E-Commerce Backend built using Spring Boot.  
It supports authentication, product management, cart, orders, and admin operations.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Docker & Docker Compose
- Maven

---

## 🔥 Features

### 👤 Authentication
- User Registration
- Login with JWT
- Role-based access (USER / ADMIN)

---

### 🛍️ Product Management
- Create Product (Admin)
- Update Product (Admin)
- Delete Product (Admin)
- Get all products (Pagination + Sorting)
- Search products (name, category, price range)
- Get product by ID

---

### 🛒 Cart
- Add to cart
- Update quantity
- Remove item
- View cart

---

### 📦 Orders
- Checkout system
- Order history (pagination)
- Order details

---

### 🔄 Order Management
- Cancel order (User)
- Update order status (Admin)
- Get all orders (Admin)

#### Order Lifecycle:
- PENDING
- SHIPPED
- DELIVERED
- CANCELLED

---

### 🛡️ Security
- JWT Authentication
- Role-based authorization using Spring Security

---

## 📦 API Endpoints

---

### 🔐 Auth APIs

| Method | Endpoint |
|--------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |

---

### 🛍️ Product APIs

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | /api/products | ADMIN |
| PUT | /api/products/{id} | ADMIN |
| DELETE | /api/products/{id} | ADMIN |
| GET | /api/products | PUBLIC |
| GET | /api/products/{id} | PUBLIC |
| GET | /api/products/search | PUBLIC |

---

### 🛒 Cart APIs

| Method | Endpoint |
|--------|----------|
| POST | /api/cart |
| GET | /api/cart |
| PUT | /api/cart |
| DELETE | /api/cart/{id} |

---

### 📦 Order APIs

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | /api/orders | USER |
| GET | /api/orders | USER |
| GET | /api/orders/{id} | USER |
| PUT | /api/orders/{id}/cancel | USER |
| PUT | /api/orders/admin/{id}/status | ADMIN |
| GET | /api/orders/admin | ADMIN |

---

## 🐳 Run with Docker

### 1️⃣ Build and Run

```bash
docker-compose up --build

## 📦 API Documentation

Swagger UI:
http://localhost:8090/swagger-ui.html

---



