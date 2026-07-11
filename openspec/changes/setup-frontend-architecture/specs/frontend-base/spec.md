## ADDED Requirements

### Requirement: Nx Workspace Scaffolding
The system MUST initialize an Nx workspace within the `/frontend` directory, configured with Angular 18+ application support.

#### Scenario: Nx workspace creation
- **WHEN** initializing the workspace
- **THEN** an `nx.json` and a standard workspace folder structure are created in `/frontend`.

### Requirement: Angular 18+ Core Application
The application MUST be created using Angular 18+ with Vite and esbuild builders enabled in the Nx workspace.

#### Scenario: Application build verification
- **WHEN** running the build command
- **THEN** the application compiles successfully using the configured Vite/esbuild builders.

### Requirement: Strict Coding Standards
The project MUST enforce strict TypeScript compiler flags, ESLint rules, and Prettier formatting.

#### Scenario: Linting enforcement
- **WHEN** code violating ESLint rules is committed
- **THEN** the linting check fails.

### Requirement: Styling Foundation
The application MUST integrate Tailwind CSS with a structured design token system, avoiding hardcoded styling values.

#### Scenario: Tailwind class application
- **WHEN** applying Tailwind classes
- **THEN** the styles are correctly computed based on defined theme tokens.

### Requirement: Modular Monolith Feature Layout
The application MUST organize features into dedicated directories under `src/app/features/`, ensuring feature encapsulation.

#### Scenario: Feature structure verification
- **WHEN** adding a new feature
- **THEN** it must be placed within `src/app/features/<feature-name>/`.

### Requirement: Decoupled Communication
The system MUST implement an Event Bus using RxJS (Subjects/CustomEvents) for inter-feature communication to ensure decoupling.

#### Scenario: Cross-feature communication
- **WHEN** a feature emits an event via the Event Bus
- **THEN** the subscribed features receive the data without direct coupling between the features.

### Requirement: OpenAPI API Integration
The application MUST automate code generation for the OpenAPI client, synchronizing frontend data models with backend APIs.

#### Scenario: API model synchronization
- **WHEN** the OpenAPI definition is updated
- **THEN** the frontend API client and models are updated accordingly through the generation pipeline.
