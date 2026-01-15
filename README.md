# Java Spring Microservices

This repository contains a fully featured **Java Spring Boot microservices project** designed to demonstrate real-world microservices architecture, communication patterns, and infrastructure setup.

The project showcases how independently deployable services can work together using REST, gRPC, event streaming, and centralized authentication, similar to production enterprise systems.

---

## 📌 Project Overview

This project implements a complete microservices ecosystem with multiple domain-driven services, an API gateway, and supporting infrastructure.

Each service is built using **Spring Boot** and runs independently while communicating with other services through well-defined interfaces.

---

## 🧩 Services Included

### 🔹 API Gateway
- Acts as the single entry point into the system
- Routes incoming requests to appropriate backend services
- Handles cross-cutting concerns such as authentication and request validation

### 🔹 Patient Service
- Manages patient-related domain logic
- Handles CRUD operations and data persistence
- Uses Spring Data JPA with a relational database

### 🔹 Billing Service
- Responsible for billing and payment-related logic
- Communicates with other services using **gRPC** for efficient inter-service calls

### 🔹 Auth Service
- Handles authentication and authorization
- Issues and validates **JWT tokens**
- Manages users and roles

### 🔹 Analytics Service
- Collects and aggregates data from multiple services
- Used for metrics, reporting, and monitoring use cases

---

## 🏗 Infrastructure

The project includes infrastructure setup required to run the system locally or in containerized environments:

- **Docker & Docker Compose** for containerization and orchestration
- **PostgreSQL** for persistent data storage
- **Kafka** for asynchronous, event-driven communication
- Centralized configuration using environment variables

---

## 🧪 Testing

- Integration tests are included to validate end-to-end flows across services
- Ensures correct communication between microservices and infrastructure components

---

## 🛠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT Authentication
- gRPC

### Infrastructure & DevOps
- Docker
- Docker Compose
- Apache Kafka
- PostgreSQL

---

## 🧠 Architecture Highlights

- Microservices-based architecture
- Independently deployable services
- REST and gRPC communication
- Event-driven messaging with Kafka
- Centralized authentication
- Production-style configuration management

---

