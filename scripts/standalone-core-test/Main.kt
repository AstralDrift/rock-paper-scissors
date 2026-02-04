package com.skr.game.core
fun main() {
    var failed = 0
    fun eq<T>(a: T, b: T, msg: String) { if (a != b) { println("FAIL: $msg (got $a, expected $b)"); failed++ } }
    // resolveRound
    eq(GameEngine.resolveRound(Move.ROCK, Move.SCISSORS), RoundResult.PLAYER_A_WINS, "Rock beats Scissors")
    eq(GameEngine.resolveRound(Move.PAPER, Move.ROCK), RoundResult.PLAYER_A_WINS, "Paper beats Rock")
    eq(GameEngine.resolveRound(Move.SCISSORS, Move.PAPER), RoundResult.PLAYER_A_WINS, "Scissors beats Paper")
    eq(GameEngine.resolveRound(Move.ROCK, Move.ROCK), RoundResult.DRAW, "Rock draw")
    // potAfterDraw
    eq(GameEngine.potAfterDraw(20.0), 19.80, "potAfterDraw 20")
    eq(GameEngine.winnerPayout(20.0), 18.0, "winnerPayout 20")
    eq(GameEngine.winnerPayout(19.8), 17.82, "winnerPayout 19.8")
    eq(GameEngine.isMatchOver(2, 0), true, "match over 2-0")
    eq(GameEngine.isMatchOver(1, 1), false, "match not over 1-1")
    eq(GameEngine.stakeTiers(), listOf(5, 20, 100, 500), "stakeTiers")
    eq(GameEngine.potForStake(5), 10, "potForStake 5")
    // runMatch
    val r1 = GameEngine.runMatch(20.0, listOf(Move.ROCK to Move.SCISSORS, Move.PAPER to Move.ROCK))
    eq(r1.scoreA, 2, "runMatch 2-0 scoreA"); eq(r1.scoreB, 0, "runMatch 2-0 scoreB")
    eq(r1.winner, RoundResult.PLAYER_A_WINS, "runMatch 2-0 winner"); eq(r1.winnerPayout, 18.0, "runMatch 2-0 payout")
    val r2 = GameEngine.runMatch(20.0, listOf(Move.SCISSORS to Move.ROCK, Move.PAPER to Move.SCISSORS))
    eq(r2.scoreA, 0, "runMatch B 2-0 scoreA"); eq(r2.scoreB, 2, "runMatch B 2-0 scoreB")
    eq(r2.winner, RoundResult.PLAYER_B_WINS, "runMatch B 2-0 winner")
    if (failed == 0) println("All checks passed.") else error("$failed check(s) failed")
}
