# Bank Account REST API


## Features

- Create new bank client
- Create new account for client
- Make deposits to accounts
- Make withdrawals from accounts
- View account balance and details
- Generate account statements with transaction history
- Thread-safe account operations
- Comprehensive error handling
- Full test coverage with JUnit and Mockito

## Technical Stack

- Java 17
- Spring Boot 3.4.1
- JUnit 5
- Mockito
- Maven

## API Endpoints

### Create Client
```http
POST /api/bank/create/client?clientId={clientId}

Response: 200 OK
{
    "clientId": "account123",
    "accounts": []
}
```

### Create Account
```http
POST /api/bank/createaccount?clientId={clientId}&accountId={accountId}

Response: 200 OK
{
    "accountId": "account123",
    "balance": 0,
    "lastUpdate": "2025-01-20",
    "statement": [],
    "createdDate": "2025-01-20" 
}
```

### Make Deposit
```http
POST /api/bank/deposit
Content-Type: application-json

{
    "clientId": "clientId",
    "accountId": "accountId",
    "amount": 100
}

Response: 200 OK
```

### Make Withdrawal
```http
POST /api/bank/withdraw
Content-Type: application-json

{
    "clientId": "clientId",
    "accountId": "accountId",
    "amount": 10
}

Response: 200 OK
```

### Get Account Details
```http
GET /api/bank/account?clientId={clientId}&accountId={accountId}

Response: 200 OK
{
    "accountId": "account-1",
    "balance": 1350,
    "lastUpdateDate": "2025-01-17T11:03:56.80028031",
    "statement": [
        {
            "date": "2025-01-17T11:03:48.880950066",
            "type": "DEPOSIT",
            "amount": 1500,
            "balance": 1500
        },
        {
            "date": "2025-01-17T11:03:56.80028031",
            "type": "WITHDRAWAL",
            "amount": 150,
            "balance": 1350
        }
    ],
    "createdData": "2025-01-17T11:03:41.353123123"
}
```

### Get Account Statement
```http
GET api/bank/statement?clientId={clientId}&accountId={accountId}

Response: 200 OK
[
    {
        "date": "2025-01-17T11:03:48.880950066",
        "type": "DEPOSIT",
        "amount": 1500,
        "balance": 1500
    },
    {
        "date": "2025-01-17T11:03:56.80028031",
        "type": "WITHDRAWAL",
        "amount": 150,
        "balance": 1350
    }
]
```

## Error Handling

The API uses standard HTTP status codes and includes detailed error messages:

- `400 Bad Request`: Invalid input (negative amounts, zero amounts)
- `404 Not Found`: Account not found
- `400 Bad Request`: Insufficient funds for withdrawal

Error Response Format:
```json
{
    "status": 400,
    "message": "Insufficient funds"
}
```

## Running the Application

1. Clone the repository
```bash
git clone https://github.com/zaerbib/bank-account.git
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8085`

## Running Tests

Execute all tests:
```bash
mvn test
```

