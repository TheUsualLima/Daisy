package com.jason.daisy.scrambletimer

sealed class CubeType(val cubeName : String) {
    object TwoByTwo : CubeType("Two by Two")
    object ThreeByThree : CubeType("Three by Three")
    object FourByFour : CubeType("Four by Four")
    object Pyraminx : CubeType("Pyraminx")
    object Clock : CubeType("Clock")
    object SquareOne : CubeType("Square One")
    object Skewb : CubeType("Skewb")
    object Megaminx : CubeType("Megaminx")
}
