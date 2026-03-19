# Research (MD-first)

Dette repoet er en **MD-first** kunnskapsbase for research på tvers av domener: flora, fauna, geologi (stein/mineral), megalitter/arkeologi, tidsepoker, fenomener, teknologi (raketter, drivstoff), osv.

Målet nå er å bygge en god, fleksibel struktur i **Markdown** (vanlig tekst med enkel formatering). Web/app/database kommer **senere**.

---

## Hva er dette prosjektet?

Dette prosjektet er et **lokalt research-bibliotek**:

- `library/` er selve biblioteket (strukturert og ryddet innhold).
- `inbox/` er innkurven (ting du nettopp har funnet og ikke har sortert ennå).
- `templates/` inneholder maler slik at nye objekter får samme struktur.
- `infra/` og `src/` finnes fordi vi senere kan få behov for tjenester og kode (database/web), men **MD er master**.

Det viktigste poenget: Vi skal kunne lese og bruke dette repoet uten database, uten applikasjon og uten spesielle verktøy.

---

## Hva betyr “MD-first”?

“MD-first” betyr at vi først og fremst lagrer kunnskap som **lesbare tekstfiler** (Markdown), ikke som et skjema i en database.

Fordeler:
- Lett å lese og redigere uten spesialverktøy
- Enkelt å versjonere (Git viser endringer)
- Fleksibelt: fungerer for alt fra dyr til raketter uten store migreringer

---

## Rotstruktur

```text
research/
  library/     # ferdig/strukturert research (master)
  inbox/       # nye funn og utkast (midlertidig)
  templates/   # maler for nye objekter
  infra/       # docker-compose, scripts, drift (ved behov)
  src/         # kode (ved behov senere)
