package com.jason.daisy.scrambletimer

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.worldcubeassociation.tnoodle.puzzle.FourByFourCubePuzzle
import org.worldcubeassociation.tnoodle.puzzle.ThreeByThreeCubeFewestMovesPuzzle

internal class GetPuzzleStringUseCaseTest {
    @Test
    fun getLegalPuzzle() {
        val s1 = GetPuzzleStringUseCase().execute(FourByFourCubePuzzle())
        val s2 = PuzzleType.FourByFour.puzzleName
        Assertions.assertEquals(s1, s2)
    }

    @Test
    fun getIllegalPuzzle() {
        var eCaught = false
        try {
            GetPuzzleStringUseCase().execute(ThreeByThreeCubeFewestMovesPuzzle())
        } catch (e: Exception) {
            eCaught = true
        }
        Assertions.assertTrue(eCaught)
    }
}