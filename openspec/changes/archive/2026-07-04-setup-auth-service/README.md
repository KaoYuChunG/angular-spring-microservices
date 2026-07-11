# Archive Summary: setup-auth-service

## 🛠️ Tools & Technology Stack
- **Identity Provider**: Keycloak 24.0 (OIDC/OAuth2)
- **Gateway**: Spring Cloud Gateway (MVC)
- **Security**: Spring Security OAuth2 Resource Server
- **E2E Testing**: Playwright
- **Infra**: Docker Compose + PostgreSQL 16

## 🎯 Architecture Decisions & Rationale

### 1. Centralized Auth at Gateway
- **Decision**: All JWT validations are performed at the Java Gateway.
- **Rationale**: To ensure a "Single Point of Entry" for security. This allows downstream microservices (including Node.js) to remain lightweight and agnostic of the specific auth provider, trusting the identity propagated via HTTP headers (`X-User-Id`, `X-User-Roles`).

### 2. Keycloak for SSO
- **Decision**: Use Keycloak instead of a custom auth implementation.
- **Rationale**: Provides enterprise-grade features (RBAC, Google OAuth2, User Management) out-of-the-box, reducing the risk of security vulnerabilities in custom auth code.

### 3. Playwright for E2E
- **Decision**: Use Playwright for authentication flow verification.
- **Rationale**: Ability to simulate multi-role contexts and handle async WebSocket/HTTP interactions more reliably than legacy tools.

## ⚠️ Critical Lessons Learned (The "Pits")
- **Keycloak Temporary Passwords**: Discovered that `grant_type: 'password'` fails if `temporary: true` is set in Keycloak user credentials. 
- **Fix**: Explicitly set `"temporary": false` in `realm-export.json` to allow direct API login for automated tests.
- **PostgreSQL Init Race Condition**: Keycloak failed to start because it tried to connect before the DB initialization script finished.
- **Fix**: Implemented `healthcheck` in `docker-compose.yml` to ensure Keycloak waits for `postgres` to be `healthy`.

## ✅ Validation Status
- [x] Keycloak Realm imported successfully.
- [x] Java Gateway validates JWT and propagates headers.
- [x] E2E test `auth.spec.ts` passed.
