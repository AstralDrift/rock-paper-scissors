#!/usr/bin/env bash
# Run core unit tests. Prefers local Gradle; falls back to Docker (no Android SDK needed).
set -e
cd "$(dirname "$0")/.."
PROJECT_ROOT="$PWD"
CORE_ONLY_SETTINGS="settings-core-only.gradle.kts"
TEST_CMD=":core:test --no-daemon --no-configuration-cache"
TIMEOUT_SEC="${TEST_TIMEOUT_SEC:-300}"

# Exit code from run_local: 0 = success, 1 = ran but failed, 2 = could not run (try Docker).
run_local() {
  if [[ -x "./gradlew" ]]; then
    echo "Running :core:test locally (./gradlew with $CORE_ONLY_SETTINGS)..."
    set +e
    ./gradlew -c "$CORE_ONLY_SETTINGS" $TEST_CMD
    local r=$?
    set -e
    return $r
  fi
  if command -v gradle >/dev/null 2>&1; then
    echo "Generating Gradle wrapper..."
    gradle wrapper --gradle-version=8.9 --no-daemon
    chmod +x ./gradlew
    echo "Running :core:test locally..."
    set +e
    ./gradlew -c "$CORE_ONLY_SETTINGS" $TEST_CMD
    local r=$?
    set -e
    return $r
  fi
  return 2
}

run_docker() {
  echo "No local Gradle wrapper or gradle found. Using Docker (Java 17 + Gradle, no Android SDK)..."
  IMAGE="${ANDROID_BUILD_IMAGE:-gradle:8.9-jdk17}"
  echo "Image: $IMAGE (override with ANDROID_BUILD_IMAGE)"
  timeout "$TIMEOUT_SEC" docker run --rm \
    -v "$PROJECT_ROOT:/app" \
    -w /app \
    -e GRADLE_USER_HOME=/app/.gradle \
    "$IMAGE" \
    bash -c "gradle -c $CORE_ONLY_SETTINGS $TEST_CMD" || {
      e=$?
      if [[ $e -eq 124 ]]; then
        echo "Tests timed out after ${TIMEOUT_SEC}s. Increase with TEST_TIMEOUT_SEC or run locally with ./gradlew."
        exit 124
      fi
      exit $e
    }
}

run_local
r=$?
if [[ $r -eq 0 ]]; then exit 0; fi
if [[ $r -eq 2 ]]; then run_docker; fi
exit $r
