# IU-18 Template Fit Notes

## Purpose
Verify that the current Iudicium template model can handle broad scientific and non-scientific domains without changing the core record structure.

## Core model
The core model remains:

```text
Entity
├── Classification
├── Claim
├── Finding
└── Source
```

This structure is intentionally domain-neutral.

## Main expansion
`domainSpecificProfiles` has been changed from fixed named profile fields to an extension-friendly profile map.

This allows new domains such as medicine, physics, chemistry, quantum technology, artifacts, legal context, and historical context to be added without changing the core template shape.

## Domain examples

| Area | Entity profile(s) | Notes |
|---|---|---|
| Astronomy | astronomy, physics, historical | Planets, stars, galaxies, missions, observations |
| Quantum technology | physics, technology, materials | Qubits, experiments, devices, coherence results |
| Medicine | medicine, biology | Conditions, treatments, clinical findings, studies |
| Chair | artifact, materials, historical | Object/furniture classification |
| Bicycle | artifact, technology, materials | Object/vehicle/tool classification |
| Firearm | artifact, technology, materials, legal, historical | Object/weapon classification; legal context optional |
| Atom | physics, chemistry | Elements, isotopes, atomic properties |
| Molecule | chemistry, biology, medicine, materials | Compounds, structures, effects, uses |

## Important design rule
Known domain profiles can receive stricter validation later, but unknown profiles should be preserved unless rejected by explicit service validation.

## Backend implication
Java records should not hardcode one field per domain profile.

Recommended backend shape:

```java
Map<String, JsonNode> domainSpecificProfiles
```

or a wrapper record containing such a map.

Avoid:

```java
JsonNode biologyProfile;
JsonNode geologyProfile;
JsonNode astronomyProfile;
```

That creates needless API and record changes every time a new domain is added.

## Conclusion
The template model fits the requested domains if the profile section remains dynamic and `Finding` remains broad enough to represent observations, measurements, experiments, clinical results, datasets, and physical findings.
