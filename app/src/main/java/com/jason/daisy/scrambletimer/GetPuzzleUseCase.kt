package com.jason.daisy.scrambletimer

import org.worldcubeassociation.tnoodle.puzzle.*

class GetPuzzleUseCase {
    fun execute(puzzle : PuzzleType) = when(puzzle) {
            PuzzleType.TwoByTwo -> TwoByTwoCubePuzzle()
            PuzzleType.ThreeByThree -> ThreeByThreeCubePuzzle()
            PuzzleType.FourByFour -> FourByFourCubePuzzle()
            PuzzleType.Pyraminx -> PyraminxPuzzle()
            PuzzleType.Clock -> ClockPuzzle()
            PuzzleType.SquareOne -> SquareOnePuzzle()
            PuzzleType.Skewb -> SkewbPuzzle()
            PuzzleType.Megaminx -> MegaminxPuzzle()
    }
}