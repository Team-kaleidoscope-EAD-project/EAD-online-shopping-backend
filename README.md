# Kalei Online Fashion Store - Backend

## Overview
The Kalei Online Shopping Platform backend is a robust, scalable, and modular microservices-based application designed to facilitate seamless e-commerce operations. Built using **Spring Boot**, it features four distinct microservices:

- **Order Service**: Manages order-related operations, including creation, updates, and tracking.  
- **Inventory Service**: Handles stock management and availability using **MySQL**.  
- **Product Service**: Stores and retrieves detailed product information using **MongoDB** for scalable document storage.  
- **Feedback Service**: Collects and manages customer reviews and ratings, backed by **MySQL**.  

### Key Features
- **Scalable Architecture**: Microservices design for modularity and easy scaling.
- **Centralized Security**: Authentication and authorization powered by **Keycloak**.
- **Service Discovery**: Efficient inter-service communication using **Eureka Server**.
- **Containerization**: Simplified deployment through **Docker** and **Docker Compose**.
- **Asynchronous Processing**: Event-driven architecture enabled by **Kafka**.
- **Payment Integration**: Secure transactions with **Stripe**.
- **Dedicated Databases**: Separate databases for each service, ensuring data optimization and fault isolation.

## Architecture

### Microservices Overview
1. **Order Service**: Facilitates order lifecycle management, including tracking and updates.  
2. **Inventory Service**: Maintains stock levels and product availability.  
3. **Product Service**: Handles dynamic product details, optimized with document storage.  
4. **Feedback Service**: Ensures structured customer feedback storage for insights.

### Supporting Components
- **API Gateway**: A single entry point for routing client requests to appropriate microservices.  
- **Authentication & Authorization**: Centralized using **Keycloak** with integration via **Spring Security**.  
- **Asynchronous Communication**: Implemented with **Kafka** for reliable event handling.  
- **Payment Gateway**: Secured with **Stripe** for processing transactions.  

## Prerequisites
Before setup, ensure you have the following:
- **Java** 17 or later.
- **Docker** and **Docker Compose** installed.
- Local instances of **MySQL**, **MongoDB**, and **PostgreSQL** (optional if not using Docker).

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/Team-kaleidoscope-EAD-project/EAD-online-shopping-backend.git
cd EAD-online-shopping-backend
```

### 2. Configure Environment Variables
Create a `.env` file based on the provided `env.example` template:
```plaintext
DATABASE_ROOT_USERNAME=root
DATABASE_ROOT_PASSWORD=yourpassword
```

### 3. Build and Start the Services
Run all services with the following command:
```bash
docker-compose up --build
```

### 4. Access Services
- **API Gateway**: [http://localhost:8080](http://localhost:8080)  
- **Keycloak Admin Console**: [http://localhost:9090](http://localhost:9090)  

All service-specific endpoints are accessible through the API Gateway.

## Database Configuration
- **Inventory Service** and **Feedback Service**: Use **MySQL**.
- **Product Service**: Uses **MongoDB** for flexible storage.
- **Keycloak**: Operates on a **PostgreSQL** database.

Ensure configurations match the `application.properties` files in each service.

## Technologies Used
- **Backend Framework**: Spring Boot  
- **Databases**: MySQL, MongoDB, PostgreSQL  
- **Authentication**: Keycloak with Spring Security  
- **Service Discovery**: Eureka Server  
- **Asynchronous Messaging**: Kafka  
- **Payment Gateway**: Stripe  
- **Containerization**: Docker & Docker Compose  

## Directory Structure
```plaintext
EAD-online-shopping-backend/
├── order/                 # Order Service
├── inventory/             # Inventory Service
├── product/               # Product Service
├── feedback/              # Feedback Service
├── apigateway/            # API Gateway
├── servicediscovery/      # Eureka Server
├── docker-compose.yml     # Docker Compose configuration
├── pom.xml                # Maven configuration file
├── .env                   # Environment variables file
├── .env.example           # Sample environment variables
├── .gitignore             # Git ignore file
```

## Contributing
Contributions are welcome! Follow these steps:
1. Fork the repository.  
2. Create a feature branch.  
3. Submit a pull request with detailed changes.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

---
