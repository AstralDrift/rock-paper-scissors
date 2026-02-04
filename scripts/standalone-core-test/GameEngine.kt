package com.skr.game.core
import kotlin.math.roundToLong
object GameEngine {
    const val HOUSE_CUT_WIN_PERCENT = 10.0
    const val HOUSE_CUT_DRAW_PERCENT = 1.0
    const val ROUNDS_TO_WIN_MATCH = 2
    fun resolveRound(moveA: Move, moveB: Move): RoundResult = when {
        moveA == moveB -> RoundResult.DRAW
        moveA == Move.ROCK && moveB == Move.SCISSORS -> RoundResult.PLAYER_A_WINS
        moveA == Move.ROCK && moveB == Move.PAPER -> RoundResult.PLAYER_B_WINS
        moveA == Move.PAPER && moveB == Move.ROCK -> RoundResult.PLAYER_A_WINS
        moveA == Move.PAPER && moveB == Move.SCISSORS -> RoundResult.PLAYER_B_WINS
        moveA == Move.SCISSORS && moveB == Move.PAPER -> RoundResult.PLAYER_A_WINS
        moveA == Move.SCISSORS && moveB == Move.ROCK -> RoundResult.PLAYER_B_WINS
        else -> RoundResult.DRAW
    }
    fun potAfterDraw(currentPot: Double): Double {
        val houseTake = currentPot * (HOUSE_CUT_DRAW_PERCENT / 100.0)
        return (currentPot - houseTake).let { (it * 100.0).roundToLong() / 100.0 }
    }
    fun winnerPayout(pot: Double): Double = (pot * (100.0 - HOUSE_CUT_WIN_PERCENT) / 100.0).let { (it * 100.0).roundToLong() / 100.0 }
    fun isMatchOver(scoreA: Int, scoreB: Int): Boolean = scoreA >= ROUNDS_TO_WIN_MATCH || scoreB >= ROUNDS_TO_WIN_MATCH
    fun stakeTiers(): List<Int> = listOf(5, 20, 100, 500)
    fun potForStake(entryPerPlayer: Int): Int = entryPerPlayer * 2
    fun runMatch(initialPot: Double, rounds: List<Pair<Move, Move>>): MatchResult {
        var scoreA = 0
        var scoreB = 0
        var pot = initialPot
        for ((moveA, moveB) in rounds) {
            when (val r = resolveRound(moveA, moveB)) {
                RoundResult.DRAW -> pot = potAfterDraw(pot)
                RoundResult.PLAYER_A_WINS -> { scoreA++; if (isMatchOver(scoreA, scoreB)) break }
                RoundResult.PLAYER_B_WINS -> { scoreB++; if (isMatchOver(scoreA, scoreB)) break }
            }
        }
        val winner = when {
            scoreA >= ROUNDS_TO_WIN_MATCH -> RoundResult.PLAYER_A_WINS
            scoreB >= ROUNDS_TO_WIN_MATCH -> RoundResult.PLAYER_B_WINS
            else -> null
        }
        val payout = winner?.let { winnerPayout(pot) }
        return MatchResult(scoreA, scoreB, pot, winner, payout)
    }
}
