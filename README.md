# Fitness Microservices System

This project is a backend system built using Spring Boot microservices.
All services are independent but work together as one complete system.

The main focus of this project is:

* Clean microservices architecture
* Centralized configuration
* Easy setup for anyone who downloads the project

---

## 🔷 Architecture Diagram

```plaintext
Git (Config Repo)
        │
        ▼
Config Server (8888)
        │
        ▼
Eureka Server (8761)
        │
 ┌──────┼──────────────┐
 ▼      ▼              ▼
User   Activity       AI
8081    8082         8083
```

---

## What this project contains

* User Service → manages users
* Activity Service → handles fitness activities
* AI Service → generates recommendations
* Eureka Server → service discovery
* Configuration Server → centralized config management
* Config Repo → all configuration files in one place
* Shared Module → common DTOs / contracts

---

## Project Structure

```
fitness-microservices/
│── activity-service/
│── ai-service/
│── user-service/
│── eureka-server/
│── configuration-server/
│── config-repo/
│── fitness-event-contract/
│── docker-compose.yml
│── README.md
```

---

## How configuration works

* All configs are stored in `config-repo`
* Configuration Server reads configs from GitHub
* All services fetch config at startup

Example:

```
spring.config.import=configserver:http://localhost:8888
```

This means:

* No hardcoded configs inside services
* Easy to manage and update configs

---

## How to run the project

### Option 1 (Recommended) — Using Docker

Run everything with one command:

```bash
docker-compose up --build
```

This will start:

* Config Server
* Eureka Server
* All microservices

---

### Option 2 — Manual Run

Start services in this order:

1. configuration-server
2. eureka-server
3. user-service
4. activity-service
5. ai-service

---

## Service URLs

* Config Server → http://localhost:8888
* Eureka Dashboard → http://localhost:8761
* User Service → http://localhost:8081
* Activity Service → http://localhost:8082
* AI Service → http://localhost:8083

---

## Important points

* Config Server must start first
* Service name must match config file name

Example:

```
activity-service → activity-service.yml
```

* Do not commit:

  * target/
  * .env
  * IDE files

---

## Why this approach

This setup is close to real company projects:

* All services in one repo → easy to manage
* Central config → no duplication
* Docker support → easy for anyone to run
* Clean structure → easy to understand

---

## Future improvements (next steps)

* Add API Gateway
* Add Kafka for async communication
* Add centralized logging
* Add monitoring (Actuator, Prometheus)

---

## Author

Amol Gadkari
Java Developer (Microservices / Spring Boot)
