package com.jason.daisy.scrambletimer

import org.worldcubeassociation.tnoodle.puzzle.ThreeByThreeCubePuzzle

class GetPuzzleUseCase {
    fun execute(c : CubeType) = when(c) {
            CubeType.TwoByTwo -> ThreeByThreeCubePuzzle()
            CubeType.ThreeByThree -> ThreeByThreeCubePuzzle()
            CubeType.FourByFour -> ThreeByThreeCubePuzzle()
            CubeType.Pyraminx -> ThreeByThreeCubePuzzle()
            CubeType.Clock -> ThreeByThreeCubePuzzle()
            CubeType.SquareOne -> ThreeByThreeCubePuzzle()
            CubeType.Skewb -> ThreeByThreeCubePuzzle()
            CubeType.Megaminx -> ThreeByThreeCubePuzzle()
    }
}