package com.skr.game.core

/**
 * Pure progression math: XP, level, crate earn rate.
 * See docs/game-design.md §5. No Android deps.
 */
object Progression {

    const val XP_PER_MATCH = 10
    const val XP_WIN_BONUS = 20
    const val XP_PER_LEVEL = 100
    const val GAMES_PER_CRATE = 3

    /** XP earned from one match. [won] true = match winner. */
    fun xpForMatch(won: Boolean): Int = XP_PER_MATCH + if (won) XP_WIN_BONUS else 0

    /** Level from total cumulative XP (1-based). */
    fun levelFromTotalXp(totalXp: Int): Int =
        (totalXp / XP_PER_LEVEL).coerceAtLeast(0) + 1

    /** XP required to reach [level] (cumulative). Level 1 = 0, level 2 = 100, etc. */
    fun xpToReachLevel(level: Int): Int = ((level - 1) * XP_PER_LEVEL).coerceAtLeast(0)

    /** XP progress within current level (0 until XP_PER_LEVEL). */
    fun xpInCurrentLevel(totalXp: Int): Int = totalXp % XP_PER_LEVEL

    /** Number of crates earned by playing [gamesPlayed] games (e.g. 1 per GAMES_PER_CRATE). */
    fun cratesEarnedFromGames(gamesPlayed: Int): Int = gamesPlayed / GAMES_PER_CRATE
}
