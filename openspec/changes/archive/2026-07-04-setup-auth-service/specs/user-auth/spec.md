## ADDED Requirements

### Requirement: Centralized Authentication via Keycloak
The system SHALL use Keycloak as the Identity Provider (IdP) for SSO and multi-role authentication.

#### Scenario: User successfully logs in
- **WHEN** user provides valid credentials to Keycloak
- **THEN** Keycloak issues a JWT token

### Requirement: JWT Validation at Gateway
The Java Gateway SHALL validate the JWT token on every request.

#### Scenario: Valid JWT request
- **WHEN** request arrives with a valid JWT
- **THEN** Gateway forwards the request to downstream services with X-User-Id and X-User-Roles headers

#### Scenario: Invalid JWT request
- **WHEN** request arrives with an invalid or expired JWT
- **THEN** Gateway rejects the request with 401 Unauthorized

### Requirement: Identity Propagation
Downstream microservices SHALL trust and use identity information from HTTP headers provided by the Gateway.

#### Scenario: Downstream service receives identity
- **WHEN** downstream service receives a request from Gateway
- **THEN** it reads X-User-Id and X-User-Roles from headers to perform business logic
