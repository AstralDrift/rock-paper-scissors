package com.skr.game.core
data class MatchResult(
    val scoreA: Int,
    val scoreB: Int,
    val finalPot: Double,
    val winner: RoundResult?,
    val winnerPayout: Double?
)
