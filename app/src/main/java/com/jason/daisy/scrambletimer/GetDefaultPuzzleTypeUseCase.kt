package com.jason.daisy.scrambletimer

class GetDefaultPuzzleTypeUseCase {
    fun execute() : PuzzleType {
        return PuzzleType.ThreeByThree
    }
}