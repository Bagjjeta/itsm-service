# ITSM Service – System zarządzania zgłoszeniami

## Wymagania
- Java 21
- Docker Desktop

## Uruchomienie projektu

1. W głównym katalogu projektu utwórz plik `.env` (np. na podstawie `.env.example`):
   ```env
   POSTGRES_DB=<TWOJA NAZWA BAZY>
   POSTGRES_USER=<TWÓJ UŻYTKOWNIK>
   POSTGRES_PASSWORD=<TWOJE HASŁO>
   ```
2. Uruchom bazę danych PostgreSQL w kontenerze Docker:
   ```bash
   docker compose up -d
   ```
3. Uruchom aplikację Spring Boot (z poziomu IntelliJ IDEA uruchamiając klasę `ServiceApplication` lub z terminala):
   ```bash
   ./mvnw spring-boot:run
   ```
4. Dokumentacja API (Swagger UI) jest dostępna pod adresem:
   - http://localhost:8080/swagger-ui/index.html