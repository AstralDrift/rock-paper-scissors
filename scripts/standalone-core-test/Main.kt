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
    if (failed == 0) println("All checks passed.") else error("$failed check(s) failed")
}
