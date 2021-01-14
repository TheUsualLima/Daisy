package com.jason.daisy.scrambletimer

class GetDefaultPuzzleTypeUseCase {
    fun execute() : CubeType {
        return CubeType.ThreeByThree
    }
}