# Entity Template

## Record identity
- Entity ID (UUID):

## Identity
- Canonical name:
- Display name:
- Alternative names:
- Domain:
- Primary kind:
- Secondary kind:
- Existence status:
- Summary:

## Core classification
- What is it?:
- Broad category:
- Specific category:
- Scientific field(s):
- Object field(s):
- Notes:

## Domain-specific profiles
Domain-specific profiles are extension points. They must allow new domains without changing the core entity structure.

Recommended structure:

```json
{
  "profiles": {
    "biology": {},
    "geology": {},
    "astronomy": {},
    "geography": {},
    "archaeology": {},
    "physics": {},
    "chemistry": {},
    "medicine": {},
    "technology": {},
    "artifact": {},
    "materials": {},
    "legal": {},
    "historical": {}
  }
}
```

### Profile rules
- Known profile keys may receive stricter validation later.
- Unknown profile keys should be preserved as raw structured data unless service validation rejects them.
- An entity may use several profiles at once.
- Do not place validation logic in records/templates. Validation belongs in services/validators.

### Example profile usage
- Astronomy object: `astronomy`, `physics`, `historical`
- Quantum device: `physics`, `technology`, `materials`
- Medical condition: `medicine`, `biology`
- Chair, bicycle, firearm, tool: `artifact`, `technology`, `materials`, optionally `legal` or `historical`
- Atom or isotope: `physics`, `chemistry`
- Molecule or compound: `chemistry`, optionally `biology`, `medicine`, or `materials`

## Source basis
- Primary source ID(s):
- Secondary source ID(s):
- Notes:

## Linked records
- Classification ID(s):
- Claim ID(s):
- Finding ID(s):
