In this project you’ll find a fully-featured microservices ecosystem that includes:

API Gateway – Entry point into the system to route and secure incoming requests.

Patient Service – Manages patient domain logic and persistence.

Billing Service – Handles billing logic, often communicating via gRPC for inter-service calls.

Auth Service – Responsible for authentication and authorization (JWT, users, roles).

Analytics Service – Aggregates service data for metrics or reporting.

Infrastructure – Scripts and definitions for Docker, Kafka, and database containers.

Integration Tests – End-to-end test coverage across services.

This setup mirrors real-world architectures where systems are composed of independently deployable services communicating via REST, gRPC, Kafka, and authenticated through centralized tokens.

🛠 Tech Stack

The core technologies and frameworks used include:

Backend & Frameworks
Technology	Purpose
Java 17+ / JVM	Primary language for services.
Spring Boot	Creates production-ready microservices with minimal configuration.
Spring Data JPA	Database access and ORM.
Spring Security / JWT	Secures service endpoints with token-based auth.
gRPC	High-performance inter-service communication.
Infrastructure
Tool	Purpose
Docker	Containerizes each microservice.
Docker Compose	Orchestrates multi-container environments (PostgreSQL, Kafka, etc.).
Kafka	Event streaming between services.
PostgreSQL	Persistent relational storage for domain data.
🧱 Architecture Highlights

This microservices architecture follows common design patterns used in enterprise systems:

Service-Oriented Decomposition
Each domain (patients, billing, auth) is its own Spring Boot application.

API Gateway Pattern
A unified entry point that routes requests, handles cross-cutting concerns (like auth), and simplifies client interaction.

Inter-Service Communication
Services communicate either via REST or gRPC where high performance is required (e.g., billing) — perfect for low-latency calls between services.

Event Messaging
Kafka is included to model asynchronous event-driven communication (e.g., notifications, analytics).

Environment-Based Config
Each microservice reads configuration from environment variables or Docker Compose overrides — allowing easy deployments across environments.
