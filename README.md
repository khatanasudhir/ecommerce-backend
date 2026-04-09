# 🛒 E-Commerce Backend (Spring Boot)

## 🚀 Overview

This is a production-ready backend for an e-commerce application built using Spring Boot.
It provides secure REST APIs for authentication, product management, and cart operations.

---

## 🧰 Tech Stack

* Java 17
* Spring Boot 3
* Spring Security + JWT
* PostgreSQL
* Spring Data JPA (Hibernate)
* Swagger (OpenAPI)
* Docker

---

## 🔐 Features

* JWT-based Authentication
* Role-based Authorization (Admin/User)
* Product & Category Management
* Cart Management
* Pagination & Filtering APIs
* Secure REST APIs

---

## 📦 API Documentation

Swagger UI:
http://localhost:8090/swagger-ui.html

---

## ⚙️ Run Locally

```bash
mvn clean package
docker-compose up --build
```

---

## 📂 Project Structure

```text
controller/
service/
repository/
entity/
dto/
security/
exception/
```

---

## 📌 Future Improvements

* Order & Checkout Module
* Payment Integration
* Redis Caching
* Email Notifications
