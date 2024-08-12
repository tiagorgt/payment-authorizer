# Payment Authorizer REST API

This project implements a payment authorizer REST API using Spring Boot and PostgreSQL.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 21 or higher installed.
- **Docker**: Docker should be installed to run an instance of PostgreSQL.
- **Maven**: Maven should be installed to build the project.

## Running the Application

1. **Start the PostgreSQL database using docker compose**:
    ```bash
    docker-compose up
    ```

2. **Build and run the Spring Boot application**:
    ```bash
    mvn spring-boot:run
    ```

### Accessing the Application

- The application will be accessible at `http://localhost:8080`.
- The API Documentation will be accessible at `http://localhost:8080/swagger-ui.html`

### Authorizing a Transaction

#### 1. Create an account:

**Request**:
```bash
curl --location 'http://localhost:8080/accounts' \
--header 'Content-Type: application/json' \
--data '{
    "cashBalance": 100,
    "foodBalance": 100,
    "mealBalance": 100
}'
```

**Response**
```json
{
    "id": 3,
    "foodBalance": 100,
    "mealBalance": 100,
    "cashBalance": 100
}
```

#### 2. Authorize a Transaction
**Request**:
```bash
curl --location 'http://localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data '{
	"account": "3",
	"totalAmount": "1.00",
	"mcc": "581143",
	"merchant": "UBER EATS                   SAO PAULO BR"
}'
```

**Response**
```json
{
    "code": "00",
    "message": "Transaction approved"
}
```

## Running Tests

To run the tests, use the following command:
```bash
mvn test
```