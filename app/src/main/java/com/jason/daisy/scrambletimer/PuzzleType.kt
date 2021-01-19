package com.jason.daisy.scrambletimer

enum class PuzzleType(val puzzleName: String) {
    ThreeByThree ("Three by Three"),
    TwoByTwo("Two by Two"),
    FourByFour ("Four by Four"),
    Pyraminx ("Pyraminx"),
    Clock ("Clock"),
    SquareOne("Square One"),
    Skewb("Skewb"),
    Megaminx("Megaminx");

    override fun toString(): String {
        return puzzleName
    }

    companion object {
        fun getPuzzleType(str: String) = when(str) {
            ThreeByThree.toString() -> ThreeByThree
            TwoByTwo.toString() -> TwoByTwo
            FourByFour.toString() -> FourByFour
            Pyraminx.toString() -> Pyraminx
            Clock.toString() -> Clock
            SquareOne.toString() -> SquareOne
            Skewb.toString() -> Skewb
            Megaminx.toString() -> Megaminx
            else -> null
        }
    }
}
