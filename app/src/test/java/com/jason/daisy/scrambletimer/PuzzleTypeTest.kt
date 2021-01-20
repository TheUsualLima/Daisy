package com.jason.daisy.scrambletimer

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PuzzleTypeTest {

    @Test
    fun getPuzzleTypeException() {
        var eCaught = false
        try {
            PuzzleType.getPuzzleType("Puzzle of Life")
        } catch (e: Exception) {
            eCaught = true
        }
        Assertions.assertTrue(eCaught)
    }

    @Test
    fun getPuzzleType() {
        val puzzleType = PuzzleType.getPuzzleType("Pyraminx")
        Assertions.assertEquals(puzzleType, PuzzleType.Pyraminx)
    }
}