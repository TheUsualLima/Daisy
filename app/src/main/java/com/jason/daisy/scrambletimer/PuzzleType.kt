package com.jason.daisy.scrambletimer

enum class PuzzleType(val puzzleName: String) {
    TwoByTwo("Two by Two"),
    ThreeByThree ("Three by Three"),
    FourByFour ("Four by Four"),
    Pyraminx ("Pyraminx"),
    Clock ("Clock"),
    SquareOne("Square One"),
    Skewb("Skewb"),
    Megaminx("Megaminx");

    override fun toString(): String {
        return puzzleName
    }
}
