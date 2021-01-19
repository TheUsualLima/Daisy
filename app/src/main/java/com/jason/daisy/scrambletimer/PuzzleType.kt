package com.jason.daisy.scrambletimer

//sealed class PuzzleType(val cubeName : String) {
//    object TwoByTwo : PuzzleType("Two by Two")
//    object ThreeByThree : PuzzleType("Three by Three")
//    object FourByFour : PuzzleType("Four by Four")
//    object Pyraminx : PuzzleType("Pyraminx")
//    object Clock : PuzzleType("Clock")
//    object SquareOne : PuzzleType("Square One")
//    object Skewb : PuzzleType("Skewb")
//    object Megaminx : PuzzleType("Megaminx")
//}
enum class PuzzleType(val puzzleName: String) {
    TwoByTwo("Two by Two"),
    ThreeByThree ("Three by Three"),
    FourByFour ("Four by Four"),
    Pyraminx ("Pyraminx"),
    Clock ("Clock"),
    SquareOne("Square One"),
    Skewb("Skewb"),
    Megaminx("Megaminx")
}
