package com.skr.game.data

/**
 * Local progression state (persisted). See docs/game-design.md §5.
 */
data class ProgressionState(
    val totalXp: Int = 0,
    val gamesPlayed: Int = 0,
    val cratesOwned: Int = 0,
    val unlockedCollectibleIds: Set<String> = emptySet(),
    val dust: Int = 0,
) {
    val level: Int get() = com.skr.game.core.Progression.levelFromTotalXp(totalXp)
    val xpInLevel: Int get() = com.skr.game.core.Progression.xpInCurrentLevel(totalXp)
    val xpPerLevel: Int get() = com.skr.game.core.Progression.XP_PER_LEVEL
}

/** Result of opening one crate: either a new collectible or dust (duplicate). */
sealed class OpenCrateResult {
    data class New(val collectible: Collectible) : OpenCrateResult()
    data class Duplicate(val dust: Int) : OpenCrateResult()
}
