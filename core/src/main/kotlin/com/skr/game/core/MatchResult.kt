package com.skr.game.core

/**
 * Result of a completed or in-progress best-of-3 match.
 * [winner] and [winnerPayout] are non-null when the match is over (either player has 2 round wins).
 */
data class MatchResult(
    val scoreA: Int,
    val scoreB: Int,
    val finalPot: Double,
    val winner: RoundResult?,
    val winnerPayout: Double?,
)
