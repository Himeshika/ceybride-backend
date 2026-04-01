# Endpoint Summary

## Auth
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `PUT /api/auth/change-password`

## Wedding
- `POST /api/wedding`
- `GET /api/wedding/me`
- `PUT /api/wedding/me`

## Blueprint
- `POST /api/blueprint/generate`
- `POST /api/blueprint/regenerate`
- `GET /api/blueprint/preview`

## Guests
- `POST /api/guests`
- `GET /api/guests`
- `GET /api/guests/{id}`
- `PUT /api/guests/{id}`
- `DELETE /api/guests/{id}`
- `GET /api/guests/stats`

## Tasks
- `POST /api/tasks`
- `GET /api/tasks`
- `GET /api/tasks/{id}`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`
- `PATCH /api/tasks/{id}/status`
- `GET /api/tasks/upcoming`

## Expenses
- `POST /api/expenses`
- `GET /api/expenses`
- `GET /api/expenses/{id}`
- `PUT /api/expenses/{id}`
- `DELETE /api/expenses/{id}`
- `GET /api/expenses/summary`
- `GET /api/expenses/by-side`

## Vendors
- `POST /api/vendors`
- `GET /api/vendors`
- `GET /api/vendors/filter`
- `GET /api/vendors/{id}`
- `PUT /api/vendors/{id}`
- `DELETE /api/vendors/{id}`

## Rituals
- `POST /api/rituals`
- `GET /api/rituals`
- `GET /api/rituals/{id}`
- `PUT /api/rituals/{id}`
- `DELETE /api/rituals/{id}`
- `GET /api/rituals/upcoming`

## Dashboard
- `GET /api/dashboard/summary`
