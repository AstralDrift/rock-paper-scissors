#!/usr/bin/env bash
# Run build and tests from Cursor (no Android Studio).
# Uses Docker with Java 17 + Android SDK so full Gradle build works.
set -e
cd "$(dirname "$0")/.."
PROJECT_ROOT="$PWD"
IMAGE="${ANDROID_BUILD_IMAGE:-mobiledevops/android-sdk-image:34.0.0}"

# Ensure we have Gradle wrapper (generated once inside Docker)
if [[ ! -f ./gradlew ]]; then
  echo "Generating Gradle wrapper (requires Docker)..."
  docker run --rm \
    -v "$PROJECT_ROOT:/app" \
    -w /app \
    -e GRADLE_USER_HOME=/app/.gradle \
    "$IMAGE" \
    bash -c 'gradle wrapper --gradle-version=8.9 --no-daemon 2>/dev/null || (apt-get update -qq && apt-get install -y -qq unzip curl > /dev/null && curl -sL -o /tmp/gradle.zip https://services.gradle.org/distributions/gradle-8.9-bin.zip && unzip -q -o /tmp/gradle.zip -d /tmp && /tmp/gradle-8.9/bin/gradle wrapper --gradle-version=8.9 --no-daemon)'
  chmod +x ./gradlew
  echo "Wrapper created."
fi

# Run core module unit tests
echo "Running :core:test..."
docker run --rm \
  -v "$PROJECT_ROOT:/app" \
  -w /app \
  -e GRADLE_USER_HOME=/app/.gradle \
  -e ANDROID_HOME=/opt/android-sdk \
  "$IMAGE" \
  ./gradlew :core:test --no-daemon --no-configuration-cache
