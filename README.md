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
            ├── aws/                     // Module for AWS integrations (e.g., S3), handling configurations and AWS-specific services.
            ├── controller/              // Contains controllers (endpoints) that expose the API.
            │   ├── property/            // Controllers related to properties, handling HTTP requests for property management.
            │   ├── user/                // Controllers related to users, managing operations on user accounts.
            ├── dto/                     // Contains Data Transfer Objects (DTOs), which are used for data transfer between layers.
            │   ├── property/            // DTOs specific to properties, transferring property data to/from the frontend.
            │   └── user/                // DTOs specific to users.
            ├── model/                   // Contains data model entities representing database tables.
            │   ├── property/            // Entities related to properties.
            │   └── user/                // Entities related to users.
            ├── repository/              // Interfaces extending JpaRepository to interact with the database.
            │   ├── property/            // Repositories specific to managing CRUD operations for properties.
            │   └── user/                // Repositories specific to managing CRUD operations for users.
            ├── response/                // Classes to structure API responses.
            │   └── ApiResponse          // Generic response class, used to standardize API responses, e.g., for success/error messages.
            ├── security/                // Security module containing authentication and authorization configurations.
            │   ├── auth/                // Classes related to authentication, such as the authentication controller.
            │   ├── config/              // Security configurations, including CORS and authorization rules.
            │   └── jwt/                 // Classes to handle JWT tokens, including generation and validation.
            ├── service/                 // Contains the application's business logic.
            │   ├── property/            // Services specific to property-related business logic, handling operations for properties.
            │   └── user/                // Services specific to user-related business logic, handling operations for users.
            └── HomechoiceApplication    // Main class that starts the Spring Boot application.      
    └── resources/                       // Configuration files and static resources.
        ├── application.yaml             // Main configuration file for Spring Boot, setting up properties such as database connection, security, and other environment-specific settings.
        └── import.sql                   // SQL file for initializing the database with sample data or necessary tables. Automatically executed on startup if configured in `application.yaml`.
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
