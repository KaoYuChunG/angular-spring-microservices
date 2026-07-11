## Context

The backend microservices do not currently expose formal API documentation. This design proposes integrating SpringDoc OpenAPI for Java services to provide automated API documentation and exporting specifications to facilitate frontend API client generation.

## Goals / Non-Goals

**Goals:**
- Integrate `springdoc-openapi-starter-webmvc-ui` into Java microservices.
- Enable automatic generation of OpenAPI specs (`openapi.json`).
- Establish a mechanism to export and centralize these specs for use by the frontend build pipeline.

**Non-Goals:**
- Modifying business logic.
- Changing existing API behaviors.

## Decisions

- **SpringDoc OpenAPI**: Standard solution for Spring Boot to generate OpenAPI specs automatically from request mappings.
- **Centralized Export**: Use a custom Maven plugin or a post-build task in the Java build pipeline to extract `openapi.json` to a project-root `infrastructure/` directory.

## Risks / Trade-offs

- **[Risk] Sync issues**: Automated docs might not always reflect the latest code if not integrated into CI/CD. → **Mitigation**: Fail the build if the generated OpenAPI spec changes and is not committed/updated.
