#!/usr/bin/env bash
# Run core game logic tests without Gradle/Android (small Docker, Java 17 only).
set -e
cd "$(dirname "$0")/.."
STANDALONE="$(dirname "$0")/standalone-core-test"
echo "Running core game logic checks (standalone, Java 17 Docker)..."
docker run --rm \
  -v "$STANDALONE:/src:ro" \
  -w /src \
  eclipse-temurin:17-jdk \
  bash -c 'apt-get update -qq && apt-get install -y -qq kotlin 2>/dev/null; kotlinc -include-runtime -d out.jar Move.kt RoundResult.kt GameEngine.kt Main.kt && java -jar out.jar'
