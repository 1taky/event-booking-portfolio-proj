# Event Booking API

A RESTful backend API for booking tickets to events. Built with Spring Boot, secured with JWT authentication, and deployed via Docker Compose. Includes email notifications on booking and cancellation.

## Features

- JWT-based authentication with two roles: **ADMIN** and **USER**
- ADMIN can create, update, and delete events and venues
- USER can browse events, book tickets, view their bookings, and cancel
- Seat availability validation — prevents overbooking
- **Email notifications** on booking confirmation and cancellation (HTML emails)
- DTO pattern for clean request/response separation
- Global exception handling with meaningful error responses
- Fully containerized with Docker Compose

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Security | Spring Security + JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Email | Spring Mail (JavaMailSender) |
| Containerization | Docker, Docker Compose |
| Testing | JUnit 5, Mockito |
| Build Tool | Maven |
| Utilities | Lombok |

## Data Model

```
Venue (1) ──── (N) Event (1) ──── (N) Booking (N) ──── (1) User
```

- **Venue** — event location (name, address)
- **Event** — event details (title, description, dateTime, availableSeats, price)
- **Booking** — ticket reservation (seats, status: CONFIRMED / CANCELLED, createdAt, totalPrice)
- **User** — registered user with role (ADMIN / USER)

## Getting Started

### Prerequisites

- Docker
- Docker Compose

### Environment variables

Create a `.env` file in the project root:

```env
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

> For Gmail: enable 2FA and generate an **App Password** in your Google account settings.

### Run the application

```bash
git clone https://github.com/1taky/event-booking-portfolio-proj.git
cd event-booking-portfolio-proj
./mvnw package -DskipTests
docker-compose up --build
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Auth (public)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Venues

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/venues` | Public | Get all venues |
| GET | `/api/venues/{id}` | Public | Get venue by ID |
| POST | `/api/venues` | ADMIN | Create venue |
| PUT | `/api/venues/{id}` | ADMIN | Update venue |
| DELETE | `/api/venues/{id}` | ADMIN | Delete venue |

### Events

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/events` | Public | Get all events |
| GET | `/api/events/{id}` | Public | Get event by ID |
| POST | `/api/events` | ADMIN | Create event |
| PUT | `/api/events/{id}` | ADMIN | Update event |
| DELETE | `/api/events/{id}` | ADMIN | Delete event |

### Bookings

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/bookings` | USER | Create booking (sends confirmation email) |
| GET | `/api/bookings/my` | USER | Get current user's bookings |
| DELETE | `/api/bookings/{id}` | USER | Cancel booking (sends cancellation email) |

## Authentication

All protected endpoints require a Bearer token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

### Example flow

```bash
# 1. Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "password123"}'

# 2. Login — get token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "password123"}'

# 3. Create a booking
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_token>" \
  -d '{"eventId": 1, "seats": 2}'
```

## Email Notifications

After a successful booking or cancellation, the user receives an HTML email with booking details including event name, number of seats, status, and total price.

## Running Tests

```bash
./mvnw test
```

## Project Structure

```
src/main/java/com/portfolio/bookingapp/
├── controllers/          # REST controllers (Auth, Venue, Event, Booking)
├── models/               # JPA entities (User, Venue, Event, Booking)
│   └── enums/            # BookingStatus, Roles
├── repositories/         # Spring Data JPA repositories
├── services/             # Service interfaces
│   └── impl/             # Service implementations + MailSenderService
├── security/             # JWT filter, SecurityConfig, UserDetailsServiceImpl
├── dto/                  # Request/Response DTOs
└── exeptions/            # Custom exceptions + GlobalExceptionHandler
```

## Author

**Vitalii Syniak**
- GitHub: [@1taky](https://github.com/1taky)
- LinkedIn: [Vitalii Syniak](https://www.linkedin.com/in/віталій-синяк-7040262a5)