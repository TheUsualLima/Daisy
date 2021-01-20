package com.jason.daisy.scrambletimer

import org.worldcubeassociation.tnoodle.puzzle.*
import org.worldcubeassociation.tnoodle.scrambles.Puzzle

class GetPuzzleStringUseCase {
    fun execute(puzzle: Puzzle) = when(puzzle::class) {
        ThreeByThreeCubePuzzle::class -> PuzzleType.ThreeByThree.puzzleName
        TwoByTwoCubePuzzle::class -> PuzzleType.TwoByTwo.puzzleName
        FourByFourCubePuzzle::class -> PuzzleType.FourByFour.puzzleName
        PyraminxPuzzle::class -> PuzzleType.Pyraminx.puzzleName
        ClockPuzzle::class -> PuzzleType.Clock.puzzleName
        SquareOnePuzzle::class -> PuzzleType.SquareOne.puzzleName
        SkewbPuzzle::class -> PuzzleType.Skewb.puzzleName
        MegaminxPuzzle::class -> PuzzleType.Megaminx.puzzleName
        else -> throw Exception("WCA puzzle does not have an associated enum value")
    }
}