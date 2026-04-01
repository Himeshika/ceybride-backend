# Sample Requests

## Register
```json
{
  "fullName": "Nadeesha Perera",
  "email": "nadeesha@example.com",
  "password": "Password@123",
  "phone": "0771234567"
}
```

## Login
```json
{
  "email": "nadeesha@example.com",
  "password": "Password@123"
}
```

## Create wedding profile
```json
{
  "weddingTitle": "Nadeesha & Kasun Wedding",
  "brideName": "Nadeesha Perera",
  "groomName": "Kasun Silva",
  "weddingType": "SINHALA_PORUWA",
  "weddingDate": "2026-12-20",
  "guestCount": 350,
  "totalBudget": 2500000,
  "venue": "Colombo"
}
```

## Generate blueprint
```json
{
  "weddingTitle": "Nadeesha & Kasun Wedding",
  "brideName": "Nadeesha Perera",
  "groomName": "Kasun Silva",
  "weddingType": "SINHALA_PORUWA",
  "weddingDate": "2026-12-20",
  "guestCount": 350,
  "totalBudget": 2500000,
  "venue": "Colombo"
}
```

## Create guest
```json
{
  "fullName": "Amma",
  "guestSide": "BRIDE_SIDE",
  "rsvpStatus": "CONFIRMED",
  "tableNumber": 1
}
```

## Create task
```json
{
  "title": "Meet decorator",
  "category": "DECORATION",
  "priority": "HIGH",
  "status": "PENDING",
  "assignedSide": "SHARED",
  "dueDate": "2026-11-10"
}
```

## Create expense
```json
{
  "title": "Advance payment for hall",
  "category": "VENUE",
  "estimatedAmount": 500000,
  "paidAmount": 100000,
  "responsibleSide": "SHARED",
  "paymentStatus": "PARTIAL"
}
```
