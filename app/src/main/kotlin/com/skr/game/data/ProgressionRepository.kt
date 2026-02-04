package com.skr.game.data

import android.content.Context
import com.skr.game.core.Progression

private const val PREFS_NAME = "progression"
private const val KEY_TOTAL_XP = "totalXp"
private const val KEY_GAMES_PLAYED = "gamesPlayed"
private const val KEY_CRATES_OWNED = "cratesOwned"
private const val KEY_UNLOCKED = "unlockedIds"
private const val KEY_DUST = "dust"
private const val DUST_PER_DUPLICATE = 1

class ProgressionRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getState(): ProgressionState = ProgressionState(
        totalXp = prefs.getInt(KEY_TOTAL_XP, 0),
        gamesPlayed = prefs.getInt(KEY_GAMES_PLAYED, 0),
        cratesOwned = prefs.getInt(KEY_CRATES_OWNED, 0),
        unlockedCollectibleIds = prefs.getStringSet(KEY_UNLOCKED, null) ?: emptySet(),
        dust = prefs.getInt(KEY_DUST, 0),
    )

    fun addMatchResult(won: Boolean): ProgressionState {
        val state = getState()
        val newXp = state.totalXp + Progression.xpForMatch(won)
        val newGames = state.gamesPlayed + 1
        val prevCratesFromGames = Progression.cratesEarnedFromGames(state.gamesPlayed)
        val newCratesFromGames = Progression.cratesEarnedFromGames(newGames)
        val extraCrates = newCratesFromGames - prevCratesFromGames
        val newState = state.copy(
            totalXp = newXp,
            gamesPlayed = newGames,
            cratesOwned = state.cratesOwned + extraCrates,
        )
        saveState(newState)
        return newState
    }

    fun openCrate(): Pair<ProgressionState, OpenCrateResult>? {
        val state = getState()
        if (state.cratesOwned <= 0) return null
        val collectible = Catalog.random()
        val (newUnlocked, result) = if (collectible.id in state.unlockedCollectibleIds) {
            state.unlockedCollectibleIds to OpenCrateResult.Duplicate(DUST_PER_DUPLICATE)
        } else {
            state.unlockedCollectibleIds + collectible.id to OpenCrateResult.New(collectible)
        }
        val newDust = state.dust + if (result is OpenCrateResult.Duplicate) DUST_PER_DUPLICATE else 0
        val newState = state.copy(
            cratesOwned = state.cratesOwned - 1,
            unlockedCollectibleIds = newUnlocked,
            dust = newDust,
        )
        saveState(newState)
        return newState to result
    }

    private fun saveState(s: ProgressionState) {
        prefs.edit()
            .putInt(KEY_TOTAL_XP, s.totalXp)
            .putInt(KEY_GAMES_PLAYED, s.gamesPlayed)
            .putInt(KEY_CRATES_OWNED, s.cratesOwned)
            .putStringSet(KEY_UNLOCKED, s.unlockedCollectibleIds)
            .putInt(KEY_DUST, s.dust)
            .apply()
    }
}
