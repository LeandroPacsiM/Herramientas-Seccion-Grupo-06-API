# LlamaTours Backend

Backend API for LlamaTours — expedition booking platform built with Spring Boot 3.

## Tecnologías

- **Java 17**
- **Spring Boot 3.3.0**, Spring Security, Spring Data JPA
- **JWT** (JJWT 0.12.5)
- **H2** in-memory database (dev)
- **Lombok**
- **Swagger/OpenAPI** (SpringDoc 2.5.0)

## Prerrequisitos

- **Java 17** o superior
- **Maven** (o usar el wrapper `./mvnw`)
- **Git**

## Configuración

Variables de entorno opcionales (con valores por defecto):

| Variable | Default | Descripción |
|----------|---------|-------------|
| `JWT_SECRET` | `404E63...` | Clave secreta para firmar JWT (Base64) |
| `JWT_EXPIRATION` | `86400000` | Tiempo de expiración del token (24h en ms) |

## Ejecución

```bash
./mvnw spring-boot:run
```

Servidor en `http://localhost:8080`.

## Autenticación

1. Registrarse en `POST /auth/register` o hacer login en `POST /auth/login`
2. Copiar el token JWT de la respuesta
3. Incluirlo en el header de las peticiones protegidas:

```
Authorization: Bearer <token>
```

## Endpoints

### Auth (público)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/register` | Registrar usuario |
| POST | `/auth/login` | Login, devuelve JWT |

### Expedition (público)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/expeditions` | Listar expediciones |
| GET | `/api/expeditions/{id}` | Obtener expedición |

### Expedition (admin — requiere rol ADMIN)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/admin/expeditions` | Crear expedición |
| PUT | `/api/admin/expeditions/{id}` | Actualizar expedición |
| DELETE | `/api/admin/expeditions/{id}` | Eliminar expedición |

### Booking (requiere rol USER + JWT)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/bookings` | Crear reserva |
| GET | `/api/bookings` | Listar reservas del usuario |
| POST | `/api/bookings/{id}/cancel` | Cancelar reserva |

### Booking (admin — requiere rol ADMIN)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/admin/bookings` | Listar todas las reservas |

### Contact (público)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/contact` | Enviar mensaje de contacto |

### Consolas

| URL | Descripción |
|-----|-------------|
| `/swagger-ui/index.html` | Documentación Swagger |
| `/h2-console` | Consola H2 (JDBC: `jdbc:h2:mem:testdb`) |

## Tests

```bash
./mvnw test                    # Todos los tests
./mvnw test -Dtest=BookingServiceTest  # Test específico
```

## Estructura

```
com.llamatours/
├── auth/          → AuthController, AuthService, DTOs
├── booking/       → BookingController, BookingService, Booking entity
├── common/        → GlobalExceptionHandler, DataSeeder
├── config/        → SecurityConfig, JwtUtils, JwtFilter
├── contact/       → ContactController, ContactService, Contact entity
├── enums/         → Role, BookingStatus, Difficulty
├── expedition/    → Entities, Repository, Service, Controller, Mapper, DTOs
└── user/          → User entity, UserRepository, UserService
```
