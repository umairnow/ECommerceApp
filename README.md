# E-commerce Watch Checkout API
This application provides a Spring Boot API for a basic e-commerce checkout system focused on watches.

## Getting Started

### Prerequisites
- Java 22+
- Maven build tool
### Installation
1. Clone the repository
```
git clone https://github.com/umairnow/ECommerceApp.git
```
2. Install dependencies
```
mvn install
```

### Running the application
1. Start the application
```
mvn spring-boot:run
```
### API Endpoints
- GET `/api/v1/watches`
  - Reterieve a list of all available watches
  - Example response:
  ```json
    [
        {
            "id": 1,
            "name": "Rolex",
            "price": 100,
            "discount": 0.2
        },
        {
            "id": 2,
            "name": "Michael Kors",
            "price": 80,
            "discount": 0.1
        }
    ]
    ```
- POST `/api/v1/watches` 
  - Accepts a JSON object with a list of watch IDs and returns the total price after applying discounts if available
  - Example request:
  ```json
    {
        "watchIds": [1, 2, 1]
    }
    ```

### Data Model
- <b>Watch:</b> Represents a watch product with properties like name, unit price, discount information, and quantity for discount.
- <b>RequestBodyWrapper:</b> Encapsulates a list of watch IDs sent in the POST request body.
- <b>WatchResponse:</b> Holds the total checkout price for the requested watches.

### Testing
1. Run tests using Maven
```
mvn test
```
2. Run tests using Intellij IDEA, you can run the tests directly from the IDE. We have a `WatchRequests.http` file which contains all the API requests and responses. You can run these requests directly from the IDE.
3. You can also use Postman to test the APIs.

### How I Approached It
- Spring Boot framework was used to build a RESTful API.
- JPA (Hibernate) was used for data persistence with an H2 database in-memory for simplicity.
- Lombok library (optional) is used for boilerplate code reduction (getters, setters, etc.).

### Room for Improvement

- <b>Security:</b> The current implementation disables security for simplicity. Implement proper authentication and authorization mechanisms for production use.
- <b>Error Handling:</b> Implement robust error handling to provide more informative messages in case of exceptions. The current implementation provides basic error responses. I would add a custom ErrorController will be implemented to provide more informative error messages to the user based on the exception type or HTTP status code. This will improve the user experience by giving them a better understanding of the encountered error. 
- <b>Scalability:</b> Consider using a dedicated database server and connection pooling for larger deployments. Additionally, for application-level scalability, explore using Kubernetes for container orchestration and horizontal pod autoscaling to manage deployments across multiple nodes.
- <b>Testing:</b> Add unit and integration tests for improved code coverage and maintainability.
- <b>Logging:</b> Implement proper logging to track application behavior and errors.
- <b>API Documentation:</b> Implement Swagger for API documentation.
- <b>Continuous Integration/Continuous Deployment:</b> Implement CI/CD pipelines for automated testing and deployment.
- <b>Frontend Integration:</b> Develop a frontend application to interact with the API for a complete e-commerce experience.
- <b>Monitoring:</b> Implement monitoring and alerting to track application performance and health.
- <b>Data Model:</b> The current implementation uses `Long` data type for the Watch entity's id field. In the future, this will be changed to `UUID` to provide a more robust and globally unique identifier for watches. This change will improve data integrity and avoid potential conflicts arising from relying on sequential `Long` values.