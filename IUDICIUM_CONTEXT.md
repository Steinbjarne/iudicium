# Template relationships with UUIDs

## What actually exists in the project

The following is already established in **Research / Iudicium**:

- Backend uses **Spring Boot** with dependency injection
- Java base package is **`org.curiosity`**
- Focus is **backend, API, and database**
- DTO / intermediary models must be used between API and database
- Health ping endpoint is **`/health/ping`**
- Template files exist under:
    - `docs/templates/markdown`
    - `docs/templates/json`

The current template set is:

- `entity-template`
- `classification-template`
- `claim-template`
- `finding-template`
- `source-template`

## What is proposed here

This section describes how the five template types are linked together using **UUID-based references**.

This is a **model proposal for the data structure**, not a statement that all of this is already implemented in code.

---

## Why UUIDs are needed

Each main record should have its own unique UUID.

Example:

- Entity has its own UUID
- Classification has its own UUID
- Claim has its own UUID
- Finding has its own UUID
- Source has its own UUID

These UUIDs make it possible to connect records without ambiguity.

---

## Main principle

Each record has its own identity.

Records are connected by storing the UUID of another record.

Examples:

- `Classification.entityId` points to `Entity.id`
- `Claim.entityId` points to `Entity.id`
- `Finding.entityId` points to `Entity.id`
- `Claim.sourceIds` points to one or more `Source.id`
- `Claim.supportingFindingIds` points to one or more `Finding.id`

---

## Tree view

```text
Entity (E1: Camelops hesternus)
├── Classification (CL1)
│   └── entityId = E1
├── Claim (C1)
│   ├── entityId = E1
│   ├── sourceIds = [S1]
│   └── supportingFindingIds = [F1]
├── Finding (F1)
│   ├── entityId = E1
│   └── sourceIds = [S2]
├── Source (S1)
└── Source (S2)