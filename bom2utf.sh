#!/usr/bin/env bash
set -euo pipefail

ROOT="${1:-.}"
PATTERN="${2:-*.java}"

if [ ! -d "$ROOT" ]; then
  echo "Feil: '$ROOT' er ikke en mappe."
  exit 1
fi

removed=0
checked=0

while IFS= read -r -d '' file; do
  checked=$((checked + 1))

  if [ "$(LC_ALL=C head -c 3 "$file" | xxd -p -c 3)" = "efbbbf" ]; then
    tmp="$(mktemp)"
    tail -c +4 "$file" > "$tmp"
    cat "$tmp" > "$file"
    rm -f "$tmp"
    echo "Fjernet BOM: $file"
    removed=$((removed + 1))
  fi
done < <(find "$ROOT" -type f -name "$PATTERN" -print0)

echo
echo "Sjekket filer: $checked"
echo "Fjernet BOM fra: $removed"