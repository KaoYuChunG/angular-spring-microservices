## Why

The platform currently lacks an authentication mechanism. To build a secure, multi-role (Student, Teacher, Admin) platform, we need a centralized SSO solution. Keycloak will act as the identity provider, and the Java Gateway will serve as the centralized authentication point to secure all downstream microservices.

## What Changes

- Set up Keycloak with necessary realms, clients, and roles.
- Implement JWT validation in the Java Gateway using Spring Security.
- Propagate user identity (userId, roles) to downstream microservices via HTTP headers (X-User-Id, X-User-Roles).
- Configure environment variables for security-sensitive information (client secret, DB credentials).

## Capabilities

### New Capabilities
- `user-auth`: Setup of Keycloak SSO, integration with Java Gateway for JWT validation, and user identity propagation.

### Modified Capabilities
<!-- None -->

## Impact

- **Affected Services**:
  - `backend/auth` (Keycloak configuration)
  - `backend/gateway` (Spring Security validation, Header propagation)
- **APIs**: New authentication flow will be introduced.
- **Dependencies**: Keycloak (Docker container).
