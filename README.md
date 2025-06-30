# 🚗 Rental Car Backend System | Java/Spring Boot

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 🔗 Full System Architecture
A complete backend system for car rental management, featuring JWT authentication, role-based authorization, and RESTful API design.
This backend service is designed to work seamlessly with its [React frontend counterpart](https://github.com/Giorgio-Rossi/react-rental-car) to form a complete car rental platform:
<br>
## ✨ Key Features
- **JWT Authentication** (Spring Security 6)
- **Role-based access control** (Admin/Customer)
- **REST API** with HATEOAS support
- **Database**: PostgreSQL with Flyway migrations
- **Testing**: JUnit 5, Mockito, Testcontainers
- **API Documentation**: OpenAPI 3 (Swagger UI)
- **Docker-ready** containerization

## 📦 Tech Stack
| Category       | Technologies                          |
|----------------|---------------------------------------|
| Core           | Java 17, Spring Boot 3.1.5           |
| Security       | JWT, Spring Security 6                |
| Database       | PostgreSQL, Hibernate, Flyway         |
| Testing        | JUnit 5, Mockito, Testcontainers      |
| Tools          | Docker, Maven, Lombok                 |
| Documentation  | OpenAPI 3, Swagger UI                 |


## 🚀 Quick Start
### Prerequisites
- Java 17+
- PostgreSQL 15+
- Maven 3.9+

### Local Development
1. Clone the repo:
   ```bash
   git clone https://github.com/Giorgio-Rossi/backend-rental-car.git
2. Configure database in application.yml:
   ```bash
     spring:
       datasource:
          url: jdbc:postgresql://localhost:5432/rentalcar
          username: youruser
          password: yourpass
   
3. Clean & Install Maven:
   
   ```bash
   mvn clean install
4. Start the application:
   
  ```bash
 mvn spring-boot:run
  ```

## 🐳 Docker Deployment
 ```bash
  docker-compose up --build
 ```
Access Swagger UI at: http://localhost:8080/swagger-ui.html

🌐 API Endpoints
| Endpoint             | Method | Description                 | Auth Required   |
|----------------------|--------|-----------------------------|-----------------|
| /api/auth/**         | POST   | Authentication & registration | No              |
| /api/cars/**         | GET    | Car inventory management     | Yes (Admin)     |
| /api/reservations/** | POST   | Booking operations           | Yes (Customer)  |

Example JWT Auth Flow:
 ```bash
 POST /api/auth/login
  Content-Type: application/json
  {
    "email": "admin@rental.com",
    "password": "admin123"
  }
 ```

## 🧪 Testing Strategy
- Unit Tests: 85% coverage (Service layer)

- Integration Tests: Test containers for DB tests

- Security Tests: MockMVC for auth endpoints

Run tests:
 ```bash
  mvn test
 ```

## 📂 Project Structure
 ```bash
 src/
├── main/
│   ├── java/
│   │   └── com/rentalcar/
│   │       ├── config/       # Security & App config
│   │       ├── controllers/  # REST Controllers
│   │       ├── dto/          # Data Transfer Objects
│   │       ├── entities/     # JPA Entities
│   │       ├── exceptions/   # Custom exceptions
│   │       ├── repositories/ # JPA Repositories
│   │       ├── security/     # JWT components
│   │       └── services/     # Business logic
│   └── resources/
│       ├── db/migration      # Flyway scripts
│       └── application.yml   # Config
└── test/                     # Comprehensive tests
 ```


### Other Features
- **End-to-end JWT flow**: Frontend handles token storage/refresh
- **Consistent DTOs**: Shared data models between frontend and backend
- **Unified CI/CD**: Both components can be deployed together via Docker Compose

### Recommended Setup
1. First launch this backend service (follow instructions below)
2. Then setup the [frontend application](https://github.com/Giorgio-Rossi/react-rental-car#-quick-start)
3. Configure the frontend's `.env` to point to your backend:
   ```env
   REACT_APP_API_URL=http://localhost:8080/api
   ```
   
## 🤝 How to Contribute
1. Fork the project
2. Create your feature branch (git checkout -b feature/AmazingFeature)
3. Commit your changes (git commit -m 'Add some AmazingFeature')
4. Push to the branch (git push origin feature/AmazingFeature)
5. Open a Pull Request


Connect with me: [[LinkedIn](https://www.linkedin.com/in/rossi-giorgio/)]
