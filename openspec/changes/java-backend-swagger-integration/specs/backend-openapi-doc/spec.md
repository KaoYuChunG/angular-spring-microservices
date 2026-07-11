## ADDED Requirements

### Requirement: Automated OpenAPI Generation
The Java Spring Boot services MUST integrate `springdoc-openapi` to expose OpenAPI definitions automatically from controller endpoints.

#### Scenario: Verify Swagger UI availability
- **WHEN** the application is running
- **THEN** navigating to `/swagger-ui.html` SHALL display the API documentation.

### Requirement: OpenAPI Specification Export
The build process SHALL automatically export the full OpenAPI specification to `infrastructure/openapi.json`.

#### Scenario: Verify spec export
- **WHEN** the project build finishes
- **THEN** `infrastructure/openapi.json` SHALL contain the current API definitions.
