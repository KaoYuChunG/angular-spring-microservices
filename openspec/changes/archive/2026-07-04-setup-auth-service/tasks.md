## 1. Keycloak Infrastructure Setup

- [x] 1.1 Configure Keycloak realm, clients, and roles via Docker Compose or initial setup script.
- [x] 1.2 Verify Keycloak is accessible and reachable by the Java Gateway.

## 2. Java Gateway Implementation

- [x] 2.1 Add Spring Security and OAuth2 Resource Server dependencies to `backend/gateway/pom.xml`.
- [x] 2.2 Configure `application.yml` for JWT validation using Keycloak's JWKS endpoint.
- [x] 2.3 Implement security filter to validate JWT and extract userId/roles.
- [x] 2.4 Implement Header propagation filter to add X-User-Id and X-User-Roles headers.

## 3. Downstream Service Integration

- [x] 3.1 Verify headers are correctly received by downstream services (e.g., in a simple dummy endpoint).

## 4. Testing and Verification

- [x] 4.1 Write integration tests for Gateway using Testcontainers to start Keycloak and Gateway containers.
- [x] 4.2 Create Playwright E2E test to verify the complete login and header propagation flow.
