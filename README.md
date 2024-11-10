# HomeChoice Backend
The backend service for the HomeChoice real estate management system. This service is built with Spring Boot and provides functionalities to manage properties, users, and roles within a real estate agency. It includes a hierarchical role-based access control (RBAC) system to manage user permissions and resource access.

## Technologies Used
- **Spring Boot**: Framework for building the application.
- **PostgreSQL**: Relational database for data persistence.
- **Spring Security**: For authentication and authorization.
- **JWT (JSON Web Tokens)**: For secure token-based authentication.
- **Swagger**: API documentation.
- **JPA/Hibernate**: For database access.

## Folder Structure
```
src/
└── main/
    └── java/
        └── com.homechoice/
            ├── aws/                     // Module for AWS integrations (S3 Bucket).
            ├── controller/              // Contains controllers (endpoints) that expose the API.
            ├── dto/                     // Contains Data Transfer Objects (DTOs), which are used for data transfer between layers.
            ├── model/                   // Contains data model entities representing database tables.
            ├── repository/              // Interfaces extending JpaRepository to interact with the database.
            ├── response/                // Classes to structure API responses.
            ├── security/                // Security module containing authentication and authorization configurations.
            │   ├── auth/                // Classes related to authentication, such as the authentication controller.
            │   ├── config/              // Security configurations, including CORS and authorization rules.
            │   └── jwt/                 // Classes to handle JWT tokens, including generation and validation.
            ├── service/                 // Contains the application's business logic.
            └── HomechoiceApplication    // Main class that starts the Spring Boot application.      
    └── resources/                       // Configuration files and static resources.
        ├── application.yaml             // Main configuration file for Spring Boot.
        └── import.sql                   // SQL file for initializing the database with sample data or necessary tables.
```

## API Documentation
The API is documented with Swagger. After running the application, you can access the documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Contributing
Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a pull request.
