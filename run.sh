#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COMPOSE_FILE="${SCRIPT_DIR}/infra/docker-compose.yaml"

compose() {
  if command -v docker >/dev/null 2>&1 && docker compose version >/dev/null 2>&1; then
    docker compose -f "$COMPOSE_FILE" "$@"
    return
  fi

  if command -v docker-compose >/dev/null 2>&1; then
    docker-compose -f "$COMPOSE_FILE" "$@"
    return
  fi

  echo "Fant ikke 'docker compose' eller 'docker-compose'. Installer Docker."
  exit 1
}

ensure_compose_file() {
  if [ ! -f "$COMPOSE_FILE" ]; then
    echo "Mangler compose-fil: $COMPOSE_FILE"
    exit 1
  fi
}

resolve_target() {
  case "${1:-debug}" in
    debug|prod)
      echo "$1"
      ;;
    "")
      echo "debug"
      ;;
    *)
      echo "Ugyldig target: '$1'. Bruk 'debug' eller 'prod'."
      exit 1
      ;;
  esac
}

compose_with_target() {
  local target="$1"
  shift

  if command -v docker >/dev/null 2>&1 && docker compose version >/dev/null 2>&1; then
    BACKEND_BUILD_TARGET="$target" docker compose -f "$COMPOSE_FILE" "$@"
    return
  fi

  if command -v docker-compose >/dev/null 2>&1; then
    BACKEND_BUILD_TARGET="$target" docker-compose -f "$COMPOSE_FILE" "$@"
    return
  fi

  echo "Fant ikke 'docker compose' eller 'docker-compose'. Installer Docker."
  exit 1
}

usage() {
  cat <<EOF
Bruk:
  ./run.sh start [debug|prod]
  ./run.sh stop
  ./run.sh build [debug|prod]
  ./run.sh clean
  ./run.sh clean all

Eksempler:
  ./run.sh build
  ./run.sh build debug
  ./run.sh build prod
  ./run.sh start
  ./run.sh start debug
  ./run.sh start prod
EOF
  exit 1
}

cmd="${1:-}"
sub="${2:-}"

ensure_compose_file

case "$cmd" in
  start)
    target="$(resolve_target "${sub:-debug}")"
    compose pull
    compose_with_target "$target" up -d --build
    ;;
  stop)
    compose stop
    ;;
  build)
    target="$(resolve_target "${sub:-debug}")"
    compose pull
    compose_with_target "$target" build
     ;;
  clean)
    if [ "${sub:-}" = "all" ]; then
      compose down -v --remove-orphans
    elif [ -z "${sub:-}" ]; then
      compose down --remove-orphans
    else
      usage
    fi
    ;;
  *)
    usage
    ;;
esac