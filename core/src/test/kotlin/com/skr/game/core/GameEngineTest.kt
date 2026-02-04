package com.skr.game.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GameEngineTest {

    @ParameterizedTest
    @CsvSource(
        "ROCK, SCISSORS, PLAYER_A_WINS",
        "SCISSORS, PAPER, PLAYER_A_WINS",
        "PAPER, ROCK, PLAYER_A_WINS",
        "SCISSORS, ROCK, PLAYER_B_WINS",
        "PAPER, SCISSORS, PLAYER_B_WINS",
        "ROCK, PAPER, PLAYER_B_WINS",
        "ROCK, ROCK, DRAW",
        "PAPER, PAPER, DRAW",
        "SCISSORS, SCISSORS, DRAW",
    )
    fun `resolveRound returns correct result`(a: String, b: String, expected: String) {
        val moveA = Move.valueOf(a)
        val moveB = Move.valueOf(b)
        val expectedResult = RoundResult.valueOf(expected)
        assertEquals(expectedResult, GameEngine.resolveRound(moveA, moveB))
    }

    @Test
    fun `potAfterDraw takes 1 percent`() {
        assertEquals(19.80, GameEngine.potAfterDraw(20.0))
        assertEquals(99.00, GameEngine.potAfterDraw(100.0))
    }

    @Test
    fun `winnerPayout returns 90 percent of pot`() {
        assertEquals(18.0, GameEngine.winnerPayout(20.0))
        assertEquals(17.82, GameEngine.winnerPayout(19.8))
    }

    @Test
    fun `isMatchOver true when either has 2 wins`() {
        assertTrue(GameEngine.isMatchOver(2, 0))
        assertTrue(GameEngine.isMatchOver(0, 2))
        assertTrue(GameEngine.isMatchOver(2, 1))
        assertFalse(GameEngine.isMatchOver(1, 0))
        assertFalse(GameEngine.isMatchOver(1, 1))
    }

    @Test
    fun `stakeTiers and potForStake match game design`() {
        assertEquals(listOf(5, 20, 100, 500), GameEngine.stakeTiers())
        assertEquals(10, GameEngine.potForStake(5))
        assertEquals(1000, GameEngine.potForStake(500))
    }

    @Test
    fun `runMatch win 2-0`() {
        val rounds = listOf(
            Move.ROCK to Move.SCISSORS,
            Move.PAPER to Move.ROCK,
        )
        val result = GameEngine.runMatch(20.0, rounds)
        assertEquals(2, result.scoreA)
        assertEquals(0, result.scoreB)
        assertEquals(20.0, result.finalPot)
        assertEquals(RoundResult.PLAYER_A_WINS, result.winner)
        assertEquals(18.0, result.winnerPayout)
    }

    @Test
    fun `runMatch win 2-1 with one draw`() {
        val rounds = listOf(
            Move.ROCK to Move.ROCK,
            Move.ROCK to Move.SCISSORS,
            Move.PAPER to Move.ROCK,
        )
        val result = GameEngine.runMatch(20.0, rounds)
        assertEquals(2, result.scoreA)
        assertEquals(0, result.scoreB)
        assertEquals(19.80, result.finalPot)
        assertEquals(RoundResult.PLAYER_A_WINS, result.winner)
        assertEquals(17.82, result.winnerPayout)
    }

    @Test
    fun `runMatch two draws then win`() {
        val rounds = listOf(
            Move.ROCK to Move.ROCK,
            Move.PAPER to Move.PAPER,
            Move.ROCK to Move.SCISSORS,
            Move.PAPER to Move.ROCK,
        )
        val result = GameEngine.runMatch(20.0, rounds)
        assertEquals(2, result.scoreA)
        assertEquals(0, result.scoreB)
        assertEquals(19.60, result.finalPot)
        assertEquals(RoundResult.PLAYER_A_WINS, result.winner)
        assertEquals(17.64, result.winnerPayout)
    }

    @Test
    fun `runMatch player B wins 2-0`() {
        val rounds = listOf(
            Move.SCISSORS to Move.ROCK,
            Move.PAPER to Move.SCISSORS,
        )
        val result = GameEngine.runMatch(20.0, rounds)
        assertEquals(0, result.scoreA)
        assertEquals(2, result.scoreB)
        assertEquals(20.0, result.finalPot)
        assertEquals(RoundResult.PLAYER_B_WINS, result.winner)
        assertEquals(18.0, result.winnerPayout)
    }
}
