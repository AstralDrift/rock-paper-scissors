package com.skr.game.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProgressionTest {

    @Test
    fun `xpForMatch gives base plus win bonus`() {
        assertEquals(10, Progression.xpForMatch(false))
        assertEquals(30, Progression.xpForMatch(true))
    }

    @Test
    fun `levelFromTotalXp is 1-based and steps every 100 XP`() {
        assertEquals(1, Progression.levelFromTotalXp(0))
        assertEquals(1, Progression.levelFromTotalXp(99))
        assertEquals(2, Progression.levelFromTotalXp(100))
        assertEquals(3, Progression.levelFromTotalXp(200))
    }

    @Test
    fun `xpToReachLevel and xpInCurrentLevel`() {
        assertEquals(0, Progression.xpToReachLevel(1))
        assertEquals(100, Progression.xpToReachLevel(2))
        assertEquals(50, Progression.xpInCurrentLevel(150))
    }

    @Test
    fun `cratesEarnedFromGames one per 3 games`() {
        assertEquals(0, Progression.cratesEarnedFromGames(0))
        assertEquals(0, Progression.cratesEarnedFromGames(2))
        assertEquals(1, Progression.cratesEarnedFromGames(3))
        assertEquals(2, Progression.cratesEarnedFromGames(6))
    }
}
