package com.jason.daisy.scrambletimer

sealed class CubeType(val cubeName : String) {
    object ThreeByThree : CubeType("Three by Three")
    object Clock : CubeType("Clock")
    object FourByFour : CubeType("Four by Four")
    object Megaminx : CubeType("Megaminx")
    object TwoByTwo : CubeType("Two by Two")
    object SquareOne : CubeType("Square One")
    object Skewb : CubeType("Skewb")
    object Pyraminx : CubeType("Pyraminx")
}
