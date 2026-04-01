# CeyBride Backend

A Spring Boot REST API for managing Sri Lankan wedding planning activities.

## Main feature
**Sri Lankan Wedding Blueprint Generator**  
A rule-based module that generates:
- default wedding tasks
- ritual schedule items
- starter budget / expense records
- bride-side, groom-side, and shared responsibility breakdowns

## Tech stack
- Java 21
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Lombok

## Modules
- Authentication & User
- Wedding Profile
- Blueprint Generator
- Guests
- Tasks
- Expenses
- Vendors
- Ritual Schedule
- Dashboard Summary

## Important notes
- The project is designed for **one authenticated user managing one wedding profile**.
- Bride and groom are stored in `WeddingProfile`, not as separate entities.
- The environment used to produce this zip did not include Maven, so the code was structured carefully but not compiled here.

## Quick start
1. Create a MySQL database, for example `ceybride_db`
2. Copy `src/main/resources/application.yml` and update username/password
3. Run with Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Use `/api/auth/register` then `/api/auth/login`
5. Create or update the wedding profile
6. Use `/api/blueprint/generate` to generate the initial wedding plan

## Authentication endpoints
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `PUT /api/auth/change-password`

## Main endpoints
- `POST /api/wedding`
- `GET /api/wedding/me`
- `PUT /api/wedding/me`
- `POST /api/blueprint/generate`
- `POST /api/blueprint/regenerate`
- `GET /api/blueprint/preview`
- `GET /api/dashboard/summary`

Plus full CRUD for:
- guests
- tasks
- expenses
- vendors
- rituals

## Security
All non-auth endpoints require `Authorization: Bearer <token>`.

## Regeneration behavior
`/api/blueprint/regenerate` removes only generated tasks, generated expenses, and generated rituals for the current wedding profile, then rebuilds them from templates. Custom records remain untouched.
