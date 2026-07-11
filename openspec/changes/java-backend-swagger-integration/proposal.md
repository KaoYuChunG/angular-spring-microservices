## Why

The backend Java (Spring Boot) microservices currently lack formal API documentation, making it difficult for the frontend to integrate reliably and preventing automated API client generation.

## What Changes

- **Add SpringDoc OpenAPI**: Integrate SpringDoc OpenAPI (Swagger UI) into Java microservices to automatically generate API documentation.
- **Export OpenAPI Specification**: Configure the build process to export the `openapi.json` definition to a centralized `infrastructure/` directory.

## Capabilities

### New Capabilities

- `backend-openapi-doc`: Automated generation of OpenAPI specs from Spring Boot controllers.
- `openapi-export-pipeline`: Automated extraction of `openapi.json` during the build phase.

### Modified Capabilities

- None

## Impact

- **Affected Services**: Backend Java microservices.
- **APIs**: Standardizes API documentation and enables automated frontend client generation.
- **Dependencies**: Introduces `springdoc-openapi-starter-webmvc-ui` dependency to Java services.
