<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP — micro-mensajeria</h3>
  <p align="center">
    Microservice responsible for sending SMS notifications to customers via Twilio when order statuses change.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

> This microservice is stateless — it does not use a database.

### Service Dependencies

Communicates with the following microservices via Feign Client:
- **micro-usuarios** — retrieves the customer's phone number for SMS delivery

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* JDK 17 [https://jdk.java.net/17/](https://jdk.java.net/17/)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* Twilio account [https://www.twilio.com/](https://www.twilio.com/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Environment Variables

Configure the following environment variables before running:

| Variable | Description |
|---|---|
| `SERVER_PORT` | Port on which the service runs (default: `8083`) |
| `SPRING_PROFILES_ACTIVE` | Active profile (e.g. `dev`) |
| `JWT_SECRET` | Secret key for JWT validation |
| `TWILIO_ACCOUNT_SID` | Twilio account SID |
| `TWILIO_AUTH_TOKEN` | Twilio authentication token |
| `TWILIO_PHONE_NUMBER` | Twilio sender phone number |
| `USERS_SERVICE_URL` | Base URL of micro-usuarios |

### Installation

1. Clone the repo
2. Change directory
   ```sh
   cd micro-mensajeria
   ```
3. Set the required environment variables
4. Build and run
   ```sh
   ./gradlew bootRun
   ```

<!-- USAGE -->
## Usage

Once running, open the Swagger UI in your browser:

```
http://localhost:<SERVER_PORT>/swagger-ui/index.html
```

### API Endpoints

| Method | Path | Role | Description |
|---|---|---|---|
| `POST` | `/api/v1/sms` | INTERNAL | Send an SMS notification to a customer |

<!-- TESTS -->
## Tests

```sh
./gradlew test jacocoTestReport
```

Or right-click the test folder in IntelliJ and choose **Run tests with coverage**.
