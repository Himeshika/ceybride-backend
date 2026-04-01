# Database Notes

Main tables:
- users
- wedding_profiles
- guests
- tasks
- expenses
- vendors
- ritual_schedules
- task_templates
- expense_templates
- ritual_templates

Key relationships:
- one user -> one wedding profile
- one wedding profile -> many guests
- one wedding profile -> many tasks
- one wedding profile -> many expenses
- one wedding profile -> many vendors
- one wedding profile -> many ritual schedule records

Design notes:
- Bride and groom are stored inside `wedding_profiles`
- Expense records support `BRIDE_SIDE`, `GROOM_SIDE`, and `SHARED`
- Task and ritual records can be generated from templates
- Generated records are marked with `is_generated`
