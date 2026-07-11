## Context

The current project has a Java-based microservices backend and an empty `frontend` directory. We need to bootstrap a scalable, enterprise-grade Angular 18+ frontend architecture that adheres to our standards: Nx Monorepo, Tailwind CSS, modular monolith structure, RxJS-based inter-feature communication, and OpenAPI-driven API integration.

## Goals / Non-Goals

**Goals:**
- Set up an Nx Workspace inside `/frontend`.
- Generate a robust, production-ready Angular 18+ app structure.
- Define strict linting, styling (Tailwind), and testing (Jest/Playwright) standards.
- Implement a feature-based folder structure (`src/app/features/`).
- Create an OpenAPI-based API generation pipeline.

**Non-Goals:**
- Building specific UI components or business features (this is just the foundational scaffolding).

## Decisions

- **Nx Workspace**: Chosen for scaling capabilities, caching, and integrated monorepo support for frontend and potential shared libraries.
- **Angular 18+ with Vite/esbuild**: Chosen for modern performance, fast build times, and compatibility with upcoming framework features.
- **Tailwind CSS**: Chosen for consistent, token-based styling across the application, avoiding hardcoded CSS.
- **RxJS/CustomEvents**: Chosen for the Event Bus to decouple features and enable future migration to micro-frontends without heavy re-architecture.

## Risks / Trade-offs

- **[Risk] Monorepo Complexity**: Managing dependencies across shared libraries could become complex. → **Mitigation**: Use strict Nx dependency boundaries and enforce module ownership.
- **[Risk] OpenAPI Sync Drift**: Backend API changes might desync from generated frontend models. → **Mitigation**: Integrate OpenAPI generation directly into the CI/CD pipeline (Jenkins).
