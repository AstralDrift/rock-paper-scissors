# SKR – Rock, Paper, Scissors on SKR Tokens

Mobile Android game: RPS-style matches with SKR token stakes, async play, and cosmetic progression. See [docs/game-design.md](docs/game-design.md) for full rules and economy.

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3)
- **Android** min SDK 26, target 34
- Navigation: Compose Navigation (Home → Rules, Play → Stake → Game, Profile)

## How to run and test (including from Cursor, no Android Studio)

All build and test steps can be run from the Cursor terminal. You need **Docker** (for a Java 17 + Android SDK environment, since the host may have Java 25 which Gradle/Kotlin don’t support yet).

1. **Run core game logic tests (recommended first step)**  
   From the project root:
   ```bash
   ./scripts/run-tests.sh
   ```
   - Uses Docker image `mobiledevops/android-sdk-image:34.0.0` (first run can take several minutes to pull).
   - If `gradlew` is missing, the script generates it inside the container, then runs `:core:test`.
   - The **core** module is pure Kotlin (RPS rules, pot math, match state) and is tested without launching the app.

2. **Quick game-logic check (no Android SDK)**  
   Smaller Docker image (eclipse-temurin:17-jdk); installs Kotlin in the container on first run. Covers `GameEngine` (resolve round, pot, stakes, `runMatch`):
   ```bash
   ./scripts/run-standalone-test.sh
   ```

3. **Full Android build** (with Docker, same image as above)  
   After `./scripts/run-tests.sh` has run once (so `gradlew` exists), you can build the app:
   ```bash
   docker run --rm -v "$PWD:/app" -w /app -e GRADLE_USER_HOME=/app/.gradle -e ANDROID_HOME=/opt/android-sdk mobiledevops/android-sdk-image:34.0.0 ./gradlew assembleDebug
   ```
   Output: `app/build/outputs/apk/debug/app-debug.apk`.

4. **Android Studio**  
   Open this folder, sync Gradle, run on an emulator or device.

5. **Local Gradle (if you have Java 17 and Android SDK)**  
   `gradle wrapper --gradle-version=8.9` then `./gradlew :core:test` or `./gradlew assembleDebug`.

## Project layout

- **core/** – Pure Kotlin game logic (no Android): `Move`, `RoundResult`, `MatchResult`, `GameEngine` (resolve round, pot math, stake tiers, `runMatch` for full best-of-3). Unit tests in `core/src/test/`.
- **app/** – Android app (Compose UI, navigation). Depends on `core`.

## App structure

- **Home**: Play, Rules, Profile.
- **Rules**: In-app rules text from game design (payouts, async, stakes, progression).
- **Stake**: Pick tier (5 / 20 / 100 / 500 SKR); “Find match” navigates to Game with that stake.
- **Game**: Full local match: round number, score, pot; you pick a move, opponent is simulated; round and match result (you win/lose, payout). “End match” returns to Home. Backend/commit-reveal not added yet.
- **Profile**: Placeholder for level, collection, crates.

## Next steps (out of scope of current scaffold)

- Backend or Solana program: matchmaking, round state, commit-reveal moves, payouts.
- SKR token integration (wallet, stake, receive winnings).
- Real matchmaking and round resolution.
- Level/XP, loot crates, cosmetics (see game-design.md for the list).
